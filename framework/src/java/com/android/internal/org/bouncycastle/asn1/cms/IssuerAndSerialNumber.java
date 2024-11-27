package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class IssuerAndSerialNumber extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x500.X500Name name;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;

    public static com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber) {
            return (com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(0));
        this.serialNumber = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) {
        this.name = certificate.getIssuer();
        this.serialNumber = certificate.getSerialNumber();
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.x509.X509CertificateStructure x509CertificateStructure) {
        this.name = x509CertificateStructure.getIssuer();
        this.serialNumber = x509CertificateStructure.getSerialNumber();
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger) {
        this.name = x500Name;
        this.serialNumber = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name, java.math.BigInteger bigInteger) {
        this.name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name);
        this.serialNumber = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name);
        this.serialNumber = aSN1Integer;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getName() {
        return this.name;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.name);
        aSN1EncodableVector.add(this.serialNumber);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
