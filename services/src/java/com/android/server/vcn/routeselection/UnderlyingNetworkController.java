package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
public class UnderlyingNetworkController {

    @android.annotation.NonNull
    private static final java.lang.String TAG = com.android.server.vcn.routeselection.UnderlyingNetworkController.class.getSimpleName();

    @android.annotation.NonNull
    private final android.telephony.TelephonyCallback mActiveDataSubIdListener;

    @android.annotation.Nullable
    private com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper mCarrierConfig;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback mCb;

    @android.annotation.NonNull
    private final java.util.List<android.net.ConnectivityManager.NetworkCallback> mCellBringupCallbacks;

    @android.annotation.NonNull
    private final android.net.vcn.VcnGatewayConnectionConfig mConnectionConfig;

    @android.annotation.NonNull
    private final android.net.ConnectivityManager mConnectivityManager;

    @android.annotation.Nullable
    private com.android.server.vcn.routeselection.UnderlyingNetworkRecord mCurrentRecord;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkController.Dependencies mDeps;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private boolean mIsQuitting;

    @android.annotation.NonNull
    private com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;

    @android.annotation.Nullable
    private com.android.server.vcn.routeselection.UnderlyingNetworkRecord.Builder mRecordInProgress;

    @android.annotation.Nullable
    private com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkListener mRouteSelectionCallback;

    @android.annotation.NonNull
    private final android.os.ParcelUuid mSubscriptionGroup;
    private final java.util.Map<android.net.Network, com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> mUnderlyingNetworkRecords;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnContext mVcnContext;

    @android.annotation.Nullable
    private android.net.ConnectivityManager.NetworkCallback mWifiBringupCallback;

    @android.annotation.Nullable
    private android.net.ConnectivityManager.NetworkCallback mWifiEntryRssiThresholdCallback;

    @android.annotation.Nullable
    private android.net.ConnectivityManager.NetworkCallback mWifiExitRssiThresholdCallback;

    public interface UnderlyingNetworkControllerCallback {
        void onSelectedUnderlyingNetworkChanged(@android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord);
    }

    public UnderlyingNetworkController(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback underlyingNetworkControllerCallback) {
        this(vcnContext, vcnGatewayConnectionConfig, parcelUuid, telephonySubscriptionSnapshot, underlyingNetworkControllerCallback, new com.android.server.vcn.routeselection.UnderlyingNetworkController.Dependencies());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    UnderlyingNetworkController(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback underlyingNetworkControllerCallback, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkController.Dependencies dependencies) {
        this.mActiveDataSubIdListener = new com.android.server.vcn.routeselection.UnderlyingNetworkController.VcnActiveDataSubscriptionIdListener();
        this.mUnderlyingNetworkRecords = new android.util.ArrayMap();
        this.mCellBringupCallbacks = new java.util.ArrayList();
        this.mIsQuitting = false;
        java.util.Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mVcnContext = vcnContext;
        java.util.Objects.requireNonNull(vcnGatewayConnectionConfig, "Missing connectionConfig");
        this.mConnectionConfig = vcnGatewayConnectionConfig;
        java.util.Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        java.util.Objects.requireNonNull(underlyingNetworkControllerCallback, "Missing cb");
        this.mCb = underlyingNetworkControllerCallback;
        java.util.Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        this.mHandler = new android.os.Handler(this.mVcnContext.getLooper());
        this.mConnectivityManager = (android.net.ConnectivityManager) this.mVcnContext.getContext().getSystemService(android.net.ConnectivityManager.class);
        ((android.telephony.TelephonyManager) this.mVcnContext.getContext().getSystemService(android.telephony.TelephonyManager.class)).registerTelephonyCallback(new android.os.HandlerExecutor(this.mHandler), this.mActiveDataSubIdListener);
        this.mCarrierConfig = this.mLastSnapshot.getCarrierConfigForSubGrp(this.mSubscriptionGroup);
        registerOrUpdateNetworkRequests();
    }

    private static class CapabilityMatchCriteria {
        public final int capability;
        public final int matchCriteria;

        CapabilityMatchCriteria(int i, int i2) {
            this.capability = i;
            this.matchCriteria = i2;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.capability), java.lang.Integer.valueOf(this.matchCriteria));
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria)) {
                return false;
            }
            com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria capabilityMatchCriteria = (com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria) obj;
            return this.capability == capabilityMatchCriteria.capability && this.matchCriteria == capabilityMatchCriteria.matchCriteria;
        }
    }

    private static java.util.Set<java.util.Set<com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria>> dedupAndGetCapRequirementsForCell(android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.net.vcn.VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate : vcnGatewayConnectionConfig.getVcnUnderlyingNetworkPriorities()) {
            if (vcnUnderlyingNetworkTemplate instanceof android.net.vcn.VcnCellUnderlyingNetworkTemplate) {
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                for (java.util.Map.Entry entry : ((android.net.vcn.VcnCellUnderlyingNetworkTemplate) vcnUnderlyingNetworkTemplate).getCapabilitiesMatchCriteria().entrySet()) {
                    int intValue = ((java.lang.Integer) entry.getKey()).intValue();
                    int intValue2 = ((java.lang.Integer) entry.getValue()).intValue();
                    if (intValue2 != 0) {
                        arraySet2.add(new com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria(intValue, intValue2));
                    }
                }
                arraySet.add(arraySet2);
            }
        }
        arraySet.add(java.util.Collections.singleton(new com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria(12, 1)));
        return arraySet;
    }

    private void registerOrUpdateNetworkRequests() {
        com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkListener underlyingNetworkListener = this.mRouteSelectionCallback;
        android.net.ConnectivityManager.NetworkCallback networkCallback = this.mWifiBringupCallback;
        android.net.ConnectivityManager.NetworkCallback networkCallback2 = this.mWifiEntryRssiThresholdCallback;
        android.net.ConnectivityManager.NetworkCallback networkCallback3 = this.mWifiExitRssiThresholdCallback;
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mCellBringupCallbacks);
        this.mCellBringupCallbacks.clear();
        if (this.mVcnContext.isFlagNetworkMetricMonitorEnabled() && this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
            java.util.Iterator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> it = this.mUnderlyingNetworkRecords.values().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
        }
        this.mUnderlyingNetworkRecords.clear();
        if (!this.mIsQuitting) {
            this.mRouteSelectionCallback = new com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkListener();
            this.mConnectivityManager.registerNetworkCallback(getRouteSelectionRequest(), this.mRouteSelectionCallback, this.mHandler);
            this.mWifiEntryRssiThresholdCallback = new com.android.server.vcn.routeselection.UnderlyingNetworkController.NetworkBringupCallback();
            this.mConnectivityManager.registerNetworkCallback(getWifiEntryRssiThresholdNetworkRequest(), this.mWifiEntryRssiThresholdCallback, this.mHandler);
            this.mWifiExitRssiThresholdCallback = new com.android.server.vcn.routeselection.UnderlyingNetworkController.NetworkBringupCallback();
            this.mConnectivityManager.registerNetworkCallback(getWifiExitRssiThresholdNetworkRequest(), this.mWifiExitRssiThresholdCallback, this.mHandler);
            this.mWifiBringupCallback = new com.android.server.vcn.routeselection.UnderlyingNetworkController.NetworkBringupCallback();
            this.mConnectivityManager.requestBackgroundNetwork(getWifiNetworkRequest(), this.mWifiBringupCallback, this.mHandler);
            java.util.Iterator<java.lang.Integer> it2 = this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup).iterator();
            while (it2.hasNext()) {
                int intValue = it2.next().intValue();
                for (java.util.Set<com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria> set : dedupAndGetCapRequirementsForCell(this.mConnectionConfig)) {
                    com.android.server.vcn.routeselection.UnderlyingNetworkController.NetworkBringupCallback networkBringupCallback = new com.android.server.vcn.routeselection.UnderlyingNetworkController.NetworkBringupCallback();
                    this.mCellBringupCallbacks.add(networkBringupCallback);
                    this.mConnectivityManager.requestBackgroundNetwork(getCellNetworkRequestForSubId(intValue, set), networkBringupCallback, this.mHandler);
                }
            }
        } else {
            this.mRouteSelectionCallback = null;
            this.mWifiBringupCallback = null;
            this.mWifiEntryRssiThresholdCallback = null;
            this.mWifiExitRssiThresholdCallback = null;
        }
        if (underlyingNetworkListener != null) {
            this.mConnectivityManager.unregisterNetworkCallback(underlyingNetworkListener);
        }
        if (networkCallback != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback);
        }
        if (networkCallback2 != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback2);
        }
        if (networkCallback3 != null) {
            this.mConnectivityManager.unregisterNetworkCallback(networkCallback3);
        }
        java.util.Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            this.mConnectivityManager.unregisterNetworkCallback((android.net.ConnectivityManager.NetworkCallback) it3.next());
        }
    }

    private android.net.NetworkRequest getRouteSelectionRequest() {
        if (this.mVcnContext.isInTestMode()) {
            return getTestNetworkRequest(this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup));
        }
        return getBaseNetworkRequestBuilder().addCapability(16).addCapability(21).setSubscriptionIds(this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup)).build();
    }

    private android.net.NetworkRequest.Builder getBaseWifiNetworkRequestBuilder() {
        return getBaseNetworkRequestBuilder().addTransportType(1).addCapability(12).setSubscriptionIds(this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup));
    }

    private android.net.NetworkRequest getWifiNetworkRequest() {
        return getBaseWifiNetworkRequestBuilder().build();
    }

    private android.net.NetworkRequest getWifiEntryRssiThresholdNetworkRequest() {
        return getBaseWifiNetworkRequestBuilder().setSignalStrength(com.android.server.vcn.routeselection.NetworkPriorityClassifier.getWifiEntryRssiThreshold(this.mCarrierConfig)).build();
    }

    private android.net.NetworkRequest getWifiExitRssiThresholdNetworkRequest() {
        return getBaseWifiNetworkRequestBuilder().setSignalStrength(com.android.server.vcn.routeselection.NetworkPriorityClassifier.getWifiExitRssiThreshold(this.mCarrierConfig)).build();
    }

    private android.net.NetworkRequest getCellNetworkRequestForSubId(int i, java.util.Set<com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria> set) {
        android.net.NetworkRequest.Builder networkSpecifier = getBaseNetworkRequestBuilder().addTransportType(0).setNetworkSpecifier(new android.net.TelephonyNetworkSpecifier(i));
        for (com.android.server.vcn.routeselection.UnderlyingNetworkController.CapabilityMatchCriteria capabilityMatchCriteria : set) {
            int i2 = capabilityMatchCriteria.capability;
            int i3 = capabilityMatchCriteria.matchCriteria;
            if (i3 == 1) {
                networkSpecifier.addCapability(i2);
            } else if (i3 == 2) {
                networkSpecifier.addForbiddenCapability(i2);
            }
        }
        return networkSpecifier.build();
    }

    private android.net.NetworkRequest.Builder getBaseNetworkRequestBuilder() {
        return new android.net.NetworkRequest.Builder().removeCapability(14).removeCapability(13).removeCapability(28);
    }

    private android.net.NetworkRequest getTestNetworkRequest(@android.annotation.NonNull java.util.Set<java.lang.Integer> set) {
        return new android.net.NetworkRequest.Builder().clearCapabilities().addTransportType(7).setSubscriptionIds(set).build();
    }

    public void updateSubscriptionSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing newSnapshot");
        com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot2 = this.mLastSnapshot;
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        this.mCarrierConfig = this.mLastSnapshot.getCarrierConfigForSubGrp(this.mSubscriptionGroup);
        java.util.Iterator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> it = this.mUnderlyingNetworkRecords.values().iterator();
        while (it.hasNext()) {
            it.next().reevaluate(this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), this.mSubscriptionGroup, this.mLastSnapshot, this.mCarrierConfig);
        }
        if (telephonySubscriptionSnapshot2.getAllSubIdsInGroup(this.mSubscriptionGroup).equals(telephonySubscriptionSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup))) {
            if (this.mVcnContext.isFlagNetworkMetricMonitorEnabled() && this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
                reevaluateNetworks();
                return;
            }
            return;
        }
        registerOrUpdateNetworkRequests();
    }

    public void updateInboundTransform(@android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform) {
        if (!this.mVcnContext.isFlagNetworkMetricMonitorEnabled() || !this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
            logWtf("#updateInboundTransform: unexpected call; flags missing");
            return;
        }
        java.util.Objects.requireNonNull(underlyingNetworkRecord, "currentNetwork is null");
        java.util.Objects.requireNonNull(ipSecTransform, "transform is null");
        if (this.mCurrentRecord == null || this.mRouteSelectionCallback == null || !java.util.Objects.equals(underlyingNetworkRecord.network, this.mCurrentRecord.network)) {
            return;
        }
        this.mUnderlyingNetworkRecords.get(this.mCurrentRecord.network).setInboundTransform(ipSecTransform);
    }

    public void teardown() {
        this.mVcnContext.ensureRunningOnLooperThread();
        this.mIsQuitting = true;
        registerOrUpdateNetworkRequests();
        ((android.telephony.TelephonyManager) this.mVcnContext.getContext().getSystemService(android.telephony.TelephonyManager.class)).unregisterTelephonyCallback(this.mActiveDataSubIdListener);
    }

    private java.util.TreeSet<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> getSortedUnderlyingNetworks() {
        java.util.TreeSet<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> treeSet = new java.util.TreeSet<>(com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.getComparator(this.mVcnContext));
        for (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator : this.mUnderlyingNetworkRecords.values()) {
            if (underlyingNetworkEvaluator.getPriorityClass() != -1) {
                treeSet.add(underlyingNetworkEvaluator);
            }
        }
        return treeSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reevaluateNetworks() {
        if (this.mIsQuitting || this.mRouteSelectionCallback == null) {
            return;
        }
        java.util.TreeSet<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> sortedUnderlyingNetworks = getSortedUnderlyingNetworks();
        com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator first = sortedUnderlyingNetworks.isEmpty() ? null : sortedUnderlyingNetworks.first();
        com.android.server.vcn.routeselection.UnderlyingNetworkRecord networkRecord = first == null ? null : first.getNetworkRecord();
        if (java.util.Objects.equals(this.mCurrentRecord, networkRecord)) {
            return;
        }
        java.util.Iterator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> it = sortedUnderlyingNetworks.iterator();
        java.lang.String str = "";
        while (it.hasNext()) {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator next = it.next();
            if (!str.isEmpty()) {
                str = str + ", ";
            }
            str = str + next.getNetwork() + ": " + next.getPriorityClass();
        }
        if (!com.android.server.vcn.routeselection.UnderlyingNetworkRecord.isSameNetwork(this.mCurrentRecord, networkRecord)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Selected network changed to ");
            sb.append(networkRecord != null ? networkRecord.network : null);
            sb.append(", selected from list: ");
            sb.append(str);
            logInfo(sb.toString());
        }
        this.mCurrentRecord = networkRecord;
        this.mCb.onSelectedUnderlyingNetworkChanged(this.mCurrentRecord);
        java.util.Iterator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> it2 = this.mUnderlyingNetworkRecords.values().iterator();
        while (it2.hasNext()) {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator next2 = it2.next();
            next2.setIsSelected(first == next2, this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), this.mSubscriptionGroup, this.mLastSnapshot, this.mCarrierConfig);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class NetworkBringupCallback extends android.net.ConnectivityManager.NetworkCallback {
        NetworkBringupCallback() {
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class UnderlyingNetworkListener extends android.net.ConnectivityManager.NetworkCallback {
        UnderlyingNetworkListener() {
            super(1);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(@android.annotation.NonNull android.net.Network network) {
            com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.put(network, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mDeps.newUnderlyingNetworkEvaluator(com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext, network, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mSubscriptionGroup, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mLastSnapshot, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mCarrierConfig, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.new NetworkEvaluatorCallbackImpl()));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(@android.annotation.NonNull android.net.Network network) {
            if (com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext.isFlagNetworkMetricMonitorEnabled() && com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
                ((com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.get(network)).close();
            }
            com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.remove(network);
            com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator = (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.get(network);
            if (underlyingNetworkEvaluator == null) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.logWtf("Got capabilities change for unknown key: " + network);
                return;
            }
            underlyingNetworkEvaluator.setNetworkCapabilities(networkCapabilities, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mSubscriptionGroup, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mLastSnapshot, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mCarrierConfig);
            if (underlyingNetworkEvaluator.isValid()) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.LinkProperties linkProperties) {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator = (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.get(network);
            if (underlyingNetworkEvaluator == null) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.logWtf("Got link properties change for unknown key: " + network);
                return;
            }
            underlyingNetworkEvaluator.setLinkProperties(linkProperties, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mSubscriptionGroup, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mLastSnapshot, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mCarrierConfig);
            if (underlyingNetworkEvaluator.isValid()) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onBlockedStatusChanged(@android.annotation.NonNull android.net.Network network, boolean z) {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator = (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mUnderlyingNetworkRecords.get(network);
            if (underlyingNetworkEvaluator == null) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.logWtf("Got blocked status change for unknown key: " + network);
                return;
            }
            underlyingNetworkEvaluator.setIsBlocked(z, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mConnectionConfig.getVcnUnderlyingNetworkPriorities(), com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mSubscriptionGroup, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mLastSnapshot, com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mCarrierConfig);
            if (underlyingNetworkEvaluator.isValid()) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class NetworkEvaluatorCallbackImpl implements com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback {
        NetworkEvaluatorCallbackImpl() {
        }

        @Override // com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback
        public void onEvaluationResultChanged() {
            if (!com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext.isFlagNetworkMetricMonitorEnabled() || !com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.logWtf("#onEvaluationResultChanged: unexpected call; flags missing");
            } else {
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.mVcnContext.ensureRunningOnLooperThread();
                com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
            }
        }
    }

    private java.lang.String getLogPrefix() {
        return "(" + com.android.server.vcn.util.LogUtils.getHashedSubscriptionGroup(this.mSubscriptionGroup) + "-" + this.mConnectionConfig.getGatewayConnectionName() + "-" + java.lang.System.identityHashCode(this) + ") ";
    }

    private java.lang.String getTagLogPrefix() {
        return "[ " + TAG + " " + getLogPrefix() + "]";
    }

    private void logInfo(java.lang.String str) {
        android.util.Slog.i(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO] " + getTagLogPrefix() + str);
    }

    private void logInfo(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.i(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO] " + getTagLogPrefix() + str + th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, str);
        com.android.server.VcnManagementService.LOCAL_LOG.log(TAG + "[WTF ] " + getTagLogPrefix() + str);
    }

    private void logWtf(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log(TAG + "[WTF ] " + getTagLogPrefix() + str + th);
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("UnderlyingNetworkController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Carrier WiFi Entry Threshold: " + com.android.server.vcn.routeselection.NetworkPriorityClassifier.getWifiEntryRssiThreshold(this.mCarrierConfig));
        indentingPrintWriter.println("Carrier WiFi Exit Threshold: " + com.android.server.vcn.routeselection.NetworkPriorityClassifier.getWifiExitRssiThreshold(this.mCarrierConfig));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Currently selected: ");
        sb.append(this.mCurrentRecord == null ? null : this.mCurrentRecord.network);
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("VcnUnderlyingNetworkTemplate list:");
        indentingPrintWriter.increaseIndent();
        int i = 0;
        for (android.net.vcn.VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate : this.mConnectionConfig.getVcnUnderlyingNetworkPriorities()) {
            indentingPrintWriter.println("Priority index: " + i);
            vcnUnderlyingNetworkTemplate.dump(indentingPrintWriter);
            i++;
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Underlying networks:");
        indentingPrintWriter.increaseIndent();
        if (this.mRouteSelectionCallback != null) {
            java.util.Iterator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> it = getSortedUnderlyingNetworks().iterator();
            while (it.hasNext()) {
                it.next().dump(indentingPrintWriter);
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    private class VcnActiveDataSubscriptionIdListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener {
        private VcnActiveDataSubscriptionIdListener() {
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public void onActiveDataSubscriptionIdChanged(int i) {
            com.android.server.vcn.routeselection.UnderlyingNetworkController.this.reevaluateNetworks();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator newUnderlyingNetworkEvaluator(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback networkEvaluatorCallback) {
            return new com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator(vcnContext, network, list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper, networkEvaluatorCallback);
        }
    }
}
