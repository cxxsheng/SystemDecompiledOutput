package android.media.metrics;

/* loaded from: classes2.dex */
public final class PlaybackMetrics implements android.os.Parcelable {
    public static final int CONTENT_TYPE_AD = 2;
    public static final int CONTENT_TYPE_MAIN = 1;
    public static final int CONTENT_TYPE_OTHER = 3;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.media.metrics.PlaybackMetrics> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.PlaybackMetrics>() { // from class: android.media.metrics.PlaybackMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackMetrics[] newArray(int i) {
            return new android.media.metrics.PlaybackMetrics[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackMetrics createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.PlaybackMetrics(parcel);
        }
    };
    public static final int DRM_TYPE_CLEARKEY = 6;
    public static final int DRM_TYPE_NONE = 0;
    public static final int DRM_TYPE_OTHER = 1;
    public static final int DRM_TYPE_PLAY_READY = 2;
    public static final int DRM_TYPE_WIDEVINE_L1 = 3;
    public static final int DRM_TYPE_WIDEVINE_L3 = 4;
    public static final int DRM_TYPE_WV_L3_FALLBACK = 5;
    public static final int PLAYBACK_TYPE_LIVE = 2;
    public static final int PLAYBACK_TYPE_OTHER = 3;
    public static final int PLAYBACK_TYPE_UNKNOWN = 0;
    public static final int PLAYBACK_TYPE_VOD = 1;
    public static final int STREAM_SOURCE_DEVICE = 2;
    public static final int STREAM_SOURCE_MIXED = 3;
    public static final int STREAM_SOURCE_NETWORK = 1;
    public static final int STREAM_SOURCE_UNKNOWN = 0;
    public static final int STREAM_TYPE_DASH = 3;
    public static final int STREAM_TYPE_HLS = 4;
    public static final int STREAM_TYPE_OTHER = 1;
    public static final int STREAM_TYPE_PROGRESSIVE = 2;
    public static final int STREAM_TYPE_SS = 5;
    public static final int STREAM_TYPE_UNKNOWN = 0;
    private final int mAudioUnderrunCount;
    private final int mContentType;
    private final byte[] mDrmSessionId;
    private final int mDrmType;
    private final long[] mExperimentIds;
    private final long mLocalBytesRead;
    private final long mMediaDurationMillis;
    private final android.os.Bundle mMetricsBundle;
    private final long mNetworkBytesRead;
    private final long mNetworkTransferDurationMillis;
    private final int mPlaybackType;
    private final java.lang.String mPlayerName;
    private final java.lang.String mPlayerVersion;
    private final int mStreamSource;
    private final int mStreamType;
    private final int mVideoFramesDropped;
    private final int mVideoFramesPlayed;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DrmType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlaybackType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamType {
    }

    public PlaybackMetrics(long j, int i, int i2, int i3, int i4, int i5, java.lang.String str, java.lang.String str2, long[] jArr, int i6, int i7, int i8, long j2, long j3, long j4, byte[] bArr, android.os.Bundle bundle) {
        this.mMediaDurationMillis = j;
        this.mStreamSource = i;
        this.mStreamType = i2;
        this.mPlaybackType = i3;
        this.mDrmType = i4;
        this.mContentType = i5;
        this.mPlayerName = str;
        this.mPlayerVersion = str2;
        this.mExperimentIds = jArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExperimentIds);
        this.mVideoFramesPlayed = i6;
        this.mVideoFramesDropped = i7;
        this.mAudioUnderrunCount = i8;
        this.mNetworkBytesRead = j2;
        this.mLocalBytesRead = j3;
        this.mNetworkTransferDurationMillis = j4;
        this.mDrmSessionId = bArr;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public long getMediaDurationMillis() {
        return this.mMediaDurationMillis;
    }

    public int getStreamSource() {
        return this.mStreamSource;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public int getPlaybackType() {
        return this.mPlaybackType;
    }

    public int getDrmType() {
        return this.mDrmType;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public java.lang.String getPlayerName() {
        return this.mPlayerName;
    }

    public java.lang.String getPlayerVersion() {
        return this.mPlayerVersion;
    }

    public long[] getExperimentIds() {
        return java.util.Arrays.copyOf(this.mExperimentIds, this.mExperimentIds.length);
    }

    public int getVideoFramesPlayed() {
        return this.mVideoFramesPlayed;
    }

    public int getVideoFramesDropped() {
        return this.mVideoFramesDropped;
    }

    public int getAudioUnderrunCount() {
        return this.mAudioUnderrunCount;
    }

    public long getNetworkBytesRead() {
        return this.mNetworkBytesRead;
    }

    public long getLocalBytesRead() {
        return this.mLocalBytesRead;
    }

    public long getNetworkTransferDurationMillis() {
        return this.mNetworkTransferDurationMillis;
    }

    public byte[] getDrmSessionId() {
        return this.mDrmSessionId;
    }

    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public java.lang.String toString() {
        return "PlaybackMetrics { mediaDurationMillis = " + this.mMediaDurationMillis + ", streamSource = " + this.mStreamSource + ", streamType = " + this.mStreamType + ", playbackType = " + this.mPlaybackType + ", drmType = " + this.mDrmType + ", contentType = " + this.mContentType + ", playerName = " + this.mPlayerName + ", playerVersion = " + this.mPlayerVersion + ", experimentIds = " + java.util.Arrays.toString(this.mExperimentIds) + ", videoFramesPlayed = " + this.mVideoFramesPlayed + ", videoFramesDropped = " + this.mVideoFramesDropped + ", audioUnderrunCount = " + this.mAudioUnderrunCount + ", networkBytesRead = " + this.mNetworkBytesRead + ", localBytesRead = " + this.mLocalBytesRead + ", networkTransferDurationMillis = " + this.mNetworkTransferDurationMillis + "drmSessionId = " + java.util.Arrays.toString(this.mDrmSessionId) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.PlaybackMetrics playbackMetrics = (android.media.metrics.PlaybackMetrics) obj;
        if (this.mMediaDurationMillis == playbackMetrics.mMediaDurationMillis && this.mStreamSource == playbackMetrics.mStreamSource && this.mStreamType == playbackMetrics.mStreamType && this.mPlaybackType == playbackMetrics.mPlaybackType && this.mDrmType == playbackMetrics.mDrmType && this.mContentType == playbackMetrics.mContentType && java.util.Objects.equals(this.mPlayerName, playbackMetrics.mPlayerName) && java.util.Objects.equals(this.mPlayerVersion, playbackMetrics.mPlayerVersion) && java.util.Arrays.equals(this.mExperimentIds, playbackMetrics.mExperimentIds) && this.mVideoFramesPlayed == playbackMetrics.mVideoFramesPlayed && this.mVideoFramesDropped == playbackMetrics.mVideoFramesDropped && this.mAudioUnderrunCount == playbackMetrics.mAudioUnderrunCount && this.mNetworkBytesRead == playbackMetrics.mNetworkBytesRead && this.mLocalBytesRead == playbackMetrics.mLocalBytesRead && this.mNetworkTransferDurationMillis == playbackMetrics.mNetworkTransferDurationMillis && java.util.Arrays.equals(this.mDrmSessionId, playbackMetrics.mDrmSessionId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mMediaDurationMillis), java.lang.Integer.valueOf(this.mStreamSource), java.lang.Integer.valueOf(this.mStreamType), java.lang.Integer.valueOf(this.mPlaybackType), java.lang.Integer.valueOf(this.mDrmType), java.lang.Integer.valueOf(this.mContentType), this.mPlayerName, this.mPlayerVersion, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mExperimentIds)), java.lang.Integer.valueOf(this.mVideoFramesPlayed), java.lang.Integer.valueOf(this.mVideoFramesDropped), java.lang.Integer.valueOf(this.mAudioUnderrunCount), java.lang.Long.valueOf(this.mNetworkBytesRead), java.lang.Long.valueOf(this.mLocalBytesRead), java.lang.Long.valueOf(this.mNetworkTransferDurationMillis), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mDrmSessionId)));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        long j = this.mPlayerName != null ? 128L : 0L;
        if (this.mPlayerVersion != null) {
            j |= 256;
        }
        parcel.writeLong(j);
        parcel.writeLong(this.mMediaDurationMillis);
        parcel.writeInt(this.mStreamSource);
        parcel.writeInt(this.mStreamType);
        parcel.writeInt(this.mPlaybackType);
        parcel.writeInt(this.mDrmType);
        parcel.writeInt(this.mContentType);
        if (this.mPlayerName != null) {
            parcel.writeString(this.mPlayerName);
        }
        if (this.mPlayerVersion != null) {
            parcel.writeString(this.mPlayerVersion);
        }
        parcel.writeLongArray(this.mExperimentIds);
        parcel.writeInt(this.mVideoFramesPlayed);
        parcel.writeInt(this.mVideoFramesDropped);
        parcel.writeInt(this.mAudioUnderrunCount);
        parcel.writeLong(this.mNetworkBytesRead);
        parcel.writeLong(this.mLocalBytesRead);
        parcel.writeLong(this.mNetworkTransferDurationMillis);
        parcel.writeInt(this.mDrmSessionId.length);
        parcel.writeByteArray(this.mDrmSessionId);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    PlaybackMetrics(android.os.Parcel parcel) {
        long readLong = parcel.readLong();
        long readLong2 = parcel.readLong();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        java.lang.String readString = (128 & readLong) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readLong & 256) == 0 ? null : parcel.readString();
        long[] createLongArray = parcel.createLongArray();
        int readInt6 = parcel.readInt();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        long readLong3 = parcel.readLong();
        long readLong4 = parcel.readLong();
        long readLong5 = parcel.readLong();
        byte[] bArr = new byte[parcel.readInt()];
        parcel.readByteArray(bArr);
        android.os.Bundle readBundle = parcel.readBundle();
        this.mMediaDurationMillis = readLong2;
        this.mStreamSource = readInt;
        this.mStreamType = readInt2;
        this.mPlaybackType = readInt3;
        this.mDrmType = readInt4;
        this.mContentType = readInt5;
        this.mPlayerName = readString;
        this.mPlayerVersion = readString2;
        this.mExperimentIds = createLongArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExperimentIds);
        this.mVideoFramesPlayed = readInt6;
        this.mVideoFramesDropped = readInt7;
        this.mAudioUnderrunCount = readInt8;
        this.mNetworkBytesRead = readLong3;
        this.mLocalBytesRead = readLong4;
        this.mNetworkTransferDurationMillis = readLong5;
        this.mDrmSessionId = bArr;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private java.lang.String mPlayerName;
        private java.lang.String mPlayerVersion;
        private long mMediaDurationMillis = -1;
        private int mStreamSource = 0;
        private int mStreamType = 0;
        private int mPlaybackType = 0;
        private int mDrmType = 0;
        private int mContentType = 0;
        private java.util.List<java.lang.Long> mExperimentIds = new java.util.ArrayList();
        private int mVideoFramesPlayed = -1;
        private int mVideoFramesDropped = -1;
        private int mAudioUnderrunCount = -1;
        private long mNetworkBytesRead = -1;
        private long mLocalBytesRead = -1;
        private long mNetworkTransferDurationMillis = -1;
        private byte[] mDrmSessionId = new byte[0];
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();

        public android.media.metrics.PlaybackMetrics.Builder setMediaDurationMillis(long j) {
            this.mMediaDurationMillis = j;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setStreamSource(int i) {
            this.mStreamSource = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setStreamType(int i) {
            this.mStreamType = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setPlaybackType(int i) {
            this.mPlaybackType = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setDrmType(int i) {
            this.mDrmType = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setContentType(int i) {
            this.mContentType = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setPlayerName(java.lang.String str) {
            this.mPlayerName = str;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setPlayerVersion(java.lang.String str) {
            this.mPlayerVersion = str;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder addExperimentId(long j) {
            this.mExperimentIds.add(java.lang.Long.valueOf(j));
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setVideoFramesPlayed(int i) {
            this.mVideoFramesPlayed = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setVideoFramesDropped(int i) {
            this.mVideoFramesDropped = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setAudioUnderrunCount(int i) {
            this.mAudioUnderrunCount = i;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setNetworkBytesRead(long j) {
            this.mNetworkBytesRead = j;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setLocalBytesRead(long j) {
            this.mLocalBytesRead = j;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setNetworkTransferDurationMillis(long j) {
            this.mNetworkTransferDurationMillis = j;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setDrmSessionId(byte[] bArr) {
            this.mDrmSessionId = bArr;
            return this;
        }

        public android.media.metrics.PlaybackMetrics.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public android.media.metrics.PlaybackMetrics build() {
            return new android.media.metrics.PlaybackMetrics(this.mMediaDurationMillis, this.mStreamSource, this.mStreamType, this.mPlaybackType, this.mDrmType, this.mContentType, this.mPlayerName, this.mPlayerVersion, idsToLongArray(), this.mVideoFramesPlayed, this.mVideoFramesDropped, this.mAudioUnderrunCount, this.mNetworkBytesRead, this.mLocalBytesRead, this.mNetworkTransferDurationMillis, this.mDrmSessionId, this.mMetricsBundle);
        }

        private long[] idsToLongArray() {
            long[] jArr = new long[this.mExperimentIds.size()];
            for (int i = 0; i < this.mExperimentIds.size(); i++) {
                jArr[i] = this.mExperimentIds.get(i).longValue();
            }
            return jArr;
        }
    }
}
