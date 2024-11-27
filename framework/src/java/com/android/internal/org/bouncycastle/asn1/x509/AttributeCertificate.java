package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AttributeCertificate extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo acinfo;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signatureAlgorithm;
    com.android.internal.org.bouncycastle.asn1.DERBitString signatureValue;

    public static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AttributeCertificate(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo attributeCertificateInfo, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.acinfo = attributeCertificateInfo;
        this.signatureAlgorithm = algorithmIdentifier;
        this.signatureValue = dERBitString;
    }

    public AttributeCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.acinfo = com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.signatureAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.signatureValue = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo getAcinfo() {
        return this.acinfo;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSignatureValue() {
        return this.signatureValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.acinfo);
        aSN1EncodableVector.add(this.signatureAlgorithm);
        aSN1EncodableVector.add(this.signatureValue);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
