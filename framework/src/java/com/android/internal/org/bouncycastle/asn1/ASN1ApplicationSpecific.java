package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1ApplicationSpecific extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    protected final boolean isConstructed;
    protected final byte[] octets;
    protected final int tag;

    ASN1ApplicationSpecific(boolean z, int i, byte[] bArr) {
        this.isConstructed = z;
        this.tag = i;
        this.octets = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Failed to construct object from byte[]: " + e.getMessage());
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    protected static int getLengthOfHeader(byte[] bArr) {
        int i = bArr[1] & 255;
        if (i == 128 || i <= 127) {
            return 2;
        }
        int i2 = i & 127;
        if (i2 > 4) {
            throw new java.lang.IllegalStateException("DER length more than 4 bytes: " + i2);
        }
        return i2 + 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return this.isConstructed;
    }

    public byte[] getContents() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.octets);
    }

    public int getApplicationTag() {
        return this.tag;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObject() throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(getContents());
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObject(int i) throws java.io.IOException {
        if (i >= 31) {
            throw new java.io.IOException("unsupported tag number");
        }
        byte[] encoded = getEncoded();
        byte[] replaceTagNumber = replaceTagNumber(i, encoded);
        if ((encoded[0] & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 0) {
            replaceTagNumber[0] = (byte) (replaceTagNumber[0] | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(replaceTagNumber);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateTagLength(this.tag) + com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.octets.length) + this.octets.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        int i;
        if (!this.isConstructed) {
            i = 64;
        } else {
            i = 96;
        }
        aSN1OutputStream.writeEncoded(z, i, this.tag, this.octets);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific aSN1ApplicationSpecific = (com.android.internal.org.bouncycastle.asn1.ASN1ApplicationSpecific) aSN1Primitive;
        return this.isConstructed == aSN1ApplicationSpecific.isConstructed && this.tag == aSN1ApplicationSpecific.tag && com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.octets, aSN1ApplicationSpecific.octets);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        boolean z = this.isConstructed;
        return ((z ? 1 : 0) ^ this.tag) ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.octets);
    }

    private byte[] replaceTagNumber(int i, byte[] bArr) throws java.io.IOException {
        int i2;
        if ((bArr[0] & 31) != 31) {
            i2 = 1;
        } else {
            int i3 = bArr[1] & 255;
            if ((i3 & 127) == 0) {
                throw new java.io.IOException("corrupted stream - invalid high tag number found");
            }
            i2 = 2;
            while ((i3 & 128) != 0) {
                int i4 = i2 + 1;
                int i5 = bArr[i2] & 255;
                i2 = i4;
                i3 = i5;
            }
        }
        int length = (bArr.length - i2) + 1;
        byte[] bArr2 = new byte[length];
        java.lang.System.arraycopy(bArr, i2, bArr2, 1, length - 1);
        bArr2[0] = (byte) i;
        return bArr2;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        if (isConstructed()) {
            stringBuffer.append("CONSTRUCTED ");
        }
        stringBuffer.append("APPLICATION ");
        stringBuffer.append(java.lang.Integer.toString(getApplicationTag()));
        stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        if (this.octets != null) {
            stringBuffer.append(" #");
            stringBuffer.append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(this.octets));
        } else {
            stringBuffer.append(" #null");
        }
        stringBuffer.append(" ");
        return stringBuffer.toString();
    }
}
