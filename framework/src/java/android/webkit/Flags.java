package android.webkit;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.webkit.FeatureFlags FEATURE_FLAGS = new android.webkit.FeatureFlagsImpl();
    public static final java.lang.String FLAG_UPDATE_SERVICE_IPC_WRAPPER = "android.webkit.update_service_ipc_wrapper";
    public static final java.lang.String FLAG_UPDATE_SERVICE_V2 = "android.webkit.update_service_v2";

    public static boolean updateServiceIpcWrapper() {
        return FEATURE_FLAGS.updateServiceIpcWrapper();
    }

    public static boolean updateServiceV2() {
        return FEATURE_FLAGS.updateServiceV2();
    }
}
