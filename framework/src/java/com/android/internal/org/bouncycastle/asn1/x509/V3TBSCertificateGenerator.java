package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class V3TBSCertificateGenerator {
    private boolean altNamePresentAndCritical;
    com.android.internal.org.bouncycastle.asn1.x509.Time endDate;
    com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    private com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueID;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    com.android.internal.org.bouncycastle.asn1.x509.Time startDate;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name subject;
    com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo;
    private com.android.internal.org.bouncycastle.asn1.DERBitString subjectUniqueID;
    com.android.internal.org.bouncycastle.asn1.DERTaggedObject version = new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, new com.android.internal.org.bouncycastle.asn1.ASN1Integer(2));

    public void setSerialNumber(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.serialNumber = aSN1Integer;
    }

    public void setSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this.signature = algorithmIdentifier;
    }

    public void setIssuer(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name);
    }

    public void setIssuer(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.issuer = x500Name;
    }

    public void setStartDate(com.android.internal.org.bouncycastle.asn1.ASN1UTCTime aSN1UTCTime) {
        this.startDate = new com.android.internal.org.bouncycastle.asn1.x509.Time(aSN1UTCTime);
    }

    public void setStartDate(com.android.internal.org.bouncycastle.asn1.x509.Time time) {
        this.startDate = time;
    }

    public void setEndDate(com.android.internal.org.bouncycastle.asn1.ASN1UTCTime aSN1UTCTime) {
        this.endDate = new com.android.internal.org.bouncycastle.asn1.x509.Time(aSN1UTCTime);
    }

    public void setEndDate(com.android.internal.org.bouncycastle.asn1.x509.Time time) {
        this.endDate = time;
    }

    public void setSubject(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.subject = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setSubject(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.subject = x500Name;
    }

    public void setIssuerUniqueID(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.issuerUniqueID = dERBitString;
    }

    public void setSubjectUniqueID(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.subjectUniqueID = dERBitString;
    }

    public void setSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.subjectPublicKeyInfo = subjectPublicKeyInfo;
    }

    public void setExtensions(com.android.internal.org.bouncycastle.asn1.x509.X509Extensions x509Extensions) {
        setExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(x509Extensions));
    }

    public void setExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        this.extensions = extensions;
        if (extensions != null && (extension = extensions.getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName)) != null && extension.isCritical()) {
            this.altNamePresentAndCritical = true;
        }
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate generateTBSCertificate() {
        if (this.serialNumber == null || this.signature == null || this.issuer == null || this.startDate == null || this.endDate == null || ((this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)) {
            throw new java.lang.IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(10);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.serialNumber);
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.issuer);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector2.add(this.startDate);
        aSN1EncodableVector2.add(this.endDate);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
        if (this.subject != null) {
            aSN1EncodableVector.add(this.subject);
        } else {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence());
        }
        aSN1EncodableVector.add(this.subjectPublicKeyInfo);
        if (this.issuerUniqueID != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.issuerUniqueID));
        }
        if (this.subjectUniqueID != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.subjectUniqueID));
        }
        if (this.extensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 3, this.extensions));
        }
        return com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }
}
