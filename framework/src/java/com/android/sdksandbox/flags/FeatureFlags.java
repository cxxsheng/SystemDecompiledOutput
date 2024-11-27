package com.android.sdksandbox.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean firstAndLastSdkSandboxUidPublic();

    boolean getEffectiveTargetSdkVersionApi();

    boolean sandboxActivitySdkBasedContext();

    boolean sandboxClientImportanceListener();

    boolean sdkSandboxInstrumentationInfo();

    boolean sdkSandboxUidToAppUidApi();

    boolean selinuxSdkSandboxAudit();
}
