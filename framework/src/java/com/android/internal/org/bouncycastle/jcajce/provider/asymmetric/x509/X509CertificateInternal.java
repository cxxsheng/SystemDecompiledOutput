package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509CertificateInternal extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl {
    private final byte[] encoding;

    X509CertificateInternal(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate, com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints, boolean[] zArr, java.lang.String str, byte[] bArr, byte[] bArr2) {
        super(jcaJceHelper, certificate, basicConstraints, zArr, str, bArr);
        this.encoding = bArr2;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        if (this.encoding == null) {
            throw new java.security.cert.CertificateEncodingException();
        }
        return this.encoding;
    }
}
