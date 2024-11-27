package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsiEncryptionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ImsiEncryptionInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.ImsiEncryptionInfo>() { // from class: android.telephony.ImsiEncryptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ImsiEncryptionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ImsiEncryptionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ImsiEncryptionInfo[] newArray(int i) {
            return new android.telephony.ImsiEncryptionInfo[i];
        }
    };
    private static final java.lang.String LOG_TAG = "ImsiEncryptionInfo";
    private final int carrierId;
    private final java.util.Date expirationTime;
    private final java.lang.String keyIdentifier;
    private final int keyType;
    private final java.lang.String mcc;
    private final java.lang.String mnc;
    private final java.security.PublicKey publicKey;

    public ImsiEncryptionInfo(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, byte[] bArr, java.util.Date date, int i2) {
        this(str, str2, i, str3, makeKeyObject(bArr), date, i2);
    }

    public ImsiEncryptionInfo(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.security.PublicKey publicKey, java.util.Date date, int i2) {
        this.mcc = str;
        this.mnc = str2;
        this.keyType = i;
        this.publicKey = publicKey;
        this.keyIdentifier = str3;
        this.expirationTime = date;
        this.carrierId = i2;
    }

    public ImsiEncryptionInfo(android.os.Parcel parcel) {
        byte[] bArr = new byte[parcel.readInt()];
        parcel.readByteArray(bArr);
        this.publicKey = makeKeyObject(bArr);
        this.mcc = parcel.readString();
        this.mnc = parcel.readString();
        this.keyIdentifier = parcel.readString();
        this.keyType = parcel.readInt();
        this.expirationTime = new java.util.Date(parcel.readLong());
        this.carrierId = parcel.readInt();
    }

    public java.lang.String getMnc() {
        return this.mnc;
    }

    public java.lang.String getMcc() {
        return this.mcc;
    }

    public int getCarrierId() {
        return this.carrierId;
    }

    public java.lang.String getKeyIdentifier() {
        return this.keyIdentifier;
    }

    public int getKeyType() {
        return this.keyType;
    }

    public java.security.PublicKey getPublicKey() {
        return this.publicKey;
    }

    public java.util.Date getExpirationTime() {
        return this.expirationTime;
    }

    private static java.security.PublicKey makeKeyObject(byte[] bArr) {
        try {
            return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA).generatePublic(new java.security.spec.X509EncodedKeySpec(bArr));
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
            android.util.Log.e(LOG_TAG, "Error makeKeyObject: unable to convert into PublicKey", e);
            throw new java.lang.IllegalArgumentException();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte[] encoded = this.publicKey.getEncoded();
        parcel.writeInt(encoded.length);
        parcel.writeByteArray(encoded);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.keyIdentifier);
        parcel.writeInt(this.keyType);
        parcel.writeLong(this.expirationTime.getTime());
        parcel.writeInt(this.carrierId);
    }

    public java.lang.String toString() {
        return "[ImsiEncryptionInfo mcc=" + this.mcc + " mnc=" + this.mnc + ", publicKey=" + this.publicKey + ", keyIdentifier=" + this.keyIdentifier + ", keyType=" + this.keyType + ", expirationTime=" + this.expirationTime + ", carrier_id=" + this.carrierId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
