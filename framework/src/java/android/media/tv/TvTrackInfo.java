package android.media.tv;

/* loaded from: classes2.dex */
public final class TvTrackInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TvTrackInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvTrackInfo>() { // from class: android.media.tv.TvTrackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvTrackInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.TvTrackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvTrackInfo[] newArray(int i) {
            return new android.media.tv.TvTrackInfo[i];
        }
    };
    public static final java.lang.String EXTRA_BUNDLE_KEY_COMPONENT_TAG = "component_tag";
    public static final java.lang.String EXTRA_BUNDLE_KEY_PID = "pid";
    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_VIDEO = 1;
    private final int mAudioChannelCount;
    private final boolean mAudioDescription;
    private final int mAudioSampleRate;
    private final java.lang.CharSequence mDescription;
    private final java.lang.String mEncoding;
    private final boolean mEncrypted;
    private final android.os.Bundle mExtra;
    private final boolean mHardOfHearing;
    private final java.lang.String mId;
    private final java.lang.String mLanguage;
    private final boolean mSpokenSubtitle;
    private final int mType;
    private final byte mVideoActiveFormatDescription;
    private final float mVideoFrameRate;
    private final int mVideoHeight;
    private final float mVideoPixelAspectRatio;
    private final int mVideoWidth;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private TvTrackInfo(int i, java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3, boolean z, int i2, int i3, boolean z2, boolean z3, boolean z4, int i4, int i5, float f, float f2, byte b, android.os.Bundle bundle) {
        this.mType = i;
        this.mId = str;
        this.mLanguage = str2;
        this.mDescription = charSequence;
        this.mEncoding = str3;
        this.mEncrypted = z;
        this.mAudioChannelCount = i2;
        this.mAudioSampleRate = i3;
        this.mAudioDescription = z2;
        this.mHardOfHearing = z3;
        this.mSpokenSubtitle = z4;
        this.mVideoWidth = i4;
        this.mVideoHeight = i5;
        this.mVideoFrameRate = f;
        this.mVideoPixelAspectRatio = f2;
        this.mVideoActiveFormatDescription = b;
        this.mExtra = bundle;
    }

    private TvTrackInfo(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readString();
        this.mLanguage = parcel.readString();
        this.mDescription = parcel.readString();
        this.mEncoding = parcel.readString();
        this.mEncrypted = parcel.readInt() != 0;
        this.mAudioChannelCount = parcel.readInt();
        this.mAudioSampleRate = parcel.readInt();
        this.mAudioDescription = parcel.readInt() != 0;
        this.mHardOfHearing = parcel.readInt() != 0;
        this.mSpokenSubtitle = parcel.readInt() != 0;
        this.mVideoWidth = parcel.readInt();
        this.mVideoHeight = parcel.readInt();
        this.mVideoFrameRate = parcel.readFloat();
        this.mVideoPixelAspectRatio = parcel.readFloat();
        this.mVideoActiveFormatDescription = parcel.readByte();
        this.mExtra = parcel.readBundle();
    }

    public final int getType() {
        return this.mType;
    }

    public final java.lang.String getId() {
        return this.mId;
    }

    public final java.lang.String getLanguage() {
        return this.mLanguage;
    }

    public final java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public java.lang.String getEncoding() {
        return this.mEncoding;
    }

    public boolean isEncrypted() {
        return this.mEncrypted;
    }

    public final int getAudioChannelCount() {
        if (this.mType != 0) {
            throw new java.lang.IllegalStateException("Not an audio track");
        }
        return this.mAudioChannelCount;
    }

    public final int getAudioSampleRate() {
        if (this.mType != 0) {
            throw new java.lang.IllegalStateException("Not an audio track");
        }
        return this.mAudioSampleRate;
    }

    public boolean isAudioDescription() {
        if (this.mType != 0) {
            throw new java.lang.IllegalStateException("Not an audio track");
        }
        return this.mAudioDescription;
    }

    public boolean isHardOfHearing() {
        if (this.mType != 0 && this.mType != 2) {
            throw new java.lang.IllegalStateException("Not an audio or a subtitle track");
        }
        return this.mHardOfHearing;
    }

    public boolean isSpokenSubtitle() {
        if (this.mType != 0) {
            throw new java.lang.IllegalStateException("Not an audio track");
        }
        return this.mSpokenSubtitle;
    }

    public final int getVideoWidth() {
        if (this.mType != 1) {
            throw new java.lang.IllegalStateException("Not a video track");
        }
        return this.mVideoWidth;
    }

    public final int getVideoHeight() {
        if (this.mType != 1) {
            throw new java.lang.IllegalStateException("Not a video track");
        }
        return this.mVideoHeight;
    }

    public final float getVideoFrameRate() {
        if (this.mType != 1) {
            throw new java.lang.IllegalStateException("Not a video track");
        }
        return this.mVideoFrameRate;
    }

    public final float getVideoPixelAspectRatio() {
        if (this.mType != 1) {
            throw new java.lang.IllegalStateException("Not a video track");
        }
        return this.mVideoPixelAspectRatio;
    }

    public final byte getVideoActiveFormatDescription() {
        if (this.mType != 1) {
            throw new java.lang.IllegalStateException("Not a video track");
        }
        return this.mVideoActiveFormatDescription;
    }

    public final android.os.Bundle getExtra() {
        return this.mExtra;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        com.android.internal.util.Preconditions.checkNotNull(parcel);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mId);
        parcel.writeString(this.mLanguage);
        parcel.writeString(this.mDescription != null ? this.mDescription.toString() : null);
        parcel.writeString(this.mEncoding);
        parcel.writeInt(this.mEncrypted ? 1 : 0);
        parcel.writeInt(this.mAudioChannelCount);
        parcel.writeInt(this.mAudioSampleRate);
        parcel.writeInt(this.mAudioDescription ? 1 : 0);
        parcel.writeInt(this.mHardOfHearing ? 1 : 0);
        parcel.writeInt(this.mSpokenSubtitle ? 1 : 0);
        parcel.writeInt(this.mVideoWidth);
        parcel.writeInt(this.mVideoHeight);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeFloat(this.mVideoPixelAspectRatio);
        parcel.writeByte(this.mVideoActiveFormatDescription);
        parcel.writeBundle(this.mExtra);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.tv.TvTrackInfo)) {
            return false;
        }
        android.media.tv.TvTrackInfo tvTrackInfo = (android.media.tv.TvTrackInfo) obj;
        if (!android.text.TextUtils.equals(this.mId, tvTrackInfo.mId) || this.mType != tvTrackInfo.mType || !android.text.TextUtils.equals(this.mLanguage, tvTrackInfo.mLanguage) || !android.text.TextUtils.equals(this.mDescription, tvTrackInfo.mDescription) || !android.text.TextUtils.equals(this.mEncoding, tvTrackInfo.mEncoding) || this.mEncrypted != tvTrackInfo.mEncrypted) {
            return false;
        }
        switch (this.mType) {
            case 0:
                return this.mAudioChannelCount == tvTrackInfo.mAudioChannelCount && this.mAudioSampleRate == tvTrackInfo.mAudioSampleRate && this.mAudioDescription == tvTrackInfo.mAudioDescription && this.mHardOfHearing == tvTrackInfo.mHardOfHearing && this.mSpokenSubtitle == tvTrackInfo.mSpokenSubtitle;
            case 1:
                return this.mVideoWidth == tvTrackInfo.mVideoWidth && this.mVideoHeight == tvTrackInfo.mVideoHeight && this.mVideoFrameRate == tvTrackInfo.mVideoFrameRate && this.mVideoPixelAspectRatio == tvTrackInfo.mVideoPixelAspectRatio && this.mVideoActiveFormatDescription == tvTrackInfo.mVideoActiveFormatDescription;
            case 2:
                return this.mHardOfHearing == tvTrackInfo.mHardOfHearing;
            default:
                return true;
        }
    }

    public int hashCode() {
        int hash = java.util.Objects.hash(this.mId, java.lang.Integer.valueOf(this.mType), this.mLanguage, this.mDescription);
        if (this.mType == 0) {
            return java.util.Objects.hash(java.lang.Integer.valueOf(hash), java.lang.Integer.valueOf(this.mAudioChannelCount), java.lang.Integer.valueOf(this.mAudioSampleRate));
        }
        if (this.mType == 1) {
            return java.util.Objects.hash(java.lang.Integer.valueOf(hash), java.lang.Integer.valueOf(this.mVideoWidth), java.lang.Integer.valueOf(this.mVideoHeight), java.lang.Float.valueOf(this.mVideoFrameRate), java.lang.Float.valueOf(this.mVideoPixelAspectRatio));
        }
        return hash;
    }

    public static final class Builder {
        private int mAudioChannelCount;
        private boolean mAudioDescription;
        private int mAudioSampleRate;
        private java.lang.CharSequence mDescription;
        private java.lang.String mEncoding;
        private boolean mEncrypted;
        private android.os.Bundle mExtra;
        private boolean mHardOfHearing;
        private final java.lang.String mId;
        private java.lang.String mLanguage;
        private boolean mSpokenSubtitle;
        private final int mType;
        private byte mVideoActiveFormatDescription;
        private float mVideoFrameRate;
        private int mVideoHeight;
        private float mVideoPixelAspectRatio = 1.0f;
        private int mVideoWidth;

        public Builder(int i, java.lang.String str) {
            if (i != 0 && i != 1 && i != 2) {
                throw new java.lang.IllegalArgumentException("Unknown type: " + i);
            }
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mType = i;
            this.mId = str;
        }

        public android.media.tv.TvTrackInfo.Builder setLanguage(java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mLanguage = str;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setDescription(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mDescription = charSequence;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setEncoding(java.lang.String str) {
            this.mEncoding = str;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setEncrypted(boolean z) {
            this.mEncrypted = z;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setAudioChannelCount(int i) {
            if (this.mType != 0) {
                throw new java.lang.IllegalStateException("Not an audio track");
            }
            this.mAudioChannelCount = i;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setAudioSampleRate(int i) {
            if (this.mType != 0) {
                throw new java.lang.IllegalStateException("Not an audio track");
            }
            this.mAudioSampleRate = i;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setAudioDescription(boolean z) {
            if (this.mType != 0) {
                throw new java.lang.IllegalStateException("Not an audio track");
            }
            this.mAudioDescription = z;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setHardOfHearing(boolean z) {
            if (this.mType != 0 && this.mType != 2) {
                throw new java.lang.IllegalStateException("Not an audio track or a subtitle track");
            }
            this.mHardOfHearing = z;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setSpokenSubtitle(boolean z) {
            if (this.mType != 0) {
                throw new java.lang.IllegalStateException("Not an audio track");
            }
            this.mSpokenSubtitle = z;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setVideoWidth(int i) {
            if (this.mType != 1) {
                throw new java.lang.IllegalStateException("Not a video track");
            }
            this.mVideoWidth = i;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setVideoHeight(int i) {
            if (this.mType != 1) {
                throw new java.lang.IllegalStateException("Not a video track");
            }
            this.mVideoHeight = i;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setVideoFrameRate(float f) {
            if (this.mType != 1) {
                throw new java.lang.IllegalStateException("Not a video track");
            }
            this.mVideoFrameRate = f;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setVideoPixelAspectRatio(float f) {
            if (this.mType != 1) {
                throw new java.lang.IllegalStateException("Not a video track");
            }
            this.mVideoPixelAspectRatio = f;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setVideoActiveFormatDescription(byte b) {
            if (this.mType != 1) {
                throw new java.lang.IllegalStateException("Not a video track");
            }
            this.mVideoActiveFormatDescription = b;
            return this;
        }

        public android.media.tv.TvTrackInfo.Builder setExtra(android.os.Bundle bundle) {
            com.android.internal.util.Preconditions.checkNotNull(bundle);
            this.mExtra = new android.os.Bundle(bundle);
            return this;
        }

        public android.media.tv.TvTrackInfo build() {
            return new android.media.tv.TvTrackInfo(this.mType, this.mId, this.mLanguage, this.mDescription, this.mEncoding, this.mEncrypted, this.mAudioChannelCount, this.mAudioSampleRate, this.mAudioDescription, this.mHardOfHearing, this.mSpokenSubtitle, this.mVideoWidth, this.mVideoHeight, this.mVideoFrameRate, this.mVideoPixelAspectRatio, this.mVideoActiveFormatDescription, this.mExtra);
        }
    }
}
