package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class Certificate extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    com.android.internal.org.bouncycastle.asn1.DERBitString sig;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tbsCert;

    public static com.android.internal.org.bouncycastle.asn1.x509.Certificate getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.Certificate getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Certificate) {
            return (com.android.internal.org.bouncycastle.asn1.x509.Certificate) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.Certificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private Certificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        if (aSN1Sequence.size() == 3) {
            this.tbsCert = com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate.getInstance(aSN1Sequence.getObjectAt(0));
            this.sigAlgId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sig = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
            return;
        }
        throw new java.lang.IllegalArgumentException("sequence wrong size for a certificate");
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate getTBSCertificate() {
        return this.tbsCert;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.tbsCert.getVersion();
    }

    public int getVersionNumber() {
        return this.tbsCert.getVersionNumber();
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
