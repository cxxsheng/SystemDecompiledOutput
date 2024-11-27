package com.android.input.flags;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements com.android.input.flags.FeatureFlags {
    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableGesturesLibraryTimerProvider() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInboundEventVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputEventTracing() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputFilterRustImpl() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceInput() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableNewMousePointerBallistics() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableOutboundEventVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePointerChoreographer() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadFlingStop() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadTypingPalmRejection() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean removePointerEventTrackingInWm() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean reportPalmsToGesturesLibrary() {
        return true;
    }
}
