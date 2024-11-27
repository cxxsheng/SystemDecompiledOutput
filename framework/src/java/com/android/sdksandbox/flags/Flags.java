package com.android.sdksandbox.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.sdksandbox.flags.FeatureFlags FEATURE_FLAGS = new com.android.sdksandbox.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_FIRST_AND_LAST_SDK_SANDBOX_UID_PUBLIC = "com.android.sdksandbox.flags.first_and_last_sdk_sandbox_uid_public";
    public static final java.lang.String FLAG_GET_EFFECTIVE_TARGET_SDK_VERSION_API = "com.android.sdksandbox.flags.get_effective_target_sdk_version_api";
    public static final java.lang.String FLAG_SANDBOX_ACTIVITY_SDK_BASED_CONTEXT = "com.android.sdksandbox.flags.sandbox_activity_sdk_based_context";
    public static final java.lang.String FLAG_SANDBOX_CLIENT_IMPORTANCE_LISTENER = "com.android.sdksandbox.flags.sandbox_client_importance_listener";
    public static final java.lang.String FLAG_SDK_SANDBOX_INSTRUMENTATION_INFO = "com.android.sdksandbox.flags.sdk_sandbox_instrumentation_info";
    public static final java.lang.String FLAG_SDK_SANDBOX_UID_TO_APP_UID_API = "com.android.sdksandbox.flags.sdk_sandbox_uid_to_app_uid_api";
    public static final java.lang.String FLAG_SELINUX_SDK_SANDBOX_AUDIT = "com.android.sdksandbox.flags.selinux_sdk_sandbox_audit";

    public static boolean firstAndLastSdkSandboxUidPublic() {
        return FEATURE_FLAGS.firstAndLastSdkSandboxUidPublic();
    }

    public static boolean getEffectiveTargetSdkVersionApi() {
        return FEATURE_FLAGS.getEffectiveTargetSdkVersionApi();
    }

    public static boolean sandboxActivitySdkBasedContext() {
        return FEATURE_FLAGS.sandboxActivitySdkBasedContext();
    }

    public static boolean sandboxClientImportanceListener() {
        return FEATURE_FLAGS.sandboxClientImportanceListener();
    }

    public static boolean sdkSandboxInstrumentationInfo() {
        return FEATURE_FLAGS.sdkSandboxInstrumentationInfo();
    }

    public static boolean sdkSandboxUidToAppUidApi() {
        return FEATURE_FLAGS.sdkSandboxUidToAppUidApi();
    }

    public static boolean selinuxSdkSandboxAudit() {
        return FEATURE_FLAGS.selinuxSdkSandboxAudit();
    }
}
