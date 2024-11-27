package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public class AttributeTypeAndValue extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier type;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable value;

    private AttributeTypeAndValue(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.type = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.value = aSN1Sequence.getObjectAt(1);
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue) {
            return (com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        throw new java.lang.IllegalArgumentException("null value in getInstance()");
    }

    public AttributeTypeAndValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.type = aSN1ObjectIdentifier;
        this.value = aSN1Encodable;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getType() {
        return this.type;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getValue() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.type);
        aSN1EncodableVector.add(this.value);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
