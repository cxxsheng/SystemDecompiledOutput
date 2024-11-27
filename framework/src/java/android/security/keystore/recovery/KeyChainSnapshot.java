package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class KeyChainSnapshot implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keystore.recovery.KeyChainSnapshot> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.recovery.KeyChainSnapshot>() { // from class: android.security.keystore.recovery.KeyChainSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyChainSnapshot createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.recovery.KeyChainSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyChainSnapshot[] newArray(int i) {
            return new android.security.keystore.recovery.KeyChainSnapshot[i];
        }
    };
    private static final long DEFAULT_COUNTER_ID = 1;
    private static final int DEFAULT_MAX_ATTEMPTS = 10;
    private android.security.keystore.recovery.RecoveryCertPath mCertPath;
    private long mCounterId;
    private byte[] mEncryptedRecoveryKeyBlob;
    private java.util.List<android.security.keystore.recovery.WrappedApplicationKey> mEntryRecoveryData;
    private java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> mKeyChainProtectionParams;
    private int mMaxAttempts;
    private byte[] mServerParams;
    private int mSnapshotVersion;

    private KeyChainSnapshot() {
        this.mMaxAttempts = 10;
        this.mCounterId = 1L;
    }

    public int getSnapshotVersion() {
        return this.mSnapshotVersion;
    }

    public int getMaxAttempts() {
        return this.mMaxAttempts;
    }

    public long getCounterId() {
        return this.mCounterId;
    }

    public byte[] getServerParams() {
        return this.mServerParams;
    }

    public java.security.cert.CertPath getTrustedHardwareCertPath() {
        try {
            return this.mCertPath.getCertPath();
        } catch (java.security.cert.CertificateException e) {
            throw new android.os.BadParcelableException(e);
        }
    }

    public java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> getKeyChainProtectionParams() {
        return this.mKeyChainProtectionParams;
    }

    public java.util.List<android.security.keystore.recovery.WrappedApplicationKey> getWrappedApplicationKeys() {
        return this.mEntryRecoveryData;
    }

    public byte[] getEncryptedRecoveryKeyBlob() {
        return this.mEncryptedRecoveryKeyBlob;
    }

    public static class Builder {
        private android.security.keystore.recovery.KeyChainSnapshot mInstance = new android.security.keystore.recovery.KeyChainSnapshot();

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setSnapshotVersion(int i) {
            this.mInstance.mSnapshotVersion = i;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setMaxAttempts(int i) {
            this.mInstance.mMaxAttempts = i;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setCounterId(long j) {
            this.mInstance.mCounterId = j;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setServerParams(byte[] bArr) {
            this.mInstance.mServerParams = bArr;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setTrustedHardwareCertPath(java.security.cert.CertPath certPath) throws java.security.cert.CertificateException {
            this.mInstance.mCertPath = android.security.keystore.recovery.RecoveryCertPath.createRecoveryCertPath(certPath);
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setKeyChainProtectionParams(java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) {
            this.mInstance.mKeyChainProtectionParams = list;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setWrappedApplicationKeys(java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) {
            this.mInstance.mEntryRecoveryData = list;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot.Builder setEncryptedRecoveryKeyBlob(byte[] bArr) {
            this.mInstance.mEncryptedRecoveryKeyBlob = bArr;
            return this;
        }

        public android.security.keystore.recovery.KeyChainSnapshot build() {
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mInstance.mKeyChainProtectionParams, "keyChainProtectionParams");
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mInstance.mEntryRecoveryData, "entryRecoveryData");
            java.util.Objects.requireNonNull(this.mInstance.mEncryptedRecoveryKeyBlob);
            java.util.Objects.requireNonNull(this.mInstance.mServerParams);
            java.util.Objects.requireNonNull(this.mInstance.mCertPath);
            return this.mInstance;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSnapshotVersion);
        parcel.writeTypedList(this.mKeyChainProtectionParams);
        parcel.writeByteArray(this.mEncryptedRecoveryKeyBlob);
        parcel.writeTypedList(this.mEntryRecoveryData);
        parcel.writeInt(this.mMaxAttempts);
        parcel.writeLong(this.mCounterId);
        parcel.writeByteArray(this.mServerParams);
        parcel.writeTypedObject(this.mCertPath, 0);
    }

    protected KeyChainSnapshot(android.os.Parcel parcel) {
        this.mMaxAttempts = 10;
        this.mCounterId = 1L;
        this.mSnapshotVersion = parcel.readInt();
        this.mKeyChainProtectionParams = parcel.createTypedArrayList(android.security.keystore.recovery.KeyChainProtectionParams.CREATOR);
        this.mEncryptedRecoveryKeyBlob = parcel.createByteArray();
        this.mEntryRecoveryData = parcel.createTypedArrayList(android.security.keystore.recovery.WrappedApplicationKey.CREATOR);
        this.mMaxAttempts = parcel.readInt();
        this.mCounterId = parcel.readLong();
        this.mServerParams = parcel.createByteArray();
        this.mCertPath = (android.security.keystore.recovery.RecoveryCertPath) parcel.readTypedObject(android.security.keystore.recovery.RecoveryCertPath.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
