package com.android.input.flags;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean a11yCrashOnInconsistentEventStream();

    boolean disableRejectTouchOnStylusHover();

    boolean enableGesturesLibraryTimerProvider();

    boolean enableInboundEventVerification();

    boolean enableInputEventTracing();

    boolean enableInputFilterRustImpl();

    boolean enableMultiDeviceInput();

    boolean enableNewMousePointerBallistics();

    boolean enableOutboundEventVerification();

    boolean enablePointerChoreographer();

    boolean enableTouchpadFlingStop();

    boolean enableTouchpadTypingPalmRejection();

    boolean enableV2TouchpadTypingPalmRejection();

    boolean inputDeviceViewBehaviorApi();

    boolean overrideKeyBehaviorPermissionApis();

    boolean rateLimitUserActivityPokeInDispatcher();

    boolean removePointerEventTrackingInWm();

    boolean reportPalmsToGesturesLibrary();
}
