package android.hardware.camera2;

/* loaded from: classes.dex */
public final class CameraCharacteristics extends android.hardware.camera2.CameraMetadata<android.hardware.camera2.CameraCharacteristics.Key<?>> {
    private static final java.lang.String TAG = "CameraCharacteristics";
    private java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> mAvailablePhysicalRequestKeys;
    private java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> mAvailableRequestKeys;
    private java.util.List<android.hardware.camera2.CaptureResult.Key<?>> mAvailableResultKeys;
    private java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> mAvailableSessionCharacteristicsKeys;
    private java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> mAvailableSessionKeys;
    private android.hardware.camera2.CameraManager.DeviceStateListener mFoldStateListener;
    private boolean mFoldedDeviceState;
    private java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> mKeys;
    private java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> mKeysNeedingPermission;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.camera2.impl.CameraMetadataNative mProperties;
    private java.util.ArrayList<android.hardware.camera2.params.RecommendedStreamConfigurationMap> mRecommendedConfigurations;

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.colorCorrection.availableAberrationModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AE_AVAILABLE_ANTIBANDING_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeAvailableAntibandingModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AE_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeAvailableModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Integer>[]> CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeAvailableTargetFpsRanges", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>[]>() { // from class: android.hardware.camera2.CameraCharacteristics.2
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Integer>> CONTROL_AE_COMPENSATION_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeCompensationRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.3
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Rational> CONTROL_AE_COMPENSATION_STEP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeCompensationStep", android.util.Rational.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AF_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.afAvailableModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_EFFECTS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableEffects", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_SCENE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableSceneModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableVideoStabilizationModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AWB_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.awbAvailableModes", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_MAX_REGIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.maxRegions", int[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> CONTROL_MAX_REGIONS_AE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.maxRegionsAe", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> CONTROL_MAX_REGIONS_AWB = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.maxRegionsAwb", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> CONTROL_MAX_REGIONS_AF = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.maxRegionsAf", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.HighSpeedVideoConfiguration[]> CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableHighSpeedVideoConfigurations", android.hardware.camera2.params.HighSpeedVideoConfiguration[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> CONTROL_AE_LOCK_AVAILABLE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.aeLockAvailable", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> CONTROL_AWB_LOCK_AVAILABLE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.awbLockAvailable", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Integer>> CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.postRawSensitivityBoostRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.4
    });
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_MAX_SIZES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableExtendedSceneModeMaxSizes", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_ZOOM_RATIO_RANGES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableExtendedSceneModeZoomRatioRanges", float[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.Capability[]> CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableExtendedSceneModeCapabilities", android.hardware.camera2.params.Capability[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Float>> CONTROL_ZOOM_RATIO_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.zoomRatioRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Float>>() { // from class: android.hardware.camera2.CameraCharacteristics.5
    });
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.HighSpeedVideoConfiguration[]> CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableHighSpeedVideoConfigurationsMaximumResolution", android.hardware.camera2.params.HighSpeedVideoConfiguration[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> CONTROL_AVAILABLE_SETTINGS_OVERRIDES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.availableSettingsOverrides", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> CONTROL_AUTOFRAMING_AVAILABLE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.autoframingAvailable", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Float>> CONTROL_LOW_LIGHT_BOOST_INFO_LUMINANCE_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.control.lowLightBoostInfoLuminanceRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Float>>() { // from class: android.hardware.camera2.CameraCharacteristics.6
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> EDGE_AVAILABLE_EDGE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.edge.availableEdgeModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> FLASH_INFO_AVAILABLE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.info.available", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_INFO_STRENGTH_MAXIMUM_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.info.strengthMaximumLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_INFO_STRENGTH_DEFAULT_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.info.strengthDefaultLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_SINGLE_STRENGTH_MAX_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.singleStrengthMaxLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_SINGLE_STRENGTH_DEFAULT_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.singleStrengthDefaultLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_TORCH_STRENGTH_MAX_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.torchStrengthMaxLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> FLASH_TORCH_STRENGTH_DEFAULT_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.flash.torchStrengthDefaultLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.hotPixel.availableHotPixelModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size[]> JPEG_AVAILABLE_THUMBNAIL_SIZES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpeg.availableThumbnailSizes", android.util.Size[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_INFO_AVAILABLE_APERTURES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.availableApertures", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_INFO_AVAILABLE_FILTER_DENSITIES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.availableFilterDensities", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_INFO_AVAILABLE_FOCAL_LENGTHS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.availableFocalLengths", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.availableOpticalStabilization", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Float> LENS_INFO_HYPERFOCAL_DISTANCE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.hyperfocalDistance", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Float> LENS_INFO_MINIMUM_FOCUS_DISTANCE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.minimumFocusDistance", java.lang.Float.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size> LENS_INFO_SHADING_MAP_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.shadingMapSize", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> LENS_INFO_FOCUS_DISTANCE_CALIBRATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.info.focusDistanceCalibration", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> LENS_FACING = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.facing", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_POSE_ROTATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.poseRotation", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_POSE_TRANSLATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.poseTranslation", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_INTRINSIC_CALIBRATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.intrinsicCalibration", float[].class);

    @android.hardware.camera2.impl.PublicKey
    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_RADIAL_DISTORTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.radialDistortion", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> LENS_POSE_REFERENCE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.poseReference", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_DISTORTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.distortion", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_DISTORTION_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.distortionMaximumResolution", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<float[]> LENS_INTRINSIC_CALIBRATION_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.lens.intrinsicCalibrationMaximumResolution", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.noiseReduction.availableNoiseReductionModes", int[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Byte> QUIRKS_USE_PARTIAL_RESULT = new android.hardware.camera2.CameraCharacteristics.Key<>("android.quirks.usePartialResult", java.lang.Byte.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_MAX_NUM_OUTPUT_STREAMS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.maxNumOutputStreams", int[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REQUEST_MAX_NUM_OUTPUT_RAW = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.maxNumOutputRaw", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REQUEST_MAX_NUM_OUTPUT_PROC = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.maxNumOutputProc", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REQUEST_MAX_NUM_OUTPUT_PROC_STALLING = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.maxNumOutputProcStalling", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REQUEST_MAX_NUM_INPUT_STREAMS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.maxNumInputStreams", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Byte> REQUEST_PIPELINE_MAX_DEPTH = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.pipelineMaxDepth", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REQUEST_PARTIAL_RESULT_COUNT = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.partialResultCount", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_CAPABILITIES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableCapabilities", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_REQUEST_KEYS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableRequestKeys", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_RESULT_KEYS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableResultKeys", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_CHARACTERISTICS_KEYS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableCharacteristicsKeys", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_SESSION_KEYS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableSessionKeys", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availablePhysicalCameraRequestKeys", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.characteristicKeysNeedingPermission", int[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.DynamicRangeProfiles> REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableDynamicRangeProfiles", android.hardware.camera2.params.DynamicRangeProfiles.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableDynamicRangeProfilesMap", long[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Long> REQUEST_RECOMMENDED_TEN_BIT_DYNAMIC_RANGE_PROFILE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.recommendedTenBitDynamicRangeProfile", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceProfiles> REQUEST_AVAILABLE_COLOR_SPACE_PROFILES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableColorSpaceProfiles", android.hardware.camera2.params.ColorSpaceProfiles.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> REQUEST_AVAILABLE_COLOR_SPACE_PROFILES_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.request.availableColorSpaceProfilesMap", long[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> SCALER_AVAILABLE_FORMATS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableFormats", int[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> SCALER_AVAILABLE_JPEG_MIN_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableJpegMinDurations", long[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size[]> SCALER_AVAILABLE_JPEG_SIZES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableJpegSizes", android.util.Size[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Float> SCALER_AVAILABLE_MAX_DIGITAL_ZOOM = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableMaxDigitalZoom", java.lang.Float.TYPE);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> SCALER_AVAILABLE_PROCESSED_MIN_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableProcessedMinDurations", long[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size[]> SCALER_AVAILABLE_PROCESSED_SIZES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableProcessedSizes", android.util.Size[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ReprocessFormatsMap> SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableInputOutputFormatsMap", android.hardware.camera2.params.ReprocessFormatsMap.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> SCALER_AVAILABLE_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> SCALER_AVAILABLE_MIN_FRAME_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableMinFrameDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> SCALER_AVAILABLE_STALL_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableStallDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.streamConfigurationMap", android.hardware.camera2.params.StreamConfigurationMap.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SCALER_CROPPING_TYPE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.croppingType", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.RecommendedStreamConfiguration[]> SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableRecommendedStreamConfigurations", android.hardware.camera2.params.RecommendedStreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ReprocessFormatsMap> SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableRecommendedInputOutputFormatsMap", android.hardware.camera2.params.ReprocessFormatsMap.class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_CONCURRENT_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryConcurrentStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> SCALER_AVAILABLE_ROTATE_AND_CROP_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableRotateAndCropModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size> SCALER_DEFAULT_SECURE_IMAGE_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.defaultSecureImageSize", android.util.Size.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.physicalCameraMultiResolutionStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MultiResolutionStreamConfigurationMap> SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.multiResolutionStreamConfigurationMap", android.hardware.camera2.params.MultiResolutionStreamConfigurationMap.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> SCALER_AVAILABLE_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableStreamConfigurationsMaximumResolution", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> SCALER_AVAILABLE_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableMinFrameDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> SCALER_AVAILABLE_STALL_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableStallDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.streamConfigurationMapMaximumResolution", android.hardware.camera2.params.StreamConfigurationMap.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ReprocessFormatsMap> SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableInputOutputFormatsMapMaximumResolution", android.hardware.camera2.params.ReprocessFormatsMap.class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_MAXIMUM_RESOLUTION_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryMaximumResolutionStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_TEN_BIT_OUTPUT_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryTenBitOutputStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_PREVIEW_STABILIZATION_OUTPUT_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryPreviewStabilizationOutputStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.multiResolutionStreamSupported", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> SCALER_AVAILABLE_STREAM_USE_CASES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.availableStreamUseCases", long[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.MandatoryStreamCombination[]> SCALER_MANDATORY_USE_CASE_STREAM_COMBINATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.scaler.mandatoryUseCaseStreamCombinations", android.hardware.camera2.params.MandatoryStreamCombination[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.graphics.Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.activeArraySize", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Integer>> SENSOR_INFO_SENSITIVITY_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.sensitivityRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>>() { // from class: android.hardware.camera2.CameraCharacteristics.7
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_INFO_COLOR_FILTER_ARRANGEMENT = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.colorFilterArrangement", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Long>> SENSOR_INFO_EXPOSURE_TIME_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.exposureTimeRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Long>>() { // from class: android.hardware.camera2.CameraCharacteristics.8
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Long> SENSOR_INFO_MAX_FRAME_DURATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.maxFrameDuration", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.SizeF> SENSOR_INFO_PHYSICAL_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.physicalSize", android.util.SizeF.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size> SENSOR_INFO_PIXEL_ARRAY_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.pixelArraySize", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_INFO_WHITE_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.whiteLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_INFO_TIMESTAMP_SOURCE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.timestampSource", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> SENSOR_INFO_LENS_SHADING_APPLIED = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.lensShadingApplied", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.graphics.Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.preCorrectionActiveArraySize", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.graphics.Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.activeArraySizeMaximumResolution", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size> SENSOR_INFO_PIXEL_ARRAY_SIZE_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.pixelArraySizeMaximumResolution", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.graphics.Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.preCorrectionActiveArraySizeMaximumResolution", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Size> SENSOR_INFO_BINNING_FACTOR = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.info.binningFactor", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_REFERENCE_ILLUMINANT1 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.referenceIlluminant1", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Byte> SENSOR_REFERENCE_ILLUMINANT2 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.referenceIlluminant2", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM1 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.calibrationTransform1", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM2 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.calibrationTransform2", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_COLOR_TRANSFORM1 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.colorTransform1", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_COLOR_TRANSFORM2 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.colorTransform2", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_FORWARD_MATRIX1 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.forwardMatrix1", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.ColorSpaceTransform> SENSOR_FORWARD_MATRIX2 = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.forwardMatrix2", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.BlackLevelPattern> SENSOR_BLACK_LEVEL_PATTERN = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.blackLevelPattern", android.hardware.camera2.params.BlackLevelPattern.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_MAX_ANALOG_SENSITIVITY = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.maxAnalogSensitivity", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_ORIENTATION = new android.hardware.camera2.CameraCharacteristics.Key<>(android.hardware.Sensor.STRING_TYPE_ORIENTATION, java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> SENSOR_AVAILABLE_TEST_PATTERN_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.availableTestPatternModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.graphics.Rect[]> SENSOR_OPTICAL_BLACK_REGIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.opticalBlackRegions", android.graphics.Rect[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SENSOR_READOUT_TIMESTAMP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sensor.readoutTimestamp", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> SHADING_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.shading.availableModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.statistics.info.availableFaceDetectModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> STATISTICS_INFO_MAX_FACE_COUNT = new android.hardware.camera2.CameraCharacteristics.Key<>("android.statistics.info.maxFaceCount", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<boolean[]> STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.statistics.info.availableHotPixelMapModes", boolean[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.statistics.info.availableLensShadingMapModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> STATISTICS_INFO_AVAILABLE_OIS_DATA_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.statistics.info.availableOisDataModes", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> TONEMAP_MAX_CURVE_POINTS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.tonemap.maxCurvePoints", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> TONEMAP_AVAILABLE_TONE_MAP_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.tonemap.availableToneMapModes", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> LED_AVAILABLE_LEDS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.led.availableLeds", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> INFO_SUPPORTED_HARDWARE_LEVEL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.info.supportedHardwareLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.String> INFO_VERSION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.info.version", java.lang.String.class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.DeviceStateSensorOrientationMap> INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP = new android.hardware.camera2.CameraCharacteristics.Key<>("android.info.deviceStateSensorOrientationMap", android.hardware.camera2.params.DeviceStateSensorOrientationMap.class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<long[]> INFO_DEVICE_STATE_ORIENTATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.info.deviceStateOrientations", long[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> INFO_SESSION_CONFIGURATION_QUERY_VERSION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.info.sessionConfigurationQueryVersion", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> SYNC_MAX_LATENCY = new android.hardware.camera2.CameraCharacteristics.Key<>("android.sync.maxLatency", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> REPROCESS_MAX_CAPTURE_STALL = new android.hardware.camera2.CameraCharacteristics.Key<>("android.reprocess.maxCaptureStall", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthMinFrameDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthStallDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Boolean> DEPTH_DEPTH_IS_EXCLUSIVE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.depthIsExclusive", java.lang.Boolean.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.RecommendedStreamConfiguration[]> DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableRecommendedDepthStreamConfigurations", android.hardware.camera2.params.RecommendedStreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthMinFrameDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthStallDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthStreamConfigurationsMaximumResolution", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthMinFrameDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDepthStallDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthStreamConfigurationsMaximumResolution", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthMinFrameDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.depth.availableDynamicDepthStallDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<byte[]> LOGICAL_MULTI_CAMERA_PHYSICAL_IDS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.logicalMultiCamera.physicalIds", byte[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.logicalMultiCamera.sensorSyncType", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> DISTORTION_CORRECTION_AVAILABLE_MODES = new android.hardware.camera2.CameraCharacteristics.Key<>("android.distortionCorrection.availableModes", int[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicMinFrameDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_STALL_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicStallDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicStreamConfigurationsMaximumResolution", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicMinFrameDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_STALL_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.heic.availableHeicStallDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<int[]> AUTOMOTIVE_LENS_FACING = new android.hardware.camera2.CameraCharacteristics.Key<>("android.automotive.lens.facing", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<java.lang.Integer> AUTOMOTIVE_LOCATION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.automotive.location", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRStreamConfigurations", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRMinFrameDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRStallDurations", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfiguration[]> JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRStreamConfigurationsMaximumResolution", android.hardware.camera2.params.StreamConfiguration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRMinFrameDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.params.StreamConfigurationDuration[]> JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS_MAXIMUM_RESOLUTION = new android.hardware.camera2.CameraCharacteristics.Key<>("android.jpegr.availableJpegRStallDurationsMaximumResolution", android.hardware.camera2.params.StreamConfigurationDuration[].class);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Float>> EFV_PADDING_ZOOM_FACTOR_RANGE = new android.hardware.camera2.CameraCharacteristics.Key<>("android.efv.paddingZoomFactorRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Float>>() { // from class: android.hardware.camera2.CameraCharacteristics.9
    });

    public static final class Key<T> {
        private final android.hardware.camera2.impl.CameraMetadataNative.Key<T> mKey;

        public Key(java.lang.String str, java.lang.Class<T> cls, long j) {
            this.mKey = new android.hardware.camera2.impl.CameraMetadataNative.Key<>(str, cls, j);
        }

        public Key(java.lang.String str, java.lang.String str2, java.lang.Class<T> cls) {
            this.mKey = new android.hardware.camera2.impl.CameraMetadataNative.Key<>(str, str2, cls);
        }

        public Key(java.lang.String str, java.lang.Class<T> cls) {
            this.mKey = new android.hardware.camera2.impl.CameraMetadataNative.Key<>(str, cls);
        }

        public Key(java.lang.String str, android.hardware.camera2.utils.TypeReference<T> typeReference) {
            this.mKey = new android.hardware.camera2.impl.CameraMetadataNative.Key<>(str, typeReference);
        }

        public java.lang.String getName() {
            return this.mKey.getName();
        }

        public long getVendorId() {
            return this.mKey.getVendorId();
        }

        public final int hashCode() {
            return this.mKey.hashCode();
        }

        public final boolean equals(java.lang.Object obj) {
            return (obj instanceof android.hardware.camera2.CameraCharacteristics.Key) && ((android.hardware.camera2.CameraCharacteristics.Key) obj).mKey.equals(this.mKey);
        }

        public java.lang.String toString() {
            return java.lang.String.format("CameraCharacteristics.Key(%s)", this.mKey.getName());
        }

        public android.hardware.camera2.impl.CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Key(android.hardware.camera2.impl.CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }
    }

    public CameraCharacteristics(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
        this.mProperties = android.hardware.camera2.impl.CameraMetadataNative.move(cameraMetadataNative);
        setNativeInstance(this.mProperties);
    }

    public android.hardware.camera2.impl.CameraMetadataNative getNativeCopy() {
        return new android.hardware.camera2.impl.CameraMetadataNative(this.mProperties);
    }

    android.hardware.camera2.CameraManager.DeviceStateListener getDeviceStateListener() {
        if (this.mFoldStateListener == null) {
            this.mFoldStateListener = new android.hardware.camera2.CameraManager.DeviceStateListener() { // from class: android.hardware.camera2.CameraCharacteristics.1
                @Override // android.hardware.camera2.CameraManager.DeviceStateListener
                public final void onDeviceStateChanged(boolean z) {
                    synchronized (android.hardware.camera2.CameraCharacteristics.this.mLock) {
                        android.hardware.camera2.CameraCharacteristics.this.mFoldedDeviceState = z;
                    }
                }
            };
        }
        return this.mFoldStateListener;
    }

    /* JADX WARN: Type inference failed for: r4v13, types: [T, java.lang.Integer] */
    private <T> T overrideProperty(android.hardware.camera2.CameraCharacteristics.Key<T> key) {
        if (SENSOR_ORIENTATION.equals(key) && this.mFoldStateListener != null && this.mProperties.get(INFO_DEVICE_STATE_ORIENTATIONS) != null) {
            android.hardware.camera2.params.DeviceStateSensorOrientationMap deviceStateSensorOrientationMap = (android.hardware.camera2.params.DeviceStateSensorOrientationMap) this.mProperties.get(INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP);
            synchronized (this.mLock) {
                ?? r4 = (T) java.lang.Integer.valueOf(deviceStateSensorOrientationMap.getSensorOrientation(this.mFoldedDeviceState ? 4L : 0L));
                if (r4.intValue() >= 0) {
                    return r4;
                }
                android.util.Log.w(TAG, "No valid device state to orientation mapping! Using default!");
                return null;
            }
        }
        return null;
    }

    public <T> T get(android.hardware.camera2.CameraCharacteristics.Key<T> key) {
        T t = (T) overrideProperty(key);
        return t != null ? t : (T) this.mProperties.get(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.hardware.camera2.CameraMetadata
    public <T> T getProtected(android.hardware.camera2.CameraCharacteristics.Key<?> key) {
        return (T) this.mProperties.get(key);
    }

    @Override // android.hardware.camera2.CameraMetadata
    protected java.lang.Class<android.hardware.camera2.CameraCharacteristics.Key<?>> getKeyClass() {
        return android.hardware.camera2.CameraCharacteristics.Key.class;
    }

    @Override // android.hardware.camera2.CameraMetadata
    public java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> getKeys() {
        if (this.mKeys != null) {
            return this.mKeys;
        }
        int[] iArr = (int[]) get(REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
        if (iArr == null) {
            throw new java.lang.AssertionError("android.request.availableCharacteristicsKeys must be non-null in the characteristics");
        }
        this.mKeys = java.util.Collections.unmodifiableList(getKeys(getClass(), getKeyClass(), this, iArr, true));
        return this.mKeys;
    }

    public java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> getKeysNeedingPermission() {
        if (this.mKeysNeedingPermission == null) {
            int[] iArr = (int[]) get(REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION);
            if (iArr != null) {
                this.mKeysNeedingPermission = getAvailableKeyList(android.hardware.camera2.CameraCharacteristics.class, android.hardware.camera2.CameraCharacteristics.Key.class, iArr, false);
            } else {
                this.mKeysNeedingPermission = java.util.Collections.unmodifiableList(new java.util.ArrayList());
                return this.mKeysNeedingPermission;
            }
        }
        return this.mKeysNeedingPermission;
    }

    public android.hardware.camera2.params.RecommendedStreamConfigurationMap getRecommendedStreamConfigurationMap(int i) {
        if ((i >= 0 && i <= 8) || (i >= 24 && i < 32)) {
            if (this.mRecommendedConfigurations == null) {
                this.mRecommendedConfigurations = this.mProperties.getRecommendedStreamConfigurations();
                if (this.mRecommendedConfigurations == null) {
                    return null;
                }
            }
            return this.mRecommendedConfigurations.get(i);
        }
        throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid use case: %d", java.lang.Integer.valueOf(i)));
    }

    public java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> getAvailableSessionKeys() {
        if (this.mAvailableSessionKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_SESSION_KEYS);
            if (iArr != null) {
                this.mAvailableSessionKeys = getAvailableKeyList(android.hardware.camera2.CaptureRequest.class, android.hardware.camera2.CaptureRequest.Key.class, iArr, false);
            } else {
                return null;
            }
        }
        return this.mAvailableSessionKeys;
    }

    public java.util.List<android.hardware.camera2.CameraCharacteristics.Key<?>> getAvailableSessionCharacteristicsKeys() {
        if (this.mAvailableSessionCharacteristicsKeys == null) {
            this.mAvailableSessionCharacteristicsKeys = java.util.Arrays.asList(CONTROL_ZOOM_RATIO_RANGE, SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
        }
        return this.mAvailableSessionCharacteristicsKeys;
    }

    public java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> getAvailablePhysicalCameraRequestKeys() {
        if (this.mAvailablePhysicalRequestKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS);
            if (iArr != null) {
                this.mAvailablePhysicalRequestKeys = getAvailableKeyList(android.hardware.camera2.CaptureRequest.class, android.hardware.camera2.CaptureRequest.Key.class, iArr, false);
            } else {
                return null;
            }
        }
        return this.mAvailablePhysicalRequestKeys;
    }

    public java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> getAvailableCaptureRequestKeys() {
        if (this.mAvailableRequestKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_REQUEST_KEYS);
            if (iArr != null) {
                this.mAvailableRequestKeys = getAvailableKeyList(android.hardware.camera2.CaptureRequest.class, android.hardware.camera2.CaptureRequest.Key.class, iArr, true);
            } else {
                throw new java.lang.AssertionError("android.request.availableRequestKeys must be non-null in the characteristics");
            }
        }
        return this.mAvailableRequestKeys;
    }

    public java.util.List<android.hardware.camera2.CaptureResult.Key<?>> getAvailableCaptureResultKeys() {
        if (this.mAvailableResultKeys == null) {
            int[] iArr = (int[]) get(REQUEST_AVAILABLE_RESULT_KEYS);
            if (iArr != null) {
                this.mAvailableResultKeys = getAvailableKeyList(android.hardware.camera2.CaptureResult.class, android.hardware.camera2.CaptureResult.Key.class, iArr, true);
            } else {
                throw new java.lang.AssertionError("android.request.availableResultKeys must be non-null in the characteristics");
            }
        }
        return this.mAvailableResultKeys;
    }

    <TKey> java.util.List<TKey> getAvailableKeyList(java.lang.Class<?> cls, java.lang.Class<TKey> cls2, int[] iArr, boolean z) {
        if (cls.equals(android.hardware.camera2.CameraMetadata.class)) {
            throw new java.lang.AssertionError("metadataClass must be a strict subclass of CameraMetadata");
        }
        if (!android.hardware.camera2.CameraMetadata.class.isAssignableFrom(cls)) {
            throw new java.lang.AssertionError("metadataClass must be a subclass of CameraMetadata");
        }
        return java.util.Collections.unmodifiableList(getKeys(cls, cls2, null, iArr, z));
    }

    public java.util.Set<java.lang.String> getPhysicalCameraIds() {
        return this.mProperties.getPhysicalCameraIds();
    }
}
