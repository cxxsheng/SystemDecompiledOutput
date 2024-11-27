package android.media.metrics;

/* loaded from: classes2.dex */
public final class PlaybackErrorEvent extends android.media.metrics.Event implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.PlaybackErrorEvent> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.PlaybackErrorEvent>() { // from class: android.media.metrics.PlaybackErrorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackErrorEvent[] newArray(int i) {
            return new android.media.metrics.PlaybackErrorEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackErrorEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.PlaybackErrorEvent(parcel);
        }
    };
    public static final int ERROR_AUDIO_TRACK_INIT_FAILED = 17;
    public static final int ERROR_AUDIO_TRACK_OTHER = 19;
    public static final int ERROR_AUDIO_TRACK_WRITE_FAILED = 18;
    public static final int ERROR_DECODER_INIT_FAILED = 13;
    public static final int ERROR_DECODING_FAILED = 14;
    public static final int ERROR_DECODING_FORMAT_EXCEEDS_CAPABILITIES = 15;
    public static final int ERROR_DECODING_FORMAT_UNSUPPORTED = 35;
    public static final int ERROR_DECODING_OTHER = 16;
    public static final int ERROR_DRM_CONTENT_ERROR = 28;
    public static final int ERROR_DRM_DEVICE_REVOKED = 29;
    public static final int ERROR_DRM_DISALLOWED_OPERATION = 26;
    public static final int ERROR_DRM_LICENSE_ACQUISITION_FAILED = 25;
    public static final int ERROR_DRM_OTHER = 30;
    public static final int ERROR_DRM_PROVISIONING_FAILED = 24;
    public static final int ERROR_DRM_SCHEME_UNSUPPORTED = 23;
    public static final int ERROR_DRM_SYSTEM_ERROR = 27;
    public static final int ERROR_IO_BAD_HTTP_STATUS = 5;
    public static final int ERROR_IO_CONNECTION_CLOSED = 8;
    public static final int ERROR_IO_CONNECTION_TIMEOUT = 7;
    public static final int ERROR_IO_DNS_FAILED = 6;
    public static final int ERROR_IO_FILE_NOT_FOUND = 31;
    public static final int ERROR_IO_NETWORK_CONNECTION_FAILED = 4;
    public static final int ERROR_IO_NETWORK_UNAVAILABLE = 3;
    public static final int ERROR_IO_NO_PERMISSION = 32;
    public static final int ERROR_IO_OTHER = 9;
    public static final int ERROR_OTHER = 1;
    public static final int ERROR_PARSING_CONTAINER_MALFORMED = 11;
    public static final int ERROR_PARSING_CONTAINER_UNSUPPORTED = 34;
    public static final int ERROR_PARSING_MANIFEST_MALFORMED = 10;
    public static final int ERROR_PARSING_MANIFEST_UNSUPPORTED = 33;
    public static final int ERROR_PARSING_OTHER = 12;
    public static final int ERROR_PLAYER_BEHIND_LIVE_WINDOW = 21;
    public static final int ERROR_PLAYER_OTHER = 22;
    public static final int ERROR_PLAYER_REMOTE = 20;
    public static final int ERROR_RUNTIME = 2;
    public static final int ERROR_UNKNOWN = 0;
    private final int mErrorCode;
    private final java.lang.String mExceptionStack;
    private final int mSubErrorCode;
    private final long mTimeSinceCreatedMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    private PlaybackErrorEvent(java.lang.String str, int i, int i2, long j, android.os.Bundle bundle) {
        this.mExceptionStack = str;
        this.mErrorCode = i;
        this.mSubErrorCode = i2;
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public java.lang.String getExceptionStack() {
        return this.mExceptionStack;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getSubErrorCode() {
        return this.mSubErrorCode;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public java.lang.String toString() {
        return "PlaybackErrorEvent { exceptionStack = " + this.mExceptionStack + ", errorCode = " + this.mErrorCode + ", subErrorCode = " + this.mSubErrorCode + ", timeSinceCreatedMillis = " + this.mTimeSinceCreatedMillis + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.PlaybackErrorEvent playbackErrorEvent = (android.media.metrics.PlaybackErrorEvent) obj;
        if (java.util.Objects.equals(this.mExceptionStack, playbackErrorEvent.mExceptionStack) && this.mErrorCode == playbackErrorEvent.mErrorCode && this.mSubErrorCode == playbackErrorEvent.mSubErrorCode && this.mTimeSinceCreatedMillis == playbackErrorEvent.mTimeSinceCreatedMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mExceptionStack, java.lang.Integer.valueOf(this.mErrorCode), java.lang.Integer.valueOf(this.mSubErrorCode), java.lang.Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mExceptionStack != null ? (byte) 1 : (byte) 0);
        if (this.mExceptionStack != null) {
            parcel.writeString(this.mExceptionStack);
        }
        parcel.writeInt(this.mErrorCode);
        parcel.writeInt(this.mSubErrorCode);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PlaybackErrorEvent(android.os.Parcel parcel) {
        java.lang.String readString = (parcel.readByte() & 1) == 0 ? null : parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mExceptionStack = readString;
        this.mErrorCode = readInt;
        this.mSubErrorCode = readInt2;
        this.mTimeSinceCreatedMillis = readLong;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private java.lang.Exception mException;
        private int mSubErrorCode;
        private int mErrorCode = 0;
        private long mTimeSinceCreatedMillis = -1;
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();

        public android.media.metrics.PlaybackErrorEvent.Builder setException(java.lang.Exception exc) {
            this.mException = exc;
            return this;
        }

        public android.media.metrics.PlaybackErrorEvent.Builder setErrorCode(int i) {
            this.mErrorCode = i;
            return this;
        }

        public android.media.metrics.PlaybackErrorEvent.Builder setSubErrorCode(int i) {
            this.mSubErrorCode = i;
            return this;
        }

        public android.media.metrics.PlaybackErrorEvent.Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public android.media.metrics.PlaybackErrorEvent.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public android.media.metrics.PlaybackErrorEvent build() {
            java.lang.String str;
            if (this.mException.getStackTrace() != null && this.mException.getStackTrace().length > 0) {
                str = this.mException.getStackTrace()[0].toString();
            } else {
                str = null;
            }
            return new android.media.metrics.PlaybackErrorEvent(str, this.mErrorCode, this.mSubErrorCode, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}
