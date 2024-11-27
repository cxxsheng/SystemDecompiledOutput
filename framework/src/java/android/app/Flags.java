package android.app;

/* loaded from: classes.dex */
public final class Flags {
    private static android.app.FeatureFlags FEATURE_FLAGS = new android.app.FeatureFlagsImpl();
    public static final java.lang.String FLAG_API_TVEXTENDER = "android.app.api_tvextender";
    public static final java.lang.String FLAG_APP_RESTRICTIONS_API = "android.app.app_restrictions_api";
    public static final java.lang.String FLAG_APP_START_INFO = "android.app.app_start_info";
    public static final java.lang.String FLAG_BCAST_EVENT_TIMESTAMPS = "android.app.bcast_event_timestamps";
    public static final java.lang.String FLAG_BIC_CLIENT = "android.app.bic_client";
    public static final java.lang.String FLAG_CATEGORY_VOICEMAIL = "android.app.category_voicemail";
    public static final java.lang.String FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE = "android.app.enable_night_mode_binder_cache";
    public static final java.lang.String FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING = "android.app.enable_pip_ui_state_callback_on_entering";
    public static final java.lang.String FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT = "android.app.evenly_divided_call_style_action_layout";
    public static final java.lang.String FLAG_GET_BINDING_UID_IMPORTANCE = "android.app.get_binding_uid_importance";
    public static final java.lang.String FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK = "android.app.introduce_new_service_ontimeout_callback";
    public static final java.lang.String FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS = "android.app.keyguard_private_notifications";
    public static final java.lang.String FLAG_LIFETIME_EXTENSION_REFACTOR = "android.app.lifetime_extension_refactor";
    public static final java.lang.String FLAG_MODES_API = "android.app.modes_api";
    public static final java.lang.String FLAG_MODES_UI = "android.app.modes_ui";
    public static final java.lang.String FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API = "android.app.notification_channel_vibration_effect_api";
    public static final java.lang.String FLAG_PINNER_SERVICE_CLIENT_API = "android.app.pinner_service_client_api";
    public static final java.lang.String FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED = "android.app.system_terms_of_address_enabled";
    public static final java.lang.String FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS = "android.app.uid_importance_listener_for_uids";
    public static final java.lang.String FLAG_VISIT_RISKY_URIS = "android.app.visit_risky_uris";

    public static boolean apiTvextender() {
        return FEATURE_FLAGS.apiTvextender();
    }

    public static boolean appRestrictionsApi() {
        return FEATURE_FLAGS.appRestrictionsApi();
    }

    public static boolean appStartInfo() {
        return FEATURE_FLAGS.appStartInfo();
    }

    public static boolean bcastEventTimestamps() {
        return FEATURE_FLAGS.bcastEventTimestamps();
    }

    public static boolean bicClient() {
        return FEATURE_FLAGS.bicClient();
    }

    public static boolean categoryVoicemail() {
        return FEATURE_FLAGS.categoryVoicemail();
    }

    public static boolean enableNightModeBinderCache() {
        return FEATURE_FLAGS.enableNightModeBinderCache();
    }

    public static boolean enablePipUiStateCallbackOnEntering() {
        return FEATURE_FLAGS.enablePipUiStateCallbackOnEntering();
    }

    public static boolean evenlyDividedCallStyleActionLayout() {
        return FEATURE_FLAGS.evenlyDividedCallStyleActionLayout();
    }

    public static boolean getBindingUidImportance() {
        return FEATURE_FLAGS.getBindingUidImportance();
    }

    public static boolean introduceNewServiceOntimeoutCallback() {
        return FEATURE_FLAGS.introduceNewServiceOntimeoutCallback();
    }

    public static boolean keyguardPrivateNotifications() {
        return FEATURE_FLAGS.keyguardPrivateNotifications();
    }

    public static boolean lifetimeExtensionRefactor() {
        return FEATURE_FLAGS.lifetimeExtensionRefactor();
    }

    public static boolean modesApi() {
        return FEATURE_FLAGS.modesApi();
    }

    public static boolean modesUi() {
        return FEATURE_FLAGS.modesUi();
    }

    public static boolean notificationChannelVibrationEffectApi() {
        return FEATURE_FLAGS.notificationChannelVibrationEffectApi();
    }

    public static boolean pinnerServiceClientApi() {
        return FEATURE_FLAGS.pinnerServiceClientApi();
    }

    public static boolean systemTermsOfAddressEnabled() {
        return FEATURE_FLAGS.systemTermsOfAddressEnabled();
    }

    public static boolean uidImportanceListenerForUids() {
        return FEATURE_FLAGS.uidImportanceListenerForUids();
    }

    public static boolean visitRiskyUris() {
        return FEATURE_FLAGS.visitRiskyUris();
    }
}
