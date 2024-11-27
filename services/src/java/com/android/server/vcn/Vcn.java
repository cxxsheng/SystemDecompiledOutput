package com.android.server.vcn;

/* loaded from: classes2.dex */
public class Vcn extends android.os.Handler {
    private static final int MSG_CMD_BASE = 100;
    private static final int MSG_CMD_TEARDOWN = 100;
    private static final int MSG_EVENT_BASE = 0;
    private static final int MSG_EVENT_CONFIG_UPDATED = 0;
    private static final int MSG_EVENT_GATEWAY_CONNECTION_QUIT = 3;
    private static final int MSG_EVENT_MOBILE_DATA_TOGGLED = 5;
    private static final int MSG_EVENT_NETWORK_REQUESTED = 1;
    private static final int MSG_EVENT_SAFE_MODE_STATE_CHANGED = 4;
    private static final int MSG_EVENT_SUBSCRIPTIONS_CHANGED = 2;
    private static final int VCN_LEGACY_SCORE_INT = 52;

    @android.annotation.NonNull
    private android.net.vcn.VcnConfig mConfig;

    @android.annotation.NonNull
    private final com.android.server.vcn.Vcn.VcnContentResolver mContentResolver;
    private volatile int mCurrentStatus;

    @android.annotation.NonNull
    private final com.android.server.vcn.Vcn.Dependencies mDeps;
    private boolean mIsMobileDataEnabled;

    @android.annotation.NonNull
    private com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;

    @android.annotation.NonNull
    private final android.database.ContentObserver mMobileDataSettingsObserver;

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, com.android.server.vcn.Vcn.VcnUserMobileDataStateListener> mMobileDataStateListeners;

    @android.annotation.NonNull
    private final com.android.server.vcn.Vcn.VcnNetworkRequestListener mRequestListener;

    @android.annotation.NonNull
    private final android.os.ParcelUuid mSubscriptionGroup;

    @android.annotation.NonNull
    private final com.android.server.VcnManagementService.VcnCallback mVcnCallback;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnContext mVcnContext;

    @android.annotation.NonNull
    private final java.util.Map<android.net.vcn.VcnGatewayConnectionConfig, com.android.server.vcn.VcnGatewayConnection> mVcnGatewayConnections;
    private static final java.lang.String TAG = com.android.server.vcn.Vcn.class.getSimpleName();
    private static final java.util.List<java.lang.Integer> CAPS_REQUIRING_MOBILE_DATA = java.util.Arrays.asList(12, 2);

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public interface VcnGatewayStatusCallback {
        void onGatewayConnectionError(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3);

        void onQuit();

        void onSafeModeStatusChanged();
    }

    public Vcn(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull com.android.server.VcnManagementService.VcnCallback vcnCallback) {
        this(vcnContext, parcelUuid, vcnConfig, telephonySubscriptionSnapshot, vcnCallback, new com.android.server.vcn.Vcn.Dependencies());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public Vcn(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull com.android.server.VcnManagementService.VcnCallback vcnCallback, @android.annotation.NonNull com.android.server.vcn.Vcn.Dependencies dependencies) {
        super(vcnContext.getLooper());
        java.util.Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mMobileDataStateListeners = new android.util.ArrayMap();
        this.mVcnGatewayConnections = new java.util.HashMap();
        this.mCurrentStatus = 2;
        this.mIsMobileDataEnabled = false;
        this.mVcnContext = vcnContext;
        java.util.Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        java.util.Objects.requireNonNull(vcnCallback, "Missing vcnCallback");
        this.mVcnCallback = vcnCallback;
        java.util.Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        this.mRequestListener = new com.android.server.vcn.Vcn.VcnNetworkRequestListener();
        this.mContentResolver = this.mDeps.newVcnContentResolver(this.mVcnContext);
        this.mMobileDataSettingsObserver = new com.android.server.vcn.Vcn.VcnMobileDataContentObserver(this);
        this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("mobile_data"), true, this.mMobileDataSettingsObserver);
        java.util.Objects.requireNonNull(vcnConfig, "Missing config");
        this.mConfig = vcnConfig;
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        this.mIsMobileDataEnabled = getMobileDataStatus();
        updateMobileDataStateListeners();
        this.mVcnContext.getVcnNetworkProvider().registerListener(this.mRequestListener);
    }

    public void updateConfig(@android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig) {
        java.util.Objects.requireNonNull(vcnConfig, "Missing config");
        sendMessage(obtainMessage(0, vcnConfig));
    }

    public void updateSubscriptionSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        sendMessage(obtainMessage(2, telephonySubscriptionSnapshot));
    }

    public void teardownAsynchronously() {
        sendMessageAtFrontOfQueue(obtainMessage(100));
    }

    public int getStatus() {
        return this.mCurrentStatus;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public void setStatus(int i) {
        this.mCurrentStatus = i;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public java.util.Set<com.android.server.vcn.VcnGatewayConnection> getVcnGatewayConnections() {
        return java.util.Collections.unmodifiableSet(new java.util.HashSet(this.mVcnGatewayConnections.values()));
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public java.util.Map<android.net.vcn.VcnGatewayConnectionConfig, com.android.server.vcn.VcnGatewayConnection> getVcnGatewayConnectionConfigMap() {
        return java.util.Collections.unmodifiableMap(new java.util.HashMap(this.mVcnGatewayConnections));
    }

    private class VcnNetworkRequestListener implements com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener {
        private VcnNetworkRequestListener() {
        }

        @Override // com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener
        public void onNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
            java.util.Objects.requireNonNull(networkRequest, "Missing request");
            com.android.server.vcn.Vcn.this.sendMessage(com.android.server.vcn.Vcn.this.obtainMessage(1, networkRequest));
        }
    }

    @Override // android.os.Handler
    public void handleMessage(@android.annotation.NonNull android.os.Message message) {
        if (this.mCurrentStatus != 2 && this.mCurrentStatus != 3) {
        }
        switch (message.what) {
            case 0:
                handleConfigUpdated((android.net.vcn.VcnConfig) message.obj);
                break;
            case 1:
                handleNetworkRequested((android.net.NetworkRequest) message.obj);
                break;
            case 2:
                handleSubscriptionsChanged((com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot) message.obj);
                break;
            case 3:
                handleGatewayConnectionQuit((android.net.vcn.VcnGatewayConnectionConfig) message.obj);
                break;
            case 4:
                handleSafeModeStatusChanged();
                break;
            case 5:
                handleMobileDataToggled();
                break;
            case 100:
                handleTeardown();
                break;
            default:
                logWtf("Unknown msg.what: " + message.what);
                break;
        }
    }

    private void handleConfigUpdated(@android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig) {
        logDbg("Config updated: old = " + this.mConfig.hashCode() + "; new = " + vcnConfig.hashCode());
        this.mConfig = vcnConfig;
        for (java.util.Map.Entry<android.net.vcn.VcnGatewayConnectionConfig, com.android.server.vcn.VcnGatewayConnection> entry : this.mVcnGatewayConnections.entrySet()) {
            android.net.vcn.VcnGatewayConnectionConfig key = entry.getKey();
            com.android.server.vcn.VcnGatewayConnection value = entry.getValue();
            if (!this.mConfig.getGatewayConnectionConfigs().contains(key)) {
                if (value == null) {
                    logWtf("Found gatewayConnectionConfig without GatewayConnection");
                } else {
                    logInfo("Config updated, restarting gateway " + value.getLogPrefix());
                    value.teardownAsynchronously();
                }
            }
        }
        this.mVcnContext.getVcnNetworkProvider().resendAllRequests(this.mRequestListener);
    }

    private void handleTeardown() {
        logDbg("Tearing down");
        this.mVcnContext.getVcnNetworkProvider().unregisterListener(this.mRequestListener);
        java.util.Iterator<com.android.server.vcn.VcnGatewayConnection> it = this.mVcnGatewayConnections.values().iterator();
        while (it.hasNext()) {
            it.next().teardownAsynchronously();
        }
        java.util.Iterator<com.android.server.vcn.Vcn.VcnUserMobileDataStateListener> it2 = this.mMobileDataStateListeners.values().iterator();
        while (it2.hasNext()) {
            getTelephonyManager().unregisterTelephonyCallback(it2.next());
        }
        this.mMobileDataStateListeners.clear();
        this.mCurrentStatus = 1;
    }

    private void handleSafeModeStatusChanged() {
        boolean z;
        logVdbg("VcnGatewayConnection safe mode status changed");
        java.util.Iterator<com.android.server.vcn.VcnGatewayConnection> it = this.mVcnGatewayConnections.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (it.next().isInSafeMode()) {
                z = true;
                break;
            }
        }
        int i = this.mCurrentStatus;
        this.mCurrentStatus = z ? 3 : 2;
        if (i != this.mCurrentStatus) {
            this.mVcnCallback.onSafeModeStatusChanged(z);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Safe mode ");
            sb.append(this.mCurrentStatus == 3 ? "entered" : "exited");
            logInfo(sb.toString());
        }
    }

    private void handleNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        logVdbg("Received request " + networkRequest);
        java.util.Iterator<android.net.vcn.VcnGatewayConnectionConfig> it = this.mVcnGatewayConnections.keySet().iterator();
        while (it.hasNext()) {
            if (isRequestSatisfiedByGatewayConnectionConfig(networkRequest, it.next())) {
                logVdbg("Request already satisfied by existing VcnGatewayConnection: " + networkRequest);
                return;
            }
        }
        for (android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig : this.mConfig.getGatewayConnectionConfigs()) {
            if (isRequestSatisfiedByGatewayConnectionConfig(networkRequest, vcnGatewayConnectionConfig) && !getExposedCapabilitiesForMobileDataState(vcnGatewayConnectionConfig).isEmpty()) {
                if (this.mVcnGatewayConnections.containsKey(vcnGatewayConnectionConfig)) {
                    logWtf("Attempted to bring up VcnGatewayConnection for config with existing VcnGatewayConnection");
                    return;
                }
                logInfo("Bringing up new VcnGatewayConnection for request " + networkRequest);
                this.mVcnGatewayConnections.put(vcnGatewayConnectionConfig, this.mDeps.newVcnGatewayConnection(this.mVcnContext, this.mSubscriptionGroup, this.mLastSnapshot, vcnGatewayConnectionConfig, new com.android.server.vcn.Vcn.VcnGatewayStatusCallbackImpl(vcnGatewayConnectionConfig), this.mIsMobileDataEnabled));
                return;
            }
        }
        logVdbg("Request could not be fulfilled by VCN: " + networkRequest);
    }

    private java.util.Set<java.lang.Integer> getExposedCapabilitiesForMobileDataState(android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
        if (this.mIsMobileDataEnabled) {
            return vcnGatewayConnectionConfig.getAllExposedCapabilities();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(vcnGatewayConnectionConfig.getAllExposedCapabilities());
        arraySet.removeAll(CAPS_REQUIRING_MOBILE_DATA);
        return arraySet;
    }

    private void handleGatewayConnectionQuit(android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
        logInfo("VcnGatewayConnection quit: " + vcnGatewayConnectionConfig);
        this.mVcnGatewayConnections.remove(vcnGatewayConnectionConfig);
        this.mVcnContext.getVcnNetworkProvider().resendAllRequests(this.mRequestListener);
    }

    private void handleSubscriptionsChanged(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        java.util.Iterator<com.android.server.vcn.VcnGatewayConnection> it = this.mVcnGatewayConnections.values().iterator();
        while (it.hasNext()) {
            it.next().updateSubscriptionSnapshot(this.mLastSnapshot);
        }
        updateMobileDataStateListeners();
        handleMobileDataToggled();
    }

    private void updateMobileDataStateListeners() {
        java.util.Set<java.lang.Integer> allSubIdsInGroup = this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup);
        android.os.HandlerExecutor handlerExecutor = new android.os.HandlerExecutor(this);
        java.util.Iterator<java.lang.Integer> it = allSubIdsInGroup.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (!this.mMobileDataStateListeners.containsKey(java.lang.Integer.valueOf(intValue))) {
                com.android.server.vcn.Vcn.VcnUserMobileDataStateListener vcnUserMobileDataStateListener = new com.android.server.vcn.Vcn.VcnUserMobileDataStateListener();
                getTelephonyManagerForSubid(intValue).registerTelephonyCallback(handlerExecutor, vcnUserMobileDataStateListener);
                this.mMobileDataStateListeners.put(java.lang.Integer.valueOf(intValue), vcnUserMobileDataStateListener);
            }
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, com.android.server.vcn.Vcn.VcnUserMobileDataStateListener>> it2 = this.mMobileDataStateListeners.entrySet().iterator();
        while (it2.hasNext()) {
            java.util.Map.Entry<java.lang.Integer, com.android.server.vcn.Vcn.VcnUserMobileDataStateListener> next = it2.next();
            if (!allSubIdsInGroup.contains(next.getKey())) {
                getTelephonyManager().unregisterTelephonyCallback(next.getValue());
                it2.remove();
            }
        }
    }

    private void handleMobileDataToggled() {
        boolean z = this.mIsMobileDataEnabled;
        this.mIsMobileDataEnabled = getMobileDataStatus();
        if (z != this.mIsMobileDataEnabled) {
            for (java.util.Map.Entry<android.net.vcn.VcnGatewayConnectionConfig, com.android.server.vcn.VcnGatewayConnection> entry : this.mVcnGatewayConnections.entrySet()) {
                android.net.vcn.VcnGatewayConnectionConfig key = entry.getKey();
                com.android.server.vcn.VcnGatewayConnection value = entry.getValue();
                java.util.Set allExposedCapabilities = key.getAllExposedCapabilities();
                if (allExposedCapabilities.contains(12) || allExposedCapabilities.contains(2)) {
                    if (value == null) {
                        logWtf("Found gatewayConnectionConfig without GatewayConnection");
                    } else {
                        value.teardownAsynchronously();
                    }
                }
            }
            this.mVcnContext.getVcnNetworkProvider().resendAllRequests(this.mRequestListener);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Mobile data ");
            sb.append(this.mIsMobileDataEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            logInfo(sb.toString());
        }
    }

    private boolean getMobileDataStatus() {
        java.util.Iterator<java.lang.Integer> it = this.mLastSnapshot.getAllSubIdsInGroup(this.mSubscriptionGroup).iterator();
        while (it.hasNext()) {
            if (getTelephonyManagerForSubid(it.next().intValue()).isDataEnabled()) {
                return true;
            }
        }
        return false;
    }

    private boolean isRequestSatisfiedByGatewayConnectionConfig(@android.annotation.NonNull android.net.NetworkRequest networkRequest, @android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
        android.net.NetworkCapabilities.Builder builder = new android.net.NetworkCapabilities.Builder();
        builder.addTransportType(0);
        builder.addCapability(28);
        java.util.Iterator<java.lang.Integer> it = getExposedCapabilitiesForMobileDataState(vcnGatewayConnectionConfig).iterator();
        while (it.hasNext()) {
            builder.addCapability(it.next().intValue());
        }
        return networkRequest.canBeSatisfiedBy(builder.build());
    }

    private android.telephony.TelephonyManager getTelephonyManager() {
        return (android.telephony.TelephonyManager) this.mVcnContext.getContext().getSystemService(android.telephony.TelephonyManager.class);
    }

    private android.telephony.TelephonyManager getTelephonyManagerForSubid(int i) {
        return getTelephonyManager().createForSubscriptionId(i);
    }

    private java.lang.String getLogPrefix() {
        return "(" + com.android.server.vcn.util.LogUtils.getHashedSubscriptionGroup(this.mSubscriptionGroup) + "-" + java.lang.System.identityHashCode(this) + ") ";
    }

    private void logVdbg(java.lang.String str) {
    }

    private void logDbg(java.lang.String str) {
        android.util.Slog.d(TAG, getLogPrefix() + str);
    }

    private void logDbg(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.d(TAG, getLogPrefix() + str, th);
    }

    private void logInfo(java.lang.String str) {
        android.util.Slog.i(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "INFO: " + str);
    }

    private void logInfo(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.i(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "INFO: " + str + th);
    }

    private void logErr(java.lang.String str) {
        android.util.Slog.e(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "ERR: " + str);
    }

    private void logErr(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.e(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "ERR: " + str + th);
    }

    private void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "WTF: " + str);
    }

    private void logWtf(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log(getLogPrefix() + "WTF: " + str + th);
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Vcn (" + this.mSubscriptionGroup + "):");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentStatus: " + this.mCurrentStatus);
        indentingPrintWriter.println("mIsMobileDataEnabled: " + this.mIsMobileDataEnabled);
        indentingPrintWriter.println();
        indentingPrintWriter.println("mVcnGatewayConnections:");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<com.android.server.vcn.VcnGatewayConnection> it = this.mVcnGatewayConnections.values().iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public boolean isMobileDataEnabled() {
        return this.mIsMobileDataEnabled;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public void setMobileDataEnabled(boolean z) {
        this.mIsMobileDataEnabled = z;
    }

    static android.net.NetworkScore getNetworkScore() {
        return new android.net.NetworkScore.Builder().setLegacyInt(52).setTransportPrimary(true).build();
    }

    private class VcnGatewayStatusCallbackImpl implements com.android.server.vcn.Vcn.VcnGatewayStatusCallback {
        public final android.net.vcn.VcnGatewayConnectionConfig mGatewayConnectionConfig;

        VcnGatewayStatusCallbackImpl(android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
            this.mGatewayConnectionConfig = vcnGatewayConnectionConfig;
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public void onQuit() {
            com.android.server.vcn.Vcn.this.sendMessage(com.android.server.vcn.Vcn.this.obtainMessage(3, this.mGatewayConnectionConfig));
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public void onSafeModeStatusChanged() {
            com.android.server.vcn.Vcn.this.sendMessage(com.android.server.vcn.Vcn.this.obtainMessage(4));
        }

        @Override // com.android.server.vcn.Vcn.VcnGatewayStatusCallback
        public void onGatewayConnectionError(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
            com.android.server.vcn.Vcn.this.mVcnCallback.onGatewayConnectionError(str, i, str2, str3);
        }
    }

    private class VcnMobileDataContentObserver extends android.database.ContentObserver {
        private VcnMobileDataContentObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.vcn.Vcn.this.sendMessage(com.android.server.vcn.Vcn.this.obtainMessage(5));
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    class VcnUserMobileDataStateListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.UserMobileDataStateListener {
        VcnUserMobileDataStateListener() {
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public void onUserMobileDataStateChanged(boolean z) {
            com.android.server.vcn.Vcn.this.sendMessage(com.android.server.vcn.Vcn.this.obtainMessage(5));
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public com.android.server.vcn.VcnGatewayConnection newVcnGatewayConnection(com.android.server.vcn.VcnContext vcnContext, android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, com.android.server.vcn.Vcn.VcnGatewayStatusCallback vcnGatewayStatusCallback, boolean z) {
            return new com.android.server.vcn.VcnGatewayConnection(vcnContext, parcelUuid, telephonySubscriptionSnapshot, vcnGatewayConnectionConfig, vcnGatewayStatusCallback, z);
        }

        public com.android.server.vcn.Vcn.VcnContentResolver newVcnContentResolver(com.android.server.vcn.VcnContext vcnContext) {
            return new com.android.server.vcn.Vcn.VcnContentResolver(vcnContext);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class VcnContentResolver {
        private final android.content.ContentResolver mImpl;

        public VcnContentResolver(com.android.server.vcn.VcnContext vcnContext) {
            this.mImpl = vcnContext.getContext().getContentResolver();
        }

        public void registerContentObserver(@android.annotation.NonNull android.net.Uri uri, boolean z, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
            this.mImpl.registerContentObserver(uri, z, contentObserver);
        }
    }
}
