package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryDetectionServiceFailure implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.VisualQueryDetectionServiceFailure> CREATOR = new android.os.Parcelable.Creator<android.service.voice.VisualQueryDetectionServiceFailure>() { // from class: android.service.voice.VisualQueryDetectionServiceFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryDetectionServiceFailure[] newArray(int i) {
            return new android.service.voice.VisualQueryDetectionServiceFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryDetectionServiceFailure createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.VisualQueryDetectionServiceFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_BINDING_DIED = 2;
    public static final int ERROR_CODE_BIND_FAILURE = 1;
    public static final int ERROR_CODE_ILLEGAL_ATTENTION_STATE = 3;
    public static final int ERROR_CODE_ILLEGAL_STREAMING_STATE = 4;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 5;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private int mErrorCode;
    private java.lang.String mErrorMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VisualQueryDetectionServiceErrorCode {
    }

    public VisualQueryDetectionServiceFailure(int i, java.lang.String str) {
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
            case 3:
            case 5:
                return 3;
            case 4:
                return 4;
            default:
                return 1;
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
        return "VisualQueryDetectionServiceFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + " }";
    }
}
