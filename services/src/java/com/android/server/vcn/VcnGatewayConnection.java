package com.android.server.vcn;

/* loaded from: classes2.dex */
public class VcnGatewayConnection extends com.android.internal.util.StateMachine {
    private static final int ARG_NOT_PRESENT = Integer.MIN_VALUE;
    private static final java.lang.String DISCONNECT_REASON_INTERNAL_ERROR = "Uncaught exception: ";
    private static final java.lang.String DISCONNECT_REASON_NETWORK_AGENT_UNWANTED = "NetworkAgent was unwanted";
    private static final java.lang.String DISCONNECT_REASON_TEARDOWN = "teardown() called on VcnTunnel";
    private static final java.lang.String DISCONNECT_REASON_UNDERLYING_NETWORK_LOST = "Underlying Network lost";
    private static final int EVENT_DATA_STALL_SUSPECTED = 13;
    private static final int EVENT_DISCONNECT_REQUESTED = 7;
    private static final int EVENT_IKE_CONNECTION_INFO_CHANGED = 12;
    private static final int EVENT_MIGRATION_COMPLETED = 11;
    private static final int EVENT_RETRY_TIMEOUT_EXPIRED = 2;
    private static final int EVENT_SAFE_MODE_TIMEOUT_EXCEEDED = 10;
    private static final int EVENT_SESSION_CLOSED = 4;
    private static final int EVENT_SESSION_LOST = 3;
    private static final int EVENT_SETUP_COMPLETED = 6;
    private static final int EVENT_SUBSCRIPTIONS_CHANGED = 9;
    private static final int EVENT_TEARDOWN_TIMEOUT_EXPIRED = 8;
    private static final int EVENT_TRANSFORM_CREATED = 5;
    private static final int EVENT_UNDERLYING_NETWORK_CHANGED = 1;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String NETWORK_INFO_EXTRA_INFO = "VCN";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String NETWORK_INFO_NETWORK_TYPE_STRING = "MOBILE";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int NETWORK_LOSS_DISCONNECT_TIMEOUT_SECONDS = 30;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int SAFEMODE_TIMEOUT_SECONDS = 30;
    private static final int SAFEMODE_TIMEOUT_SECONDS_TEST_MODE = 10;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int TEARDOWN_TIMEOUT_SECONDS = 5;
    private static final int TOKEN_ALL = Integer.MIN_VALUE;
    static final int TUNNEL_AGGREGATION_SA_COUNT_MAX_DEFAULT = 1;
    private com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration mChildConfig;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.vcn.VcnGatewayConnection.ConnectedState mConnectedState;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.vcn.VcnGatewayConnection.ConnectingState mConnectingState;

    @android.annotation.NonNull
    private final android.net.vcn.VcnGatewayConnectionConfig mConnectionConfig;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnGatewayConnection.VcnConnectivityDiagnosticsCallback mConnectivityDiagnosticsCallback;

    @android.annotation.NonNull
    private final android.net.ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;

    @android.annotation.NonNull
    private final android.net.ConnectivityManager mConnectivityManager;
    private int mCurrentToken;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnGatewayConnection.Dependencies mDeps;

    @android.annotation.Nullable
    private com.android.internal.util.WakeupMessage mDisconnectRequestAlarm;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.vcn.VcnGatewayConnection.DisconnectedState mDisconnectedState;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.vcn.VcnGatewayConnection.DisconnectingState mDisconnectingState;
    private int mFailedAttempts;

    @android.annotation.NonNull
    private final com.android.server.vcn.Vcn.VcnGatewayStatusCallback mGatewayStatusCallback;
    private android.net.ipsec.ike.IkeSessionConnectionInfo mIkeConnectionInfo;
    private com.android.server.vcn.VcnGatewayConnection.VcnIkeSession mIkeSession;

    @android.annotation.NonNull
    private final android.net.IpSecManager mIpSecManager;
    private boolean mIsInSafeMode;
    private final boolean mIsMobileDataEnabled;
    private com.android.server.vcn.util.OneWayBoolean mIsQuitting;

    @android.annotation.NonNull
    private com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;
    private com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent mNetworkAgent;

    @android.annotation.Nullable
    private com.android.internal.util.WakeupMessage mRetryTimeoutAlarm;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.vcn.VcnGatewayConnection.RetryTimeoutState mRetryTimeoutState;

    @android.annotation.Nullable
    private com.android.internal.util.WakeupMessage mSafeModeTimeoutAlarm;

    @android.annotation.NonNull
    private final android.os.ParcelUuid mSubscriptionGroup;

    @android.annotation.Nullable
    private com.android.internal.util.WakeupMessage mTeardownTimeoutAlarm;

    @android.annotation.Nullable
    private android.net.IpSecManager.IpSecTunnelInterface mTunnelIface;
    private com.android.server.vcn.routeselection.UnderlyingNetworkRecord mUnderlying;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkController mUnderlyingNetworkController;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnGatewayConnection.VcnUnderlyingNetworkControllerCallback mUnderlyingNetworkControllerCallback;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnContext mVcnContext;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnGatewayConnection.VcnWakeLock mWakeLock;
    private static final java.lang.String TAG = com.android.server.vcn.VcnGatewayConnection.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.net.InetAddress DUMMY_ADDR = android.net.InetAddresses.parseNumericAddress("192.0.2.0");

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String TEARDOWN_TIMEOUT_ALARM = TAG + "_TEARDOWN_TIMEOUT_ALARM";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String DISCONNECT_REQUEST_ALARM = TAG + "_DISCONNECT_REQUEST_ALARM";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String RETRY_TIMEOUT_ALARM = TAG + "_RETRY_TIMEOUT_ALARM";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String SAFEMODE_TIMEOUT_ALARM = TAG + "_SAFEMODE_TIMEOUT_ALARM";
    private static final int[] MERGED_CAPABILITIES = {11, 18};

    private interface EventInfo {
    }

    private static class EventUnderlyingNetworkChangedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.Nullable
        public final com.android.server.vcn.routeselection.UnderlyingNetworkRecord newUnderlying;

        EventUnderlyingNetworkChangedInfo(@android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord) {
            this.newUnderlying = underlyingNetworkRecord;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.newUnderlying);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo)) {
                return false;
            }
            return java.util.Objects.equals(this.newUnderlying, ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) obj).newUnderlying);
        }
    }

    private static class EventSessionLostInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.Nullable
        public final java.lang.Exception exception;

        EventSessionLostInfo(@android.annotation.NonNull java.lang.Exception exc) {
            this.exception = exc;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.exception);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventSessionLostInfo)) {
                return false;
            }
            return java.util.Objects.equals(this.exception, ((com.android.server.vcn.VcnGatewayConnection.EventSessionLostInfo) obj).exception);
        }
    }

    private static class EventTransformCreatedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {
        public final int direction;

        @android.annotation.NonNull
        public final android.net.IpSecTransform transform;

        EventTransformCreatedInfo(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform) {
            this.direction = i;
            java.util.Objects.requireNonNull(ipSecTransform);
            this.transform = ipSecTransform;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.direction), this.transform);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo)) {
                return false;
            }
            com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo eventTransformCreatedInfo = (com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo) obj;
            return this.direction == eventTransformCreatedInfo.direction && java.util.Objects.equals(this.transform, eventTransformCreatedInfo.transform);
        }
    }

    private static class EventSetupCompletedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.NonNull
        public final com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration childSessionConfig;

        EventSetupCompletedInfo(@android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration) {
            java.util.Objects.requireNonNull(vcnChildSessionConfiguration);
            this.childSessionConfig = vcnChildSessionConfiguration;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.childSessionConfig);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventSetupCompletedInfo)) {
                return false;
            }
            return java.util.Objects.equals(this.childSessionConfig, ((com.android.server.vcn.VcnGatewayConnection.EventSetupCompletedInfo) obj).childSessionConfig);
        }
    }

    private static class EventDisconnectRequestedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.NonNull
        public final java.lang.String reason;
        public final boolean shouldQuit;

        EventDisconnectRequestedInfo(@android.annotation.NonNull java.lang.String str, boolean z) {
            java.util.Objects.requireNonNull(str);
            this.reason = str;
            this.shouldQuit = z;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.reason, java.lang.Boolean.valueOf(this.shouldQuit));
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo)) {
                return false;
            }
            com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo eventDisconnectRequestedInfo = (com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) obj;
            return this.reason.equals(eventDisconnectRequestedInfo.reason) && this.shouldQuit == eventDisconnectRequestedInfo.shouldQuit;
        }
    }

    private static class EventMigrationCompletedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.NonNull
        public final android.net.IpSecTransform inTransform;

        @android.annotation.NonNull
        public final android.net.IpSecTransform outTransform;

        EventMigrationCompletedInfo(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2) {
            java.util.Objects.requireNonNull(ipSecTransform);
            this.inTransform = ipSecTransform;
            java.util.Objects.requireNonNull(ipSecTransform2);
            this.outTransform = ipSecTransform2;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.inTransform, this.outTransform);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo)) {
                return false;
            }
            com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo eventMigrationCompletedInfo = (com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo) obj;
            return java.util.Objects.equals(this.inTransform, eventMigrationCompletedInfo.inTransform) && java.util.Objects.equals(this.outTransform, eventMigrationCompletedInfo.outTransform);
        }
    }

    private static class EventIkeConnectionInfoChangedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.NonNull
        public final android.net.ipsec.ike.IkeSessionConnectionInfo ikeConnectionInfo;

        EventIkeConnectionInfoChangedInfo(@android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            this.ikeConnectionInfo = ikeSessionConnectionInfo;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.ikeConnectionInfo);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventIkeConnectionInfoChangedInfo)) {
                return false;
            }
            return java.util.Objects.equals(this.ikeConnectionInfo, ((com.android.server.vcn.VcnGatewayConnection.EventIkeConnectionInfoChangedInfo) obj).ikeConnectionInfo);
        }
    }

    private static class EventDataStallSuspectedInfo implements com.android.server.vcn.VcnGatewayConnection.EventInfo {

        @android.annotation.NonNull
        public final android.net.Network network;

        EventDataStallSuspectedInfo(@android.annotation.NonNull android.net.Network network) {
            this.network = network;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.network);
        }

        public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.VcnGatewayConnection.EventDataStallSuspectedInfo)) {
                return false;
            }
            return java.util.Objects.equals(this.network, ((com.android.server.vcn.VcnGatewayConnection.EventDataStallSuspectedInfo) obj).network);
        }
    }

    public VcnGatewayConnection(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.NonNull com.android.server.vcn.Vcn.VcnGatewayStatusCallback vcnGatewayStatusCallback, boolean z) {
        this(vcnContext, parcelUuid, telephonySubscriptionSnapshot, vcnGatewayConnectionConfig, vcnGatewayStatusCallback, z, new com.android.server.vcn.VcnGatewayConnection.Dependencies());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    VcnGatewayConnection(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.NonNull com.android.server.vcn.Vcn.VcnGatewayStatusCallback vcnGatewayStatusCallback, boolean z, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.Dependencies dependencies) {
        super(r0, vcnContext.getLooper());
        java.lang.String str = TAG;
        java.util.Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mDisconnectedState = new com.android.server.vcn.VcnGatewayConnection.DisconnectedState();
        this.mDisconnectingState = new com.android.server.vcn.VcnGatewayConnection.DisconnectingState();
        this.mConnectingState = new com.android.server.vcn.VcnGatewayConnection.ConnectingState();
        this.mConnectedState = new com.android.server.vcn.VcnGatewayConnection.ConnectedState();
        this.mRetryTimeoutState = new com.android.server.vcn.VcnGatewayConnection.RetryTimeoutState();
        this.mTunnelIface = null;
        this.mIsQuitting = new com.android.server.vcn.util.OneWayBoolean();
        this.mIsInSafeMode = false;
        this.mCurrentToken = -1;
        this.mFailedAttempts = 0;
        this.mVcnContext = vcnContext;
        java.util.Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        java.util.Objects.requireNonNull(vcnGatewayConnectionConfig, "Missing connectionConfig");
        this.mConnectionConfig = vcnGatewayConnectionConfig;
        java.util.Objects.requireNonNull(vcnGatewayStatusCallback, "Missing gatewayStatusCallback");
        this.mGatewayStatusCallback = vcnGatewayStatusCallback;
        this.mIsMobileDataEnabled = z;
        java.util.Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        this.mUnderlyingNetworkControllerCallback = new com.android.server.vcn.VcnGatewayConnection.VcnUnderlyingNetworkControllerCallback();
        this.mWakeLock = this.mDeps.newWakeLock(this.mVcnContext.getContext(), 1, TAG);
        this.mUnderlyingNetworkController = this.mDeps.newUnderlyingNetworkController(this.mVcnContext, this.mConnectionConfig, parcelUuid, this.mLastSnapshot, this.mUnderlyingNetworkControllerCallback);
        this.mIpSecManager = (android.net.IpSecManager) this.mVcnContext.getContext().getSystemService(android.net.IpSecManager.class);
        this.mConnectivityManager = (android.net.ConnectivityManager) this.mVcnContext.getContext().getSystemService(android.net.ConnectivityManager.class);
        this.mConnectivityDiagnosticsManager = (android.net.ConnectivityDiagnosticsManager) this.mVcnContext.getContext().getSystemService(android.net.ConnectivityDiagnosticsManager.class);
        this.mConnectivityDiagnosticsCallback = new com.android.server.vcn.VcnGatewayConnection.VcnConnectivityDiagnosticsCallback();
        if (this.mConnectionConfig.hasGatewayOption(0)) {
            this.mConnectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(new android.net.NetworkRequest.Builder().addTransportType(0).build(), new android.os.HandlerExecutor(new android.os.Handler(vcnContext.getLooper())), this.mConnectivityDiagnosticsCallback);
        }
        addState(this.mDisconnectedState);
        addState(this.mDisconnectingState);
        addState(this.mConnectingState);
        addState(this.mConnectedState);
        addState(this.mRetryTimeoutState);
        setInitialState(this.mDisconnectedState);
        setDbg(false);
        start();
    }

    public boolean isInSafeMode() {
        this.mVcnContext.ensureRunningOnLooperThread();
        return this.mIsInSafeMode;
    }

    public void teardownAsynchronously() {
        logDbg("Triggering async teardown");
        sendDisconnectRequestedAndAcquireWakelock(DISCONNECT_REASON_TEARDOWN, true);
    }

    protected void onQuitting() {
        logInfo("Quitting VcnGatewayConnection");
        if (this.mNetworkAgent != null) {
            logWtf("NetworkAgent was non-null in onQuitting");
            this.mNetworkAgent.unregister();
            this.mNetworkAgent = null;
        }
        if (this.mIkeSession != null) {
            logWtf("IkeSession was non-null in onQuitting");
            this.mIkeSession.kill();
            this.mIkeSession = null;
        }
        if (this.mTunnelIface != null) {
            this.mTunnelIface.close();
        }
        releaseWakeLock();
        cancelTeardownTimeoutAlarm();
        cancelDisconnectRequestAlarm();
        cancelRetryTimeoutAlarm();
        cancelSafeModeAlarm();
        this.mUnderlyingNetworkController.teardown();
        this.mGatewayStatusCallback.onQuit();
        this.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(this.mConnectivityDiagnosticsCallback);
    }

    public void updateSubscriptionSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mVcnContext.ensureRunningOnLooperThread();
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        this.mUnderlyingNetworkController.updateSubscriptionSnapshot(this.mLastSnapshot);
        sendMessageAndAcquireWakeLock(9, Integer.MIN_VALUE);
    }

    private class VcnConnectivityDiagnosticsCallback extends android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback {
        private VcnConnectivityDiagnosticsCallback() {
        }

        @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
        public void onDataStallSuspected(android.net.ConnectivityDiagnosticsManager.DataStallReport dataStallReport) {
            com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.ensureRunningOnLooperThread();
            android.net.Network network = dataStallReport.getNetwork();
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("Data stall suspected on " + network);
            com.android.server.vcn.VcnGatewayConnection.this.sendMessageAndAcquireWakeLock(13, Integer.MIN_VALUE, new com.android.server.vcn.VcnGatewayConnection.EventDataStallSuspectedInfo(network));
        }
    }

    private class VcnUnderlyingNetworkControllerCallback implements com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback {
        private VcnUnderlyingNetworkControllerCallback() {
        }

        @Override // com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback
        public void onSelectedUnderlyingNetworkChanged(@android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord) {
            com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.ensureRunningOnLooperThread();
            if (!com.android.server.vcn.routeselection.UnderlyingNetworkRecord.isSameNetwork(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, underlyingNetworkRecord)) {
                com.android.server.vcn.VcnGatewayConnection vcnGatewayConnection = com.android.server.vcn.VcnGatewayConnection.this;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Selected underlying network changed: ");
                sb.append(underlyingNetworkRecord == null ? null : underlyingNetworkRecord.network);
                vcnGatewayConnection.logInfo(sb.toString());
            }
            if (underlyingNetworkRecord == null) {
                if (com.android.server.vcn.VcnGatewayConnection.this.mDeps.isAirplaneModeOn(com.android.server.vcn.VcnGatewayConnection.this.mVcnContext)) {
                    com.android.server.vcn.VcnGatewayConnection.this.sendMessageAndAcquireWakeLock(1, Integer.MIN_VALUE, new com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo(null));
                    com.android.server.vcn.VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_UNDERLYING_NETWORK_LOST, false);
                    return;
                }
                com.android.server.vcn.VcnGatewayConnection.this.setDisconnectRequestAlarm();
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.cancelDisconnectRequestAlarm();
            }
            com.android.server.vcn.VcnGatewayConnection.this.sendMessageAndAcquireWakeLock(1, Integer.MIN_VALUE, new com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo(underlyingNetworkRecord));
        }
    }

    private void acquireWakeLock() {
        this.mVcnContext.ensureRunningOnLooperThread();
        if (!this.mIsQuitting.getValue()) {
            this.mWakeLock.acquire();
            logVdbg("Wakelock acquired: " + this.mWakeLock);
        }
    }

    private void releaseWakeLock() {
        this.mVcnContext.ensureRunningOnLooperThread();
        this.mWakeLock.release();
        logVdbg("Wakelock released: " + this.mWakeLock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeReleaseWakeLock() {
        android.os.Handler handler = getHandler();
        if (handler == null || !handler.hasMessagesOrCallbacks()) {
            releaseWakeLock();
        }
    }

    public void sendMessage(int i) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i);
    }

    public void sendMessage(int i, java.lang.Object obj) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, obj);
    }

    public void sendMessage(int i, int i2) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2);
    }

    public void sendMessage(int i, int i2, int i3) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2, i3);
    }

    public void sendMessage(int i, int i2, int i3, java.lang.Object obj) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2, i3, obj);
    }

    public void sendMessage(android.os.Message message) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessageAndAcquireWakeLock(int i, int i2) {
        acquireWakeLock();
        super.sendMessage(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessageAndAcquireWakeLock(int i, int i2, com.android.server.vcn.VcnGatewayConnection.EventInfo eventInfo) {
        acquireWakeLock();
        super.sendMessage(i, i2, Integer.MIN_VALUE, eventInfo);
    }

    private void sendMessageAndAcquireWakeLock(int i, int i2, int i3, com.android.server.vcn.VcnGatewayConnection.EventInfo eventInfo) {
        acquireWakeLock();
        super.sendMessage(i, i2, i3, eventInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendMessageAndAcquireWakeLock, reason: merged with bridge method [inline-methods] */
    public void lambda$createScheduledAlarm$0(android.os.Message message) {
        acquireWakeLock();
        super.sendMessage(message);
    }

    private void removeEqualMessages(int i) {
        removeEqualMessages(i, null);
    }

    private void removeEqualMessages(int i, @android.annotation.Nullable java.lang.Object obj) {
        android.os.Handler handler = getHandler();
        if (handler != null) {
            handler.removeEqualMessages(i, obj);
        }
        maybeReleaseWakeLock();
    }

    private com.android.internal.util.WakeupMessage createScheduledAlarm(@android.annotation.NonNull java.lang.String str, final android.os.Message message, long j) {
        android.os.Handler handler = getHandler();
        if (handler == null) {
            logWarn("Attempted to schedule alarm after StateMachine has quit", new java.lang.IllegalStateException());
            return null;
        }
        com.android.internal.util.WakeupMessage newWakeupMessage = this.mDeps.newWakeupMessage(this.mVcnContext, handler, str, new java.lang.Runnable() { // from class: com.android.server.vcn.VcnGatewayConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.vcn.VcnGatewayConnection.this.lambda$createScheduledAlarm$0(message);
            }
        });
        newWakeupMessage.schedule(this.mDeps.getElapsedRealTime() + j);
        return newWakeupMessage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTeardownTimeoutAlarm() {
        logVdbg("Setting teardown timeout alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mTeardownTimeoutAlarm != null) {
            logWtf("mTeardownTimeoutAlarm should be null before being set; mCurrentToken: " + this.mCurrentToken);
        }
        this.mTeardownTimeoutAlarm = createScheduledAlarm(TEARDOWN_TIMEOUT_ALARM, obtainMessage(8, this.mCurrentToken), java.util.concurrent.TimeUnit.SECONDS.toMillis(5L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelTeardownTimeoutAlarm() {
        logVdbg("Cancelling teardown timeout alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mTeardownTimeoutAlarm != null) {
            this.mTeardownTimeoutAlarm.cancel();
            this.mTeardownTimeoutAlarm = null;
        }
        removeEqualMessages(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisconnectRequestAlarm() {
        logVdbg("Setting alarm to disconnect due to underlying network loss; mCurrentToken: " + this.mCurrentToken);
        if (this.mDisconnectRequestAlarm != null) {
            return;
        }
        this.mDisconnectRequestAlarm = createScheduledAlarm(DISCONNECT_REQUEST_ALARM, obtainMessage(7, Integer.MIN_VALUE, 0, new com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo(DISCONNECT_REASON_UNDERLYING_NETWORK_LOST, false)), java.util.concurrent.TimeUnit.SECONDS.toMillis(30L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelDisconnectRequestAlarm() {
        logVdbg("Cancelling alarm to disconnect due to underlying network loss; mCurrentToken: " + this.mCurrentToken);
        if (this.mDisconnectRequestAlarm != null) {
            this.mDisconnectRequestAlarm.cancel();
            this.mDisconnectRequestAlarm = null;
        }
        removeEqualMessages(7, new com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo(DISCONNECT_REASON_UNDERLYING_NETWORK_LOST, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRetryTimeoutAlarm(long j) {
        logVdbg("Setting retry alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mRetryTimeoutAlarm != null) {
            logWtf("mRetryTimeoutAlarm should be null before being set; mCurrentToken: " + this.mCurrentToken);
        }
        this.mRetryTimeoutAlarm = createScheduledAlarm(RETRY_TIMEOUT_ALARM, obtainMessage(2, this.mCurrentToken), j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelRetryTimeoutAlarm() {
        logVdbg("Cancel retry alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mRetryTimeoutAlarm != null) {
            this.mRetryTimeoutAlarm.cancel();
            this.mRetryTimeoutAlarm = null;
        }
        removeEqualMessages(2);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setSafeModeAlarm() {
        boolean safeModeConfig = this.mVcnContext.getFeatureFlags().safeModeConfig();
        logVdbg("isFlagSafeModeConfigEnabled " + safeModeConfig);
        if (safeModeConfig && !this.mConnectionConfig.isSafeModeEnabled()) {
            logVdbg("setSafeModeAlarm: safe mode disabled");
            return;
        }
        logVdbg("Setting safe mode alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mSafeModeTimeoutAlarm != null) {
            return;
        }
        this.mSafeModeTimeoutAlarm = createScheduledAlarm(SAFEMODE_TIMEOUT_ALARM, obtainMessage(10, Integer.MIN_VALUE), getSafeModeTimeoutMs(this.mVcnContext, this.mLastSnapshot, this.mSubscriptionGroup));
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static long getSafeModeTimeoutMs(com.android.server.vcn.VcnContext vcnContext, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, android.os.ParcelUuid parcelUuid) {
        int i;
        if (vcnContext.isInTestMode()) {
            i = 10;
        } else {
            i = 30;
        }
        com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
        if (vcnContext.isFlagSafeModeTimeoutConfigEnabled() && carrierConfigForSubGrp != null) {
            i = carrierConfigForSubGrp.getInt("vcn_safe_mode_timeout_seconds_key", i);
        }
        return java.util.concurrent.TimeUnit.SECONDS.toMillis(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelSafeModeAlarm() {
        logVdbg("Cancel safe mode alarm; mCurrentToken: " + this.mCurrentToken);
        if (this.mSafeModeTimeoutAlarm != null) {
            this.mSafeModeTimeoutAlarm.cancel();
            this.mSafeModeTimeoutAlarm = null;
        }
        removeEqualMessages(10);
    }

    private void sessionLostWithoutCallback(int i, @android.annotation.Nullable java.lang.Exception exc) {
        sendMessageAndAcquireWakeLock(3, i, new com.android.server.vcn.VcnGatewayConnection.EventSessionLostInfo(exc));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sessionLost(int i, @android.annotation.Nullable java.lang.Exception exc) {
        if (exc != null) {
            this.mGatewayStatusCallback.onGatewayConnectionError(this.mConnectionConfig.getGatewayConnectionName(), 0, java.lang.RuntimeException.class.getName(), "Received " + exc.getClass().getSimpleName() + " with message: " + exc.getMessage());
        }
        sessionLostWithoutCallback(i, exc);
    }

    private static boolean isIkeAuthFailure(@android.annotation.NonNull java.lang.Exception exc) {
        return (exc instanceof android.net.ipsec.ike.exceptions.IkeProtocolException) && ((android.net.ipsec.ike.exceptions.IkeProtocolException) exc).getErrorType() == 24;
    }

    private void notifyStatusCallbackForSessionClosed(@android.annotation.NonNull java.lang.Exception exc) {
        java.lang.String name;
        java.lang.String str;
        int i;
        if (isIkeAuthFailure(exc)) {
            name = exc.getClass().getName();
            str = exc.getMessage();
            i = 1;
        } else if ((exc instanceof android.net.ipsec.ike.exceptions.IkeInternalException) && (exc.getCause() instanceof java.io.IOException)) {
            name = java.io.IOException.class.getName();
            str = exc.getCause().getMessage();
            i = 2;
        } else {
            name = java.lang.RuntimeException.class.getName();
            str = "Received " + exc.getClass().getSimpleName() + " with message: " + exc.getMessage();
            i = 0;
        }
        logDbg("Encountered error; code=" + i + ", exceptionClass=" + name + ", exceptionMessage=" + str);
        this.mGatewayStatusCallback.onGatewayConnectionError(this.mConnectionConfig.getGatewayConnectionName(), i, name, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ikeConnectionInfoChanged(int i, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
        sendMessageAndAcquireWakeLock(12, i, new com.android.server.vcn.VcnGatewayConnection.EventIkeConnectionInfoChangedInfo(ikeSessionConnectionInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sessionClosed(int i, @android.annotation.Nullable java.lang.Exception exc) {
        if (exc != null) {
            notifyStatusCallbackForSessionClosed(exc);
        }
        sessionLostWithoutCallback(i, exc);
        sendMessageAndAcquireWakeLock(4, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void migrationCompleted(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2) {
        sendMessageAndAcquireWakeLock(11, i, new com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo(ipSecTransform, ipSecTransform2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void childTransformCreated(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i2) {
        sendMessageAndAcquireWakeLock(5, i, new com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo(i2, ipSecTransform));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void childOpened(int i, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration) {
        sendMessageAndAcquireWakeLock(6, i, new com.android.server.vcn.VcnGatewayConnection.EventSetupCompletedInfo(vcnChildSessionConfiguration));
    }

    private abstract class BaseState extends com.android.internal.util.State {
        protected abstract void processStateMsg(android.os.Message message) throws java.lang.Exception;

        private BaseState() {
        }

        public void enter() {
            try {
                enterState();
            } catch (java.lang.Exception e) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Uncaught exception", e);
                com.android.server.vcn.VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_INTERNAL_ERROR + e.toString(), true);
            }
        }

        protected void enterState() throws java.lang.Exception {
        }

        protected boolean isValidToken(int i) {
            return true;
        }

        public final boolean processMessage(android.os.Message message) {
            int i = message.arg1;
            if (!isValidToken(i)) {
                com.android.server.vcn.VcnGatewayConnection.this.logDbg("Message called with obsolete token: " + i + "; what: " + message.what);
                return true;
            }
            try {
                processStateMsg(message);
            } catch (java.lang.Exception e) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Uncaught exception", e);
                com.android.server.vcn.VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_INTERNAL_ERROR + e.toString(), true);
            }
            com.android.server.vcn.VcnGatewayConnection.this.maybeReleaseWakeLock();
            return true;
        }

        public void exit() {
            try {
                exitState();
            } catch (java.lang.Exception e) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Uncaught exception", e);
                com.android.server.vcn.VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_INTERNAL_ERROR + e.toString(), true);
            }
        }

        protected void exitState() throws java.lang.Exception {
        }

        protected void logUnhandledMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    logUnexpectedEvent(message.what);
                    break;
                default:
                    logWtfUnknownEvent(message.what);
                    break;
            }
        }

        protected void teardownNetwork() {
            if (com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent != null) {
                com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent.unregister();
                com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent = null;
            }
        }

        protected void handleDisconnectRequested(com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo eventDisconnectRequestedInfo) {
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("Tearing down. Cause: " + eventDisconnectRequestedInfo.reason + "; quitting = " + eventDisconnectRequestedInfo.shouldQuit);
            if (eventDisconnectRequestedInfo.shouldQuit) {
                com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.setTrue();
            }
            teardownNetwork();
            if (com.android.server.vcn.VcnGatewayConnection.this.mIkeSession == null) {
                com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectedState);
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
            }
        }

        protected void handleSafeModeTimeoutExceeded() {
            com.android.server.vcn.VcnGatewayConnection.this.mSafeModeTimeoutAlarm = null;
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("Entering safe mode after timeout exceeded");
            teardownNetwork();
            com.android.server.vcn.VcnGatewayConnection.this.mIsInSafeMode = true;
            com.android.server.vcn.VcnGatewayConnection.this.mGatewayStatusCallback.onSafeModeStatusChanged();
        }

        protected void logUnexpectedEvent(int i) {
            com.android.server.vcn.VcnGatewayConnection.this.logVdbg("Unexpected event code " + i + " in state " + getClass().getSimpleName());
        }

        protected void logWtfUnknownEvent(int i) {
            com.android.server.vcn.VcnGatewayConnection.this.logWtf("Unknown event code " + i + " in state " + getClass().getSimpleName());
        }
    }

    private class DisconnectedState extends com.android.server.vcn.VcnGatewayConnection.BaseState {
        private DisconnectedState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void enterState() {
            if (com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.getValue()) {
                com.android.server.vcn.VcnGatewayConnection.this.quitNow();
            }
            if (com.android.server.vcn.VcnGatewayConnection.this.mIkeSession != null || com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent != null) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Active IKE Session or NetworkAgent in DisconnectedState");
            }
            com.android.server.vcn.VcnGatewayConnection.this.cancelSafeModeAlarm();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void processStateMsg(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.vcn.VcnGatewayConnection.this.mUnderlying = ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                    if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying != null) {
                        com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mConnectingState);
                        break;
                    }
                    break;
                case 7:
                    if (((com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) message.obj).shouldQuit) {
                        com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.setTrue();
                        com.android.server.vcn.VcnGatewayConnection.this.quitNow();
                        break;
                    }
                    break;
                default:
                    logUnhandledMessage(message);
                    break;
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void exitState() {
            com.android.server.vcn.VcnGatewayConnection.this.setSafeModeAlarm();
        }
    }

    private abstract class ActiveBaseState extends com.android.server.vcn.VcnGatewayConnection.BaseState {
        private ActiveBaseState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected boolean isValidToken(int i) {
            return i == Integer.MIN_VALUE || i == com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken;
        }
    }

    private class DisconnectingState extends com.android.server.vcn.VcnGatewayConnection.ActiveBaseState {
        private boolean mSkipRetryTimeout;

        private DisconnectingState() {
            super();
            this.mSkipRetryTimeout = false;
        }

        public void setSkipRetryTimeout(boolean z) {
            this.mSkipRetryTimeout = z;
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void enterState() throws java.lang.Exception {
            if (com.android.server.vcn.VcnGatewayConnection.this.mIkeSession == null) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("IKE session was already closed when entering Disconnecting state.");
                com.android.server.vcn.VcnGatewayConnection.this.sendMessageAndAcquireWakeLock(4, com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken);
            } else if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null) {
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.kill();
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.close();
                com.android.server.vcn.VcnGatewayConnection.this.setTeardownTimeoutAlarm();
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void processStateMsg(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.vcn.VcnGatewayConnection.this.mUnderlying = ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                    if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying != null) {
                        return;
                    }
                    break;
                case 4:
                    com.android.server.vcn.VcnGatewayConnection.this.mIkeSession = null;
                    if (!com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.getValue() && com.android.server.vcn.VcnGatewayConnection.this.mUnderlying != null) {
                        com.android.server.vcn.VcnGatewayConnection.this.transitionTo(this.mSkipRetryTimeout ? com.android.server.vcn.VcnGatewayConnection.this.mConnectingState : com.android.server.vcn.VcnGatewayConnection.this.mRetryTimeoutState);
                        return;
                    } else {
                        teardownNetwork();
                        com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectedState);
                        return;
                    }
                case 7:
                    com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo eventDisconnectRequestedInfo = (com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) message.obj;
                    if (eventDisconnectRequestedInfo.shouldQuit) {
                        com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.setTrue();
                    }
                    teardownNetwork();
                    if (eventDisconnectRequestedInfo.reason.equals(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_UNDERLYING_NETWORK_LOST)) {
                        com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.kill();
                        return;
                    }
                    return;
                case 8:
                    break;
                case 10:
                    handleSafeModeTimeoutExceeded();
                    return;
                default:
                    logUnhandledMessage(message);
                    return;
            }
            com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.kill();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void exitState() throws java.lang.Exception {
            this.mSkipRetryTimeout = false;
            com.android.server.vcn.VcnGatewayConnection.this.cancelTeardownTimeoutAlarm();
        }
    }

    private class ConnectingState extends com.android.server.vcn.VcnGatewayConnection.ActiveBaseState {
        private ConnectingState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void enterState() {
            if (com.android.server.vcn.VcnGatewayConnection.this.mIkeSession != null) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("ConnectingState entered with active session");
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.kill();
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession = null;
            }
            com.android.server.vcn.VcnGatewayConnection.this.mIkeSession = com.android.server.vcn.VcnGatewayConnection.this.buildIkeSession(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void processStateMsg(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord = com.android.server.vcn.VcnGatewayConnection.this.mUnderlying;
                    com.android.server.vcn.VcnGatewayConnection.this.mUnderlying = ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                    if (underlyingNetworkRecord == null) {
                        com.android.server.vcn.VcnGatewayConnection.this.logWtf("Old underlying network was null in connected state. Bug?");
                    }
                    if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null) {
                        com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
                        return;
                    } else if (underlyingNetworkRecord == null || !com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network.equals(underlyingNetworkRecord.network)) {
                        com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState.setSkipRetryTimeout(true);
                        break;
                    } else {
                        return;
                    }
                    break;
                case 2:
                case 8:
                case 9:
                case 11:
                default:
                    logUnhandledMessage(message);
                    return;
                case 3:
                    break;
                case 4:
                    com.android.server.vcn.VcnGatewayConnection.this.deferMessage(message);
                    com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
                    return;
                case 5:
                case 6:
                case 12:
                    com.android.server.vcn.VcnGatewayConnection.this.deferMessage(message);
                    com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mConnectedState);
                    return;
                case 7:
                    handleDisconnectRequested((com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) message.obj);
                    return;
                case 10:
                    handleSafeModeTimeoutExceeded();
                    return;
            }
            com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    abstract class ConnectedStateBase extends com.android.server.vcn.VcnGatewayConnection.ActiveBaseState {
        private ConnectedStateBase() {
            super();
        }

        protected void updateNetworkAgent(@android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            android.net.NetworkCapabilities buildNetworkCapabilities = com.android.server.vcn.VcnGatewayConnection.buildNetworkCapabilities(com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, com.android.server.vcn.VcnGatewayConnection.this.mIsMobileDataEnabled);
            android.net.LinkProperties buildConnectedLinkProperties = com.android.server.vcn.VcnGatewayConnection.this.buildConnectedLinkProperties(com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig, ipSecTunnelInterface, vcnChildSessionConfiguration, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, ikeSessionConnectionInfo);
            vcnNetworkAgent.sendNetworkCapabilities(buildNetworkCapabilities);
            vcnNetworkAgent.sendLinkProperties(buildConnectedLinkProperties);
            vcnNetworkAgent.setUnderlyingNetworks(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null ? null : java.util.Collections.singletonList(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network));
        }

        protected com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent buildNetworkAgent(@android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent newNetworkAgent = com.android.server.vcn.VcnGatewayConnection.this.mDeps.newNetworkAgent(com.android.server.vcn.VcnGatewayConnection.this.mVcnContext, com.android.server.vcn.VcnGatewayConnection.TAG, com.android.server.vcn.VcnGatewayConnection.buildNetworkCapabilities(com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, com.android.server.vcn.VcnGatewayConnection.this.mIsMobileDataEnabled), com.android.server.vcn.VcnGatewayConnection.this.buildConnectedLinkProperties(com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig, ipSecTunnelInterface, vcnChildSessionConfiguration, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, ikeSessionConnectionInfo), com.android.server.vcn.Vcn.getNetworkScore(), new android.net.NetworkAgentConfig.Builder().setLegacyType(0).setLegacyTypeName(com.android.server.vcn.VcnGatewayConnection.NETWORK_INFO_NETWORK_TYPE_STRING).setLegacySubType(0).setLegacySubTypeName(android.telephony.TelephonyManager.getNetworkTypeName(0)).setLegacyExtraInfo(com.android.server.vcn.VcnGatewayConnection.NETWORK_INFO_EXTRA_INFO).build(), com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.getVcnNetworkProvider(), new java.util.function.Consumer() { // from class: com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.vcn.VcnGatewayConnection.ConnectedStateBase.this.lambda$buildNetworkAgent$0((com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.vcn.VcnGatewayConnection.ConnectedStateBase.this.lambda$buildNetworkAgent$1((java.lang.Integer) obj);
                }
            });
            newNetworkAgent.register();
            newNetworkAgent.markConnected();
            return newNetworkAgent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$buildNetworkAgent$0(com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent) {
            if (com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent != vcnNetworkAgent) {
                com.android.server.vcn.VcnGatewayConnection.this.logDbg("unwanted() called on stale NetworkAgent");
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo(com.android.server.vcn.VcnGatewayConnection.DISCONNECT_REASON_NETWORK_AGENT_UNWANTED);
                com.android.server.vcn.VcnGatewayConnection.this.teardownAsynchronously();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$buildNetworkAgent$1(java.lang.Integer num) {
            if (com.android.server.vcn.VcnGatewayConnection.this.mIsQuitting.getValue()) {
            }
            switch (num.intValue()) {
                case 1:
                    clearFailedAttemptCounterAndSafeModeAlarm();
                    break;
                case 2:
                    if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying != null) {
                        com.android.server.vcn.VcnGatewayConnection.this.mConnectivityManager.reportNetworkConnectivity(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network, false);
                    }
                    com.android.server.vcn.VcnGatewayConnection.this.setSafeModeAlarm();
                    break;
                default:
                    com.android.server.vcn.VcnGatewayConnection.this.logWtf("Unknown validation status " + num + "; ignoring");
                    break;
            }
        }

        protected void clearFailedAttemptCounterAndSafeModeAlarm() {
            com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.ensureRunningOnLooperThread();
            com.android.server.vcn.VcnGatewayConnection.this.mFailedAttempts = 0;
            com.android.server.vcn.VcnGatewayConnection.this.cancelSafeModeAlarm();
            com.android.server.vcn.VcnGatewayConnection.this.mIsInSafeMode = false;
            com.android.server.vcn.VcnGatewayConnection.this.mGatewayStatusCallback.onSafeModeStatusChanged();
        }

        protected void applyTransform(int i, @android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i2) {
            if (i2 != 0 && i2 != 1) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Applying transform for unexpected direction: " + i2);
            }
            try {
                ipSecTunnelInterface.setUnderlyingNetwork(network);
                com.android.server.vcn.VcnGatewayConnection.this.mIpSecManager.applyTunnelModeTransform(ipSecTunnelInterface, i2, ipSecTransform);
                if (i2 == 0 && com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.isFlagNetworkMetricMonitorEnabled() && com.android.server.vcn.VcnGatewayConnection.this.mVcnContext.isFlagIpSecTransformStateEnabled()) {
                    com.android.server.vcn.VcnGatewayConnection.this.mUnderlyingNetworkController.updateInboundTransform(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying, ipSecTransform);
                }
                java.util.Set allExposedCapabilities = com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig.getAllExposedCapabilities();
                if (i2 == 0 && allExposedCapabilities.contains(2)) {
                    com.android.server.vcn.VcnGatewayConnection.this.mIpSecManager.applyTunnelModeTransform(ipSecTunnelInterface, 2, ipSecTransform);
                }
            } catch (java.io.IOException | java.lang.IllegalArgumentException e) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("Transform application failed for network " + i, e);
                com.android.server.vcn.VcnGatewayConnection.this.sessionLost(i, e);
            }
        }

        protected void setupInterface(int i, @android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration, @android.annotation.Nullable com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration2) {
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet(vcnChildSessionConfiguration.getInternalAddresses());
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                if (vcnChildSessionConfiguration2 != null) {
                    arraySet2.addAll(vcnChildSessionConfiguration2.getInternalAddresses());
                }
                android.util.ArraySet<android.net.LinkAddress> arraySet3 = new android.util.ArraySet();
                arraySet3.addAll((java.util.Collection) arraySet);
                arraySet3.removeAll((java.util.Collection<?>) arraySet2);
                android.util.ArraySet<android.net.LinkAddress> arraySet4 = new android.util.ArraySet();
                arraySet4.addAll((java.util.Collection) arraySet2);
                arraySet4.removeAll((java.util.Collection<?>) arraySet);
                for (android.net.LinkAddress linkAddress : arraySet3) {
                    ipSecTunnelInterface.addAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                }
                for (android.net.LinkAddress linkAddress2 : arraySet4) {
                    ipSecTunnelInterface.removeAddress(linkAddress2.getAddress(), linkAddress2.getPrefixLength());
                }
            } catch (java.io.IOException e) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("Adding address to tunnel failed for token " + i, e);
                com.android.server.vcn.VcnGatewayConnection.this.sessionLost(i, e);
            }
        }
    }

    class ConnectedState extends com.android.server.vcn.VcnGatewayConnection.ConnectedStateBase {
        ConnectedState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void enterState() throws java.lang.Exception {
            if (com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface == null) {
                try {
                    com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface = com.android.server.vcn.VcnGatewayConnection.this.mIpSecManager.createIpSecTunnelInterface(com.android.server.vcn.VcnGatewayConnection.DUMMY_ADDR, com.android.server.vcn.VcnGatewayConnection.DUMMY_ADDR, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
                } catch (android.net.IpSecManager.ResourceUnavailableException | java.io.IOException e) {
                    com.android.server.vcn.VcnGatewayConnection.this.teardownAsynchronously();
                }
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void processStateMsg(android.os.Message message) {
            switch (message.what) {
                case 1:
                    handleUnderlyingNetworkChanged(message);
                    break;
                case 2:
                case 8:
                case 9:
                default:
                    logUnhandledMessage(message);
                    break;
                case 3:
                    com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
                    break;
                case 4:
                    com.android.server.vcn.VcnGatewayConnection.this.deferMessage(message);
                    com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectingState);
                    break;
                case 5:
                    com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo eventTransformCreatedInfo = (com.android.server.vcn.VcnGatewayConnection.EventTransformCreatedInfo) message.obj;
                    applyTransform(com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken, com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network, eventTransformCreatedInfo.transform, eventTransformCreatedInfo.direction);
                    break;
                case 6:
                    com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration = com.android.server.vcn.VcnGatewayConnection.this.mChildConfig;
                    com.android.server.vcn.VcnGatewayConnection.this.mChildConfig = ((com.android.server.vcn.VcnGatewayConnection.EventSetupCompletedInfo) message.obj).childSessionConfig;
                    setupInterfaceAndNetworkAgent(com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken, com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mChildConfig, vcnChildSessionConfiguration, com.android.server.vcn.VcnGatewayConnection.this.mIkeConnectionInfo);
                    int parallelTunnelCount = com.android.server.vcn.VcnGatewayConnection.this.mDeps.getParallelTunnelCount(com.android.server.vcn.VcnGatewayConnection.this.mLastSnapshot, com.android.server.vcn.VcnGatewayConnection.this.mSubscriptionGroup);
                    com.android.server.vcn.VcnGatewayConnection.this.logInfo("Parallel tunnel count: " + parallelTunnelCount);
                    for (int i = 0; i < parallelTunnelCount - 1; i++) {
                        com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.openChildSession(com.android.server.vcn.VcnGatewayConnection.this.buildOpportunisticChildParams(), com.android.server.vcn.VcnGatewayConnection.this.new VcnChildSessionCallback(com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken, true));
                    }
                    break;
                case 7:
                    handleDisconnectRequested((com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) message.obj);
                    break;
                case 10:
                    handleSafeModeTimeoutExceeded();
                    break;
                case 11:
                    handleMigrationCompleted((com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo) message.obj);
                    break;
                case 12:
                    com.android.server.vcn.VcnGatewayConnection.this.mIkeConnectionInfo = ((com.android.server.vcn.VcnGatewayConnection.EventIkeConnectionInfoChangedInfo) message.obj).ikeConnectionInfo;
                    break;
                case 13:
                    handleDataStallSuspected(((com.android.server.vcn.VcnGatewayConnection.EventDataStallSuspectedInfo) message.obj).network);
                    break;
            }
        }

        private void handleMigrationCompleted(com.android.server.vcn.VcnGatewayConnection.EventMigrationCompletedInfo eventMigrationCompletedInfo) {
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("Migration completed: " + com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
            applyTransform(com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken, com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network, eventMigrationCompletedInfo.inTransform, 0);
            applyTransform(com.android.server.vcn.VcnGatewayConnection.this.mCurrentToken, com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network, eventMigrationCompletedInfo.outTransform, 1);
            updateNetworkAgent(com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent, com.android.server.vcn.VcnGatewayConnection.this.mChildConfig, com.android.server.vcn.VcnGatewayConnection.this.mIkeConnectionInfo);
            com.android.server.vcn.VcnGatewayConnection.this.mConnectivityManager.reportNetworkConnectivity(com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent.getNetwork(), false);
        }

        private void handleUnderlyingNetworkChanged(@android.annotation.NonNull android.os.Message message) {
            com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord = com.android.server.vcn.VcnGatewayConnection.this.mUnderlying;
            com.android.server.vcn.VcnGatewayConnection.this.mUnderlying = ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
            if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("Underlying network lost");
                return;
            }
            if (underlyingNetworkRecord == null || !underlyingNetworkRecord.network.equals(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network)) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("Migrating to new network: " + com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.setNetwork(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
                return;
            }
            if (com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent != null && com.android.server.vcn.VcnGatewayConnection.this.mChildConfig != null) {
                updateNetworkAgent(com.android.server.vcn.VcnGatewayConnection.this.mTunnelIface, com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent, com.android.server.vcn.VcnGatewayConnection.this.mChildConfig, com.android.server.vcn.VcnGatewayConnection.this.mIkeConnectionInfo);
            }
        }

        private void handleDataStallSuspected(android.net.Network network) {
            if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying != null && com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent != null && com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent.getNetwork().equals(network)) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("Perform Mobility update to recover from suspected data stall");
                com.android.server.vcn.VcnGatewayConnection.this.mIkeSession.setNetwork(com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network);
            }
        }

        protected void setupInterfaceAndNetworkAgent(int i, @android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration2, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            setupInterface(i, ipSecTunnelInterface, vcnChildSessionConfiguration, vcnChildSessionConfiguration2);
            if (com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent == null) {
                com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent = buildNetworkAgent(ipSecTunnelInterface, vcnChildSessionConfiguration, ikeSessionConnectionInfo);
            } else {
                updateNetworkAgent(ipSecTunnelInterface, com.android.server.vcn.VcnGatewayConnection.this.mNetworkAgent, vcnChildSessionConfiguration, ikeSessionConnectionInfo);
                clearFailedAttemptCounterAndSafeModeAlarm();
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void exitState() {
            com.android.server.vcn.VcnGatewayConnection.this.setSafeModeAlarm();
        }
    }

    class RetryTimeoutState extends com.android.server.vcn.VcnGatewayConnection.ActiveBaseState {
        RetryTimeoutState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void enterState() throws java.lang.Exception {
            com.android.server.vcn.VcnGatewayConnection.this.mFailedAttempts++;
            if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null) {
                com.android.server.vcn.VcnGatewayConnection.this.logWtf("Underlying network was null in retry state");
                teardownNetwork();
                com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectedState);
                return;
            }
            com.android.server.vcn.VcnGatewayConnection.this.setRetryTimeoutAlarm(getNextRetryIntervalsMs());
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        protected void processStateMsg(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord = com.android.server.vcn.VcnGatewayConnection.this.mUnderlying;
                    com.android.server.vcn.VcnGatewayConnection.this.mUnderlying = ((com.android.server.vcn.VcnGatewayConnection.EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                    if (com.android.server.vcn.VcnGatewayConnection.this.mUnderlying == null) {
                        teardownNetwork();
                        com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mDisconnectedState);
                        return;
                    } else if (underlyingNetworkRecord != null && com.android.server.vcn.VcnGatewayConnection.this.mUnderlying.network.equals(underlyingNetworkRecord.network)) {
                        return;
                    }
                    break;
                case 2:
                    break;
                case 7:
                    handleDisconnectRequested((com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo) message.obj);
                    return;
                case 10:
                    handleSafeModeTimeoutExceeded();
                    return;
                default:
                    logUnhandledMessage(message);
                    return;
            }
            com.android.server.vcn.VcnGatewayConnection.this.transitionTo(com.android.server.vcn.VcnGatewayConnection.this.mConnectingState);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public void exitState() {
            com.android.server.vcn.VcnGatewayConnection.this.cancelRetryTimeoutAlarm();
        }

        private long getNextRetryIntervalsMs() {
            int i = com.android.server.vcn.VcnGatewayConnection.this.mFailedAttempts - 1;
            long[] retryIntervalsMillis = com.android.server.vcn.VcnGatewayConnection.this.mConnectionConfig.getRetryIntervalsMillis();
            if (i >= retryIntervalsMillis.length) {
                return retryIntervalsMillis[retryIntervalsMillis.length - 1];
            }
            return retryIntervalsMillis[i];
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static android.net.NetworkCapabilities buildNetworkCapabilities(@android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, boolean z) {
        android.net.NetworkCapabilities.Builder builder = new android.net.NetworkCapabilities.Builder();
        builder.addTransportType(0);
        builder.addCapability(28);
        builder.addCapability(20);
        builder.addCapability(21);
        java.util.Iterator it = vcnGatewayConnectionConfig.getAllExposedCapabilities().iterator();
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            if (z || (intValue != 12 && intValue != 2)) {
                builder.addCapability(intValue);
            }
        }
        if (underlyingNetworkRecord != null) {
            android.net.NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
            for (int i : MERGED_CAPABILITIES) {
                if (networkCapabilities.hasCapability(i)) {
                    builder.addCapability(i);
                }
            }
            int[] administratorUids = networkCapabilities.getAdministratorUids();
            java.util.Arrays.sort(administratorUids);
            if (networkCapabilities.getOwnerUid() > 0 && java.util.Arrays.binarySearch(administratorUids, networkCapabilities.getOwnerUid()) < 0) {
                administratorUids = java.util.Arrays.copyOf(administratorUids, administratorUids.length + 1);
                administratorUids[administratorUids.length - 1] = networkCapabilities.getOwnerUid();
                java.util.Arrays.sort(administratorUids);
            }
            builder.setOwnerUid(android.os.Process.myUid());
            int[] copyOf = java.util.Arrays.copyOf(administratorUids, administratorUids.length + 1);
            copyOf[copyOf.length - 1] = android.os.Process.myUid();
            builder.setAdministratorUids(copyOf);
            builder.setLinkUpstreamBandwidthKbps(networkCapabilities.getLinkUpstreamBandwidthKbps());
            builder.setLinkDownstreamBandwidthKbps(networkCapabilities.getLinkDownstreamBandwidthKbps());
            if (!networkCapabilities.hasTransport(1) || !(networkCapabilities.getTransportInfo() instanceof android.net.wifi.WifiInfo)) {
                if (networkCapabilities.hasTransport(0) && (networkCapabilities.getNetworkSpecifier() instanceof android.net.TelephonyNetworkSpecifier)) {
                    builder.setTransportInfo(new android.net.vcn.VcnTransportInfo(((android.net.TelephonyNetworkSpecifier) networkCapabilities.getNetworkSpecifier()).getSubscriptionId(), vcnGatewayConnectionConfig.getMinUdpPort4500NatTimeoutSeconds()));
                } else {
                    android.util.Slog.wtf(TAG, "Unknown transport type or missing TransportInfo/NetworkSpecifier for non-null underlying network");
                }
            } else {
                builder.setTransportInfo(new android.net.vcn.VcnTransportInfo((android.net.wifi.WifiInfo) networkCapabilities.getTransportInfo(), vcnGatewayConnectionConfig.getMinUdpPort4500NatTimeoutSeconds()));
            }
            builder.setUnderlyingNetworks(java.util.List.of(underlyingNetworkRecord.network));
        } else {
            android.util.Slog.wtf(TAG, "No underlying network while building network capabilities", new java.lang.IllegalStateException());
        }
        return builder.build();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    android.net.LinkProperties buildConnectedLinkProperties(@android.annotation.NonNull android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, @android.annotation.NonNull android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, @android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration, @android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
        android.net.ipsec.ike.IkeTunnelConnectionParams tunnelConnectionParams = vcnGatewayConnectionConfig.getTunnelConnectionParams();
        android.net.LinkProperties linkProperties = new android.net.LinkProperties();
        linkProperties.setInterfaceName(ipSecTunnelInterface.getInterfaceName());
        java.util.Iterator<android.net.LinkAddress> it = vcnChildSessionConfiguration.getInternalAddresses().iterator();
        while (it.hasNext()) {
            linkProperties.addLinkAddress(it.next());
        }
        java.util.Iterator<java.net.InetAddress> it2 = vcnChildSessionConfiguration.getInternalDnsServers().iterator();
        while (it2.hasNext()) {
            linkProperties.addDnsServer(it2.next());
        }
        int i = 0;
        linkProperties.addRoute(new android.net.RouteInfo(new android.net.IpPrefix(java.net.Inet4Address.ANY, 0), null, null, 1));
        linkProperties.addRoute(new android.net.RouteInfo(new android.net.IpPrefix(java.net.Inet6Address.ANY, 0), null, null, 1));
        if (underlyingNetworkRecord != null) {
            android.net.LinkProperties linkProperties2 = underlyingNetworkRecord.linkProperties;
            linkProperties.setTcpBufferSizes(linkProperties2.getTcpBufferSizes());
            int mtu = linkProperties2.getMtu();
            if (mtu == 0 && linkProperties2.getInterfaceName() != null) {
                i = this.mDeps.getUnderlyingIfaceMtu(linkProperties2.getInterfaceName());
            } else {
                i = mtu;
            }
        } else {
            android.util.Slog.wtf(TAG, "No underlying network while building link properties", new java.lang.IllegalStateException());
        }
        linkProperties.setMtu(com.android.server.vcn.util.MtuUtils.getMtu(tunnelConnectionParams.getTunnelModeChildSessionParams().getSaProposals(), vcnGatewayConnectionConfig.getMaxMtu(), i, ikeSessionConnectionInfo.getLocalAddress() instanceof java.net.Inet4Address));
        return linkProperties;
    }

    private class IkeSessionCallbackImpl implements android.net.ipsec.ike.IkeSessionCallback {
        private final int mToken;

        IkeSessionCallbackImpl(int i) {
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public void onOpened(@android.annotation.NonNull android.net.ipsec.ike.IkeSessionConfiguration ikeSessionConfiguration) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("IkeOpened for token " + this.mToken);
            com.android.server.vcn.VcnGatewayConnection.this.ikeConnectionInfoChanged(this.mToken, ikeSessionConfiguration.getIkeSessionConnectionInfo());
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public void onClosed() {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("IkeClosed for token " + this.mToken);
            com.android.server.vcn.VcnGatewayConnection.this.sessionClosed(this.mToken, null);
        }

        public void onClosedExceptionally(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeException ikeException) {
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("IkeClosedExceptionally for token " + this.mToken, ikeException);
            com.android.server.vcn.VcnGatewayConnection.this.sessionClosed(this.mToken, ikeException);
        }

        public void onError(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeProtocolException ikeProtocolException) {
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("IkeError for token " + this.mToken, ikeProtocolException);
        }

        public void onIkeSessionConnectionInfoChanged(@android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("onIkeSessionConnectionInfoChanged for token " + this.mToken);
            com.android.server.vcn.VcnGatewayConnection.this.ikeConnectionInfoChanged(this.mToken, ikeSessionConnectionInfo);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public class VcnChildSessionCallback implements android.net.ipsec.ike.ChildSessionCallback {
        private boolean mIsChildOpened;
        private final boolean mIsOpportunistic;
        private final int mToken;

        VcnChildSessionCallback(com.android.server.vcn.VcnGatewayConnection vcnGatewayConnection, int i) {
            this(i, false);
        }

        VcnChildSessionCallback(int i, boolean z) {
            this.mIsChildOpened = false;
            this.mToken = i;
            this.mIsOpportunistic = z;
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        void onOpened(@android.annotation.NonNull com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration vcnChildSessionConfiguration) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildOpened for token " + this.mToken);
            if (this.mIsOpportunistic) {
                com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildOpened for opportunistic child; suppressing event message");
                this.mIsChildOpened = true;
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.childOpened(this.mToken, vcnChildSessionConfiguration);
            }
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onOpened(@android.annotation.NonNull android.net.ipsec.ike.ChildSessionConfiguration childSessionConfiguration) {
            onOpened(new com.android.server.vcn.VcnGatewayConnection.VcnChildSessionConfiguration(childSessionConfiguration));
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onClosed() {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildClosed for token " + this.mToken);
            if (this.mIsOpportunistic && !this.mIsChildOpened) {
                com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildClosed for unopened opportunistic child; ignoring");
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.sessionLost(this.mToken, null);
            }
        }

        public void onClosedExceptionally(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeException ikeException) {
            com.android.server.vcn.VcnGatewayConnection.this.logInfo("ChildClosedExceptionally for token " + this.mToken, ikeException);
            if (this.mIsOpportunistic && !this.mIsChildOpened) {
                com.android.server.vcn.VcnGatewayConnection.this.logInfo("ChildClosedExceptionally for unopened opportunistic child; ignoring");
            } else {
                com.android.server.vcn.VcnGatewayConnection.this.sessionLost(this.mToken, ikeException);
            }
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onIpSecTransformCreated(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildTransformCreated; Direction: " + i + "; token " + this.mToken);
            com.android.server.vcn.VcnGatewayConnection.this.childTransformCreated(this.mToken, ipSecTransform, i);
        }

        public void onIpSecTransformsMigrated(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildTransformsMigrated; token " + this.mToken);
            com.android.server.vcn.VcnGatewayConnection.this.migrationCompleted(this.mToken, ipSecTransform, ipSecTransform2);
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onIpSecTransformDeleted(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i) {
            com.android.server.vcn.VcnGatewayConnection.this.logDbg("ChildTransformDeleted; Direction: " + i + "; for token " + this.mToken);
        }
    }

    public java.lang.String getLogPrefix() {
        return "(" + com.android.server.vcn.util.LogUtils.getHashedSubscriptionGroup(this.mSubscriptionGroup) + "-" + this.mConnectionConfig.getGatewayConnectionName() + "-" + java.lang.System.identityHashCode(this) + ") ";
    }

    private java.lang.String getTagLogPrefix() {
        return "[ " + TAG + " " + getLogPrefix() + "]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logVdbg(java.lang.String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logDbg(java.lang.String str) {
        android.util.Slog.d(TAG, getLogPrefix() + str);
    }

    private void logDbg(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.d(TAG, getLogPrefix() + str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logInfo(java.lang.String str) {
        android.util.Slog.i(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO] " + getTagLogPrefix() + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logInfo(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.i(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO] " + getTagLogPrefix() + str + th);
    }

    private void logWarn(java.lang.String str) {
        android.util.Slog.w(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WARN] " + getTagLogPrefix() + str);
    }

    private void logWarn(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.w(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WARN] " + getTagLogPrefix() + str + th);
    }

    private void logErr(java.lang.String str) {
        android.util.Slog.e(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[ERR ] " + getTagLogPrefix() + str);
    }

    private void logErr(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.e(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[ERR ] " + getTagLogPrefix() + str + th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WTF ] " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWtf(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, getLogPrefix() + str, th);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WTF ] " + str + th);
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.String simpleName;
        indentingPrintWriter.println("VcnGatewayConnection (" + this.mConnectionConfig.getGatewayConnectionName() + "):");
        indentingPrintWriter.increaseIndent();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Current state: ");
        if (getCurrentState() == null) {
            simpleName = null;
        } else {
            simpleName = getCurrentState().getClass().getSimpleName();
        }
        sb.append(simpleName);
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("mIsQuitting: " + this.mIsQuitting.getValue());
        indentingPrintWriter.println("mIsInSafeMode: " + this.mIsInSafeMode);
        indentingPrintWriter.println("mCurrentToken: " + this.mCurrentToken);
        indentingPrintWriter.println("mFailedAttempts: " + this.mFailedAttempts);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("mNetworkAgent.getNetwork(): ");
        sb2.append(this.mNetworkAgent != null ? this.mNetworkAgent.getNetwork() : null);
        indentingPrintWriter.println(sb2.toString());
        indentingPrintWriter.println();
        this.mUnderlyingNetworkController.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setTunnelInterface(android.net.IpSecManager.IpSecTunnelInterface ipSecTunnelInterface) {
        this.mTunnelIface = ipSecTunnelInterface;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback getUnderlyingNetworkControllerCallback() {
        return this.mUnderlyingNetworkControllerCallback;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback getConnectivityDiagnosticsCallback() {
        return this.mConnectivityDiagnosticsCallback;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.vcn.routeselection.UnderlyingNetworkRecord getUnderlyingNetwork() {
        return this.mUnderlying;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setUnderlyingNetwork(@android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord) {
        this.mUnderlying = underlyingNetworkRecord;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    android.net.ipsec.ike.IkeSessionConnectionInfo getIkeConnectionInfo() {
        return this.mIkeConnectionInfo;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    boolean isQuitting() {
        return this.mIsQuitting.getValue();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setQuitting() {
        this.mIsQuitting.setTrue();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.vcn.VcnGatewayConnection.VcnIkeSession getIkeSession() {
        return this.mIkeSession;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setIkeSession(@android.annotation.Nullable com.android.server.vcn.VcnGatewayConnection.VcnIkeSession vcnIkeSession) {
        this.mIkeSession = vcnIkeSession;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent getNetworkAgent() {
        return this.mNetworkAgent;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setNetworkAgent(@android.annotation.Nullable com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent) {
        this.mNetworkAgent = vcnNetworkAgent;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void sendDisconnectRequestedAndAcquireWakelock(java.lang.String str, boolean z) {
        sendMessageAndAcquireWakeLock(7, Integer.MIN_VALUE, new com.android.server.vcn.VcnGatewayConnection.EventDisconnectRequestedInfo(str, z));
    }

    private android.net.ipsec.ike.IkeSessionParams buildIkeParams(@android.annotation.NonNull android.net.Network network) {
        android.net.ipsec.ike.IkeSessionParams.Builder builder = new android.net.ipsec.ike.IkeSessionParams.Builder(this.mConnectionConfig.getTunnelConnectionParams().getIkeSessionParams());
        builder.setNetwork(network);
        return builder.build();
    }

    private android.net.ipsec.ike.ChildSessionParams buildChildParams() {
        return this.mConnectionConfig.getTunnelConnectionParams().getTunnelModeChildSessionParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.net.ipsec.ike.ChildSessionParams buildOpportunisticChildParams() {
        android.net.ipsec.ike.TunnelModeChildSessionParams tunnelModeChildSessionParams = this.mConnectionConfig.getTunnelConnectionParams().getTunnelModeChildSessionParams();
        android.net.ipsec.ike.TunnelModeChildSessionParams.Builder builder = new android.net.ipsec.ike.TunnelModeChildSessionParams.Builder();
        java.util.Iterator<android.net.ipsec.ike.ChildSaProposal> it = tunnelModeChildSessionParams.getChildSaProposals().iterator();
        while (it.hasNext()) {
            builder.addChildSaProposal(it.next());
        }
        java.util.Iterator<android.net.ipsec.ike.IkeTrafficSelector> it2 = tunnelModeChildSessionParams.getInboundTrafficSelectors().iterator();
        while (it2.hasNext()) {
            builder.addInboundTrafficSelectors(it2.next());
        }
        java.util.Iterator<android.net.ipsec.ike.IkeTrafficSelector> it3 = tunnelModeChildSessionParams.getOutboundTrafficSelectors().iterator();
        while (it3.hasNext()) {
            builder.addOutboundTrafficSelectors(it3.next());
        }
        builder.setLifetimeSeconds(tunnelModeChildSessionParams.getHardLifetimeSeconds(), tunnelModeChildSessionParams.getSoftLifetimeSeconds());
        return builder.build();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    com.android.server.vcn.VcnGatewayConnection.VcnIkeSession buildIkeSession(@android.annotation.NonNull android.net.Network network) {
        int i = this.mCurrentToken + 1;
        this.mCurrentToken = i;
        return this.mDeps.newIkeSession(this.mVcnContext, buildIkeParams(network), buildChildParams(), new com.android.server.vcn.VcnGatewayConnection.IkeSessionCallbackImpl(i), new com.android.server.vcn.VcnGatewayConnection.VcnChildSessionCallback(this, i));
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public com.android.server.vcn.routeselection.UnderlyingNetworkController newUnderlyingNetworkController(com.android.server.vcn.VcnContext vcnContext, android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig, android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, com.android.server.vcn.routeselection.UnderlyingNetworkController.UnderlyingNetworkControllerCallback underlyingNetworkControllerCallback) {
            return new com.android.server.vcn.routeselection.UnderlyingNetworkController(vcnContext, vcnGatewayConnectionConfig, parcelUuid, telephonySubscriptionSnapshot, underlyingNetworkControllerCallback);
        }

        public com.android.server.vcn.VcnGatewayConnection.VcnIkeSession newIkeSession(com.android.server.vcn.VcnContext vcnContext, android.net.ipsec.ike.IkeSessionParams ikeSessionParams, android.net.ipsec.ike.ChildSessionParams childSessionParams, android.net.ipsec.ike.IkeSessionCallback ikeSessionCallback, android.net.ipsec.ike.ChildSessionCallback childSessionCallback) {
            return new com.android.server.vcn.VcnGatewayConnection.VcnIkeSession(vcnContext, ikeSessionParams, childSessionParams, ikeSessionCallback, childSessionCallback);
        }

        public com.android.server.vcn.VcnGatewayConnection.VcnWakeLock newWakeLock(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str) {
            return new com.android.server.vcn.VcnGatewayConnection.VcnWakeLock(context, i, str);
        }

        public com.android.internal.util.WakeupMessage newWakeupMessage(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.Runnable runnable) {
            return new com.android.internal.util.WakeupMessage(vcnContext.getContext(), handler, str, runnable);
        }

        public com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent newNetworkAgent(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull android.net.LinkProperties linkProperties, @android.annotation.NonNull android.net.NetworkScore networkScore, @android.annotation.NonNull android.net.NetworkAgentConfig networkAgentConfig, @android.annotation.NonNull android.net.NetworkProvider networkProvider, @android.annotation.NonNull java.util.function.Consumer<com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent> consumer, @android.annotation.NonNull java.util.function.Consumer<java.lang.Integer> consumer2) {
            return new com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent(vcnContext, str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider, consumer, consumer2);
        }

        public boolean isAirplaneModeOn(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext) {
            return android.provider.Settings.Global.getInt(vcnContext.getContext().getContentResolver(), "airplane_mode_on", 0) != 0;
        }

        public long getElapsedRealTime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        public int getUnderlyingIfaceMtu(java.lang.String str) {
            try {
                java.net.NetworkInterface byName = java.net.NetworkInterface.getByName(str);
                return byName != null ? byName.getMTU() : 0;
            } catch (java.io.IOException e) {
                android.util.Slog.d(com.android.server.vcn.VcnGatewayConnection.TAG, "Could not get MTU of underlying network", e);
                return 0;
            }
        }

        public int getParallelTunnelCount(com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, android.os.ParcelUuid parcelUuid) {
            com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
            return java.lang.Math.max(1, carrierConfigForSubGrp != null ? carrierConfigForSubGrp.getInt("vcn_tunnel_aggregation_sa_count_max", 1) : 1);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class VcnChildSessionConfiguration {
        private final android.net.ipsec.ike.ChildSessionConfiguration mChildConfig;

        public VcnChildSessionConfiguration(android.net.ipsec.ike.ChildSessionConfiguration childSessionConfiguration) {
            this.mChildConfig = childSessionConfiguration;
        }

        public java.util.List<android.net.LinkAddress> getInternalAddresses() {
            return this.mChildConfig.getInternalAddresses();
        }

        public java.util.List<java.net.InetAddress> getInternalDnsServers() {
            return this.mChildConfig.getInternalDnsServers();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class VcnIkeSession {
        private final android.net.ipsec.ike.IkeSession mImpl;

        public VcnIkeSession(com.android.server.vcn.VcnContext vcnContext, android.net.ipsec.ike.IkeSessionParams ikeSessionParams, android.net.ipsec.ike.ChildSessionParams childSessionParams, android.net.ipsec.ike.IkeSessionCallback ikeSessionCallback, android.net.ipsec.ike.ChildSessionCallback childSessionCallback) {
            this.mImpl = new android.net.ipsec.ike.IkeSession(vcnContext.getContext(), ikeSessionParams, childSessionParams, new android.os.HandlerExecutor(new android.os.Handler(vcnContext.getLooper())), ikeSessionCallback, childSessionCallback);
        }

        public void openChildSession(@android.annotation.NonNull android.net.ipsec.ike.ChildSessionParams childSessionParams, @android.annotation.NonNull android.net.ipsec.ike.ChildSessionCallback childSessionCallback) {
            this.mImpl.openChildSession(childSessionParams, childSessionCallback);
        }

        public void closeChildSession(@android.annotation.NonNull android.net.ipsec.ike.ChildSessionCallback childSessionCallback) {
            this.mImpl.closeChildSession(childSessionCallback);
        }

        public void close() {
            this.mImpl.close();
        }

        public void kill() {
            this.mImpl.kill();
        }

        public void setNetwork(@android.annotation.NonNull android.net.Network network) {
            this.mImpl.setNetwork(network);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class VcnWakeLock {
        private final android.os.PowerManager.WakeLock mImpl;

        public VcnWakeLock(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str) {
            this.mImpl = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(i, str);
            this.mImpl.setReferenceCounted(false);
        }

        public synchronized void acquire() {
            this.mImpl.acquire();
        }

        public synchronized void release() {
            this.mImpl.release();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class VcnNetworkAgent {
        private final android.net.NetworkAgent mImpl;

        public VcnNetworkAgent(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull android.net.LinkProperties linkProperties, @android.annotation.NonNull android.net.NetworkScore networkScore, @android.annotation.NonNull android.net.NetworkAgentConfig networkAgentConfig, @android.annotation.NonNull android.net.NetworkProvider networkProvider, @android.annotation.NonNull final java.util.function.Consumer<com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent> consumer, @android.annotation.NonNull final java.util.function.Consumer<java.lang.Integer> consumer2) {
            this.mImpl = new android.net.NetworkAgent(vcnContext.getContext(), vcnContext.getLooper(), str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider) { // from class: com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent.1
                public void onNetworkUnwanted() {
                    consumer.accept(com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent.this);
                }

                public void onValidationStatus(int i, @android.annotation.Nullable android.net.Uri uri) {
                    consumer2.accept(java.lang.Integer.valueOf(i));
                }
            };
        }

        public void register() {
            this.mImpl.register();
        }

        public void markConnected() {
            this.mImpl.markConnected();
        }

        public void unregister() {
            this.mImpl.unregister();
        }

        public void sendNetworkCapabilities(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            this.mImpl.sendNetworkCapabilities(networkCapabilities);
        }

        public void sendLinkProperties(@android.annotation.NonNull android.net.LinkProperties linkProperties) {
            this.mImpl.sendLinkProperties(linkProperties);
        }

        public void setUnderlyingNetworks(@android.annotation.Nullable java.util.List<android.net.Network> list) {
            this.mImpl.setUnderlyingNetworks(list);
        }

        @android.annotation.Nullable
        public android.net.Network getNetwork() {
            return this.mImpl.getNetwork();
        }
    }
}
