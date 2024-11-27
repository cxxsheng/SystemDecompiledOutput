package com.android.server.connectivity;

/* loaded from: classes.dex */
public final class IpConnectivityEventBuilder {
    private static final int[] IFNAME_LINKLAYERS;
    private static final java.lang.String[] IFNAME_PREFIXES;
    private static final int KNOWN_PREFIX = 7;
    private static final android.util.SparseIntArray TRANSPORT_LINKLAYER_MAP = new android.util.SparseIntArray();

    private IpConnectivityEventBuilder() {
    }

    public static byte[] serialize(int i, java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> list) throws java.io.IOException {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog ipConnectivityLog = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog();
        ipConnectivityLog.events = (com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[]) list.toArray(new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent[list.size()]);
        ipConnectivityLog.droppedEvents = i;
        if (ipConnectivityLog.events.length > 0 || i > 0) {
            ipConnectivityLog.version = 2;
        }
        return com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityLog.toByteArray(ipConnectivityLog);
    }

    public static java.util.List<com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent> toProto(java.util.List<android.net.ConnectivityMetricsEvent> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.net.ConnectivityMetricsEvent> it = list.iterator();
        while (it.hasNext()) {
            com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent proto = toProto(it.next());
            if (proto != null) {
                arrayList.add(proto);
            }
        }
        return arrayList;
    }

    public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent toProto(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent = buildEvent(connectivityMetricsEvent.netId, connectivityMetricsEvent.transports, connectivityMetricsEvent.ifname);
        buildEvent.timeMs = connectivityMetricsEvent.timestamp;
        if (!setEvent(buildEvent, connectivityMetricsEvent.data)) {
            return null;
        }
        return buildEvent;
    }

    public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent toProto(android.net.metrics.ConnectStats connectStats) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics connectStatistics = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ConnectStatistics();
        connectStatistics.connectCount = connectStats.connectCount;
        connectStatistics.connectBlockingCount = connectStats.connectBlockingCount;
        connectStatistics.ipv6AddrCount = connectStats.ipv6ConnectCount;
        connectStatistics.latenciesMs = connectStats.latencies.toArray();
        connectStatistics.errnosCounters = toPairArray(connectStats.errnos);
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent = buildEvent(connectStats.netId, connectStats.transports, null);
        buildEvent.setConnectStatistics(connectStatistics);
        return buildEvent;
    }

    public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent toProto(android.net.metrics.DnsEvent dnsEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch dNSLookupBatch = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DNSLookupBatch();
        dnsEvent.resize(dnsEvent.eventCount);
        dNSLookupBatch.eventTypes = bytesToInts(dnsEvent.eventTypes);
        dNSLookupBatch.returnCodes = bytesToInts(dnsEvent.returnCodes);
        dNSLookupBatch.latenciesMs = dnsEvent.latenciesMs;
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent = buildEvent(dnsEvent.netId, dnsEvent.transports, null);
        buildEvent.setDnsLookupBatch(dNSLookupBatch);
        return buildEvent;
    }

    public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent toProto(android.net.metrics.WakeupStats wakeupStats) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats wakeupStats2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.WakeupStats();
        wakeupStats.updateDuration();
        wakeupStats2.durationSec = wakeupStats.durationSec;
        wakeupStats2.totalWakeups = wakeupStats.totalWakeups;
        wakeupStats2.rootWakeups = wakeupStats.rootWakeups;
        wakeupStats2.systemWakeups = wakeupStats.systemWakeups;
        wakeupStats2.nonApplicationWakeups = wakeupStats.nonApplicationWakeups;
        wakeupStats2.applicationWakeups = wakeupStats.applicationWakeups;
        wakeupStats2.noUidWakeups = wakeupStats.noUidWakeups;
        wakeupStats2.l2UnicastCount = wakeupStats.l2UnicastCount;
        wakeupStats2.l2MulticastCount = wakeupStats.l2MulticastCount;
        wakeupStats2.l2BroadcastCount = wakeupStats.l2BroadcastCount;
        wakeupStats2.ethertypeCounts = toPairArray(wakeupStats.ethertypes);
        wakeupStats2.ipNextHeaderCounts = toPairArray(wakeupStats.ipNextHeaders);
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent = buildEvent(0, 0L, wakeupStats.iface);
        buildEvent.setWakeupStats(wakeupStats2);
        return buildEvent;
    }

    public static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent toProto(android.net.metrics.DefaultNetworkEvent defaultNetworkEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent defaultNetworkEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DefaultNetworkEvent();
        defaultNetworkEvent2.finalScore = defaultNetworkEvent.finalScore;
        defaultNetworkEvent2.initialScore = defaultNetworkEvent.initialScore;
        defaultNetworkEvent2.ipSupport = ipSupportOf(defaultNetworkEvent);
        defaultNetworkEvent2.defaultNetworkDurationMs = defaultNetworkEvent.durationMs;
        defaultNetworkEvent2.validationDurationMs = defaultNetworkEvent.validatedMs;
        defaultNetworkEvent2.previousDefaultNetworkLinkLayer = transportsToLinkLayer(defaultNetworkEvent.previousTransports);
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent = buildEvent(defaultNetworkEvent.netId, defaultNetworkEvent.transports, null);
        if (defaultNetworkEvent.transports == 0) {
            buildEvent.linkLayer = 5;
        }
        buildEvent.setDefaultNetworkEvent(defaultNetworkEvent2);
        return buildEvent;
    }

    private static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent buildEvent(int i, long j, java.lang.String str) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent();
        ipConnectivityEvent.networkId = i;
        ipConnectivityEvent.transports = j;
        if (str != null) {
            ipConnectivityEvent.ifName = str;
        }
        inferLinkLayer(ipConnectivityEvent);
        return ipConnectivityEvent;
    }

    private static boolean setEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.os.Parcelable parcelable) {
        if (parcelable instanceof android.net.metrics.DhcpErrorEvent) {
            setDhcpErrorEvent(ipConnectivityEvent, (android.net.metrics.DhcpErrorEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.DhcpClientEvent) {
            setDhcpClientEvent(ipConnectivityEvent, (android.net.metrics.DhcpClientEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.IpManagerEvent) {
            setIpManagerEvent(ipConnectivityEvent, (android.net.metrics.IpManagerEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.IpReachabilityEvent) {
            setIpReachabilityEvent(ipConnectivityEvent, (android.net.metrics.IpReachabilityEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.NetworkEvent) {
            setNetworkEvent(ipConnectivityEvent, (android.net.metrics.NetworkEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.ValidationProbeEvent) {
            setValidationProbeEvent(ipConnectivityEvent, (android.net.metrics.ValidationProbeEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.ApfProgramEvent) {
            setApfProgramEvent(ipConnectivityEvent, (android.net.metrics.ApfProgramEvent) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.ApfStats) {
            setApfStats(ipConnectivityEvent, (android.net.metrics.ApfStats) parcelable);
            return true;
        }
        if (parcelable instanceof android.net.metrics.RaEvent) {
            setRaEvent(ipConnectivityEvent, (android.net.metrics.RaEvent) parcelable);
            return true;
        }
        return false;
    }

    private static void setDhcpErrorEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.DhcpErrorEvent dhcpErrorEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent dHCPEvent = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent();
        dHCPEvent.setErrorCode(dhcpErrorEvent.errorCode);
        ipConnectivityEvent.setDhcpEvent(dHCPEvent);
    }

    private static void setDhcpClientEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.DhcpClientEvent dhcpClientEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent dHCPEvent = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.DHCPEvent();
        dHCPEvent.setStateTransition(dhcpClientEvent.msg);
        dHCPEvent.durationMs = dhcpClientEvent.durationMs;
        ipConnectivityEvent.setDhcpEvent(dHCPEvent);
    }

    private static void setIpManagerEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.IpManagerEvent ipManagerEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent ipProvisioningEvent = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpProvisioningEvent();
        ipProvisioningEvent.eventType = ipManagerEvent.eventType;
        ipProvisioningEvent.latencyMs = (int) ipManagerEvent.durationMs;
        ipConnectivityEvent.setIpProvisioningEvent(ipProvisioningEvent);
    }

    private static void setIpReachabilityEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.IpReachabilityEvent ipReachabilityEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent ipReachabilityEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpReachabilityEvent();
        ipReachabilityEvent2.eventType = ipReachabilityEvent.eventType;
        ipConnectivityEvent.setIpReachabilityEvent(ipReachabilityEvent2);
    }

    private static void setNetworkEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.NetworkEvent networkEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent networkEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.NetworkEvent();
        networkEvent2.eventType = networkEvent.eventType;
        networkEvent2.latencyMs = (int) networkEvent.durationMs;
        ipConnectivityEvent.setNetworkEvent(networkEvent2);
    }

    private static void setValidationProbeEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.ValidationProbeEvent validationProbeEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent validationProbeEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ValidationProbeEvent();
        validationProbeEvent2.latencyMs = (int) validationProbeEvent.durationMs;
        validationProbeEvent2.probeType = validationProbeEvent.probeType;
        validationProbeEvent2.probeResult = validationProbeEvent.returnCode;
        ipConnectivityEvent.setValidationProbeEvent(validationProbeEvent2);
    }

    private static void setApfProgramEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.ApfProgramEvent apfProgramEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent apfProgramEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfProgramEvent();
        apfProgramEvent2.lifetime = apfProgramEvent.lifetime;
        apfProgramEvent2.effectiveLifetime = apfProgramEvent.actualLifetime;
        apfProgramEvent2.filteredRas = apfProgramEvent.filteredRas;
        apfProgramEvent2.currentRas = apfProgramEvent.currentRas;
        apfProgramEvent2.programLength = apfProgramEvent.programLength;
        if (isBitSet(apfProgramEvent.flags, 0)) {
            apfProgramEvent2.dropMulticast = true;
        }
        if (isBitSet(apfProgramEvent.flags, 1)) {
            apfProgramEvent2.hasIpv4Addr = true;
        }
        ipConnectivityEvent.setApfProgramEvent(apfProgramEvent2);
    }

    private static void setApfStats(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.ApfStats apfStats) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics apfStatistics = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.ApfStatistics();
        apfStatistics.durationMs = apfStats.durationMs;
        apfStatistics.receivedRas = apfStats.receivedRas;
        apfStatistics.matchingRas = apfStats.matchingRas;
        apfStatistics.droppedRas = apfStats.droppedRas;
        apfStatistics.zeroLifetimeRas = apfStats.zeroLifetimeRas;
        apfStatistics.parseErrors = apfStats.parseErrors;
        apfStatistics.programUpdates = apfStats.programUpdates;
        apfStatistics.programUpdatesAll = apfStats.programUpdatesAll;
        apfStatistics.programUpdatesAllowingMulticast = apfStats.programUpdatesAllowingMulticast;
        apfStatistics.maxProgramSize = apfStats.maxProgramSize;
        ipConnectivityEvent.setApfStatistics(apfStatistics);
    }

    private static void setRaEvent(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent, android.net.metrics.RaEvent raEvent) {
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent raEvent2 = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.RaEvent();
        raEvent2.routerLifetime = raEvent.routerLifetime;
        raEvent2.prefixValidLifetime = raEvent.prefixValidLifetime;
        raEvent2.prefixPreferredLifetime = raEvent.prefixPreferredLifetime;
        raEvent2.routeInfoLifetime = raEvent.routeInfoLifetime;
        raEvent2.rdnssLifetime = raEvent.rdnssLifetime;
        raEvent2.dnsslLifetime = raEvent.dnsslLifetime;
        ipConnectivityEvent.setRaEvent(raEvent2);
    }

    private static int[] bytesToInts(byte[] bArr) {
        int[] iArr = new int[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            iArr[i] = bArr[i] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        }
        return iArr;
    }

    private static com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] toPairArray(android.util.SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[] pairArr = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair[size];
        for (int i = 0; i < size; i++) {
            com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair pair = new com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.Pair();
            pair.key = sparseIntArray.keyAt(i);
            pair.value = sparseIntArray.valueAt(i);
            pairArr[i] = pair;
        }
        return pairArr;
    }

    private static int ipSupportOf(android.net.metrics.DefaultNetworkEvent defaultNetworkEvent) {
        if (defaultNetworkEvent.ipv4 && defaultNetworkEvent.ipv6) {
            return 3;
        }
        if (defaultNetworkEvent.ipv6) {
            return 2;
        }
        if (defaultNetworkEvent.ipv4) {
            return 1;
        }
        return 0;
    }

    private static boolean isBitSet(int i, int i2) {
        return (i & (1 << i2)) != 0;
    }

    private static void inferLinkLayer(com.android.server.connectivity.metrics.nano.IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent) {
        int i;
        if (ipConnectivityEvent.transports != 0) {
            i = transportsToLinkLayer(ipConnectivityEvent.transports);
        } else if (ipConnectivityEvent.ifName == null) {
            i = 0;
        } else {
            i = ifnameToLinkLayer(ipConnectivityEvent.ifName);
        }
        if (i == 0) {
            return;
        }
        ipConnectivityEvent.linkLayer = i;
        ipConnectivityEvent.ifName = "";
    }

    private static int transportsToLinkLayer(long j) {
        switch (java.lang.Long.bitCount(j)) {
            case 0:
                return 0;
            case 1:
                return TRANSPORT_LINKLAYER_MAP.get(java.lang.Long.numberOfTrailingZeros(j), 0);
            default:
                return 6;
        }
    }

    static {
        TRANSPORT_LINKLAYER_MAP.append(0, 2);
        TRANSPORT_LINKLAYER_MAP.append(1, 4);
        TRANSPORT_LINKLAYER_MAP.append(2, 1);
        TRANSPORT_LINKLAYER_MAP.append(3, 3);
        TRANSPORT_LINKLAYER_MAP.append(4, 0);
        TRANSPORT_LINKLAYER_MAP.append(5, 8);
        TRANSPORT_LINKLAYER_MAP.append(6, 9);
        IFNAME_PREFIXES = new java.lang.String[7];
        IFNAME_LINKLAYERS = new int[7];
        IFNAME_PREFIXES[0] = "rmnet";
        IFNAME_LINKLAYERS[0] = 2;
        IFNAME_PREFIXES[1] = "wlan";
        IFNAME_LINKLAYERS[1] = 4;
        IFNAME_PREFIXES[2] = "bt-pan";
        IFNAME_LINKLAYERS[2] = 1;
        IFNAME_PREFIXES[3] = "p2p";
        IFNAME_LINKLAYERS[3] = 7;
        IFNAME_PREFIXES[4] = "aware";
        IFNAME_LINKLAYERS[4] = 8;
        IFNAME_PREFIXES[5] = "eth";
        IFNAME_LINKLAYERS[5] = 3;
        IFNAME_PREFIXES[6] = "wpan";
        IFNAME_LINKLAYERS[6] = 9;
    }

    private static int ifnameToLinkLayer(java.lang.String str) {
        for (int i = 0; i < 7; i++) {
            if (str.startsWith(IFNAME_PREFIXES[i])) {
                return IFNAME_LINKLAYERS[i];
            }
        }
        return 0;
    }
}
