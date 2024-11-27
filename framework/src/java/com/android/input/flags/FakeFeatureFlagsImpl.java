package com.android.input.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.input.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.input.flags.Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_INPUT_EVENT_TRACING, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_POINTER_CHOREOGRAPHER, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM, false), java.util.Map.entry(com.android.input.flags.Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.input.flags.Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM, com.android.input.flags.Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER, com.android.input.flags.Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER, com.android.input.flags.Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION, com.android.input.flags.Flags.FLAG_ENABLE_INPUT_EVENT_TRACING, com.android.input.flags.Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL, com.android.input.flags.Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT, com.android.input.flags.Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS, com.android.input.flags.Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION, com.android.input.flags.Flags.FLAG_ENABLE_POINTER_CHOREOGRAPHER, com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP, com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION, com.android.input.flags.Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION, com.android.input.flags.Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API, com.android.input.flags.Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS, com.android.input.flags.Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER, com.android.input.flags.Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM, com.android.input.flags.Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return getValue(com.android.input.flags.Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return getValue(com.android.input.flags.Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableGesturesLibraryTimerProvider() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInboundEventVerification() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputEventTracing() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_INPUT_EVENT_TRACING);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputFilterRustImpl() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceInput() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableNewMousePointerBallistics() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableOutboundEventVerification() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePointerChoreographer() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_POINTER_CHOREOGRAPHER);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadFlingStop() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadTypingPalmRejection() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return getValue(com.android.input.flags.Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return getValue(com.android.input.flags.Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return getValue(com.android.input.flags.Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return getValue(com.android.input.flags.Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean removePointerEventTrackingInWm() {
        return getValue(com.android.input.flags.Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM);
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean reportPalmsToGesturesLibrary() {
        return getValue(com.android.input.flags.Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY);
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
