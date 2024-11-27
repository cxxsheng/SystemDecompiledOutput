package android.hardware.camera2;

/* loaded from: classes.dex */
public class CaptureResult extends android.hardware.camera2.CameraMetadata<android.hardware.camera2.CaptureResult.Key<?>> {
    private static final java.lang.String TAG = "CaptureResult";
    private static final boolean VERBOSE = false;
    private final java.lang.String mCameraId;
    private final long mFrameNumber;
    private final android.hardware.camera2.CaptureRequest mRequest;
    private final android.hardware.camera2.impl.CameraMetadataNative mResults;
    private final int mSequenceId;

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> COLOR_CORRECTION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.colorCorrection.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.ColorSpaceTransform> COLOR_CORRECTION_TRANSFORM = new android.hardware.camera2.CaptureResult.Key<>("android.colorCorrection.transform", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.RggbChannelVector> COLOR_CORRECTION_GAINS = new android.hardware.camera2.CaptureResult.Key<>("android.colorCorrection.gains", android.hardware.camera2.params.RggbChannelVector.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> COLOR_CORRECTION_ABERRATION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.colorCorrection.aberrationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AE_ANTIBANDING_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeAntibandingMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AE_EXPOSURE_COMPENSATION = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeExposureCompensation", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> CONTROL_AE_LOCK = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeLock", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AE_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AE_REGIONS = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Range<java.lang.Integer>> CONTROL_AE_TARGET_FPS_RANGE = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeTargetFpsRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>>() { // from class: android.hardware.camera2.CaptureResult.1
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AE_PRECAPTURE_TRIGGER = new android.hardware.camera2.CaptureResult.Key<>("android.control.aePrecaptureTrigger", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AE_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.control.aeState", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AF_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.afMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AF_REGIONS = new android.hardware.camera2.CaptureResult.Key<>("android.control.afRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AF_TRIGGER = new android.hardware.camera2.CaptureResult.Key<>("android.control.afTrigger", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AF_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.control.afState", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> CONTROL_AWB_LOCK = new android.hardware.camera2.CaptureResult.Key<>("android.control.awbLock", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AWB_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.awbMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AWB_REGIONS = new android.hardware.camera2.CaptureResult.Key<>("android.control.awbRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_CAPTURE_INTENT = new android.hardware.camera2.CaptureResult.Key<>("android.control.captureIntent", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AWB_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.control.awbState", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_EFFECT_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.effectMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_SCENE_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.sceneMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_VIDEO_STABILIZATION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.videoStabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_POST_RAW_SENSITIVITY_BOOST = new android.hardware.camera2.CaptureResult.Key<>("android.control.postRawSensitivityBoost", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> CONTROL_ENABLE_ZSL = new android.hardware.camera2.CaptureResult.Key<>("android.control.enableZsl", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AF_SCENE_CHANGE = new android.hardware.camera2.CaptureResult.Key<>("android.control.afSceneChange", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_EXTENDED_SCENE_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.control.extendedSceneMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> CONTROL_ZOOM_RATIO = new android.hardware.camera2.CaptureResult.Key<>("android.control.zoomRatio", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_SETTINGS_OVERRIDE = new android.hardware.camera2.CaptureResult.Key<>("android.control.settingsOverride", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AUTOFRAMING = new android.hardware.camera2.CaptureResult.Key<>("android.control.autoframing", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_AUTOFRAMING_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.control.autoframingState", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> CONTROL_LOW_LIGHT_BOOST_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.control.lowLightBoostState", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> EDGE_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.edge.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> FLASH_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.flash.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> FLASH_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.flash.state", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> FLASH_STRENGTH_LEVEL = new android.hardware.camera2.CaptureResult.Key<>("android.flash.strengthLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> HOT_PIXEL_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.hotPixel.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.location.Location> JPEG_GPS_LOCATION = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.gpsLocation", android.location.Location.class);
    public static final android.hardware.camera2.CaptureResult.Key<double[]> JPEG_GPS_COORDINATES = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.gpsCoordinates", double[].class);
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.String> JPEG_GPS_PROCESSING_METHOD = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.gpsProcessingMethod", java.lang.String.class);
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> JPEG_GPS_TIMESTAMP = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.gpsTimestamp", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> JPEG_ORIENTATION = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.orientation", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Byte> JPEG_QUALITY = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.quality", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Byte> JPEG_THUMBNAIL_QUALITY = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.thumbnailQuality", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Size> JPEG_THUMBNAIL_SIZE = new android.hardware.camera2.CaptureResult.Key<>("android.jpeg.thumbnailSize", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> LENS_APERTURE = new android.hardware.camera2.CaptureResult.Key<>("android.lens.aperture", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> LENS_FILTER_DENSITY = new android.hardware.camera2.CaptureResult.Key<>("android.lens.filterDensity", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> LENS_FOCAL_LENGTH = new android.hardware.camera2.CaptureResult.Key<>("android.lens.focalLength", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> LENS_FOCUS_DISTANCE = new android.hardware.camera2.CaptureResult.Key<>("android.lens.focusDistance", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Pair<java.lang.Float, java.lang.Float>> LENS_FOCUS_RANGE = new android.hardware.camera2.CaptureResult.Key<>("android.lens.focusRange", new android.hardware.camera2.utils.TypeReference<android.util.Pair<java.lang.Float, java.lang.Float>>() { // from class: android.hardware.camera2.CaptureResult.2
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> LENS_OPTICAL_STABILIZATION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.lens.opticalStabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> LENS_STATE = new android.hardware.camera2.CaptureResult.Key<>("android.lens.state", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<float[]> LENS_POSE_ROTATION = new android.hardware.camera2.CaptureResult.Key<>("android.lens.poseRotation", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<float[]> LENS_POSE_TRANSLATION = new android.hardware.camera2.CaptureResult.Key<>("android.lens.poseTranslation", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<float[]> LENS_INTRINSIC_CALIBRATION = new android.hardware.camera2.CaptureResult.Key<>("android.lens.intrinsicCalibration", float[].class);

    @android.hardware.camera2.impl.PublicKey
    @java.lang.Deprecated
    public static final android.hardware.camera2.CaptureResult.Key<float[]> LENS_RADIAL_DISTORTION = new android.hardware.camera2.CaptureResult.Key<>("android.lens.radialDistortion", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<float[]> LENS_DISTORTION = new android.hardware.camera2.CaptureResult.Key<>("android.lens.distortion", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> NOISE_REDUCTION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.noiseReduction.mode", java.lang.Integer.TYPE);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> QUIRKS_PARTIAL_RESULT = new android.hardware.camera2.CaptureResult.Key<>("android.quirks.partialResult", java.lang.Boolean.TYPE);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> REQUEST_FRAME_COUNT = new android.hardware.camera2.CaptureResult.Key<>("android.request.frameCount", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> REQUEST_ID = new android.hardware.camera2.CaptureResult.Key<>(android.content.RestrictionsManager.REQUEST_KEY_ID, java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Byte> REQUEST_PIPELINE_DEPTH = new android.hardware.camera2.CaptureResult.Key<>("android.request.pipelineDepth", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.Rect> SCALER_CROP_REGION = new android.hardware.camera2.CaptureResult.Key<>("android.scaler.cropRegion", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SCALER_ROTATE_AND_CROP = new android.hardware.camera2.CaptureResult.Key<>("android.scaler.rotateAndCrop", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.Rect> SCALER_RAW_CROP_REGION = new android.hardware.camera2.CaptureResult.Key<>("android.scaler.rawCropRegion", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> SENSOR_EXPOSURE_TIME = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.exposureTime", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> SENSOR_FRAME_DURATION = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.frameDuration", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SENSOR_SENSITIVITY = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.sensitivity", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> SENSOR_TIMESTAMP = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.timestamp", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Rational[]> SENSOR_NEUTRAL_COLOR_POINT = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.neutralColorPoint", android.util.Rational[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Pair<java.lang.Double, java.lang.Double>[]> SENSOR_NOISE_PROFILE = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.noiseProfile", new android.hardware.camera2.utils.TypeReference<android.util.Pair<java.lang.Double, java.lang.Double>[]>() { // from class: android.hardware.camera2.CaptureResult.3
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> SENSOR_GREEN_SPLIT = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.greenSplit", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<int[]> SENSOR_TEST_PATTERN_DATA = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.testPatternData", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SENSOR_TEST_PATTERN_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.testPatternMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> SENSOR_ROLLING_SHUTTER_SKEW = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.rollingShutterSkew", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<float[]> SENSOR_DYNAMIC_BLACK_LEVEL = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.dynamicBlackLevel", float[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SENSOR_DYNAMIC_WHITE_LEVEL = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.dynamicWhiteLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SENSOR_PIXEL_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.pixelMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> SENSOR_RAW_BINNING_FACTOR_USED = new android.hardware.camera2.CaptureResult.Key<>("android.sensor.rawBinningFactorUsed", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> SHADING_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.shading.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> STATISTICS_FACE_DETECT_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faceDetectMode", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureResult.Key<int[]> STATISTICS_FACE_IDS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faceIds", int[].class);
    public static final android.hardware.camera2.CaptureResult.Key<int[]> STATISTICS_FACE_LANDMARKS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faceLandmarks", int[].class);
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.Rect[]> STATISTICS_FACE_RECTANGLES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faceRectangles", android.graphics.Rect[].class);
    public static final android.hardware.camera2.CaptureResult.Key<byte[]> STATISTICS_FACE_SCORES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faceScores", byte[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.Face[]> STATISTICS_FACES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.faces", android.hardware.camera2.params.Face[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.LensShadingMap> STATISTICS_LENS_SHADING_CORRECTION_MAP = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensShadingCorrectionMap", android.hardware.camera2.params.LensShadingMap.class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> STATISTICS_LENS_SHADING_MAP = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensShadingMap", float[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CaptureResult.Key<float[]> STATISTICS_PREDICTED_COLOR_GAINS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.predictedColorGains", float[].class);

    @java.lang.Deprecated
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Rational[]> STATISTICS_PREDICTED_COLOR_TRANSFORM = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.predictedColorTransform", android.util.Rational[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> STATISTICS_SCENE_FLICKER = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.sceneFlicker", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> STATISTICS_HOT_PIXEL_MAP_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.hotPixelMapMode", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.Point[]> STATISTICS_HOT_PIXEL_MAP = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.hotPixelMap", android.graphics.Point[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> STATISTICS_LENS_SHADING_MAP_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensShadingMapMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> STATISTICS_OIS_DATA_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.oisDataMode", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureResult.Key<long[]> STATISTICS_OIS_TIMESTAMPS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.oisTimestamps", long[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> STATISTICS_OIS_X_SHIFTS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.oisXShifts", float[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> STATISTICS_OIS_Y_SHIFTS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.oisYShifts", float[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.OisSample[]> STATISTICS_OIS_SAMPLES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.oisSamples", android.hardware.camera2.params.OisSample[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.LensIntrinsicsSample[]> STATISTICS_LENS_INTRINSICS_SAMPLES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensIntrinsicsSamples", android.hardware.camera2.params.LensIntrinsicsSample[].class);
    public static final android.hardware.camera2.CaptureResult.Key<long[]> STATISTICS_LENS_INTRINSIC_TIMESTAMPS = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensIntrinsicTimestamps", long[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> STATISTICS_LENS_INTRINSIC_SAMPLES = new android.hardware.camera2.CaptureResult.Key<>("android.statistics.lensIntrinsicSamples", float[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> TONEMAP_CURVE_BLUE = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.curveBlue", float[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> TONEMAP_CURVE_GREEN = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.curveGreen", float[].class);
    public static final android.hardware.camera2.CaptureResult.Key<float[]> TONEMAP_CURVE_RED = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.curveRed", float[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.params.TonemapCurve> TONEMAP_CURVE = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.curve", android.hardware.camera2.params.TonemapCurve.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> TONEMAP_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> TONEMAP_GAMMA = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.gamma", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> TONEMAP_PRESET_CURVE = new android.hardware.camera2.CaptureResult.Key<>("android.tonemap.presetCurve", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> LED_TRANSMIT = new android.hardware.camera2.CaptureResult.Key<>("android.led.transmit", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> BLACK_LEVEL_LOCK = new android.hardware.camera2.CaptureResult.Key<>("android.blackLevel.lock", java.lang.Boolean.TYPE);
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Long> SYNC_FRAME_NUMBER = new android.hardware.camera2.CaptureResult.Key<>("android.sync.frameNumber", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = new android.hardware.camera2.CaptureResult.Key<>("android.reprocess.effectiveExposureFactor", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.String> LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID = new android.hardware.camera2.CaptureResult.Key<>("android.logicalMultiCamera.activePhysicalId", java.lang.String.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.Rect> LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_SENSOR_CROP_REGION = new android.hardware.camera2.CaptureResult.Key<>("android.logicalMultiCamera.activePhysicalSensorCropRegion", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> DISTORTION_CORRECTION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.distortionCorrection.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> EXTENSION_CURRENT_TYPE = new android.hardware.camera2.CaptureResult.Key<>("android.extension.currentType", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> EXTENSION_STRENGTH = new android.hardware.camera2.CaptureResult.Key<>("android.extension.strength", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<int[]> EFV_PADDING_REGION = new android.hardware.camera2.CaptureResult.Key<>("android.efv.paddingRegion", int[].class);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<int[]> EFV_AUTO_ZOOM_PADDING_REGION = new android.hardware.camera2.CaptureResult.Key<>("android.efv.autoZoomPaddingRegion", int[].class);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<android.graphics.PointF[]> EFV_TARGET_COORDINATES = new android.hardware.camera2.CaptureResult.Key<>("android.efv.targetCoordinates", android.graphics.PointF[].class);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> EFV_PADDING_ZOOM_FACTOR = new android.hardware.camera2.CaptureResult.Key<>("android.efv.paddingZoomFactor", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Integer> EFV_STABILIZATION_MODE = new android.hardware.camera2.CaptureResult.Key<>("android.efv.stabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Boolean> EFV_AUTO_ZOOM = new android.hardware.camera2.CaptureResult.Key<>("android.efv.autoZoom", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> EFV_ROTATE_VIEWPORT = new android.hardware.camera2.CaptureResult.Key<>("android.efv.rotateViewport", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<android.util.Pair<java.lang.Integer, java.lang.Integer>> EFV_TRANSLATE_VIEWPORT = new android.hardware.camera2.CaptureResult.Key<>("android.efv.translateViewport", new android.hardware.camera2.utils.TypeReference<android.util.Pair<java.lang.Integer, java.lang.Integer>>() { // from class: android.hardware.camera2.CaptureResult.4
    });

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureResult.Key<java.lang.Float> EFV_MAX_PADDING_ZOOM_FACTOR = new android.hardware.camera2.CaptureResult.Key<>("android.efv.maxPaddingZoomFactor", java.lang.Float.TYPE);

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
            return (obj instanceof android.hardware.camera2.CaptureResult.Key) && ((android.hardware.camera2.CaptureResult.Key) obj).mKey.equals(this.mKey);
        }

        public java.lang.String toString() {
            return java.lang.String.format("CaptureResult.Key(%s)", this.mKey.getName());
        }

        public android.hardware.camera2.impl.CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Key(android.hardware.camera2.impl.CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }
    }

    public CaptureResult(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) {
        if (cameraMetadataNative == null) {
            throw new java.lang.IllegalArgumentException("results was null");
        }
        if (captureRequest == null) {
            throw new java.lang.IllegalArgumentException("parent was null");
        }
        if (captureResultExtras == null) {
            throw new java.lang.IllegalArgumentException("extras was null");
        }
        this.mResults = android.hardware.camera2.impl.CameraMetadataNative.move(cameraMetadataNative);
        if (this.mResults.isEmpty()) {
            throw new java.lang.AssertionError("Results must not be empty");
        }
        setNativeInstance(this.mResults);
        this.mCameraId = str;
        this.mRequest = captureRequest;
        this.mSequenceId = captureResultExtras.getRequestId();
        this.mFrameNumber = captureResultExtras.getFrameNumber();
    }

    public CaptureResult(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.CaptureRequest captureRequest, int i, long j) {
        if (cameraMetadataNative == null) {
            throw new java.lang.IllegalArgumentException("results was null");
        }
        if (captureRequest == null) {
            throw new java.lang.IllegalArgumentException("parent was null");
        }
        this.mResults = android.hardware.camera2.impl.CameraMetadataNative.move(cameraMetadataNative);
        if (this.mResults.isEmpty()) {
            throw new java.lang.AssertionError("Results must not be empty");
        }
        setNativeInstance(this.mResults);
        this.mCameraId = str;
        this.mRequest = captureRequest;
        this.mSequenceId = i;
        this.mFrameNumber = j;
    }

    public android.hardware.camera2.impl.CameraMetadataNative getNativeCopy() {
        return new android.hardware.camera2.impl.CameraMetadataNative(this.mResults);
    }

    public CaptureResult(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i) {
        if (cameraMetadataNative == null) {
            throw new java.lang.IllegalArgumentException("results was null");
        }
        this.mResults = android.hardware.camera2.impl.CameraMetadataNative.move(cameraMetadataNative);
        if (this.mResults.isEmpty()) {
            throw new java.lang.AssertionError("Results must not be empty");
        }
        setNativeInstance(this.mResults);
        this.mCameraId = "none";
        this.mRequest = null;
        this.mSequenceId = i;
        this.mFrameNumber = -1L;
    }

    public java.lang.String getCameraId() {
        return this.mCameraId;
    }

    public <T> T get(android.hardware.camera2.CaptureResult.Key<T> key) {
        return (T) this.mResults.get(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.hardware.camera2.CameraMetadata
    public <T> T getProtected(android.hardware.camera2.CaptureResult.Key<?> key) {
        return (T) this.mResults.get(key);
    }

    @Override // android.hardware.camera2.CameraMetadata
    protected java.lang.Class<android.hardware.camera2.CaptureResult.Key<?>> getKeyClass() {
        return android.hardware.camera2.CaptureResult.Key.class;
    }

    public void dumpToLog() {
        this.mResults.dumpToLog();
    }

    @Override // android.hardware.camera2.CameraMetadata
    public java.util.List<android.hardware.camera2.CaptureResult.Key<?>> getKeys() {
        return super.getKeys();
    }

    public android.hardware.camera2.CaptureRequest getRequest() {
        return this.mRequest;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }
}
