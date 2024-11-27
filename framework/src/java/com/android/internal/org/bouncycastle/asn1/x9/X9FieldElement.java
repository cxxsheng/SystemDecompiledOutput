package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X9FieldElement extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter converter = new com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter();
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement f;

    public X9FieldElement(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        this.f = eCFieldElement;
    }

    public X9FieldElement(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(bigInteger, new java.math.BigInteger(1, aSN1OctetString.getOctets())));
    }

    public X9FieldElement(int i, int i2, int i3, int i4, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(i, i2, i3, i4, new java.math.BigInteger(1, aSN1OctetString.getOctets())));
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getValue() {
        return this.f;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(converter.integerToBytes(this.f.toBigInteger(), converter.getByteLength(this.f)));
    }
}
