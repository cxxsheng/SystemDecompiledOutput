package android.net.vcn;

/* loaded from: classes2.dex */
public class VcnManager {
    public static final int VCN_ERROR_CODE_CONFIG_ERROR = 1;
    public static final int VCN_ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int VCN_ERROR_CODE_NETWORK_ERROR = 2;
    public static final int VCN_STATUS_CODE_ACTIVE = 2;
    public static final int VCN_STATUS_CODE_INACTIVE = 1;
    public static final int VCN_STATUS_CODE_NOT_CONFIGURED = 0;
    public static final int VCN_STATUS_CODE_SAFE_MODE = 3;
    private final android.content.Context mContext;
    private final android.net.vcn.IVcnManagementService mService;
    private static final java.lang.String TAG = android.net.vcn.VcnManager.class.getSimpleName();
    public static final java.lang.String VCN_NETWORK_SELECTION_WIFI_ENTRY_RSSI_THRESHOLD_KEY = "vcn_network_selection_wifi_entry_rssi_threshold";
    public static final java.lang.String VCN_NETWORK_SELECTION_WIFI_EXIT_RSSI_THRESHOLD_KEY = "vcn_network_selection_wifi_exit_rssi_threshold";
    public static final java.lang.String VCN_NETWORK_SELECTION_POLL_IPSEC_STATE_INTERVAL_SECONDS_KEY = "vcn_network_selection_poll_ipsec_state_interval_seconds";
    public static final java.lang.String VCN_NETWORK_SELECTION_IPSEC_PACKET_LOSS_PERCENT_THRESHOLD_KEY = "vcn_network_selection_ipsec_packet_loss_percent_threshold";
    public static final java.lang.String VCN_NETWORK_SELECTION_PENALTY_TIMEOUT_MINUTES_LIST_KEY = "vcn_network_selection_penalty_timeout_minutes_list";
    public static final java.lang.String VCN_RESTRICTED_TRANSPORTS_INT_ARRAY_KEY = "vcn_restricted_transports";
    public static final java.lang.String VCN_SAFE_MODE_TIMEOUT_SECONDS_KEY = "vcn_safe_mode_timeout_seconds_key";
    public static final java.lang.String VCN_TUNNEL_AGGREGATION_SA_COUNT_MAX_KEY = "vcn_tunnel_aggregation_sa_count_max";
    public static final java.lang.String[] VCN_RELATED_CARRIER_CONFIG_KEYS = {VCN_NETWORK_SELECTION_WIFI_ENTRY_RSSI_THRESHOLD_KEY, VCN_NETWORK_SELECTION_WIFI_EXIT_RSSI_THRESHOLD_KEY, VCN_NETWORK_SELECTION_POLL_IPSEC_STATE_INTERVAL_SECONDS_KEY, VCN_NETWORK_SELECTION_IPSEC_PACKET_LOSS_PERCENT_THRESHOLD_KEY, VCN_NETWORK_SELECTION_PENALTY_TIMEOUT_MINUTES_LIST_KEY, VCN_RESTRICTED_TRANSPORTS_INT_ARRAY_KEY, VCN_SAFE_MODE_TIMEOUT_SECONDS_KEY, VCN_TUNNEL_AGGREGATION_SA_COUNT_MAX_KEY};
    private static final java.util.Map<android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener, android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder> REGISTERED_POLICY_LISTENERS = new java.util.concurrent.ConcurrentHashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VcnErrorCode {
    }

    @android.annotation.SystemApi
    public interface VcnNetworkPolicyChangeListener {
        void onPolicyChanged();
    }

    public static abstract class VcnStatusCallback {
        private android.net.vcn.VcnManager.VcnStatusCallbackBinder mCbBinder;

        public abstract void onGatewayConnectionError(java.lang.String str, int i, java.lang.Throwable th);

        public abstract void onStatusChanged(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VcnStatusCode {
    }

    public interface VcnUnderlyingNetworkPolicyListener extends android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener {
    }

    public VcnManager(android.content.Context context, android.net.vcn.IVcnManagementService iVcnManagementService) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context, "missing context");
        this.mService = (android.net.vcn.IVcnManagementService) java.util.Objects.requireNonNull(iVcnManagementService, "missing service");
    }

    public static java.util.Map<android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener, android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder> getAllPolicyListeners() {
        return java.util.Collections.unmodifiableMap(REGISTERED_POLICY_LISTENERS);
    }

    public void setVcnConfig(android.os.ParcelUuid parcelUuid, android.net.vcn.VcnConfig vcnConfig) throws java.io.IOException {
        java.util.Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        java.util.Objects.requireNonNull(vcnConfig, "config was null");
        try {
            this.mService.setVcnConfig(parcelUuid, vcnConfig, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.io.IOException(e2);
        }
    }

    public void clearVcnConfig(android.os.ParcelUuid parcelUuid) throws java.io.IOException {
        java.util.Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        try {
            this.mService.clearVcnConfig(parcelUuid, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.io.IOException(e2);
        }
    }

    public java.util.List<android.os.ParcelUuid> getConfiguredSubscriptionGroups() {
        try {
            return this.mService.getConfiguredSubscriptionGroups(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addVcnUnderlyingNetworkPolicyListener(java.util.concurrent.Executor executor, android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListener vcnUnderlyingNetworkPolicyListener) {
        addVcnNetworkPolicyChangeListener(executor, vcnUnderlyingNetworkPolicyListener);
    }

    public void removeVcnUnderlyingNetworkPolicyListener(android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListener vcnUnderlyingNetworkPolicyListener) {
        removeVcnNetworkPolicyChangeListener(vcnUnderlyingNetworkPolicyListener);
    }

    public android.net.vcn.VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) {
        java.util.Objects.requireNonNull(networkCapabilities, "networkCapabilities must not be null");
        java.util.Objects.requireNonNull(linkProperties, "linkProperties must not be null");
        try {
            return this.mService.getUnderlyingNetworkPolicy(networkCapabilities, linkProperties);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addVcnNetworkPolicyChangeListener(java.util.concurrent.Executor executor, android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener vcnNetworkPolicyChangeListener) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(vcnNetworkPolicyChangeListener, "listener must not be null");
        android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder vcnUnderlyingNetworkPolicyListenerBinder = new android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder(executor, vcnNetworkPolicyChangeListener);
        if (REGISTERED_POLICY_LISTENERS.putIfAbsent(vcnNetworkPolicyChangeListener, vcnUnderlyingNetworkPolicyListenerBinder) != null) {
            throw new java.lang.IllegalStateException("listener is already registered with VcnManager");
        }
        try {
            this.mService.addVcnUnderlyingNetworkPolicyListener(vcnUnderlyingNetworkPolicyListenerBinder);
        } catch (android.os.RemoteException e) {
            REGISTERED_POLICY_LISTENERS.remove(vcnNetworkPolicyChangeListener);
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void removeVcnNetworkPolicyChangeListener(android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener vcnNetworkPolicyChangeListener) {
        java.util.Objects.requireNonNull(vcnNetworkPolicyChangeListener, "listener must not be null");
        android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder remove = REGISTERED_POLICY_LISTENERS.remove(vcnNetworkPolicyChangeListener);
        if (remove == null) {
            return;
        }
        try {
            this.mService.removeVcnUnderlyingNetworkPolicyListener(remove);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.net.vcn.VcnNetworkPolicyResult applyVcnNetworkPolicy(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) {
        java.util.Objects.requireNonNull(networkCapabilities, "networkCapabilities must not be null");
        java.util.Objects.requireNonNull(linkProperties, "linkProperties must not be null");
        android.net.vcn.VcnUnderlyingNetworkPolicy underlyingNetworkPolicy = getUnderlyingNetworkPolicy(networkCapabilities, linkProperties);
        return new android.net.vcn.VcnNetworkPolicyResult(underlyingNetworkPolicy.isTeardownRequested(), underlyingNetworkPolicy.getMergedNetworkCapabilities());
    }

    public void registerVcnStatusCallback(android.os.ParcelUuid parcelUuid, java.util.concurrent.Executor executor, android.net.vcn.VcnManager.VcnStatusCallback vcnStatusCallback) {
        java.util.Objects.requireNonNull(parcelUuid, "subscriptionGroup must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(vcnStatusCallback, "callback must not be null");
        synchronized (vcnStatusCallback) {
            if (vcnStatusCallback.mCbBinder != null) {
                throw new java.lang.IllegalStateException("callback is already registered with VcnManager");
            }
            vcnStatusCallback.mCbBinder = new android.net.vcn.VcnManager.VcnStatusCallbackBinder(executor, vcnStatusCallback);
            try {
                this.mService.registerVcnStatusCallback(parcelUuid, vcnStatusCallback.mCbBinder, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                vcnStatusCallback.mCbBinder = null;
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterVcnStatusCallback(android.net.vcn.VcnManager.VcnStatusCallback vcnStatusCallback) {
        java.util.Objects.requireNonNull(vcnStatusCallback, "callback must not be null");
        synchronized (vcnStatusCallback) {
            if (vcnStatusCallback.mCbBinder == null) {
                return;
            }
            try {
                try {
                    this.mService.unregisterVcnStatusCallback(vcnStatusCallback.mCbBinder);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                vcnStatusCallback.mCbBinder = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VcnUnderlyingNetworkPolicyListenerBinder extends android.net.vcn.IVcnUnderlyingNetworkPolicyListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener mListener;

        private VcnUnderlyingNetworkPolicyListenerBinder(java.util.concurrent.Executor executor, android.net.vcn.VcnManager.VcnNetworkPolicyChangeListener vcnNetworkPolicyChangeListener) {
            this.mExecutor = executor;
            this.mListener = vcnNetworkPolicyChangeListener;
        }

        @Override // android.net.vcn.IVcnUnderlyingNetworkPolicyListener
        public void onPolicyChanged() {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.net.vcn.VcnManager$VcnUnderlyingNetworkPolicyListenerBinder$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder.this.lambda$onPolicyChanged$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPolicyChanged$0() {
            this.mListener.onPolicyChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPolicyChanged$1() throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.vcn.VcnManager$VcnUnderlyingNetworkPolicyListenerBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.vcn.VcnManager.VcnUnderlyingNetworkPolicyListenerBinder.this.lambda$onPolicyChanged$0();
                }
            });
        }
    }

    public static class VcnStatusCallbackBinder extends android.net.vcn.IVcnStatusCallback.Stub {
        private final android.net.vcn.VcnManager.VcnStatusCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        public VcnStatusCallbackBinder(java.util.concurrent.Executor executor, android.net.vcn.VcnManager.VcnStatusCallback vcnStatusCallback) {
            this.mExecutor = executor;
            this.mCallback = vcnStatusCallback;
        }

        @Override // android.net.vcn.IVcnStatusCallback
        public void onVcnStatusChanged(final int i) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.net.vcn.VcnManager$VcnStatusCallbackBinder$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.net.vcn.VcnManager.VcnStatusCallbackBinder.this.lambda$onVcnStatusChanged$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVcnStatusChanged$0(int i) {
            this.mCallback.onStatusChanged(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVcnStatusChanged$1(final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.vcn.VcnManager$VcnStatusCallbackBinder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.vcn.VcnManager.VcnStatusCallbackBinder.this.lambda$onVcnStatusChanged$0(i);
                }
            });
        }

        @Override // android.net.vcn.IVcnStatusCallback
        public void onGatewayConnectionError(final java.lang.String str, final int i, java.lang.String str2, java.lang.String str3) {
            final java.lang.Throwable createThrowableByClassName = createThrowableByClassName(str2, str3);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.net.vcn.VcnManager$VcnStatusCallbackBinder$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.net.vcn.VcnManager.VcnStatusCallbackBinder.this.lambda$onGatewayConnectionError$3(str, i, createThrowableByClassName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGatewayConnectionError$3(final java.lang.String str, final int i, final java.lang.Throwable th) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.vcn.VcnManager$VcnStatusCallbackBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.vcn.VcnManager.VcnStatusCallbackBinder.this.lambda$onGatewayConnectionError$2(str, i, th);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGatewayConnectionError$2(java.lang.String str, int i, java.lang.Throwable th) {
            this.mCallback.onGatewayConnectionError(str, i, th);
        }

        private static java.lang.Throwable createThrowableByClassName(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                return null;
            }
            try {
                return (java.lang.Throwable) java.lang.Class.forName(str).getConstructor(java.lang.String.class).newInstance(str2);
            } catch (java.lang.ClassCastException | java.lang.ReflectiveOperationException e) {
                return new java.lang.RuntimeException(str + ": " + str2);
            }
        }
    }
}
