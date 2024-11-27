package com.android.window.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.window.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ALLOW_HIDE_SCM_BUTTON, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_APP_COMPAT_PROPERTIES_API, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_APP_COMPAT_REFACTORING, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_COVER_DISPLAY_OPT_IN, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_DEFER_DISPLAY_UPDATES, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_DELEGATE_UNHANDLED_DRAGS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_DELETE_CAPTURE_DISPLAY, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_DENSITY_390_API, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ENABLE_SCALED_RESIZING, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_ENFORCE_EDGE_TO_EDGE, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_FULLSCREEN_DIM_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_INTRODUCE_SMOOTHER_DIMMER, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_MAGNIFICATION_ALWAYS_DRAW_FULLSCREEN_BORDER, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_MULTI_CROP, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SCREEN_RECORDING_CALLBACKS, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SDK_DESIRED_PRESENT_TIME, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SECURE_WINDOW_STATE, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SURFACE_TRUSTED_OVERLAY, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_SYNC_SCREEN_CAPTURE, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_TRANSIT_READY_TRACKING, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT, false), java.util.Map.entry(com.android.window.flags.Flags.FLAG_WALLPAPER_OFFSET_ASYNC, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG, com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG, com.android.window.flags.Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT, com.android.window.flags.Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG, com.android.window.flags.Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK, com.android.window.flags.Flags.FLAG_ALLOW_HIDE_SCM_BUTTON, com.android.window.flags.Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT, com.android.window.flags.Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION, com.android.window.flags.Flags.FLAG_APP_COMPAT_PROPERTIES_API, com.android.window.flags.Flags.FLAG_APP_COMPAT_REFACTORING, com.android.window.flags.Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG, com.android.window.flags.Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK, com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR, com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID, com.android.window.flags.Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID, com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS, com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED, com.android.window.flags.Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG, com.android.window.flags.Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM, com.android.window.flags.Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR, com.android.window.flags.Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT, com.android.window.flags.Flags.FLAG_COVER_DISPLAY_OPT_IN, com.android.window.flags.Flags.FLAG_DEFER_DISPLAY_UPDATES, com.android.window.flags.Flags.FLAG_DELEGATE_UNHANDLED_DRAGS, com.android.window.flags.Flags.FLAG_DELETE_CAPTURE_DISPLAY, com.android.window.flags.Flags.FLAG_DENSITY_390_API, com.android.window.flags.Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS, com.android.window.flags.Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT, com.android.window.flags.Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG, com.android.window.flags.Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY, com.android.window.flags.Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE, com.android.window.flags.Flags.FLAG_ENABLE_SCALED_RESIZING, com.android.window.flags.Flags.FLAG_ENFORCE_EDGE_TO_EDGE, com.android.window.flags.Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS, com.android.window.flags.Flags.FLAG_FULLSCREEN_DIM_FLAG, com.android.window.flags.Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION, com.android.window.flags.Flags.FLAG_INTRODUCE_SMOOTHER_DIMMER, com.android.window.flags.Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER, com.android.window.flags.Flags.FLAG_MAGNIFICATION_ALWAYS_DRAW_FULLSCREEN_BORDER, com.android.window.flags.Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION, com.android.window.flags.Flags.FLAG_MULTI_CROP, com.android.window.flags.Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT, com.android.window.flags.Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS, com.android.window.flags.Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS, com.android.window.flags.Flags.FLAG_SCREEN_RECORDING_CALLBACKS, com.android.window.flags.Flags.FLAG_SDK_DESIRED_PRESENT_TIME, com.android.window.flags.Flags.FLAG_SECURE_WINDOW_STATE, com.android.window.flags.Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI, com.android.window.flags.Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER, com.android.window.flags.Flags.FLAG_SURFACE_TRUSTED_OVERLAY, com.android.window.flags.Flags.FLAG_SYNC_SCREEN_CAPTURE, com.android.window.flags.Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG, com.android.window.flags.Flags.FLAG_TRANSIT_READY_TRACKING, com.android.window.flags.Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW, com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION, com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING, com.android.window.flags.Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT, com.android.window.flags.Flags.FLAG_WALLPAPER_OFFSET_ASYNC, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingInteractiveDividerFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityEmbeddingOverlayPresentationFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activitySnapshotByDefault() {
        return getValue(com.android.window.flags.Flags.FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean activityWindowInfoFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_ACTIVITY_WINDOW_INFO_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowDisableActivityRecordInputSink() {
        return getValue(com.android.window.flags.Flags.FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowHideScmButton() {
        return getValue(com.android.window.flags.Flags.FLAG_ALLOW_HIDE_SCM_BUTTON);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean allowsScreenSizeDecoupledFromStatusBarAndCutout() {
        return getValue(com.android.window.flags.Flags.FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean alwaysUpdateWallpaperPermission() {
        return getValue(com.android.window.flags.Flags.FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean appCompatPropertiesApi() {
        return getValue(com.android.window.flags.Flags.FLAG_APP_COMPAT_PROPERTIES_API);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean appCompatRefactoring() {
        return getValue(com.android.window.flags.Flags.FLAG_APP_COMPAT_REFACTORING);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balDontBringExistingBackgroundTaskStackToFg() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balImproveRealCallerVisibilityCheck() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRequireOptInByPendingIntentCreator() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRequireOptInSameUid() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_REQUIRE_OPT_IN_SAME_UID);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balShowToasts() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean balShowToastsBlocked() {
        return getValue(com.android.window.flags.Flags.FLAG_BAL_SHOW_TOASTS_BLOCKED);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean bundleClientTransactionFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean cameraCompatForFreeform() {
        return getValue(com.android.window.flags.Flags.FLAG_CAMERA_COMPAT_FOR_FREEFORM);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean closeToSquareConfigIncludesStatusBar() {
        return getValue(com.android.window.flags.Flags.FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean configurableFontScaleDefault() {
        return getValue(com.android.window.flags.Flags.FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean coverDisplayOptIn() {
        return getValue(com.android.window.flags.Flags.FLAG_COVER_DISPLAY_OPT_IN);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean deferDisplayUpdates() {
        return getValue(com.android.window.flags.Flags.FLAG_DEFER_DISPLAY_UPDATES);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean delegateUnhandledDrags() {
        return getValue(com.android.window.flags.Flags.FLAG_DELEGATE_UNHANDLED_DRAGS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean deleteCaptureDisplay() {
        return getValue(com.android.window.flags.Flags.FLAG_DELETE_CAPTURE_DISPLAY);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean density390Api() {
        return getValue(com.android.window.flags.Flags.FLAG_DENSITY_390_API);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions() {
        return getValue(com.android.window.flags.Flags.FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean edgeToEdgeByDefault() {
        return getValue(com.android.window.flags.Flags.FLAG_EDGE_TO_EDGE_BY_DEFAULT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean embeddedActivityBackNavFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableBufferTransformHintFromDisplay() {
        return getValue(com.android.window.flags.Flags.FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableDesktopWindowingMode() {
        return getValue(com.android.window.flags.Flags.FLAG_ENABLE_DESKTOP_WINDOWING_MODE);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enableScaledResizing() {
        return getValue(com.android.window.flags.Flags.FLAG_ENABLE_SCALED_RESIZING);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean enforceEdgeToEdge() {
        return getValue(com.android.window.flags.Flags.FLAG_ENFORCE_EDGE_TO_EDGE);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean explicitRefreshRateHints() {
        return getValue(com.android.window.flags.Flags.FLAG_EXPLICIT_REFRESH_RATE_HINTS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean fullscreenDimFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_FULLSCREEN_DIM_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean insetsDecoupledConfiguration() {
        return getValue(com.android.window.flags.Flags.FLAG_INSETS_DECOUPLED_CONFIGURATION);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean introduceSmootherDimmer() {
        return getValue(com.android.window.flags.Flags.FLAG_INTRODUCE_SMOOTHER_DIMMER);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean letterboxBackgroundWallpaper() {
        return getValue(com.android.window.flags.Flags.FLAG_LETTERBOX_BACKGROUND_WALLPAPER);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean magnificationAlwaysDrawFullscreenBorder() {
        return getValue(com.android.window.flags.Flags.FLAG_MAGNIFICATION_ALWAYS_DRAW_FULLSCREEN_BORDER);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean movableCutoutConfiguration() {
        return getValue(com.android.window.flags.Flags.FLAG_MOVABLE_CUTOUT_CONFIGURATION);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean multiCrop() {
        return getValue(com.android.window.flags.Flags.FLAG_MULTI_CROP);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean navBarTransparentByDefault() {
        return getValue(com.android.window.flags.Flags.FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean noConsecutiveVisibilityEvents() {
        return getValue(com.android.window.flags.Flags.FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean predictiveBackSystemAnims() {
        return getValue(com.android.window.flags.Flags.FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean screenRecordingCallbacks() {
        return getValue(com.android.window.flags.Flags.FLAG_SCREEN_RECORDING_CALLBACKS);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean sdkDesiredPresentTime() {
        return getValue(com.android.window.flags.Flags.FLAG_SDK_DESIRED_PRESENT_TIME);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean secureWindowState() {
        return getValue(com.android.window.flags.Flags.FLAG_SECURE_WINDOW_STATE);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean supportsMultiInstanceSystemUi() {
        return getValue(com.android.window.flags.Flags.FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean surfaceControlInputReceiver() {
        return getValue(com.android.window.flags.Flags.FLAG_SURFACE_CONTROL_INPUT_RECEIVER);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean surfaceTrustedOverlay() {
        return getValue(com.android.window.flags.Flags.FLAG_SURFACE_TRUSTED_OVERLAY);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean syncScreenCapture() {
        return getValue(com.android.window.flags.Flags.FLAG_SYNC_SCREEN_CAPTURE);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean taskFragmentSystemOrganizerFlag() {
        return getValue(com.android.window.flags.Flags.FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean transitReadyTracking() {
        return getValue(com.android.window.flags.Flags.FLAG_TRANSIT_READY_TRACKING);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean trustedPresentationListenerForWindow() {
        return getValue(com.android.window.flags.Flags.FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingAnyAppPermission() {
        return getValue(com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean untrustedEmbeddingStateSharing() {
        return getValue(com.android.window.flags.Flags.FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean userMinAspectRatioAppDefault() {
        return getValue(com.android.window.flags.Flags.FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT);
    }

    @Override // com.android.window.flags.FeatureFlags
    public boolean wallpaperOffsetAsync() {
        return getValue(com.android.window.flags.Flags.FLAG_WALLPAPER_OFFSET_ASYNC);
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
