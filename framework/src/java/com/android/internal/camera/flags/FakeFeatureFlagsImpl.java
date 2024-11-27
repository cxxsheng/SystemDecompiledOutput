package com.android.internal.camera.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.internal.camera.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_DEVICE_SETUP, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_HSUM_PERMISSION, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_CONCERT_MODE, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_EXTENSION_10_BIT, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_FEATURE_COMBINATION_QUERY, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_INJECT_SESSION_PARAMS, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_LOG_ULTRAWIDE_USAGE, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_SESSION_HAL_BUF_MANAGER, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION, false), java.util.Map.entry(com.android.internal.camera.flags.Flags.FLAG_WATCH_FOREGROUND_CHANGES, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.internal.camera.flags.Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST, com.android.internal.camera.flags.Flags.FLAG_CAMERA_DEVICE_SETUP, com.android.internal.camera.flags.Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET, com.android.internal.camera.flags.Flags.FLAG_CAMERA_HSUM_PERMISSION, com.android.internal.camera.flags.Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL, com.android.internal.camera.flags.Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST, com.android.internal.camera.flags.Flags.FLAG_CONCERT_MODE, com.android.internal.camera.flags.Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION, com.android.internal.camera.flags.Flags.FLAG_EXTENSION_10_BIT, com.android.internal.camera.flags.Flags.FLAG_FEATURE_COMBINATION_QUERY, com.android.internal.camera.flags.Flags.FLAG_INJECT_SESSION_PARAMS, com.android.internal.camera.flags.Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE, com.android.internal.camera.flags.Flags.FLAG_LOG_ULTRAWIDE_USAGE, com.android.internal.camera.flags.Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE, com.android.internal.camera.flags.Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG, com.android.internal.camera.flags.Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS, com.android.internal.camera.flags.Flags.FLAG_SESSION_HAL_BUF_MANAGER, com.android.internal.camera.flags.Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION, com.android.internal.camera.flags.Flags.FLAG_WATCH_FOREGROUND_CHANGES, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraAeModeLowLightBoost() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraDeviceSetup() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_DEVICE_SETUP);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraExtensionsCharacteristicsGet() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraHsumPermission() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_HSUM_PERMISSION);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraManualFlashStrengthControl() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean cameraPrivacyAllowlist() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CAMERA_PRIVACY_ALLOWLIST);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean concertMode() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_CONCERT_MODE);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean delayLazyHalInstantiation() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_DELAY_LAZY_HAL_INSTANTIATION);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean extension10Bit() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_EXTENSION_10_BIT);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean featureCombinationQuery() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_FEATURE_COMBINATION_QUERY);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean injectSessionParams() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_INJECT_SESSION_PARAMS);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean lazyAidlWaitForService() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_LAZY_AIDL_WAIT_FOR_SERVICE);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean logUltrawideUsage() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_LOG_ULTRAWIDE_USAGE);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean logZoomOverrideUsage() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_LOG_ZOOM_OVERRIDE_USAGE);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean multiresolutionImagereaderUsageConfig() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean returnBuffersOutsideLocks() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean sessionHalBufManager() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_SESSION_HAL_BUF_MANAGER);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean useRoBoardApiLevelForVndkVersion() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION);
    }

    @Override // com.android.internal.camera.flags.FeatureFlags
    public boolean watchForegroundChanges() {
        return getValue(com.android.internal.camera.flags.Flags.FLAG_WATCH_FOREGROUND_CHANGES);
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
