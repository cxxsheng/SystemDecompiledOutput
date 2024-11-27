package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class OtherName extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier typeID;
    private final com.android.internal.org.bouncycastle.asn1.ASN1Encodable value;

    public static com.android.internal.org.bouncycastle.asn1.x509.OtherName getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.OtherName) {
            return (com.android.internal.org.bouncycastle.asn1.x509.OtherName) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.OtherName(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public OtherName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.typeID = aSN1ObjectIdentifier;
        this.value = aSN1Encodable;
    }

    private OtherName(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.typeID = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.value = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getObject();
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getTypeID() {
        return this.typeID;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getValue() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.typeID);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.value));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
