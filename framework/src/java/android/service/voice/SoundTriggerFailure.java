package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SoundTriggerFailure implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.SoundTriggerFailure> CREATOR = new android.os.Parcelable.Creator<android.service.voice.SoundTriggerFailure>() { // from class: android.service.voice.SoundTriggerFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.SoundTriggerFailure[] newArray(int i) {
            return new android.service.voice.SoundTriggerFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.SoundTriggerFailure createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.SoundTriggerFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_MODULE_DIED = 1;
    public static final int ERROR_CODE_RECOGNITION_RESUME_FAILED = 2;
    public static final int ERROR_CODE_UNEXPECTED_PREEMPTION = 3;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private final int mErrorCode;
    private final java.lang.String mErrorMessage;
    private final int mSuggestedAction;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SoundTriggerErrorCode {
    }

    public SoundTriggerFailure(int i, java.lang.String str) {
        this(i, str, getSuggestedActionBasedOnErrorCode(i));
    }

    public SoundTriggerFailure(int i, java.lang.String str, int i2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("errorMessage is empty or null.");
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                this.mErrorCode = i;
                if (i2 != getSuggestedActionBasedOnErrorCode(i) && i != 0) {
                    throw new java.lang.IllegalArgumentException("Invalid suggested next action: errorCode=" + i + ", suggestedAction=" + i2);
                }
                this.mErrorMessage = str;
                this.mSuggestedAction = i2;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Invalid ErrorCode: " + i);
        }
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getSuggestedAction() {
        return this.mSuggestedAction;
    }

    private static int getSuggestedActionBasedOnErrorCode(int i) {
        switch (i) {
            case 0:
            case 1:
            case 3:
                return 3;
            case 2:
                return 4;
            default:
                throw new java.lang.AssertionError("Unexpected error code");
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
        return "SoundTriggerFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + ", suggestedNextAction = " + this.mSuggestedAction + " }";
    }
}
