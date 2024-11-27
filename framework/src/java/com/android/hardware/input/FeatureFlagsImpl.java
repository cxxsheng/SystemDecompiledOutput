package com.android.hardware.input;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements com.android.hardware.input.FeatureFlags {
    @Override // com.android.hardware.input.FeatureFlags
    public boolean emojiAndScreenshotKeycodesAvailable() {
        return false;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yBounceKeysFlag() {
        return true;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11ySlowKeysFlag() {
        return false;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yStickyKeysFlag() {
        return true;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean keyboardLayoutPreviewFlag() {
        return true;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean pointerCoordsIsResampledApi() {
        return false;
    }

    @Override // com.android.hardware.input.FeatureFlags
    public boolean touchpadTapDragging() {
        return false;
    }
}
