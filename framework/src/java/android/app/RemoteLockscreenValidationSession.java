package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RemoteLockscreenValidationSession implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RemoteLockscreenValidationSession> CREATOR = new android.os.Parcelable.Creator<android.app.RemoteLockscreenValidationSession>() { // from class: android.app.RemoteLockscreenValidationSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteLockscreenValidationSession createFromParcel(android.os.Parcel parcel) {
            return new android.app.RemoteLockscreenValidationSession(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteLockscreenValidationSession[] newArray(int i) {
            return new android.app.RemoteLockscreenValidationSession[i];
        }
    };
    private int mLockType;
    private int mRemainingAttempts;
    private byte[] mSourcePublicKey;

    public static final class Builder {
        private android.app.RemoteLockscreenValidationSession mInstance = new android.app.RemoteLockscreenValidationSession();

        public android.app.RemoteLockscreenValidationSession.Builder setLockType(int i) {
            this.mInstance.mLockType = i;
            return this;
        }

        public android.app.RemoteLockscreenValidationSession.Builder setSourcePublicKey(byte[] bArr) {
            this.mInstance.mSourcePublicKey = bArr;
            return this;
        }

        public android.app.RemoteLockscreenValidationSession.Builder setRemainingAttempts(int i) {
            this.mInstance.mRemainingAttempts = i;
            return this;
        }

        public android.app.RemoteLockscreenValidationSession build() {
            java.util.Objects.requireNonNull(this.mInstance.mSourcePublicKey);
            return this.mInstance;
        }
    }

    public int getLockType() {
        return this.mLockType;
    }

    public byte[] getSourcePublicKey() {
        return this.mSourcePublicKey;
    }

    public int getRemainingAttempts() {
        return this.mRemainingAttempts;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLockType);
        parcel.writeByteArray(this.mSourcePublicKey);
        parcel.writeInt(this.mRemainingAttempts);
    }

    private RemoteLockscreenValidationSession() {
    }

    private RemoteLockscreenValidationSession(android.os.Parcel parcel) {
        this.mLockType = parcel.readInt();
        this.mSourcePublicKey = parcel.createByteArray();
        this.mRemainingAttempts = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
