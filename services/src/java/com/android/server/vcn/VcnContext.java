package com.android.server.vcn;

/* loaded from: classes2.dex */
public class VcnContext {

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.net.platform.flags.FeatureFlags mCoreNetFeatureFlags;

    @android.annotation.NonNull
    private final android.net.vcn.FeatureFlags mFeatureFlags;
    private final boolean mIsInTestMode;

    @android.annotation.NonNull
    private final android.os.Looper mLooper;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnNetworkProvider mVcnNetworkProvider;

    public VcnContext(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider vcnNetworkProvider, boolean z) {
        java.util.Objects.requireNonNull(context, "Missing context");
        this.mContext = context;
        java.util.Objects.requireNonNull(looper, "Missing looper");
        this.mLooper = looper;
        java.util.Objects.requireNonNull(vcnNetworkProvider, "Missing networkProvider");
        this.mVcnNetworkProvider = vcnNetworkProvider;
        this.mIsInTestMode = z;
        this.mFeatureFlags = new android.net.vcn.FeatureFlagsImpl();
        this.mCoreNetFeatureFlags = new android.net.platform.flags.FeatureFlagsImpl();
    }

    @android.annotation.NonNull
    public android.content.Context getContext() {
        return this.mContext;
    }

    @android.annotation.NonNull
    public android.os.Looper getLooper() {
        return this.mLooper;
    }

    @android.annotation.NonNull
    public com.android.server.vcn.VcnNetworkProvider getVcnNetworkProvider() {
        return this.mVcnNetworkProvider;
    }

    public boolean isInTestMode() {
        return this.mIsInTestMode;
    }

    public boolean isFlagNetworkMetricMonitorEnabled() {
        return this.mFeatureFlags.networkMetricMonitor();
    }

    public boolean isFlagIpSecTransformStateEnabled() {
        return this.mCoreNetFeatureFlags.ipsecTransformState();
    }

    @android.annotation.NonNull
    public android.net.vcn.FeatureFlags getFeatureFlags() {
        return this.mFeatureFlags;
    }

    public boolean isFlagSafeModeTimeoutConfigEnabled() {
        return this.mFeatureFlags.safeModeTimeoutConfig();
    }

    public void ensureRunningOnLooperThread() {
        if (getLooper().getThread() != java.lang.Thread.currentThread()) {
            throw new java.lang.IllegalStateException("Not running on VcnMgmtSvc thread");
        }
    }
}
