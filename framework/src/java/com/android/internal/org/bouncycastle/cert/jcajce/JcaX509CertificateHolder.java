package com.android.internal.org.bouncycastle.cert.jcajce;

/* loaded from: classes4.dex */
public class JcaX509CertificateHolder extends com.android.internal.org.bouncycastle.cert.X509CertificateHolder {
    public JcaX509CertificateHolder(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException {
        super(com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(x509Certificate.getEncoded()));
    }
}
