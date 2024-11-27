package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class BasicOCSPResponse extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence certs;
    private com.android.internal.org.bouncycastle.asn1.DERBitString signature;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signatureAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData tbsResponseData;

    public BasicOCSPResponse(com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData responseData, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.tbsResponseData = responseData;
        this.signatureAlgorithm = algorithmIdentifier;
        this.signature = dERBitString;
        this.certs = aSN1Sequence;
    }

    private BasicOCSPResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.tbsResponseData = com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData.getInstance(aSN1Sequence.getObjectAt(0));
        this.signatureAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.signature = (com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1Sequence.getObjectAt(2);
        if (aSN1Sequence.size() > 3) {
            this.certs = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(3), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData getTbsResponseData() {
        return this.tbsResponseData;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSignature() {
        return this.signature;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Sequence getCerts() {
        return this.certs;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.tbsResponseData);
        aSN1EncodableVector.add(this.signatureAlgorithm);
        aSN1EncodableVector.add(this.signature);
        if (this.certs != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.certs));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
