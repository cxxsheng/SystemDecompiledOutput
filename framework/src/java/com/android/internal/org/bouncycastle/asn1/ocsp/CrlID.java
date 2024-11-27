package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class CrlID extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer crlNum;
    private com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime crlTime;
    private com.android.internal.org.bouncycastle.asn1.DERIA5String crlUrl;

    private CrlID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objects.nextElement();
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.crlUrl = com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.crlNum = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, true);
                    break;
                case 2:
                    this.crlTime = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unknown tag number: " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.CrlID getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.CrlID) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.CrlID) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.CrlID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.DERIA5String getCrlUrl() {
        return this.crlUrl;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getCrlNum() {
        return this.crlNum;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getCrlTime() {
        return this.crlTime;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (this.crlUrl != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.crlUrl));
        }
        if (this.crlNum != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.crlNum));
        }
        if (this.crlTime != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 2, this.crlTime));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
