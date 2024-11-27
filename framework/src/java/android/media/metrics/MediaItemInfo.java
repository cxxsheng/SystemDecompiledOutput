package android.media.metrics;

/* loaded from: classes2.dex */
public final class MediaItemInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.MediaItemInfo> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.MediaItemInfo>() { // from class: android.media.metrics.MediaItemInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.MediaItemInfo[] newArray(int i) {
            return new android.media.metrics.MediaItemInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.MediaItemInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.MediaItemInfo(parcel);
        }
    };
    public static final long DATA_TYPE_AUDIO = 4;
    public static final long DATA_TYPE_DEPTH = 16;
    public static final long DATA_TYPE_GAIN_MAP = 32;
    public static final long DATA_TYPE_GAPLESS = 256;
    public static final long DATA_TYPE_HIGH_DYNAMIC_RANGE_VIDEO = 1024;
    public static final long DATA_TYPE_HIGH_FRAME_RATE = 64;
    public static final long DATA_TYPE_IMAGE = 1;
    public static final long DATA_TYPE_METADATA = 8;
    public static final long DATA_TYPE_SPATIAL_AUDIO = 512;
    public static final long DATA_TYPE_SPEED_SETTING_CUE_POINTS = 128;
    public static final long DATA_TYPE_VIDEO = 2;
    public static final int SOURCE_TYPE_CAMERA = 2;
    public static final int SOURCE_TYPE_EDITING_SESSION = 3;
    public static final int SOURCE_TYPE_GALLERY = 1;
    public static final int SOURCE_TYPE_GENERATED = 7;
    public static final int SOURCE_TYPE_LOCAL_FILE = 4;
    public static final int SOURCE_TYPE_REMOTE_FILE = 5;
    public static final int SOURCE_TYPE_REMOTE_LIVE_STREAM = 6;
    public static final int SOURCE_TYPE_UNSPECIFIED = 0;
    public static final int VALUE_UNSPECIFIED = -1;
    private final int mAudioChannelCount;
    private final long mAudioSampleCount;
    private final int mAudioSampleRateHz;
    private final long mClipDurationMillis;
    private final java.util.List<java.lang.String> mCodecNames;
    private final java.lang.String mContainerMimeType;
    private final long mDataTypes;
    private final long mDurationMillis;
    private final java.util.List<java.lang.String> mSampleMimeTypes;
    private final int mSourceType;
    private final int mVideoDataSpace;
    private final float mVideoFrameRate;
    private final long mVideoSampleCount;
    private final android.util.Size mVideoSize;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SourceType {
    }

    private MediaItemInfo(int i, long j, long j2, long j3, java.lang.String str, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i2, int i3, long j4, android.util.Size size, int i4, float f, long j5) {
        this.mSourceType = i;
        this.mDataTypes = j;
        this.mDurationMillis = j2;
        this.mClipDurationMillis = j3;
        this.mContainerMimeType = str;
        this.mSampleMimeTypes = list;
        this.mCodecNames = list2;
        this.mAudioSampleRateHz = i2;
        this.mAudioChannelCount = i3;
        this.mAudioSampleCount = j4;
        this.mVideoSize = size;
        this.mVideoDataSpace = i4;
        this.mVideoFrameRate = f;
        this.mVideoSampleCount = j5;
    }

    public int getSourceType() {
        return this.mSourceType;
    }

    public long getDataTypes() {
        return this.mDataTypes;
    }

    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    public long getClipDurationMillis() {
        return this.mClipDurationMillis;
    }

    public java.lang.String getContainerMimeType() {
        return this.mContainerMimeType;
    }

    public java.util.List<java.lang.String> getSampleMimeTypes() {
        return new java.util.ArrayList(this.mSampleMimeTypes);
    }

    public java.util.List<java.lang.String> getCodecNames() {
        return new java.util.ArrayList(this.mCodecNames);
    }

    public int getAudioSampleRateHz() {
        return this.mAudioSampleRateHz;
    }

    public int getAudioChannelCount() {
        return this.mAudioChannelCount;
    }

    public long getAudioSampleCount() {
        return this.mAudioSampleCount;
    }

    public android.util.Size getVideoSize() {
        return this.mVideoSize;
    }

    public int getVideoDataSpace() {
        return this.mVideoDataSpace;
    }

    public float getVideoFrameRate() {
        return this.mVideoFrameRate;
    }

    public long getVideoSampleCount() {
        return this.mVideoSampleCount;
    }

    public static final class Builder {
        private java.lang.String mContainerMimeType;
        private long mDataTypes;
        private int mVideoDataSpace;
        private int mSourceType = 0;
        private long mDurationMillis = -1;
        private long mClipDurationMillis = -1;
        private final java.util.ArrayList<java.lang.String> mSampleMimeTypes = new java.util.ArrayList<>();
        private final java.util.ArrayList<java.lang.String> mCodecNames = new java.util.ArrayList<>();
        private int mAudioSampleRateHz = -1;
        private int mAudioChannelCount = -1;
        private long mAudioSampleCount = -1;
        private android.util.Size mVideoSize = new android.util.Size(-1, -1);
        private float mVideoFrameRate = -1.0f;
        private long mVideoSampleCount = -1;

        public android.media.metrics.MediaItemInfo.Builder setSourceType(int i) {
            this.mSourceType = i;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder addDataType(long j) {
            this.mDataTypes = j | this.mDataTypes;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setDurationMillis(long j) {
            this.mDurationMillis = j;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setClipDurationMillis(long j) {
            this.mClipDurationMillis = j;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setContainerMimeType(java.lang.String str) {
            this.mContainerMimeType = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder addSampleMimeType(java.lang.String str) {
            this.mSampleMimeTypes.add((java.lang.String) java.util.Objects.requireNonNull(str));
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder addCodecName(java.lang.String str) {
            this.mCodecNames.add((java.lang.String) java.util.Objects.requireNonNull(str));
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setAudioSampleRateHz(int i) {
            this.mAudioSampleRateHz = i;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setAudioChannelCount(int i) {
            this.mAudioChannelCount = i;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setAudioSampleCount(long j) {
            this.mAudioSampleCount = j;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setVideoSize(android.util.Size size) {
            this.mVideoSize = (android.util.Size) java.util.Objects.requireNonNull(size);
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setVideoDataSpace(int i) {
            this.mVideoDataSpace = i;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setVideoFrameRate(float f) {
            this.mVideoFrameRate = f;
            return this;
        }

        public android.media.metrics.MediaItemInfo.Builder setVideoSampleCount(long j) {
            this.mVideoSampleCount = j;
            return this;
        }

        public android.media.metrics.MediaItemInfo build() {
            return new android.media.metrics.MediaItemInfo(this.mSourceType, this.mDataTypes, this.mDurationMillis, this.mClipDurationMillis, this.mContainerMimeType, this.mSampleMimeTypes, this.mCodecNames, this.mAudioSampleRateHz, this.mAudioChannelCount, this.mAudioSampleCount, this.mVideoSize, this.mVideoDataSpace, this.mVideoFrameRate, this.mVideoSampleCount);
        }
    }

    public java.lang.String toString() {
        return "MediaItemInfo { sourceType = " + this.mSourceType + ", dataTypes = " + this.mDataTypes + ", durationMillis = " + this.mDurationMillis + ", clipDurationMillis = " + this.mClipDurationMillis + ", containerMimeType = " + this.mContainerMimeType + ", sampleMimeTypes = " + this.mSampleMimeTypes + ", codecNames = " + this.mCodecNames + ", audioSampleRateHz = " + this.mAudioSampleRateHz + ", audioChannelCount = " + this.mAudioChannelCount + ", audioSampleCount = " + this.mAudioSampleCount + ", videoSize = " + this.mVideoSize + ", videoDataSpace = " + this.mVideoDataSpace + ", videoFrameRate = " + this.mVideoFrameRate + ", videoSampleCount = " + this.mVideoSampleCount + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.MediaItemInfo mediaItemInfo = (android.media.metrics.MediaItemInfo) obj;
        if (this.mSourceType == mediaItemInfo.mSourceType && this.mDataTypes == mediaItemInfo.mDataTypes && this.mDurationMillis == mediaItemInfo.mDurationMillis && this.mClipDurationMillis == mediaItemInfo.mClipDurationMillis && java.util.Objects.equals(this.mContainerMimeType, mediaItemInfo.mContainerMimeType) && this.mSampleMimeTypes.equals(mediaItemInfo.mSampleMimeTypes) && this.mCodecNames.equals(mediaItemInfo.mCodecNames) && this.mAudioSampleRateHz == mediaItemInfo.mAudioSampleRateHz && this.mAudioChannelCount == mediaItemInfo.mAudioChannelCount && this.mAudioSampleCount == mediaItemInfo.mAudioSampleCount && java.util.Objects.equals(this.mVideoSize, mediaItemInfo.mVideoSize) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mVideoDataSpace), java.lang.Integer.valueOf(mediaItemInfo.mVideoDataSpace)) && this.mVideoFrameRate == mediaItemInfo.mVideoFrameRate && this.mVideoSampleCount == mediaItemInfo.mVideoSampleCount) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSourceType), java.lang.Long.valueOf(this.mDataTypes));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSourceType);
        parcel.writeLong(this.mDataTypes);
        parcel.writeLong(this.mDurationMillis);
        parcel.writeLong(this.mClipDurationMillis);
        parcel.writeString(this.mContainerMimeType);
        parcel.writeStringList(this.mSampleMimeTypes);
        parcel.writeStringList(this.mCodecNames);
        parcel.writeInt(this.mAudioSampleRateHz);
        parcel.writeInt(this.mAudioChannelCount);
        parcel.writeLong(this.mAudioSampleCount);
        parcel.writeInt(this.mVideoSize.getWidth());
        parcel.writeInt(this.mVideoSize.getHeight());
        parcel.writeInt(this.mVideoDataSpace);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeLong(this.mVideoSampleCount);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MediaItemInfo(android.os.Parcel parcel) {
        this.mSourceType = parcel.readInt();
        this.mDataTypes = parcel.readLong();
        this.mDurationMillis = parcel.readLong();
        this.mClipDurationMillis = parcel.readLong();
        this.mContainerMimeType = parcel.readString();
        this.mSampleMimeTypes = new java.util.ArrayList();
        parcel.readStringList(this.mSampleMimeTypes);
        this.mCodecNames = new java.util.ArrayList();
        parcel.readStringList(this.mCodecNames);
        this.mAudioSampleRateHz = parcel.readInt();
        this.mAudioChannelCount = parcel.readInt();
        this.mAudioSampleCount = parcel.readLong();
        this.mVideoSize = new android.util.Size(parcel.readInt(), parcel.readInt());
        this.mVideoDataSpace = parcel.readInt();
        this.mVideoFrameRate = parcel.readFloat();
        this.mVideoSampleCount = parcel.readLong();
    }
}
