package android.os.vibrator;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.os.vibrator.FeatureFlags FEATURE_FLAGS = new android.os.vibrator.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADAPTIVE_HAPTICS_ENABLED = "android.os.vibrator.adaptive_haptics_enabled";
    public static final java.lang.String FLAG_HAPTICS_CUSTOMIZATION_ENABLED = "android.os.vibrator.haptics_customization_enabled";
    public static final java.lang.String FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED = "android.os.vibrator.haptic_feedback_vibration_oem_customization_enabled";
    public static final java.lang.String FLAG_KEYBOARD_CATEGORY_ENABLED = "android.os.vibrator.keyboard_category_enabled";
    public static final java.lang.String FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK = "android.os.vibrator.use_vibrator_haptic_feedback";

    public static boolean adaptiveHapticsEnabled() {
        return FEATURE_FLAGS.adaptiveHapticsEnabled();
    }

    public static boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return FEATURE_FLAGS.hapticFeedbackVibrationOemCustomizationEnabled();
    }

    public static boolean hapticsCustomizationEnabled() {
        return FEATURE_FLAGS.hapticsCustomizationEnabled();
    }

    public static boolean keyboardCategoryEnabled() {
        return FEATURE_FLAGS.keyboardCategoryEnabled();
    }

    public static boolean useVibratorHapticFeedback() {
        return FEATURE_FLAGS.useVibratorHapticFeedback();
    }
}
