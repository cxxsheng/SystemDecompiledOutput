package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public class HexEncoder implements com.android.internal.org.bouncycastle.util.encoders.Encoder {
    protected final byte[] encodingTable = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.decodingTable.length; i++) {
            this.decodingTable[i] = -1;
        }
        for (int i2 = 0; i2 < this.encodingTable.length; i2++) {
            this.decodingTable[this.encodingTable[i2]] = (byte) i2;
        }
        this.decodingTable[65] = this.decodingTable[97];
        this.decodingTable[66] = this.decodingTable[98];
        this.decodingTable[67] = this.decodingTable[99];
        this.decodingTable[68] = this.decodingTable[100];
        this.decodingTable[69] = this.decodingTable[101];
        this.decodingTable[70] = this.decodingTable[102];
    }

    public HexEncoder() {
        initialiseDecodingTable();
    }

    public int encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws java.io.IOException {
        int i4 = i2 + i;
        int i5 = i3;
        while (i < i4) {
            int i6 = i + 1;
            int i7 = bArr[i] & 255;
            int i8 = i5 + 1;
            bArr2[i5] = this.encodingTable[i7 >>> 4];
            i5 = i8 + 1;
            bArr2[i8] = this.encodingTable[i7 & 15];
            i = i6;
        }
        return i5 - i3;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr2 = new byte[72];
        while (i2 > 0) {
            int min = java.lang.Math.min(36, i2);
            outputStream.write(bArr2, 0, encode(bArr, i, min, bArr2, 0));
            i += min;
            i2 -= min;
        }
        return i2 * 2;
    }

    private static boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr2 = new byte[36];
        int i3 = i2 + i;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = 0;
        int i5 = 0;
        while (i < i3) {
            while (i < i3 && ignore((char) bArr[i])) {
                i++;
            }
            int i6 = i + 1;
            byte b = this.decodingTable[bArr[i]];
            while (i6 < i3 && ignore((char) bArr[i6])) {
                i6++;
            }
            int i7 = i6 + 1;
            byte b2 = this.decodingTable[bArr[i6]];
            if ((b | b2) < 0) {
                throw new java.io.IOException("invalid characters encountered in Hex data");
            }
            int i8 = i4 + 1;
            bArr2[i4] = (byte) ((b << 4) | b2);
            if (i8 != 36) {
                i4 = i8;
            } else {
                outputStream.write(bArr2);
                i4 = 0;
            }
            i5++;
            i = i7;
        }
        if (i4 > 0) {
            outputStream.write(bArr2, 0, i4);
        }
        return i5;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(java.lang.String str, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr = new byte[36];
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            while (i < length && ignore(str.charAt(i))) {
                i++;
            }
            int i4 = i + 1;
            byte b = this.decodingTable[str.charAt(i)];
            while (i4 < length && ignore(str.charAt(i4))) {
                i4++;
            }
            int i5 = i4 + 1;
            byte b2 = this.decodingTable[str.charAt(i4)];
            if ((b | b2) < 0) {
                throw new java.io.IOException("invalid characters encountered in Hex string");
            }
            int i6 = i2 + 1;
            bArr[i2] = (byte) ((b << 4) | b2);
            if (i6 != 36) {
                i2 = i6;
            } else {
                outputStream.write(bArr);
                i2 = 0;
            }
            i3++;
            i = i5;
        }
        if (i2 > 0) {
            outputStream.write(bArr, 0, i2);
        }
        return i3;
    }

    byte[] decodeStrict(java.lang.String str, int i, int i2) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.NullPointerException("'str' cannot be null");
        }
        if (i < 0 || i2 < 0 || i > str.length() - i2) {
            throw new java.lang.IndexOutOfBoundsException("invalid offset and/or length specified");
        }
        if ((i2 & 1) != 0) {
            throw new java.io.IOException("a hexadecimal encoding must have an even number of characters");
        }
        int i3 = i2 >>> 1;
        byte[] bArr = new byte[i3];
        int i4 = 0;
        while (i4 < i3) {
            int i5 = i + 1;
            int i6 = i5 + 1;
            int i7 = (this.decodingTable[str.charAt(i)] << 4) | this.decodingTable[str.charAt(i5)];
            if (i7 < 0) {
                throw new java.io.IOException("invalid characters encountered in Hex string");
            }
            bArr[i4] = (byte) i7;
            i4++;
            i = i6;
        }
        return bArr;
    }
}
