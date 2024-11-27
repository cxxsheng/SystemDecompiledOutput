package com.android.hardware.input;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.hardware.input.FeatureFlags FEATURE_FLAGS = new com.android.hardware.input.FeatureFlagsImpl();
    public static final java.lang.String FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE = "com.android.hardware.input.emoji_and_screenshot_keycodes_available";
    public static final java.lang.String FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG = "com.android.hardware.input.keyboard_a11y_bounce_keys_flag";
    public static final java.lang.String FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG = "com.android.hardware.input.keyboard_a11y_slow_keys_flag";
    public static final java.lang.String FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG = "com.android.hardware.input.keyboard_a11y_sticky_keys_flag";
    public static final java.lang.String FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG = "com.android.hardware.input.keyboard_layout_preview_flag";
    public static final java.lang.String FLAG_POINTER_COORDS_IS_RESAMPLED_API = "com.android.hardware.input.pointer_coords_is_resampled_api";
    public static final java.lang.String FLAG_TOUCHPAD_TAP_DRAGGING = "com.android.hardware.input.touchpad_tap_dragging";

    public static boolean emojiAndScreenshotKeycodesAvailable() {
        return FEATURE_FLAGS.emojiAndScreenshotKeycodesAvailable();
    }

    public static boolean keyboardA11yBounceKeysFlag() {
        return FEATURE_FLAGS.keyboardA11yBounceKeysFlag();
    }

    public static boolean keyboardA11ySlowKeysFlag() {
        return FEATURE_FLAGS.keyboardA11ySlowKeysFlag();
    }

    public static boolean keyboardA11yStickyKeysFlag() {
        return FEATURE_FLAGS.keyboardA11yStickyKeysFlag();
    }

    public static boolean keyboardLayoutPreviewFlag() {
        return FEATURE_FLAGS.keyboardLayoutPreviewFlag();
    }

    public static boolean pointerCoordsIsResampledApi() {
        return FEATURE_FLAGS.pointerCoordsIsResampledApi();
    }

    public static boolean touchpadTapDragging() {
        return FEATURE_FLAGS.touchpadTapDragging();
    }
}
