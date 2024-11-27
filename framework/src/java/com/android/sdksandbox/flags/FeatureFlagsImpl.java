package com.android.sdksandbox.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.sdksandbox.flags.FeatureFlags {
    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean firstAndLastSdkSandboxUidPublic() {
        return true;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean getEffectiveTargetSdkVersionApi() {
        return false;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxActivitySdkBasedContext() {
        return false;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxClientImportanceListener() {
        return false;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxInstrumentationInfo() {
        return false;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxUidToAppUidApi() {
        return false;
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxSdkSandboxAudit() {
        return false;
    }
}
