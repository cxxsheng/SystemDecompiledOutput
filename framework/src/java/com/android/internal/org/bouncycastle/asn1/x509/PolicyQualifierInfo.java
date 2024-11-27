package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class PolicyQualifierInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyQualifierId;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable qualifier;

    public PolicyQualifierInfo(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.policyQualifierId = aSN1ObjectIdentifier;
        this.qualifier = aSN1Encodable;
    }

    public PolicyQualifierInfo(java.lang.String str) {
        this.policyQualifierId = com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierId.id_qt_cps;
        this.qualifier = new com.android.internal.org.bouncycastle.asn1.DERIA5String(str);
    }

    public PolicyQualifierInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.policyQualifierId = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.qualifier = aSN1Sequence.getObjectAt(1);
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierInfo) {
            return (com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getPolicyQualifierId() {
        return this.policyQualifierId;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getQualifier() {
        return this.qualifier;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.policyQualifierId);
        aSN1EncodableVector.add(this.qualifier);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
