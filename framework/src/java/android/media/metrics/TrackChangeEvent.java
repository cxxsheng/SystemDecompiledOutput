package android.media.metrics;

/* loaded from: classes2.dex */
public final class TrackChangeEvent extends android.media.metrics.Event implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.TrackChangeEvent> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.TrackChangeEvent>() { // from class: android.media.metrics.TrackChangeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.TrackChangeEvent[] newArray(int i) {
            return new android.media.metrics.TrackChangeEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.TrackChangeEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.TrackChangeEvent(parcel);
        }
    };
    public static final int TRACK_CHANGE_REASON_ADAPTIVE = 4;
    public static final int TRACK_CHANGE_REASON_INITIAL = 2;
    public static final int TRACK_CHANGE_REASON_MANUAL = 3;
    public static final int TRACK_CHANGE_REASON_OTHER = 1;
    public static final int TRACK_CHANGE_REASON_UNKNOWN = 0;
    public static final int TRACK_STATE_OFF = 0;
    public static final int TRACK_STATE_ON = 1;
    public static final int TRACK_TYPE_AUDIO = 0;
    public static final int TRACK_TYPE_TEXT = 2;
    public static final int TRACK_TYPE_VIDEO = 1;
    private final int mAudioSampleRate;
    private final int mBitrate;
    private final int mChannelCount;
    private final java.lang.String mCodecName;
    private final java.lang.String mContainerMimeType;
    private final int mHeight;
    private final java.lang.String mLanguage;
    private final java.lang.String mLanguageRegion;
    private final int mReason;
    private final java.lang.String mSampleMimeType;
    private final int mState;
    private final long mTimeSinceCreatedMillis;
    private final int mType;
    private final float mVideoFrameRate;
    private final int mWidth;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TrackChangeReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TrackState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TrackType {
    }

    private TrackChangeEvent(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3, long j, int i4, java.lang.String str4, java.lang.String str5, int i5, int i6, int i7, int i8, float f, android.os.Bundle bundle) {
        this.mState = i;
        this.mReason = i2;
        this.mContainerMimeType = str;
        this.mSampleMimeType = str2;
        this.mCodecName = str3;
        this.mBitrate = i3;
        this.mTimeSinceCreatedMillis = j;
        this.mType = i4;
        this.mLanguage = str4;
        this.mLanguageRegion = str5;
        this.mChannelCount = i5;
        this.mAudioSampleRate = i6;
        this.mWidth = i7;
        this.mHeight = i8;
        this.mVideoFrameRate = f;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public int getTrackState() {
        return this.mState;
    }

    public int getTrackChangeReason() {
        return this.mReason;
    }

    public java.lang.String getContainerMimeType() {
        return this.mContainerMimeType;
    }

    public java.lang.String getSampleMimeType() {
        return this.mSampleMimeType;
    }

    public java.lang.String getCodecName() {
        return this.mCodecName;
    }

    public int getBitrate() {
        return this.mBitrate;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public int getTrackType() {
        return this.mType;
    }

    public java.lang.String getLanguage() {
        return this.mLanguage;
    }

    public java.lang.String getLanguageRegion() {
        return this.mLanguageRegion;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getAudioSampleRate() {
        return this.mAudioSampleRate;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public float getVideoFrameRate() {
        return this.mVideoFrameRate;
    }

    @Override // android.media.metrics.Event
    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int i2 = this.mContainerMimeType != null ? 4 : 0;
        if (this.mSampleMimeType != null) {
            i2 |= 8;
        }
        if (this.mCodecName != null) {
            i2 |= 16;
        }
        if (this.mLanguage != null) {
            i2 |= 256;
        }
        if (this.mLanguageRegion != null) {
            i2 |= 512;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mReason);
        if (this.mContainerMimeType != null) {
            parcel.writeString(this.mContainerMimeType);
        }
        if (this.mSampleMimeType != null) {
            parcel.writeString(this.mSampleMimeType);
        }
        if (this.mCodecName != null) {
            parcel.writeString(this.mCodecName);
        }
        parcel.writeInt(this.mBitrate);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeInt(this.mType);
        if (this.mLanguage != null) {
            parcel.writeString(this.mLanguage);
        }
        if (this.mLanguageRegion != null) {
            parcel.writeString(this.mLanguageRegion);
        }
        parcel.writeInt(this.mChannelCount);
        parcel.writeInt(this.mAudioSampleRate);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TrackChangeEvent(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        java.lang.String readString = (readInt & 4) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readInt & 8) == 0 ? null : parcel.readString();
        java.lang.String readString3 = (readInt & 16) == 0 ? null : parcel.readString();
        int readInt4 = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt5 = parcel.readInt();
        java.lang.String readString4 = (readInt & 256) == 0 ? null : parcel.readString();
        java.lang.String readString5 = (readInt & 512) != 0 ? parcel.readString() : null;
        int readInt6 = parcel.readInt();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        int readInt9 = parcel.readInt();
        float readFloat = parcel.readFloat();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mState = readInt2;
        this.mReason = readInt3;
        this.mContainerMimeType = readString;
        this.mSampleMimeType = readString2;
        this.mCodecName = readString3;
        this.mBitrate = readInt4;
        this.mTimeSinceCreatedMillis = readLong;
        this.mType = readInt5;
        this.mLanguage = readString4;
        this.mLanguageRegion = readString5;
        this.mChannelCount = readInt6;
        this.mAudioSampleRate = readInt7;
        this.mWidth = readInt8;
        this.mHeight = readInt9;
        this.mVideoFrameRate = readFloat;
        this.mMetricsBundle = readBundle;
    }

    public java.lang.String toString() {
        return "TrackChangeEvent { state = " + this.mState + ", reason = " + this.mReason + ", containerMimeType = " + this.mContainerMimeType + ", sampleMimeType = " + this.mSampleMimeType + ", codecName = " + this.mCodecName + ", bitrate = " + this.mBitrate + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + ", type = " + this.mType + ", language = " + this.mLanguage + ", languageRegion = " + this.mLanguageRegion + ", channelCount = " + this.mChannelCount + ", sampleRate = " + this.mAudioSampleRate + ", width = " + this.mWidth + ", height = " + this.mHeight + ", videoFrameRate = " + this.mVideoFrameRate + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.TrackChangeEvent trackChangeEvent = (android.media.metrics.TrackChangeEvent) obj;
        if (this.mState == trackChangeEvent.mState && this.mReason == trackChangeEvent.mReason && java.util.Objects.equals(this.mContainerMimeType, trackChangeEvent.mContainerMimeType) && java.util.Objects.equals(this.mSampleMimeType, trackChangeEvent.mSampleMimeType) && java.util.Objects.equals(this.mCodecName, trackChangeEvent.mCodecName) && this.mBitrate == trackChangeEvent.mBitrate && this.mTimeSinceCreatedMillis == trackChangeEvent.mTimeSinceCreatedMillis && this.mType == trackChangeEvent.mType && java.util.Objects.equals(this.mLanguage, trackChangeEvent.mLanguage) && java.util.Objects.equals(this.mLanguageRegion, trackChangeEvent.mLanguageRegion) && this.mChannelCount == trackChangeEvent.mChannelCount && this.mAudioSampleRate == trackChangeEvent.mAudioSampleRate && this.mWidth == trackChangeEvent.mWidth && this.mHeight == trackChangeEvent.mHeight && this.mVideoFrameRate == trackChangeEvent.mVideoFrameRate) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mState), java.lang.Integer.valueOf(this.mReason), this.mContainerMimeType, this.mSampleMimeType, this.mCodecName, java.lang.Integer.valueOf(this.mBitrate), java.lang.Long.valueOf(this.mTimeSinceCreatedMillis), java.lang.Integer.valueOf(this.mType), this.mLanguage, this.mLanguageRegion, java.lang.Integer.valueOf(this.mChannelCount), java.lang.Integer.valueOf(this.mAudioSampleRate), java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Float.valueOf(this.mVideoFrameRate));
    }

    public static final class Builder {
        private java.lang.String mCodecName;
        private java.lang.String mContainerMimeType;
        private java.lang.String mLanguage;
        private java.lang.String mLanguageRegion;
        private java.lang.String mSampleMimeType;
        private final int mType;
        private int mState = 0;
        private int mReason = 0;
        private int mBitrate = -1;
        private long mTimeSinceCreatedMillis = -1;
        private int mChannelCount = -1;
        private int mAudioSampleRate = -1;
        private int mWidth = -1;
        private int mHeight = -1;
        private float mVideoFrameRate = -1.0f;
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();
        private long mBuilderFieldsSet = 0;

        public Builder(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new java.lang.IllegalArgumentException("track type must be one of TRACK_TYPE_AUDIO, TRACK_TYPE_VIDEO, TRACK_TYPE_TEXT.");
            }
            this.mType = i;
        }

        public android.media.metrics.TrackChangeEvent.Builder setTrackState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mState = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setTrackChangeReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mReason = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setContainerMimeType(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mContainerMimeType = str;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setSampleMimeType(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mSampleMimeType = str;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setCodecName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mCodecName = str;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setBitrate(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mBitrate = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setTimeSinceCreatedMillis(long j) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setLanguage(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            this.mLanguage = str;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setLanguageRegion(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 512;
            this.mLanguageRegion = str;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setChannelCount(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1024;
            this.mChannelCount = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setAudioSampleRate(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2048;
            this.mAudioSampleRate = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setWidth(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4096;
            this.mWidth = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setHeight(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8192;
            this.mHeight = i;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setVideoFrameRate(float f) {
            checkNotUsed();
            this.mVideoFrameRate = f;
            return this;
        }

        public android.media.metrics.TrackChangeEvent.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public android.media.metrics.TrackChangeEvent build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16384;
            return new android.media.metrics.TrackChangeEvent(this.mState, this.mReason, this.mContainerMimeType, this.mSampleMimeType, this.mCodecName, this.mBitrate, this.mTimeSinceCreatedMillis, this.mType, this.mLanguage, this.mLanguageRegion, this.mChannelCount, this.mAudioSampleRate, this.mWidth, this.mHeight, this.mVideoFrameRate, this.mMetricsBundle);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16384) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
