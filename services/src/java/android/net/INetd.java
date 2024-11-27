package android.net;

/* loaded from: classes.dex */
public interface INetd extends android.os.IInterface {
    public static final int CLAT_MARK = -559038041;
    public static final int CONF = 1;
    public static final int DUMMY_NET_ID = 51;
    public static final int FIREWALL_ALLOWLIST = 0;

    @java.lang.Deprecated
    public static final int FIREWALL_BLACKLIST = 1;
    public static final int FIREWALL_CHAIN_DOZABLE = 1;
    public static final int FIREWALL_CHAIN_NONE = 0;
    public static final int FIREWALL_CHAIN_POWERSAVE = 3;
    public static final int FIREWALL_CHAIN_RESTRICTED = 4;
    public static final int FIREWALL_CHAIN_STANDBY = 2;
    public static final int FIREWALL_DENYLIST = 1;
    public static final int FIREWALL_RULE_ALLOW = 1;
    public static final int FIREWALL_RULE_DENY = 2;

    @java.lang.Deprecated
    public static final int FIREWALL_WHITELIST = 0;
    public static final java.lang.String HASH = "50bce96bc8d5811ed952950df30ec503f8a561ed";
    public static final java.lang.String IF_FLAG_BROADCAST = "broadcast";
    public static final java.lang.String IF_FLAG_LOOPBACK = "loopback";
    public static final java.lang.String IF_FLAG_MULTICAST = "multicast";
    public static final java.lang.String IF_FLAG_POINTOPOINT = "point-to-point";
    public static final java.lang.String IF_FLAG_RUNNING = "running";
    public static final java.lang.String IF_STATE_DOWN = "down";
    public static final java.lang.String IF_STATE_UP = "up";
    public static final int IPSEC_DIRECTION_IN = 0;
    public static final int IPSEC_DIRECTION_OUT = 1;
    public static final java.lang.String IPSEC_INTERFACE_PREFIX = "ipsec";
    public static final int IPV4 = 4;
    public static final int IPV6 = 6;
    public static final int IPV6_ADDR_GEN_MODE_DEFAULT = 0;
    public static final int IPV6_ADDR_GEN_MODE_EUI64 = 0;
    public static final int IPV6_ADDR_GEN_MODE_NONE = 1;
    public static final int IPV6_ADDR_GEN_MODE_RANDOM = 3;
    public static final int IPV6_ADDR_GEN_MODE_STABLE_PRIVACY = 2;
    public static final int LOCAL_NET_ID = 99;
    public static final int NEIGH = 2;
    public static final java.lang.String NEXTHOP_NONE = "";
    public static final java.lang.String NEXTHOP_THROW = "throw";
    public static final java.lang.String NEXTHOP_UNREACHABLE = "unreachable";
    public static final int NO_PERMISSIONS = 0;
    public static final int PENALTY_POLICY_ACCEPT = 1;
    public static final int PENALTY_POLICY_LOG = 2;
    public static final int PENALTY_POLICY_REJECT = 3;
    public static final int PERMISSION_INTERNET = 4;
    public static final int PERMISSION_NETWORK = 1;
    public static final int PERMISSION_NONE = 0;
    public static final int PERMISSION_SYSTEM = 2;
    public static final int PERMISSION_UNINSTALLED = -1;
    public static final int PERMISSION_UPDATE_DEVICE_STATS = 8;
    public static final int UNREACHABLE_NET_ID = 52;
    public static final int VERSION = 14;

    @java.lang.Deprecated
    void bandwidthAddNaughtyApp(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void bandwidthAddNiceApp(int i) throws android.os.RemoteException;

    boolean bandwidthEnableDataSaver(boolean z) throws android.os.RemoteException;

    void bandwidthRemoveInterfaceAlert(java.lang.String str) throws android.os.RemoteException;

    void bandwidthRemoveInterfaceQuota(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    void bandwidthRemoveNaughtyApp(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void bandwidthRemoveNiceApp(int i) throws android.os.RemoteException;

    void bandwidthSetGlobalAlert(long j) throws android.os.RemoteException;

    void bandwidthSetInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException;

    void bandwidthSetInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException;

    @java.lang.Deprecated
    java.lang.String clatdStart(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    @java.lang.Deprecated
    void clatdStop(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    void firewallAddUidInterfaceRules(java.lang.String str, int[] iArr) throws android.os.RemoteException;

    @java.lang.Deprecated
    void firewallEnableChildChain(int i, boolean z) throws android.os.RemoteException;

    @java.lang.Deprecated
    void firewallRemoveUidInterfaceRules(int[] iArr) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean firewallReplaceUidChain(java.lang.String str, boolean z, int[] iArr) throws android.os.RemoteException;

    void firewallSetFirewallType(int i) throws android.os.RemoteException;

    void firewallSetInterfaceRule(java.lang.String str, int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void firewallSetUidRule(int i, int i2, int i3) throws android.os.RemoteException;

    android.net.MarkMaskParcel getFwmarkForNetwork(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.os.IBinder getOemNetd() throws android.os.RemoteException;

    java.lang.String getProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void idletimerAddInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void idletimerRemoveInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void interfaceAddAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void interfaceClearAddrs(java.lang.String str) throws android.os.RemoteException;

    void interfaceDelAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    android.net.InterfaceConfigurationParcel interfaceGetCfg(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] interfaceGetList() throws android.os.RemoteException;

    void interfaceSetCfg(android.net.InterfaceConfigurationParcel interfaceConfigurationParcel) throws android.os.RemoteException;

    void interfaceSetEnableIPv6(java.lang.String str, boolean z) throws android.os.RemoteException;

    void interfaceSetIPv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException;

    void interfaceSetMtu(java.lang.String str, int i) throws android.os.RemoteException;

    void ipSecAddSecurityAssociation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, java.lang.String str3, byte[] bArr, int i7, java.lang.String str4, byte[] bArr2, int i8, java.lang.String str5, byte[] bArr3, int i9, int i10, int i11, int i12, int i13) throws android.os.RemoteException;

    void ipSecAddSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException;

    void ipSecAddTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException;

    int ipSecAllocateSpi(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException;

    void ipSecApplyTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException;

    void ipSecDeleteSecurityAssociation(int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4, int i5) throws android.os.RemoteException;

    void ipSecDeleteSecurityPolicy(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException;

    void ipSecMigrate(android.net.IpSecMigrateInfoParcel ipSecMigrateInfoParcel) throws android.os.RemoteException;

    void ipSecRemoveTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void ipSecRemoveTunnelInterface(java.lang.String str) throws android.os.RemoteException;

    void ipSecSetEncapSocketOwner(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException;

    void ipSecUpdateSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException;

    void ipSecUpdateTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException;

    void ipfwdAddInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void ipfwdDisableForwarding(java.lang.String str) throws android.os.RemoteException;

    void ipfwdEnableForwarding(java.lang.String str) throws android.os.RemoteException;

    boolean ipfwdEnabled() throws android.os.RemoteException;

    java.lang.String[] ipfwdGetRequesterList() throws android.os.RemoteException;

    void ipfwdRemoveInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isAlive() throws android.os.RemoteException;

    void networkAddInterface(int i, java.lang.String str) throws android.os.RemoteException;

    void networkAddLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException;

    void networkAddRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void networkAddRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException;

    void networkAddUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException;

    void networkAddUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException;

    boolean networkCanProtect(int i) throws android.os.RemoteException;

    void networkClearDefault() throws android.os.RemoteException;

    void networkClearPermissionForUser(int[] iArr) throws android.os.RemoteException;

    void networkCreate(android.net.NativeNetworkConfig nativeNetworkConfig) throws android.os.RemoteException;

    @java.lang.Deprecated
    void networkCreatePhysical(int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    void networkCreateVpn(int i, boolean z) throws android.os.RemoteException;

    void networkDestroy(int i) throws android.os.RemoteException;

    int networkGetDefault() throws android.os.RemoteException;

    void networkRejectNonSecureVpn(boolean z, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException;

    void networkRemoveInterface(int i, java.lang.String str) throws android.os.RemoteException;

    void networkRemoveLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException;

    void networkRemoveRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void networkRemoveRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException;

    void networkRemoveUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException;

    void networkRemoveUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException;

    void networkSetDefault(int i) throws android.os.RemoteException;

    void networkSetPermissionForNetwork(int i, int i2) throws android.os.RemoteException;

    void networkSetPermissionForUser(int i, int[] iArr) throws android.os.RemoteException;

    void networkSetProtectAllow(int i) throws android.os.RemoteException;

    void networkSetProtectDeny(int i) throws android.os.RemoteException;

    void networkUpdateRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException;

    void registerUnsolicitedEventListener(android.net.INetdUnsolicitedEventListener iNetdUnsolicitedEventListener) throws android.os.RemoteException;

    void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException;

    void setNetworkAllowlist(android.net.netd.aidl.NativeUidRangeConfig[] nativeUidRangeConfigArr) throws android.os.RemoteException;

    void setProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void setTcpRWmemorySize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void socketDestroy(android.net.UidRangeParcel[] uidRangeParcelArr, int[] iArr) throws android.os.RemoteException;

    void strictUidCleartextPenalty(int i, int i2) throws android.os.RemoteException;

    void tetherAddForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean tetherApplyDnsInterfaces() throws android.os.RemoteException;

    java.lang.String[] tetherDnsList() throws android.os.RemoteException;

    void tetherDnsSet(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    android.net.TetherStatsParcel[] tetherGetStats() throws android.os.RemoteException;

    void tetherInterfaceAdd(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] tetherInterfaceList() throws android.os.RemoteException;

    void tetherInterfaceRemove(java.lang.String str) throws android.os.RemoteException;

    boolean tetherIsEnabled() throws android.os.RemoteException;

    @java.lang.Deprecated
    android.net.TetherStatsParcel tetherOffloadGetAndClearStats(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.net.TetherStatsParcel[] tetherOffloadGetStats() throws android.os.RemoteException;

    @java.lang.Deprecated
    void tetherOffloadRuleAdd(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException;

    @java.lang.Deprecated
    void tetherOffloadRuleRemove(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException;

    @java.lang.Deprecated
    void tetherOffloadSetInterfaceQuota(int i, long j) throws android.os.RemoteException;

    void tetherRemoveForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void tetherStart(java.lang.String[] strArr) throws android.os.RemoteException;

    void tetherStartWithConfiguration(android.net.TetherConfigParcel tetherConfigParcel) throws android.os.RemoteException;

    void tetherStop() throws android.os.RemoteException;

    @java.lang.Deprecated
    void trafficSetNetPermForUids(int i, int[] iArr) throws android.os.RemoteException;

    @java.lang.Deprecated
    void trafficSwapActiveStatsMap() throws android.os.RemoteException;

    void wakeupAddInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void wakeupDelInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.net.INetd {
        @Override // android.net.INetd
        public boolean isAlive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public boolean firewallReplaceUidChain(java.lang.String str, boolean z, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public boolean bandwidthEnableDataSaver(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public void networkCreatePhysical(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkCreateVpn(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkDestroy(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkAddInterface(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveInterface(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkAddUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRejectNonSecureVpn(boolean z, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void socketDestroy(android.net.UidRangeParcel[] uidRangeParcelArr, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public boolean tetherApplyDnsInterfaces() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public android.net.TetherStatsParcel[] tetherGetStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void interfaceAddAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void interfaceDelAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public java.lang.String getProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void setProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecSetEncapSocketOwner(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public int ipSecAllocateSpi(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetd
        public void ipSecAddSecurityAssociation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, java.lang.String str3, byte[] bArr, int i7, java.lang.String str4, byte[] bArr2, int i8, java.lang.String str5, byte[] bArr3, int i9, int i10, int i11, int i12, int i13) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecDeleteSecurityAssociation(int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecApplyTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecRemoveTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecAddSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecUpdateSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecDeleteSecurityPolicy(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecAddTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecUpdateTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecRemoveTunnelInterface(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void wakeupAddInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void wakeupDelInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void idletimerAddInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void idletimerRemoveInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void strictUidCleartextPenalty(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public java.lang.String clatdStart(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void clatdStop(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public boolean ipfwdEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public java.lang.String[] ipfwdGetRequesterList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void ipfwdEnableForwarding(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipfwdDisableForwarding(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipfwdAddInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipfwdRemoveInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthSetInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthRemoveInterfaceQuota(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthSetInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthRemoveInterfaceAlert(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthSetGlobalAlert(long j) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthAddNaughtyApp(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthRemoveNaughtyApp(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthAddNiceApp(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void bandwidthRemoveNiceApp(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherStart(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherStop() throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public boolean tetherIsEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public void tetherInterfaceAdd(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherInterfaceRemove(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public java.lang.String[] tetherInterfaceList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void tetherDnsSet(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public java.lang.String[] tetherDnsList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void networkAddRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkAddLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public int networkGetDefault() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetd
        public void networkSetDefault(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkClearDefault() throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkSetPermissionForNetwork(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkSetPermissionForUser(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkClearPermissionForUser(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void trafficSetNetPermForUids(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkSetProtectAllow(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkSetProtectDeny(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public boolean networkCanProtect(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetd
        public void firewallSetFirewallType(int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void firewallSetInterfaceRule(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void firewallSetUidRule(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void firewallEnableChildChain(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public java.lang.String[] interfaceGetList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public android.net.InterfaceConfigurationParcel interfaceGetCfg(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void interfaceSetCfg(android.net.InterfaceConfigurationParcel interfaceConfigurationParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void interfaceSetIPv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void interfaceClearAddrs(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void interfaceSetEnableIPv6(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void interfaceSetMtu(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherAddForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherRemoveForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void setTcpRWmemorySize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void registerUnsolicitedEventListener(android.net.INetdUnsolicitedEventListener iNetdUnsolicitedEventListener) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void firewallAddUidInterfaceRules(java.lang.String str, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void firewallRemoveUidInterfaceRules(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void trafficSwapActiveStatsMap() throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public android.os.IBinder getOemNetd() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void tetherStartWithConfiguration(android.net.TetherConfigParcel tetherConfigParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public android.net.MarkMaskParcel getFwmarkForNetwork(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void networkAddRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkUpdateRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherOffloadRuleAdd(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void tetherOffloadRuleRemove(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public android.net.TetherStatsParcel[] tetherOffloadGetStats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void tetherOffloadSetInterfaceQuota(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public android.net.TetherStatsParcel tetherOffloadGetAndClearStats(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetd
        public void networkCreate(android.net.NativeNetworkConfig nativeNetworkConfig) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkAddUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void networkRemoveUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void ipSecMigrate(android.net.IpSecMigrateInfoParcel ipSecMigrateInfoParcel) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public void setNetworkAllowlist(android.net.netd.aidl.NativeUidRangeConfig[] nativeUidRangeConfigArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetd
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetd
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetd {
        public static final java.lang.String DESCRIPTOR = "android$net$INetd".replace('$', '.');
        static final int TRANSACTION_bandwidthAddNaughtyApp = 50;
        static final int TRANSACTION_bandwidthAddNiceApp = 52;
        static final int TRANSACTION_bandwidthEnableDataSaver = 3;
        static final int TRANSACTION_bandwidthRemoveInterfaceAlert = 48;
        static final int TRANSACTION_bandwidthRemoveInterfaceQuota = 46;
        static final int TRANSACTION_bandwidthRemoveNaughtyApp = 51;
        static final int TRANSACTION_bandwidthRemoveNiceApp = 53;
        static final int TRANSACTION_bandwidthSetGlobalAlert = 49;
        static final int TRANSACTION_bandwidthSetInterfaceAlert = 47;
        static final int TRANSACTION_bandwidthSetInterfaceQuota = 45;
        static final int TRANSACTION_clatdStart = 37;
        static final int TRANSACTION_clatdStop = 38;
        static final int TRANSACTION_firewallAddUidInterfaceRules = 91;
        static final int TRANSACTION_firewallEnableChildChain = 79;
        static final int TRANSACTION_firewallRemoveUidInterfaceRules = 92;
        static final int TRANSACTION_firewallReplaceUidChain = 2;
        static final int TRANSACTION_firewallSetFirewallType = 76;
        static final int TRANSACTION_firewallSetInterfaceRule = 77;
        static final int TRANSACTION_firewallSetUidRule = 78;
        static final int TRANSACTION_getFwmarkForNetwork = 96;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getOemNetd = 94;
        static final int TRANSACTION_getProcSysNet = 17;
        static final int TRANSACTION_idletimerAddInterface = 34;
        static final int TRANSACTION_idletimerRemoveInterface = 35;
        static final int TRANSACTION_interfaceAddAddress = 15;
        static final int TRANSACTION_interfaceClearAddrs = 84;
        static final int TRANSACTION_interfaceDelAddress = 16;
        static final int TRANSACTION_interfaceGetCfg = 81;
        static final int TRANSACTION_interfaceGetList = 80;
        static final int TRANSACTION_interfaceSetCfg = 82;
        static final int TRANSACTION_interfaceSetEnableIPv6 = 85;
        static final int TRANSACTION_interfaceSetIPv6PrivacyExtensions = 83;
        static final int TRANSACTION_interfaceSetMtu = 86;
        static final int TRANSACTION_ipSecAddSecurityAssociation = 21;
        static final int TRANSACTION_ipSecAddSecurityPolicy = 25;
        static final int TRANSACTION_ipSecAddTunnelInterface = 28;
        static final int TRANSACTION_ipSecAllocateSpi = 20;
        static final int TRANSACTION_ipSecApplyTransportModeTransform = 23;
        static final int TRANSACTION_ipSecDeleteSecurityAssociation = 22;
        static final int TRANSACTION_ipSecDeleteSecurityPolicy = 27;
        static final int TRANSACTION_ipSecMigrate = 108;
        static final int TRANSACTION_ipSecRemoveTransportModeTransform = 24;
        static final int TRANSACTION_ipSecRemoveTunnelInterface = 30;
        static final int TRANSACTION_ipSecSetEncapSocketOwner = 19;
        static final int TRANSACTION_ipSecUpdateSecurityPolicy = 26;
        static final int TRANSACTION_ipSecUpdateTunnelInterface = 29;
        static final int TRANSACTION_ipfwdAddInterfaceForward = 43;
        static final int TRANSACTION_ipfwdDisableForwarding = 42;
        static final int TRANSACTION_ipfwdEnableForwarding = 41;
        static final int TRANSACTION_ipfwdEnabled = 39;
        static final int TRANSACTION_ipfwdGetRequesterList = 40;
        static final int TRANSACTION_ipfwdRemoveInterfaceForward = 44;
        static final int TRANSACTION_isAlive = 1;
        static final int TRANSACTION_networkAddInterface = 7;
        static final int TRANSACTION_networkAddLegacyRoute = 64;
        static final int TRANSACTION_networkAddRoute = 62;
        static final int TRANSACTION_networkAddRouteParcel = 97;
        static final int TRANSACTION_networkAddUidRanges = 9;
        static final int TRANSACTION_networkAddUidRangesParcel = 106;
        static final int TRANSACTION_networkCanProtect = 75;
        static final int TRANSACTION_networkClearDefault = 68;
        static final int TRANSACTION_networkClearPermissionForUser = 71;
        static final int TRANSACTION_networkCreate = 105;
        static final int TRANSACTION_networkCreatePhysical = 4;
        static final int TRANSACTION_networkCreateVpn = 5;
        static final int TRANSACTION_networkDestroy = 6;
        static final int TRANSACTION_networkGetDefault = 66;
        static final int TRANSACTION_networkRejectNonSecureVpn = 11;
        static final int TRANSACTION_networkRemoveInterface = 8;
        static final int TRANSACTION_networkRemoveLegacyRoute = 65;
        static final int TRANSACTION_networkRemoveRoute = 63;
        static final int TRANSACTION_networkRemoveRouteParcel = 99;
        static final int TRANSACTION_networkRemoveUidRanges = 10;
        static final int TRANSACTION_networkRemoveUidRangesParcel = 107;
        static final int TRANSACTION_networkSetDefault = 67;
        static final int TRANSACTION_networkSetPermissionForNetwork = 69;
        static final int TRANSACTION_networkSetPermissionForUser = 70;
        static final int TRANSACTION_networkSetProtectAllow = 73;
        static final int TRANSACTION_networkSetProtectDeny = 74;
        static final int TRANSACTION_networkUpdateRouteParcel = 98;
        static final int TRANSACTION_registerUnsolicitedEventListener = 90;
        static final int TRANSACTION_setIPv6AddrGenMode = 33;
        static final int TRANSACTION_setNetworkAllowlist = 109;
        static final int TRANSACTION_setProcSysNet = 18;
        static final int TRANSACTION_setTcpRWmemorySize = 89;
        static final int TRANSACTION_socketDestroy = 12;
        static final int TRANSACTION_strictUidCleartextPenalty = 36;
        static final int TRANSACTION_tetherAddForward = 87;
        static final int TRANSACTION_tetherApplyDnsInterfaces = 13;
        static final int TRANSACTION_tetherDnsList = 61;
        static final int TRANSACTION_tetherDnsSet = 60;
        static final int TRANSACTION_tetherGetStats = 14;
        static final int TRANSACTION_tetherInterfaceAdd = 57;
        static final int TRANSACTION_tetherInterfaceList = 59;
        static final int TRANSACTION_tetherInterfaceRemove = 58;
        static final int TRANSACTION_tetherIsEnabled = 56;
        static final int TRANSACTION_tetherOffloadGetAndClearStats = 104;
        static final int TRANSACTION_tetherOffloadGetStats = 102;
        static final int TRANSACTION_tetherOffloadRuleAdd = 100;
        static final int TRANSACTION_tetherOffloadRuleRemove = 101;
        static final int TRANSACTION_tetherOffloadSetInterfaceQuota = 103;
        static final int TRANSACTION_tetherRemoveForward = 88;
        static final int TRANSACTION_tetherStart = 54;
        static final int TRANSACTION_tetherStartWithConfiguration = 95;
        static final int TRANSACTION_tetherStop = 55;
        static final int TRANSACTION_trafficSetNetPermForUids = 72;
        static final int TRANSACTION_trafficSwapActiveStatsMap = 93;
        static final int TRANSACTION_wakeupAddInterface = 31;
        static final int TRANSACTION_wakeupDelInterface = 32;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.INetd asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetd)) {
                return (android.net.INetd) queryLocalInterface;
            }
            return new android.net.INetd.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    boolean isAlive = isAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlive);
                    break;
                case 2:
                    boolean firewallReplaceUidChain = firewallReplaceUidChain(parcel.readString(), parcel.readBoolean(), parcel.createIntArray());
                    parcel2.writeNoException();
                    parcel2.writeBoolean(firewallReplaceUidChain);
                    break;
                case 3:
                    boolean bandwidthEnableDataSaver = bandwidthEnableDataSaver(parcel.readBoolean());
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bandwidthEnableDataSaver);
                    break;
                case 4:
                    networkCreatePhysical(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 5:
                    networkCreateVpn(parcel.readInt(), parcel.readBoolean());
                    parcel2.writeNoException();
                    break;
                case 6:
                    networkDestroy(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 7:
                    networkAddInterface(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 8:
                    networkRemoveInterface(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 9:
                    networkAddUidRanges(parcel.readInt(), (android.net.UidRangeParcel[]) parcel.createTypedArray(android.net.UidRangeParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 10:
                    networkRemoveUidRanges(parcel.readInt(), (android.net.UidRangeParcel[]) parcel.createTypedArray(android.net.UidRangeParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 11:
                    networkRejectNonSecureVpn(parcel.readBoolean(), (android.net.UidRangeParcel[]) parcel.createTypedArray(android.net.UidRangeParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 12:
                    socketDestroy((android.net.UidRangeParcel[]) parcel.createTypedArray(android.net.UidRangeParcel.CREATOR), parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 13:
                    boolean tetherApplyDnsInterfaces = tetherApplyDnsInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tetherApplyDnsInterfaces);
                    break;
                case 14:
                    android.net.TetherStatsParcel[] tetherGetStats = tetherGetStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(tetherGetStats, 1);
                    break;
                case 15:
                    interfaceAddAddress(parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 16:
                    interfaceDelAddress(parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 17:
                    java.lang.String procSysNet = getProcSysNet(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(procSysNet);
                    break;
                case 18:
                    setProcSysNet(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 19:
                    ipSecSetEncapSocketOwner((android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 20:
                    int ipSecAllocateSpi = ipSecAllocateSpi(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(ipSecAllocateSpi);
                    break;
                case 21:
                    ipSecAddSecurityAssociation(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.createByteArray(), parcel.readInt(), parcel.readString(), parcel.createByteArray(), parcel.readInt(), parcel.readString(), parcel.createByteArray(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 22:
                    ipSecDeleteSecurityAssociation(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 23:
                    ipSecApplyTransportModeTransform((android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 24:
                    ipSecRemoveTransportModeTransform((android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 25:
                    ipSecAddSecurityPolicy(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 26:
                    ipSecUpdateSecurityPolicy(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 27:
                    ipSecDeleteSecurityPolicy(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 28:
                    ipSecAddTunnelInterface(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 29:
                    ipSecUpdateTunnelInterface(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 30:
                    ipSecRemoveTunnelInterface(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 31:
                    wakeupAddInterface(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 32:
                    wakeupDelInterface(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 33:
                    setIPv6AddrGenMode(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 34:
                    idletimerAddInterface(parcel.readString(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 35:
                    idletimerRemoveInterface(parcel.readString(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 36:
                    strictUidCleartextPenalty(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 37:
                    java.lang.String clatdStart = clatdStart(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(clatdStart);
                    break;
                case 38:
                    clatdStop(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 39:
                    boolean ipfwdEnabled = ipfwdEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ipfwdEnabled);
                    break;
                case 40:
                    java.lang.String[] ipfwdGetRequesterList = ipfwdGetRequesterList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(ipfwdGetRequesterList);
                    break;
                case 41:
                    ipfwdEnableForwarding(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 42:
                    ipfwdDisableForwarding(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 43:
                    ipfwdAddInterfaceForward(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 44:
                    ipfwdRemoveInterfaceForward(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 45:
                    bandwidthSetInterfaceQuota(parcel.readString(), parcel.readLong());
                    parcel2.writeNoException();
                    break;
                case 46:
                    bandwidthRemoveInterfaceQuota(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 47:
                    bandwidthSetInterfaceAlert(parcel.readString(), parcel.readLong());
                    parcel2.writeNoException();
                    break;
                case 48:
                    bandwidthRemoveInterfaceAlert(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 49:
                    bandwidthSetGlobalAlert(parcel.readLong());
                    parcel2.writeNoException();
                    break;
                case 50:
                    bandwidthAddNaughtyApp(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 51:
                    bandwidthRemoveNaughtyApp(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 52:
                    bandwidthAddNiceApp(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 53:
                    bandwidthRemoveNiceApp(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 54:
                    tetherStart(parcel.createStringArray());
                    parcel2.writeNoException();
                    break;
                case 55:
                    tetherStop();
                    parcel2.writeNoException();
                    break;
                case 56:
                    boolean tetherIsEnabled = tetherIsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tetherIsEnabled);
                    break;
                case 57:
                    tetherInterfaceAdd(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 58:
                    tetherInterfaceRemove(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 59:
                    java.lang.String[] tetherInterfaceList = tetherInterfaceList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(tetherInterfaceList);
                    break;
                case 60:
                    tetherDnsSet(parcel.readInt(), parcel.createStringArray());
                    parcel2.writeNoException();
                    break;
                case 61:
                    java.lang.String[] tetherDnsList = tetherDnsList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(tetherDnsList);
                    break;
                case 62:
                    networkAddRoute(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 63:
                    networkRemoveRoute(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 64:
                    networkAddLegacyRoute(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 65:
                    networkRemoveLegacyRoute(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 66:
                    int networkGetDefault = networkGetDefault();
                    parcel2.writeNoException();
                    parcel2.writeInt(networkGetDefault);
                    break;
                case 67:
                    networkSetDefault(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 68:
                    networkClearDefault();
                    parcel2.writeNoException();
                    break;
                case 69:
                    networkSetPermissionForNetwork(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 70:
                    networkSetPermissionForUser(parcel.readInt(), parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 71:
                    networkClearPermissionForUser(parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 72:
                    trafficSetNetPermForUids(parcel.readInt(), parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 73:
                    networkSetProtectAllow(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 74:
                    networkSetProtectDeny(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 75:
                    boolean networkCanProtect = networkCanProtect(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeBoolean(networkCanProtect);
                    break;
                case 76:
                    firewallSetFirewallType(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 77:
                    firewallSetInterfaceRule(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 78:
                    firewallSetUidRule(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 79:
                    firewallEnableChildChain(parcel.readInt(), parcel.readBoolean());
                    parcel2.writeNoException();
                    break;
                case 80:
                    java.lang.String[] interfaceGetList = interfaceGetList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(interfaceGetList);
                    break;
                case 81:
                    android.net.InterfaceConfigurationParcel interfaceGetCfg = interfaceGetCfg(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(interfaceGetCfg, 1);
                    break;
                case 82:
                    interfaceSetCfg((android.net.InterfaceConfigurationParcel) parcel.readTypedObject(android.net.InterfaceConfigurationParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 83:
                    interfaceSetIPv6PrivacyExtensions(parcel.readString(), parcel.readBoolean());
                    parcel2.writeNoException();
                    break;
                case 84:
                    interfaceClearAddrs(parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 85:
                    interfaceSetEnableIPv6(parcel.readString(), parcel.readBoolean());
                    parcel2.writeNoException();
                    break;
                case 86:
                    interfaceSetMtu(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 87:
                    tetherAddForward(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 88:
                    tetherRemoveForward(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 89:
                    setTcpRWmemorySize(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    break;
                case 90:
                    registerUnsolicitedEventListener(android.net.INetdUnsolicitedEventListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    break;
                case 91:
                    firewallAddUidInterfaceRules(parcel.readString(), parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 92:
                    firewallRemoveUidInterfaceRules(parcel.createIntArray());
                    parcel2.writeNoException();
                    break;
                case 93:
                    trafficSwapActiveStatsMap();
                    parcel2.writeNoException();
                    break;
                case 94:
                    android.os.IBinder oemNetd = getOemNetd();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(oemNetd);
                    break;
                case 95:
                    tetherStartWithConfiguration((android.net.TetherConfigParcel) parcel.readTypedObject(android.net.TetherConfigParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 96:
                    android.net.MarkMaskParcel fwmarkForNetwork = getFwmarkForNetwork(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fwmarkForNetwork, 1);
                    break;
                case 97:
                    networkAddRouteParcel(parcel.readInt(), (android.net.RouteInfoParcel) parcel.readTypedObject(android.net.RouteInfoParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 98:
                    networkUpdateRouteParcel(parcel.readInt(), (android.net.RouteInfoParcel) parcel.readTypedObject(android.net.RouteInfoParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 99:
                    networkRemoveRouteParcel(parcel.readInt(), (android.net.RouteInfoParcel) parcel.readTypedObject(android.net.RouteInfoParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 100:
                    tetherOffloadRuleAdd((android.net.TetherOffloadRuleParcel) parcel.readTypedObject(android.net.TetherOffloadRuleParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 101:
                    tetherOffloadRuleRemove((android.net.TetherOffloadRuleParcel) parcel.readTypedObject(android.net.TetherOffloadRuleParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 102:
                    android.net.TetherStatsParcel[] tetherOffloadGetStats = tetherOffloadGetStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(tetherOffloadGetStats, 1);
                    break;
                case 103:
                    tetherOffloadSetInterfaceQuota(parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    break;
                case 104:
                    android.net.TetherStatsParcel tetherOffloadGetAndClearStats = tetherOffloadGetAndClearStats(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tetherOffloadGetAndClearStats, 1);
                    break;
                case 105:
                    networkCreate((android.net.NativeNetworkConfig) parcel.readTypedObject(android.net.NativeNetworkConfig.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 106:
                    networkAddUidRangesParcel((android.net.netd.aidl.NativeUidRangeConfig) parcel.readTypedObject(android.net.netd.aidl.NativeUidRangeConfig.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 107:
                    networkRemoveUidRangesParcel((android.net.netd.aidl.NativeUidRangeConfig) parcel.readTypedObject(android.net.netd.aidl.NativeUidRangeConfig.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 108:
                    ipSecMigrate((android.net.IpSecMigrateInfoParcel) parcel.readTypedObject(android.net.IpSecMigrateInfoParcel.CREATOR));
                    parcel2.writeNoException();
                    break;
                case 109:
                    setNetworkAllowlist((android.net.netd.aidl.NativeUidRangeConfig[]) parcel.createTypedArray(android.net.netd.aidl.NativeUidRangeConfig.CREATOR));
                    parcel2.writeNoException();
                    break;
            }
            return true;
        }

        private static class Proxy implements android.net.INetd {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetd.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetd
            public boolean isAlive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isAlive is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean firewallReplaceUidChain(java.lang.String str, boolean z, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallReplaceUidChain is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean bandwidthEnableDataSaver(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthEnableDataSaver is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkCreatePhysical(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkCreatePhysical is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkCreateVpn(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkCreateVpn is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkDestroy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkDestroy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddInterface(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveInterface(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddUidRanges is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveUidRanges(int i, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveUidRanges is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRejectNonSecureVpn(boolean z, android.net.UidRangeParcel[] uidRangeParcelArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRejectNonSecureVpn is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void socketDestroy(android.net.UidRangeParcel[] uidRangeParcelArr, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method socketDestroy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean tetherApplyDnsInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherApplyDnsInterfaces is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.net.TetherStatsParcel[] tetherGetStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherGetStats is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.net.TetherStatsParcel[]) obtain2.createTypedArray(android.net.TetherStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceAddAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceAddAddress is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceDelAddress(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceDelAddress is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String getProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getProcSysNet is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void setProcSysNet(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setProcSysNet is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecSetEncapSocketOwner(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecSetEncapSocketOwner is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public int ipSecAllocateSpi(int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecAllocateSpi is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecAddSecurityAssociation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, java.lang.String str3, byte[] bArr, int i7, java.lang.String str4, byte[] bArr2, int i8, java.lang.String str5, byte[] bArr3, int i9, int i10, int i11, int i12, int i13) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i7);
                    obtain.writeString(str4);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i8);
                    obtain.writeString(str5);
                    obtain.writeByteArray(bArr3);
                    obtain.writeInt(i9);
                    obtain.writeInt(i10);
                    obtain.writeInt(i11);
                    obtain.writeInt(i12);
                    obtain.writeInt(i13);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecAddSecurityAssociation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecDeleteSecurityAssociation(int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecDeleteSecurityAssociation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecApplyTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecApplyTransportModeTransform is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecRemoveTransportModeTransform(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecRemoveTransportModeTransform is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecAddSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecAddSecurityPolicy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecUpdateSecurityPolicy(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, int i6, int i7) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    if (!this.mRemote.transact(26, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecUpdateSecurityPolicy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecDeleteSecurityPolicy(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    if (!this.mRemote.transact(27, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecDeleteSecurityPolicy is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecAddTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(28, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecAddTunnelInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecUpdateTunnelInterface(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(29, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecUpdateTunnelInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecRemoveTunnelInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(30, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecRemoveTunnelInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void wakeupAddInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(31, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method wakeupAddInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void wakeupDelInterface(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(32, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method wakeupDelInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(33, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setIPv6AddrGenMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void idletimerAddInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(34, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method idletimerAddInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void idletimerRemoveInterface(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(35, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method idletimerRemoveInterface is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void strictUidCleartextPenalty(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(36, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method strictUidCleartextPenalty is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String clatdStart(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(37, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method clatdStart is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void clatdStop(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(38, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method clatdStop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean ipfwdEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(39, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdEnabled is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String[] ipfwdGetRequesterList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(40, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdGetRequesterList is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipfwdEnableForwarding(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(41, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdEnableForwarding is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipfwdDisableForwarding(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(42, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdDisableForwarding is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipfwdAddInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(43, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdAddInterfaceForward is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipfwdRemoveInterfaceForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(44, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipfwdRemoveInterfaceForward is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthSetInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(45, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthSetInterfaceQuota is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthRemoveInterfaceQuota(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(46, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthRemoveInterfaceQuota is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthSetInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(47, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthSetInterfaceAlert is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthRemoveInterfaceAlert(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(48, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthRemoveInterfaceAlert is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthSetGlobalAlert(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(49, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthSetGlobalAlert is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthAddNaughtyApp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(50, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthAddNaughtyApp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthRemoveNaughtyApp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(51, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthRemoveNaughtyApp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthAddNiceApp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(52, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthAddNiceApp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void bandwidthRemoveNiceApp(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(53, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bandwidthRemoveNiceApp is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherStart(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(54, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherStart is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherStop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(55, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherStop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean tetherIsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(56, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherIsEnabled is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherInterfaceAdd(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(57, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherInterfaceAdd is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherInterfaceRemove(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(58, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherInterfaceRemove is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String[] tetherInterfaceList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(59, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherInterfaceList is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherDnsSet(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(60, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherDnsSet is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String[] tetherDnsList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(61, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherDnsList is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(62, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddRoute is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(63, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveRoute is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(64, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddLegacyRoute is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveLegacyRoute(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(65, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveLegacyRoute is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public int networkGetDefault() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(66, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkGetDefault is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkSetDefault(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(67, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkSetDefault is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkClearDefault() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(68, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkClearDefault is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkSetPermissionForNetwork(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(69, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkSetPermissionForNetwork is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkSetPermissionForUser(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(70, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkSetPermissionForUser is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkClearPermissionForUser(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(71, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkClearPermissionForUser is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void trafficSetNetPermForUids(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(72, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method trafficSetNetPermForUids is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkSetProtectAllow(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(73, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkSetProtectAllow is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkSetProtectDeny(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(74, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkSetProtectDeny is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public boolean networkCanProtect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(75, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkCanProtect is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallSetFirewallType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(76, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallSetFirewallType is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallSetInterfaceRule(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(77, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallSetInterfaceRule is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallSetUidRule(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(78, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallSetUidRule is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallEnableChildChain(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(79, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallEnableChildChain is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public java.lang.String[] interfaceGetList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(80, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceGetList is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.net.InterfaceConfigurationParcel interfaceGetCfg(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(81, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceGetCfg is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.net.InterfaceConfigurationParcel) obtain2.readTypedObject(android.net.InterfaceConfigurationParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceSetCfg(android.net.InterfaceConfigurationParcel interfaceConfigurationParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(interfaceConfigurationParcel, 0);
                    if (!this.mRemote.transact(82, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceSetCfg is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceSetIPv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(83, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceSetIPv6PrivacyExtensions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceClearAddrs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(84, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceClearAddrs is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceSetEnableIPv6(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(85, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceSetEnableIPv6 is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void interfaceSetMtu(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(86, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method interfaceSetMtu is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherAddForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(87, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherAddForward is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherRemoveForward(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(88, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherRemoveForward is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void setTcpRWmemorySize(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(89, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setTcpRWmemorySize is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void registerUnsolicitedEventListener(android.net.INetdUnsolicitedEventListener iNetdUnsolicitedEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetdUnsolicitedEventListener);
                    if (!this.mRemote.transact(90, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerUnsolicitedEventListener is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallAddUidInterfaceRules(java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(91, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallAddUidInterfaceRules is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void firewallRemoveUidInterfaceRules(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(92, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method firewallRemoveUidInterfaceRules is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void trafficSwapActiveStatsMap() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(93, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method trafficSwapActiveStatsMap is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.os.IBinder getOemNetd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(94, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getOemNetd is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherStartWithConfiguration(android.net.TetherConfigParcel tetherConfigParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tetherConfigParcel, 0);
                    if (!this.mRemote.transact(95, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherStartWithConfiguration is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.net.MarkMaskParcel getFwmarkForNetwork(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(96, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFwmarkForNetwork is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.net.MarkMaskParcel) obtain2.readTypedObject(android.net.MarkMaskParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routeInfoParcel, 0);
                    if (!this.mRemote.transact(97, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddRouteParcel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkUpdateRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routeInfoParcel, 0);
                    if (!this.mRemote.transact(98, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkUpdateRouteParcel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveRouteParcel(int i, android.net.RouteInfoParcel routeInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routeInfoParcel, 0);
                    if (!this.mRemote.transact(99, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveRouteParcel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherOffloadRuleAdd(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tetherOffloadRuleParcel, 0);
                    if (!this.mRemote.transact(100, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherOffloadRuleAdd is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherOffloadRuleRemove(android.net.TetherOffloadRuleParcel tetherOffloadRuleParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tetherOffloadRuleParcel, 0);
                    if (!this.mRemote.transact(101, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherOffloadRuleRemove is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.net.TetherStatsParcel[] tetherOffloadGetStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(102, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherOffloadGetStats is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.net.TetherStatsParcel[]) obtain2.createTypedArray(android.net.TetherStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void tetherOffloadSetInterfaceQuota(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(103, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherOffloadSetInterfaceQuota is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public android.net.TetherStatsParcel tetherOffloadGetAndClearStats(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(104, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tetherOffloadGetAndClearStats is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.net.TetherStatsParcel) obtain2.readTypedObject(android.net.TetherStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkCreate(android.net.NativeNetworkConfig nativeNetworkConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nativeNetworkConfig, 0);
                    if (!this.mRemote.transact(105, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkCreate is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkAddUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nativeUidRangeConfig, 0);
                    if (!this.mRemote.transact(106, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkAddUidRangesParcel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void networkRemoveUidRangesParcel(android.net.netd.aidl.NativeUidRangeConfig nativeUidRangeConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nativeUidRangeConfig, 0);
                    if (!this.mRemote.transact(107, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method networkRemoveUidRangesParcel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void ipSecMigrate(android.net.IpSecMigrateInfoParcel ipSecMigrateInfoParcel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(ipSecMigrateInfoParcel, 0);
                    if (!this.mRemote.transact(108, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ipSecMigrate is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public void setNetworkAllowlist(android.net.netd.aidl.NativeUidRangeConfig[] nativeUidRangeConfigArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(nativeUidRangeConfigArr, 0);
                    if (!this.mRemote.transact(109, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setNetworkAllowlist is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetd
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetd
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.INetd.Stub.DESCRIPTOR);
                            this.mRemote.transact(android.net.INetd.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
