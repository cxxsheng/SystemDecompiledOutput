package com.android.server.accessibility;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.accessibility.FeatureFlags FEATURE_FLAGS = new com.android.server.accessibility.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADD_WINDOW_TOKEN_WITHOUT_LOCK = "com.android.server.accessibility.add_window_token_without_lock";
    public static final java.lang.String FLAG_CLEANUP_A11Y_OVERLAYS = "com.android.server.accessibility.cleanup_a11y_overlays";
    public static final java.lang.String FLAG_COMPUTE_WINDOW_CHANGES_ON_A11Y = "com.android.server.accessibility.compute_window_changes_on_a11y";
    public static final java.lang.String FLAG_DEPRECATE_PACKAGE_LIST_OBSERVER = "com.android.server.accessibility.deprecate_package_list_observer";
    public static final java.lang.String FLAG_DISABLE_CONTINUOUS_SHORTCUT_ON_FORCE_STOP = "com.android.server.accessibility.disable_continuous_shortcut_on_force_stop";
    public static final java.lang.String FLAG_ENABLE_MAGNIFICATION_JOYSTICK = "com.android.server.accessibility.enable_magnification_joystick";
    public static final java.lang.String FLAG_ENABLE_MAGNIFICATION_MULTIPLE_FINGER_MULTIPLE_TAP_GESTURE = "com.android.server.accessibility.enable_magnification_multiple_finger_multiple_tap_gesture";
    public static final java.lang.String FLAG_FIX_DRAG_POINTER_WHEN_ENDING_DRAG = "com.android.server.accessibility.fix_drag_pointer_when_ending_drag";
    public static final java.lang.String FLAG_FULLSCREEN_FLING_GESTURE = "com.android.server.accessibility.fullscreen_fling_gesture";
    public static final java.lang.String FLAG_HANDLE_MULTI_DEVICE_INPUT = "com.android.server.accessibility.handle_multi_device_input";
    public static final java.lang.String FLAG_PINCH_ZOOM_ZERO_MIN_SPAN = "com.android.server.accessibility.pinch_zoom_zero_min_span";
    public static final java.lang.String FLAG_PROXY_USE_APPS_ON_VIRTUAL_DEVICE_LISTENER = "com.android.server.accessibility.proxy_use_apps_on_virtual_device_listener";
    public static final java.lang.String FLAG_RESETTABLE_DYNAMIC_PROPERTIES = "com.android.server.accessibility.resettable_dynamic_properties";
    public static final java.lang.String FLAG_RESET_HOVER_EVENT_TIMER_ON_ACTION_UP = "com.android.server.accessibility.reset_hover_event_timer_on_action_up";
    public static final java.lang.String FLAG_SCAN_PACKAGES_WITHOUT_LOCK = "com.android.server.accessibility.scan_packages_without_lock";
    public static final java.lang.String FLAG_SEND_A11Y_EVENTS_BASED_ON_STATE = "com.android.server.accessibility.send_a11y_events_based_on_state";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean addWindowTokenWithoutLock() {
        FEATURE_FLAGS.addWindowTokenWithoutLock();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean cleanupA11yOverlays() {
        FEATURE_FLAGS.cleanupA11yOverlays();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean computeWindowChangesOnA11y() {
        FEATURE_FLAGS.computeWindowChangesOnA11y();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean deprecatePackageListObserver() {
        FEATURE_FLAGS.deprecatePackageListObserver();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean disableContinuousShortcutOnForceStop() {
        FEATURE_FLAGS.disableContinuousShortcutOnForceStop();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableMagnificationJoystick() {
        FEATURE_FLAGS.enableMagnificationJoystick();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableMagnificationMultipleFingerMultipleTapGesture() {
        FEATURE_FLAGS.enableMagnificationMultipleFingerMultipleTapGesture();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean fixDragPointerWhenEndingDrag() {
        FEATURE_FLAGS.fixDragPointerWhenEndingDrag();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean fullscreenFlingGesture() {
        FEATURE_FLAGS.fullscreenFlingGesture();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean handleMultiDeviceInput() {
        FEATURE_FLAGS.handleMultiDeviceInput();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean pinchZoomZeroMinSpan() {
        FEATURE_FLAGS.pinchZoomZeroMinSpan();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean proxyUseAppsOnVirtualDeviceListener() {
        FEATURE_FLAGS.proxyUseAppsOnVirtualDeviceListener();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean resetHoverEventTimerOnActionUp() {
        FEATURE_FLAGS.resetHoverEventTimerOnActionUp();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean resettableDynamicProperties() {
        FEATURE_FLAGS.resettableDynamicProperties();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean scanPackagesWithoutLock() {
        FEATURE_FLAGS.scanPackagesWithoutLock();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean sendA11yEventsBasedOnState() {
        FEATURE_FLAGS.sendA11yEventsBasedOnState();
        return false;
    }
}
