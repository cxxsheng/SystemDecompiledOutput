package android.util.apk;

/* loaded from: classes3.dex */
class VerbatimX509Certificate extends android.util.apk.WrappedX509Certificate {
    private final byte[] mEncodedVerbatim;
    private int mHash;

    VerbatimX509Certificate(java.security.cert.X509Certificate x509Certificate, byte[] bArr) {
        super(x509Certificate);
        this.mHash = -1;
        this.mEncodedVerbatim = bArr;
    }

    @Override // android.util.apk.WrappedX509Certificate, java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        return this.mEncodedVerbatim;
    }

    @Override // java.security.cert.Certificate
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.util.apk.VerbatimX509Certificate)) {
            return false;
        }
        try {
            return java.util.Arrays.equals(getEncoded(), ((android.util.apk.VerbatimX509Certificate) obj).getEncoded());
        } catch (java.security.cert.CertificateEncodingException e) {
            return false;
        }
    }

    @Override // java.security.cert.Certificate
    public int hashCode() {
        if (this.mHash == -1) {
            try {
                this.mHash = java.util.Arrays.hashCode(getEncoded());
            } catch (java.security.cert.CertificateEncodingException e) {
                this.mHash = 0;
            }
        }
        return this.mHash;
    }
}
