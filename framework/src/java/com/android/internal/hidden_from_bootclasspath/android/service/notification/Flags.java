package com.android.internal.hidden_from_bootclasspath.android.service.notification;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags FEATURE_FLAGS = new com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CALLSTYLE_CALLBACK_API = "android.service.notification.callstyle_callback_api";
    public static final java.lang.String FLAG_RANKING_UPDATE_ASHMEM = "android.service.notification.ranking_update_ashmem";
    public static final java.lang.String FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS = "android.service.notification.redact_sensitive_notifications_from_untrusted_listeners";

    public static boolean callstyleCallbackApi() {
        return FEATURE_FLAGS.callstyleCallbackApi();
    }

    public static boolean rankingUpdateAshmem() {
        return FEATURE_FLAGS.rankingUpdateAshmem();
    }

    public static boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return FEATURE_FLAGS.redactSensitiveNotificationsFromUntrustedListeners();
    }
}
