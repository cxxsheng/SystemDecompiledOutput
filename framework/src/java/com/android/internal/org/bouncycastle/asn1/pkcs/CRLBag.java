package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class CRLBag extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier crlId;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable crlValue;

    private CRLBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.crlId = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.crlValue = ((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1)).getObject();
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.CRLBag getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.CRLBag) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.CRLBag) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.CRLBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CRLBag(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.crlId = aSN1ObjectIdentifier;
        this.crlValue = aSN1Encodable;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getCrlId() {
        return this.crlId;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getCrlValue() {
        return this.crlValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.crlId);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(0, this.crlValue));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
