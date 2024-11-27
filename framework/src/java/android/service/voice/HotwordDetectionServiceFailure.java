package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class HotwordDetectionServiceFailure implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.HotwordDetectionServiceFailure> CREATOR = new android.os.Parcelable.Creator<android.service.voice.HotwordDetectionServiceFailure>() { // from class: android.service.voice.HotwordDetectionServiceFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordDetectionServiceFailure[] newArray(int i) {
            return new android.service.voice.HotwordDetectionServiceFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.HotwordDetectionServiceFailure createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.HotwordDetectionServiceFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_BINDING_DIED = 2;
    public static final int ERROR_CODE_BIND_FAILURE = 1;
    public static final int ERROR_CODE_COPY_AUDIO_DATA_FAILURE = 3;
    public static final int ERROR_CODE_DETECT_TIMEOUT = 4;
    public static final int ERROR_CODE_ON_DETECTED_SECURITY_EXCEPTION = 5;
    public static final int ERROR_CODE_ON_DETECTED_STREAM_COPY_FAILURE = 6;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 7;
    public static final int ERROR_CODE_SHUTDOWN_HDS_ON_VOICE_ACTIVATION_OP_DISABLED = 10;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private int mErrorCode;
    private java.lang.String mErrorMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HotwordDetectionServiceErrorCode {
    }

    public HotwordDetectionServiceFailure(int i, java.lang.String str) {
        this.mErrorCode = 0;
        this.mErrorMessage = "Unknown";
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("errorMessage is empty or null.");
        }
        this.mErrorCode = i;
        this.mErrorMessage = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getSuggestedAction() {
        switch (this.mErrorCode) {
            case 1:
            case 2:
            case 7:
                return 3;
            case 3:
            default:
                return 1;
            case 4:
            case 5:
            case 6:
                return 4;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mErrorCode);
        parcel.writeString8(this.mErrorMessage);
    }

    public java.lang.String toString() {
        return "HotwordDetectionServiceFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + " }";
    }
}
