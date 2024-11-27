package android.os.vibrator;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.os.vibrator.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.os.vibrator.Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, false), java.util.Map.entry(android.os.vibrator.Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, false), java.util.Map.entry(android.os.vibrator.Flags.FLAG_HAPTICS_CUSTOMIZATION_ENABLED, false), java.util.Map.entry(android.os.vibrator.Flags.FLAG_KEYBOARD_CATEGORY_ENABLED, false), java.util.Map.entry(android.os.vibrator.Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.os.vibrator.Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, android.os.vibrator.Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, android.os.vibrator.Flags.FLAG_HAPTICS_CUSTOMIZATION_ENABLED, android.os.vibrator.Flags.FLAG_KEYBOARD_CATEGORY_ENABLED, android.os.vibrator.Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean adaptiveHapticsEnabled() {
        return getValue(android.os.vibrator.Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED);
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return getValue(android.os.vibrator.Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED);
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticsCustomizationEnabled() {
        return getValue(android.os.vibrator.Flags.FLAG_HAPTICS_CUSTOMIZATION_ENABLED);
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean keyboardCategoryEnabled() {
        return getValue(android.os.vibrator.Flags.FLAG_KEYBOARD_CATEGORY_ENABLED);
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean useVibratorHapticFeedback() {
        return getValue(android.os.vibrator.Flags.FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK);
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
