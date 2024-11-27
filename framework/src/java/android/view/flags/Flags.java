package android.view.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.view.flags.FeatureFlags FEATURE_FLAGS = new android.view.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CUSTOMIZABLE_WINDOW_HEADERS = "android.view.flags.customizable_window_headers";
    public static final java.lang.String FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE = "android.view.flags.enable_arrow_icon_on_hover_when_clickable";
    public static final java.lang.String FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO = "android.view.flags.enable_surface_native_alloc_registration_ro";
    public static final java.lang.String FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT = "android.view.flags.enable_use_measure_cache_during_force_layout";
    public static final java.lang.String FLAG_ENABLE_VECTOR_CURSORS = "android.view.flags.enable_vector_cursors";
    public static final java.lang.String FLAG_EXPECTED_PRESENTATION_TIME_API = "android.view.flags.expected_presentation_time_api";
    public static final java.lang.String FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY = "android.view.flags.expected_presentation_time_read_only";
    public static final java.lang.String FLAG_SCROLL_FEEDBACK_API = "android.view.flags.scroll_feedback_api";
    public static final java.lang.String FLAG_SENSITIVE_CONTENT_APP_PROTECTION = "android.view.flags.sensitive_content_app_protection";
    public static final java.lang.String FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API = "android.view.flags.sensitive_content_app_protection_api";
    public static final java.lang.String FLAG_SET_FRAME_RATE_CALLBACK = "android.view.flags.set_frame_rate_callback";
    public static final java.lang.String FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY = "android.view.flags.toolkit_frame_rate_by_size_read_only";
    public static final java.lang.String FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY = "android.view.flags.toolkit_frame_rate_default_normal_read_only";
    public static final java.lang.String FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY = "android.view.flags.toolkit_frame_rate_typing_read_only";
    public static final java.lang.String FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY = "android.view.flags.toolkit_frame_rate_velocity_mapping_read_only";
    public static final java.lang.String FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION = "android.view.flags.toolkit_metrics_for_frame_rate_decision";
    public static final java.lang.String FLAG_TOOLKIT_SET_FRAME_RATE = "android.view.flags.toolkit_set_frame_rate";
    public static final java.lang.String FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY = "android.view.flags.toolkit_set_frame_rate_read_only";
    public static final java.lang.String FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS = "android.view.flags.use_view_based_rotary_encoder_scroll_haptics";
    public static final java.lang.String FLAG_VIEW_VELOCITY_API = "android.view.flags.view_velocity_api";

    public static boolean customizableWindowHeaders() {
        return FEATURE_FLAGS.customizableWindowHeaders();
    }

    public static boolean enableArrowIconOnHoverWhenClickable() {
        return FEATURE_FLAGS.enableArrowIconOnHoverWhenClickable();
    }

    public static boolean enableSurfaceNativeAllocRegistrationRo() {
        return FEATURE_FLAGS.enableSurfaceNativeAllocRegistrationRo();
    }

    public static boolean enableUseMeasureCacheDuringForceLayout() {
        return FEATURE_FLAGS.enableUseMeasureCacheDuringForceLayout();
    }

    public static boolean enableVectorCursors() {
        return FEATURE_FLAGS.enableVectorCursors();
    }

    public static boolean expectedPresentationTimeApi() {
        return FEATURE_FLAGS.expectedPresentationTimeApi();
    }

    public static boolean expectedPresentationTimeReadOnly() {
        return FEATURE_FLAGS.expectedPresentationTimeReadOnly();
    }

    public static boolean scrollFeedbackApi() {
        return FEATURE_FLAGS.scrollFeedbackApi();
    }

    public static boolean sensitiveContentAppProtection() {
        return FEATURE_FLAGS.sensitiveContentAppProtection();
    }

    public static boolean sensitiveContentAppProtectionApi() {
        return FEATURE_FLAGS.sensitiveContentAppProtectionApi();
    }

    public static boolean setFrameRateCallback() {
        return FEATURE_FLAGS.setFrameRateCallback();
    }

    public static boolean toolkitFrameRateBySizeReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateBySizeReadOnly();
    }

    public static boolean toolkitFrameRateDefaultNormalReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateDefaultNormalReadOnly();
    }

    public static boolean toolkitFrameRateTypingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateTypingReadOnly();
    }

    public static boolean toolkitFrameRateVelocityMappingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateVelocityMappingReadOnly();
    }

    public static boolean toolkitMetricsForFrameRateDecision() {
        return FEATURE_FLAGS.toolkitMetricsForFrameRateDecision();
    }

    public static boolean toolkitSetFrameRate() {
        return FEATURE_FLAGS.toolkitSetFrameRate();
    }

    public static boolean toolkitSetFrameRateReadOnly() {
        return FEATURE_FLAGS.toolkitSetFrameRateReadOnly();
    }

    public static boolean useViewBasedRotaryEncoderScrollHaptics() {
        return FEATURE_FLAGS.useViewBasedRotaryEncoderScrollHaptics();
    }

    public static boolean viewVelocityApi() {
        return FEATURE_FLAGS.viewVelocityApi();
    }
}
