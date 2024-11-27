package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
public class UnderlyingNetworkEvaluator {

    @android.annotation.NonNull
    private final java.lang.Object mCancellationToken;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.Dependencies mDependencies;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback mEvaluatorCallback;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private boolean mIsPenalized;
    private boolean mIsSelected;

    @android.annotation.NonNull
    private final java.util.List<com.android.server.vcn.routeselection.NetworkMetricMonitor> mMetricMonitors;

    @android.annotation.NonNull
    private final com.android.server.vcn.routeselection.UnderlyingNetworkRecord.Builder mNetworkRecordBuilder;
    private long mPenalizedTimeoutMs;
    private int mPriorityClass;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnContext mVcnContext;
    private static final java.lang.String TAG = com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.class.getSimpleName();
    private static final int[] PENALTY_TIMEOUT_MINUTES_DEFAULT = {5};

    public interface NetworkEvaluatorCallback {
        void onEvaluationResultChanged();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public UnderlyingNetworkEvaluator(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback networkEvaluatorCallback, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.Dependencies dependencies) {
        this.mCancellationToken = new java.lang.Object();
        this.mMetricMonitors = new java.util.ArrayList();
        this.mPriorityClass = -1;
        java.util.Objects.requireNonNull(vcnContext, "Missing vcnContext");
        this.mVcnContext = vcnContext;
        this.mHandler = new android.os.Handler(this.mVcnContext.getLooper());
        java.util.Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDependencies = dependencies;
        java.util.Objects.requireNonNull(networkEvaluatorCallback, "Missing deps");
        this.mEvaluatorCallback = networkEvaluatorCallback;
        java.util.Objects.requireNonNull(list, "Missing underlyingNetworkTemplates");
        java.util.Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing lastSnapshot");
        java.util.Objects.requireNonNull(network, "Missing network");
        this.mNetworkRecordBuilder = new com.android.server.vcn.routeselection.UnderlyingNetworkRecord.Builder(network);
        this.mIsSelected = false;
        this.mIsPenalized = false;
        this.mPenalizedTimeoutMs = getPenaltyTimeoutMs(persistableBundleWrapper);
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
        if (isIpSecPacketLossDetectorEnabled()) {
            try {
                this.mMetricMonitors.add(this.mDependencies.newIpSecPacketLossDetector(this.mVcnContext, this.mNetworkRecordBuilder.getNetwork(), persistableBundleWrapper, new com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.MetricMonitorCallbackImpl()));
            } catch (java.lang.IllegalAccessException e) {
            }
        }
    }

    public UnderlyingNetworkEvaluator(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.NetworkEvaluatorCallback networkEvaluatorCallback) {
        this(vcnContext, network, list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper, networkEvaluatorCallback, new com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.Dependencies());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public com.android.server.vcn.routeselection.IpSecPacketLossDetector newIpSecPacketLossDetector(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.net.Network network, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper, @android.annotation.NonNull com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback networkMetricMonitorCallback) throws java.lang.IllegalAccessException {
            return new com.android.server.vcn.routeselection.IpSecPacketLossDetector(vcnContext, network, persistableBundleWrapper, networkMetricMonitorCallback);
        }
    }

    private class MetricMonitorCallbackImpl implements com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback {
        private MetricMonitorCallbackImpl() {
        }

        @Override // com.android.server.vcn.routeselection.NetworkMetricMonitor.NetworkMetricMonitorCallback
        public void onValidationResultReceived() {
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.mVcnContext.ensureRunningOnLooperThread();
            com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.handleValidationResult();
        }
    }

    private void updatePriorityClass(@android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        if (this.mNetworkRecordBuilder.isValid()) {
            this.mPriorityClass = com.android.server.vcn.routeselection.NetworkPriorityClassifier.calculatePriorityClass(this.mVcnContext, this.mNetworkRecordBuilder.build(), list, parcelUuid, telephonySubscriptionSnapshot, this.mIsSelected, persistableBundleWrapper);
        } else {
            this.mPriorityClass = -1;
        }
    }

    private boolean isIpSecPacketLossDetectorEnabled() {
        return isIpSecPacketLossDetectorEnabled(this.mVcnContext);
    }

    private static boolean isIpSecPacketLossDetectorEnabled(com.android.server.vcn.VcnContext vcnContext) {
        return vcnContext.isFlagIpSecTransformStateEnabled() && vcnContext.isFlagNetworkMetricMonitorEnabled();
    }

    public static java.util.Comparator<com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator> getComparator(final com.android.server.vcn.VcnContext vcnContext) {
        return new java.util.Comparator() { // from class: com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$getComparator$0;
                lambda$getComparator$0 = com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.lambda$getComparator$0(com.android.server.vcn.VcnContext.this, (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) obj, (com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator) obj2);
                return lambda$getComparator$0;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getComparator$0(com.android.server.vcn.VcnContext vcnContext, com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator, com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator underlyingNetworkEvaluator2) {
        if (isIpSecPacketLossDetectorEnabled(vcnContext) && underlyingNetworkEvaluator.mIsPenalized != underlyingNetworkEvaluator2.mIsPenalized) {
            return underlyingNetworkEvaluator.mIsPenalized ? 1 : -1;
        }
        int i = underlyingNetworkEvaluator.mPriorityClass;
        int i2 = underlyingNetworkEvaluator2.mPriorityClass;
        if (i == i2) {
            if (underlyingNetworkEvaluator.mIsSelected) {
                return -1;
            }
            if (underlyingNetworkEvaluator2.mIsSelected) {
                return 1;
            }
        }
        return java.lang.Integer.compare(i, i2);
    }

    private static long getPenaltyTimeoutMs(@android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        int[] iArr;
        if (persistableBundleWrapper != null) {
            iArr = persistableBundleWrapper.getIntArray("vcn_network_selection_penalty_timeout_minutes_list", PENALTY_TIMEOUT_MINUTES_DEFAULT);
        } else {
            iArr = PENALTY_TIMEOUT_MINUTES_DEFAULT;
        }
        return java.util.concurrent.TimeUnit.MINUTES.toMillis(iArr[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleValidationResult() {
        boolean z = this.mIsPenalized;
        this.mIsPenalized = false;
        java.util.Iterator<com.android.server.vcn.routeselection.NetworkMetricMonitor> it = this.mMetricMonitors.iterator();
        while (it.hasNext()) {
            this.mIsPenalized = it.next().isValidationFailed() | this.mIsPenalized;
        }
        if (z == this.mIsPenalized) {
            return;
        }
        logInfo("#handleValidationResult: wasPenalized " + z + " mIsPenalized " + this.mIsPenalized);
        if (this.mIsPenalized) {
            this.mHandler.postDelayed(new com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.ExitPenaltyBoxRunnable(), this.mCancellationToken, this.mPenalizedTimeoutMs);
        } else {
            this.mHandler.removeCallbacksAndEqualMessages(this.mCancellationToken);
        }
        this.mEvaluatorCallback.onEvaluationResultChanged();
    }

    public class ExitPenaltyBoxRunnable implements java.lang.Runnable {
        public ExitPenaltyBoxRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.mIsPenalized) {
                com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.logWtf("Evaluator not being penalized but ExitPenaltyBoxRunnable was scheduled");
            } else {
                com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.mIsPenalized = false;
                com.android.server.vcn.routeselection.UnderlyingNetworkEvaluator.this.mEvaluatorCallback.onEvaluationResultChanged();
            }
        }
    }

    public void setNetworkCapabilities(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        this.mNetworkRecordBuilder.setNetworkCapabilities(networkCapabilities);
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
    }

    public void setLinkProperties(@android.annotation.NonNull android.net.LinkProperties linkProperties, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        this.mNetworkRecordBuilder.setLinkProperties(linkProperties);
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
    }

    public void setIsBlocked(boolean z, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        this.mNetworkRecordBuilder.setIsBlocked(z);
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
    }

    public void setIsSelected(boolean z, @android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        this.mIsSelected = z;
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
        java.util.Iterator<com.android.server.vcn.routeselection.NetworkMetricMonitor> it = this.mMetricMonitors.iterator();
        while (it.hasNext()) {
            it.next().setIsSelectedUnderlyingNetwork(z);
        }
    }

    public void reevaluate(@android.annotation.NonNull java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.Nullable com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper) {
        updatePriorityClass(list, parcelUuid, telephonySubscriptionSnapshot, persistableBundleWrapper);
        this.mPenalizedTimeoutMs = getPenaltyTimeoutMs(persistableBundleWrapper);
        java.util.Iterator<com.android.server.vcn.routeselection.NetworkMetricMonitor> it = this.mMetricMonitors.iterator();
        while (it.hasNext()) {
            it.next().setCarrierConfig(persistableBundleWrapper);
        }
    }

    public void setInboundTransform(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform) {
        if (!this.mIsSelected) {
            logWtf("setInboundTransform on an unselected evaluator");
            return;
        }
        java.util.Iterator<com.android.server.vcn.routeselection.NetworkMetricMonitor> it = this.mMetricMonitors.iterator();
        while (it.hasNext()) {
            it.next().setInboundTransform(ipSecTransform);
        }
    }

    public void close() {
        this.mHandler.removeCallbacksAndEqualMessages(this.mCancellationToken);
        java.util.Iterator<com.android.server.vcn.routeselection.NetworkMetricMonitor> it = this.mMetricMonitors.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
    }

    public boolean isValid() {
        return this.mNetworkRecordBuilder.isValid();
    }

    public android.net.Network getNetwork() {
        return this.mNetworkRecordBuilder.getNetwork();
    }

    public com.android.server.vcn.routeselection.UnderlyingNetworkRecord getNetworkRecord() {
        return this.mNetworkRecordBuilder.build();
    }

    public int getPriorityClass() {
        return this.mPriorityClass;
    }

    public boolean isPenalized() {
        return this.mIsPenalized;
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("UnderlyingNetworkEvaluator:");
        indentingPrintWriter.increaseIndent();
        if (this.mNetworkRecordBuilder.isValid()) {
            getNetworkRecord().dump(indentingPrintWriter);
        } else {
            indentingPrintWriter.println("UnderlyingNetworkRecord incomplete: mNetwork: " + this.mNetworkRecordBuilder.getNetwork());
        }
        indentingPrintWriter.println("mIsSelected: " + this.mIsSelected);
        indentingPrintWriter.println("mPriorityClass: " + this.mPriorityClass);
        indentingPrintWriter.println("mIsPenalized: " + this.mIsPenalized);
        indentingPrintWriter.decreaseIndent();
    }

    private java.lang.String getLogPrefix() {
        return "[Network " + this.mNetworkRecordBuilder.getNetwork() + "] ";
    }

    private void logInfo(java.lang.String str) {
        android.util.Slog.i(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[INFO ] " + TAG + getLogPrefix() + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, getLogPrefix() + str);
        com.android.server.VcnManagementService.LOCAL_LOG.log("[WTF ] " + TAG + getLogPrefix() + str);
    }
}
