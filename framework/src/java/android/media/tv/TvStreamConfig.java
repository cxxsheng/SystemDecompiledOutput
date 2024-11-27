package android.media.tv;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class TvStreamConfig implements android.os.Parcelable {
    public static final int STREAM_TYPE_BUFFER_PRODUCER = 2;
    public static final int STREAM_TYPE_INDEPENDENT_VIDEO_SOURCE = 1;
    private int mGeneration;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mStreamId;
    private int mType;
    static final java.lang.String TAG = android.media.tv.TvStreamConfig.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.media.tv.TvStreamConfig> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvStreamConfig>() { // from class: android.media.tv.TvStreamConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvStreamConfig createFromParcel(android.os.Parcel parcel) {
            try {
                return new android.media.tv.TvStreamConfig.Builder().streamId(parcel.readInt()).type(parcel.readInt()).maxWidth(parcel.readInt()).maxHeight(parcel.readInt()).generation(parcel.readInt()).build();
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.media.tv.TvStreamConfig.TAG, "Exception creating TvStreamConfig from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvStreamConfig[] newArray(int i) {
            return new android.media.tv.TvStreamConfig[i];
        }
    };

    private TvStreamConfig() {
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public int getType() {
        return this.mType;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public java.lang.String toString() {
        return "TvStreamConfig {mStreamId=" + this.mStreamId + ";mType=" + this.mType + ";mGeneration=" + this.mGeneration + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStreamId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mMaxWidth);
        parcel.writeInt(this.mMaxHeight);
        parcel.writeInt(this.mGeneration);
    }

    public static final class Builder {
        private java.lang.Integer mGeneration;
        private java.lang.Integer mMaxHeight;
        private java.lang.Integer mMaxWidth;
        private java.lang.Integer mStreamId;
        private java.lang.Integer mType;

        public android.media.tv.TvStreamConfig.Builder streamId(int i) {
            this.mStreamId = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvStreamConfig.Builder type(int i) {
            this.mType = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvStreamConfig.Builder maxWidth(int i) {
            this.mMaxWidth = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvStreamConfig.Builder maxHeight(int i) {
            this.mMaxHeight = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvStreamConfig.Builder generation(int i) {
            this.mGeneration = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.media.tv.TvStreamConfig build() {
            if (this.mStreamId == null || this.mType == null || this.mMaxWidth == null || this.mMaxHeight == null || this.mGeneration == null) {
                throw new java.lang.UnsupportedOperationException();
            }
            android.media.tv.TvStreamConfig tvStreamConfig = new android.media.tv.TvStreamConfig();
            tvStreamConfig.mStreamId = this.mStreamId.intValue();
            tvStreamConfig.mType = this.mType.intValue();
            tvStreamConfig.mMaxWidth = this.mMaxWidth.intValue();
            tvStreamConfig.mMaxHeight = this.mMaxHeight.intValue();
            tvStreamConfig.mGeneration = this.mGeneration.intValue();
            return tvStreamConfig;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.media.tv.TvStreamConfig)) {
            return false;
        }
        android.media.tv.TvStreamConfig tvStreamConfig = (android.media.tv.TvStreamConfig) obj;
        return tvStreamConfig.mGeneration == this.mGeneration && tvStreamConfig.mStreamId == this.mStreamId && tvStreamConfig.mType == this.mType && tvStreamConfig.mMaxWidth == this.mMaxWidth && tvStreamConfig.mMaxHeight == this.mMaxHeight;
    }
}
