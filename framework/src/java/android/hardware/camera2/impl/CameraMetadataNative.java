package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraMetadataNative implements android.os.Parcelable {
    private static final java.lang.String CELLID_PROCESS = "CELLID";
    private static final boolean DEBUG = false;
    private static final int FACE_LANDMARK_SIZE = 6;
    private static final java.lang.String GPS_PROCESS = "GPS";
    private static final int MANDATORY_STREAM_CONFIGURATIONS_10BIT = 3;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_CONCURRENT = 2;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_DEFAULT = 0;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_MAX_RESOLUTION = 1;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_PREVIEW_STABILIZATION = 5;
    private static final int MANDATORY_STREAM_CONFIGURATIONS_USE_CASE = 4;
    public static final int NATIVE_JPEG_FORMAT = 33;
    public static final int NUM_TYPES = 6;
    private static final java.lang.String TAG = "CameraMetadataJV";
    public static final int TYPE_BYTE = 0;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_INT32 = 1;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_RATIONAL = 5;
    private static final java.util.HashMap<android.hardware.camera2.impl.CameraMetadataNative.Key<?>, android.hardware.camera2.impl.SetCommand> sSetCommandMap;
    private long mBufferSize;
    private int mCameraId;
    private android.util.Size mDisplaySize;
    private boolean mHasMandatoryConcurrentStreams;
    private long mMetadataPtr;
    private android.hardware.camera2.params.MultiResolutionStreamConfigurationMap mMultiResolutionStreamConfigurationMap;
    public static final android.os.Parcelable.Creator<android.hardware.camera2.impl.CameraMetadataNative> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.impl.CameraMetadataNative>() { // from class: android.hardware.camera2.impl.CameraMetadataNative.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.CameraMetadataNative createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = new android.hardware.camera2.impl.CameraMetadataNative();
            cameraMetadataNative.readFromParcel(parcel);
            return cameraMetadataNative;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.impl.CameraMetadataNative[] newArray(int i) {
            return new android.hardware.camera2.impl.CameraMetadataNative[i];
        }
    };
    private static final java.util.HashMap<android.hardware.camera2.impl.CameraMetadataNative.Key<?>, android.hardware.camera2.impl.GetCommand> sGetCommandMap = new java.util.HashMap<>();

    @dalvik.annotation.optimization.FastNative
    private static native long nativeAllocate();

    @dalvik.annotation.optimization.FastNative
    private static native long nativeAllocateCopy(long j) throws java.lang.NullPointerException;

    private static native synchronized void nativeClose(long j);

    private static native synchronized void nativeDump(long j) throws java.io.IOException;

    private static native synchronized java.util.ArrayList nativeGetAllVendorKeys(long j, java.lang.Class cls);

    private static native synchronized long nativeGetBufferSize(long j);

    private static native synchronized int nativeGetEntryCount(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetTagFromKey(java.lang.String str, long j) throws java.lang.IllegalArgumentException;

    private static native synchronized int nativeGetTagFromKeyLocal(long j, java.lang.String str) throws java.lang.IllegalArgumentException;

    @dalvik.annotation.optimization.FastNative
    private static native int nativeGetTypeFromTag(int i, long j) throws java.lang.IllegalArgumentException;

    private static native synchronized int nativeGetTypeFromTagLocal(long j, int i) throws java.lang.IllegalArgumentException;

    private static native synchronized boolean nativeIsEmpty(long j);

    private static native synchronized void nativeReadFromParcel(android.os.Parcel parcel, long j);

    private static native synchronized byte[] nativeReadValues(int i, long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeSetVendorId(long j, long j2);

    private static native int nativeSetupGlobalVendorTagDescriptor();

    private static native synchronized void nativeSwap(long j, long j2) throws java.lang.NullPointerException;

    @dalvik.annotation.optimization.FastNative
    private static native void nativeUpdate(long j, long j2);

    private static native synchronized void nativeWriteToParcel(android.os.Parcel parcel, long j);

    private static native synchronized void nativeWriteValues(int i, byte[] bArr, long j);

    public static class Key<T> {
        private final java.lang.String mFallbackName;
        private boolean mHasTag;
        private final int mHash;
        private final java.lang.String mName;
        private int mTag;
        private final java.lang.Class<T> mType;
        private final android.hardware.camera2.utils.TypeReference<T> mTypeReference;
        private long mVendorId;

        public Key(java.lang.String str, java.lang.Class<T> cls, long j) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new java.lang.NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new java.lang.NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = cls;
            this.mVendorId = j;
            this.mTypeReference = android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference((java.lang.Class) cls);
            this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
        }

        public Key(java.lang.String str, java.lang.String str2, java.lang.Class<T> cls) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new java.lang.NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new java.lang.NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = str2;
            this.mType = cls;
            this.mTypeReference = android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference((java.lang.Class) cls);
            this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
        }

        public Key(java.lang.String str, java.lang.Class<T> cls) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new java.lang.NullPointerException("Key needs a valid name");
            }
            if (cls == null) {
                throw new java.lang.NullPointerException("Type needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = cls;
            this.mTypeReference = android.hardware.camera2.utils.TypeReference.createSpecializedTypeReference((java.lang.Class) cls);
            this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
        }

        public Key(java.lang.String str, android.hardware.camera2.utils.TypeReference<T> typeReference) {
            this.mVendorId = Long.MAX_VALUE;
            if (str == null) {
                throw new java.lang.NullPointerException("Key needs a valid name");
            }
            if (typeReference == null) {
                throw new java.lang.NullPointerException("TypeReference needs to be non-null");
            }
            this.mName = str;
            this.mFallbackName = null;
            this.mType = typeReference.getRawType();
            this.mTypeReference = typeReference;
            this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
        }

        public final java.lang.String getName() {
            return this.mName;
        }

        public final int hashCode() {
            return this.mHash;
        }

        public final boolean equals(java.lang.Object obj) {
            android.hardware.camera2.impl.CameraMetadataNative.Key<T> key;
            if (this == obj) {
                return true;
            }
            if (obj == null || hashCode() != obj.hashCode()) {
                return false;
            }
            if (obj instanceof android.hardware.camera2.CaptureResult.Key) {
                key = ((android.hardware.camera2.CaptureResult.Key) obj).getNativeKey();
            } else if (obj instanceof android.hardware.camera2.CaptureRequest.Key) {
                key = ((android.hardware.camera2.CaptureRequest.Key) obj).getNativeKey();
            } else if (obj instanceof android.hardware.camera2.CameraCharacteristics.Key) {
                key = ((android.hardware.camera2.CameraCharacteristics.Key) obj).getNativeKey();
            } else {
                if (!(obj instanceof android.hardware.camera2.impl.CameraMetadataNative.Key)) {
                    return false;
                }
                key = (android.hardware.camera2.impl.CameraMetadataNative.Key) obj;
            }
            if (this.mName.equals(key.mName) && this.mTypeReference.equals(key.mTypeReference)) {
                return true;
            }
            return false;
        }

        public final int getTag() {
            if (!this.mHasTag) {
                this.mTag = android.hardware.camera2.impl.CameraMetadataNative.getTag(this.mName, this.mVendorId);
                this.mHasTag = true;
            }
            return this.mTag;
        }

        public final boolean hasTag() {
            return this.mHasTag;
        }

        public final void cacheTag(int i) {
            this.mHasTag = true;
            this.mTag = i;
        }

        public final java.lang.Class<T> getType() {
            return this.mType;
        }

        public final long getVendorId() {
            return this.mVendorId;
        }

        public final android.hardware.camera2.utils.TypeReference<T> getTypeReference() {
            return this.mTypeReference;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String translateLocationProviderToProcess(java.lang.String str) {
        char c;
        if (str == null) {
            return null;
        }
        switch (str.hashCode()) {
            case 102570:
                if (str.equals("gps")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1843485230:
                if (str.equals("network")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String translateProcessToLocationProvider(java.lang.String str) {
        char c;
        if (str == null) {
            return null;
        }
        switch (str.hashCode()) {
            case 70794:
                if (str.equals(GPS_PROCESS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1984215549:
                if (str.equals(CELLID_PROCESS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return null;
    }

    public CameraMetadataNative() {
        this.mCameraId = -1;
        this.mHasMandatoryConcurrentStreams = false;
        this.mDisplaySize = new android.util.Size(0, 0);
        this.mBufferSize = 0L;
        this.mMultiResolutionStreamConfigurationMap = null;
        this.mMetadataPtr = nativeAllocate();
        if (this.mMetadataPtr == 0) {
            throw new java.lang.OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
        updateNativeAllocation();
    }

    public CameraMetadataNative(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
        this.mCameraId = -1;
        this.mHasMandatoryConcurrentStreams = false;
        this.mDisplaySize = new android.util.Size(0, 0);
        this.mBufferSize = 0L;
        this.mMultiResolutionStreamConfigurationMap = null;
        this.mMetadataPtr = nativeAllocateCopy(cameraMetadataNative.mMetadataPtr);
        if (this.mMetadataPtr == 0) {
            throw new java.lang.OutOfMemoryError("Failed to allocate native CameraMetadata");
        }
        updateNativeAllocation();
    }

    public static android.hardware.camera2.impl.CameraMetadataNative move(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
        android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2 = new android.hardware.camera2.impl.CameraMetadataNative();
        cameraMetadataNative2.swap(cameraMetadataNative);
        return cameraMetadataNative2;
    }

    public static void update(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2) {
        nativeUpdate(cameraMetadataNative.mMetadataPtr, cameraMetadataNative2.mMetadataPtr);
    }

    static {
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getAvailableFormats();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_FACES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getFaces();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getFaceRectangles();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.5
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getStreamConfigurationMap();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.6
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getStreamConfigurationMapMaximumResolution();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_CONCURRENT_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.8
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryConcurrentStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_TEN_BIT_OUTPUT_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.9
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatory10BitStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_MAXIMUM_RESOLUTION_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.10
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryMaximumResolutionStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_USE_CASE_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.11
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryUseCaseStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MANDATORY_PREVIEW_STABILIZATION_OUTPUT_STREAM_COMBINATIONS.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.12
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMandatoryPreviewStabilizationStreamCombinations();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AE.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.13
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AWB.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.14
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AF.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.15
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.16
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.17
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.18
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureRequest.TONEMAP_CURVE.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.19
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getTonemapCurve();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.20
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getGpsLocation();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.21
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getLensShadingMap();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.INFO_DEVICE_STATE_SENSOR_ORIENTATION_MAP.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.22
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getDeviceStateOrientationMap();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.23
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getDynamicRangeProfiles();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.24
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getColorSpaceProfiles();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_OIS_SAMPLES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.25
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getOisSamples();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_CAPABILITIES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.26
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getExtendedSceneModeCapabilities();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.27
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getMultiResolutionStreamConfigurationMap();
            }
        });
        sGetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSICS_SAMPLES.getNativeKey(), new android.hardware.camera2.impl.GetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.28
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.GetCommand
            public <T> T getValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
                return (T) cameraMetadataNative.getLensIntrinsicSamples();
            }
        });
        sSetCommandMap = new java.util.HashMap<>();
        sSetCommandMap.put(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.29
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAvailableFormats((int[]) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.30
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaceRectangles((android.graphics.Rect[]) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_FACES.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.31
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaces((android.hardware.camera2.params.Face[]) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureRequest.TONEMAP_CURVE.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.32
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setTonemapCurve((android.hardware.camera2.params.TonemapCurve) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.33
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setGpsLocation((android.location.Location) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureRequest.SCALER_CROP_REGION.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.34
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setScalerCropRegion((android.graphics.Rect) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureRequest.CONTROL_AWB_REGIONS.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.35
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAWBRegions(t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureRequest.CONTROL_AF_REGIONS.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.36
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAFRegions(t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureRequest.CONTROL_AE_REGIONS.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.37
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAERegions(t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.38
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setLensShadingMap((android.hardware.camera2.params.LensShadingMap) t);
            }
        });
        sSetCommandMap.put(android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSICS_SAMPLES.getNativeKey(), new android.hardware.camera2.impl.SetCommand() { // from class: android.hardware.camera2.impl.CameraMetadataNative.39
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.SetCommand
            public <T> void setValue(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setLensIntrinsicsSamples((android.hardware.camera2.params.LensIntrinsicsSample[]) t);
            }
        });
        registerAllMarshalers();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        nativeWriteToParcel(parcel, this.mMetadataPtr);
    }

    public <T> T get(android.hardware.camera2.CameraCharacteristics.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(android.hardware.camera2.CaptureResult.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(android.hardware.camera2.CaptureRequest.Key<T> key) {
        return (T) get(key.getNativeKey());
    }

    public <T> T get(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
        java.util.Objects.requireNonNull(key, "key must not be null");
        android.hardware.camera2.impl.GetCommand getCommand = sGetCommandMap.get(key);
        if (getCommand != null) {
            return (T) getCommand.getValue(this, key);
        }
        return (T) getBase(key);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        nativeReadFromParcel(parcel, this.mMetadataPtr);
        updateNativeAllocation();
    }

    public static void setupGlobalVendorTagDescriptor() throws android.os.ServiceSpecificException {
        int nativeSetupGlobalVendorTagDescriptor = nativeSetupGlobalVendorTagDescriptor();
        if (nativeSetupGlobalVendorTagDescriptor != 0) {
            throw new android.os.ServiceSpecificException(nativeSetupGlobalVendorTagDescriptor, "Failure to set up global vendor tags");
        }
    }

    public <T> void set(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key, T t) {
        android.hardware.camera2.impl.SetCommand setCommand = sSetCommandMap.get(key);
        if (setCommand != null) {
            setCommand.setValue(this, t);
        } else {
            setBase((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key, (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
        }
    }

    public <T> void set(android.hardware.camera2.CaptureRequest.Key<T> key, T t) {
        set((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    public <T> void set(android.hardware.camera2.CaptureResult.Key<T> key, T t) {
        set((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    public <T> void set(android.hardware.camera2.CameraCharacteristics.Key<T> key, T t) {
        set((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    private void close() {
        nativeClose(this.mMetadataPtr);
        this.mMetadataPtr = 0L;
        if (this.mBufferSize > 0) {
            dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mBufferSize);
        }
        this.mBufferSize = 0L;
    }

    private <T> T getBase(android.hardware.camera2.CameraCharacteristics.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(android.hardware.camera2.CaptureResult.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(android.hardware.camera2.CaptureRequest.Key<T> key) {
        return (T) getBase(key.getNativeKey());
    }

    private <T> T getBase(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
        int nativeGetTagFromKeyLocal;
        byte[] readValues;
        if (key.hasTag()) {
            nativeGetTagFromKeyLocal = key.getTag();
        } else {
            nativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, key.getName());
            key.cacheTag(nativeGetTagFromKeyLocal);
        }
        byte[] readValues2 = readValues(nativeGetTagFromKeyLocal);
        if (readValues2 == null) {
            if (((android.hardware.camera2.impl.CameraMetadataNative.Key) key).mFallbackName == null || (readValues = readValues((nativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, ((android.hardware.camera2.impl.CameraMetadataNative.Key) key).mFallbackName)))) == null) {
                return null;
            }
            readValues2 = readValues;
        }
        return (T) getMarshalerForKey(key, nativeGetTypeFromTagLocal(this.mMetadataPtr, nativeGetTagFromKeyLocal)).unmarshal(java.nio.ByteBuffer.wrap(readValues2).order(java.nio.ByteOrder.nativeOrder()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getAvailableFormats() {
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_FORMATS);
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                if (iArr[i] == 33) {
                    iArr[i] = 256;
                }
            }
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setFaces(android.hardware.camera2.params.Face[] faceArr) {
        int[] iArr;
        int[] iArr2;
        if (faceArr == null) {
            return false;
        }
        int length = faceArr.length;
        boolean z = true;
        for (android.hardware.camera2.params.Face face : faceArr) {
            if (face == null) {
                length--;
                android.util.Log.w(TAG, "setFaces - null face detected, skipping");
            } else if (face.getId() == -1) {
                z = false;
            }
        }
        android.graphics.Rect[] rectArr = new android.graphics.Rect[length];
        byte[] bArr = new byte[length];
        if (!z) {
            iArr = null;
            iArr2 = null;
        } else {
            iArr = new int[length];
            iArr2 = new int[length * 6];
        }
        int i = 0;
        for (android.hardware.camera2.params.Face face2 : faceArr) {
            if (face2 != null) {
                rectArr[i] = face2.getBounds();
                bArr[i] = (byte) face2.getScore();
                if (z) {
                    iArr[i] = face2.getId();
                    int i2 = i * 6;
                    iArr2[i2 + 0] = face2.getLeftEyePosition().x;
                    iArr2[i2 + 1] = face2.getLeftEyePosition().y;
                    iArr2[i2 + 2] = face2.getRightEyePosition().x;
                    iArr2[i2 + 3] = face2.getRightEyePosition().y;
                    iArr2[i2 + 4] = face2.getMouthPosition().x;
                    iArr2[i2 + 5] = face2.getMouthPosition().y;
                }
                i++;
            }
        }
        set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<android.graphics.Rect[]>>) android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES, (android.hardware.camera2.CaptureResult.Key<android.graphics.Rect[]>) rectArr);
        set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<int[]>>) android.hardware.camera2.CaptureResult.STATISTICS_FACE_IDS, (android.hardware.camera2.CaptureResult.Key<int[]>) iArr);
        set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<int[]>>) android.hardware.camera2.CaptureResult.STATISTICS_FACE_LANDMARKS, (android.hardware.camera2.CaptureResult.Key<int[]>) iArr2);
        set((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<byte[]>>) android.hardware.camera2.CaptureResult.STATISTICS_FACE_SCORES, (android.hardware.camera2.CaptureResult.Key<byte[]>) bArr);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.Face[] getFaces() {
        java.lang.Integer num = (java.lang.Integer) get(android.hardware.camera2.CaptureResult.STATISTICS_FACE_DETECT_MODE);
        byte[] bArr = (byte[]) get(android.hardware.camera2.CaptureResult.STATISTICS_FACE_SCORES);
        android.graphics.Rect[] rectArr = (android.graphics.Rect[]) get(android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES);
        int[] iArr = (int[]) get(android.hardware.camera2.CaptureResult.STATISTICS_FACE_IDS);
        int[] iArr2 = (int[]) get(android.hardware.camera2.CaptureResult.STATISTICS_FACE_LANDMARKS);
        if (areValuesAllNull(num, bArr, rectArr, iArr, iArr2)) {
            return null;
        }
        int i = 0;
        if (num == null) {
            android.util.Log.w(TAG, "Face detect mode metadata is null, assuming the mode is SIMPLE");
            num = 1;
        } else if (num.intValue() > 2) {
            num = 2;
        } else {
            if (num.intValue() == 0) {
                return new android.hardware.camera2.params.Face[0];
            }
            if (num.intValue() != 1 && num.intValue() != 2) {
                android.util.Log.w(TAG, "Unknown face detect mode: " + num);
                return new android.hardware.camera2.params.Face[0];
            }
        }
        if (bArr == null || rectArr == null) {
            android.util.Log.w(TAG, "Expect face scores and rectangles to be non-null");
            return new android.hardware.camera2.params.Face[0];
        }
        if (bArr.length != rectArr.length) {
            android.util.Log.w(TAG, java.lang.String.format("Face score size(%d) doesn match face rectangle size(%d)!", java.lang.Integer.valueOf(bArr.length), java.lang.Integer.valueOf(rectArr.length)));
        }
        int min = java.lang.Math.min(bArr.length, rectArr.length);
        if (num.intValue() == 2) {
            if (iArr == null || iArr2 == null) {
                android.util.Log.w(TAG, "Expect face ids and landmarks to be non-null for FULL mode,fallback to SIMPLE mode");
                num = 1;
            } else {
                if (iArr.length != min || iArr2.length != min * 6) {
                    android.util.Log.w(TAG, java.lang.String.format("Face id size(%d), or face landmark size(%d) don'tmatch face number(%d)!", java.lang.Integer.valueOf(iArr.length), java.lang.Integer.valueOf(iArr2.length * 6), java.lang.Integer.valueOf(min)));
                }
                min = java.lang.Math.min(java.lang.Math.min(min, iArr.length), iArr2.length / 6);
            }
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (num.intValue() == 1) {
            while (i < min) {
                if (bArr[i] <= 100 && bArr[i] >= 1) {
                    arrayList.add(new android.hardware.camera2.params.Face(rectArr[i], bArr[i]));
                }
                i++;
            }
        } else {
            while (i < min) {
                if (bArr[i] <= 100 && bArr[i] >= 1 && iArr[i] >= 0) {
                    int i2 = i * 6;
                    arrayList.add(new android.hardware.camera2.params.Face(rectArr[i], bArr[i], iArr[i], new android.graphics.Point(iArr2[i2], iArr2[i2 + 1]), new android.graphics.Point(iArr2[i2 + 2], iArr2[i2 + 3]), new android.graphics.Point(iArr2[i2 + 4], iArr2[i2 + 5])));
                }
                i++;
            }
        }
        android.hardware.camera2.params.Face[] faceArr = new android.hardware.camera2.params.Face[arrayList.size()];
        arrayList.toArray(faceArr);
        return faceArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.graphics.Rect[] getFaceRectangles() {
        android.graphics.Rect[] rectArr = (android.graphics.Rect[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES);
        if (rectArr == null) {
            return null;
        }
        android.graphics.Rect[] rectArr2 = new android.graphics.Rect[rectArr.length];
        for (int i = 0; i < rectArr.length; i++) {
            rectArr2[i] = new android.graphics.Rect(rectArr[i].left, rectArr[i].top, rectArr[i].right - rectArr[i].left, rectArr[i].bottom - rectArr[i].top);
        }
        return rectArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLensShadingMap(android.hardware.camera2.params.LensShadingMap lensShadingMap) {
        if (lensShadingMap == null) {
            return false;
        }
        float[] fArr = new float[lensShadingMap.getGainFactorCount()];
        lensShadingMap.copyGainFactors(fArr, 0);
        setBase((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<float[]>>) android.hardware.camera2.CaptureResult.STATISTICS_LENS_SHADING_MAP, (android.hardware.camera2.CaptureResult.Key<float[]>) fArr);
        setBase((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>>) android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (android.hardware.camera2.CameraCharacteristics.Key<android.util.Size>) new android.util.Size(lensShadingMap.getRowCount(), lensShadingMap.getColumnCount()));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.LensShadingMap getLensShadingMap() {
        float[] fArr = (float[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_LENS_SHADING_MAP);
        android.util.Size size = (android.util.Size) get(android.hardware.camera2.CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE);
        if (fArr == null) {
            return null;
        }
        if (size == null) {
            android.util.Log.w(TAG, "getLensShadingMap - Lens shading map size was null.");
            return null;
        }
        return new android.hardware.camera2.params.LensShadingMap(fArr, size.getHeight(), size.getWidth());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.DeviceStateSensorOrientationMap getDeviceStateOrientationMap() {
        long[] jArr = (long[]) getBase(android.hardware.camera2.CameraCharacteristics.INFO_DEVICE_STATE_ORIENTATIONS);
        if (jArr == null) {
            return null;
        }
        return new android.hardware.camera2.params.DeviceStateSensorOrientationMap(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.DynamicRangeProfiles getDynamicRangeProfiles() {
        long[] jArr = (long[]) getBase(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES_MAP);
        if (jArr == null) {
            return null;
        }
        return new android.hardware.camera2.params.DynamicRangeProfiles(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.ColorSpaceProfiles getColorSpaceProfiles() {
        long[] jArr = (long[]) getBase(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_COLOR_SPACE_PROFILES_MAP);
        if (jArr == null) {
            return null;
        }
        return new android.hardware.camera2.params.ColorSpaceProfiles(jArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.location.Location getGpsLocation() {
        java.lang.String str = (java.lang.String) get(android.hardware.camera2.CaptureResult.JPEG_GPS_PROCESSING_METHOD);
        double[] dArr = (double[]) get(android.hardware.camera2.CaptureResult.JPEG_GPS_COORDINATES);
        java.lang.Long l = (java.lang.Long) get(android.hardware.camera2.CaptureResult.JPEG_GPS_TIMESTAMP);
        if (areValuesAllNull(str, dArr, l)) {
            return null;
        }
        android.location.Location location = new android.location.Location(translateProcessToLocationProvider(str));
        if (l == null) {
            android.util.Log.w(TAG, "getGpsLocation - No timestamp for GPS location.");
        } else {
            location.setTime(l.longValue() * 1000);
        }
        if (dArr == null) {
            android.util.Log.w(TAG, "getGpsLocation - No coordinates for GPS location");
        } else {
            location.setLatitude(dArr[0]);
            location.setLongitude(dArr[1]);
            location.setAltitude(dArr[2]);
        }
        return location;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setGpsLocation(android.location.Location location) {
        if (location == null) {
            setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Long>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_TIMESTAMP, (android.hardware.camera2.CaptureRequest.Key<java.lang.Long>) null);
            setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<double[]>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_COORDINATES, (android.hardware.camera2.CaptureRequest.Key<double[]>) null);
            setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.String>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_PROCESSING_METHOD, (android.hardware.camera2.CaptureRequest.Key<java.lang.String>) null);
            return false;
        }
        double[] dArr = {location.getLatitude(), location.getLongitude(), location.getAltitude()};
        java.lang.String translateLocationProviderToProcess = translateLocationProviderToProcess(location.getProvider());
        set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Long>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_TIMESTAMP, (android.hardware.camera2.CaptureRequest.Key<java.lang.Long>) java.lang.Long.valueOf(location.getTime() / 1000));
        set((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<double[]>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_COORDINATES, (android.hardware.camera2.CaptureRequest.Key<double[]>) dArr);
        if (translateLocationProviderToProcess == null) {
            android.util.Log.w(TAG, "setGpsLocation - No process method, Location is not from a GPS or NETWORKprovider");
        } else {
            setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.String>>) android.hardware.camera2.CaptureRequest.JPEG_GPS_PROCESSING_METHOD, (android.hardware.camera2.CaptureRequest.Key<java.lang.String>) translateLocationProviderToProcess);
        }
        return true;
    }

    private void parseRecommendedConfigurations(android.hardware.camera2.params.RecommendedStreamConfiguration[] recommendedStreamConfigurationArr, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap, boolean z, java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfiguration>> arrayList, java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList2, java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList3, boolean[] zArr) {
        char c;
        int i;
        android.util.Size size;
        int i2;
        int i3;
        int i4;
        int i5;
        android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration;
        android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration2;
        android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration3;
        android.hardware.camera2.params.RecommendedStreamConfiguration[] recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr;
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap2 = streamConfigurationMap;
        arrayList.ensureCapacity(32);
        arrayList2.ensureCapacity(32);
        arrayList3.ensureCapacity(32);
        boolean z2 = false;
        for (int i6 = 0; i6 < 32; i6++) {
            arrayList.add(new java.util.ArrayList<>());
            arrayList2.add(new java.util.ArrayList<>());
            arrayList3.add(new java.util.ArrayList<>());
        }
        int length = recommendedStreamConfigurationArr2.length;
        int i7 = 0;
        while (i7 < length) {
            android.hardware.camera2.params.RecommendedStreamConfiguration recommendedStreamConfiguration = recommendedStreamConfigurationArr2[i7];
            int width = recommendedStreamConfiguration.getWidth();
            int height = recommendedStreamConfiguration.getHeight();
            int format = recommendedStreamConfiguration.getFormat();
            int depthFormatToPublic = z ? android.hardware.camera2.params.StreamConfigurationMap.depthFormatToPublic(format) : android.hardware.camera2.params.StreamConfigurationMap.imageFormatToPublic(format);
            android.util.Size size2 = new android.util.Size(width, height);
            int usecaseBitmap = recommendedStreamConfiguration.getUsecaseBitmap();
            if (!recommendedStreamConfiguration.isInput()) {
                android.hardware.camera2.params.StreamConfiguration streamConfiguration = new android.hardware.camera2.params.StreamConfiguration(format, width, height, z2);
                long outputMinFrameDuration = streamConfigurationMap2.getOutputMinFrameDuration(depthFormatToPublic, size2);
                if (outputMinFrameDuration <= 0) {
                    i = usecaseBitmap;
                    size = size2;
                    i2 = depthFormatToPublic;
                    i3 = format;
                    i4 = width;
                    i5 = height;
                    streamConfigurationDuration = null;
                } else {
                    i = usecaseBitmap;
                    size = size2;
                    i2 = depthFormatToPublic;
                    i3 = format;
                    i4 = width;
                    i5 = height;
                    streamConfigurationDuration = new android.hardware.camera2.params.StreamConfigurationDuration(format, width, height, outputMinFrameDuration);
                }
                long outputStallDuration = streamConfigurationMap2.getOutputStallDuration(i2, size);
                if (outputStallDuration <= 0) {
                    streamConfigurationDuration2 = streamConfigurationDuration;
                    streamConfigurationDuration3 = null;
                } else {
                    streamConfigurationDuration2 = streamConfigurationDuration;
                    streamConfigurationDuration3 = new android.hardware.camera2.params.StreamConfigurationDuration(i3, i4, i5, outputStallDuration);
                }
                int i8 = 0;
                while (true) {
                    c = ' ';
                    if (i8 < 32) {
                        if ((i & (1 << i8)) != 0) {
                            arrayList.get(i8).add(streamConfiguration);
                            if (outputMinFrameDuration > 0) {
                                arrayList2.get(i8).add(streamConfigurationDuration2);
                            }
                            if (outputStallDuration > 0) {
                                arrayList3.get(i8).add(streamConfigurationDuration3);
                            }
                            if (zArr != null && !zArr[i8] && i2 == 34) {
                                zArr[i8] = true;
                            }
                        }
                        i8++;
                    }
                }
            } else {
                c = ' ';
                if (usecaseBitmap != 16) {
                    throw new java.lang.IllegalArgumentException("Recommended input stream configurations should only be advertised in the ZSL use case!");
                }
                arrayList.get(4).add(new android.hardware.camera2.params.StreamConfiguration(format, width, height, true));
            }
            i7++;
            recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr;
            streamConfigurationMap2 = streamConfigurationMap;
            z2 = false;
        }
    }

    private class StreamConfigurationData {
        android.hardware.camera2.params.StreamConfigurationDuration[] minDurationArray;
        android.hardware.camera2.params.StreamConfigurationDuration[] stallDurationArray;
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArray;

        private StreamConfigurationData() {
            this.streamConfigurationArray = null;
            this.minDurationArray = null;
            this.stallDurationArray = null;
        }
    }

    public void initializeStreamConfigurationData(java.util.ArrayList<android.hardware.camera2.params.StreamConfiguration> arrayList, java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration> arrayList2, java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration> arrayList3, android.hardware.camera2.impl.CameraMetadataNative.StreamConfigurationData streamConfigurationData) {
        if (streamConfigurationData == null || arrayList == null) {
            return;
        }
        streamConfigurationData.streamConfigurationArray = new android.hardware.camera2.params.StreamConfiguration[arrayList.size()];
        streamConfigurationData.streamConfigurationArray = (android.hardware.camera2.params.StreamConfiguration[]) arrayList.toArray(streamConfigurationData.streamConfigurationArray);
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            streamConfigurationData.minDurationArray = new android.hardware.camera2.params.StreamConfigurationDuration[arrayList2.size()];
            streamConfigurationData.minDurationArray = (android.hardware.camera2.params.StreamConfigurationDuration[]) arrayList2.toArray(streamConfigurationData.minDurationArray);
        } else {
            streamConfigurationData.minDurationArray = new android.hardware.camera2.params.StreamConfigurationDuration[0];
        }
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            streamConfigurationData.stallDurationArray = new android.hardware.camera2.params.StreamConfigurationDuration[arrayList3.size()];
            streamConfigurationData.stallDurationArray = (android.hardware.camera2.params.StreamConfigurationDuration[]) arrayList3.toArray(streamConfigurationData.stallDurationArray);
        } else {
            streamConfigurationData.stallDurationArray = new android.hardware.camera2.params.StreamConfigurationDuration[0];
        }
    }

    public java.util.ArrayList<android.hardware.camera2.params.RecommendedStreamConfigurationMap> getRecommendedStreamConfigurations() {
        java.lang.String str;
        boolean[] zArr;
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList;
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList2;
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList3;
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfiguration>> arrayList4;
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList5;
        android.hardware.camera2.params.RecommendedStreamConfiguration[] recommendedStreamConfigurationArr;
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap;
        android.hardware.camera2.params.RecommendedStreamConfiguration[] recommendedStreamConfigurationArr2 = (android.hardware.camera2.params.RecommendedStreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS);
        android.hardware.camera2.params.RecommendedStreamConfiguration[] recommendedStreamConfigurationArr3 = (android.hardware.camera2.params.RecommendedStreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS);
        if (recommendedStreamConfigurationArr2 == null && recommendedStreamConfigurationArr3 == null) {
            return null;
        }
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap2 = getStreamConfigurationMap();
        java.util.ArrayList<android.hardware.camera2.params.RecommendedStreamConfigurationMap> arrayList6 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfiguration>> arrayList7 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList8 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList9 = new java.util.ArrayList<>();
        boolean[] zArr2 = new boolean[32];
        if (recommendedStreamConfigurationArr2 != null) {
            str = TAG;
            zArr = zArr2;
            arrayList = arrayList9;
            try {
                parseRecommendedConfigurations(recommendedStreamConfigurationArr2, streamConfigurationMap2, false, arrayList7, arrayList8, arrayList9, zArr);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(str, "Failed parsing the recommended stream configurations!");
                return null;
            }
        } else {
            str = TAG;
            zArr = zArr2;
            arrayList = arrayList9;
        }
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfiguration>> arrayList10 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList11 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.ArrayList<android.hardware.camera2.params.StreamConfigurationDuration>> arrayList12 = new java.util.ArrayList<>();
        if (recommendedStreamConfigurationArr3 == null) {
            arrayList2 = arrayList12;
            arrayList3 = arrayList11;
            arrayList4 = arrayList10;
        } else {
            arrayList3 = arrayList11;
            arrayList2 = arrayList12;
            arrayList4 = arrayList10;
            try {
                parseRecommendedConfigurations(recommendedStreamConfigurationArr3, streamConfigurationMap2, true, arrayList10, arrayList11, arrayList12, null);
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Log.e(str, "Failed parsing the recommended depth stream configurations!");
                return null;
            }
        }
        android.hardware.camera2.params.ReprocessFormatsMap reprocessFormatsMap = (android.hardware.camera2.params.ReprocessFormatsMap) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP);
        android.hardware.camera2.params.HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr = (android.hardware.camera2.params.HighSpeedVideoConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS);
        boolean isBurstSupported = isBurstSupported();
        arrayList6.ensureCapacity(32);
        int i = 0;
        for (int i2 = 32; i < i2; i2 = 32) {
            android.hardware.camera2.impl.CameraMetadataNative.StreamConfigurationData streamConfigurationData = new android.hardware.camera2.impl.CameraMetadataNative.StreamConfigurationData();
            if (recommendedStreamConfigurationArr2 == null) {
                arrayList5 = arrayList;
            } else {
                arrayList5 = arrayList;
                initializeStreamConfigurationData(arrayList7.get(i), arrayList8.get(i), arrayList5.get(i), streamConfigurationData);
            }
            android.hardware.camera2.impl.CameraMetadataNative.StreamConfigurationData streamConfigurationData2 = new android.hardware.camera2.impl.CameraMetadataNative.StreamConfigurationData();
            if (recommendedStreamConfigurationArr3 == null) {
                recommendedStreamConfigurationArr = recommendedStreamConfigurationArr2;
            } else {
                recommendedStreamConfigurationArr = recommendedStreamConfigurationArr2;
                initializeStreamConfigurationData(arrayList4.get(i), arrayList3.get(i), arrayList2.get(i), streamConfigurationData2);
            }
            if ((streamConfigurationData.streamConfigurationArray == null || streamConfigurationData.streamConfigurationArray.length == 0) && (streamConfigurationData2.streamConfigurationArray == null || streamConfigurationData2.streamConfigurationArray.length == 0)) {
                arrayList6.add(null);
            } else {
                switch (i) {
                    case 0:
                    case 2:
                    case 5:
                    case 6:
                        streamConfigurationMap = new android.hardware.camera2.params.StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, null, null, isBurstSupported, zArr[i]);
                        break;
                    case 1:
                        streamConfigurationMap = new android.hardware.camera2.params.StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, null, highSpeedVideoConfigurationArr, null, isBurstSupported, zArr[i]);
                        break;
                    case 3:
                    default:
                        streamConfigurationMap = new android.hardware.camera2.params.StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, streamConfigurationData2.streamConfigurationArray, streamConfigurationData2.minDurationArray, streamConfigurationData2.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, isBurstSupported, zArr[i]);
                        break;
                    case 4:
                        streamConfigurationMap = new android.hardware.camera2.params.StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, streamConfigurationData2.streamConfigurationArray, streamConfigurationData2.minDurationArray, streamConfigurationData2.stallDurationArray, null, null, null, null, null, null, null, null, null, null, reprocessFormatsMap, isBurstSupported, zArr[i]);
                        break;
                }
                arrayList6.add(new android.hardware.camera2.params.RecommendedStreamConfigurationMap(streamConfigurationMap, i, zArr[i]));
            }
            i++;
            arrayList = arrayList5;
            recommendedStreamConfigurationArr2 = recommendedStreamConfigurationArr;
        }
        return arrayList6;
    }

    private boolean isCapabilitySupported(int i) {
        for (int i2 : (int[]) getBase(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean isUltraHighResolutionSensor() {
        return isCapabilitySupported(16);
    }

    private boolean isBurstSupported() {
        return isCapabilitySupported(6);
    }

    private boolean isPreviewStabilizationSupported() {
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
        if (iArr == null) {
            return false;
        }
        for (int i : iArr) {
            if (i == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isCroppedRawSupported() {
        long[] jArr = (long[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_STREAM_USE_CASES);
        if (jArr == null) {
            return false;
        }
        for (long j : jArr) {
            if (j == 6) {
                return true;
            }
        }
        return false;
    }

    private android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryStreamCombinationsHelper(int i) {
        java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> availableMandatoryMaximumResolutionStreamCombinations;
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.ensureCapacity(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(new java.lang.Integer(i2));
        }
        android.hardware.camera2.params.MandatoryStreamCombination.Builder builder = new android.hardware.camera2.params.MandatoryStreamCombination.Builder(this.mCameraId, ((java.lang.Integer) getBase(android.hardware.camera2.CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue(), this.mDisplaySize, arrayList, getStreamConfigurationMap(), getStreamConfigurationMapMaximumResolution(), isPreviewStabilizationSupported(), isCroppedRawSupported());
        switch (i) {
            case 1:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryMaximumResolutionStreamCombinations();
                break;
            case 2:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryConcurrentStreamCombinations();
                break;
            case 3:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatory10BitStreamCombinations();
                break;
            case 4:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryStreamUseCaseCombinations();
                break;
            case 5:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryPreviewStabilizedStreamCombinations();
                break;
            default:
                availableMandatoryMaximumResolutionStreamCombinations = builder.getAvailableMandatoryStreamCombinations();
                break;
        }
        if (availableMandatoryMaximumResolutionStreamCombinations != null && !availableMandatoryMaximumResolutionStreamCombinations.isEmpty()) {
            return (android.hardware.camera2.params.MandatoryStreamCombination[]) availableMandatoryMaximumResolutionStreamCombinations.toArray(new android.hardware.camera2.params.MandatoryStreamCombination[availableMandatoryMaximumResolutionStreamCombinations.size()]);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatory10BitStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryConcurrentStreamCombinations() {
        if (!this.mHasMandatoryConcurrentStreams) {
            return null;
        }
        return getMandatoryStreamCombinationsHelper(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryMaximumResolutionStreamCombinations() {
        if (!isUltraHighResolutionSensor()) {
            return null;
        }
        return getMandatoryStreamCombinationsHelper(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryUseCaseStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.MandatoryStreamCombination[] getMandatoryPreviewStabilizationStreamCombinations() {
        return getMandatoryStreamCombinationsHelper(5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.StreamConfigurationMap getStreamConfigurationMap() {
        return new android.hardware.camera2.params.StreamConfigurationMap((android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS), (android.hardware.camera2.params.HighSpeedVideoConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS), (android.hardware.camera2.params.ReprocessFormatsMap) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP), isBurstSupported());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.StreamConfigurationMap getStreamConfigurationMapMaximumResolution() {
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr = (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION);
        android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr = (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION);
        android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr2 = (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS_MAXIMUM_RESOLUTION);
        if (streamConfigurationArr == null || streamConfigurationDurationArr == null || streamConfigurationDurationArr2 == null) {
            return null;
        }
        return new android.hardware.camera2.params.StreamConfigurationMap(streamConfigurationArr, streamConfigurationDurationArr, streamConfigurationDurationArr2, (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STREAM_CONFIGURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_MIN_FRAME_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.StreamConfigurationDuration[]) getBase(android.hardware.camera2.CameraCharacteristics.JPEGR_AVAILABLE_JPEG_R_STALL_DURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.HighSpeedVideoConfiguration[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS_MAXIMUM_RESOLUTION), (android.hardware.camera2.params.ReprocessFormatsMap) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP_MAXIMUM_RESOLUTION), isBurstSupported(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> java.lang.Integer getMaxRegions(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS);
        if (iArr == null) {
            return null;
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AE)) {
            return java.lang.Integer.valueOf(iArr[0]);
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)) {
            return java.lang.Integer.valueOf(iArr[1]);
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.CONTROL_MAX_REGIONS_AF)) {
            return java.lang.Integer.valueOf(iArr[2]);
        }
        throw new java.lang.AssertionError("Invalid key " + key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> java.lang.Integer getMaxNumOutputs(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key) {
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS);
        if (iArr == null) {
            return null;
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW)) {
            return java.lang.Integer.valueOf(iArr[0]);
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC)) {
            return java.lang.Integer.valueOf(iArr[1]);
        }
        if (key.equals(android.hardware.camera2.CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING)) {
            return java.lang.Integer.valueOf(iArr[2]);
        }
        throw new java.lang.AssertionError("Invalid key " + key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> android.hardware.camera2.params.TonemapCurve getTonemapCurve() {
        float[] fArr = (float[]) getBase(android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_RED);
        float[] fArr2 = (float[]) getBase(android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_GREEN);
        float[] fArr3 = (float[]) getBase(android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_BLUE);
        if (areValuesAllNull(fArr, fArr2, fArr3)) {
            return null;
        }
        if (fArr == null || fArr2 == null || fArr3 == null) {
            android.util.Log.w(TAG, "getTonemapCurve - missing tone curve components");
            return null;
        }
        return new android.hardware.camera2.params.TonemapCurve(fArr, fArr2, fArr3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.OisSample[] getOisSamples() {
        long[] jArr = (long[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_OIS_TIMESTAMPS);
        float[] fArr = (float[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_OIS_X_SHIFTS);
        float[] fArr2 = (float[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_OIS_Y_SHIFTS);
        if (jArr == null) {
            if (fArr != null) {
                throw new java.lang.AssertionError("timestamps is null but xShifts is not");
            }
            if (fArr2 != null) {
                throw new java.lang.AssertionError("timestamps is null but yShifts is not");
            }
            return null;
        }
        if (fArr == null) {
            throw new java.lang.AssertionError("timestamps is not null but xShifts is");
        }
        if (fArr2 == null) {
            throw new java.lang.AssertionError("timestamps is not null but yShifts is");
        }
        if (fArr.length != jArr.length) {
            throw new java.lang.AssertionError(java.lang.String.format("timestamps has %d entries but xShifts has %d", java.lang.Integer.valueOf(jArr.length), java.lang.Integer.valueOf(fArr.length)));
        }
        if (fArr2.length != jArr.length) {
            throw new java.lang.AssertionError(java.lang.String.format("timestamps has %d entries but yShifts has %d", java.lang.Integer.valueOf(jArr.length), java.lang.Integer.valueOf(fArr2.length)));
        }
        android.hardware.camera2.params.OisSample[] oisSampleArr = new android.hardware.camera2.params.OisSample[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            oisSampleArr[i] = new android.hardware.camera2.params.OisSample(jArr[i], fArr[i], fArr2[i]);
        }
        return oisSampleArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLensIntrinsicsSamples(android.hardware.camera2.params.LensIntrinsicsSample[] lensIntrinsicsSampleArr) {
        if (lensIntrinsicsSampleArr == null || !com.android.internal.camera.flags.Flags.concertMode()) {
            return false;
        }
        long[] jArr = new long[lensIntrinsicsSampleArr.length];
        float[] fArr = new float[lensIntrinsicsSampleArr.length * 5];
        for (int i = 0; i < lensIntrinsicsSampleArr.length; i++) {
            jArr[i] = lensIntrinsicsSampleArr[i].getTimestampNanos();
            java.lang.System.arraycopy(lensIntrinsicsSampleArr[i].getLensIntrinsics(), 0, fArr, i * 5, 5);
        }
        setBase((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<float[]>>) android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSIC_SAMPLES, (android.hardware.camera2.CaptureResult.Key<float[]>) fArr);
        setBase((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<long[]>>) android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSIC_TIMESTAMPS, (android.hardware.camera2.CaptureResult.Key<long[]>) jArr);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.LensIntrinsicsSample[] getLensIntrinsicSamples() {
        if (!com.android.internal.camera.flags.Flags.concertMode()) {
            return null;
        }
        long[] jArr = (long[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSIC_TIMESTAMPS);
        float[] fArr = (float[]) getBase(android.hardware.camera2.CaptureResult.STATISTICS_LENS_INTRINSIC_SAMPLES);
        if (jArr == null) {
            if (fArr == null) {
                return null;
            }
            throw new java.lang.AssertionError("timestamps is null but intrinsics is not");
        }
        if (fArr == null) {
            throw new java.lang.AssertionError("timestamps is not null but intrinsics is");
        }
        if (fArr.length % 5 != 0) {
            throw new java.lang.AssertionError("intrinsics are not multiple of 5");
        }
        if (fArr.length / 5 != jArr.length) {
            throw new java.lang.AssertionError(java.lang.String.format("timestamps has %d entries but intrinsics has %d", java.lang.Integer.valueOf(jArr.length), java.lang.Integer.valueOf(fArr.length / 5)));
        }
        android.hardware.camera2.params.LensIntrinsicsSample[] lensIntrinsicsSampleArr = new android.hardware.camera2.params.LensIntrinsicsSample[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            int i2 = i * 5;
            lensIntrinsicsSampleArr[i] = new android.hardware.camera2.params.LensIntrinsicsSample(jArr[i], java.util.Arrays.copyOfRange(fArr, i2, i2 + 5));
        }
        return lensIntrinsicsSampleArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.camera2.params.Capability[] getExtendedSceneModeCapabilities() {
        int i;
        float f;
        float f2;
        int[] iArr = (int[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_MAX_SIZES);
        float[] fArr = (float[]) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_AVAILABLE_EXTENDED_SCENE_MODE_ZOOM_RATIO_RANGES);
        android.util.Range range = (android.util.Range) getBase(android.hardware.camera2.CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE);
        float floatValue = ((java.lang.Float) getBase(android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
        if (iArr == null) {
            return null;
        }
        if (iArr.length % 3 != 0) {
            throw new java.lang.AssertionError("availableExtendedSceneModeMaxSizes must be tuples of [mode, width, height]");
        }
        int length = iArr.length / 3;
        if (fArr == null) {
            i = 0;
        } else {
            if (fArr.length % 2 != 0) {
                throw new java.lang.AssertionError("availableExtendedSceneModeZoomRanges must be tuples of [minZoom, maxZoom]");
            }
            i = fArr.length / 2;
            if (length - i != 1) {
                throw new java.lang.AssertionError("Number of extended scene mode zoom ranges must be 1 less than number of supported modes");
            }
        }
        if (range == null) {
            f = floatValue;
            f2 = 1.0f;
        } else {
            f2 = ((java.lang.Float) range.getLower()).floatValue();
            f = ((java.lang.Float) range.getUpper()).floatValue();
        }
        android.hardware.camera2.params.Capability[] capabilityArr = new android.hardware.camera2.params.Capability[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i3 * 3;
            int i5 = iArr[i4];
            int i6 = iArr[i4 + 1];
            int i7 = iArr[i4 + 2];
            if (i5 != 0 && i2 < i) {
                android.util.Size size = new android.util.Size(i6, i7);
                int i8 = i2 * 2;
                capabilityArr[i3] = new android.hardware.camera2.params.Capability(i5, size, new android.util.Range(java.lang.Float.valueOf(fArr[i8]), java.lang.Float.valueOf(fArr[i8 + 1])));
                i2++;
            } else {
                capabilityArr[i3] = new android.hardware.camera2.params.Capability(i5, new android.util.Size(i6, i7), new android.util.Range(java.lang.Float.valueOf(f2), java.lang.Float.valueOf(f)));
            }
        }
        return capabilityArr;
    }

    private <T> void setBase(android.hardware.camera2.CameraCharacteristics.Key<T> key, T t) {
        setBase((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    private <T> void setBase(android.hardware.camera2.CaptureResult.Key<T> key, T t) {
        setBase((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    private <T> void setBase(android.hardware.camera2.CaptureRequest.Key<T> key, T t) {
        setBase((android.hardware.camera2.impl.CameraMetadataNative.Key<android.hardware.camera2.impl.CameraMetadataNative.Key<T>>) key.getNativeKey(), (android.hardware.camera2.impl.CameraMetadataNative.Key<T>) t);
    }

    private <T> void setBase(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key, T t) {
        int nativeGetTagFromKeyLocal;
        if (key.hasTag()) {
            nativeGetTagFromKeyLocal = key.getTag();
        } else {
            nativeGetTagFromKeyLocal = nativeGetTagFromKeyLocal(this.mMetadataPtr, key.getName());
            key.cacheTag(nativeGetTagFromKeyLocal);
        }
        if (t == null) {
            writeValues(nativeGetTagFromKeyLocal, null);
            return;
        }
        android.hardware.camera2.marshal.Marshaler marshalerForKey = getMarshalerForKey(key, nativeGetTypeFromTagLocal(this.mMetadataPtr, nativeGetTagFromKeyLocal));
        byte[] bArr = new byte[marshalerForKey.calculateMarshalSize(t)];
        marshalerForKey.marshal(t, java.nio.ByteBuffer.wrap(bArr).order(java.nio.ByteOrder.nativeOrder()));
        writeValues(nativeGetTagFromKeyLocal, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setAvailableFormats(int[] iArr) {
        if (iArr == null) {
            return false;
        }
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = iArr[i];
            if (iArr[i] == 256) {
                iArr2[i] = 33;
            }
        }
        setBase((android.hardware.camera2.CameraCharacteristics.Key<android.hardware.camera2.CameraCharacteristics.Key<int[]>>) android.hardware.camera2.CameraCharacteristics.SCALER_AVAILABLE_FORMATS, (android.hardware.camera2.CameraCharacteristics.Key<int[]>) iArr2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setFaceRectangles(android.graphics.Rect[] rectArr) {
        if (rectArr == null) {
            return false;
        }
        int length = rectArr.length;
        android.graphics.Rect[] rectArr2 = new android.graphics.Rect[length];
        for (int i = 0; i < length; i++) {
            rectArr2[i] = new android.graphics.Rect(rectArr[i].left, rectArr[i].top, rectArr[i].right + rectArr[i].left, rectArr[i].bottom + rectArr[i].top);
        }
        setBase((android.hardware.camera2.CaptureResult.Key<android.hardware.camera2.CaptureResult.Key<android.graphics.Rect[]>>) android.hardware.camera2.CaptureResult.STATISTICS_FACE_RECTANGLES, (android.hardware.camera2.CaptureResult.Key<android.graphics.Rect[]>) rectArr2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setTonemapCurve(android.hardware.camera2.params.TonemapCurve tonemapCurve) {
        if (tonemapCurve == null) {
            return false;
        }
        float[][] fArr = new float[3][];
        for (int i = 0; i <= 2; i++) {
            fArr[i] = new float[tonemapCurve.getPointCount(i) * 2];
            tonemapCurve.copyColorCurve(i, fArr[i], 0);
        }
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<float[]>>) android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_RED, (android.hardware.camera2.CaptureRequest.Key<float[]>) fArr[0]);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<float[]>>) android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_GREEN, (android.hardware.camera2.CaptureRequest.Key<float[]>) fArr[1]);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<float[]>>) android.hardware.camera2.CaptureRequest.TONEMAP_CURVE_BLUE, (android.hardware.camera2.CaptureRequest.Key<float[]>) fArr[2]);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setScalerCropRegion(android.graphics.Rect rect) {
        if (rect == null) {
            return false;
        }
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>>) android.hardware.camera2.CaptureRequest.SCALER_CROP_REGION_SET, (android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>) true);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<android.graphics.Rect>>) android.hardware.camera2.CaptureRequest.SCALER_CROP_REGION, (android.hardware.camera2.CaptureRequest.Key<android.graphics.Rect>) rect);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAFRegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>>) android.hardware.camera2.CaptureRequest.CONTROL_AF_REGIONS_SET, (android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>) true);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>>) android.hardware.camera2.CaptureRequest.CONTROL_AF_REGIONS, (android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>) t);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAERegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>>) android.hardware.camera2.CaptureRequest.CONTROL_AE_REGIONS_SET, (android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>) true);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>>) android.hardware.camera2.CaptureRequest.CONTROL_AE_REGIONS, (android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>) t);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> boolean setAWBRegions(T t) {
        if (t == null) {
            return false;
        }
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>>) android.hardware.camera2.CaptureRequest.CONTROL_AWB_REGIONS_SET, (android.hardware.camera2.CaptureRequest.Key<java.lang.Boolean>) true);
        setBase((android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>>) android.hardware.camera2.CaptureRequest.CONTROL_AWB_REGIONS, (android.hardware.camera2.CaptureRequest.Key<android.hardware.camera2.params.MeteringRectangle[]>) t);
        return true;
    }

    private void updateNativeAllocation() {
        long nativeGetBufferSize = nativeGetBufferSize(this.mMetadataPtr);
        if (nativeGetBufferSize != this.mBufferSize) {
            if (this.mBufferSize > 0) {
                dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mBufferSize);
            }
            this.mBufferSize = nativeGetBufferSize;
            if (this.mBufferSize > 0) {
                dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(this.mBufferSize);
            }
        }
    }

    public void setCameraId(int i) {
        this.mCameraId = i;
    }

    public void setHasMandatoryConcurrentStreams(boolean z) {
        this.mHasMandatoryConcurrentStreams = z;
    }

    public void setDisplaySize(android.util.Size size) {
        this.mDisplaySize = size;
    }

    public void setMultiResolutionStreamConfigurationMap(java.util.Map<java.lang.String, android.hardware.camera2.params.StreamConfiguration[]> map) {
        this.mMultiResolutionStreamConfigurationMap = new android.hardware.camera2.params.MultiResolutionStreamConfigurationMap(map);
    }

    public android.hardware.camera2.params.MultiResolutionStreamConfigurationMap getMultiResolutionStreamConfigurationMap() {
        return this.mMultiResolutionStreamConfigurationMap;
    }

    private synchronized byte[] nativeReadValues(int i) {
        return nativeReadValues(i, this.mMetadataPtr);
    }

    private synchronized int nativeGetTypeFromTagLocal(int i) {
        return nativeGetTypeFromTagLocal(this.mMetadataPtr, i);
    }

    private synchronized int nativeGetTagFromKeyLocal(java.lang.String str) {
        return nativeGetTagFromKeyLocal(this.mMetadataPtr, str);
    }

    public void swap(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
        nativeSwap(this.mMetadataPtr, cameraMetadataNative.mMetadataPtr);
        this.mCameraId = cameraMetadataNative.mCameraId;
        this.mHasMandatoryConcurrentStreams = cameraMetadataNative.mHasMandatoryConcurrentStreams;
        this.mDisplaySize = cameraMetadataNative.mDisplaySize;
        this.mMultiResolutionStreamConfigurationMap = cameraMetadataNative.mMultiResolutionStreamConfigurationMap;
        updateNativeAllocation();
        cameraMetadataNative.updateNativeAllocation();
    }

    public void setVendorId(long j) {
        nativeSetVendorId(this.mMetadataPtr, j);
    }

    public int getEntryCount() {
        return nativeGetEntryCount(this.mMetadataPtr);
    }

    public boolean isEmpty() {
        return nativeIsEmpty(this.mMetadataPtr);
    }

    public long getMetadataPtr() {
        return this.mMetadataPtr;
    }

    public <K> java.util.ArrayList<K> getAllVendorKeys(java.lang.Class<K> cls) {
        if (cls == null) {
            throw new java.lang.NullPointerException();
        }
        return nativeGetAllVendorKeys(this.mMetadataPtr, cls);
    }

    public static int getTag(java.lang.String str) {
        return nativeGetTagFromKey(str, Long.MAX_VALUE);
    }

    public static int getTag(java.lang.String str, long j) {
        return nativeGetTagFromKey(str, j);
    }

    public static int getNativeType(int i, long j) {
        return nativeGetTypeFromTag(i, j);
    }

    public void writeValues(int i, byte[] bArr) {
        nativeWriteValues(i, bArr, this.mMetadataPtr);
    }

    public byte[] readValues(int i) {
        return nativeReadValues(i, this.mMetadataPtr);
    }

    public void dumpToLog() {
        try {
            nativeDump(this.mMetadataPtr);
        } catch (java.io.IOException e) {
            android.util.Log.wtf(TAG, "Dump logging failed", e);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private static <T> android.hardware.camera2.marshal.Marshaler<T> getMarshalerForKey(android.hardware.camera2.impl.CameraMetadataNative.Key<T> key, int i) {
        return android.hardware.camera2.marshal.MarshalRegistry.getMarshaler(key.getTypeReference(), i);
    }

    private static void registerAllMarshalers() {
        android.hardware.camera2.marshal.MarshalQueryable[] marshalQueryableArr = {new android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive(), new android.hardware.camera2.marshal.impl.MarshalQueryableEnum(), new android.hardware.camera2.marshal.impl.MarshalQueryableArray(), new android.hardware.camera2.marshal.impl.MarshalQueryableBoolean(), new android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger(), new android.hardware.camera2.marshal.impl.MarshalQueryableRect(), new android.hardware.camera2.marshal.impl.MarshalQueryableSize(), new android.hardware.camera2.marshal.impl.MarshalQueryableSizeF(), new android.hardware.camera2.marshal.impl.MarshalQueryableString(), new android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap(), new android.hardware.camera2.marshal.impl.MarshalQueryableRange(), new android.hardware.camera2.marshal.impl.MarshalQueryablePair(), new android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle(), new android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform(), new android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration(), new android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration(), new android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector(), new android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern(), new android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration(), new android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration(), new android.hardware.camera2.marshal.impl.MarshalQueryableParcelable()};
        for (int i = 0; i < 21; i++) {
            android.hardware.camera2.marshal.MarshalRegistry.registerMarshalQueryable(marshalQueryableArr[i]);
        }
    }

    private static boolean areValuesAllNull(java.lang.Object... objArr) {
        for (java.lang.Object obj : objArr) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    public java.util.Set<java.lang.String> getPhysicalCameraIds() {
        int[] iArr = (int[]) get(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        if (iArr == null) {
            throw new java.lang.AssertionError("android.request.availableCapabilities must be non-null in the characteristics");
        }
        if (!android.hardware.camera2.utils.ArrayUtils.contains(iArr, 11)) {
            return java.util.Collections.emptySet();
        }
        try {
            return java.util.Collections.unmodifiableSet(new java.util.HashSet(java.util.Arrays.asList(new java.lang.String((byte[]) get(android.hardware.camera2.CameraCharacteristics.LOGICAL_MULTI_CAMERA_PHYSICAL_IDS), android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8).split("\u0000"))));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.AssertionError("android.logicalCam.physicalIds must be UTF-8 string");
        }
    }
}
