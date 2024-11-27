package android.hardware.camera2;

/* loaded from: classes.dex */
public final class CaptureRequest extends android.hardware.camera2.CameraMetadata<android.hardware.camera2.CaptureRequest.Key<?>> implements android.os.Parcelable {
    public static final int REQUEST_TYPE_COUNT = 3;
    public static final int REQUEST_TYPE_REGULAR = 0;
    public static final int REQUEST_TYPE_REPROCESS = 1;
    public static final int REQUEST_TYPE_ZSL_STILL = 2;
    private static final java.lang.String SET_TAG_STRING_PREFIX = "android.hardware.camera2.CaptureRequest.setTag.";
    private final java.lang.String TAG;
    private boolean mIsPartOfCHSRequestList;
    private boolean mIsReprocess;
    private java.lang.String mLogicalCameraId;
    private android.hardware.camera2.impl.CameraMetadataNative mLogicalCameraSettings;
    private final java.util.HashMap<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> mPhysicalCameraSettings;
    private int mReprocessableSessionId;
    private int mRequestType;
    private int[] mStreamIdxArray;
    private boolean mSurfaceConverted;
    private int[] mSurfaceIdxArray;
    private final android.util.ArraySet<android.view.Surface> mSurfaceSet;
    private final java.lang.Object mSurfacesLock;
    private java.lang.Object mUserTag;
    private static final android.util.ArraySet<android.view.Surface> mEmptySurfaceSet = new android.util.ArraySet<>();
    public static final android.os.Parcelable.Creator<android.hardware.camera2.CaptureRequest> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.CaptureRequest>() { // from class: android.hardware.camera2.CaptureRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.CaptureRequest createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.CaptureRequest captureRequest = new android.hardware.camera2.CaptureRequest();
            captureRequest.readFromParcel(parcel);
            return captureRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.CaptureRequest[] newArray(int i) {
            return new android.hardware.camera2.CaptureRequest[i];
        }
    };

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> COLOR_CORRECTION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.colorCorrection.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.ColorSpaceTransform> COLOR_CORRECTION_TRANSFORM = new android.hardware.camera2.CaptureRequest.Key<>("android.colorCorrection.transform", android.hardware.camera2.params.ColorSpaceTransform.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.RggbChannelVector> COLOR_CORRECTION_GAINS = new android.hardware.camera2.CaptureRequest.Key<>("android.colorCorrection.gains", android.hardware.camera2.params.RggbChannelVector.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> COLOR_CORRECTION_ABERRATION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.colorCorrection.aberrationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AE_ANTIBANDING_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeAntibandingMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AE_EXPOSURE_COMPENSATION = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeExposureCompensation", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_AE_LOCK = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeLock", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AE_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AE_REGIONS = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.util.Range<java.lang.Integer>> CONTROL_AE_TARGET_FPS_RANGE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeTargetFpsRange", new android.hardware.camera2.utils.TypeReference<android.util.Range<java.lang.Integer>>() { // from class: android.hardware.camera2.CaptureRequest.2
    });

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AE_PRECAPTURE_TRIGGER = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aePrecaptureTrigger", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AF_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.afMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AF_REGIONS = new android.hardware.camera2.CaptureRequest.Key<>("android.control.afRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AF_TRIGGER = new android.hardware.camera2.CaptureRequest.Key<>("android.control.afTrigger", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_AWB_LOCK = new android.hardware.camera2.CaptureRequest.Key<>("android.control.awbLock", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AWB_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.awbMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]> CONTROL_AWB_REGIONS = new android.hardware.camera2.CaptureRequest.Key<>("android.control.awbRegions", android.hardware.camera2.params.MeteringRectangle[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_CAPTURE_INTENT = new android.hardware.camera2.CaptureRequest.Key<>("android.control.captureIntent", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_EFFECT_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.effectMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_SCENE_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.sceneMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_VIDEO_STABILIZATION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.videoStabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_POST_RAW_SENSITIVITY_BOOST = new android.hardware.camera2.CaptureRequest.Key<>("android.control.postRawSensitivityBoost", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_ENABLE_ZSL = new android.hardware.camera2.CaptureRequest.Key<>("android.control.enableZsl", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_EXTENDED_SCENE_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.extendedSceneMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> CONTROL_ZOOM_RATIO = new android.hardware.camera2.CaptureRequest.Key<>("android.control.zoomRatio", java.lang.Float.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_AF_REGIONS_SET = new android.hardware.camera2.CaptureRequest.Key<>("android.control.afRegionsSet", java.lang.Boolean.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_AE_REGIONS_SET = new android.hardware.camera2.CaptureRequest.Key<>("android.control.aeRegionsSet", java.lang.Boolean.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> CONTROL_AWB_REGIONS_SET = new android.hardware.camera2.CaptureRequest.Key<>("android.control.awbRegionsSet", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_SETTINGS_OVERRIDE = new android.hardware.camera2.CaptureRequest.Key<>("android.control.settingsOverride", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> CONTROL_AUTOFRAMING = new android.hardware.camera2.CaptureRequest.Key<>("android.control.autoframing", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> EDGE_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.edge.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> FLASH_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.flash.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> FLASH_STRENGTH_LEVEL = new android.hardware.camera2.CaptureRequest.Key<>("android.flash.strengthLevel", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> HOT_PIXEL_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.hotPixel.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.location.Location> JPEG_GPS_LOCATION = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.gpsLocation", android.location.Location.class);
    public static final android.hardware.camera2.CaptureRequest.Key<double[]> JPEG_GPS_COORDINATES = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.gpsCoordinates", double[].class);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.String> JPEG_GPS_PROCESSING_METHOD = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.gpsProcessingMethod", java.lang.String.class);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Long> JPEG_GPS_TIMESTAMP = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.gpsTimestamp", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> JPEG_ORIENTATION = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.orientation", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Byte> JPEG_QUALITY = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.quality", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Byte> JPEG_THUMBNAIL_QUALITY = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.thumbnailQuality", java.lang.Byte.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.util.Size> JPEG_THUMBNAIL_SIZE = new android.hardware.camera2.CaptureRequest.Key<>("android.jpeg.thumbnailSize", android.util.Size.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> LENS_APERTURE = new android.hardware.camera2.CaptureRequest.Key<>("android.lens.aperture", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> LENS_FILTER_DENSITY = new android.hardware.camera2.CaptureRequest.Key<>("android.lens.filterDensity", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> LENS_FOCAL_LENGTH = new android.hardware.camera2.CaptureRequest.Key<>("android.lens.focalLength", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> LENS_FOCUS_DISTANCE = new android.hardware.camera2.CaptureRequest.Key<>("android.lens.focusDistance", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> LENS_OPTICAL_STABILIZATION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.lens.opticalStabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> NOISE_REDUCTION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.noiseReduction.mode", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> REQUEST_ID = new android.hardware.camera2.CaptureRequest.Key<>(android.content.RestrictionsManager.REQUEST_KEY_ID, java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.graphics.Rect> SCALER_CROP_REGION = new android.hardware.camera2.CaptureRequest.Key<>("android.scaler.cropRegion", android.graphics.Rect.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> SCALER_ROTATE_AND_CROP = new android.hardware.camera2.CaptureRequest.Key<>("android.scaler.rotateAndCrop", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> SCALER_CROP_REGION_SET = new android.hardware.camera2.CaptureRequest.Key<>("android.scaler.cropRegionSet", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Long> SENSOR_EXPOSURE_TIME = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.exposureTime", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Long> SENSOR_FRAME_DURATION = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.frameDuration", java.lang.Long.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> SENSOR_SENSITIVITY = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.sensitivity", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<int[]> SENSOR_TEST_PATTERN_DATA = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.testPatternData", int[].class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> SENSOR_TEST_PATTERN_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.testPatternMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> SENSOR_PIXEL_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.sensor.pixelMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> SHADING_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.shading.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> STATISTICS_FACE_DETECT_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.statistics.faceDetectMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> STATISTICS_HOT_PIXEL_MAP_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.statistics.hotPixelMapMode", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> STATISTICS_LENS_SHADING_MAP_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.statistics.lensShadingMapMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> STATISTICS_OIS_DATA_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.statistics.oisDataMode", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<float[]> TONEMAP_CURVE_BLUE = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.curveBlue", float[].class);
    public static final android.hardware.camera2.CaptureRequest.Key<float[]> TONEMAP_CURVE_GREEN = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.curveGreen", float[].class);
    public static final android.hardware.camera2.CaptureRequest.Key<float[]> TONEMAP_CURVE_RED = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.curveRed", float[].class);

    @android.hardware.camera2.impl.SyntheticKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.TonemapCurve> TONEMAP_CURVE = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.curve", android.hardware.camera2.params.TonemapCurve.class);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> TONEMAP_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> TONEMAP_GAMMA = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.gamma", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> TONEMAP_PRESET_CURVE = new android.hardware.camera2.CaptureRequest.Key<>("android.tonemap.presetCurve", java.lang.Integer.TYPE);
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> LED_TRANSMIT = new android.hardware.camera2.CaptureRequest.Key<>("android.led.transmit", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> BLACK_LEVEL_LOCK = new android.hardware.camera2.CaptureRequest.Key<>("android.blackLevel.lock", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = new android.hardware.camera2.CaptureRequest.Key<>("android.reprocess.effectiveExposureFactor", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> DISTORTION_CORRECTION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.distortionCorrection.mode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> EXTENSION_STRENGTH = new android.hardware.camera2.CaptureRequest.Key<>("android.extension.strength", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_PADDING_ZOOM_FACTOR = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.paddingZoomFactor", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean> EFV_AUTO_ZOOM = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.autoZoom", java.lang.Boolean.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_MAX_PADDING_ZOOM_FACTOR = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.maxPaddingZoomFactor", java.lang.Float.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Integer> EFV_STABILIZATION_MODE = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.stabilizationMode", java.lang.Integer.TYPE);

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<android.util.Pair<java.lang.Integer, java.lang.Integer>> EFV_TRANSLATE_VIEWPORT = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.translateViewport", new android.hardware.camera2.utils.TypeReference<android.util.Pair<java.lang.Integer, java.lang.Integer>>() { // from class: android.hardware.camera2.CaptureRequest.3
    });

    @android.hardware.camera2.impl.ExtensionKey
    public static final android.hardware.camera2.CaptureRequest.Key<java.lang.Float> EFV_ROTATE_VIEWPORT = new android.hardware.camera2.CaptureRequest.Key<>("android.efv.rotateViewport", java.lang.Float.TYPE);

    public static final class Key<T> {
        private final android.hardware.camera2.impl.CameraMetadataNative.Key<T> mKey;

        public Key(java.lang.String str, java.lang.Class<T> cls, long j) {
            this.mKey = new android.hardware.camera2.impl.CameraMetadataNative.Key<>(str, cls, j);
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
            return (obj instanceof android.hardware.camera2.CaptureRequest.Key) && ((android.hardware.camera2.CaptureRequest.Key) obj).mKey.equals(this.mKey);
        }

        public java.lang.String toString() {
            return java.lang.String.format("CaptureRequest.Key(%s)", this.mKey.getName());
        }

        public android.hardware.camera2.impl.CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        /* JADX WARN: Multi-variable type inference failed */
        Key(android.hardware.camera2.impl.CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }
    }

    public int getRequestType() {
        if (this.mRequestType == -1) {
            if (this.mIsReprocess) {
                this.mRequestType = 1;
            } else {
                java.lang.Boolean bool = (java.lang.Boolean) this.mLogicalCameraSettings.get(CONTROL_ENABLE_ZSL);
                this.mRequestType = bool != null && bool.booleanValue() && ((java.lang.Integer) this.mLogicalCameraSettings.get(CONTROL_CAPTURE_INTENT)).intValue() == 2 ? 2 : 0;
            }
        }
        return this.mRequestType;
    }

    private CaptureRequest() {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new android.util.ArraySet<>();
        this.mSurfacesLock = new java.lang.Object();
        this.mSurfaceConverted = false;
        this.mPhysicalCameraSettings = new java.util.HashMap<>();
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        this.mIsReprocess = false;
        this.mReprocessableSessionId = -1;
    }

    private CaptureRequest(android.hardware.camera2.CaptureRequest captureRequest) {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new android.util.ArraySet<>();
        this.mSurfacesLock = new java.lang.Object();
        this.mSurfaceConverted = false;
        this.mPhysicalCameraSettings = new java.util.HashMap<>();
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        this.mLogicalCameraId = new java.lang.String(captureRequest.mLogicalCameraId);
        for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> entry : captureRequest.mPhysicalCameraSettings.entrySet()) {
            this.mPhysicalCameraSettings.put(new java.lang.String(entry.getKey()), new android.hardware.camera2.impl.CameraMetadataNative(entry.getValue()));
        }
        this.mLogicalCameraSettings = this.mPhysicalCameraSettings.get(this.mLogicalCameraId);
        setNativeInstance(this.mLogicalCameraSettings);
        this.mSurfaceSet.addAll((android.util.ArraySet<? extends android.view.Surface>) captureRequest.mSurfaceSet);
        this.mIsReprocess = captureRequest.mIsReprocess;
        this.mIsPartOfCHSRequestList = captureRequest.mIsPartOfCHSRequestList;
        this.mReprocessableSessionId = captureRequest.mReprocessableSessionId;
        this.mUserTag = captureRequest.mUserTag;
    }

    private CaptureRequest(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, boolean z, int i, java.lang.String str, java.util.Set<java.lang.String> set) {
        this.TAG = "CaptureRequest-JV";
        this.mSurfaceSet = new android.util.ArraySet<>();
        this.mSurfacesLock = new java.lang.Object();
        this.mSurfaceConverted = false;
        this.mPhysicalCameraSettings = new java.util.HashMap<>();
        this.mRequestType = -1;
        this.mIsPartOfCHSRequestList = false;
        if (set != null && z) {
            throw new java.lang.IllegalArgumentException("Create a reprocess capture request with with more than one physical camera is not supported!");
        }
        this.mLogicalCameraId = str;
        this.mLogicalCameraSettings = android.hardware.camera2.impl.CameraMetadataNative.move(cameraMetadataNative);
        this.mPhysicalCameraSettings.put(this.mLogicalCameraId, this.mLogicalCameraSettings);
        if (set != null) {
            java.util.Iterator<java.lang.String> it = set.iterator();
            while (it.hasNext()) {
                this.mPhysicalCameraSettings.put(it.next(), new android.hardware.camera2.impl.CameraMetadataNative(this.mLogicalCameraSettings));
            }
        }
        setNativeInstance(this.mLogicalCameraSettings);
        this.mIsReprocess = z;
        if (z) {
            if (i == -1) {
                throw new java.lang.IllegalArgumentException("Create a reprocess capture request with an invalid session ID: " + i);
            }
            this.mReprocessableSessionId = i;
            return;
        }
        this.mReprocessableSessionId = -1;
    }

    public <T> T get(android.hardware.camera2.CaptureRequest.Key<T> key) {
        return (T) this.mLogicalCameraSettings.get(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.hardware.camera2.CameraMetadata
    public <T> T getProtected(android.hardware.camera2.CaptureRequest.Key<?> key) {
        return (T) this.mLogicalCameraSettings.get(key);
    }

    @Override // android.hardware.camera2.CameraMetadata
    protected java.lang.Class<android.hardware.camera2.CaptureRequest.Key<?>> getKeyClass() {
        return android.hardware.camera2.CaptureRequest.Key.class;
    }

    @Override // android.hardware.camera2.CameraMetadata
    public java.util.List<android.hardware.camera2.CaptureRequest.Key<?>> getKeys() {
        return super.getKeys();
    }

    public java.lang.Object getTag() {
        return this.mUserTag;
    }

    public boolean isReprocess() {
        return this.mIsReprocess;
    }

    public boolean isPartOfCRequestList() {
        return this.mIsPartOfCHSRequestList;
    }

    public android.hardware.camera2.impl.CameraMetadataNative getNativeCopy() {
        return new android.hardware.camera2.impl.CameraMetadataNative(this.mLogicalCameraSettings);
    }

    public int getReprocessableSessionId() {
        if (!this.mIsReprocess || this.mReprocessableSessionId == -1) {
            throw new java.lang.IllegalStateException("Getting the reprocessable session ID for a non-reprocess capture request is illegal.");
        }
        return this.mReprocessableSessionId;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.hardware.camera2.CaptureRequest) && equals((android.hardware.camera2.CaptureRequest) obj);
    }

    private boolean equals(android.hardware.camera2.CaptureRequest captureRequest) {
        return captureRequest != null && java.util.Objects.equals(this.mUserTag, captureRequest.mUserTag) && this.mSurfaceSet.equals(captureRequest.mSurfaceSet) && this.mPhysicalCameraSettings.equals(captureRequest.mPhysicalCameraSettings) && this.mLogicalCameraId.equals(captureRequest.mLogicalCameraId) && this.mLogicalCameraSettings.equals(captureRequest.mLogicalCameraSettings) && this.mIsReprocess == captureRequest.mIsReprocess && this.mReprocessableSessionId == captureRequest.mReprocessableSessionId;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCodeGeneric(this.mPhysicalCameraSettings, this.mSurfaceSet, this.mUserTag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            throw new java.lang.RuntimeException("Physical camera count" + readInt + " should always be positive");
        }
        this.mLogicalCameraId = parcel.readString();
        this.mLogicalCameraSettings = new android.hardware.camera2.impl.CameraMetadataNative();
        this.mLogicalCameraSettings.readFromParcel(parcel);
        setNativeInstance(this.mLogicalCameraSettings);
        this.mPhysicalCameraSettings.put(this.mLogicalCameraId, this.mLogicalCameraSettings);
        for (int i = 1; i < readInt; i++) {
            java.lang.String readString = parcel.readString();
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
            cameraMetadataNative.readFromParcel(parcel);
            this.mPhysicalCameraSettings.put(readString, cameraMetadataNative);
        }
        this.mIsReprocess = parcel.readInt() != 0;
        this.mReprocessableSessionId = -1;
        synchronized (this.mSurfacesLock) {
            this.mSurfaceSet.clear();
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(android.view.Surface.class.getClassLoader(), android.view.Surface.class);
            if (parcelableArr != null) {
                for (android.os.Parcelable parcelable : parcelableArr) {
                    this.mSurfaceSet.add((android.view.Surface) parcelable);
                }
            }
            if (parcel.readInt() != 0) {
                throw new java.lang.RuntimeException("Reading cached CaptureRequest is not supported");
            }
        }
        if (parcel.readInt() == 1) {
            this.mUserTag = parcel.readString();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (!this.mPhysicalCameraSettings.containsKey(this.mLogicalCameraId)) {
            throw new java.lang.IllegalStateException("Physical camera settings map must contain a key for the logical camera id.");
        }
        parcel.writeInt(this.mPhysicalCameraSettings.size());
        parcel.writeString(this.mLogicalCameraId);
        this.mLogicalCameraSettings.writeToParcel(parcel, i);
        for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> entry : this.mPhysicalCameraSettings.entrySet()) {
            if (!entry.getKey().equals(this.mLogicalCameraId)) {
                parcel.writeString(entry.getKey());
                entry.getValue().writeToParcel(parcel, i);
            }
        }
        parcel.writeInt(this.mIsReprocess ? 1 : 0);
        synchronized (this.mSurfacesLock) {
            android.util.ArraySet<android.view.Surface> arraySet = this.mSurfaceConverted ? mEmptySurfaceSet : this.mSurfaceSet;
            parcel.writeParcelableArray((android.view.Surface[]) arraySet.toArray(new android.view.Surface[arraySet.size()]), i);
            if (this.mSurfaceConverted) {
                parcel.writeInt(this.mStreamIdxArray.length);
                for (int i2 = 0; i2 < this.mStreamIdxArray.length; i2++) {
                    parcel.writeInt(this.mStreamIdxArray[i2]);
                    parcel.writeInt(this.mSurfaceIdxArray[i2]);
                }
            } else {
                parcel.writeInt(0);
            }
        }
        if (this.mUserTag != null) {
            java.lang.String obj = this.mUserTag.toString();
            if (obj != null && obj.startsWith(SET_TAG_STRING_PREFIX)) {
                parcel.writeInt(1);
                parcel.writeString(obj.substring(SET_TAG_STRING_PREFIX.length()));
                return;
            } else {
                parcel.writeInt(0);
                return;
            }
        }
        parcel.writeInt(0);
    }

    public boolean containsTarget(android.view.Surface surface) {
        return this.mSurfaceSet.contains(surface);
    }

    public java.util.Collection<android.view.Surface> getTargets() {
        return java.util.Collections.unmodifiableCollection(this.mSurfaceSet);
    }

    public java.lang.String getLogicalCameraId() {
        return this.mLogicalCameraId;
    }

    public void convertSurfaceToStreamId(android.util.SparseArray<android.hardware.camera2.params.OutputConfiguration> sparseArray) {
        synchronized (this.mSurfacesLock) {
            if (this.mSurfaceConverted) {
                android.util.Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = new int[this.mSurfaceSet.size()];
            this.mSurfaceIdxArray = new int[this.mSurfaceSet.size()];
            java.util.Iterator<android.view.Surface> it = this.mSurfaceSet.iterator();
            int i = 0;
            while (it.hasNext()) {
                android.view.Surface next = it.next();
                boolean z = false;
                for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                    int keyAt = sparseArray.keyAt(i2);
                    java.util.Iterator<android.view.Surface> it2 = sparseArray.valueAt(i2).getSurfaces().iterator();
                    int i3 = 0;
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (next == it2.next()) {
                            this.mStreamIdxArray[i] = keyAt;
                            this.mSurfaceIdxArray[i] = i3;
                            i++;
                            z = true;
                            break;
                        }
                        i3++;
                    }
                    if (z) {
                        break;
                    }
                }
                if (!z) {
                    long surfaceId = android.hardware.camera2.utils.SurfaceUtils.getSurfaceId(next);
                    for (int i4 = 0; i4 < sparseArray.size(); i4++) {
                        int keyAt2 = sparseArray.keyAt(i4);
                        java.util.Iterator<android.view.Surface> it3 = sparseArray.valueAt(i4).getSurfaces().iterator();
                        int i5 = 0;
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            if (surfaceId == android.hardware.camera2.utils.SurfaceUtils.getSurfaceId(it3.next())) {
                                this.mStreamIdxArray[i] = keyAt2;
                                this.mSurfaceIdxArray[i] = i5;
                                i++;
                                z = true;
                                break;
                            }
                            i5++;
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (!z) {
                    this.mStreamIdxArray = null;
                    this.mSurfaceIdxArray = null;
                    throw new java.lang.IllegalArgumentException("CaptureRequest contains unconfigured Input/Output Surface!");
                }
            }
            this.mSurfaceConverted = true;
        }
    }

    public void recoverStreamIdToSurface() {
        synchronized (this.mSurfacesLock) {
            if (!this.mSurfaceConverted) {
                android.util.Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = null;
            this.mSurfaceIdxArray = null;
            this.mSurfaceConverted = false;
        }
    }

    public static final class Builder {
        private final android.hardware.camera2.CaptureRequest mRequest;

        public Builder(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, boolean z, int i, java.lang.String str, java.util.Set<java.lang.String> set) {
            this.mRequest = new android.hardware.camera2.CaptureRequest(cameraMetadataNative, z, i, str, set);
        }

        public void addTarget(android.view.Surface surface) {
            this.mRequest.mSurfaceSet.add(surface);
        }

        public void removeTarget(android.view.Surface surface) {
            this.mRequest.mSurfaceSet.remove(surface);
        }

        public <T> void set(android.hardware.camera2.CaptureRequest.Key<T> key, T t) {
            this.mRequest.mLogicalCameraSettings.set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<T>>) key, (android.hardware.camera2.CaptureRequest.Key<T>) t);
        }

        public <T> T get(android.hardware.camera2.CaptureRequest.Key<T> key) {
            return (T) this.mRequest.mLogicalCameraSettings.get(key);
        }

        public <T> android.hardware.camera2.CaptureRequest.Builder setPhysicalCameraKey(android.hardware.camera2.CaptureRequest.Key<T> key, T t, java.lang.String str) {
            if (!this.mRequest.mPhysicalCameraSettings.containsKey(str)) {
                throw new java.lang.IllegalArgumentException("Physical camera id: " + str + " is not valid!");
            }
            ((android.hardware.camera2.impl.CameraMetadataNative) this.mRequest.mPhysicalCameraSettings.get(str)).set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<T>>) key, (android.hardware.camera2.CaptureRequest.Key<T>) t);
            return this;
        }

        public <T> T getPhysicalCameraKey(android.hardware.camera2.CaptureRequest.Key<T> key, java.lang.String str) {
            if (!this.mRequest.mPhysicalCameraSettings.containsKey(str)) {
                throw new java.lang.IllegalArgumentException("Physical camera id: " + str + " is not valid!");
            }
            return (T) ((android.hardware.camera2.impl.CameraMetadataNative) this.mRequest.mPhysicalCameraSettings.get(str)).get(key);
        }

        public void setTag(java.lang.Object obj) {
            this.mRequest.mUserTag = obj;
        }

        public void setPartOfCHSRequestList(boolean z) {
            this.mRequest.mIsPartOfCHSRequestList = z;
        }

        public android.hardware.camera2.CaptureRequest build() {
            return new android.hardware.camera2.CaptureRequest();
        }

        public boolean isEmpty() {
            return this.mRequest.mLogicalCameraSettings.isEmpty();
        }
    }
}
