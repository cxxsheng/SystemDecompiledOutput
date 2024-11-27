package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AttCertValidityPeriod extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime notAfterTime;
    com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime notBeforeTime;

    public static com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private AttCertValidityPeriod(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.notBeforeTime = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(0));
        this.notAfterTime = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public AttCertValidityPeriod(com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime2) {
        this.notBeforeTime = aSN1GeneralizedTime;
        this.notAfterTime = aSN1GeneralizedTime2;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getNotBeforeTime() {
        return this.notBeforeTime;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getNotAfterTime() {
        return this.notAfterTime;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.notBeforeTime);
        aSN1EncodableVector.add(this.notAfterTime);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
