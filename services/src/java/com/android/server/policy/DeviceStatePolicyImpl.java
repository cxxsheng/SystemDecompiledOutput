package com.android.server.policy;

/* loaded from: classes2.dex */
public final class DeviceStatePolicyImpl extends com.android.server.devicestate.DeviceStatePolicy {
    private final com.android.server.devicestate.DeviceStateProvider mProvider;

    public DeviceStatePolicyImpl(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mProvider = com.android.server.policy.DeviceStateProviderImpl.create(this.mContext);
    }

    @Override // com.android.server.devicestate.DeviceStatePolicy
    public com.android.server.devicestate.DeviceStateProvider getDeviceStateProvider() {
        return this.mProvider;
    }

    @Override // com.android.server.devicestate.DeviceStatePolicy
    public void configureDeviceForState(int i, @android.annotation.NonNull java.lang.Runnable runnable) {
        runnable.run();
    }

    @Override // android.util.Dumpable
    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        this.mProvider.dump(printWriter, strArr);
    }
}
