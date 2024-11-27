package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class CertificationRequest extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    protected com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo reqInfo;
    protected com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    protected com.android.internal.org.bouncycastle.asn1.DERBitString sigBits;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequest getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequest) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequest) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    protected CertificationRequest() {
        this.reqInfo = null;
        this.sigAlgId = null;
        this.sigBits = null;
    }

    public CertificationRequest(com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo certificationRequestInfo, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.reqInfo = null;
        this.sigAlgId = null;
        this.sigBits = null;
        this.reqInfo = certificationRequestInfo;
        this.sigAlgId = algorithmIdentifier;
        this.sigBits = dERBitString;
    }

    public CertificationRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.reqInfo = null;
        this.sigAlgId = null;
        this.sigBits = null;
        this.reqInfo = com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.sigAlgId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.sigBits = (com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1Sequence.getObjectAt(2);
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo getCertificationRequestInfo() {
        return this.reqInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSignature() {
        return this.sigBits;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.reqInfo);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(this.sigBits);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
