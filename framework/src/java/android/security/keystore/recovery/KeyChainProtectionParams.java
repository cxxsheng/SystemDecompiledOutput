package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class KeyChainProtectionParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keystore.recovery.KeyChainProtectionParams> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.recovery.KeyChainProtectionParams>() { // from class: android.security.keystore.recovery.KeyChainProtectionParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyChainProtectionParams createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.recovery.KeyChainProtectionParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyChainProtectionParams[] newArray(int i) {
            return new android.security.keystore.recovery.KeyChainProtectionParams[i];
        }
    };
    public static final int TYPE_LOCKSCREEN = 100;
    public static final int UI_FORMAT_PASSWORD = 2;
    public static final int UI_FORMAT_PATTERN = 3;
    public static final int UI_FORMAT_PIN = 1;
    private android.security.keystore.recovery.KeyDerivationParams mKeyDerivationParams;
    private java.lang.Integer mLockScreenUiFormat;
    private byte[] mSecret;
    private java.lang.Integer mUserSecretType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LockScreenUiFormat {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserSecretType {
    }

    private KeyChainProtectionParams() {
    }

    public int getUserSecretType() {
        return this.mUserSecretType.intValue();
    }

    public int getLockScreenUiFormat() {
        return this.mLockScreenUiFormat.intValue();
    }

    public android.security.keystore.recovery.KeyDerivationParams getKeyDerivationParams() {
        return this.mKeyDerivationParams;
    }

    public byte[] getSecret() {
        return this.mSecret;
    }

    public static class Builder {
        private android.security.keystore.recovery.KeyChainProtectionParams mInstance = new android.security.keystore.recovery.KeyChainProtectionParams();

        public android.security.keystore.recovery.KeyChainProtectionParams.Builder setUserSecretType(int i) {
            this.mInstance.mUserSecretType = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.security.keystore.recovery.KeyChainProtectionParams.Builder setLockScreenUiFormat(int i) {
            this.mInstance.mLockScreenUiFormat = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.security.keystore.recovery.KeyChainProtectionParams.Builder setKeyDerivationParams(android.security.keystore.recovery.KeyDerivationParams keyDerivationParams) {
            this.mInstance.mKeyDerivationParams = keyDerivationParams;
            return this;
        }

        public android.security.keystore.recovery.KeyChainProtectionParams.Builder setSecret(byte[] bArr) {
            this.mInstance.mSecret = bArr;
            return this;
        }

        public android.security.keystore.recovery.KeyChainProtectionParams build() {
            if (this.mInstance.mUserSecretType == null) {
                this.mInstance.mUserSecretType = 100;
            }
            java.util.Objects.requireNonNull(this.mInstance.mLockScreenUiFormat);
            java.util.Objects.requireNonNull(this.mInstance.mKeyDerivationParams);
            if (this.mInstance.mSecret == null) {
                this.mInstance.mSecret = new byte[0];
            }
            return this.mInstance;
        }
    }

    public void clearSecret() {
        java.util.Arrays.fill(this.mSecret, (byte) 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUserSecretType.intValue());
        parcel.writeInt(this.mLockScreenUiFormat.intValue());
        parcel.writeTypedObject(this.mKeyDerivationParams, i);
        parcel.writeByteArray(this.mSecret);
    }

    protected KeyChainProtectionParams(android.os.Parcel parcel) {
        this.mUserSecretType = java.lang.Integer.valueOf(parcel.readInt());
        this.mLockScreenUiFormat = java.lang.Integer.valueOf(parcel.readInt());
        this.mKeyDerivationParams = (android.security.keystore.recovery.KeyDerivationParams) parcel.readTypedObject(android.security.keystore.recovery.KeyDerivationParams.CREATOR);
        this.mSecret = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
