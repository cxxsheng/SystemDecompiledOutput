package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509CertificateStructure extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    com.android.internal.org.bouncycastle.asn1.DERBitString sig;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure tbsCert;

    public static com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure) {
            return (com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public X509CertificateStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        if (aSN1Sequence.size() == 3) {
            this.tbsCert = com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure.getInstance(aSN1Sequence.getObjectAt(0));
            this.sigAlgId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sig = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
            return;
        }
        throw new java.lang.IllegalArgumentException("sequence wrong size for a certificate");
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure getTBSCertificate() {
        return this.tbsCert;
    }

    public int getVersion() {
        return this.tbsCert.getVersion();
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.tbsCert.getSerialNumber();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.tbsCert.getIssuer();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getStartDate() {
        return this.tbsCert.getStartDate();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getEndDate() {
        return this.tbsCert.getEndDate();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubject() {
        return this.tbsCert.getSubject();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.tbsCert.getSubjectPublicKeyInfo();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSignature() {
        return this.sig;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}
