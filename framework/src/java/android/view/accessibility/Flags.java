package android.view.accessibility;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.view.accessibility.FeatureFlags FEATURE_FLAGS = new android.view.accessibility.FeatureFlagsImpl();
    public static final java.lang.String FLAG_A11Y_OVERLAY_CALLBACKS = "android.view.accessibility.a11y_overlay_callbacks";
    public static final java.lang.String FLAG_A11Y_QS_SHORTCUT = "android.view.accessibility.a11y_qs_shortcut";
    public static final java.lang.String FLAG_ADD_TYPE_WINDOW_CONTROL = "android.view.accessibility.add_type_window_control";
    public static final java.lang.String FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN = "android.view.accessibility.allow_shortcut_chooser_on_lockscreen";
    public static final java.lang.String FLAG_BRAILLE_DISPLAY_HID = "android.view.accessibility.braille_display_hid";
    public static final java.lang.String FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG = "android.view.accessibility.cleanup_accessibility_warning_dialog";
    public static final java.lang.String FLAG_COLLECTION_INFO_ITEM_COUNTS = "android.view.accessibility.collection_info_item_counts";
    public static final java.lang.String FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION = "android.view.accessibility.copy_events_for_gesture_detection";
    public static final java.lang.String FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE = "android.view.accessibility.enable_system_pinch_zoom_gesture";
    public static final java.lang.String FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT = "android.view.accessibility.fix_merged_content_change_event";
    public static final java.lang.String FLAG_FLASH_NOTIFICATION_SYSTEM_API = "android.view.accessibility.flash_notification_system_api";
    public static final java.lang.String FLAG_FORCE_INVERT_COLOR = "android.view.accessibility.force_invert_color";
    public static final java.lang.String FLAG_GRANULAR_SCROLLING = "android.view.accessibility.granular_scrolling";
    public static final java.lang.String FLAG_MOTION_EVENT_OBSERVING = "android.view.accessibility.motion_event_observing";
    public static final java.lang.String FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE = "android.view.accessibility.reduce_window_content_changed_event_throttle";
    public static final java.lang.String FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES = "android.view.accessibility.skip_accessibility_warning_dialog_for_trusted_services";
    public static final java.lang.String FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS = "android.view.accessibility.support_system_pinch_zoom_opt_out_apis";
    public static final java.lang.String FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE = "android.view.accessibility.update_always_on_a11y_service";

    public static boolean a11yOverlayCallbacks() {
        return FEATURE_FLAGS.a11yOverlayCallbacks();
    }

    public static boolean a11yQsShortcut() {
        return FEATURE_FLAGS.a11yQsShortcut();
    }

    public static boolean addTypeWindowControl() {
        return FEATURE_FLAGS.addTypeWindowControl();
    }

    public static boolean allowShortcutChooserOnLockscreen() {
        return FEATURE_FLAGS.allowShortcutChooserOnLockscreen();
    }

    public static boolean brailleDisplayHid() {
        return FEATURE_FLAGS.brailleDisplayHid();
    }

    public static boolean cleanupAccessibilityWarningDialog() {
        return FEATURE_FLAGS.cleanupAccessibilityWarningDialog();
    }

    public static boolean collectionInfoItemCounts() {
        return FEATURE_FLAGS.collectionInfoItemCounts();
    }

    public static boolean copyEventsForGestureDetection() {
        return FEATURE_FLAGS.copyEventsForGestureDetection();
    }

    public static boolean enableSystemPinchZoomGesture() {
        return FEATURE_FLAGS.enableSystemPinchZoomGesture();
    }

    public static boolean fixMergedContentChangeEvent() {
        return FEATURE_FLAGS.fixMergedContentChangeEvent();
    }

    public static boolean flashNotificationSystemApi() {
        return FEATURE_FLAGS.flashNotificationSystemApi();
    }

    public static boolean forceInvertColor() {
        return FEATURE_FLAGS.forceInvertColor();
    }

    public static boolean granularScrolling() {
        return FEATURE_FLAGS.granularScrolling();
    }

    public static boolean motionEventObserving() {
        return FEATURE_FLAGS.motionEventObserving();
    }

    public static boolean reduceWindowContentChangedEventThrottle() {
        return FEATURE_FLAGS.reduceWindowContentChangedEventThrottle();
    }

    public static boolean skipAccessibilityWarningDialogForTrustedServices() {
        return FEATURE_FLAGS.skipAccessibilityWarningDialogForTrustedServices();
    }

    public static boolean supportSystemPinchZoomOptOutApis() {
        return FEATURE_FLAGS.supportSystemPinchZoomOptOutApis();
    }

    public static boolean updateAlwaysOnA11yService() {
        return FEATURE_FLAGS.updateAlwaysOnA11yService();
    }
}
