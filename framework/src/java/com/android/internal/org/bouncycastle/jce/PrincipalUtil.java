package com.android.internal.org.bouncycastle.jce;

/* loaded from: classes4.dex */
public class PrincipalUtil {
    public static com.android.internal.org.bouncycastle.jce.X509Principal getIssuerX509Principal(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException {
        try {
            return new com.android.internal.org.bouncycastle.jce.X509Principal(com.android.internal.org.bouncycastle.asn1.x509.X509Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(x509Certificate.getTBSCertificate())).getIssuer()));
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    public static com.android.internal.org.bouncycastle.jce.X509Principal getSubjectX509Principal(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException {
        try {
            return new com.android.internal.org.bouncycastle.jce.X509Principal(com.android.internal.org.bouncycastle.asn1.x509.X509Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(x509Certificate.getTBSCertificate())).getSubject()));
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    public static com.android.internal.org.bouncycastle.jce.X509Principal getIssuerX509Principal(java.security.cert.X509CRL x509crl) throws java.security.cert.CRLException {
        try {
            return new com.android.internal.org.bouncycastle.jce.X509Principal(com.android.internal.org.bouncycastle.asn1.x509.X509Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(x509crl.getTBSCertList())).getIssuer()));
        } catch (java.io.IOException e) {
            throw new java.security.cert.CRLException(e.toString());
        }
    }
}
