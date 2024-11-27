package com.android.hardware.input;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.hardware.input.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.hardware.input.Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API, false), java.util.Map.entry(com.android.hardware.input.Flags.FLAG_TOUCHPAD_TAP_DRAGGING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.hardware.input.Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE, com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG, com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG, com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG, com.android.hardware.input.Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG, com.android.hardware.input.Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API, com.android.hardware.input.Flags.FLAG_TOUCHPAD_TAP_DRAGGING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean emojiAndScreenshotKeycodesAvailable() {
        return getValue(com.android.hardware.input.Flags.FLAG_EMOJI_AND_SCREENSHOT_KEYCODES_AVAILABLE);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yBounceKeysFlag() {
        return getValue(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_BOUNCE_KEYS_FLAG);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11ySlowKeysFlag() {
        return getValue(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_SLOW_KEYS_FLAG);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yStickyKeysFlag() {
        return getValue(com.android.hardware.input.Flags.FLAG_KEYBOARD_A11Y_STICKY_KEYS_FLAG);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardLayoutPreviewFlag() {
        return getValue(com.android.hardware.input.Flags.FLAG_KEYBOARD_LAYOUT_PREVIEW_FLAG);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean pointerCoordsIsResampledApi() {
        return getValue(com.android.hardware.input.Flags.FLAG_POINTER_COORDS_IS_RESAMPLED_API);
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean touchpadTapDragging() {
        return getValue(com.android.hardware.input.Flags.FLAG_TOUCHPAD_TAP_DRAGGING);
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
