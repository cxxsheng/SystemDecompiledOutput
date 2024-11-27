package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class CertBag extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certId;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable certValue;

    private CertBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.certId = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.certValue = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getObject();
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.CertBag getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.CertBag) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.CertBag) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.CertBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertBag(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.certId = aSN1ObjectIdentifier;
        this.certValue = aSN1Encodable;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getCertId() {
        return this.certId;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getCertValue() {
        return this.certValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.certId);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(0, this.certValue));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
