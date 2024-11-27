package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
class NetworkPriorityClassifier {

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int PRIORITY_FALLBACK = Integer.MAX_VALUE;
    static final int PRIORITY_INVALID = -1;

    @android.annotation.NonNull
    private static final java.lang.String TAG = com.android.server.vcn.routeselection.NetworkPriorityClassifier.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT = -70;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int WIFI_EXIT_RSSI_THRESHOLD_DEFAULT = -74;

    NetworkPriorityClassifier() {
    }

    public static int calculatePriorityClass(com.android.server.vcn.VcnContext vcnContext, com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, boolean z, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        if (underlyingNetworkRecord.isBlocked) {
            logWtf("Network blocked for System Server: " + underlyingNetworkRecord.network);
            return -1;
        }
        if (telephonySubscriptionSnapshot == null) {
            logWtf("Got null snapshot");
            return -1;
        }
        java.util.Iterator<android.net.vcn.VcnUnderlyingNetworkTemplate> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (checkMatchesPriorityRule(vcnContext, it.next(), underlyingNetworkRecord, parcelUuid, telephonySubscriptionSnapshot, z, persistableBundleWrapper)) {
                return i;
            }
            i++;
        }
        android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (networkCapabilities.hasCapability(12)) {
            return Integer.MAX_VALUE;
        }
        return (vcnContext.isInTestMode() && networkCapabilities.hasTransport(7)) ? Integer.MAX_VALUE : -1;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static boolean checkMatchesPriorityRule(com.android.server.vcn.VcnContext vcnContext, android.net.vcn.VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate, com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, boolean z, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        int metered = vcnUnderlyingNetworkTemplate.getMetered();
        boolean z2 = !networkCapabilities.hasCapability(11);
        if ((metered == 1 && !z2) || ((metered == 2 && z2) || networkCapabilities.getLinkUpstreamBandwidthKbps() < vcnUnderlyingNetworkTemplate.getMinExitUpstreamBandwidthKbps() || ((networkCapabilities.getLinkUpstreamBandwidthKbps() < vcnUnderlyingNetworkTemplate.getMinEntryUpstreamBandwidthKbps() && !z) || networkCapabilities.getLinkDownstreamBandwidthKbps() < vcnUnderlyingNetworkTemplate.getMinExitDownstreamBandwidthKbps() || (networkCapabilities.getLinkDownstreamBandwidthKbps() < vcnUnderlyingNetworkTemplate.getMinEntryDownstreamBandwidthKbps() && !z)))) {
            return false;
        }
        for (java.util.Map.Entry entry : vcnUnderlyingNetworkTemplate.getCapabilitiesMatchCriteria().entrySet()) {
            int intValue = ((java.lang.Integer) entry.getKey()).intValue();
            int intValue2 = ((java.lang.Integer) entry.getValue()).intValue();
            if (intValue2 == 1 && !networkCapabilities.hasCapability(intValue)) {
                return false;
            }
            if (intValue2 == 2 && networkCapabilities.hasCapability(intValue)) {
                return false;
            }
        }
        if (vcnContext.isInTestMode() && networkCapabilities.hasTransport(7)) {
            return true;
        }
        if (vcnUnderlyingNetworkTemplate instanceof android.net.vcn.VcnWifiUnderlyingNetworkTemplate) {
            return checkMatchesWifiPriorityRule((android.net.vcn.VcnWifiUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, z, persistableBundleWrapper);
        }
        if (vcnUnderlyingNetworkTemplate instanceof android.net.vcn.VcnCellUnderlyingNetworkTemplate) {
            return checkMatchesCellPriorityRule(vcnContext, (android.net.vcn.VcnCellUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate, underlyingNetworkRecord, parcelUuid, telephonySubscriptionSnapshot);
        }
        logWtf("Got unknown VcnUnderlyingNetworkTemplate class: " + vcnUnderlyingNetworkTemplate.getClass().getSimpleName());
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static boolean checkMatchesWifiPriorityRule(android.net.vcn.VcnWifiUnderlyingNetworkTemplate vcnWifiUnderlyingNetworkTemplate, com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, boolean z, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (networkCapabilities.hasTransport(1) && isWifiRssiAcceptable(underlyingNetworkRecord, z, persistableBundleWrapper)) {
            return vcnWifiUnderlyingNetworkTemplate.getSsids().isEmpty() || vcnWifiUnderlyingNetworkTemplate.getSsids().contains(networkCapabilities.getSsid());
        }
        return false;
    }

    private static boolean isWifiRssiAcceptable(com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, boolean z, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if ((z && networkCapabilities.getSignalStrength() >= getWifiExitRssiThreshold(persistableBundleWrapper)) || networkCapabilities.getSignalStrength() >= getWifiEntryRssiThreshold(persistableBundleWrapper)) {
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static boolean checkMatchesCellPriorityRule(com.android.server.vcn.VcnContext vcnContext, android.net.vcn.VcnCellUnderlyingNetworkTemplate vcnCellUnderlyingNetworkTemplate, com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
        if (!networkCapabilities.hasTransport(0)) {
            return false;
        }
        android.net.TelephonyNetworkSpecifier telephonyNetworkSpecifier = (android.net.TelephonyNetworkSpecifier) networkCapabilities.getNetworkSpecifier();
        if (telephonyNetworkSpecifier == null) {
            logWtf("Got null NetworkSpecifier");
            return false;
        }
        android.telephony.TelephonyManager createForSubscriptionId = ((android.telephony.TelephonyManager) vcnContext.getContext().getSystemService(android.telephony.TelephonyManager.class)).createForSubscriptionId(telephonyNetworkSpecifier.getSubscriptionId());
        if (!vcnCellUnderlyingNetworkTemplate.getOperatorPlmnIds().isEmpty()) {
            if (!vcnCellUnderlyingNetworkTemplate.getOperatorPlmnIds().contains(createForSubscriptionId.getNetworkOperator())) {
                return false;
            }
        }
        if (!vcnCellUnderlyingNetworkTemplate.getSimSpecificCarrierIds().isEmpty()) {
            if (!vcnCellUnderlyingNetworkTemplate.getSimSpecificCarrierIds().contains(java.lang.Integer.valueOf(createForSubscriptionId.getSimSpecificCarrierId()))) {
                return false;
            }
        }
        int roaming = vcnCellUnderlyingNetworkTemplate.getRoaming();
        boolean z = !networkCapabilities.hasCapability(18);
        if ((roaming == 1 && !z) || (roaming == 2 && z)) {
            return false;
        }
        int opportunistic = vcnCellUnderlyingNetworkTemplate.getOpportunistic();
        boolean isOpportunistic = isOpportunistic(telephonySubscriptionSnapshot, networkCapabilities.getSubscriptionIds());
        if (opportunistic == 1) {
            if (!isOpportunistic) {
                return false;
            }
            if (telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid).contains(java.lang.Integer.valueOf(android.telephony.SubscriptionManager.getActiveDataSubscriptionId())) && !networkCapabilities.getSubscriptionIds().contains(java.lang.Integer.valueOf(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()))) {
                return false;
            }
        } else if (opportunistic == 2 && !isOpportunistic) {
            return false;
        }
        return true;
    }

    static boolean isOpportunistic(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, java.util.Set<java.lang.Integer> set) {
        if (telephonySubscriptionSnapshot == null) {
            logWtf("Got null snapshot");
            return false;
        }
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            if (telephonySubscriptionSnapshot.isOpportunistic(it.next().intValue())) {
                return true;
            }
        }
        return false;
    }

    static int getWifiEntryRssiThreshold(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        return persistableBundleWrapper != null ? persistableBundleWrapper.getInt("vcn_network_selection_wifi_entry_rssi_threshold", WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT) : WIFI_ENTRY_RSSI_THRESHOLD_DEFAULT;
    }

    static int getWifiExitRssiThreshold(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        return persistableBundleWrapper != null ? persistableBundleWrapper.getInt("vcn_network_selection_wifi_exit_rssi_threshold", WIFI_EXIT_RSSI_THRESHOLD_DEFAULT) : WIFI_EXIT_RSSI_THRESHOLD_DEFAULT;
    }

    private static void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, str);
        com.android.server.VcnManagementService.LOCAL_LOG.log(TAG + " WTF: " + str);
    }
}
