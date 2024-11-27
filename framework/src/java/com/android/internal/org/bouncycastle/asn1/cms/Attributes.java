package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class Attributes extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set attributes;

    private Attributes(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.attributes = aSN1Set;
    }

    public Attributes(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        this.attributes = new com.android.internal.org.bouncycastle.asn1.DLSet(aSN1EncodableVector);
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.Attributes getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.Attributes) {
            return (com.android.internal.org.bouncycastle.asn1.cms.Attributes) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.Attributes(com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.Attributes getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, z));
    }

    public com.android.internal.org.bouncycastle.asn1.cms.Attribute[] getAttributes() {
        int size = this.attributes.size();
        com.android.internal.org.bouncycastle.asn1.cms.Attribute[] attributeArr = new com.android.internal.org.bouncycastle.asn1.cms.Attribute[size];
        for (int i = 0; i != size; i++) {
            attributeArr[i] = com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(this.attributes.getObjectAt(i));
        }
        return attributeArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.attributes;
    }
}
