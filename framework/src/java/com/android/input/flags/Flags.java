package com.android.input.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.input.flags.FeatureFlags FEATURE_FLAGS = new com.android.input.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM = "com.android.input.flags.a11y_crash_on_inconsistent_event_stream";
    public static final java.lang.String FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER = "com.android.input.flags.disable_reject_touch_on_stylus_hover";
    public static final java.lang.String FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER = "com.android.input.flags.enable_gestures_library_timer_provider";
    public static final java.lang.String FLAG_ENABLE_INBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_inbound_event_verification";
    public static final java.lang.String FLAG_ENABLE_INPUT_EVENT_TRACING = "com.android.input.flags.enable_input_event_tracing";
    public static final java.lang.String FLAG_ENABLE_INPUT_FILTER_RUST_IMPL = "com.android.input.flags.enable_input_filter_rust_impl";
    public static final java.lang.String FLAG_ENABLE_MULTI_DEVICE_INPUT = "com.android.input.flags.enable_multi_device_input";
    public static final java.lang.String FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS = "com.android.input.flags.enable_new_mouse_pointer_ballistics";
    public static final java.lang.String FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_outbound_event_verification";
    public static final java.lang.String FLAG_ENABLE_POINTER_CHOREOGRAPHER = "com.android.input.flags.enable_pointer_choreographer";
    public static final java.lang.String FLAG_ENABLE_TOUCHPAD_FLING_STOP = "com.android.input.flags.enable_touchpad_fling_stop";
    public static final java.lang.String FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION = "com.android.input.flags.enable_touchpad_typing_palm_rejection";
    public static final java.lang.String FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION = "com.android.input.flags.enable_v2_touchpad_typing_palm_rejection";
    public static final java.lang.String FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API = "com.android.input.flags.input_device_view_behavior_api";
    public static final java.lang.String FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS = "com.android.input.flags.override_key_behavior_permission_apis";
    public static final java.lang.String FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER = "com.android.input.flags.rate_limit_user_activity_poke_in_dispatcher";
    public static final java.lang.String FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM = "com.android.input.flags.remove_pointer_event_tracking_in_wm";
    public static final java.lang.String FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY = "com.android.input.flags.report_palms_to_gestures_library";

    public static boolean a11yCrashOnInconsistentEventStream() {
        return FEATURE_FLAGS.a11yCrashOnInconsistentEventStream();
    }

    public static boolean disableRejectTouchOnStylusHover() {
        return FEATURE_FLAGS.disableRejectTouchOnStylusHover();
    }

    public static boolean enableGesturesLibraryTimerProvider() {
        return FEATURE_FLAGS.enableGesturesLibraryTimerProvider();
    }

    public static boolean enableInboundEventVerification() {
        return FEATURE_FLAGS.enableInboundEventVerification();
    }

    public static boolean enableInputEventTracing() {
        return FEATURE_FLAGS.enableInputEventTracing();
    }

    public static boolean enableInputFilterRustImpl() {
        return FEATURE_FLAGS.enableInputFilterRustImpl();
    }

    public static boolean enableMultiDeviceInput() {
        return FEATURE_FLAGS.enableMultiDeviceInput();
    }

    public static boolean enableNewMousePointerBallistics() {
        return FEATURE_FLAGS.enableNewMousePointerBallistics();
    }

    public static boolean enableOutboundEventVerification() {
        return FEATURE_FLAGS.enableOutboundEventVerification();
    }

    public static boolean enablePointerChoreographer() {
        return FEATURE_FLAGS.enablePointerChoreographer();
    }

    public static boolean enableTouchpadFlingStop() {
        return FEATURE_FLAGS.enableTouchpadFlingStop();
    }

    public static boolean enableTouchpadTypingPalmRejection() {
        return FEATURE_FLAGS.enableTouchpadTypingPalmRejection();
    }

    public static boolean enableV2TouchpadTypingPalmRejection() {
        return FEATURE_FLAGS.enableV2TouchpadTypingPalmRejection();
    }

    public static boolean inputDeviceViewBehaviorApi() {
        return FEATURE_FLAGS.inputDeviceViewBehaviorApi();
    }

    public static boolean overrideKeyBehaviorPermissionApis() {
        return FEATURE_FLAGS.overrideKeyBehaviorPermissionApis();
    }

    public static boolean rateLimitUserActivityPokeInDispatcher() {
        return FEATURE_FLAGS.rateLimitUserActivityPokeInDispatcher();
    }

    public static boolean removePointerEventTrackingInWm() {
        return FEATURE_FLAGS.removePointerEventTrackingInWm();
    }

    public static boolean reportPalmsToGesturesLibrary() {
        return FEATURE_FLAGS.reportPalmsToGesturesLibrary();
    }
}
