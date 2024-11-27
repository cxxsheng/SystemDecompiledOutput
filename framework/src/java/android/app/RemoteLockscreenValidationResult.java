package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RemoteLockscreenValidationResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RemoteLockscreenValidationResult> CREATOR = new android.os.Parcelable.Creator<android.app.RemoteLockscreenValidationResult>() { // from class: android.app.RemoteLockscreenValidationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteLockscreenValidationResult createFromParcel(android.os.Parcel parcel) {
            return new android.app.RemoteLockscreenValidationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteLockscreenValidationResult[] newArray(int i) {
            return new android.app.RemoteLockscreenValidationResult[i];
        }
    };
    public static final int RESULT_GUESS_INVALID = 2;
    public static final int RESULT_GUESS_VALID = 1;
    public static final int RESULT_LOCKOUT = 3;
    public static final int RESULT_NO_REMAINING_ATTEMPTS = 4;
    public static final int RESULT_SESSION_EXPIRED = 5;
    private int mResultCode;
    private long mTimeoutMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ResultCode {
    }

    public static final class Builder {
        private android.app.RemoteLockscreenValidationResult mInstance = new android.app.RemoteLockscreenValidationResult();

        public android.app.RemoteLockscreenValidationResult.Builder setResultCode(int i) {
            this.mInstance.mResultCode = i;
            return this;
        }

        public android.app.RemoteLockscreenValidationResult.Builder setTimeoutMillis(long j) {
            this.mInstance.mTimeoutMillis = j;
            return this;
        }

        public android.app.RemoteLockscreenValidationResult build() {
            if (this.mInstance.mResultCode == 0) {
                throw new java.lang.IllegalStateException("Result code must be set");
            }
            return this.mInstance;
        }
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public long getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeLong(this.mTimeoutMillis);
    }

    private RemoteLockscreenValidationResult() {
    }

    private RemoteLockscreenValidationResult(android.os.Parcel parcel) {
        this.mResultCode = parcel.readInt();
        this.mTimeoutMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
