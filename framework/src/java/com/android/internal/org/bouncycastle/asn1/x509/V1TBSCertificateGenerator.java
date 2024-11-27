package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class V1TBSCertificateGenerator {
    com.android.internal.org.bouncycastle.asn1.x509.Time endDate;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    com.android.internal.org.bouncycastle.asn1.x509.Time startDate;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name subject;
    com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo;
    com.android.internal.org.bouncycastle.asn1.DERTaggedObject version = new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0));

    public void setSerialNumber(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.serialNumber = aSN1Integer;
    }

    public void setSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this.signature = algorithmIdentifier;
    }

    public void setIssuer(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setIssuer(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.issuer = x500Name;
    }

    public void setStartDate(com.android.internal.org.bouncycastle.asn1.x509.Time time) {
        this.startDate = time;
    }

    public void setStartDate(com.android.internal.org.bouncycastle.asn1.ASN1UTCTime aSN1UTCTime) {
        this.startDate = new com.android.internal.org.bouncycastle.asn1.x509.Time(aSN1UTCTime);
    }

    public void setEndDate(com.android.internal.org.bouncycastle.asn1.x509.Time time) {
        this.endDate = time;
    }

    public void setEndDate(com.android.internal.org.bouncycastle.asn1.ASN1UTCTime aSN1UTCTime) {
        this.endDate = new com.android.internal.org.bouncycastle.asn1.x509.Time(aSN1UTCTime);
    }

    public void setSubject(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.subject = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setSubject(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.subject = x500Name;
    }

    public void setSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.subjectPublicKeyInfo = subjectPublicKeyInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate generateTBSCertificate() {
        if (this.serialNumber == null || this.signature == null || this.issuer == null || this.startDate == null || this.endDate == null || this.subject == null || this.subjectPublicKeyInfo == null) {
            throw new java.lang.IllegalStateException("not all mandatory fields set in V1 TBScertificate generator");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(6);
        aSN1EncodableVector.add(this.serialNumber);
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.issuer);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector2.add(this.startDate);
        aSN1EncodableVector2.add(this.endDate);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.subject);
        aSN1EncodableVector.add(this.subjectPublicKeyInfo);
        return com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }
}
