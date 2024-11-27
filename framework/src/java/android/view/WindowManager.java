package android.view;

/* loaded from: classes4.dex */
public interface WindowManager extends android.view.ViewManager {
    public static final int COMPAT_SMALL_COVER_SCREEN_OPT_IN = 1;

    @android.annotation.SystemApi
    public static final int DISPLAY_IME_POLICY_FALLBACK_DISPLAY = 1;

    @android.annotation.SystemApi
    public static final int DISPLAY_IME_POLICY_HIDE = 2;

    @android.annotation.SystemApi
    public static final int DISPLAY_IME_POLICY_LOCAL = 0;
    public static final int DOCKED_BOTTOM = 4;
    public static final int DOCKED_INVALID = -1;
    public static final int DOCKED_LEFT = 1;
    public static final int DOCKED_RIGHT = 3;
    public static final int DOCKED_TOP = 2;
    public static final java.lang.String INPUT_CONSUMER_NAVIGATION = "nav_input_consumer";
    public static final java.lang.String INPUT_CONSUMER_PIP = "pip_input_consumer";
    public static final java.lang.String INPUT_CONSUMER_RECENTS_ANIMATION = "recents_animation_input_consumer";
    public static final java.lang.String INPUT_CONSUMER_WALLPAPER = "wallpaper_input_consumer";
    public static final int KEYGUARD_VISIBILITY_TRANSIT_FLAGS = 14592;
    public static final int LARGE_SCREEN_SMALLEST_SCREEN_WIDTH_DP = 600;
    public static final java.lang.String PARCEL_KEY_SHORTCUTS_ARRAY = "shortcuts_array";
    public static final java.lang.String PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE = "android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE";
    public static final java.lang.String PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED = "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED";
    public static final java.lang.String PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING = "android.window.PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING";
    public static final java.lang.String PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION";
    public static final java.lang.String PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH = "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH";
    public static final java.lang.String PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE = "android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED = "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES = "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS = "android.window.PROPERTY_COMPAT_ALLOW_SANDBOXING_VIEW_BOUNDS_APIS";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_SMALL_COVER_SCREEN = "android.window.PROPERTY_COMPAT_ALLOW_SMALL_COVER_SCREEN";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE";
    public static final java.lang.String PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE = "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE";
    public static final java.lang.String PROPERTY_COMPAT_ENABLE_FAKE_FOCUS = "android.window.PROPERTY_COMPAT_ENABLE_FAKE_FOCUS";
    public static final java.lang.String PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION = "android.window.PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION";
    public static final java.lang.String PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI = "android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI";
    public static final int REMOVE_CONTENT_MODE_DESTROY = 2;
    public static final int REMOVE_CONTENT_MODE_MOVE_TO_PRIMARY = 1;
    public static final int REMOVE_CONTENT_MODE_UNDEFINED = 0;
    public static final int SCREEN_RECORDING_STATE_NOT_VISIBLE = 0;
    public static final int SCREEN_RECORDING_STATE_VISIBLE = 1;
    public static final int SHELL_ROOT_LAYER_DIVIDER = 0;
    public static final int SHELL_ROOT_LAYER_PIP = 1;
    public static final int TAKE_SCREENSHOT_FULLSCREEN = 1;
    public static final int TAKE_SCREENSHOT_PROVIDED_IMAGE = 3;
    public static final int TAKE_SCREENSHOT_SELECTED_REGION = 2;
    public static final int TRANSIT_CHANGE = 6;
    public static final int TRANSIT_CLOSE = 2;
    public static final int TRANSIT_FIRST_CUSTOM = 1000;
    public static final int TRANSIT_FLAG_APP_CRASHED = 16;
    public static final int TRANSIT_FLAG_INVISIBLE = 1024;
    public static final int TRANSIT_FLAG_IS_RECENTS = 128;
    public static final int TRANSIT_FLAG_KEYGUARD_APPEARING = 2048;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY = 256;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION = 2;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_SUBTLE_ANIMATION = 8;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_LAUNCHER_CLEAR_SNAPSHOT = 512;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE = 1;
    public static final int TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER = 4;
    public static final int TRANSIT_FLAG_KEYGUARD_LOCKED = 64;
    public static final int TRANSIT_FLAG_KEYGUARD_OCCLUDING = 4096;
    public static final int TRANSIT_FLAG_KEYGUARD_UNOCCLUDING = 8192;
    public static final int TRANSIT_FLAG_OPEN_BEHIND = 32;
    public static final int TRANSIT_FLAG_PHYSICAL_DISPLAY_SWITCH = 16384;

    @java.lang.Deprecated
    public static final int TRANSIT_KEYGUARD_GOING_AWAY = 7;
    public static final int TRANSIT_KEYGUARD_OCCLUDE = 8;
    public static final int TRANSIT_KEYGUARD_UNOCCLUDE = 9;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_OLD_ACTIVITY_CLOSE = 7;
    public static final int TRANSIT_OLD_ACTIVITY_OPEN = 6;
    public static final int TRANSIT_OLD_ACTIVITY_RELAUNCH = 18;
    public static final int TRANSIT_OLD_CRASHING_ACTIVITY_CLOSE = 26;
    public static final int TRANSIT_OLD_DREAM_ACTIVITY_CLOSE = 32;
    public static final int TRANSIT_OLD_DREAM_ACTIVITY_OPEN = 31;
    public static final int TRANSIT_OLD_KEYGUARD_GOING_AWAY = 20;
    public static final int TRANSIT_OLD_KEYGUARD_GOING_AWAY_ON_WALLPAPER = 21;
    public static final int TRANSIT_OLD_KEYGUARD_OCCLUDE = 22;
    public static final int TRANSIT_OLD_KEYGUARD_OCCLUDE_BY_DREAM = 33;
    public static final int TRANSIT_OLD_KEYGUARD_UNOCCLUDE = 23;
    public static final int TRANSIT_OLD_NONE = 0;
    public static final int TRANSIT_OLD_TASK_CHANGE_WINDOWING_MODE = 27;
    public static final int TRANSIT_OLD_TASK_CLOSE = 9;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_CHANGE = 30;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_CLOSE = 29;
    public static final int TRANSIT_OLD_TASK_FRAGMENT_OPEN = 28;
    public static final int TRANSIT_OLD_TASK_OPEN = 8;
    public static final int TRANSIT_OLD_TASK_OPEN_BEHIND = 16;
    public static final int TRANSIT_OLD_TASK_TO_BACK = 11;
    public static final int TRANSIT_OLD_TASK_TO_FRONT = 10;
    public static final int TRANSIT_OLD_TRANSLUCENT_ACTIVITY_CLOSE = 25;
    public static final int TRANSIT_OLD_TRANSLUCENT_ACTIVITY_OPEN = 24;
    public static final int TRANSIT_OLD_UNSET = -1;
    public static final int TRANSIT_OLD_WALLPAPER_CLOSE = 12;
    public static final int TRANSIT_OLD_WALLPAPER_INTRA_CLOSE = 15;
    public static final int TRANSIT_OLD_WALLPAPER_INTRA_OPEN = 14;
    public static final int TRANSIT_OLD_WALLPAPER_OPEN = 13;
    public static final int TRANSIT_OPEN = 1;
    public static final int TRANSIT_PIP = 10;
    public static final int TRANSIT_RELAUNCH = 5;
    public static final int TRANSIT_SLEEP = 12;
    public static final int TRANSIT_TO_BACK = 4;
    public static final int TRANSIT_TO_FRONT = 3;
    public static final int TRANSIT_WAKE = 11;
    public static final boolean WINDOW_EXTENSIONS_ENABLED = android.os.SystemProperties.getBoolean("persist.wm.extensions.enabled", false);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CompatSmallScreenPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayImePolicy {
    }

    public interface KeyboardShortcutsReceiver {
        void onKeyboardShortcutsReceived(java.util.List<android.view.KeyboardShortcutGroup> list);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RemoveContentMode {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScreenRecordingState {
    }

    public @interface ScreenshotSource {
        public static final int SCREENSHOT_ACCESSIBILITY_ACTIONS = 4;
        public static final int SCREENSHOT_GLOBAL_ACTIONS = 0;
        public static final int SCREENSHOT_KEY_CHORD = 1;
        public static final int SCREENSHOT_KEY_OTHER = 2;
        public static final int SCREENSHOT_OTHER = 5;
        public static final int SCREENSHOT_OVERVIEW = 3;
        public static final int SCREENSHOT_VENDOR_GESTURE = 6;
    }

    public @interface ScreenshotType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShellRootLayer {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransitionFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransitionOldType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransitionType {
    }

    @android.annotation.SystemApi
    android.graphics.Region getCurrentImeTouchRegion();

    @java.lang.Deprecated
    android.view.Display getDefaultDisplay();

    void removeViewImmediate(android.view.View view);

    void requestAppKeyboardShortcuts(android.view.WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i);

    public static class BadTokenException extends java.lang.RuntimeException {
        public BadTokenException() {
        }

        public BadTokenException(java.lang.String str) {
            super(str);
        }
    }

    public static class InvalidDisplayException extends java.lang.RuntimeException {
        public InvalidDisplayException() {
        }

        public InvalidDisplayException(java.lang.String str) {
            super(str);
        }
    }

    default android.view.WindowMetrics getCurrentWindowMetrics() {
        throw new java.lang.UnsupportedOperationException();
    }

    default android.view.WindowMetrics getMaximumWindowMetrics() {
        throw new java.lang.UnsupportedOperationException();
    }

    default java.util.Set<android.view.WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    static boolean hasWindowExtensionsEnabled() {
        return WINDOW_EXTENSIONS_ENABLED;
    }

    default void requestImeKeyboardShortcuts(android.view.WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
    }

    default void setShouldShowWithInsecureKeyguard(int i, boolean z) {
    }

    default void setShouldShowSystemDecors(int i, boolean z) {
    }

    default boolean shouldShowSystemDecors(int i) {
        return false;
    }

    default void setDisplayImePolicy(int i, int i2) {
    }

    default int getDisplayImePolicy(int i) {
        return 1;
    }

    default boolean isGlobalKey(int i) {
        return false;
    }

    default boolean isCrossWindowBlurEnabled() {
        return false;
    }

    default void addCrossWindowBlurEnabledListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
    }

    default void addCrossWindowBlurEnabledListener(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
    }

    default void removeCrossWindowBlurEnabledListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
    }

    default void addProposedRotationListener(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
    }

    default void removeProposedRotationListener(java.util.function.IntConsumer intConsumer) {
    }

    static java.lang.String transitTypeToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "OPEN";
            case 2:
                return "CLOSE";
            case 3:
                return "TO_FRONT";
            case 4:
                return "TO_BACK";
            case 5:
                return "RELAUNCH";
            case 6:
                return "CHANGE";
            case 7:
                return "KEYGUARD_GOING_AWAY";
            case 8:
                return "KEYGUARD_OCCLUDE";
            case 9:
                return "KEYGUARD_UNOCCLUDE";
            case 10:
                return "PIP";
            case 11:
                return "WAKE";
            case 12:
                return "SLEEP";
            case 1000:
                return "FIRST_CUSTOM";
            default:
                if (i > 1000) {
                    return "FIRST_CUSTOM+" + (i - 1000);
                }
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    static float fixScale(float f) {
        return java.lang.Math.max(java.lang.Math.min(f, 20.0f), 0.0f);
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams implements android.os.Parcelable {
        public static final int ACCESSIBILITY_ANCHOR_CHANGED = 16777216;
        public static final int ACCESSIBILITY_TITLE_CHANGED = 33554432;
        public static final int ALPHA_CHANGED = 128;
        public static final int ANIMATION_CHANGED = 16;
        public static final int BLUR_BEHIND_RADIUS_CHANGED = 536870912;
        public static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
        public static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
        public static final float BRIGHTNESS_OVERRIDE_OFF = 0.0f;
        public static final int BUTTON_BRIGHTNESS_CHANGED = 8192;
        public static final int COLOR_MODE_CHANGED = 67108864;
        public static final int DIM_AMOUNT_CHANGED = 32;
        public static final int DISPLAY_FLAGS_CHANGED = 4194304;
        public static final int DISPLAY_FLAG_DISABLE_HDR_CONVERSION = 1;
        public static final int FIRST_APPLICATION_WINDOW = 1;
        public static final int FIRST_SUB_WINDOW = 1000;
        public static final int FIRST_SYSTEM_WINDOW = 2000;
        public static final int FLAGS_CHANGED = 4;
        public static final int FLAG_ALLOW_LOCK_WHILE_SCREEN_ON = 1;
        public static final int FLAG_ALT_FOCUSABLE_IM = 131072;
        public static final int FLAG_BLUR_BEHIND = 4;
        public static final int FLAG_DIM_BEHIND = 2;

        @java.lang.Deprecated
        public static final int FLAG_DISMISS_KEYGUARD = 4194304;

        @java.lang.Deprecated
        public static final int FLAG_DITHER = 4096;
        public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = Integer.MIN_VALUE;

        @java.lang.Deprecated
        public static final int FLAG_FORCE_NOT_FULLSCREEN = 2048;

        @java.lang.Deprecated
        public static final int FLAG_FULLSCREEN = 1024;
        public static final int FLAG_HARDWARE_ACCELERATED = 16777216;
        public static final int FLAG_IGNORE_CHEEK_PRESSES = 32768;
        public static final int FLAG_KEEP_SCREEN_ON = 128;

        @java.lang.Deprecated
        public static final int FLAG_LAYOUT_ATTACHED_IN_DECOR = 1073741824;

        @java.lang.Deprecated
        public static final int FLAG_LAYOUT_INSET_DECOR = 65536;

        @java.lang.Deprecated
        public static final int FLAG_LAYOUT_IN_OVERSCAN = 33554432;
        public static final int FLAG_LAYOUT_IN_SCREEN = 256;
        public static final int FLAG_LAYOUT_NO_LIMITS = 512;
        public static final int FLAG_LOCAL_FOCUS_MODE = 268435456;
        public static final int FLAG_NOT_FOCUSABLE = 8;
        public static final int FLAG_NOT_TOUCHABLE = 16;
        public static final int FLAG_NOT_TOUCH_MODAL = 32;
        public static final int FLAG_SCALED = 16384;
        public static final int FLAG_SECURE = 8192;
        public static final int FLAG_SHOW_WALLPAPER = 1048576;

        @java.lang.Deprecated
        public static final int FLAG_SHOW_WHEN_LOCKED = 524288;
        public static final int FLAG_SLIPPERY = 536870912;
        public static final int FLAG_SPLIT_TOUCH = 8388608;

        @java.lang.Deprecated
        public static final int FLAG_TOUCHABLE_WHEN_WAKING = 64;

        @java.lang.Deprecated
        public static final int FLAG_TRANSLUCENT_NAVIGATION = 134217728;

        @java.lang.Deprecated
        public static final int FLAG_TRANSLUCENT_STATUS = 67108864;

        @java.lang.Deprecated
        public static final int FLAG_TURN_SCREEN_ON = 2097152;
        public static final int FLAG_WATCH_OUTSIDE_TOUCH = 262144;
        public static final int FORMAT_CHANGED = 8;
        public static final int INPUT_FEATURES_CHANGED = 65536;
        public static final int INPUT_FEATURE_DISABLE_USER_ACTIVITY = 2;
        public static final int INPUT_FEATURE_NO_INPUT_CHANNEL = 1;
        public static final int INPUT_FEATURE_SPY = 4;
        public static final int INSET_FLAGS_CHANGED = 134217728;
        public static final int INVALID_WINDOW_TYPE = -1;
        public static final int LAST_APPLICATION_WINDOW = 99;
        public static final int LAST_SUB_WINDOW = 1999;
        public static final int LAST_SYSTEM_WINDOW = 2999;
        public static final int LAYOUT_CHANGED = 1;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS = 3;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;
        public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
        public static final int MEMORY_TYPE_CHANGED = 256;

        @java.lang.Deprecated
        public static final int MEMORY_TYPE_GPU = 2;

        @java.lang.Deprecated
        public static final int MEMORY_TYPE_HARDWARE = 1;

        @java.lang.Deprecated
        public static final int MEMORY_TYPE_NORMAL = 0;

        @java.lang.Deprecated
        public static final int MEMORY_TYPE_PUSH_BUFFERS = 3;
        public static final int MINIMAL_POST_PROCESSING_PREFERENCE_CHANGED = 268435456;
        public static final int PREFERRED_DISPLAY_MODE_ID = 8388608;
        public static final int PREFERRED_MAX_DISPLAY_REFRESH_RATE = Integer.MIN_VALUE;
        public static final int PREFERRED_MIN_DISPLAY_REFRESH_RATE = 1073741824;
        public static final int PREFERRED_REFRESH_RATE_CHANGED = 2097152;
        public static final int PRIVATE_FLAGS_CHANGED = 131072;
        public static final int PRIVATE_FLAG_APPEARANCE_CONTROLLED = 67108864;
        public static final int PRIVATE_FLAG_BEHAVIOR_CONTROLLED = 134217728;
        public static final int PRIVATE_FLAG_COLOR_SPACE_AGNOSTIC = 16777216;
        public static final int PRIVATE_FLAG_CONSUME_IME_INSETS = 33554432;
        public static final int PRIVATE_FLAG_DISABLE_WALLPAPER_TOUCH_EVENTS = 1024;
        public static final int PRIVATE_FLAG_EDGE_TO_EDGE_ENFORCED = 2048;
        public static final int PRIVATE_FLAG_EXCLUDE_FROM_SCREEN_MAGNIFICATION = 2097152;
        public static final int PRIVATE_FLAG_FIT_INSETS_CONTROLLED = 268435456;
        public static final int PRIVATE_FLAG_FORCE_DECOR_VIEW_VISIBILITY = 8192;
        public static final int PRIVATE_FLAG_FORCE_DRAW_BAR_BACKGROUNDS = 32768;
        public static final int PRIVATE_FLAG_FORCE_HARDWARE_ACCELERATED = 2;
        public static final int PRIVATE_FLAG_IMMERSIVE_CONFIRMATION_WINDOW = 131072;
        public static final int PRIVATE_FLAG_INSET_PARENT_FRAME_BY_IME = 1073741824;
        public static final int PRIVATE_FLAG_INTERCEPT_GLOBAL_DRAG_AND_DROP = Integer.MIN_VALUE;
        public static final int PRIVATE_FLAG_IS_ROUNDED_CORNERS_OVERLAY = 1048576;
        public static final int PRIVATE_FLAG_LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME = 16384;
        public static final int PRIVATE_FLAG_LAYOUT_SIZE_EXTENDED_BY_CUTOUT = 4096;
        public static final int PRIVATE_FLAG_NOT_MAGNIFIABLE = 4194304;
        public static final int PRIVATE_FLAG_NO_MOVE_ANIMATION = 64;
        public static final int PRIVATE_FLAG_OPTIMIZE_MEASURE = 512;
        public static final int PRIVATE_FLAG_SUSTAINED_PERFORMANCE_MODE = 65536;
        public static final int PRIVATE_FLAG_SYSTEM_APPLICATION_OVERLAY = 8;
        public static final int PRIVATE_FLAG_SYSTEM_ERROR = 256;
        public static final int PRIVATE_FLAG_TRUSTED_OVERLAY = 536870912;
        public static final int PRIVATE_FLAG_UNRESTRICTED_GESTURE_EXCLUSION = 32;
        public static final int PRIVATE_FLAG_WANTS_OFFSET_NOTIFICATIONS = 4;
        public static final int ROTATION_ANIMATION_CHANGED = 4096;
        public static final int ROTATION_ANIMATION_CROSSFADE = 1;
        public static final int ROTATION_ANIMATION_JUMPCUT = 2;
        public static final int ROTATION_ANIMATION_ROTATE = 0;
        public static final int ROTATION_ANIMATION_SEAMLESS = 3;
        public static final int ROTATION_ANIMATION_UNSPECIFIED = -1;
        public static final int SCREEN_BRIGHTNESS_CHANGED = 2048;
        public static final int SCREEN_ORIENTATION_CHANGED = 1024;
        public static final int SOFT_INPUT_ADJUST_NOTHING = 48;
        public static final int SOFT_INPUT_ADJUST_PAN = 32;

        @java.lang.Deprecated
        public static final int SOFT_INPUT_ADJUST_RESIZE = 16;
        public static final int SOFT_INPUT_ADJUST_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_IS_FORWARD_NAVIGATION = 256;
        public static final int SOFT_INPUT_MASK_ADJUST = 240;
        public static final int SOFT_INPUT_MASK_STATE = 15;
        public static final int SOFT_INPUT_MODE_CHANGED = 512;
        public static final int SOFT_INPUT_STATE_ALWAYS_HIDDEN = 3;
        public static final int SOFT_INPUT_STATE_ALWAYS_VISIBLE = 5;
        public static final int SOFT_INPUT_STATE_HIDDEN = 2;
        public static final int SOFT_INPUT_STATE_UNCHANGED = 1;
        public static final int SOFT_INPUT_STATE_UNSPECIFIED = 0;
        public static final int SOFT_INPUT_STATE_VISIBLE = 4;
        public static final int SURFACE_INSETS_CHANGED = 1048576;

        @android.annotation.SystemApi
        public static final int SYSTEM_FLAG_HIDE_NON_SYSTEM_OVERLAY_WINDOWS = 524288;

        @android.annotation.SystemApi
        public static final int SYSTEM_FLAG_SHOW_FOR_ALL_USERS = 16;
        public static final int SYSTEM_UI_LISTENER_CHANGED = 32768;
        public static final int SYSTEM_UI_VISIBILITY_CHANGED = 16384;
        public static final int TITLE_CHANGED = 64;
        public static final int TRANSLUCENT_FLAGS_CHANGED = 524288;
        public static final int TYPE_ACCESSIBILITY_MAGNIFICATION_OVERLAY = 2039;
        public static final int TYPE_ACCESSIBILITY_OVERLAY = 2032;
        public static final int TYPE_APPLICATION = 2;
        public static final int TYPE_APPLICATION_ABOVE_SUB_PANEL = 1005;
        public static final int TYPE_APPLICATION_ATTACHED_DIALOG = 1003;
        public static final int TYPE_APPLICATION_MEDIA = 1001;
        public static final int TYPE_APPLICATION_MEDIA_OVERLAY = 1004;
        public static final int TYPE_APPLICATION_OVERLAY = 2038;
        public static final int TYPE_APPLICATION_PANEL = 1000;
        public static final int TYPE_APPLICATION_STARTING = 3;
        public static final int TYPE_APPLICATION_SUB_PANEL = 1002;
        public static final int TYPE_BASE_APPLICATION = 1;
        public static final int TYPE_BOOT_PROGRESS = 2021;
        public static final int TYPE_CHANGED = 2;
        public static final int TYPE_DISPLAY_OVERLAY = 2026;
        public static final int TYPE_DOCK_DIVIDER = 2034;
        public static final int TYPE_DRAG = 2016;
        public static final int TYPE_DRAWN_APPLICATION = 4;
        public static final int TYPE_INPUT_CONSUMER = 2022;
        public static final int TYPE_INPUT_METHOD = 2011;
        public static final int TYPE_INPUT_METHOD_DIALOG = 2012;
        public static final int TYPE_KEYGUARD = 2004;
        public static final int TYPE_KEYGUARD_DIALOG = 2009;
        public static final int TYPE_MAGNIFICATION_OVERLAY = 2027;
        public static final int TYPE_NAVIGATION_BAR = 2019;
        public static final int TYPE_NAVIGATION_BAR_PANEL = 2024;
        public static final int TYPE_NOTIFICATION_SHADE = 2040;

        @java.lang.Deprecated
        public static final int TYPE_PHONE = 2002;
        public static final int TYPE_POINTER = 2018;
        public static final int TYPE_PRESENTATION = 2037;

        @java.lang.Deprecated
        public static final int TYPE_PRIORITY_PHONE = 2007;
        public static final int TYPE_PRIVATE_PRESENTATION = 2030;
        public static final int TYPE_QS_DIALOG = 2035;
        public static final int TYPE_SCREENSHOT = 2036;
        public static final int TYPE_SEARCH_BAR = 2001;
        public static final int TYPE_SECURE_SYSTEM_OVERLAY = 2015;
        public static final int TYPE_STATUS_BAR = 2000;
        public static final int TYPE_STATUS_BAR_ADDITIONAL = 2041;
        public static final int TYPE_STATUS_BAR_PANEL = 2014;
        public static final int TYPE_STATUS_BAR_SUB_PANEL = 2017;

        @java.lang.Deprecated
        public static final int TYPE_SYSTEM_ALERT = 2003;
        public static final int TYPE_SYSTEM_DIALOG = 2008;

        @java.lang.Deprecated
        public static final int TYPE_SYSTEM_ERROR = 2010;

        @java.lang.Deprecated
        public static final int TYPE_SYSTEM_OVERLAY = 2006;

        @java.lang.Deprecated
        public static final int TYPE_TOAST = 2005;
        public static final int TYPE_VOICE_INTERACTION = 2031;
        public static final int TYPE_VOICE_INTERACTION_STARTING = 2033;
        public static final int TYPE_VOLUME_OVERLAY = 2020;
        public static final int TYPE_WALLPAPER = 2013;
        public static final int USER_ACTIVITY_TIMEOUT_CHANGED = 262144;
        public long accessibilityIdOfAnchor;
        public java.lang.CharSequence accessibilityTitle;
        public float alpha;
        public float buttonBrightness;
        public float dimAmount;

        @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "ALLOW_LOCK_WHILE_SCREEN_ON"), @android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "DIM_BEHIND"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "BLUR_BEHIND"), @android.view.ViewDebug.FlagToString(equals = 8, mask = 8, name = "NOT_FOCUSABLE"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "NOT_TOUCHABLE"), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_TOUCH_MODAL"), @android.view.ViewDebug.FlagToString(equals = 64, mask = 64, name = "TOUCHABLE_WHEN_WAKING"), @android.view.ViewDebug.FlagToString(equals = 128, mask = 128, name = "KEEP_SCREEN_ON"), @android.view.ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_IN_SCREEN"), @android.view.ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_NO_LIMITS"), @android.view.ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "FULLSCREEN"), @android.view.ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "FORCE_NOT_FULLSCREEN"), @android.view.ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "DITHER"), @android.view.ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "SECURE"), @android.view.ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "SCALED"), @android.view.ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "IGNORE_CHEEK_PRESSES"), @android.view.ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "LAYOUT_INSET_DECOR"), @android.view.ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "ALT_FOCUSABLE_IM"), @android.view.ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "WATCH_OUTSIDE_TOUCH"), @android.view.ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "SHOW_WHEN_LOCKED"), @android.view.ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "SHOW_WALLPAPER"), @android.view.ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "TURN_SCREEN_ON"), @android.view.ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "DISMISS_KEYGUARD"), @android.view.ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "SPLIT_TOUCH"), @android.view.ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "HARDWARE_ACCELERATED"), @android.view.ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "LOCAL_FOCUS_MODE"), @android.view.ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "TRANSLUCENT_STATUS"), @android.view.ViewDebug.FlagToString(equals = 134217728, mask = 134217728, name = "TRANSLUCENT_NAVIGATION"), @android.view.ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "LOCAL_FOCUS_MODE"), @android.view.ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "FLAG_SLIPPERY"), @android.view.ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "FLAG_LAYOUT_ATTACHED_IN_DECOR"), @android.view.ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "DRAWS_SYSTEM_BAR_BACKGROUNDS")}, formatToHexString = true)
        public int flags;
        public int forciblyShownTypes;
        public int format;
        public int gravity;
        public boolean hasManualSurfaceInsets;
        public boolean hasSystemUiListeners;
        public long hideTimeoutMilliseconds;
        public float horizontalMargin;

        @android.view.ViewDebug.ExportedProperty
        public float horizontalWeight;
        public int inputFeatures;
        public final android.view.InsetsFlags insetsFlags;
        public int layoutInDisplayCutoutMode;
        private int mBlurBehindRadius;
        private int mColorMode;
        private int[] mCompatibilityParamsBackup;
        private float mDesiredHdrHeadroom;
        private int mDisplayFlags;
        private boolean mFitInsetsIgnoringVisibility;

        @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "LEFT"), @android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "TOP"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "RIGHT"), @android.view.ViewDebug.FlagToString(equals = 8, mask = 8, name = "BOTTOM")})
        private int mFitInsetsSides;

        @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "STATUS_BARS"), @android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "NAVIGATION_BARS"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "CAPTION_BAR"), @android.view.ViewDebug.FlagToString(equals = 8, mask = 8, name = "IME"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "SYSTEM_GESTURES"), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "MANDATORY_SYSTEM_GESTURES"), @android.view.ViewDebug.FlagToString(equals = 64, mask = 64, name = "TAPPABLE_ELEMENT"), @android.view.ViewDebug.FlagToString(equals = 256, mask = 256, name = "WINDOW_DECOR")})
        private int mFitInsetsTypes;
        private boolean mFrameRateBoostOnTouch;
        private boolean mIsFrameRatePowerSavingsBalanced;
        private java.lang.CharSequence mTitle;
        private boolean mWallpaperTouchEventsEnabled;
        public android.os.IBinder mWindowContextToken;

        @java.lang.Deprecated
        public int memoryType;
        public java.lang.String packageName;
        public android.view.WindowManager.LayoutParams[] paramsForRotation;
        public boolean preferMinimalPostProcessing;
        public int preferredDisplayModeId;
        public float preferredMaxDisplayRefreshRate;
        public float preferredMinDisplayRefreshRate;
        public float preferredRefreshRate;
        public boolean preservePreviousSurfaceInsets;

        @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "FORCE_HARDWARE_ACCELERATED"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "WANTS_OFFSET_NOTIFICATIONS"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "SHOW_FOR_ALL_USERS"), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "UNRESTRICTED_GESTURE_EXCLUSION"), @android.view.ViewDebug.FlagToString(equals = 64, mask = 64, name = "NO_MOVE_ANIMATION"), @android.view.ViewDebug.FlagToString(equals = 256, mask = 256, name = "SYSTEM_ERROR"), @android.view.ViewDebug.FlagToString(equals = 512, mask = 512, name = "OPTIMIZE_MEASURE"), @android.view.ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "DISABLE_WALLPAPER_TOUCH_EVENTS"), @android.view.ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "EDGE_TO_EDGE_ENFORCED"), @android.view.ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "LAYOUT_SIZE_EXTENDED_BY_CUTOUT"), @android.view.ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "FORCE_DECOR_VIEW_VISIBILITY"), @android.view.ViewDebug.FlagToString(equals = 16384, mask = 16384, name = "LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME"), @android.view.ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "FORCE_DRAW_STATUS_BAR_BACKGROUND"), @android.view.ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "SUSTAINED_PERFORMANCE_MODE"), @android.view.ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "IMMERSIVE_CONFIRMATION_WINDOW"), @android.view.ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "HIDE_NON_SYSTEM_OVERLAY_WINDOWS"), @android.view.ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "IS_ROUNDED_CORNERS_OVERLAY"), @android.view.ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "EXCLUDE_FROM_SCREEN_MAGNIFICATION"), @android.view.ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "NOT_MAGNIFIABLE"), @android.view.ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "COLOR_SPACE_AGNOSTIC"), @android.view.ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "CONSUME_IME_INSETS"), @android.view.ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "APPEARANCE_CONTROLLED"), @android.view.ViewDebug.FlagToString(equals = 134217728, mask = 134217728, name = "BEHAVIOR_CONTROLLED"), @android.view.ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "FIT_INSETS_CONTROLLED"), @android.view.ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "TRUSTED_OVERLAY"), @android.view.ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "INSET_PARENT_FRAME_BY_IME"), @android.view.ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "INTERCEPT_GLOBAL_DRAG_AND_DROP"), @android.view.ViewDebug.FlagToString(equals = 8, mask = 8, name = "SYSTEM_APPLICATION_OVERLAY")})
        public int privateFlags;
        public android.view.InsetsFrameProvider[] providedInsets;
        public boolean receiveInsetsIgnoringZOrder;
        public int rotationAnimation;
        public float screenBrightness;
        public int screenOrientation;
        public int softInputMode;
        public int subtreeSystemUiVisibility;
        public final android.graphics.Rect surfaceInsets;

        @java.lang.Deprecated
        public int systemUiVisibility;
        public android.os.IBinder token;

        @android.view.ViewDebug.ExportedProperty(mapping = {@android.view.ViewDebug.IntToString(from = 1, to = "BASE_APPLICATION"), @android.view.ViewDebug.IntToString(from = 2, to = "APPLICATION"), @android.view.ViewDebug.IntToString(from = 3, to = "APPLICATION_STARTING"), @android.view.ViewDebug.IntToString(from = 4, to = "DRAWN_APPLICATION"), @android.view.ViewDebug.IntToString(from = 1000, to = "APPLICATION_PANEL"), @android.view.ViewDebug.IntToString(from = 1001, to = "APPLICATION_MEDIA"), @android.view.ViewDebug.IntToString(from = 1002, to = "APPLICATION_SUB_PANEL"), @android.view.ViewDebug.IntToString(from = 1005, to = "APPLICATION_ABOVE_SUB_PANEL"), @android.view.ViewDebug.IntToString(from = 1003, to = "APPLICATION_ATTACHED_DIALOG"), @android.view.ViewDebug.IntToString(from = 1004, to = "APPLICATION_MEDIA_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2000, to = android.app.admin.DevicePolicyResources.Drawables.Source.STATUS_BAR), @android.view.ViewDebug.IntToString(from = 2001, to = "SEARCH_BAR"), @android.view.ViewDebug.IntToString(from = 2002, to = "PHONE"), @android.view.ViewDebug.IntToString(from = 2003, to = "SYSTEM_ALERT"), @android.view.ViewDebug.IntToString(from = 2004, to = "KEYGUARD"), @android.view.ViewDebug.IntToString(from = 2005, to = "TOAST"), @android.view.ViewDebug.IntToString(from = 2006, to = "SYSTEM_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2007, to = "PRIORITY_PHONE"), @android.view.ViewDebug.IntToString(from = 2008, to = "SYSTEM_DIALOG"), @android.view.ViewDebug.IntToString(from = 2009, to = "KEYGUARD_DIALOG"), @android.view.ViewDebug.IntToString(from = 2010, to = "SYSTEM_ERROR"), @android.view.ViewDebug.IntToString(from = 2011, to = "INPUT_METHOD"), @android.view.ViewDebug.IntToString(from = 2012, to = "INPUT_METHOD_DIALOG"), @android.view.ViewDebug.IntToString(from = 2013, to = "WALLPAPER"), @android.view.ViewDebug.IntToString(from = 2014, to = "STATUS_BAR_PANEL"), @android.view.ViewDebug.IntToString(from = 2015, to = "SECURE_SYSTEM_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2016, to = "DRAG"), @android.view.ViewDebug.IntToString(from = 2017, to = "STATUS_BAR_SUB_PANEL"), @android.view.ViewDebug.IntToString(from = 2018, to = "POINTER"), @android.view.ViewDebug.IntToString(from = 2019, to = "NAVIGATION_BAR"), @android.view.ViewDebug.IntToString(from = 2020, to = "VOLUME_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2021, to = "BOOT_PROGRESS"), @android.view.ViewDebug.IntToString(from = 2022, to = "INPUT_CONSUMER"), @android.view.ViewDebug.IntToString(from = 2024, to = "NAVIGATION_BAR_PANEL"), @android.view.ViewDebug.IntToString(from = 2026, to = "DISPLAY_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2027, to = "MAGNIFICATION_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2037, to = "PRESENTATION"), @android.view.ViewDebug.IntToString(from = 2030, to = "PRIVATE_PRESENTATION"), @android.view.ViewDebug.IntToString(from = 2031, to = "VOICE_INTERACTION"), @android.view.ViewDebug.IntToString(from = 2032, to = "ACCESSIBILITY_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2033, to = "VOICE_INTERACTION_STARTING"), @android.view.ViewDebug.IntToString(from = 2034, to = "DOCK_DIVIDER"), @android.view.ViewDebug.IntToString(from = 2035, to = "QS_DIALOG"), @android.view.ViewDebug.IntToString(from = 2036, to = "SCREENSHOT"), @android.view.ViewDebug.IntToString(from = 2038, to = "APPLICATION_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2039, to = "ACCESSIBILITY_MAGNIFICATION_OVERLAY"), @android.view.ViewDebug.IntToString(from = 2040, to = "NOTIFICATION_SHADE"), @android.view.ViewDebug.IntToString(from = 2041, to = "STATUS_BAR_ADDITIONAL")})
        public int type;
        public long userActivityTimeout;
        public float verticalMargin;

        @android.view.ViewDebug.ExportedProperty
        public float verticalWeight;
        public int windowAnimations;

        @android.view.ViewDebug.ExportedProperty
        public int x;

        @android.view.ViewDebug.ExportedProperty
        public int y;
        private static boolean sToolkitSetFrameRateReadOnlyFlagValue = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
        public static final android.os.Parcelable.Creator<android.view.WindowManager.LayoutParams> CREATOR = new android.os.Parcelable.Creator<android.view.WindowManager.LayoutParams>() { // from class: android.view.WindowManager.LayoutParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.WindowManager.LayoutParams createFromParcel(android.os.Parcel parcel) {
                return new android.view.WindowManager.LayoutParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.WindowManager.LayoutParams[] newArray(int i) {
                return new android.view.WindowManager.LayoutParams[i];
            }
        };

        public @interface DisplayFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InputFeatureFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface LayoutInDisplayCutoutMode {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PrivateFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SoftInputModeFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SystemFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SystemUiVisibilityFlags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface WindowType {
        }

        public static boolean isSystemAlertWindowType(int i) {
            switch (i) {
                case 2002:
                case 2003:
                case 2006:
                case 2007:
                case 2010:
                case 2038:
                    return true;
                default:
                    return false;
            }
        }

        public static boolean mayUseInputMethod(int i) {
            return ((i & 8) == 8 || (i & 131072) == 131072) ? false : true;
        }

        public void setFitInsetsTypes(int i) {
            this.mFitInsetsTypes = i;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsSides(int i) {
            this.mFitInsetsSides = i;
            this.privateFlags |= 268435456;
        }

        public void setFitInsetsIgnoringVisibility(boolean z) {
            this.mFitInsetsIgnoringVisibility = z;
            this.privateFlags |= 268435456;
        }

        public void setTrustedOverlay() {
            this.privateFlags |= 536870912;
        }

        @android.annotation.SystemApi
        public void setSystemApplicationOverlay(boolean z) {
            if (z) {
                this.privateFlags |= 8;
            } else {
                this.privateFlags &= -9;
            }
        }

        @android.annotation.SystemApi
        public boolean isSystemApplicationOverlay() {
            return (this.privateFlags & 8) == 8;
        }

        public void setWallpaperTouchEventsEnabled(boolean z) {
            this.mWallpaperTouchEventsEnabled = z;
        }

        public boolean areWallpaperTouchEventsEnabled() {
            return this.mWallpaperTouchEventsEnabled;
        }

        public void setCanPlayMoveAnimation(boolean z) {
            if (z) {
                this.privateFlags &= -65;
            } else {
                this.privateFlags |= 64;
            }
        }

        public boolean canPlayMoveAnimation() {
            return (this.privateFlags & 64) == 0;
        }

        public int getFitInsetsTypes() {
            return this.mFitInsetsTypes;
        }

        public int getFitInsetsSides() {
            return this.mFitInsetsSides;
        }

        public boolean isFitInsetsIgnoringVisibility() {
            return this.mFitInsetsIgnoringVisibility;
        }

        private void checkNonRecursiveParams() {
            if (this.paramsForRotation == null) {
                return;
            }
            for (int length = this.paramsForRotation.length - 1; length >= 0; length--) {
                if (this.paramsForRotation[length].paramsForRotation != null) {
                    throw new java.lang.IllegalArgumentException("Params cannot contain params recursively.");
                }
            }
        }

        public android.view.WindowManager.LayoutParams forRotation(int i) {
            if (this.paramsForRotation == null || this.paramsForRotation.length <= i || this.paramsForRotation[i] == null) {
                return this;
            }
            return this.paramsForRotation[i];
        }

        public LayoutParams() {
            super(-1, -1);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = 2;
            this.format = -1;
        }

        public LayoutParams(int i) {
            super(-1, -1);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = i;
            this.format = -1;
        }

        public LayoutParams(int i, int i2) {
            super(-1, -1);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = i;
            this.flags = i2;
            this.format = -1;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(-1, -1);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = i;
            this.flags = i2;
            this.format = i3;
        }

        public LayoutParams(int i, int i2, int i3, int i4, int i5) {
            super(i, i2);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.type = i3;
            this.flags = i4;
            this.format = i5;
        }

        public LayoutParams(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            super(i, i2);
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.x = i3;
            this.y = i4;
            this.type = i5;
            this.flags = i6;
            this.format = i7;
        }

        public final void setTitle(java.lang.CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mTitle = android.text.TextUtils.stringOrSpannedString(charSequence);
        }

        public final java.lang.CharSequence getTitle() {
            return this.mTitle != null ? this.mTitle : "";
        }

        public final void setSurfaceInsets(android.view.View view, boolean z, boolean z2) {
            int ceil = (int) java.lang.Math.ceil(view.getZ() * 2.0f);
            if (ceil == 0) {
                this.surfaceInsets.set(0, 0, 0, 0);
            } else {
                this.surfaceInsets.set(java.lang.Math.max(ceil, this.surfaceInsets.left), java.lang.Math.max(ceil, this.surfaceInsets.top), java.lang.Math.max(ceil, this.surfaceInsets.right), java.lang.Math.max(ceil, this.surfaceInsets.bottom));
            }
            this.hasManualSurfaceInsets = z;
            this.preservePreviousSurfaceInsets = z2;
        }

        public boolean isHdrConversionEnabled() {
            return (this.mDisplayFlags & 1) == 0;
        }

        public void setHdrConversionEnabled(boolean z) {
            if (!z) {
                this.mDisplayFlags |= 1;
            } else {
                this.mDisplayFlags &= -2;
            }
        }

        public void setColorMode(int i) {
            this.mColorMode = i;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public void setDesiredHdrHeadroom(float f) {
            if (!java.lang.Float.isFinite(f)) {
                throw new java.lang.IllegalArgumentException("desiredHeadroom must be finite: " + f);
            }
            if (f != 0.0f && (f < 1.0f || f > 10000.0f)) {
                throw new java.lang.IllegalArgumentException("desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: " + f);
            }
            this.mDesiredHdrHeadroom = f;
        }

        public float getDesiredHdrHeadroom() {
            return this.mDesiredHdrHeadroom;
        }

        public void setFrameRateBoostOnTouchEnabled(boolean z) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = z;
            }
        }

        public boolean getFrameRateBoostOnTouchEnabled() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mFrameRateBoostOnTouch;
            }
            return true;
        }

        public void setFrameRatePowerSavingsBalanced(boolean z) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mIsFrameRatePowerSavingsBalanced = z;
            }
        }

        public boolean isFrameRatePowerSavingsBalanced() {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                return this.mIsFrameRatePowerSavingsBalanced;
            }
            return true;
        }

        public void setBlurBehindRadius(int i) {
            this.mBlurBehindRadius = i;
        }

        public int getBlurBehindRadius() {
            return this.mBlurBehindRadius;
        }

        @android.annotation.SystemApi
        public final void setUserActivityTimeout(long j) {
            this.userActivityTimeout = j;
        }

        @android.annotation.SystemApi
        public final long getUserActivityTimeout() {
            return this.userActivityTimeout;
        }

        public final void setWindowContextToken(android.os.IBinder iBinder) {
            this.mWindowContextToken = iBinder;
        }

        public final android.os.IBinder getWindowContextToken() {
            return this.mWindowContextToken;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
            parcel.writeInt(this.x);
            parcel.writeInt(this.y);
            parcel.writeInt(this.type);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.privateFlags);
            parcel.writeInt(this.softInputMode);
            parcel.writeInt(this.layoutInDisplayCutoutMode);
            parcel.writeInt(this.gravity);
            parcel.writeFloat(this.horizontalMargin);
            parcel.writeFloat(this.verticalMargin);
            parcel.writeInt(this.format);
            parcel.writeInt(this.windowAnimations);
            parcel.writeFloat(this.alpha);
            parcel.writeFloat(this.dimAmount);
            parcel.writeFloat(this.screenBrightness);
            parcel.writeFloat(this.buttonBrightness);
            parcel.writeInt(this.rotationAnimation);
            parcel.writeStrongBinder(this.token);
            parcel.writeStrongBinder(this.mWindowContextToken);
            parcel.writeString(this.packageName);
            android.text.TextUtils.writeToParcel(this.mTitle, parcel, i);
            parcel.writeInt(this.screenOrientation);
            parcel.writeFloat(this.preferredRefreshRate);
            parcel.writeInt(this.preferredDisplayModeId);
            parcel.writeFloat(this.preferredMinDisplayRefreshRate);
            parcel.writeFloat(this.preferredMaxDisplayRefreshRate);
            parcel.writeInt(this.systemUiVisibility);
            parcel.writeInt(this.subtreeSystemUiVisibility);
            parcel.writeBoolean(this.hasSystemUiListeners);
            parcel.writeInt(this.inputFeatures);
            parcel.writeLong(this.userActivityTimeout);
            parcel.writeInt(this.surfaceInsets.left);
            parcel.writeInt(this.surfaceInsets.top);
            parcel.writeInt(this.surfaceInsets.right);
            parcel.writeInt(this.surfaceInsets.bottom);
            parcel.writeBoolean(this.hasManualSurfaceInsets);
            parcel.writeBoolean(this.receiveInsetsIgnoringZOrder);
            parcel.writeBoolean(this.preservePreviousSurfaceInsets);
            parcel.writeLong(this.accessibilityIdOfAnchor);
            android.text.TextUtils.writeToParcel(this.accessibilityTitle, parcel, i);
            parcel.writeInt(this.mColorMode);
            parcel.writeLong(this.hideTimeoutMilliseconds);
            parcel.writeInt(this.insetsFlags.appearance);
            parcel.writeInt(this.insetsFlags.behavior);
            parcel.writeInt(this.mFitInsetsTypes);
            parcel.writeInt(this.mFitInsetsSides);
            parcel.writeBoolean(this.mFitInsetsIgnoringVisibility);
            parcel.writeBoolean(this.preferMinimalPostProcessing);
            parcel.writeInt(this.mBlurBehindRadius);
            parcel.writeBoolean(this.mWallpaperTouchEventsEnabled);
            parcel.writeTypedArray(this.providedInsets, 0);
            parcel.writeInt(this.forciblyShownTypes);
            checkNonRecursiveParams();
            parcel.writeTypedArray(this.paramsForRotation, 0);
            parcel.writeInt(this.mDisplayFlags);
            parcel.writeFloat(this.mDesiredHdrHeadroom);
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                parcel.writeBoolean(this.mFrameRateBoostOnTouch);
                parcel.writeBoolean(this.mIsFrameRatePowerSavingsBalanced);
            }
        }

        public LayoutParams(android.os.Parcel parcel) {
            this.surfaceInsets = new android.graphics.Rect();
            this.preservePreviousSurfaceInsets = true;
            this.alpha = 1.0f;
            this.dimAmount = 1.0f;
            this.screenBrightness = -1.0f;
            this.buttonBrightness = -1.0f;
            this.rotationAnimation = 0;
            this.token = null;
            this.mWindowContextToken = null;
            this.packageName = null;
            this.screenOrientation = -1;
            this.layoutInDisplayCutoutMode = 0;
            this.userActivityTimeout = -1L;
            this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
            this.hideTimeoutMilliseconds = -1L;
            this.preferMinimalPostProcessing = false;
            this.mBlurBehindRadius = 0;
            this.mColorMode = 0;
            this.mDesiredHdrHeadroom = 0.0f;
            this.mFrameRateBoostOnTouch = true;
            this.mIsFrameRatePowerSavingsBalanced = true;
            this.insetsFlags = new android.view.InsetsFlags();
            this.mFitInsetsTypes = android.view.WindowInsets.Type.systemBars();
            this.mFitInsetsSides = android.view.WindowInsets.Side.all();
            this.mFitInsetsIgnoringVisibility = false;
            this.mWallpaperTouchEventsEnabled = true;
            this.mCompatibilityParamsBackup = null;
            this.mTitle = null;
            this.width = parcel.readInt();
            this.height = parcel.readInt();
            this.x = parcel.readInt();
            this.y = parcel.readInt();
            this.type = parcel.readInt();
            this.flags = parcel.readInt();
            this.privateFlags = parcel.readInt();
            this.softInputMode = parcel.readInt();
            this.layoutInDisplayCutoutMode = parcel.readInt();
            this.gravity = parcel.readInt();
            this.horizontalMargin = parcel.readFloat();
            this.verticalMargin = parcel.readFloat();
            this.format = parcel.readInt();
            this.windowAnimations = parcel.readInt();
            this.alpha = parcel.readFloat();
            this.dimAmount = parcel.readFloat();
            this.screenBrightness = parcel.readFloat();
            this.buttonBrightness = parcel.readFloat();
            this.rotationAnimation = parcel.readInt();
            this.token = parcel.readStrongBinder();
            this.mWindowContextToken = parcel.readStrongBinder();
            this.packageName = parcel.readString();
            this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.screenOrientation = parcel.readInt();
            this.preferredRefreshRate = parcel.readFloat();
            this.preferredDisplayModeId = parcel.readInt();
            this.preferredMinDisplayRefreshRate = parcel.readFloat();
            this.preferredMaxDisplayRefreshRate = parcel.readFloat();
            this.systemUiVisibility = parcel.readInt();
            this.subtreeSystemUiVisibility = parcel.readInt();
            this.hasSystemUiListeners = parcel.readBoolean();
            this.inputFeatures = parcel.readInt();
            this.userActivityTimeout = parcel.readLong();
            this.surfaceInsets.left = parcel.readInt();
            this.surfaceInsets.top = parcel.readInt();
            this.surfaceInsets.right = parcel.readInt();
            this.surfaceInsets.bottom = parcel.readInt();
            this.hasManualSurfaceInsets = parcel.readBoolean();
            this.receiveInsetsIgnoringZOrder = parcel.readBoolean();
            this.preservePreviousSurfaceInsets = parcel.readBoolean();
            this.accessibilityIdOfAnchor = parcel.readLong();
            this.accessibilityTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mColorMode = parcel.readInt();
            this.hideTimeoutMilliseconds = parcel.readLong();
            this.insetsFlags.appearance = parcel.readInt();
            this.insetsFlags.behavior = parcel.readInt();
            this.mFitInsetsTypes = parcel.readInt();
            this.mFitInsetsSides = parcel.readInt();
            this.mFitInsetsIgnoringVisibility = parcel.readBoolean();
            this.preferMinimalPostProcessing = parcel.readBoolean();
            this.mBlurBehindRadius = parcel.readInt();
            this.mWallpaperTouchEventsEnabled = parcel.readBoolean();
            this.providedInsets = (android.view.InsetsFrameProvider[]) parcel.createTypedArray(android.view.InsetsFrameProvider.CREATOR);
            this.forciblyShownTypes = parcel.readInt();
            this.paramsForRotation = (android.view.WindowManager.LayoutParams[]) parcel.createTypedArray(CREATOR);
            this.mDisplayFlags = parcel.readInt();
            this.mDesiredHdrHeadroom = parcel.readFloat();
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                this.mFrameRateBoostOnTouch = parcel.readBoolean();
                this.mIsFrameRatePowerSavingsBalanced = parcel.readBoolean();
            }
        }

        public final int copyFrom(android.view.WindowManager.LayoutParams layoutParams) {
            int i;
            if (this.width == layoutParams.width) {
                i = 0;
            } else {
                this.width = layoutParams.width;
                i = 1;
            }
            if (this.height != layoutParams.height) {
                this.height = layoutParams.height;
                i = 1;
            }
            if (this.x != layoutParams.x) {
                this.x = layoutParams.x;
                i = 1;
            }
            if (this.y != layoutParams.y) {
                this.y = layoutParams.y;
                i = 1;
            }
            if (this.horizontalWeight != layoutParams.horizontalWeight) {
                this.horizontalWeight = layoutParams.horizontalWeight;
                i = 1;
            }
            if (this.verticalWeight != layoutParams.verticalWeight) {
                this.verticalWeight = layoutParams.verticalWeight;
                i = 1;
            }
            if (this.horizontalMargin != layoutParams.horizontalMargin) {
                this.horizontalMargin = layoutParams.horizontalMargin;
                i = 1;
            }
            if (this.verticalMargin != layoutParams.verticalMargin) {
                this.verticalMargin = layoutParams.verticalMargin;
                i = 1;
            }
            if (this.type != layoutParams.type) {
                this.type = layoutParams.type;
                i |= 2;
            }
            if (this.flags != layoutParams.flags) {
                if (((this.flags ^ layoutParams.flags) & android.media.audio.Enums.AUDIO_FORMAT_DTS_HD) != 0) {
                    i |= 524288;
                }
                this.flags = layoutParams.flags;
                i |= 4;
            }
            if (this.privateFlags != layoutParams.privateFlags) {
                this.privateFlags = layoutParams.privateFlags;
                i |= 131072;
            }
            if (this.softInputMode != layoutParams.softInputMode) {
                this.softInputMode = layoutParams.softInputMode;
                i |= 512;
            }
            if (this.layoutInDisplayCutoutMode != layoutParams.layoutInDisplayCutoutMode) {
                this.layoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
                i |= 1;
            }
            if (this.gravity != layoutParams.gravity) {
                this.gravity = layoutParams.gravity;
                i |= 1;
            }
            if (this.format != layoutParams.format) {
                this.format = layoutParams.format;
                i |= 8;
            }
            if (this.windowAnimations != layoutParams.windowAnimations) {
                this.windowAnimations = layoutParams.windowAnimations;
                i |= 16;
            }
            if (this.token == null) {
                this.token = layoutParams.token;
            }
            if (this.mWindowContextToken == null) {
                this.mWindowContextToken = layoutParams.mWindowContextToken;
            }
            if (this.packageName == null) {
                this.packageName = layoutParams.packageName;
            }
            if (!java.util.Objects.equals(this.mTitle, layoutParams.mTitle) && layoutParams.mTitle != null) {
                this.mTitle = layoutParams.mTitle;
                i |= 64;
            }
            if (this.alpha != layoutParams.alpha) {
                this.alpha = layoutParams.alpha;
                i |= 128;
            }
            if (this.dimAmount != layoutParams.dimAmount) {
                this.dimAmount = layoutParams.dimAmount;
                i |= 32;
            }
            if (this.screenBrightness != layoutParams.screenBrightness) {
                this.screenBrightness = layoutParams.screenBrightness;
                i |= 2048;
            }
            if (this.buttonBrightness != layoutParams.buttonBrightness) {
                this.buttonBrightness = layoutParams.buttonBrightness;
                i |= 8192;
            }
            if (this.rotationAnimation != layoutParams.rotationAnimation) {
                this.rotationAnimation = layoutParams.rotationAnimation;
                i |= 4096;
            }
            if (this.screenOrientation != layoutParams.screenOrientation) {
                this.screenOrientation = layoutParams.screenOrientation;
                i |= 1024;
            }
            if (this.preferredRefreshRate != layoutParams.preferredRefreshRate) {
                this.preferredRefreshRate = layoutParams.preferredRefreshRate;
                i |= 2097152;
            }
            if (this.preferredDisplayModeId != layoutParams.preferredDisplayModeId) {
                this.preferredDisplayModeId = layoutParams.preferredDisplayModeId;
                i |= 8388608;
            }
            if (this.preferredMinDisplayRefreshRate != layoutParams.preferredMinDisplayRefreshRate) {
                this.preferredMinDisplayRefreshRate = layoutParams.preferredMinDisplayRefreshRate;
                i |= 1073741824;
            }
            if (this.preferredMaxDisplayRefreshRate != layoutParams.preferredMaxDisplayRefreshRate) {
                this.preferredMaxDisplayRefreshRate = layoutParams.preferredMaxDisplayRefreshRate;
                i |= Integer.MIN_VALUE;
            }
            if (this.mDisplayFlags != layoutParams.mDisplayFlags) {
                this.mDisplayFlags = layoutParams.mDisplayFlags;
                i |= 4194304;
            }
            if (this.systemUiVisibility != layoutParams.systemUiVisibility || this.subtreeSystemUiVisibility != layoutParams.subtreeSystemUiVisibility) {
                this.systemUiVisibility = layoutParams.systemUiVisibility;
                this.subtreeSystemUiVisibility = layoutParams.subtreeSystemUiVisibility;
                i |= 16384;
            }
            if (this.hasSystemUiListeners != layoutParams.hasSystemUiListeners) {
                this.hasSystemUiListeners = layoutParams.hasSystemUiListeners;
                i |= 32768;
            }
            if (this.inputFeatures != layoutParams.inputFeatures) {
                this.inputFeatures = layoutParams.inputFeatures;
                i |= 65536;
            }
            if (this.userActivityTimeout != layoutParams.userActivityTimeout) {
                this.userActivityTimeout = layoutParams.userActivityTimeout;
                i |= 262144;
            }
            if (!this.surfaceInsets.equals(layoutParams.surfaceInsets)) {
                this.surfaceInsets.set(layoutParams.surfaceInsets);
                i |= 1048576;
            }
            if (this.hasManualSurfaceInsets != layoutParams.hasManualSurfaceInsets) {
                this.hasManualSurfaceInsets = layoutParams.hasManualSurfaceInsets;
                i |= 1048576;
            }
            if (this.receiveInsetsIgnoringZOrder != layoutParams.receiveInsetsIgnoringZOrder) {
                this.receiveInsetsIgnoringZOrder = layoutParams.receiveInsetsIgnoringZOrder;
                i |= 1048576;
            }
            if (this.preservePreviousSurfaceInsets != layoutParams.preservePreviousSurfaceInsets) {
                this.preservePreviousSurfaceInsets = layoutParams.preservePreviousSurfaceInsets;
                i |= 1048576;
            }
            if (this.accessibilityIdOfAnchor != layoutParams.accessibilityIdOfAnchor) {
                this.accessibilityIdOfAnchor = layoutParams.accessibilityIdOfAnchor;
                i |= 16777216;
            }
            if (!java.util.Objects.equals(this.accessibilityTitle, layoutParams.accessibilityTitle) && layoutParams.accessibilityTitle != null) {
                this.accessibilityTitle = layoutParams.accessibilityTitle;
                i |= 33554432;
            }
            if (this.mColorMode != layoutParams.mColorMode) {
                this.mColorMode = layoutParams.mColorMode;
                i |= 67108864;
            }
            if (this.mDesiredHdrHeadroom != layoutParams.mDesiredHdrHeadroom) {
                this.mDesiredHdrHeadroom = layoutParams.mDesiredHdrHeadroom;
                i |= 67108864;
            }
            if (this.preferMinimalPostProcessing != layoutParams.preferMinimalPostProcessing) {
                this.preferMinimalPostProcessing = layoutParams.preferMinimalPostProcessing;
                i |= 268435456;
            }
            if (this.mBlurBehindRadius != layoutParams.mBlurBehindRadius) {
                this.mBlurBehindRadius = layoutParams.mBlurBehindRadius;
                i |= 536870912;
            }
            this.hideTimeoutMilliseconds = layoutParams.hideTimeoutMilliseconds;
            if (this.insetsFlags.appearance != layoutParams.insetsFlags.appearance) {
                this.insetsFlags.appearance = layoutParams.insetsFlags.appearance;
                i |= 134217728;
            }
            if (this.insetsFlags.behavior != layoutParams.insetsFlags.behavior) {
                this.insetsFlags.behavior = layoutParams.insetsFlags.behavior;
                i |= 134217728;
            }
            if (this.mFitInsetsTypes != layoutParams.mFitInsetsTypes) {
                this.mFitInsetsTypes = layoutParams.mFitInsetsTypes;
                i |= 1;
            }
            if (this.mFitInsetsSides != layoutParams.mFitInsetsSides) {
                this.mFitInsetsSides = layoutParams.mFitInsetsSides;
                i |= 1;
            }
            if (this.mFitInsetsIgnoringVisibility != layoutParams.mFitInsetsIgnoringVisibility) {
                this.mFitInsetsIgnoringVisibility = layoutParams.mFitInsetsIgnoringVisibility;
                i |= 1;
            }
            if (!java.util.Arrays.equals(this.providedInsets, layoutParams.providedInsets)) {
                this.providedInsets = layoutParams.providedInsets;
                i |= 1;
            }
            if (this.forciblyShownTypes != layoutParams.forciblyShownTypes) {
                this.forciblyShownTypes = layoutParams.forciblyShownTypes;
                i |= 131072;
            }
            if (this.paramsForRotation != layoutParams.paramsForRotation) {
                if ((i & 1) == 0) {
                    if (this.paramsForRotation != null && layoutParams.paramsForRotation != null && this.paramsForRotation.length == layoutParams.paramsForRotation.length) {
                        int length = this.paramsForRotation.length - 1;
                        while (true) {
                            if (length < 0) {
                                break;
                            }
                            if (!hasLayoutDiff(this.paramsForRotation[length], layoutParams.paramsForRotation[length])) {
                                length--;
                            } else {
                                i |= 1;
                                break;
                            }
                        }
                    } else {
                        i |= 1;
                    }
                }
                this.paramsForRotation = layoutParams.paramsForRotation;
                checkNonRecursiveParams();
            }
            if (this.mWallpaperTouchEventsEnabled != layoutParams.mWallpaperTouchEventsEnabled) {
                this.mWallpaperTouchEventsEnabled = layoutParams.mWallpaperTouchEventsEnabled;
                i |= 1;
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mFrameRateBoostOnTouch != layoutParams.mFrameRateBoostOnTouch) {
                this.mFrameRateBoostOnTouch = layoutParams.mFrameRateBoostOnTouch;
                i |= 1;
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced != layoutParams.mIsFrameRatePowerSavingsBalanced) {
                this.mIsFrameRatePowerSavingsBalanced = layoutParams.mIsFrameRatePowerSavingsBalanced;
                return i | 1;
            }
            return i;
        }

        private static boolean hasLayoutDiff(android.view.WindowManager.LayoutParams layoutParams, android.view.WindowManager.LayoutParams layoutParams2) {
            return (layoutParams.width == layoutParams2.width && layoutParams.height == layoutParams2.height && layoutParams.x == layoutParams2.x && layoutParams.y == layoutParams2.y && layoutParams.horizontalMargin == layoutParams2.horizontalMargin && layoutParams.verticalMargin == layoutParams2.verticalMargin && layoutParams.layoutInDisplayCutoutMode == layoutParams2.layoutInDisplayCutoutMode && layoutParams.gravity == layoutParams2.gravity && java.util.Arrays.equals(layoutParams.providedInsets, layoutParams2.providedInsets) && layoutParams.mFitInsetsTypes == layoutParams2.mFitInsetsTypes && layoutParams.mFitInsetsSides == layoutParams2.mFitInsetsSides && layoutParams.mFitInsetsIgnoringVisibility == layoutParams2.mFitInsetsIgnoringVisibility) ? false : true;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public java.lang.String debug(java.lang.String str) {
            android.util.Log.d("Debug", str + "Contents of " + this + ":");
            android.util.Log.d("Debug", super.debug(""));
            android.util.Log.d("Debug", "");
            android.util.Log.d("Debug", "WindowManager.LayoutParams={title=" + ((java.lang.Object) this.mTitle) + "}");
            return "";
        }

        public java.lang.String toString() {
            return toString("");
        }

        public void dumpDimensions(java.lang.StringBuilder sb) {
            java.lang.String valueOf;
            sb.append('(');
            sb.append(this.x);
            sb.append(',');
            sb.append(this.y);
            sb.append(")(");
            java.lang.String str = "wrap";
            if (this.width == -1) {
                valueOf = "fill";
            } else {
                valueOf = this.width == -2 ? "wrap" : java.lang.String.valueOf(this.width);
            }
            sb.append(valueOf);
            sb.append(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            if (this.height == -1) {
                str = "fill";
            } else if (this.height != -2) {
                str = java.lang.String.valueOf(this.height);
            }
            sb.append(str);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }

        public java.lang.String toString(java.lang.String str) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
            sb.append('{');
            dumpDimensions(sb);
            if (this.horizontalMargin != 0.0f) {
                sb.append(" hm=");
                sb.append(this.horizontalMargin);
            }
            if (this.verticalMargin != 0.0f) {
                sb.append(" vm=");
                sb.append(this.verticalMargin);
            }
            if (this.gravity != 0) {
                sb.append(" gr=");
                sb.append(android.view.Gravity.toString(this.gravity));
            }
            if (this.softInputMode != 0) {
                sb.append(" sim={");
                sb.append(softInputModeToString(this.softInputMode));
                sb.append('}');
            }
            if (this.layoutInDisplayCutoutMode != 0) {
                sb.append(" layoutInDisplayCutoutMode=");
                sb.append(layoutInDisplayCutoutModeToString(this.layoutInDisplayCutoutMode));
            }
            sb.append(" ty=");
            sb.append(android.view.ViewDebug.intToString(android.view.WindowManager.LayoutParams.class, "type", this.type));
            if (this.format != -1) {
                sb.append(" fmt=");
                sb.append(android.graphics.PixelFormat.formatToString(this.format));
            }
            if (this.windowAnimations != 0) {
                sb.append(" wanim=0x");
                sb.append(java.lang.Integer.toHexString(this.windowAnimations));
            }
            if (this.screenOrientation != -1) {
                sb.append(" or=");
                sb.append(android.content.pm.ActivityInfo.screenOrientationToString(this.screenOrientation));
            }
            if (this.alpha != 1.0f) {
                sb.append(" alpha=");
                sb.append(this.alpha);
            }
            if (this.screenBrightness != -1.0f) {
                sb.append(" sbrt=");
                sb.append(this.screenBrightness);
            }
            if (this.buttonBrightness != -1.0f) {
                sb.append(" bbrt=");
                sb.append(this.buttonBrightness);
            }
            if (this.rotationAnimation != 0) {
                sb.append(" rotAnim=");
                sb.append(rotationAnimationToString(this.rotationAnimation));
            }
            if (this.preferredRefreshRate != 0.0f) {
                sb.append(" preferredRefreshRate=");
                sb.append(this.preferredRefreshRate);
            }
            if (this.preferredDisplayModeId != 0) {
                sb.append(" preferredDisplayMode=");
                sb.append(this.preferredDisplayModeId);
            }
            if (this.preferredMinDisplayRefreshRate != 0.0f) {
                sb.append(" preferredMinDisplayRefreshRate=");
                sb.append(this.preferredMinDisplayRefreshRate);
            }
            if (this.preferredMaxDisplayRefreshRate != 0.0f) {
                sb.append(" preferredMaxDisplayRefreshRate=");
                sb.append(this.preferredMaxDisplayRefreshRate);
            }
            if (this.mDisplayFlags != 0) {
                sb.append(" displayFlags=0x");
                sb.append(java.lang.Integer.toHexString(this.mDisplayFlags));
            }
            if (this.hasSystemUiListeners) {
                sb.append(" sysuil=");
                sb.append(this.hasSystemUiListeners);
            }
            if (this.inputFeatures != 0) {
                sb.append(" if=").append(inputFeaturesToString(this.inputFeatures));
            }
            if (this.userActivityTimeout >= 0) {
                sb.append(" userActivityTimeout=").append(this.userActivityTimeout);
            }
            if (this.surfaceInsets.left != 0 || this.surfaceInsets.top != 0 || this.surfaceInsets.right != 0 || this.surfaceInsets.bottom != 0 || this.hasManualSurfaceInsets || !this.preservePreviousSurfaceInsets) {
                sb.append(" surfaceInsets=").append(this.surfaceInsets);
                if (this.hasManualSurfaceInsets) {
                    sb.append(" (manual)");
                }
                if (!this.preservePreviousSurfaceInsets) {
                    sb.append(" (!preservePreviousSurfaceInsets)");
                }
            }
            if (this.receiveInsetsIgnoringZOrder) {
                sb.append(" receive insets ignoring z-order");
            }
            if (this.mColorMode != 0) {
                sb.append(" colorMode=").append(android.content.pm.ActivityInfo.colorModeToString(this.mColorMode));
            }
            if (this.mDesiredHdrHeadroom != 0.0f) {
                sb.append(" desiredHdrHeadroom=").append(this.mDesiredHdrHeadroom);
            }
            if (this.preferMinimalPostProcessing) {
                sb.append(" preferMinimalPostProcessing=");
                sb.append(this.preferMinimalPostProcessing);
            }
            if (this.mBlurBehindRadius != 0) {
                sb.append(" blurBehindRadius=");
                sb.append(this.mBlurBehindRadius);
            }
            sb.append(java.lang.System.lineSeparator());
            sb.append(str).append("  fl=").append(android.view.ViewDebug.flagsToString(android.view.WindowManager.LayoutParams.class, "flags", this.flags));
            if (this.privateFlags != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  pfl=").append(android.view.ViewDebug.flagsToString(android.view.WindowManager.LayoutParams.class, "privateFlags", this.privateFlags));
            }
            if (this.systemUiVisibility != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  sysui=").append(android.view.ViewDebug.flagsToString(android.view.View.class, "mSystemUiVisibility", this.systemUiVisibility));
            }
            if (this.subtreeSystemUiVisibility != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  vsysui=").append(android.view.ViewDebug.flagsToString(android.view.View.class, "mSystemUiVisibility", this.subtreeSystemUiVisibility));
            }
            if (this.insetsFlags.appearance != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  apr=").append(android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "appearance", this.insetsFlags.appearance));
            }
            if (this.insetsFlags.behavior != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  bhv=").append(android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "behavior", this.insetsFlags.behavior));
            }
            if (this.mFitInsetsTypes != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  fitTypes=").append(android.view.ViewDebug.flagsToString(android.view.WindowManager.LayoutParams.class, "mFitInsetsTypes", this.mFitInsetsTypes));
            }
            if (this.mFitInsetsSides != android.view.WindowInsets.Side.all()) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  fitSides=").append(android.view.ViewDebug.flagsToString(android.view.WindowManager.LayoutParams.class, "mFitInsetsSides", this.mFitInsetsSides));
            }
            if (this.mFitInsetsIgnoringVisibility) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  fitIgnoreVis");
            }
            if (this.providedInsets != null) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  providedInsets:");
                for (int i = 0; i < this.providedInsets.length; i++) {
                    sb.append(java.lang.System.lineSeparator());
                    sb.append(str).append("    ").append(this.providedInsets[i]);
                }
            }
            if (this.forciblyShownTypes != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  forciblyShownTypes=").append(android.view.WindowInsets.Type.toString(this.forciblyShownTypes));
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mFrameRateBoostOnTouch) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  frameRateBoostOnTouch=");
                sb.append(this.mFrameRateBoostOnTouch);
            }
            if (sToolkitSetFrameRateReadOnlyFlagValue && this.mIsFrameRatePowerSavingsBalanced) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  dvrrWindowFrameRateHint=");
                sb.append(this.mIsFrameRatePowerSavingsBalanced);
            }
            if (this.paramsForRotation != null && this.paramsForRotation.length != 0) {
                sb.append(java.lang.System.lineSeparator());
                sb.append(str).append("  paramsForRotation:");
                for (int i2 = 0; i2 < this.paramsForRotation.length; i2++) {
                    sb.append(java.lang.System.lineSeparator()).append(str).append("    ");
                    sb.append(android.view.Surface.rotationToString(i2)).append("=");
                    sb.append(this.paramsForRotation[i2].toString(str + "    "));
                }
            }
            sb.append('}');
            return sb.toString();
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.type);
            protoOutputStream.write(1120986464258L, this.x);
            protoOutputStream.write(1120986464259L, this.y);
            protoOutputStream.write(1120986464260L, this.width);
            protoOutputStream.write(1120986464261L, this.height);
            protoOutputStream.write(1108101562374L, this.horizontalMargin);
            protoOutputStream.write(1108101562375L, this.verticalMargin);
            protoOutputStream.write(1120986464264L, this.gravity);
            protoOutputStream.write(1120986464265L, this.softInputMode);
            protoOutputStream.write(1159641169930L, this.format);
            protoOutputStream.write(1120986464267L, this.windowAnimations);
            protoOutputStream.write(1108101562380L, this.alpha);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.SCREEN_BRIGHTNESS, this.screenBrightness);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.BUTTON_BRIGHTNESS, this.buttonBrightness);
            protoOutputStream.write(1159641169935L, this.rotationAnimation);
            protoOutputStream.write(1108101562384L, this.preferredRefreshRate);
            protoOutputStream.write(1120986464273L, this.preferredDisplayModeId);
            protoOutputStream.write(1133871366162L, this.hasSystemUiListeners);
            protoOutputStream.write(1155346202643L, this.inputFeatures);
            protoOutputStream.write(1112396529684L, this.userActivityTimeout);
            protoOutputStream.write(1159641169943L, this.mColorMode);
            protoOutputStream.write(1155346202648L, this.flags);
            protoOutputStream.write(1155346202650L, this.privateFlags);
            protoOutputStream.write(1155346202651L, this.systemUiVisibility);
            protoOutputStream.write(1155346202652L, this.subtreeSystemUiVisibility);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.APPEARANCE, this.insetsFlags.appearance);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.BEHAVIOR, this.insetsFlags.behavior);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.FIT_INSETS_TYPES, this.mFitInsetsTypes);
            protoOutputStream.write(android.view.WindowLayoutParamsProto.FIT_INSETS_SIDES, this.mFitInsetsSides);
            protoOutputStream.write(1133871366177L, this.mFitInsetsIgnoringVisibility);
            protoOutputStream.end(start);
        }

        public void scale(float f) {
            this.x = (int) ((this.x * f) + 0.5f);
            this.y = (int) ((this.y * f) + 0.5f);
            if (this.width > 0) {
                this.width = (int) ((this.width * f) + 0.5f);
            }
            if (this.height > 0) {
                this.height = (int) ((this.height * f) + 0.5f);
            }
        }

        void backup() {
            int[] iArr = this.mCompatibilityParamsBackup;
            if (iArr == null) {
                iArr = new int[4];
                this.mCompatibilityParamsBackup = iArr;
            }
            iArr[0] = this.x;
            iArr[1] = this.y;
            iArr[2] = this.width;
            iArr[3] = this.height;
        }

        void restore() {
            int[] iArr = this.mCompatibilityParamsBackup;
            if (iArr != null) {
                this.x = iArr[0];
                this.y = iArr[1];
                this.width = iArr[2];
                this.height = iArr[3];
            }
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("x", this.x);
            viewHierarchyEncoder.addProperty("y", this.y);
            viewHierarchyEncoder.addProperty("horizontalWeight", this.horizontalWeight);
            viewHierarchyEncoder.addProperty("verticalWeight", this.verticalWeight);
            viewHierarchyEncoder.addProperty("type", this.type);
            viewHierarchyEncoder.addProperty("flags", this.flags);
        }

        public boolean isFullscreen() {
            return this.x == 0 && this.y == 0 && this.width == -1 && this.height == -1;
        }

        private static java.lang.String layoutInDisplayCutoutModeToString(int i) {
            switch (i) {
                case 0:
                    return "default";
                case 1:
                    return "shortEdges";
                case 2:
                    return "never";
                case 3:
                    return "always";
                default:
                    return "unknown(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        private static java.lang.String softInputModeToString(int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int i2 = i & 15;
            if (i2 != 0) {
                sb.append("state=");
                switch (i2) {
                    case 1:
                        sb.append("unchanged");
                        break;
                    case 2:
                        sb.append("hidden");
                        break;
                    case 3:
                        sb.append("always_hidden");
                        break;
                    case 4:
                        sb.append(android.provider.CalendarContract.CalendarColumns.VISIBLE);
                        break;
                    case 5:
                        sb.append("always_visible");
                        break;
                    default:
                        sb.append(i2);
                        break;
                }
                sb.append(' ');
            }
            int i3 = i & 240;
            if (i3 != 0) {
                sb.append("adjust=");
                switch (i3) {
                    case 16:
                        sb.append("resize");
                        break;
                    case 32:
                        sb.append(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PAN);
                        break;
                    case 48:
                        sb.append("nothing");
                        break;
                    default:
                        sb.append(i3);
                        break;
                }
                sb.append(' ');
            }
            if ((i & 256) != 0) {
                sb.append("forwardNavigation").append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        private static java.lang.String rotationAnimationToString(int i) {
            switch (i) {
                case -1:
                    return "UNSPECIFIED";
                case 0:
                    return "ROTATE";
                case 1:
                    return "CROSSFADE";
                case 2:
                    return "JUMPCUT";
                case 3:
                    return "SEAMLESS";
                default:
                    return java.lang.Integer.toString(i);
            }
        }

        private static java.lang.String inputFeaturesToString(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if ((i & 1) != 0) {
                i &= -2;
                arrayList.add("INPUT_FEATURE_NO_INPUT_CHANNEL");
            }
            if ((i & 2) != 0) {
                i &= -3;
                arrayList.add("INPUT_FEATURE_DISABLE_USER_ACTIVITY");
            }
            if ((i & 4) != 0) {
                i &= -5;
                arrayList.add("INPUT_FEATURE_SPY");
            }
            if (i != 0) {
                arrayList.add(java.lang.Integer.toHexString(i));
            }
            return java.lang.String.join(" | ", arrayList);
        }

        public boolean isModal() {
            return (this.flags & 40) == 0;
        }
    }

    default void holdLock(android.os.IBinder iBinder, int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    default boolean isTaskSnapshotSupported() {
        return false;
    }

    @android.annotation.SystemApi
    default void registerTaskFpsCallback(int i, java.util.concurrent.Executor executor, android.window.TaskFpsCallback taskFpsCallback) {
    }

    @android.annotation.SystemApi
    default void unregisterTaskFpsCallback(android.window.TaskFpsCallback taskFpsCallback) {
    }

    default android.graphics.Bitmap snapshotTaskForRecents(int i) {
        return null;
    }

    @android.annotation.SystemApi
    default java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithMirror(int i, android.view.Window window) {
        throw new java.lang.UnsupportedOperationException();
    }

    default boolean replaceContentOnDisplayWithSc(int i, android.view.SurfaceControl surfaceControl) {
        throw new java.lang.UnsupportedOperationException();
    }

    default void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.TrustedPresentationThresholds trustedPresentationThresholds, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        throw new java.lang.UnsupportedOperationException();
    }

    default void unregisterTrustedPresentationListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        throw new java.lang.UnsupportedOperationException();
    }

    default android.window.InputTransferToken registerBatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.view.Choreographer choreographer, android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        throw new java.lang.UnsupportedOperationException("registerBatchedSurfaceControlInputReceiver is not implemented");
    }

    default android.window.InputTransferToken registerUnbatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.os.Looper looper, android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        throw new java.lang.UnsupportedOperationException("registerUnbatchedSurfaceControlInputReceiver is not implemented");
    }

    default void unregisterSurfaceControlInputReceiver(android.view.SurfaceControl surfaceControl) {
        throw new java.lang.UnsupportedOperationException("unregisterSurfaceControlInputReceiver is not implemented");
    }

    default android.os.IBinder getSurfaceControlInputClientToken(android.view.SurfaceControl surfaceControl) {
        throw new java.lang.UnsupportedOperationException("getSurfaceControlInputClientToken is not implemented");
    }

    default boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) {
        throw new java.lang.UnsupportedOperationException("transferTouchGesture is not implemented");
    }

    default android.os.IBinder getDefaultToken() {
        throw new java.lang.UnsupportedOperationException("getDefaultToken is not implemented");
    }

    default int addScreenRecordingCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        throw new java.lang.UnsupportedOperationException();
    }

    default void removeScreenRecordingCallback(java.util.function.Consumer<java.lang.Integer> consumer) {
        throw new java.lang.UnsupportedOperationException();
    }
}
