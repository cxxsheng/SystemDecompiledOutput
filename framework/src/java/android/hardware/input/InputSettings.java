package android.hardware.input;

/* loaded from: classes2.dex */
public class InputSettings {
    public static final float DEFAULT_MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH = 0.8f;
    public static final int DEFAULT_POINTER_SPEED = 0;
    public static final int DEFAULT_STYLUS_POINTER_ICON_ENABLED = 1;
    public static final int MAX_ACCESSIBILITY_BOUNCE_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_ACCESSIBILITY_SLOW_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_POINTER_SPEED = 7;
    public static final int MIN_POINTER_SPEED = -7;

    private InputSettings() {
    }

    public static int getPointerSpeed(android.content.Context context) {
        return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.POINTER_SPEED, 0);
    }

    public static void setPointerSpeed(android.content.Context context, int i) {
        if (i < -7 || i > 7) {
            throw new java.lang.IllegalArgumentException("speed out of range");
        }
        android.provider.Settings.System.putInt(context.getContentResolver(), android.provider.Settings.System.POINTER_SPEED, i);
    }

    public static float getMaximumObscuringOpacityForTouch(android.content.Context context) {
        return android.provider.Settings.Global.getFloat(context.getContentResolver(), android.provider.Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, 0.8f);
    }

    public static void setMaximumObscuringOpacityForTouch(android.content.Context context, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new java.lang.IllegalArgumentException("Maximum obscuring opacity for touch should be >= 0 and <= 1");
        }
        android.provider.Settings.Global.putFloat(context.getContentResolver(), android.provider.Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, f);
    }

    public static boolean isStylusEverUsed(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.STYLUS_EVER_USED, 0) == 1;
    }

    public static void setStylusEverUsed(android.content.Context context, boolean z) {
        android.provider.Settings.Global.putInt(context.getContentResolver(), android.provider.Settings.Global.STYLUS_EVER_USED, z ? 1 : 0);
    }

    public static int getTouchpadPointerSpeed(android.content.Context context) {
        return android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_POINTER_SPEED, 0, -2);
    }

    public static void setTouchpadPointerSpeed(android.content.Context context, int i) {
        if (i < -7 || i > 7) {
            throw new java.lang.IllegalArgumentException("speed out of range");
        }
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_POINTER_SPEED, i, -2);
    }

    public static boolean useTouchpadNaturalScrolling(android.content.Context context) {
        return android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_NATURAL_SCROLLING, 1, -2) == 1;
    }

    public static void setTouchpadNaturalScrolling(android.content.Context context, boolean z) {
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_NATURAL_SCROLLING, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadTapToClick(android.content.Context context) {
        return android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_TAP_TO_CLICK, 1, -2) == 1;
    }

    public static void setTouchpadTapToClick(android.content.Context context, boolean z) {
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_TAP_TO_CLICK, z ? 1 : 0, -2);
    }

    public static boolean isTouchpadTapDraggingFeatureFlagEnabled() {
        return com.android.hardware.input.Flags.touchpadTapDragging();
    }

    public static boolean useTouchpadTapDragging(android.content.Context context) {
        return isTouchpadTapDraggingFeatureFlagEnabled() && android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_TAP_DRAGGING, 0, -2) == 1;
    }

    public static void setTouchpadTapDragging(android.content.Context context, boolean z) {
        if (!isTouchpadTapDraggingFeatureFlagEnabled()) {
            return;
        }
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_TAP_DRAGGING, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadRightClickZone(android.content.Context context) {
        return android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, 0, -2) == 1;
    }

    public static void setTouchpadRightClickZone(android.content.Context context, boolean z) {
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, z ? 1 : 0, -2);
    }

    public static boolean isStylusPointerIconEnabled(android.content.Context context) {
        if (android.sysprop.InputProperties.force_enable_stylus_pointer_icon().orElse(false).booleanValue()) {
            return true;
        }
        return context.getResources().getBoolean(com.android.internal.R.bool.config_enableStylusPointerIcon) && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.STYLUS_POINTER_ICON_ENABLED, 1, -3) != 0;
    }

    public static boolean isAccessibilityBounceKeysFeatureEnabled() {
        return com.android.hardware.input.Flags.keyboardA11yBounceKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilityBounceKeysEnabled(android.content.Context context) {
        return getAccessibilityBounceKeysThreshold(context) != 0;
    }

    public static int getAccessibilityBounceKeysThreshold(android.content.Context context) {
        if (isAccessibilityBounceKeysFeatureEnabled()) {
            return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, 0, -2);
        }
        return 0;
    }

    public static void setAccessibilityBounceKeysThreshold(android.content.Context context, int i) {
        if (!isAccessibilityBounceKeysFeatureEnabled()) {
            return;
        }
        if (i < 0 || i > 5000) {
            throw new java.lang.IllegalArgumentException("Provided Bounce keys threshold should be in range [0, 5000]");
        }
        android.provider.Settings.Secure.putIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, i, -2);
    }

    public static boolean isAccessibilitySlowKeysFeatureFlagEnabled() {
        return com.android.hardware.input.Flags.keyboardA11ySlowKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilitySlowKeysEnabled(android.content.Context context) {
        return getAccessibilitySlowKeysThreshold(context) != 0;
    }

    public static int getAccessibilitySlowKeysThreshold(android.content.Context context) {
        if (isAccessibilitySlowKeysFeatureFlagEnabled()) {
            return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SLOW_KEYS, 0, -2);
        }
        return 0;
    }

    public static void setAccessibilitySlowKeysThreshold(android.content.Context context, int i) {
        if (!isAccessibilitySlowKeysFeatureFlagEnabled()) {
            return;
        }
        if (i < 0 || i > 5000) {
            throw new java.lang.IllegalArgumentException("Provided Slow keys threshold should be in range [0, 5000]");
        }
        android.provider.Settings.Secure.putIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_SLOW_KEYS, i, -2);
    }

    public static boolean isAccessibilityStickyKeysFeatureEnabled() {
        return com.android.hardware.input.Flags.keyboardA11yStickyKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilityStickyKeysEnabled(android.content.Context context) {
        return isAccessibilityStickyKeysFeatureEnabled() && android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_STICKY_KEYS, 0, -2) != 0;
    }

    public static void setAccessibilityStickyKeysEnabled(android.content.Context context, boolean z) {
        if (!isAccessibilityStickyKeysFeatureEnabled()) {
            return;
        }
        android.provider.Settings.Secure.putIntForUser(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_STICKY_KEYS, z ? 1 : 0, -2);
    }
}
