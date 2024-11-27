package com.android.internal.camera.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.internal.camera.flags.FeatureFlags FEATURE_FLAGS = new com.android.internal.camera.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CAMERA_AE_MODE_LOW_LIGHT_BOOST = "com.android.internal.camera.flags.camera_ae_mode_low_light_boost";
    public static final java.lang.String FLAG_CAMERA_DEVICE_SETUP = "com.android.internal.camera.flags.camera_device_setup";
    public static final java.lang.String FLAG_CAMERA_EXTENSIONS_CHARACTERISTICS_GET = "com.android.internal.camera.flags.camera_extensions_characteristics_get";
    public static final java.lang.String FLAG_CAMERA_HSUM_PERMISSION = "com.android.internal.camera.flags.camera_hsum_permission";
    public static final java.lang.String FLAG_CAMERA_MANUAL_FLASH_STRENGTH_CONTROL = "com.android.internal.camera.flags.camera_manual_flash_strength_control";
    public static final java.lang.String FLAG_CAMERA_PRIVACY_ALLOWLIST = "com.android.internal.camera.flags.camera_privacy_allowlist";
    public static final java.lang.String FLAG_CONCERT_MODE = "com.android.internal.camera.flags.concert_mode";
    public static final java.lang.String FLAG_DELAY_LAZY_HAL_INSTANTIATION = "com.android.internal.camera.flags.delay_lazy_hal_instantiation";
    public static final java.lang.String FLAG_EXTENSION_10_BIT = "com.android.internal.camera.flags.extension_10_bit";
    public static final java.lang.String FLAG_FEATURE_COMBINATION_QUERY = "com.android.internal.camera.flags.feature_combination_query";
    public static final java.lang.String FLAG_INJECT_SESSION_PARAMS = "com.android.internal.camera.flags.inject_session_params";
    public static final java.lang.String FLAG_LAZY_AIDL_WAIT_FOR_SERVICE = "com.android.internal.camera.flags.lazy_aidl_wait_for_service";
    public static final java.lang.String FLAG_LOG_ULTRAWIDE_USAGE = "com.android.internal.camera.flags.log_ultrawide_usage";
    public static final java.lang.String FLAG_LOG_ZOOM_OVERRIDE_USAGE = "com.android.internal.camera.flags.log_zoom_override_usage";
    public static final java.lang.String FLAG_MULTIRESOLUTION_IMAGEREADER_USAGE_CONFIG = "com.android.internal.camera.flags.multiresolution_imagereader_usage_config";
    public static final java.lang.String FLAG_RETURN_BUFFERS_OUTSIDE_LOCKS = "com.android.internal.camera.flags.return_buffers_outside_locks";
    public static final java.lang.String FLAG_SESSION_HAL_BUF_MANAGER = "com.android.internal.camera.flags.session_hal_buf_manager";
    public static final java.lang.String FLAG_USE_RO_BOARD_API_LEVEL_FOR_VNDK_VERSION = "com.android.internal.camera.flags.use_ro_board_api_level_for_vndk_version";
    public static final java.lang.String FLAG_WATCH_FOREGROUND_CHANGES = "com.android.internal.camera.flags.watch_foreground_changes";

    public static boolean cameraAeModeLowLightBoost() {
        return FEATURE_FLAGS.cameraAeModeLowLightBoost();
    }

    public static boolean cameraDeviceSetup() {
        return FEATURE_FLAGS.cameraDeviceSetup();
    }

    public static boolean cameraExtensionsCharacteristicsGet() {
        return FEATURE_FLAGS.cameraExtensionsCharacteristicsGet();
    }

    public static boolean cameraHsumPermission() {
        return FEATURE_FLAGS.cameraHsumPermission();
    }

    public static boolean cameraManualFlashStrengthControl() {
        return FEATURE_FLAGS.cameraManualFlashStrengthControl();
    }

    public static boolean cameraPrivacyAllowlist() {
        return FEATURE_FLAGS.cameraPrivacyAllowlist();
    }

    public static boolean concertMode() {
        return FEATURE_FLAGS.concertMode();
    }

    public static boolean delayLazyHalInstantiation() {
        return FEATURE_FLAGS.delayLazyHalInstantiation();
    }

    public static boolean extension10Bit() {
        return FEATURE_FLAGS.extension10Bit();
    }

    public static boolean featureCombinationQuery() {
        return FEATURE_FLAGS.featureCombinationQuery();
    }

    public static boolean injectSessionParams() {
        return FEATURE_FLAGS.injectSessionParams();
    }

    public static boolean lazyAidlWaitForService() {
        return FEATURE_FLAGS.lazyAidlWaitForService();
    }

    public static boolean logUltrawideUsage() {
        return FEATURE_FLAGS.logUltrawideUsage();
    }

    public static boolean logZoomOverrideUsage() {
        return FEATURE_FLAGS.logZoomOverrideUsage();
    }

    public static boolean multiresolutionImagereaderUsageConfig() {
        return FEATURE_FLAGS.multiresolutionImagereaderUsageConfig();
    }

    public static boolean returnBuffersOutsideLocks() {
        return FEATURE_FLAGS.returnBuffersOutsideLocks();
    }

    public static boolean sessionHalBufManager() {
        return FEATURE_FLAGS.sessionHalBufManager();
    }

    public static boolean useRoBoardApiLevelForVndkVersion() {
        return FEATURE_FLAGS.useRoBoardApiLevelForVndkVersion();
    }

    public static boolean watchForegroundChanges() {
        return FEATURE_FLAGS.watchForegroundChanges();
    }
}
