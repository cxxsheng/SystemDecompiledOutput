package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class WrappedApplicationKey implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keystore.recovery.WrappedApplicationKey> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.recovery.WrappedApplicationKey>() { // from class: android.security.keystore.recovery.WrappedApplicationKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.WrappedApplicationKey createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.recovery.WrappedApplicationKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.WrappedApplicationKey[] newArray(int i) {
            return new android.security.keystore.recovery.WrappedApplicationKey[i];
        }
    };
    private java.lang.String mAlias;
    private byte[] mEncryptedKeyMaterial;
    private byte[] mMetadata;

    public static class Builder {
        private android.security.keystore.recovery.WrappedApplicationKey mInstance = new android.security.keystore.recovery.WrappedApplicationKey();

        public android.security.keystore.recovery.WrappedApplicationKey.Builder setAlias(java.lang.String str) {
            this.mInstance.mAlias = str;
            return this;
        }

        public android.security.keystore.recovery.WrappedApplicationKey.Builder setEncryptedKeyMaterial(byte[] bArr) {
            this.mInstance.mEncryptedKeyMaterial = bArr;
            return this;
        }

        public android.security.keystore.recovery.WrappedApplicationKey.Builder setMetadata(byte[] bArr) {
            this.mInstance.mMetadata = bArr;
            return this;
        }

        public android.security.keystore.recovery.WrappedApplicationKey build() {
            java.util.Objects.requireNonNull(this.mInstance.mAlias);
            java.util.Objects.requireNonNull(this.mInstance.mEncryptedKeyMaterial);
            return this.mInstance;
        }
    }

    private WrappedApplicationKey() {
    }

    @java.lang.Deprecated
    public WrappedApplicationKey(java.lang.String str, byte[] bArr) {
        this.mAlias = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mEncryptedKeyMaterial = (byte[]) java.util.Objects.requireNonNull(bArr);
    }

    public java.lang.String getAlias() {
        return this.mAlias;
    }

    public byte[] getEncryptedKeyMaterial() {
        return this.mEncryptedKeyMaterial;
    }

    public byte[] getMetadata() {
        return this.mMetadata;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mAlias);
        parcel.writeByteArray(this.mEncryptedKeyMaterial);
        parcel.writeByteArray(this.mMetadata);
    }

    protected WrappedApplicationKey(android.os.Parcel parcel) {
        this.mAlias = parcel.readString();
        this.mEncryptedKeyMaterial = parcel.createByteArray();
        this.mMetadata = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
