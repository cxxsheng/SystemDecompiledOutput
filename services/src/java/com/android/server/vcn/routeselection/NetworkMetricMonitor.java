package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
public abstract class NetworkMetricMonitor implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = com.android.server.vcn.routeselection.NetworkMetricMonitor.class.getSimpleName();
    private static final boolean VDBG = false;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback mCallback;

    @android.annotation.NonNull
    private final android.util.CloseGuard mCloseGuard = new android.util.CloseGuard();
    private boolean mIsSelectedUnderlyingNetwork;
    private boolean mIsStarted;
    private boolean mIsValidationFailed;

    @android.annotation.NonNull
    private final android.net.Network mNetwork;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnContext mVcnContext;

    public interface NetworkMetricMonitorCallback {
        void onValidationResultReceived();
    }

    protected abstract void onSelectedUnderlyingNetworkChanged();

    protected NetworkMetricMonitor(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback networkMetricMonitorCallback) throws java.lang.IllegalAccessException {
        if (!vcnContext.isFlagNetworkMetricMonitorEnabled()) {
            logWtf("networkMetricMonitor flag disabled");
            throw new java.lang.IllegalAccessException("networkMetricMonitor flag disabled");
        }
        java.util.Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mVcnContext = vcnContext;
        java.util.Objects.requireNonNull(network, "Missing network");
        this.mNetwork = network;
        java.util.Objects.requireNonNull(networkMetricMonitorCallback, "Missing callback");
        this.mCallback = networkMetricMonitorCallback;
        this.mIsSelectedUnderlyingNetwork = false;
        this.mIsStarted = false;
        this.mIsValidationFailed = false;
    }

    protected void start() {
        this.mIsStarted = true;
    }

    public void stop() {
        this.mIsValidationFailed = false;
        this.mIsStarted = false;
    }

    protected void onValidationResultReceivedInternal(boolean z) {
        this.mIsValidationFailed = z;
        this.mCallback.onValidationResultReceived();
    }

    public void setIsSelectedUnderlyingNetwork(boolean z) {
        if (this.mIsSelectedUnderlyingNetwork == z) {
            return;
        }
        this.mIsSelectedUnderlyingNetwork = z;
        onSelectedUnderlyingNetworkChanged();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PROTECTED)
    public static class IpSecTransformWrapper {

        @android.annotation.NonNull
        public final android.net.IpSecTransform ipSecTransform;

        public IpSecTransformWrapper(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform) {
            this.ipSecTransform = ipSecTransform;
        }

        public void requestIpSecTransformState(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.os.OutcomeReceiver<android.net.IpSecTransformState, java.lang.RuntimeException> outcomeReceiver) {
            this.ipSecTransform.requestIpSecTransformState(executor, outcomeReceiver);
        }

        public void close() {
            this.ipSecTransform.close();
        }

        public int hashCode() {
            return java.util.Objects.hash(this.ipSecTransform);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper)) {
                return false;
            }
            return java.util.Objects.equals(this.ipSecTransform, ((com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper) obj).ipSecTransform);
        }
    }

    public void setInboundTransform(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform) {
        setInboundTransformInternal(new com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper(ipSecTransform));
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public void setInboundTransformInternal(@android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper ipSecTransformWrapper) {
    }

    public void setCarrierConfig(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
    }

    public boolean isValidationFailed() {
        return this.mIsValidationFailed;
    }

    public boolean isSelectedUnderlyingNetwork() {
        return this.mIsSelectedUnderlyingNetwork;
    }

    public boolean isStarted() {
        return this.mIsStarted;
    }

    @android.annotation.NonNull
    public com.android.server.vcn.VcnContext getVcnContext() {
        return this.mVcnContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        stop();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
            super.finalize();
        } catch (java.lang.Throwable th) {
            super.finalize();
            throw th;
        }
    }

    private java.lang.String getClassName() {
        return getClass().getSimpleName();
    }

    protected java.lang.String getLogPrefix() {
        return " [Network " + this.mNetwork + "] ";
    }

    protected void logV(java.lang.String str) {
    }

    protected void logInfo(java.lang.String str) {
        android.util.Slog.i(getClassName(), getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO ] " + getClassName() + getLogPrefix() + str);
    }

    protected void logW(java.lang.String str) {
        android.util.Slog.w(getClassName(), getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WARN ] " + getClassName() + getLogPrefix() + str);
    }

    protected void logWtf(java.lang.String str) {
        android.util.Slog.wtf(getClassName(), getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WTF ] " + getClassName() + getLogPrefix() + str);
    }

    protected static void logV(java.lang.String str, java.lang.String str2) {
    }

    protected static void logWtf(java.lang.String str, java.lang.String str2) {
        android.util.Slog.wtf(str, str2);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WTF ] " + str + str2);
    }
}
