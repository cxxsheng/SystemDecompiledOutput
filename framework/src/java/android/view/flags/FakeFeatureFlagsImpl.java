package android.view.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.view.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.view.flags.Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS, false), java.util.Map.entry(android.view.flags.Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE, false), java.util.Map.entry(android.view.flags.Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO, false), java.util.Map.entry(android.view.flags.Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT, false), java.util.Map.entry(android.view.flags.Flags.FLAG_ENABLE_VECTOR_CURSORS, false), java.util.Map.entry(android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_API, false), java.util.Map.entry(android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_SCROLL_FEEDBACK_API, false), java.util.Map.entry(android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION, false), java.util.Map.entry(android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API, false), java.util.Map.entry(android.view.flags.Flags.FLAG_SET_FRAME_RATE_CALLBACK, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE, false), java.util.Map.entry(android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY, false), java.util.Map.entry(android.view.flags.Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS, false), java.util.Map.entry(android.view.flags.Flags.FLAG_VIEW_VELOCITY_API, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.view.flags.Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS, android.view.flags.Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE, android.view.flags.Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO, android.view.flags.Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT, android.view.flags.Flags.FLAG_ENABLE_VECTOR_CURSORS, android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_API, android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY, android.view.flags.Flags.FLAG_SCROLL_FEEDBACK_API, android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION, android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API, android.view.flags.Flags.FLAG_SET_FRAME_RATE_CALLBACK, android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY, android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY, android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY, android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY, android.view.flags.Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION, android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE, android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY, android.view.flags.Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS, android.view.flags.Flags.FLAG_VIEW_VELOCITY_API, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.view.flags.FeatureFlags
    public boolean customizableWindowHeaders() {
        return getValue(android.view.flags.Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableArrowIconOnHoverWhenClickable() {
        return getValue(android.view.flags.Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableSurfaceNativeAllocRegistrationRo() {
        return getValue(android.view.flags.Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableUseMeasureCacheDuringForceLayout() {
        return getValue(android.view.flags.Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableVectorCursors() {
        return getValue(android.view.flags.Flags.FLAG_ENABLE_VECTOR_CURSORS);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean expectedPresentationTimeApi() {
        return getValue(android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_API);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean expectedPresentationTimeReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean scrollFeedbackApi() {
        return getValue(android.view.flags.Flags.FLAG_SCROLL_FEEDBACK_API);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean sensitiveContentAppProtection() {
        return getValue(android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean sensitiveContentAppProtectionApi() {
        return getValue(android.view.flags.Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean setFrameRateCallback() {
        return getValue(android.view.flags.Flags.FLAG_SET_FRAME_RATE_CALLBACK);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateBySizeReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateDefaultNormalReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateTypingReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateVelocityMappingReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitMetricsForFrameRateDecision() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitSetFrameRate() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitSetFrameRateReadOnly() {
        return getValue(android.view.flags.Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean useViewBasedRotaryEncoderScrollHaptics() {
        return getValue(android.view.flags.Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS);
    }

    @Override // android.view.flags.FeatureFlags
    public boolean viewVelocityApi() {
        return getValue(android.view.flags.Flags.FLAG_VIEW_VELOCITY_API);
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
