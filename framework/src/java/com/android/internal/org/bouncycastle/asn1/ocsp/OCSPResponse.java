package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class OCSPResponse extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes responseBytes;
    com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus responseStatus;

    public OCSPResponse(com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus oCSPResponseStatus, com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes responseBytes) {
        this.responseStatus = oCSPResponseStatus;
        this.responseBytes = responseBytes;
    }

    private OCSPResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.responseStatus = com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.responseBytes = com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes getResponseBytes() {
        return this.responseBytes;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.responseStatus);
        if (this.responseBytes != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.responseBytes));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
