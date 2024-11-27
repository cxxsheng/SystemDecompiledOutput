package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class Attribute extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier attrType;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set attrValues;

    public static com.android.internal.org.bouncycastle.asn1.cms.Attribute getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.Attribute) {
            return (com.android.internal.org.bouncycastle.asn1.cms.Attribute) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private Attribute(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.attrType = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.attrValues = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Sequence.getObjectAt(1);
    }

    public Attribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.attrType = aSN1ObjectIdentifier;
        this.attrValues = aSN1Set;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getAttrType() {
        return this.attrType;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getAttrValues() {
        return this.attrValues;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] getAttributeValues() {
        return this.attrValues.toArray();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.attrType);
        aSN1EncodableVector.add(this.attrValues);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
