package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class RevokedInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.CRLReason revocationReason;
    private com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime revocationTime;

    public RevokedInfo(com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.x509.CRLReason cRLReason) {
        this.revocationTime = aSN1GeneralizedTime;
        this.revocationReason = cRLReason;
    }

    private RevokedInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.revocationTime = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.revocationReason = com.android.internal.org.bouncycastle.asn1.x509.CRLReason.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true));
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getRevocationTime() {
        return this.revocationTime;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.CRLReason getRevocationReason() {
        return this.revocationReason;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.revocationTime);
        if (this.revocationReason != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.revocationReason));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
