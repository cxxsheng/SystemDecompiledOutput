package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509CRLInternal extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl {
    private final byte[] encoding;

    X509CRLInternal(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList, java.lang.String str, byte[] bArr, boolean z, byte[] bArr2) {
        super(jcaJceHelper, certificateList, str, bArr, z);
        this.encoding = bArr2;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl, java.security.cert.X509CRL
    public byte[] getEncoded() throws java.security.cert.CRLException {
        if (this.encoding == null) {
            throw new java.security.cert.CRLException();
        }
        return this.encoding;
    }
}
