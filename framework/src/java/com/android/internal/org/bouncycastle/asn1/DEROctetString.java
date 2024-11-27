package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DEROctetString extends com.android.internal.org.bouncycastle.asn1.ASN1OctetString {
    public DEROctetString(byte[] bArr) {
        super(bArr);
    }

    public DEROctetString(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 4, this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }

    static void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z, byte[] bArr, int i, int i2) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 4, bArr, i, i2);
    }
}
