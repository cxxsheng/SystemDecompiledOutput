package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509Attribute extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.Attribute attr;

    X509Attribute(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.attr = com.android.internal.org.bouncycastle.asn1.x509.Attribute.getInstance(aSN1Encodable);
    }

    public X509Attribute(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.attr = new com.android.internal.org.bouncycastle.asn1.x509.Attribute(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1Encodable));
    }

    public X509Attribute(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        this.attr = new com.android.internal.org.bouncycastle.asn1.x509.Attribute(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector));
    }

    public java.lang.String getOID() {
        return this.attr.getAttrType().getId();
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] getValues() {
        com.android.internal.org.bouncycastle.asn1.ASN1Set attrValues = this.attr.getAttrValues();
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[attrValues.size()];
        for (int i = 0; i != attrValues.size(); i++) {
            aSN1EncodableArr[i] = attrValues.getObjectAt(i);
        }
        return aSN1EncodableArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.attr.toASN1Primitive();
    }
}
