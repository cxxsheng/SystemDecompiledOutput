package com.android.sdksandbox.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.sdksandbox.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, false), java.util.Map.entry(com.android.sdksandbox.flags.Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.sdksandbox.flags.Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC, com.android.sdksandbox.flags.Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API, com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT, com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER, com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO, com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API, com.android.sdksandbox.flags.Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean firstAndLastSdkSandboxUidPublic() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean getEffectiveTargetSdkVersionApi() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxActivitySdkBasedContext() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sandboxClientImportanceListener() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxInstrumentationInfo() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean sdkSandboxUidToAppUidApi() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_SDK_SANDBOX_UID_TO_APP_UID_API);
    }

    @Override // com.android.sdksandbox.flags.FeatureFlags
    public boolean selinuxSdkSandboxAudit() {
        return getValue(com.android.sdksandbox.flags.Flags.FLAG_SELINUX_SDK_SANDBOX_AUDIT);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
