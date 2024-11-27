package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public class X500NameBuilder {
    private java.util.Vector rdns;
    private com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle template;

    public X500NameBuilder() {
        this(com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle.INSTANCE);
    }

    public X500NameBuilder(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle) {
        this.rdns = new java.util.Vector();
        this.template = x500NameStyle;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addRDN(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        addRDN(aSN1ObjectIdentifier, this.template.stringToValue(aSN1ObjectIdentifier, str));
        return this;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addRDN(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.rdns.addElement(new com.android.internal.org.bouncycastle.asn1.x500.RDN(aSN1ObjectIdentifier, aSN1Encodable));
        return this;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addRDN(com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue attributeTypeAndValue) {
        this.rdns.addElement(new com.android.internal.org.bouncycastle.asn1.x500.RDN(attributeTypeAndValue));
        return this;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addMultiValuedRDN(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr, java.lang.String[] strArr) {
        int length = strArr.length;
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[length];
        for (int i = 0; i != length; i++) {
            aSN1EncodableArr[i] = this.template.stringToValue(aSN1ObjectIdentifierArr[i], strArr[i]);
        }
        return addMultiValuedRDN(aSN1ObjectIdentifierArr, aSN1EncodableArr);
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addMultiValuedRDN(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr, com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] attributeTypeAndValueArr = new com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[aSN1ObjectIdentifierArr.length];
        for (int i = 0; i != aSN1ObjectIdentifierArr.length; i++) {
            attributeTypeAndValueArr[i] = new com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue(aSN1ObjectIdentifierArr[i], aSN1EncodableArr[i]);
        }
        return addMultiValuedRDN(attributeTypeAndValueArr);
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder addMultiValuedRDN(com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] attributeTypeAndValueArr) {
        this.rdns.addElement(new com.android.internal.org.bouncycastle.asn1.x500.RDN(attributeTypeAndValueArr));
        return this;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name build() {
        int size = this.rdns.size();
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr = new com.android.internal.org.bouncycastle.asn1.x500.RDN[size];
        for (int i = 0; i != size; i++) {
            rdnArr[i] = (com.android.internal.org.bouncycastle.asn1.x500.RDN) this.rdns.elementAt(i);
        }
        return new com.android.internal.org.bouncycastle.asn1.x500.X500Name(this.template, rdnArr);
    }
}
