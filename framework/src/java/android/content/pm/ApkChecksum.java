package android.content.pm;

/* loaded from: classes.dex */
public final class ApkChecksum implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ApkChecksum> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ApkChecksum>() { // from class: android.content.pm.ApkChecksum.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApkChecksum[] newArray(int i) {
            return new android.content.pm.ApkChecksum[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApkChecksum createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ApkChecksum(parcel);
        }
    };
    private final android.content.pm.Checksum mChecksum;
    private final byte[] mInstallerCertificate;
    private final java.lang.String mInstallerPackageName;
    private final java.lang.String mSplitName;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ApkChecksum(java.lang.String str, int i, byte[] bArr) {
        this(str, r0, null, null);
        android.content.pm.Checksum checksum = new android.content.pm.Checksum(i, bArr);
    }

    public ApkChecksum(java.lang.String str, int i, byte[] bArr, java.lang.String str2, java.security.cert.Certificate certificate) throws java.security.cert.CertificateEncodingException {
        this(str, new android.content.pm.Checksum(i, bArr), str2, certificate != null ? certificate.getEncoded() : null);
    }

    public int getType() {
        return this.mChecksum.getType();
    }

    public byte[] getValue() {
        return this.mChecksum.getValue();
    }

    public byte[] getInstallerCertificateBytes() {
        return this.mInstallerCertificate;
    }

    public java.security.cert.Certificate getInstallerCertificate() throws java.security.cert.CertificateException {
        if (this.mInstallerCertificate == null) {
            return null;
        }
        return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(this.mInstallerCertificate));
    }

    public ApkChecksum(java.lang.String str, android.content.pm.Checksum checksum, java.lang.String str2, byte[] bArr) {
        this.mSplitName = str;
        this.mChecksum = checksum;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mChecksum);
        this.mInstallerPackageName = str2;
        this.mInstallerCertificate = bArr;
    }

    public java.lang.String getSplitName() {
        return this.mSplitName;
    }

    public java.lang.String getInstallerPackageName() {
        return this.mInstallerPackageName;
    }

    public java.lang.String toString() {
        return "ApkChecksum { splitName = " + this.mSplitName + ", checksum = " + this.mChecksum + ", installerPackageName = " + this.mInstallerPackageName + ", installerCertificate = " + java.util.Arrays.toString(this.mInstallerCertificate) + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mSplitName != null ? (byte) 1 : (byte) 0;
        if (this.mInstallerPackageName != null) {
            b = (byte) (b | 4);
        }
        if (this.mInstallerCertificate != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        if (this.mSplitName != null) {
            parcel.writeString(this.mSplitName);
        }
        parcel.writeTypedObject(this.mChecksum, i);
        if (this.mInstallerPackageName != null) {
            parcel.writeString(this.mInstallerPackageName);
        }
        if (this.mInstallerCertificate != null) {
            parcel.writeByteArray(this.mInstallerCertificate);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ApkChecksum(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        java.lang.String readString = (readByte & 1) == 0 ? null : parcel.readString();
        android.content.pm.Checksum checksum = (android.content.pm.Checksum) parcel.readTypedObject(android.content.pm.Checksum.CREATOR);
        java.lang.String readString2 = (readByte & 4) == 0 ? null : parcel.readString();
        byte[] createByteArray = (readByte & 8) == 0 ? null : parcel.createByteArray();
        this.mSplitName = readString;
        this.mChecksum = checksum;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mChecksum);
        this.mInstallerPackageName = readString2;
        this.mInstallerCertificate = createByteArray;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
