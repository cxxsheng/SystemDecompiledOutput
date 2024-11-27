package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class CertID extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier hashAlgorithm;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString issuerKeyHash;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString issuerNameHash;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;

    public CertID(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString2, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.hashAlgorithm = algorithmIdentifier;
        this.issuerNameHash = aSN1OctetString;
        this.issuerKeyHash = aSN1OctetString2;
        this.serialNumber = aSN1Integer;
    }

    private CertID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.hashAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.issuerNameHash = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Sequence.getObjectAt(1);
        this.issuerKeyHash = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Sequence.getObjectAt(2);
        this.serialNumber = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(3);
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.CertID getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.CertID getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.CertID) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.CertID) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.CertID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getIssuerNameHash() {
        return this.issuerNameHash;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getIssuerKeyHash() {
        return this.issuerKeyHash;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.hashAlgorithm);
        aSN1EncodableVector.add(this.issuerNameHash);
        aSN1EncodableVector.add(this.issuerKeyHash);
        aSN1EncodableVector.add(this.serialNumber);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
