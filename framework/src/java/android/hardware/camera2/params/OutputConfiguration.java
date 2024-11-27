package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class OutputConfiguration implements android.os.Parcelable {
    private static final int MAX_SURFACES_COUNT = 4;
    public static final int MIRROR_MODE_AUTO = 0;
    public static final int MIRROR_MODE_H = 2;
    public static final int MIRROR_MODE_NONE = 1;
    public static final int MIRROR_MODE_V = 3;

    @android.annotation.SystemApi
    public static final int ROTATION_0 = 0;

    @android.annotation.SystemApi
    public static final int ROTATION_180 = 2;

    @android.annotation.SystemApi
    public static final int ROTATION_270 = 3;

    @android.annotation.SystemApi
    public static final int ROTATION_90 = 1;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    private static final java.lang.String TAG = "OutputConfiguration";
    public static final int TIMESTAMP_BASE_CHOREOGRAPHER_SYNCED = 4;
    public static final int TIMESTAMP_BASE_DEFAULT = 0;
    public static final int TIMESTAMP_BASE_MONOTONIC = 2;
    public static final int TIMESTAMP_BASE_READOUT_SENSOR = 5;
    public static final int TIMESTAMP_BASE_REALTIME = 3;
    public static final int TIMESTAMP_BASE_SENSOR = 1;
    private final int SURFACE_TYPE_SURFACE_TEXTURE;
    private final int SURFACE_TYPE_SURFACE_VIEW;
    private final int SURFACE_TYPE_UNKNOWN;
    private int mColorSpace;
    private final int mConfiguredDataspace;
    private final int mConfiguredFormat;
    private final int mConfiguredGenerationId;
    private final android.util.Size mConfiguredSize;
    private long mDynamicRangeProfile;
    private final boolean mIsDeferredConfig;
    private boolean mIsMultiResolution;
    private boolean mIsReadoutSensorTimestampBase;
    private boolean mIsShared;
    private int mMirrorMode;
    private java.lang.String mPhysicalCameraId;
    private boolean mReadoutTimestampEnabled;
    private final int mRotation;
    private java.util.ArrayList<java.lang.Integer> mSensorPixelModesUsed;
    private long mStreamUseCase;
    private final int mSurfaceGroupId;
    private final int mSurfaceType;
    private java.util.ArrayList<android.view.Surface> mSurfaces;
    private int mTimestampBase;
    public static final android.os.Parcelable.Creator<android.hardware.camera2.params.OutputConfiguration> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.params.OutputConfiguration>() { // from class: android.hardware.camera2.params.OutputConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.OutputConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.params.OutputConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.OutputConfiguration[] newArray(int i) {
            return new android.hardware.camera2.params.OutputConfiguration[i];
        }
    };
    private static int MULTI_RESOLUTION_GROUP_ID_COUNTER = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MirrorMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SensorPixelMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamUseCase {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimestampBase {
    }

    public OutputConfiguration(android.view.Surface surface) {
        this(-1, surface, 0);
    }

    public OutputConfiguration(int i, android.view.Surface surface) {
        this(i, surface, 0);
    }

    public void setMultiResolutionOutput() {
        if (this.mIsShared) {
            throw new java.lang.IllegalStateException("Multi-resolution output flag must not be set for configuration with surface sharing");
        }
        if (this.mSurfaceGroupId == -1) {
            throw new java.lang.IllegalStateException("Multi-resolution output flag should only be set for surface with non-negative group ID");
        }
        this.mIsMultiResolution = true;
    }

    public void setDynamicRangeProfile(long j) {
        this.mDynamicRangeProfile = j;
    }

    public long getDynamicRangeProfile() {
        return this.mDynamicRangeProfile;
    }

    public void setColorSpace(android.graphics.ColorSpace.Named named) {
        this.mColorSpace = named.ordinal();
    }

    public void clearColorSpace() {
        this.mColorSpace = -1;
    }

    public android.graphics.ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }

    @android.annotation.SystemApi
    public OutputConfiguration(android.view.Surface surface, int i) {
        this(-1, surface, i);
    }

    @android.annotation.SystemApi
    public OutputConfiguration(int i, android.view.Surface surface, int i2) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        com.android.internal.util.Preconditions.checkNotNull(surface, "Surface must not be null");
        com.android.internal.util.Preconditions.checkArgumentInRange(i2, 0, 3, "Rotation constant");
        this.mSurfaceGroupId = i;
        this.mSurfaceType = -1;
        this.mSurfaces = new java.util.ArrayList<>();
        this.mSurfaces.add(surface);
        this.mRotation = i2;
        this.mConfiguredSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
        this.mConfiguredFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
        this.mConfiguredDataspace = android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(surface);
        this.mConfiguredGenerationId = surface.getGenerationId();
        this.mIsDeferredConfig = false;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
        this.mIsMultiResolution = false;
        this.mSensorPixelModesUsed = new java.util.ArrayList<>();
        this.mDynamicRangeProfile = 1L;
        this.mColorSpace = -1;
        this.mStreamUseCase = 0L;
        this.mTimestampBase = 0;
        this.mMirrorMode = 0;
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
    }

    public static java.util.Collection<android.hardware.camera2.params.OutputConfiguration> createInstancesForMultiResolutionOutput(android.hardware.camera2.MultiResolutionImageReader multiResolutionImageReader) {
        com.android.internal.util.Preconditions.checkNotNull(multiResolutionImageReader, "Multi-resolution image reader must not be null");
        int i = MULTI_RESOLUTION_GROUP_ID_COUNTER;
        MULTI_RESOLUTION_GROUP_ID_COUNTER++;
        if (MULTI_RESOLUTION_GROUP_ID_COUNTER == -1) {
            MULTI_RESOLUTION_GROUP_ID_COUNTER++;
        }
        android.media.ImageReader[] readers = multiResolutionImageReader.getReaders();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < readers.length; i2++) {
            android.hardware.camera2.params.MultiResolutionStreamInfo streamInfoForImageReader = multiResolutionImageReader.getStreamInfoForImageReader(readers[i2]);
            android.hardware.camera2.params.OutputConfiguration outputConfiguration = new android.hardware.camera2.params.OutputConfiguration(i, readers[i2].getSurface());
            outputConfiguration.setPhysicalCameraId(streamInfoForImageReader.getPhysicalCameraId());
            outputConfiguration.setMultiResolutionOutput();
            arrayList.add(outputConfiguration);
        }
        return arrayList;
    }

    public <T> OutputConfiguration(android.util.Size size, java.lang.Class<T> cls) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        com.android.internal.util.Preconditions.checkNotNull(cls, "surfaceSize must not be null");
        com.android.internal.util.Preconditions.checkNotNull(cls, "klass must not be null");
        if (cls == android.view.SurfaceHolder.class) {
            this.mSurfaceType = 0;
        } else if (cls == android.graphics.SurfaceTexture.class) {
            this.mSurfaceType = 1;
        } else {
            this.mSurfaceType = -1;
            throw new java.lang.IllegalArgumentException("Unknown surface source class type");
        }
        if (size.getWidth() == 0 || size.getHeight() == 0) {
            throw new java.lang.IllegalArgumentException("Surface size needs to be non-zero");
        }
        this.mSurfaceGroupId = -1;
        this.mSurfaces = new java.util.ArrayList<>();
        this.mRotation = 0;
        this.mConfiguredSize = size;
        this.mConfiguredFormat = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToInternal(34);
        this.mConfiguredDataspace = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToDataspace(34);
        this.mConfiguredGenerationId = 0;
        this.mIsDeferredConfig = true;
        this.mIsShared = false;
        this.mPhysicalCameraId = null;
        this.mIsMultiResolution = false;
        this.mSensorPixelModesUsed = new java.util.ArrayList<>();
        this.mDynamicRangeProfile = 1L;
        this.mColorSpace = -1;
        this.mStreamUseCase = 0L;
        this.mReadoutTimestampEnabled = false;
        this.mIsReadoutSensorTimestampBase = false;
    }

    public void enableSurfaceSharing() {
        if (this.mIsMultiResolution) {
            throw new java.lang.IllegalStateException("Cannot enable surface sharing on multi-resolution output configurations");
        }
        this.mIsShared = true;
    }

    public void setPhysicalCameraId(java.lang.String str) {
        this.mPhysicalCameraId = str;
    }

    public void addSensorPixelModeUsed(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Not a valid sensor pixel mode " + i);
        }
        if (this.mSensorPixelModesUsed.contains(java.lang.Integer.valueOf(i))) {
            return;
        }
        this.mSensorPixelModesUsed.add(java.lang.Integer.valueOf(i));
    }

    public void removeSensorPixelModeUsed(int i) {
        if (!this.mSensorPixelModesUsed.remove(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("sensorPixelMode " + i + "is not part of this output configuration");
        }
    }

    public boolean isForPhysicalCamera() {
        return this.mPhysicalCameraId != null;
    }

    public boolean isDeferredConfiguration() {
        return this.mIsDeferredConfig;
    }

    public void addSurface(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface, "Surface must not be null");
        if (this.mSurfaces.contains(surface)) {
            throw new java.lang.IllegalStateException("Surface is already added!");
        }
        if (this.mSurfaces.size() == 1 && !this.mIsShared) {
            throw new java.lang.IllegalStateException("Cannot have 2 surfaces for a non-sharing configuration");
        }
        if (this.mSurfaces.size() + 1 > 4) {
            throw new java.lang.IllegalArgumentException("Exceeds maximum number of surfaces");
        }
        android.util.Size surfaceSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
        if (!surfaceSize.equals(this.mConfiguredSize)) {
            android.util.Log.w(TAG, "Added surface size " + surfaceSize + " is different than pre-configured size " + this.mConfiguredSize + ", the pre-configured size will be used.");
        }
        if (this.mConfiguredFormat != android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface)) {
            throw new java.lang.IllegalArgumentException("The format of added surface format doesn't match");
        }
        if (this.mConfiguredFormat != 34 && this.mConfiguredDataspace != android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(surface)) {
            throw new java.lang.IllegalArgumentException("The dataspace of added surface doesn't match");
        }
        this.mSurfaces.add(surface);
    }

    public void removeSurface(android.view.Surface surface) {
        if (getSurface() == surface) {
            throw new java.lang.IllegalArgumentException("Cannot remove surface associated with this output configuration");
        }
        if (!this.mSurfaces.remove(surface)) {
            throw new java.lang.IllegalArgumentException("Surface is not part of this output configuration");
        }
    }

    public void setStreamUseCase(long j) {
        if (j > 6 && j < 65536) {
            throw new java.lang.IllegalArgumentException("Not a valid stream use case value " + j);
        }
        this.mStreamUseCase = j;
    }

    public long getStreamUseCase() {
        return this.mStreamUseCase;
    }

    public void setTimestampBase(int i) {
        if (i < 0 || i > 5) {
            throw new java.lang.IllegalArgumentException("Not a valid timestamp base value " + i);
        }
        if (i == 5) {
            this.mTimestampBase = 1;
            this.mReadoutTimestampEnabled = true;
            this.mIsReadoutSensorTimestampBase = true;
        } else {
            this.mTimestampBase = i;
            this.mIsReadoutSensorTimestampBase = false;
        }
    }

    public int getTimestampBase() {
        if (this.mIsReadoutSensorTimestampBase) {
            return 5;
        }
        return this.mTimestampBase;
    }

    public void setMirrorMode(int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Not a valid mirror mode " + i);
        }
        this.mMirrorMode = i;
    }

    public int getMirrorMode() {
        return this.mMirrorMode;
    }

    public void setReadoutTimestampEnabled(boolean z) {
        this.mReadoutTimestampEnabled = z;
    }

    public boolean isReadoutTimestampEnabled() {
        return this.mReadoutTimestampEnabled;
    }

    public OutputConfiguration(android.hardware.camera2.params.OutputConfiguration outputConfiguration) {
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        if (outputConfiguration == null) {
            throw new java.lang.IllegalArgumentException("OutputConfiguration shouldn't be null");
        }
        this.mSurfaces = outputConfiguration.mSurfaces;
        this.mRotation = outputConfiguration.mRotation;
        this.mSurfaceGroupId = outputConfiguration.mSurfaceGroupId;
        this.mSurfaceType = outputConfiguration.mSurfaceType;
        this.mConfiguredDataspace = outputConfiguration.mConfiguredDataspace;
        this.mConfiguredFormat = outputConfiguration.mConfiguredFormat;
        this.mConfiguredSize = outputConfiguration.mConfiguredSize;
        this.mConfiguredGenerationId = outputConfiguration.mConfiguredGenerationId;
        this.mIsDeferredConfig = outputConfiguration.mIsDeferredConfig;
        this.mIsShared = outputConfiguration.mIsShared;
        this.mPhysicalCameraId = outputConfiguration.mPhysicalCameraId;
        this.mIsMultiResolution = outputConfiguration.mIsMultiResolution;
        this.mSensorPixelModesUsed = outputConfiguration.mSensorPixelModesUsed;
        this.mDynamicRangeProfile = outputConfiguration.mDynamicRangeProfile;
        this.mColorSpace = outputConfiguration.mColorSpace;
        this.mStreamUseCase = outputConfiguration.mStreamUseCase;
        this.mTimestampBase = outputConfiguration.mTimestampBase;
        this.mMirrorMode = outputConfiguration.mMirrorMode;
        this.mReadoutTimestampEnabled = outputConfiguration.mReadoutTimestampEnabled;
    }

    private OutputConfiguration(android.os.Parcel parcel) {
        boolean z;
        boolean z2;
        boolean z3;
        this.SURFACE_TYPE_UNKNOWN = -1;
        this.SURFACE_TYPE_SURFACE_VIEW = 0;
        this.SURFACE_TYPE_SURFACE_TEXTURE = 1;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        } else {
            z = true;
        }
        if (parcel.readInt() != 1) {
            z2 = false;
        } else {
            z2 = true;
        }
        java.util.ArrayList<android.view.Surface> arrayList = new java.util.ArrayList<>();
        parcel.readTypedList(arrayList, android.view.Surface.CREATOR);
        java.lang.String readString = parcel.readString();
        if (parcel.readInt() != 1) {
            z3 = false;
        } else {
            z3 = true;
        }
        int[] createIntArray = parcel.createIntArray();
        com.android.internal.util.Preconditions.checkArgumentInRange(readInt, 0, 3, "Rotation constant");
        long readLong = parcel.readLong();
        android.hardware.camera2.params.DynamicRangeProfiles.checkProfileValue(readLong);
        int readInt6 = parcel.readInt();
        long readLong2 = parcel.readLong();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        boolean z4 = parcel.readInt() == 1;
        this.mSurfaceGroupId = readInt2;
        this.mRotation = readInt;
        this.mSurfaces = arrayList;
        this.mConfiguredSize = new android.util.Size(readInt4, readInt5);
        this.mIsDeferredConfig = z;
        this.mIsShared = z2;
        this.mSurfaces = arrayList;
        if (this.mSurfaces.size() > 0) {
            this.mSurfaceType = -1;
            this.mConfiguredFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(this.mSurfaces.get(0));
            this.mConfiguredDataspace = android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(this.mSurfaces.get(0));
            this.mConfiguredGenerationId = this.mSurfaces.get(0).getGenerationId();
        } else {
            this.mSurfaceType = readInt3;
            this.mConfiguredFormat = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToInternal(34);
            this.mConfiguredDataspace = android.hardware.camera2.params.StreamConfigurationMap.imageFormatToDataspace(34);
            this.mConfiguredGenerationId = 0;
        }
        this.mPhysicalCameraId = readString;
        this.mIsMultiResolution = z3;
        this.mSensorPixelModesUsed = convertIntArrayToIntegerList(createIntArray);
        this.mDynamicRangeProfile = readLong;
        this.mColorSpace = readInt6;
        this.mStreamUseCase = readLong2;
        this.mTimestampBase = readInt7;
        this.mMirrorMode = readInt8;
        this.mReadoutTimestampEnabled = z4;
    }

    public int getMaxSharedSurfaceCount() {
        return 4;
    }

    public android.view.Surface getSurface() {
        if (this.mSurfaces.size() == 0) {
            return null;
        }
        return this.mSurfaces.get(0);
    }

    public java.util.List<android.view.Surface> getSurfaces() {
        return java.util.Collections.unmodifiableList(this.mSurfaces);
    }

    @android.annotation.SystemApi
    public int getRotation() {
        return this.mRotation;
    }

    public int getSurfaceGroupId() {
        return this.mSurfaceGroupId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static int[] convertIntegerToIntList(java.util.List<java.lang.Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    private static java.util.ArrayList<java.lang.Integer> convertIntArrayToIntegerList(int[] iArr) {
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        if (iArr == null) {
            return arrayList;
        }
        for (int i : iArr) {
            arrayList.add(java.lang.Integer.valueOf(i));
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("dest must not be null");
        }
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mSurfaceGroupId);
        parcel.writeInt(this.mSurfaceType);
        parcel.writeInt(this.mConfiguredSize.getWidth());
        parcel.writeInt(this.mConfiguredSize.getHeight());
        parcel.writeInt(this.mIsDeferredConfig ? 1 : 0);
        parcel.writeInt(this.mIsShared ? 1 : 0);
        parcel.writeTypedList(this.mSurfaces);
        parcel.writeString(this.mPhysicalCameraId);
        parcel.writeInt(this.mIsMultiResolution ? 1 : 0);
        parcel.writeIntArray(convertIntegerToIntList(this.mSensorPixelModesUsed));
        parcel.writeLong(this.mDynamicRangeProfile);
        parcel.writeInt(this.mColorSpace);
        parcel.writeLong(this.mStreamUseCase);
        parcel.writeInt(this.mTimestampBase);
        parcel.writeInt(this.mMirrorMode);
        parcel.writeInt(this.mReadoutTimestampEnabled ? 1 : 0);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.OutputConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.OutputConfiguration outputConfiguration = (android.hardware.camera2.params.OutputConfiguration) obj;
        if (this.mRotation != outputConfiguration.mRotation || !this.mConfiguredSize.equals(outputConfiguration.mConfiguredSize) || this.mConfiguredFormat != outputConfiguration.mConfiguredFormat || this.mSurfaceGroupId != outputConfiguration.mSurfaceGroupId || this.mSurfaceType != outputConfiguration.mSurfaceType || this.mIsDeferredConfig != outputConfiguration.mIsDeferredConfig || this.mIsShared != outputConfiguration.mIsShared || this.mConfiguredDataspace != outputConfiguration.mConfiguredDataspace || this.mConfiguredGenerationId != outputConfiguration.mConfiguredGenerationId || !java.util.Objects.equals(this.mPhysicalCameraId, outputConfiguration.mPhysicalCameraId) || this.mIsMultiResolution != outputConfiguration.mIsMultiResolution || this.mStreamUseCase != outputConfiguration.mStreamUseCase || this.mTimestampBase != outputConfiguration.mTimestampBase || this.mMirrorMode != outputConfiguration.mMirrorMode || this.mReadoutTimestampEnabled != outputConfiguration.mReadoutTimestampEnabled || this.mSensorPixelModesUsed.size() != outputConfiguration.mSensorPixelModesUsed.size()) {
            return false;
        }
        for (int i = 0; i < this.mSensorPixelModesUsed.size(); i++) {
            if (!java.util.Objects.equals(this.mSensorPixelModesUsed.get(i), outputConfiguration.mSensorPixelModesUsed.get(i))) {
                return false;
            }
        }
        int min = java.lang.Math.min(this.mSurfaces.size(), outputConfiguration.mSurfaces.size());
        for (int i2 = 0; i2 < min; i2++) {
            if (this.mSurfaces.get(i2) != outputConfiguration.mSurfaces.get(i2)) {
                return false;
            }
        }
        if ((!this.mIsDeferredConfig && this.mSurfaces.size() != outputConfiguration.mSurfaces.size()) || this.mDynamicRangeProfile != outputConfiguration.mDynamicRangeProfile || this.mColorSpace != outputConfiguration.mColorSpace) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.mIsDeferredConfig) {
            return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mRotation, this.mConfiguredSize.hashCode(), this.mConfiguredFormat, this.mConfiguredDataspace, this.mSurfaceGroupId, this.mSurfaceType, this.mIsShared ? 1.0f : 0.0f, this.mPhysicalCameraId == null ? 0.0f : this.mPhysicalCameraId.hashCode(), this.mIsMultiResolution ? 1.0f : 0.0f, this.mSensorPixelModesUsed.hashCode(), this.mDynamicRangeProfile, this.mColorSpace, this.mStreamUseCase, this.mTimestampBase, this.mMirrorMode, this.mReadoutTimestampEnabled ? 1.0f : 0.0f);
        }
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mRotation, this.mSurfaces.hashCode(), this.mConfiguredGenerationId, this.mConfiguredSize.hashCode(), this.mConfiguredFormat, this.mConfiguredDataspace, this.mSurfaceGroupId, this.mIsShared ? 1.0f : 0.0f, this.mPhysicalCameraId == null ? 0.0f : this.mPhysicalCameraId.hashCode(), this.mIsMultiResolution ? 1.0f : 0.0f, this.mSensorPixelModesUsed.hashCode(), this.mDynamicRangeProfile, this.mColorSpace, this.mStreamUseCase, this.mTimestampBase, this.mMirrorMode, this.mReadoutTimestampEnabled ? 1.0f : 0.0f);
    }
}
