package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
public class IpSecPacketLossDetector extends com.android.server.vcn.routeselection.NetworkMetricMonitor {
    private static final int IPSEC_PACKET_LOSS_PERCENT_THRESHOLD_DEFAULT = 12;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int PACKET_LOSS_UNAVALAIBLE = -1;
    private static final int POLL_IPSEC_STATE_INTERVAL_SECONDS_DEFAULT = 20;
    private static final java.lang.String TAG = com.android.server.vcn.routeselection.IpSecPacketLossDetector.class.getSimpleName();

    @android.annotation.NonNull
    private final java.lang.Object mCancellationToken;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.Nullable
    private com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper mInboundTransform;

    @android.annotation.Nullable
    private android.net.IpSecTransformState mLastIpSecTransformState;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.IpSecPacketLossDetector.PacketLossCalculator mPacketLossCalculator;
    private final int mPacketLossRatePercentThreshold;
    private long mPollIpSecStateIntervalMs;

    @android.annotation.NonNull
    private final android.os.PowerManager mPowerManager;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public IpSecPacketLossDetector(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback networkMetricMonitorCallback, @android.annotation.NonNull com.android.server.vcn.routeselection.IpSecPacketLossDetector.Dependencies dependencies) throws java.lang.IllegalAccessException {
        super(vcnContext, network, persistableBundleWrapper, networkMetricMonitorCallback);
        this.mCancellationToken = new java.lang.Object();
        java.util.Objects.requireNonNull(dependencies, "Missing deps");
        if (!vcnContext.isFlagIpSecTransformStateEnabled()) {
            logWtf("ipsecTransformState flag disabled");
            throw new java.lang.IllegalAccessException("ipsecTransformState flag disabled");
        }
        this.mHandler = new android.os.Handler(getVcnContext().getLooper());
        this.mPowerManager = (android.os.PowerManager) getVcnContext().getContext().getSystemService(android.os.PowerManager.class);
        this.mPacketLossCalculator = dependencies.getPacketLossCalculator();
        this.mPollIpSecStateIntervalMs = getPollIpSecStateIntervalMs(persistableBundleWrapper);
        this.mPacketLossRatePercentThreshold = getPacketLossRatePercentThreshold(persistableBundleWrapper);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        getVcnContext().getContext().registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.vcn.routeselection.IpSecPacketLossDetector.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(intent.getAction()) && com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mPowerManager.isDeviceIdleMode()) {
                    com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mLastIpSecTransformState = null;
                }
            }
        }, intentFilter, null, this.mHandler);
    }

    public IpSecPacketLossDetector(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback networkMetricMonitorCallback) throws java.lang.IllegalAccessException {
        this(vcnContext, network, persistableBundleWrapper, networkMetricMonitorCallback, new com.android.server.vcn.routeselection.IpSecPacketLossDetector.Dependencies());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public com.android.server.vcn.routeselection.IpSecPacketLossDetector.PacketLossCalculator getPacketLossCalculator() {
            return new com.android.server.vcn.routeselection.IpSecPacketLossDetector.PacketLossCalculator();
        }
    }

    private static long getPollIpSecStateIntervalMs(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        return java.util.concurrent.TimeUnit.SECONDS.toMillis(persistableBundleWrapper != null ? persistableBundleWrapper.getInt("vcn_network_selection_poll_ipsec_state_interval_seconds", 20) : 20);
    }

    private static int getPacketLossRatePercentThreshold(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        if (persistableBundleWrapper != null) {
            return persistableBundleWrapper.getInt("vcn_network_selection_ipsec_packet_loss_percent_threshold", 12);
        }
        return 12;
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    protected void onSelectedUnderlyingNetworkChanged() {
        if (!isSelectedUnderlyingNetwork()) {
            this.mInboundTransform = null;
            stop();
        }
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    public void setInboundTransformInternal(@android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper ipSecTransformWrapper) {
        java.util.Objects.requireNonNull(ipSecTransformWrapper, "inboundTransform is null");
        if (java.util.Objects.equals(ipSecTransformWrapper, this.mInboundTransform)) {
            return;
        }
        if (!isSelectedUnderlyingNetwork()) {
            logWtf("setInboundTransform called but network not selected");
        } else {
            this.mInboundTransform = ipSecTransformWrapper;
            start();
        }
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    public void setCarrierConfig(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        this.mPollIpSecStateIntervalMs = getPollIpSecStateIntervalMs(persistableBundleWrapper);
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    protected void start() {
        super.start();
        clearTransformStateAndPollingEvents();
        this.mHandler.postDelayed(new com.android.server.vcn.routeselection.IpSecPacketLossDetector.PollIpSecStateRunnable(), this.mCancellationToken, 0L);
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor
    public void stop() {
        super.stop();
        clearTransformStateAndPollingEvents();
    }

    private void clearTransformStateAndPollingEvents() {
        this.mHandler.removeCallbacksAndEqualMessages(this.mCancellationToken);
        this.mLastIpSecTransformState = null;
    }

    @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor, java.lang.AutoCloseable
    public void close() {
        super.close();
        if (this.mInboundTransform != null) {
            this.mInboundTransform.close();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    @android.annotation.Nullable
    public android.net.IpSecTransformState getLastTransformState() {
        return this.mLastIpSecTransformState;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PROTECTED)
    @android.annotation.Nullable
    public com.android.server.vcn.routeselection.NetworkMetricMonitor.IpSecTransformWrapper getInboundTransformInternal() {
        return this.mInboundTransform;
    }

    private class PollIpSecStateRunnable implements java.lang.Runnable {
        private PollIpSecStateRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.isStarted()) {
                com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.logWtf("Monitor stopped but PollIpSecStateRunnable not removed from Handler");
            } else {
                com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.getInboundTransformInternal().requestIpSecTransformState(new android.os.HandlerExecutor(com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mHandler), new com.android.server.vcn.routeselection.IpSecPacketLossDetector.IpSecTransformStateReceiver());
                com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mHandler.postDelayed(com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.new PollIpSecStateRunnable(), com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mCancellationToken, com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.mPollIpSecStateIntervalMs);
            }
        }
    }

    private class IpSecTransformStateReceiver implements android.os.OutcomeReceiver<android.net.IpSecTransformState, java.lang.RuntimeException> {
        private IpSecTransformStateReceiver() {
        }

        @Override // android.os.OutcomeReceiver
        public void onResult(@android.annotation.NonNull android.net.IpSecTransformState ipSecTransformState) {
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.getVcnContext().ensureRunningOnLooperThread();
            if (!com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.isStarted()) {
                return;
            }
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.onIpSecTransformStateReceived(ipSecTransformState);
        }

        @Override // android.os.OutcomeReceiver
        public void onError(@android.annotation.NonNull java.lang.RuntimeException runtimeException) {
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.getVcnContext().ensureRunningOnLooperThread();
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.this.logW("TransformStateReceiver#onError " + runtimeException.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onIpSecTransformStateReceived(@android.annotation.NonNull android.net.IpSecTransformState ipSecTransformState) {
        if (this.mLastIpSecTransformState == null) {
            this.mLastIpSecTransformState = ipSecTransformState;
            return;
        }
        int packetLossRatePercentage = this.mPacketLossCalculator.getPacketLossRatePercentage(this.mLastIpSecTransformState, ipSecTransformState, getLogPrefix());
        if (packetLossRatePercentage == -1) {
            return;
        }
        java.lang.String str = "packetLossRate: " + packetLossRatePercentage + "% in the past " + (ipSecTransformState.getTimestampMillis() - this.mLastIpSecTransformState.getTimestampMillis()) + "ms";
        this.mLastIpSecTransformState = ipSecTransformState;
        if (packetLossRatePercentage < this.mPacketLossRatePercentThreshold) {
            logV(str);
            onValidationResultReceivedInternal(false);
        } else {
            logInfo(str);
            onValidationResultReceivedInternal(true);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class PacketLossCalculator {
        public int getPacketLossRatePercentage(@android.annotation.NonNull android.net.IpSecTransformState ipSecTransformState, @android.annotation.NonNull android.net.IpSecTransformState ipSecTransformState2, java.lang.String str) {
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.logVIpSecTransform("oldState", ipSecTransformState, str);
            com.android.server.vcn.routeselection.IpSecPacketLossDetector.logVIpSecTransform("newState", ipSecTransformState2, str);
            int length = ipSecTransformState.getReplayBitmap().length * 8;
            long rxHighestSequenceNumber = ipSecTransformState.getRxHighestSequenceNumber();
            long j = length;
            long max = java.lang.Math.max(0L, (rxHighestSequenceNumber - j) + 1);
            long rxHighestSequenceNumber2 = ipSecTransformState2.getRxHighestSequenceNumber();
            long max2 = java.lang.Math.max(0L, (rxHighestSequenceNumber2 - j) + 1);
            if (rxHighestSequenceNumber == rxHighestSequenceNumber2 || rxHighestSequenceNumber2 < j) {
                return -1;
            }
            long packetCntInReplayWindow = (max2 + com.android.server.vcn.routeselection.IpSecPacketLossDetector.getPacketCntInReplayWindow(ipSecTransformState2)) - (max + com.android.server.vcn.routeselection.IpSecPacketLossDetector.getPacketCntInReplayWindow(ipSecTransformState));
            long packetCount = ipSecTransformState2.getPacketCount() - ipSecTransformState.getPacketCount();
            com.android.server.vcn.routeselection.NetworkMetricMonitor.logV(com.android.server.vcn.routeselection.IpSecPacketLossDetector.TAG, str + " expectedPktCntDiff: " + packetCntInReplayWindow + " actualPktCntDiff: " + packetCount);
            if (packetCntInReplayWindow < 0 || packetCntInReplayWindow == 0 || packetCount < 0 || packetCount > packetCntInReplayWindow) {
                com.android.server.vcn.routeselection.NetworkMetricMonitor.logWtf(com.android.server.vcn.routeselection.IpSecPacketLossDetector.TAG, "Impossible values for expectedPktCntDiff or actualPktCntDiff");
                return -1;
            }
            return 100 - ((int) ((packetCount * 100) / packetCntInReplayWindow));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logVIpSecTransform(java.lang.String str, android.net.IpSecTransformState ipSecTransformState, java.lang.String str2) {
        java.lang.String str3 = " seqNo: " + ipSecTransformState.getRxHighestSequenceNumber() + " | pktCnt: " + ipSecTransformState.getPacketCount() + " | pktCntInWindow: " + getPacketCntInReplayWindow(ipSecTransformState);
        com.android.server.vcn.routeselection.NetworkMetricMonitor.logV(TAG, str2 + " " + str + str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getPacketCntInReplayWindow(@android.annotation.NonNull android.net.IpSecTransformState ipSecTransformState) {
        return java.util.BitSet.valueOf(ipSecTransformState.getReplayBitmap()).cardinality();
    }
}
