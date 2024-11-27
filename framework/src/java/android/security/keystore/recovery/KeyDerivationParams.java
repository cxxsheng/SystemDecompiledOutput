package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class KeyDerivationParams implements android.os.Parcelable {
    public static final int ALGORITHM_SCRYPT = 2;
    public static final int ALGORITHM_SHA256 = 1;
    public static final android.os.Parcelable.Creator<android.security.keystore.recovery.KeyDerivationParams> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.recovery.KeyDerivationParams>() { // from class: android.security.keystore.recovery.KeyDerivationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyDerivationParams createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.recovery.KeyDerivationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.KeyDerivationParams[] newArray(int i) {
            return new android.security.keystore.recovery.KeyDerivationParams[i];
        }
    };
    private final int mAlgorithm;
    private final int mMemoryDifficulty;
    private final byte[] mSalt;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KeyDerivationAlgorithm {
    }

    public static android.security.keystore.recovery.KeyDerivationParams createSha256Params(byte[] bArr) {
        return new android.security.keystore.recovery.KeyDerivationParams(1, bArr);
    }

    public static android.security.keystore.recovery.KeyDerivationParams createScryptParams(byte[] bArr, int i) {
        return new android.security.keystore.recovery.KeyDerivationParams(2, bArr, i);
    }

    private KeyDerivationParams(int i, byte[] bArr) {
        this(i, bArr, -1);
    }

    private KeyDerivationParams(int i, byte[] bArr, int i2) {
        this.mAlgorithm = i;
        this.mSalt = (byte[]) java.util.Objects.requireNonNull(bArr);
        this.mMemoryDifficulty = i2;
    }

    public int getAlgorithm() {
        return this.mAlgorithm;
    }

    public byte[] getSalt() {
        return this.mSalt;
    }

    public int getMemoryDifficulty() {
        return this.mMemoryDifficulty;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAlgorithm);
        parcel.writeByteArray(this.mSalt);
        parcel.writeInt(this.mMemoryDifficulty);
    }

    protected KeyDerivationParams(android.os.Parcel parcel) {
        this.mAlgorithm = parcel.readInt();
        this.mSalt = parcel.createByteArray();
        this.mMemoryDifficulty = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
