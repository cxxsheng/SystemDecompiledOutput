package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1BitString extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.asn1.ASN1String {
    private static final char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', android.text.format.DateFormat.DAY, 'F'};
    protected final byte[] data;
    protected final int padBits;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    protected static int getPadBits(int i) {
        int i2;
        int i3 = 3;
        while (true) {
            if (i3 < 0) {
                i2 = 0;
                break;
            }
            if (i3 != 0) {
                int i4 = i >> (i3 * 8);
                if (i4 == 0) {
                    i3--;
                } else {
                    i2 = i4 & 255;
                    break;
                }
            } else if (i == 0) {
                i3--;
            } else {
                i2 = i & 255;
                break;
            }
        }
        if (i2 == 0) {
            return 0;
        }
        int i5 = 1;
        while (true) {
            i2 <<= 1;
            if ((i2 & 255) != 0) {
                i5++;
            } else {
                return 8 - i5;
            }
        }
    }

    protected static byte[] getBytes(int i) {
        if (i == 0) {
            return new byte[0];
        }
        int i2 = 4;
        for (int i3 = 3; i3 >= 1 && ((255 << (i3 * 8)) & i) == 0; i3--) {
            i2--;
        }
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr[i4] = (byte) ((i >> (i4 * 8)) & 255);
        }
        return bArr;
    }

    protected ASN1BitString(byte b, int i) {
        if (i > 7 || i < 0) {
            throw new java.lang.IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        this.data = new byte[]{b};
        this.padBits = i;
    }

    public ASN1BitString(byte[] bArr, int i) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("'data' cannot be null");
        }
        if (bArr.length == 0 && i != 0) {
            throw new java.lang.IllegalArgumentException("zero length data with non-zero pad bits");
        }
        if (i > 7 || i < 0) {
            throw new java.lang.IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        this.data = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.padBits = i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1String
    public java.lang.String getString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer("#");
        try {
            byte[] encoded = getEncoded();
            for (int i = 0; i != encoded.length; i++) {
                stringBuffer.append(table[(encoded[i] >>> 4) & 15]);
                stringBuffer.append(table[encoded[i] & 15]);
            }
            return stringBuffer.toString();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("Internal error encoding BitString: " + e.getMessage(), e);
        }
    }

    public int intValue() {
        int min = java.lang.Math.min(4, this.data.length - 1);
        int i = 0;
        for (int i2 = 0; i2 < min; i2++) {
            i |= (255 & this.data[i2]) << (i2 * 8);
        }
        if (min >= 0 && min < 4) {
            return i | ((((byte) (this.data[min] & (255 << this.padBits))) & 255) << (min * 8));
        }
        return i;
    }

    public byte[] getOctets() {
        if (this.padBits != 0) {
            throw new java.lang.IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
        }
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.data);
    }

    public byte[] getBytes() {
        if (this.data.length == 0) {
            return this.data;
        }
        byte[] clone = com.android.internal.org.bouncycastle.util.Arrays.clone(this.data);
        int length = this.data.length - 1;
        clone[length] = (byte) (clone[length] & (255 << this.padBits));
        return clone;
    }

    public int getPadBits() {
        return this.padBits;
    }

    public java.lang.String toString() {
        return getString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int length = this.data.length - 1;
        if (length < 0) {
            return 1;
        }
        return ((com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.data, 0, length) * 257) ^ ((byte) (this.data[length] & (255 << this.padBits)))) ^ this.padBits;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1BitString)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1BitString aSN1BitString = (com.android.internal.org.bouncycastle.asn1.ASN1BitString) aSN1Primitive;
        if (this.padBits != aSN1BitString.padBits) {
            return false;
        }
        byte[] bArr = this.data;
        byte[] bArr2 = aSN1BitString.data;
        int length = bArr.length;
        if (length != bArr2.length) {
            return false;
        }
        int i = length - 1;
        if (i < 0) {
            return true;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return ((byte) (bArr[i] & (255 << this.padBits))) == ((byte) (bArr2[i] & (255 << this.padBits)));
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1BitString fromInputStream(int i, java.io.InputStream inputStream) throws java.io.IOException {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("truncated BIT STRING detected");
        }
        int read = inputStream.read();
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        if (i2 != 0) {
            if (com.android.internal.org.bouncycastle.util.io.Streams.readFully(inputStream, bArr) != i2) {
                throw new java.io.EOFException("EOF encountered in middle of BIT STRING");
            }
            if (read > 0 && read < 8) {
                int i3 = i2 - 1;
                if (bArr[i3] != ((byte) (bArr[i3] & (255 << read)))) {
                    return new com.android.internal.org.bouncycastle.asn1.DLBitString(bArr, read);
                }
            }
        }
        return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr, read);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERBitString(this.data, this.padBits);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DLBitString(this.data, this.padBits);
    }
}
