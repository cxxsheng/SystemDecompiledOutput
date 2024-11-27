package com.android.internal.org.bouncycastle.x509.extension;

/* loaded from: classes4.dex */
public class AuthorityKeyIdentifierStructure extends com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier {
    public AuthorityKeyIdentifierStructure(byte[] bArr) throws java.io.IOException {
        super((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) com.android.internal.org.bouncycastle.x509.extension.X509ExtensionUtil.fromExtensionValue(bArr));
    }

    public AuthorityKeyIdentifierStructure(com.android.internal.org.bouncycastle.asn1.x509.X509Extension x509Extension) {
        super((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) x509Extension.getParsedValue());
    }

    public AuthorityKeyIdentifierStructure(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) {
        super((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) extension.getParsedValue());
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence fromCertificate(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateParsingException {
        try {
            if (x509Certificate.getVersion() != 3) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(x509Certificate.getPublicKey().getEncoded()), new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(com.android.internal.org.bouncycastle.jce.PrincipalUtil.getIssuerX509Principal(x509Certificate))), x509Certificate.getSerialNumber()).toASN1Primitive();
            }
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(com.android.internal.org.bouncycastle.jce.PrincipalUtil.getIssuerX509Principal(x509Certificate));
            byte[] extensionValue = x509Certificate.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectKeyIdentifier.getId());
            if (extensionValue != null) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier(((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.x509.extension.X509ExtensionUtil.fromExtensionValue(extensionValue)).getOctets(), new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(generalName), x509Certificate.getSerialNumber()).toASN1Primitive();
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(x509Certificate.getPublicKey().getEncoded()), new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(generalName), x509Certificate.getSerialNumber()).toASN1Primitive();
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("Exception extracting certificate details: " + e.toString());
        }
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence fromKey(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        try {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())).toASN1Primitive();
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException("can't process key: " + e);
        }
    }

    public AuthorityKeyIdentifierStructure(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateParsingException {
        super(fromCertificate(x509Certificate));
    }

    public AuthorityKeyIdentifierStructure(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        super(fromKey(publicKey));
    }
}
