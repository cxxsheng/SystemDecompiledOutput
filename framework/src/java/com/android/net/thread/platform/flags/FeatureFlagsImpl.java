package com.android.net.thread.platform.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.net.thread.platform.flags.FeatureFlags {
    @Override // com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadEnabledPlatform() {
        return false;
    }

    @Override // com.android.net.thread.platform.flags.FeatureFlags
    public boolean threadUserRestrictionEnabled() {
        return false;
    }
}
