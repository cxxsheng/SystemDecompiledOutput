package com.android.server.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.server.flags.FeatureFlags {
    @Override // com.android.server.flags.FeatureFlags
    public boolean disableSystemCompaction() {
        return true;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinWebview() {
        return true;
    }
}
