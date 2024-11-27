package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class OCSPRequest extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ocsp.Signature optionalSignature;
    com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest tbsRequest;

    public OCSPRequest(com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest tBSRequest, com.android.internal.org.bouncycastle.asn1.ocsp.Signature signature) {
        this.tbsRequest = tBSRequest;
        this.optionalSignature = signature;
    }

    private OCSPRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.tbsRequest = com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.optionalSignature = com.android.internal.org.bouncycastle.asn1.ocsp.Signature.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest getTbsRequest() {
        return this.tbsRequest;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.Signature getOptionalSignature() {
        return this.optionalSignature;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.tbsRequest);
        if (this.optionalSignature != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.optionalSignature));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
