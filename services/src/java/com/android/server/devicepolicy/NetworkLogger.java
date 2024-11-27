package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class NetworkLogger {
    private static final java.lang.String TAG = com.android.server.devicepolicy.NetworkLogger.class.getSimpleName();
    private final com.android.server.devicepolicy.DevicePolicyManagerService mDpm;
    private com.android.server.ServiceThread mHandlerThread;
    private android.net.IIpConnectivityMetrics mIpConnectivityMetrics;
    private final java.util.concurrent.atomic.AtomicBoolean mIsLoggingEnabled = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.net.INetdEventCallback mNetdEventCallback = new com.android.server.net.BaseNetdEventCallback() { // from class: com.android.server.devicepolicy.NetworkLogger.1
        public void onDnsEvent(int i, int i2, int i3, java.lang.String str, java.lang.String[] strArr, int i4, long j, int i5) {
            if (!com.android.server.devicepolicy.NetworkLogger.this.mIsLoggingEnabled.get() || !shouldLogNetworkEvent(i5)) {
                return;
            }
            sendNetworkEvent(new android.app.admin.DnsEvent(str, strArr, i4, com.android.server.devicepolicy.NetworkLogger.this.mPm.getNameForUid(i5), j));
        }

        public void onConnectEvent(java.lang.String str, int i, long j, int i2) {
            if (!com.android.server.devicepolicy.NetworkLogger.this.mIsLoggingEnabled.get() || !shouldLogNetworkEvent(i2)) {
                return;
            }
            sendNetworkEvent(new android.app.admin.ConnectEvent(str, i, com.android.server.devicepolicy.NetworkLogger.this.mPm.getNameForUid(i2), j));
        }

        private void sendNetworkEvent(android.app.admin.NetworkEvent networkEvent) {
            android.os.Message obtainMessage = com.android.server.devicepolicy.NetworkLogger.this.mNetworkLoggingHandler.obtainMessage(1);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("network_event", networkEvent);
            obtainMessage.setData(bundle);
            com.android.server.devicepolicy.NetworkLogger.this.mNetworkLoggingHandler.sendMessage(obtainMessage);
        }

        private boolean shouldLogNetworkEvent(int i) {
            return com.android.server.devicepolicy.NetworkLogger.this.mTargetUserId == -1 || com.android.server.devicepolicy.NetworkLogger.this.mTargetUserId == android.os.UserHandle.getUserId(i);
        }
    };
    private com.android.server.devicepolicy.NetworkLoggingHandler mNetworkLoggingHandler;
    private final android.content.pm.PackageManagerInternal mPm;
    private final int mTargetUserId;

    NetworkLogger(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, android.content.pm.PackageManagerInternal packageManagerInternal, int i) {
        this.mDpm = devicePolicyManagerService;
        this.mPm = packageManagerInternal;
        this.mTargetUserId = i;
    }

    private boolean checkIpConnectivityMetricsService() {
        if (this.mIpConnectivityMetrics != null) {
            return true;
        }
        android.net.IIpConnectivityMetrics iIpConnectivityMetrics = this.mDpm.mInjector.getIIpConnectivityMetrics();
        if (iIpConnectivityMetrics == null) {
            return false;
        }
        this.mIpConnectivityMetrics = iIpConnectivityMetrics;
        return true;
    }

    boolean startNetworkLogging() {
        android.util.Log.d(TAG, "Starting network logging.");
        if (!checkIpConnectivityMetricsService()) {
            android.util.Slog.wtf(TAG, "Failed to register callback with IIpConnectivityMetrics.");
            return false;
        }
        try {
            if (!this.mIpConnectivityMetrics.addNetdEventCallback(1, this.mNetdEventCallback)) {
                return false;
            }
            this.mHandlerThread = new com.android.server.ServiceThread(TAG, 10, false);
            this.mHandlerThread.start();
            this.mNetworkLoggingHandler = new com.android.server.devicepolicy.NetworkLoggingHandler(this.mHandlerThread.getLooper(), this.mDpm, this.mTargetUserId);
            this.mNetworkLoggingHandler.scheduleBatchFinalization();
            this.mIsLoggingEnabled.set(true);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(TAG, "Failed to make remote calls to register the callback", e);
            return false;
        }
    }

    boolean stopNetworkLogging() {
        android.util.Log.d(TAG, "Stopping network logging");
        this.mIsLoggingEnabled.set(false);
        discardLogs();
        try {
            try {
                if (checkIpConnectivityMetricsService()) {
                    boolean removeNetdEventCallback = this.mIpConnectivityMetrics.removeNetdEventCallback(1);
                    if (this.mHandlerThread != null) {
                        this.mHandlerThread.quitSafely();
                    }
                    return removeNetdEventCallback;
                }
                android.util.Slog.wtf(TAG, "Failed to unregister callback with IIpConnectivityMetrics.");
                if (this.mHandlerThread != null) {
                    this.mHandlerThread.quitSafely();
                }
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "Failed to make remote calls to unregister the callback", e);
                if (this.mHandlerThread != null) {
                    this.mHandlerThread.quitSafely();
                }
                return true;
            }
        } catch (java.lang.Throwable th) {
            if (this.mHandlerThread != null) {
                this.mHandlerThread.quitSafely();
            }
            throw th;
        }
    }

    void pause() {
        if (this.mNetworkLoggingHandler != null) {
            this.mNetworkLoggingHandler.pause();
        }
    }

    void resume() {
        if (this.mNetworkLoggingHandler != null) {
            this.mNetworkLoggingHandler.resume();
        }
    }

    void discardLogs() {
        if (this.mNetworkLoggingHandler != null) {
            this.mNetworkLoggingHandler.discardLogs();
        }
    }

    java.util.List<android.app.admin.NetworkEvent> retrieveLogs(long j) {
        return this.mNetworkLoggingHandler.retrieveFullLogBatch(j);
    }

    long forceBatchFinalization() {
        return this.mNetworkLoggingHandler.forceBatchFinalization();
    }
}
