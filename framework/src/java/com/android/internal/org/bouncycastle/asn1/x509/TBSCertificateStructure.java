package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class TBSCertificateStructure extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    com.android.internal.org.bouncycastle.asn1.x509.Time endDate;
    com.android.internal.org.bouncycastle.asn1.x509.X509Extensions extensions;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueId;
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    com.android.internal.org.bouncycastle.asn1.x509.Time startDate;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name subject;
    com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo;
    com.android.internal.org.bouncycastle.asn1.DERBitString subjectUniqueId;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure) {
            return (com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertificateStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public TBSCertificateStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int i;
        this.seq = aSN1Sequence;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
            i = 0;
        } else {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L);
            i = -1;
        }
        this.serialNumber = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.signature = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 2));
        this.issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(i + 3));
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(i + 4);
        this.startDate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence2.getObjectAt(0));
        this.endDate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence2.getObjectAt(1));
        this.subject = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(i + 5));
        int i2 = i + 6;
        this.subjectPublicKeyInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(i2));
        for (int size = (aSN1Sequence.size() - i2) - 1; size > 0; size--) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i2 + size));
            switch (aSN1TaggedObject.getTagNo()) {
                case 1:
                    this.issuerUniqueId = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.subjectUniqueId = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 3:
                    this.extensions = com.android.internal.org.bouncycastle.asn1.x509.X509Extensions.getInstance(aSN1TaggedObject);
                    break;
            }
        }
    }

    public int getVersion() {
        return this.version.intValueExact() + 1;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersionNumber() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.issuer;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getStartDate() {
        return this.startDate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getEndDate() {
        return this.endDate;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubject() {
        return this.subject;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.X509Extensions getExtensions() {
        return this.extensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}
