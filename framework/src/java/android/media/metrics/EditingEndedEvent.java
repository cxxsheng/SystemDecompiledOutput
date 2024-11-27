package android.media.metrics;

/* loaded from: classes2.dex */
public final class EditingEndedEvent extends android.media.metrics.Event implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.EditingEndedEvent> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.EditingEndedEvent>() { // from class: android.media.metrics.EditingEndedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.EditingEndedEvent[] newArray(int i) {
            return new android.media.metrics.EditingEndedEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.EditingEndedEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.EditingEndedEvent(parcel);
        }
    };
    public static final int ERROR_CODE_AUDIO_PROCESSING_FAILED = 18;
    public static final int ERROR_CODE_DECODER_INIT_FAILED = 11;
    public static final int ERROR_CODE_DECODING_FAILED = 12;
    public static final int ERROR_CODE_DECODING_FORMAT_UNSUPPORTED = 13;
    public static final int ERROR_CODE_ENCODER_INIT_FAILED = 14;
    public static final int ERROR_CODE_ENCODING_FAILED = 15;
    public static final int ERROR_CODE_ENCODING_FORMAT_UNSUPPORTED = 16;
    public static final int ERROR_CODE_FAILED_RUNTIME_CHECK = 2;
    public static final int ERROR_CODE_IO_BAD_HTTP_STATUS = 6;
    public static final int ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED = 9;
    public static final int ERROR_CODE_IO_FILE_NOT_FOUND = 7;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_FAILED = 4;
    public static final int ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT = 5;
    public static final int ERROR_CODE_IO_NO_PERMISSION = 8;
    public static final int ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE = 10;
    public static final int ERROR_CODE_IO_UNSPECIFIED = 3;
    public static final int ERROR_CODE_MUXING_FAILED = 19;
    public static final int ERROR_CODE_NONE = 1;
    public static final int ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED = 17;
    public static final int FINAL_STATE_CANCELED = 2;
    public static final int FINAL_STATE_ERROR = 3;
    public static final int FINAL_STATE_SUCCEEDED = 1;
    public static final long OPERATION_TYPE_AUDIO_EDIT = 8;
    public static final long OPERATION_TYPE_AUDIO_TRANSCODE = 2;
    public static final long OPERATION_TYPE_AUDIO_TRANSMUX = 32;
    public static final long OPERATION_TYPE_PAUSED = 64;
    public static final long OPERATION_TYPE_RESUMED = 128;
    public static final long OPERATION_TYPE_VIDEO_EDIT = 4;
    public static final long OPERATION_TYPE_VIDEO_TRANSCODE = 1;
    public static final long OPERATION_TYPE_VIDEO_TRANSMUX = 16;
    public static final int PROGRESS_PERCENT_UNKNOWN = -1;
    public static final int TIME_SINCE_CREATED_UNKNOWN = -1;
    private final int mErrorCode;
    private final java.lang.String mExporterName;
    private final float mFinalProgressPercent;
    private final int mFinalState;
    private final java.util.ArrayList<android.media.metrics.MediaItemInfo> mInputMediaItemInfos;
    private final java.lang.String mMuxerName;
    private final long mOperationTypes;
    private final android.media.metrics.MediaItemInfo mOutputMediaItemInfo;
    private final long mTimeSinceCreatedMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FinalState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OperationType {
    }

    private EditingEndedEvent(int i, float f, int i2, long j, java.lang.String str, java.lang.String str2, java.util.ArrayList<android.media.metrics.MediaItemInfo> arrayList, android.media.metrics.MediaItemInfo mediaItemInfo, long j2, android.os.Bundle bundle) {
        this.mFinalState = i;
        this.mFinalProgressPercent = f;
        this.mErrorCode = i2;
        this.mTimeSinceCreatedMillis = j;
        this.mExporterName = str;
        this.mMuxerName = str2;
        this.mInputMediaItemInfos = arrayList;
        this.mOutputMediaItemInfo = mediaItemInfo;
        this.mOperationTypes = j2;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public int getFinalState() {
        return this.mFinalState;
    }

    public float getFinalProgressPercent() {
        return this.mFinalProgressPercent;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public java.lang.String getExporterName() {
        return this.mExporterName;
    }

    public java.lang.String getMuxerName() {
        return this.mMuxerName;
    }

    public java.util.List<android.media.metrics.MediaItemInfo> getInputMediaItemInfos() {
        return new java.util.ArrayList(this.mInputMediaItemInfos);
    }

    public android.media.metrics.MediaItemInfo getOutputMediaItemInfo() {
        return this.mOutputMediaItemInfo;
    }

    public long getOperationTypes() {
        return this.mOperationTypes;
    }

    @Override // android.media.metrics.Event
    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public java.lang.String toString() {
        return "EditingEndedEvent { finalState = " + this.mFinalState + ", finalProgressPercent = " + this.mFinalProgressPercent + ", errorCode = " + this.mErrorCode + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + ", exporterName = " + this.mExporterName + ", muxerName = " + this.mMuxerName + ", inputMediaItemInfos = " + this.mInputMediaItemInfos + ", outputMediaItemInfo = " + this.mOutputMediaItemInfo + ", operationTypes = " + this.mOperationTypes + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.EditingEndedEvent editingEndedEvent = (android.media.metrics.EditingEndedEvent) obj;
        if (this.mFinalState == editingEndedEvent.mFinalState && this.mFinalProgressPercent == editingEndedEvent.mFinalProgressPercent && this.mErrorCode == editingEndedEvent.mErrorCode && java.util.Objects.equals(this.mInputMediaItemInfos, editingEndedEvent.mInputMediaItemInfos) && java.util.Objects.equals(this.mOutputMediaItemInfo, editingEndedEvent.mOutputMediaItemInfo) && this.mOperationTypes == editingEndedEvent.mOperationTypes && this.mTimeSinceCreatedMillis == editingEndedEvent.mTimeSinceCreatedMillis && java.util.Objects.equals(this.mExporterName, editingEndedEvent.mExporterName) && java.util.Objects.equals(this.mMuxerName, editingEndedEvent.mMuxerName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mFinalState), java.lang.Float.valueOf(this.mFinalProgressPercent), java.lang.Integer.valueOf(this.mErrorCode), this.mInputMediaItemInfos, this.mOutputMediaItemInfo, java.lang.Long.valueOf(this.mOperationTypes), java.lang.Long.valueOf(this.mTimeSinceCreatedMillis), this.mExporterName, this.mMuxerName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFinalState);
        parcel.writeFloat(this.mFinalProgressPercent);
        parcel.writeInt(this.mErrorCode);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeString(this.mExporterName);
        parcel.writeString(this.mMuxerName);
        parcel.writeTypedList(this.mInputMediaItemInfos);
        parcel.writeTypedObject(this.mOutputMediaItemInfo, 0);
        parcel.writeLong(this.mOperationTypes);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private EditingEndedEvent(android.os.Parcel parcel) {
        this.mFinalState = parcel.readInt();
        this.mFinalProgressPercent = parcel.readFloat();
        this.mErrorCode = parcel.readInt();
        this.mTimeSinceCreatedMillis = parcel.readLong();
        this.mExporterName = parcel.readString();
        this.mMuxerName = parcel.readString();
        this.mInputMediaItemInfos = new java.util.ArrayList<>();
        parcel.readTypedList(this.mInputMediaItemInfos, android.media.metrics.MediaItemInfo.CREATOR);
        this.mOutputMediaItemInfo = (android.media.metrics.MediaItemInfo) parcel.readTypedObject(android.media.metrics.MediaItemInfo.CREATOR);
        this.mOperationTypes = parcel.readLong();
        this.mMetricsBundle = parcel.readBundle();
    }

    public static final class Builder {
        private java.lang.String mExporterName;
        private final int mFinalState;
        private java.lang.String mMuxerName;
        private long mOperationTypes;
        private android.media.metrics.MediaItemInfo mOutputMediaItemInfo;
        private float mFinalProgressPercent = -1.0f;
        private int mErrorCode = 1;
        private long mTimeSinceCreatedMillis = -1;
        private final java.util.ArrayList<android.media.metrics.MediaItemInfo> mInputMediaItemInfos = new java.util.ArrayList<>();
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();

        public Builder(int i) {
            this.mFinalState = i;
        }

        public android.media.metrics.EditingEndedEvent.Builder setFinalProgressPercent(float f) {
            this.mFinalProgressPercent = f;
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setExporterName(java.lang.String str) {
            this.mExporterName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setMuxerName(java.lang.String str) {
            this.mMuxerName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setErrorCode(int i) {
            this.mErrorCode = i;
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder addInputMediaItemInfo(android.media.metrics.MediaItemInfo mediaItemInfo) {
            this.mInputMediaItemInfos.add((android.media.metrics.MediaItemInfo) java.util.Objects.requireNonNull(mediaItemInfo));
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setOutputMediaItemInfo(android.media.metrics.MediaItemInfo mediaItemInfo) {
            this.mOutputMediaItemInfo = (android.media.metrics.MediaItemInfo) java.util.Objects.requireNonNull(mediaItemInfo);
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder addOperationType(long j) {
            this.mOperationTypes = j | this.mOperationTypes;
            return this;
        }

        public android.media.metrics.EditingEndedEvent.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            return this;
        }

        public android.media.metrics.EditingEndedEvent build() {
            return new android.media.metrics.EditingEndedEvent(this.mFinalState, this.mFinalProgressPercent, this.mErrorCode, this.mTimeSinceCreatedMillis, this.mExporterName, this.mMuxerName, this.mInputMediaItemInfos, this.mOutputMediaItemInfo, this.mOperationTypes, this.mMetricsBundle);
        }
    }
}
