package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public class RDN extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set values;

    private RDN(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.values = aSN1Set;
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.RDN getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.RDN) {
            return (com.android.internal.org.bouncycastle.asn1.x500.RDN) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x500.RDN(com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(obj));
        }
        return null;
    }

    public RDN(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(aSN1ObjectIdentifier);
        aSN1EncodableVector.add(aSN1Encodable);
        this.values = new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }

    public RDN(com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue attributeTypeAndValue) {
        this.values = new com.android.internal.org.bouncycastle.asn1.DERSet(attributeTypeAndValue);
    }

    public RDN(com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] attributeTypeAndValueArr) {
        this.values = new com.android.internal.org.bouncycastle.asn1.DERSet(attributeTypeAndValueArr);
    }

    public boolean isMultiValued() {
        return this.values.size() > 1;
    }

    public int size() {
        return this.values.size();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue getFirst() {
        if (this.values.size() == 0) {
            return null;
        }
        return com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue.getInstance(this.values.getObjectAt(0));
    }

    public com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] getTypesAndValues() {
        int size = this.values.size();
        com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] attributeTypeAndValueArr = new com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[size];
        for (int i = 0; i != size; i++) {
            attributeTypeAndValueArr[i] = com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue.getInstance(this.values.getObjectAt(i));
        }
        return attributeTypeAndValueArr;
    }

    int collectAttributeTypes(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr, int i) {
        int size = this.values.size();
        for (int i2 = 0; i2 < size; i2++) {
            aSN1ObjectIdentifierArr[i + i2] = com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue.getInstance(this.values.getObjectAt(i2)).getType();
        }
        return size;
    }

    boolean containsAttributeType(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int size = this.values.size();
        for (int i = 0; i < size; i++) {
            if (com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue.getInstance(this.values.getObjectAt(i)).getType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.values;
    }
}
