package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class TunnelModeChildSessionParamsUtils {
    private static final java.lang.String CONFIG_REQUESTS_KEY = "CONFIG_REQUESTS_KEY";
    private static final java.lang.String HARD_LIFETIME_SEC_KEY = "HARD_LIFETIME_SEC_KEY";
    private static final java.lang.String INBOUND_TS_KEY = "INBOUND_TS_KEY";
    private static final java.lang.String OUTBOUND_TS_KEY = "OUTBOUND_TS_KEY";
    private static final java.lang.String SA_PROPOSALS_KEY = "SA_PROPOSALS_KEY";
    private static final java.lang.String SOFT_LIFETIME_SEC_KEY = "SOFT_LIFETIME_SEC_KEY";
    private static final java.lang.String TAG = android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.class.getSimpleName();

    /* JADX INFO: Access modifiers changed from: private */
    static class ConfigRequest {
        private static final java.lang.String IP6_PREFIX_LEN = "ip6PrefixLen";
        private static final int PREFIX_LEN_UNUSED = -1;
        private static final int TYPE_IPV4_ADDRESS = 1;
        private static final int TYPE_IPV4_DHCP = 5;
        private static final int TYPE_IPV4_DNS = 3;
        private static final int TYPE_IPV4_NETMASK = 6;
        private static final int TYPE_IPV6_ADDRESS = 2;
        private static final int TYPE_IPV6_DNS = 4;
        private static final java.lang.String TYPE_KEY = "type";
        private static final java.lang.String VALUE_KEY = "address";
        public final java.net.InetAddress address;
        public final int ip6PrefixLen;
        public final int type;

        ConfigRequest(android.net.ipsec.ike.TunnelModeChildSessionParams.TunnelModeChildConfigRequest tunnelModeChildConfigRequest) {
            int prefixLength;
            if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv4Address) {
                this.type = 1;
                this.address = ((android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv4Address) tunnelModeChildConfigRequest).getAddress();
            } else if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv6Address) {
                this.type = 2;
                android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv6Address configRequestIpv6Address = (android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv6Address) tunnelModeChildConfigRequest;
                this.address = configRequestIpv6Address.getAddress();
                if (this.address != null) {
                    prefixLength = configRequestIpv6Address.getPrefixLength();
                    this.ip6PrefixLen = prefixLength;
                }
            } else if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv4DnsServer) {
                this.type = 3;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv6DnsServer) {
                this.type = 4;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv4DhcpServer) {
                this.type = 5;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof android.net.ipsec.ike.TunnelModeChildSessionParams.ConfigRequestIpv4Netmask) {
                this.type = 6;
                this.address = null;
            } else {
                throw new java.lang.IllegalStateException("Unknown TunnelModeChildConfigRequest");
            }
            prefixLength = -1;
            this.ip6PrefixLen = prefixLength;
        }

        ConfigRequest(android.os.PersistableBundle persistableBundle) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.type = persistableBundle.getInt("type");
            this.ip6PrefixLen = persistableBundle.getInt(IP6_PREFIX_LEN);
            java.lang.String string = persistableBundle.getString("address");
            if (string == null) {
                this.address = null;
            } else {
                this.address = android.net.InetAddresses.parseNumericAddress(string);
            }
        }

        public android.os.PersistableBundle toPersistableBundle() {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putInt("type", this.type);
            persistableBundle.putInt(IP6_PREFIX_LEN, this.ip6PrefixLen);
            if (this.address != null) {
                persistableBundle.putString("address", this.address.getHostAddress());
            }
            return persistableBundle;
        }
    }

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.TunnelModeChildSessionParams tunnelModeChildSessionParams) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putPersistableBundle(SA_PROPOSALS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getSaProposals(), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda1
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return android.net.vcn.persistablebundleutils.ChildSaProposalUtils.toPersistableBundle((android.net.ipsec.ike.ChildSaProposal) obj);
            }
        }));
        persistableBundle.putPersistableBundle(INBOUND_TS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getInboundTrafficSelectors(), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return android.net.vcn.persistablebundleutils.IkeTrafficSelectorUtils.toPersistableBundle((android.net.ipsec.ike.IkeTrafficSelector) obj);
            }
        }));
        persistableBundle.putPersistableBundle(OUTBOUND_TS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getOutboundTrafficSelectors(), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return android.net.vcn.persistablebundleutils.IkeTrafficSelectorUtils.toPersistableBundle((android.net.ipsec.ike.IkeTrafficSelector) obj);
            }
        }));
        persistableBundle.putInt(HARD_LIFETIME_SEC_KEY, tunnelModeChildSessionParams.getHardLifetimeSeconds());
        persistableBundle.putInt(SOFT_LIFETIME_SEC_KEY, tunnelModeChildSessionParams.getSoftLifetimeSeconds());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.net.ipsec.ike.TunnelModeChildSessionParams.TunnelModeChildConfigRequest> it = tunnelModeChildSessionParams.getConfigurationRequests().iterator();
        while (it.hasNext()) {
            arrayList.add(new android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.ConfigRequest(it.next()));
        }
        persistableBundle.putPersistableBundle(CONFIG_REQUESTS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(arrayList, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda3
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return ((android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.ConfigRequest) obj).toPersistableBundle();
            }
        }));
        return persistableBundle;
    }

    private static java.util.List<android.net.ipsec.ike.IkeTrafficSelector> getTsFromPersistableBundle(android.os.PersistableBundle persistableBundle, java.lang.String str) {
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(str);
        java.util.Objects.requireNonNull(persistableBundle2, "Value for key " + str + " was null");
        return com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda0
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle3) {
                return android.net.vcn.persistablebundleutils.IkeTrafficSelectorUtils.fromPersistableBundle(persistableBundle3);
            }
        });
    }

    public static android.net.ipsec.ike.TunnelModeChildSessionParams fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        android.net.ipsec.ike.TunnelModeChildSessionParams.Builder builder = new android.net.ipsec.ike.TunnelModeChildSessionParams.Builder();
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(SA_PROPOSALS_KEY);
        java.util.Objects.requireNonNull(persistableBundle2, "SA proposal was null");
        java.util.Iterator it = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda4
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle3) {
                return android.net.vcn.persistablebundleutils.ChildSaProposalUtils.fromPersistableBundle(persistableBundle3);
            }
        }).iterator();
        while (it.hasNext()) {
            builder.addSaProposal((android.net.ipsec.ike.ChildSaProposal) it.next());
        }
        java.util.Iterator<android.net.ipsec.ike.IkeTrafficSelector> it2 = getTsFromPersistableBundle(persistableBundle, INBOUND_TS_KEY).iterator();
        while (it2.hasNext()) {
            builder.addInboundTrafficSelectors(it2.next());
        }
        java.util.Iterator<android.net.ipsec.ike.IkeTrafficSelector> it3 = getTsFromPersistableBundle(persistableBundle, OUTBOUND_TS_KEY).iterator();
        while (it3.hasNext()) {
            builder.addOutboundTrafficSelectors(it3.next());
        }
        builder.setLifetimeSeconds(persistableBundle.getInt(HARD_LIFETIME_SEC_KEY), persistableBundle.getInt(SOFT_LIFETIME_SEC_KEY));
        android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CONFIG_REQUESTS_KEY);
        java.util.Objects.requireNonNull(persistableBundle3, "Config request list was null");
        boolean z = false;
        boolean z2 = false;
        for (android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.ConfigRequest configRequest : com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle3, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda5
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle4) {
                return new android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.ConfigRequest(persistableBundle4);
            }
        })) {
            switch (configRequest.type) {
                case 1:
                    if (configRequest.address == null) {
                        builder.addInternalAddressRequest(android.system.OsConstants.AF_INET);
                    } else {
                        builder.addInternalAddressRequest((java.net.Inet4Address) configRequest.address);
                    }
                    z = true;
                    break;
                case 2:
                    if (configRequest.address == null) {
                        builder.addInternalAddressRequest(android.system.OsConstants.AF_INET6);
                        break;
                    } else {
                        builder.addInternalAddressRequest((java.net.Inet6Address) configRequest.address, configRequest.ip6PrefixLen);
                        break;
                    }
                case 3:
                    if (configRequest.address != null) {
                        android.util.Log.w(TAG, "Requesting a specific IPv4 DNS server is unsupported");
                    }
                    builder.addInternalDnsServerRequest(android.system.OsConstants.AF_INET);
                    break;
                case 4:
                    if (configRequest.address != null) {
                        android.util.Log.w(TAG, "Requesting a specific IPv6 DNS server is unsupported");
                    }
                    builder.addInternalDnsServerRequest(android.system.OsConstants.AF_INET6);
                    break;
                case 5:
                    if (configRequest.address != null) {
                        android.util.Log.w(TAG, "Requesting a specific IPv4 DHCP server is unsupported");
                    }
                    builder.addInternalDhcpServerRequest(android.system.OsConstants.AF_INET);
                    break;
                case 6:
                    z2 = true;
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unrecognized config request type: " + configRequest.type);
            }
        }
        if (z != z2) {
            android.util.Log.w(TAG, java.lang.String.format("Expect IPv4 address request and IPv4 netmask request either both exist or both absent, but found hasIpv4AddressReq exists? %b, hasIpv4AddressReq exists? %b, ", java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)));
        }
        return builder.build();
    }
}
