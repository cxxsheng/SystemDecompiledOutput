package android.hardware;

/* loaded from: classes.dex */
public class CameraSessionStats implements android.os.Parcelable {
    public static final int CAMERA_API_LEVEL_1 = 1;
    public static final int CAMERA_API_LEVEL_2 = 2;
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_EXTERNAL = 2;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_STATE_ACTIVE = 1;
    public static final int CAMERA_STATE_CLOSED = 3;
    public static final int CAMERA_STATE_IDLE = 2;
    public static final int CAMERA_STATE_OPEN = 0;
    public static final android.os.Parcelable.Creator<android.hardware.CameraSessionStats> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraSessionStats>() { // from class: android.hardware.CameraSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraSessionStats createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.CameraSessionStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraSessionStats[] newArray(int i) {
            return new android.hardware.CameraSessionStats[i];
        }
    };
    private int mApiLevel;
    private android.hardware.CameraExtensionSessionStats mCameraExtensionSessionStats;
    private java.lang.String mCameraId;
    private java.lang.String mClientName;
    private boolean mDeviceError;
    private int mFacing;
    private int mInternalReconfigure;
    private boolean mIsNdk;
    private int mLatencyMs;
    private long mLogId;
    private float mMaxPreviewFps;
    private int mNewCameraState;
    private long mRequestCount;
    private long mResultErrorCount;
    private int mSessionIndex;
    private int mSessionType;
    private java.util.ArrayList<android.hardware.CameraStreamStats> mStreamStats;
    private boolean mUsedUltraWide;
    private boolean mUsedZoomOverride;
    private java.lang.String mUserTag;
    private int mVideoStabilizationMode;

    public CameraSessionStats() {
        this.mFacing = -1;
        this.mNewCameraState = -1;
        this.mApiLevel = -1;
        this.mIsNdk = false;
        this.mLatencyMs = -1;
        this.mLogId = 0L;
        this.mMaxPreviewFps = 0.0f;
        this.mSessionType = -1;
        this.mInternalReconfigure = -1;
        this.mRequestCount = 0L;
        this.mResultErrorCount = 0L;
        this.mDeviceError = false;
        this.mStreamStats = new java.util.ArrayList<>();
        this.mVideoStabilizationMode = -1;
        this.mUsedUltraWide = false;
        this.mUsedZoomOverride = false;
        this.mSessionIndex = 0;
        this.mCameraExtensionSessionStats = new android.hardware.CameraExtensionSessionStats();
    }

    public CameraSessionStats(java.lang.String str, int i, int i2, java.lang.String str2, int i3, boolean z, int i4, float f, int i5, int i6, long j, int i7) {
        this.mCameraId = str;
        this.mFacing = i;
        this.mNewCameraState = i2;
        this.mClientName = str2;
        this.mApiLevel = i3;
        this.mIsNdk = z;
        this.mLatencyMs = i4;
        this.mLogId = j;
        this.mMaxPreviewFps = f;
        this.mSessionType = i5;
        this.mInternalReconfigure = i6;
        this.mStreamStats = new java.util.ArrayList<>();
        this.mVideoStabilizationMode = -1;
        this.mUsedUltraWide = false;
        this.mUsedZoomOverride = false;
        this.mSessionIndex = i7;
        this.mCameraExtensionSessionStats = new android.hardware.CameraExtensionSessionStats();
    }

    private CameraSessionStats(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCameraId);
        parcel.writeInt(this.mFacing);
        parcel.writeInt(this.mNewCameraState);
        parcel.writeString(this.mClientName);
        parcel.writeInt(this.mApiLevel);
        parcel.writeBoolean(this.mIsNdk);
        parcel.writeInt(this.mLatencyMs);
        parcel.writeLong(this.mLogId);
        parcel.writeFloat(this.mMaxPreviewFps);
        parcel.writeInt(this.mSessionType);
        parcel.writeInt(this.mInternalReconfigure);
        parcel.writeLong(this.mRequestCount);
        parcel.writeLong(this.mResultErrorCount);
        parcel.writeBoolean(this.mDeviceError);
        parcel.writeTypedList(this.mStreamStats);
        parcel.writeString(this.mUserTag);
        parcel.writeInt(this.mVideoStabilizationMode);
        parcel.writeBoolean(this.mUsedUltraWide);
        parcel.writeBoolean(this.mUsedZoomOverride);
        parcel.writeInt(this.mSessionIndex);
        this.mCameraExtensionSessionStats.writeToParcel(parcel, 0);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mCameraId = parcel.readString();
        this.mFacing = parcel.readInt();
        this.mNewCameraState = parcel.readInt();
        this.mClientName = parcel.readString();
        this.mApiLevel = parcel.readInt();
        this.mIsNdk = parcel.readBoolean();
        this.mLatencyMs = parcel.readInt();
        this.mLogId = parcel.readLong();
        this.mMaxPreviewFps = parcel.readFloat();
        this.mSessionType = parcel.readInt();
        this.mInternalReconfigure = parcel.readInt();
        this.mRequestCount = parcel.readLong();
        this.mResultErrorCount = parcel.readLong();
        this.mDeviceError = parcel.readBoolean();
        java.util.ArrayList<android.hardware.CameraStreamStats> arrayList = new java.util.ArrayList<>();
        parcel.readTypedList(arrayList, android.hardware.CameraStreamStats.CREATOR);
        this.mStreamStats = arrayList;
        this.mUserTag = parcel.readString();
        this.mVideoStabilizationMode = parcel.readInt();
        this.mUsedUltraWide = parcel.readBoolean();
        this.mUsedZoomOverride = parcel.readBoolean();
        this.mSessionIndex = parcel.readInt();
        this.mCameraExtensionSessionStats = android.hardware.CameraExtensionSessionStats.CREATOR.createFromParcel(parcel);
    }

    public java.lang.String getCameraId() {
        return this.mCameraId;
    }

    public int getFacing() {
        return this.mFacing;
    }

    public int getNewCameraState() {
        return this.mNewCameraState;
    }

    public java.lang.String getClientName() {
        return this.mClientName;
    }

    public int getApiLevel() {
        return this.mApiLevel;
    }

    public boolean isNdk() {
        return this.mIsNdk;
    }

    public int getLatencyMs() {
        return this.mLatencyMs;
    }

    public long getLogId() {
        return this.mLogId;
    }

    public float getMaxPreviewFps() {
        return this.mMaxPreviewFps;
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public int getInternalReconfigureCount() {
        return this.mInternalReconfigure;
    }

    public long getRequestCount() {
        return this.mRequestCount;
    }

    public long getResultErrorCount() {
        return this.mResultErrorCount;
    }

    public boolean getDeviceErrorFlag() {
        return this.mDeviceError;
    }

    public java.util.List<android.hardware.CameraStreamStats> getStreamStats() {
        return this.mStreamStats;
    }

    public java.lang.String getUserTag() {
        return this.mUserTag;
    }

    public int getVideoStabilizationMode() {
        return this.mVideoStabilizationMode;
    }

    public boolean getUsedUltraWide() {
        return this.mUsedUltraWide;
    }

    public boolean getUsedZoomOverride() {
        return this.mUsedZoomOverride;
    }

    public int getSessionIndex() {
        return this.mSessionIndex;
    }

    public android.hardware.CameraExtensionSessionStats getExtensionSessionStats() {
        return this.mCameraExtensionSessionStats;
    }
}
