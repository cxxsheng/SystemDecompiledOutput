package android.security.keystore.recovery;

/* loaded from: classes3.dex */
public final class RecoveryCertPath implements android.os.Parcelable {
    private static final java.lang.String CERT_PATH_ENCODING = "PkiPath";
    public static final android.os.Parcelable.Creator<android.security.keystore.recovery.RecoveryCertPath> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.recovery.RecoveryCertPath>() { // from class: android.security.keystore.recovery.RecoveryCertPath.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.RecoveryCertPath createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.recovery.RecoveryCertPath(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.recovery.RecoveryCertPath[] newArray(int i) {
            return new android.security.keystore.recovery.RecoveryCertPath[i];
        }
    };
    private final byte[] mEncodedCertPath;

    public static android.security.keystore.recovery.RecoveryCertPath createRecoveryCertPath(java.security.cert.CertPath certPath) throws java.security.cert.CertificateException {
        try {
            return new android.security.keystore.recovery.RecoveryCertPath(encodeCertPath(certPath));
        } catch (java.security.cert.CertificateEncodingException e) {
            throw new java.security.cert.CertificateException("Failed to encode the given CertPath", e);
        }
    }

    public java.security.cert.CertPath getCertPath() throws java.security.cert.CertificateException {
        return decodeCertPath(this.mEncodedCertPath);
    }

    private RecoveryCertPath(byte[] bArr) {
        this.mEncodedCertPath = (byte[]) java.util.Objects.requireNonNull(bArr);
    }

    private RecoveryCertPath(android.os.Parcel parcel) {
        this.mEncodedCertPath = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mEncodedCertPath);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static byte[] encodeCertPath(java.security.cert.CertPath certPath) throws java.security.cert.CertificateEncodingException {
        java.util.Objects.requireNonNull(certPath);
        return certPath.getEncoded(CERT_PATH_ENCODING);
    }

    private static java.security.cert.CertPath decodeCertPath(byte[] bArr) throws java.security.cert.CertificateException {
        java.util.Objects.requireNonNull(bArr);
        try {
            return java.security.cert.CertificateFactory.getInstance("X.509").generateCertPath(new java.io.ByteArrayInputStream(bArr), CERT_PATH_ENCODING);
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
