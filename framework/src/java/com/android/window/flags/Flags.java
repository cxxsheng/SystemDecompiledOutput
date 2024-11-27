package com.android.window.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.window.flags.FeatureFlags FEATURE_FLAGS = new com.android.window.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG = "com.android.window.flags.activity_embedding_interactive_divider_flag";
    public static final java.lang.String FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG = "com.android.window.flags.activity_embedding_overlay_presentation_flag";
    public static final java.lang.String FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT = "com.android.window.flags.activity_snapshot_by_default";
    public static final java.lang.String FLAG_ACTIVITY_WINDOW_INFO_FLAG = "com.android.window.flags.activity_window_info_flag";
    public static final java.lang.String FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT = "com.android.window.flags.allows_screen_size_decoupled_from_status_bar_and_cutout";
    public static final java.lang.String FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK = "com.android.window.flags.allow_disable_activity_record_input_sink";
    public static final java.lang.String FLAG_ALLOW_HIDE_SCM_BUTTON = "com.android.window.flags.allow_hide_scm_button";
    public static final java.lang.String FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION = "com.android.window.flags.always_update_wallpaper_permission";
    public static final java.lang.String FLAG_APP_COMPAT_PROPERTIES_API = "com.android.window.flags.app_compat_properties_api";
    public static final java.lang.String FLAG_APP_COMPAT_REFACTORING = "com.android.window.flags.app_compat_refactoring";
    public static final java.lang.String FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG = "com.android.window.flags.bal_dont_bring_existing_background_task_stack_to_fg";
    public static final java.lang.String FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK = "com.android.window.flags.bal_improve_real_caller_visibility_check";
    public static final java.lang.String FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR = "com.android.window.flags.bal_require_opt_in_by_pending_intent_creator";
    public static final java.lang.String FLAG_BAL_REQUIRE_OPT_IN_SAME_UID = "com.android.window.flags.bal_require_opt_in_same_uid";
    public static final java.lang.String FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID = "com.android.window.flags.bal_respect_app_switch_state_when_check_bound_by_foreground_uid";
    public static final java.lang.String FLAG_BAL_SHOW_TOASTS = "com.android.window.flags.bal_show_toasts";
    public static final java.lang.String FLAG_BAL_SHOW_TOASTS_BLOCKED = "com.android.window.flags.bal_show_toasts_blocked";
    public static final java.lang.String FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG = "com.android.window.flags.bundle_client_transaction_flag";
    public static final java.lang.String FLAG_CAMERA_COMPAT_FOR_FREEFORM = "com.android.window.flags.camera_compat_for_freeform";
    public static final java.lang.String FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR = "com.android.window.flags.close_to_square_config_includes_status_bar";
    public static final java.lang.String FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT = "com.android.window.flags.configurable_font_scale_default";
    public static final java.lang.String FLAG_COVER_DISPLAY_OPT_IN = "com.android.window.flags.cover_display_opt_in";
    public static final java.lang.String FLAG_DEFER_DISPLAY_UPDATES = "com.android.window.flags.defer_display_updates";
    public static final java.lang.String FLAG_DELEGATE_UNHANDLED_DRAGS = "com.android.window.flags.delegate_unhandled_drags";
    public static final java.lang.String FLAG_DELETE_CAPTURE_DISPLAY = "com.android.window.flags.delete_capture_display";
    public static final java.lang.String FLAG_DENSITY_390_API = "com.android.window.flags.density_390_api";
    public static final java.lang.String FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS = "com.android.window.flags.do_not_check_intersection_when_non_magnifiable_window_transitions";
    public static final java.lang.String FLAG_EDGE_TO_EDGE_BY_DEFAULT = "com.android.window.flags.edge_to_edge_by_default";
    public static final java.lang.String FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG = "com.android.window.flags.embedded_activity_back_nav_flag";
    public static final java.lang.String FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY = "com.android.window.flags.enable_buffer_transform_hint_from_display";
    public static final java.lang.String FLAG_ENABLE_DESKTOP_WINDOWING_MODE = "com.android.window.flags.enable_desktop_windowing_mode";
    public static final java.lang.String FLAG_ENABLE_SCALED_RESIZING = "com.android.window.flags.enable_scaled_resizing";
    public static final java.lang.String FLAG_ENFORCE_EDGE_TO_EDGE = "com.android.window.flags.enforce_edge_to_edge";
    public static final java.lang.String FLAG_EXPLICIT_REFRESH_RATE_HINTS = "com.android.window.flags.explicit_refresh_rate_hints";
    public static final java.lang.String FLAG_FULLSCREEN_DIM_FLAG = "com.android.window.flags.fullscreen_dim_flag";
    public static final java.lang.String FLAG_INSETS_DECOUPLED_CONFIGURATION = "com.android.window.flags.insets_decoupled_configuration";
    public static final java.lang.String FLAG_INTRODUCE_SMOOTHER_DIMMER = "com.android.window.flags.introduce_smoother_dimmer";
    public static final java.lang.String FLAG_LETTERBOX_BACKGROUND_WALLPAPER = "com.android.window.flags.letterbox_background_wallpaper";
    public static final java.lang.String FLAG_MAGNIFICATION_ALWAYS_DRAW_FULLSCREEN_BORDER = "com.android.window.flags.magnification_always_draw_fullscreen_border";
    public static final java.lang.String FLAG_MOVABLE_CUTOUT_CONFIGURATION = "com.android.window.flags.movable_cutout_configuration";
    public static final java.lang.String FLAG_MULTI_CROP = "com.android.window.flags.multi_crop";
    public static final java.lang.String FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT = "com.android.window.flags.nav_bar_transparent_by_default";
    public static final java.lang.String FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS = "com.android.window.flags.no_consecutive_visibility_events";
    public static final java.lang.String FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS = "com.android.window.flags.predictive_back_system_anims";
    public static final java.lang.String FLAG_SCREEN_RECORDING_CALLBACKS = "com.android.window.flags.screen_recording_callbacks";
    public static final java.lang.String FLAG_SDK_DESIRED_PRESENT_TIME = "com.android.window.flags.sdk_desired_present_time";
    public static final java.lang.String FLAG_SECURE_WINDOW_STATE = "com.android.window.flags.secure_window_state";
    public static final java.lang.String FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI = "com.android.window.flags.supports_multi_instance_system_ui";
    public static final java.lang.String FLAG_SURFACE_CONTROL_INPUT_RECEIVER = "com.android.window.flags.surface_control_input_receiver";
    public static final java.lang.String FLAG_SURFACE_TRUSTED_OVERLAY = "com.android.window.flags.surface_trusted_overlay";
    public static final java.lang.String FLAG_SYNC_SCREEN_CAPTURE = "com.android.window.flags.sync_screen_capture";
    public static final java.lang.String FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG = "com.android.window.flags.task_fragment_system_organizer_flag";
    public static final java.lang.String FLAG_TRANSIT_READY_TRACKING = "com.android.window.flags.transit_ready_tracking";
    public static final java.lang.String FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW = "com.android.window.flags.trusted_presentation_listener_for_window";
    public static final java.lang.String FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION = "com.android.window.flags.untrusted_embedding_any_app_permission";
    public static final java.lang.String FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING = "com.android.window.flags.untrusted_embedding_state_sharing";
    public static final java.lang.String FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT = "com.android.window.flags.user_min_aspect_ratio_app_default";
    public static final java.lang.String FLAG_WALLPAPER_OFFSET_ASYNC = "com.android.window.flags.wallpaper_offset_async";

    public static boolean activityEmbeddingInteractiveDividerFlag() {
        return FEATURE_FLAGS.activityEmbeddingInteractiveDividerFlag();
    }

    public static boolean activityEmbeddingOverlayPresentationFlag() {
        return FEATURE_FLAGS.activityEmbeddingOverlayPresentationFlag();
    }

    public static boolean activitySnapshotByDefault() {
        return FEATURE_FLAGS.activitySnapshotByDefault();
    }

    public static boolean activityWindowInfoFlag() {
        return FEATURE_FLAGS.activityWindowInfoFlag();
    }

    public static boolean allowDisableActivityRecordInputSink() {
        return FEATURE_FLAGS.allowDisableActivityRecordInputSink();
    }

    public static boolean allowHideScmButton() {
        return FEATURE_FLAGS.allowHideScmButton();
    }

    public static boolean allowsScreenSizeDecoupledFromStatusBarAndCutout() {
        return FEATURE_FLAGS.allowsScreenSizeDecoupledFromStatusBarAndCutout();
    }

    public static boolean alwaysUpdateWallpaperPermission() {
        return FEATURE_FLAGS.alwaysUpdateWallpaperPermission();
    }

    public static boolean appCompatPropertiesApi() {
        return FEATURE_FLAGS.appCompatPropertiesApi();
    }

    public static boolean appCompatRefactoring() {
        return FEATURE_FLAGS.appCompatRefactoring();
    }

    public static boolean balDontBringExistingBackgroundTaskStackToFg() {
        return FEATURE_FLAGS.balDontBringExistingBackgroundTaskStackToFg();
    }

    public static boolean balImproveRealCallerVisibilityCheck() {
        return FEATURE_FLAGS.balImproveRealCallerVisibilityCheck();
    }

    public static boolean balRequireOptInByPendingIntentCreator() {
        return FEATURE_FLAGS.balRequireOptInByPendingIntentCreator();
    }

    public static boolean balRequireOptInSameUid() {
        return FEATURE_FLAGS.balRequireOptInSameUid();
    }

    public static boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid() {
        return FEATURE_FLAGS.balRespectAppSwitchStateWhenCheckBoundByForegroundUid();
    }

    public static boolean balShowToasts() {
        return FEATURE_FLAGS.balShowToasts();
    }

    public static boolean balShowToastsBlocked() {
        return FEATURE_FLAGS.balShowToastsBlocked();
    }

    public static boolean bundleClientTransactionFlag() {
        return FEATURE_FLAGS.bundleClientTransactionFlag();
    }

    public static boolean cameraCompatForFreeform() {
        return FEATURE_FLAGS.cameraCompatForFreeform();
    }

    public static boolean closeToSquareConfigIncludesStatusBar() {
        return FEATURE_FLAGS.closeToSquareConfigIncludesStatusBar();
    }

    public static boolean configurableFontScaleDefault() {
        return FEATURE_FLAGS.configurableFontScaleDefault();
    }

    public static boolean coverDisplayOptIn() {
        return FEATURE_FLAGS.coverDisplayOptIn();
    }

    public static boolean deferDisplayUpdates() {
        return FEATURE_FLAGS.deferDisplayUpdates();
    }

    public static boolean delegateUnhandledDrags() {
        return FEATURE_FLAGS.delegateUnhandledDrags();
    }

    public static boolean deleteCaptureDisplay() {
        return FEATURE_FLAGS.deleteCaptureDisplay();
    }

    public static boolean density390Api() {
        return FEATURE_FLAGS.density390Api();
    }

    public static boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions() {
        return FEATURE_FLAGS.doNotCheckIntersectionWhenNonMagnifiableWindowTransitions();
    }

    public static boolean edgeToEdgeByDefault() {
        return FEATURE_FLAGS.edgeToEdgeByDefault();
    }

    public static boolean embeddedActivityBackNavFlag() {
        return FEATURE_FLAGS.embeddedActivityBackNavFlag();
    }

    public static boolean enableBufferTransformHintFromDisplay() {
        return FEATURE_FLAGS.enableBufferTransformHintFromDisplay();
    }

    public static boolean enableDesktopWindowingMode() {
        return FEATURE_FLAGS.enableDesktopWindowingMode();
    }

    public static boolean enableScaledResizing() {
        return FEATURE_FLAGS.enableScaledResizing();
    }

    public static boolean enforceEdgeToEdge() {
        return FEATURE_FLAGS.enforceEdgeToEdge();
    }

    public static boolean explicitRefreshRateHints() {
        return FEATURE_FLAGS.explicitRefreshRateHints();
    }

    public static boolean fullscreenDimFlag() {
        return FEATURE_FLAGS.fullscreenDimFlag();
    }

    public static boolean insetsDecoupledConfiguration() {
        return FEATURE_FLAGS.insetsDecoupledConfiguration();
    }

    public static boolean introduceSmootherDimmer() {
        return FEATURE_FLAGS.introduceSmootherDimmer();
    }

    public static boolean letterboxBackgroundWallpaper() {
        return FEATURE_FLAGS.letterboxBackgroundWallpaper();
    }

    public static boolean magnificationAlwaysDrawFullscreenBorder() {
        return FEATURE_FLAGS.magnificationAlwaysDrawFullscreenBorder();
    }

    public static boolean movableCutoutConfiguration() {
        return FEATURE_FLAGS.movableCutoutConfiguration();
    }

    public static boolean multiCrop() {
        return FEATURE_FLAGS.multiCrop();
    }

    public static boolean navBarTransparentByDefault() {
        return FEATURE_FLAGS.navBarTransparentByDefault();
    }

    public static boolean noConsecutiveVisibilityEvents() {
        return FEATURE_FLAGS.noConsecutiveVisibilityEvents();
    }

    public static boolean predictiveBackSystemAnims() {
        return FEATURE_FLAGS.predictiveBackSystemAnims();
    }

    public static boolean screenRecordingCallbacks() {
        return FEATURE_FLAGS.screenRecordingCallbacks();
    }

    public static boolean sdkDesiredPresentTime() {
        return FEATURE_FLAGS.sdkDesiredPresentTime();
    }

    public static boolean secureWindowState() {
        return FEATURE_FLAGS.secureWindowState();
    }

    public static boolean supportsMultiInstanceSystemUi() {
        return FEATURE_FLAGS.supportsMultiInstanceSystemUi();
    }

    public static boolean surfaceControlInputReceiver() {
        return FEATURE_FLAGS.surfaceControlInputReceiver();
    }

    public static boolean surfaceTrustedOverlay() {
        return FEATURE_FLAGS.surfaceTrustedOverlay();
    }

    public static boolean syncScreenCapture() {
        return FEATURE_FLAGS.syncScreenCapture();
    }

    public static boolean taskFragmentSystemOrganizerFlag() {
        return FEATURE_FLAGS.taskFragmentSystemOrganizerFlag();
    }

    public static boolean transitReadyTracking() {
        return FEATURE_FLAGS.transitReadyTracking();
    }

    public static boolean trustedPresentationListenerForWindow() {
        return FEATURE_FLAGS.trustedPresentationListenerForWindow();
    }

    public static boolean untrustedEmbeddingAnyAppPermission() {
        return FEATURE_FLAGS.untrustedEmbeddingAnyAppPermission();
    }

    public static boolean untrustedEmbeddingStateSharing() {
        return FEATURE_FLAGS.untrustedEmbeddingStateSharing();
    }

    public static boolean userMinAspectRatioAppDefault() {
        return FEATURE_FLAGS.userMinAspectRatioAppDefault();
    }

    public static boolean wallpaperOffsetAsync() {
        return FEATURE_FLAGS.wallpaperOffsetAsync();
    }
}
