package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.graphics.surfaceflinger.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_CONNECTED_DISPLAY, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DISPLAY_PROTECTED, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO2, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_SMALL_AREA_DETECTION, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_FP16_CLIENT_TARGET, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_GAME_DEFAULT_FRAME_RATE, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_GRAPHITE_RENDERENGINE, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_HDCP_LEVEL_HAL, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_HOTPLUG2, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_MISC1, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_MULTITHREADED_PRESENT, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_PROTECTED_IF_CLIENT, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_RENDERABLE_BUFFER_USAGE, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_RESTORE_BLUR_STEP, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_VRR_CONFIG, false), java.util.Map.entry(com.android.graphics.surfaceflinger.flags.Flags.FLAG_VULKAN_RENDERENGINE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE, com.android.graphics.surfaceflinger.flags.Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED, com.android.graphics.surfaceflinger.flags.Flags.FLAG_CONNECTED_DISPLAY, com.android.graphics.surfaceflinger.flags.Flags.FLAG_DISPLAY_PROTECTED, com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO, com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO2, com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES, com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING, com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_SMALL_AREA_DETECTION, com.android.graphics.surfaceflinger.flags.Flags.FLAG_FP16_CLIENT_TARGET, com.android.graphics.surfaceflinger.flags.Flags.FLAG_GAME_DEFAULT_FRAME_RATE, com.android.graphics.surfaceflinger.flags.Flags.FLAG_GRAPHITE_RENDERENGINE, com.android.graphics.surfaceflinger.flags.Flags.FLAG_HDCP_LEVEL_HAL, com.android.graphics.surfaceflinger.flags.Flags.FLAG_HOTPLUG2, com.android.graphics.surfaceflinger.flags.Flags.FLAG_MISC1, com.android.graphics.surfaceflinger.flags.Flags.FLAG_MULTITHREADED_PRESENT, com.android.graphics.surfaceflinger.flags.Flags.FLAG_PROTECTED_IF_CLIENT, com.android.graphics.surfaceflinger.flags.Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY, com.android.graphics.surfaceflinger.flags.Flags.FLAG_RENDERABLE_BUFFER_USAGE, com.android.graphics.surfaceflinger.flags.Flags.FLAG_RESTORE_BLUR_STEP, com.android.graphics.surfaceflinger.flags.Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION, com.android.graphics.surfaceflinger.flags.Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY, com.android.graphics.surfaceflinger.flags.Flags.FLAG_VRR_CONFIG, com.android.graphics.surfaceflinger.flags.Flags.FLAG_VULKAN_RENDERENGINE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean addSfSkippedFramesToTrace() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ADD_SF_SKIPPED_FRAMES_TO_TRACE);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean cacheWhenSourceCropLayerOnlyMoved() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_CACHE_WHEN_SOURCE_CROP_LAYER_ONLY_MOVED);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean connectedDisplay() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_CONNECTED_DISPLAY);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean displayProtected() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DISPLAY_PROTECTED);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo2() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_DONT_SKIP_ON_EARLY_RO2);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableFroDependentFeatures() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_FRO_DEPENDENT_FEATURES);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableLayerCommandBatching() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_LAYER_COMMAND_BATCHING);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableSmallAreaDetection() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_ENABLE_SMALL_AREA_DETECTION);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean fp16ClientTarget() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_FP16_CLIENT_TARGET);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_GAME_DEFAULT_FRAME_RATE);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean graphiteRenderengine() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_GRAPHITE_RENDERENGINE);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpLevelHal() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_HDCP_LEVEL_HAL);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hotplug2() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_HOTPLUG2);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean misc1() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_MISC1);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean multithreadedPresent() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_MULTITHREADED_PRESENT);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean protectedIfClient() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_PROTECTED_IF_CLIENT);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean refreshRateOverlayOnExternalDisplay() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_REFRESH_RATE_OVERLAY_ON_EXTERNAL_DISPLAY);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean renderableBufferUsage() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_RENDERABLE_BUFFER_USAGE);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean restoreBlurStep() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_RESTORE_BLUR_STEP);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean screenshotFencePreservation() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_SCREENSHOT_FENCE_PRESERVATION);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean useKnownRefreshRateForFpsConsistency() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_USE_KNOWN_REFRESH_RATE_FOR_FPS_CONSISTENCY);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrConfig() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_VRR_CONFIG);
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vulkanRenderengine() {
        return getValue(com.android.graphics.surfaceflinger.flags.Flags.FLAG_VULKAN_RENDERENGINE);
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
