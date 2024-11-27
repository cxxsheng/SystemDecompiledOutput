package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERBitString extends com.android.internal.org.bouncycastle.asn1.ASN1BitString {
    public static com.android.internal.org.bouncycastle.asn1.DERBitString getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.DERBitString)) {
            return (com.android.internal.org.bouncycastle.asn1.DERBitString) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DLBitString) {
            com.android.internal.org.bouncycastle.asn1.DLBitString dLBitString = (com.android.internal.org.bouncycastle.asn1.DLBitString) obj;
            return new com.android.internal.org.bouncycastle.asn1.DERBitString(dLBitString.data, dLBitString.padBits);
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.DERBitString) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.DERBitString getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.DERBitString)) {
            return getInstance(object);
        }
        return fromOctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    protected DERBitString(byte b, int i) {
        super(b, i);
    }

    public DERBitString(byte[] bArr, int i) {
        super(bArr, i);
    }

    public DERBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DERBitString(int i) {
        super(getBytes(i), getPadBits(i));
    }

    public DERBitString(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER), 0);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.data.length + 1) + 1 + this.data.length + 1;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        int length = this.data.length;
        if (length != 0 && this.padBits != 0) {
            int i = length - 1;
            if (this.data[i] != ((byte) (this.data[i] & (255 << this.padBits)))) {
                aSN1OutputStream.writeEncoded(z, 3, (byte) this.padBits, this.data, 0, i, (byte) (this.data[i] & (255 << this.padBits)));
                return;
            }
        }
        aSN1OutputStream.writeEncoded(z, 3, (byte) this.padBits, this.data);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }

    static com.android.internal.org.bouncycastle.asn1.DERBitString fromOctetString(byte[] bArr) {
        if (bArr.length < 1) {
            throw new java.lang.IllegalArgumentException("truncated BIT STRING detected");
        }
        byte b = bArr[0];
        int length = bArr.length - 1;
        byte[] bArr2 = new byte[length];
        if (length != 0) {
            java.lang.System.arraycopy(bArr, 1, bArr2, 0, bArr.length - 1);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr2, b);
    }
}
