package com.android.internal.org.bouncycastle.util.encoders;

/* loaded from: classes4.dex */
public class Base64Encoder implements com.android.internal.org.bouncycastle.util.encoders.Encoder {
    protected final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte padding = 61;
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.decodingTable.length; i++) {
            this.decodingTable[i] = -1;
        }
        for (int i2 = 0; i2 < this.encodingTable.length; i2++) {
            this.decodingTable[this.encodingTable[i2]] = (byte) i2;
        }
    }

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    public int encode(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws java.io.IOException {
        int i4 = (i + i2) - 2;
        int i5 = i;
        int i6 = i3;
        while (i5 < i4) {
            int i7 = i5 + 1;
            byte b = bArr[i5];
            int i8 = i7 + 1;
            int i9 = bArr[i7] & 255;
            int i10 = i8 + 1;
            int i11 = bArr[i8] & 255;
            int i12 = i6 + 1;
            bArr2[i6] = this.encodingTable[(b >>> 2) & 63];
            int i13 = i12 + 1;
            bArr2[i12] = this.encodingTable[((b << 4) | (i9 >>> 4)) & 63];
            int i14 = i13 + 1;
            bArr2[i13] = this.encodingTable[((i9 << 2) | (i11 >>> 6)) & 63];
            i6 = i14 + 1;
            bArr2[i14] = this.encodingTable[i11 & 63];
            i5 = i10;
        }
        switch (i2 - (i5 - i)) {
            case 1:
                int i15 = bArr[i5] & 255;
                int i16 = i6 + 1;
                bArr2[i6] = this.encodingTable[(i15 >>> 2) & 63];
                int i17 = i16 + 1;
                bArr2[i16] = this.encodingTable[(i15 << 4) & 63];
                int i18 = i17 + 1;
                bArr2[i17] = this.padding;
                i6 = i18 + 1;
                bArr2[i18] = this.padding;
                break;
            case 2:
                int i19 = bArr[i5] & 255;
                int i20 = bArr[i5 + 1] & 255;
                int i21 = i6 + 1;
                bArr2[i6] = this.encodingTable[(i19 >>> 2) & 63];
                int i22 = i21 + 1;
                bArr2[i21] = this.encodingTable[((i19 << 4) | (i20 >>> 4)) & 63];
                int i23 = i22 + 1;
                bArr2[i22] = this.encodingTable[(i20 << 2) & 63];
                i6 = i23 + 1;
                bArr2[i23] = this.padding;
                break;
        }
        return i6 - i3;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr2 = new byte[72];
        while (i2 > 0) {
            int min = java.lang.Math.min(54, i2);
            outputStream.write(bArr2, 0, encode(bArr, i, min, bArr2, 0));
            i += min;
            i2 -= min;
        }
        return ((i2 + 2) / 3) * 4;
    }

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i, int i2, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr2 = new byte[54];
        int i3 = i + i2;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        if (i3 == 0) {
            return 0;
        }
        int i4 = i3;
        int i5 = 0;
        while (i4 > i && i5 != 4) {
            if (!ignore((char) bArr[i4 - 1])) {
                i5++;
            }
            i4--;
        }
        int nextI = nextI(bArr, i, i4);
        int i6 = 0;
        int i7 = 0;
        while (nextI < i4) {
            int i8 = nextI + 1;
            byte b = this.decodingTable[bArr[nextI]];
            int nextI2 = nextI(bArr, i8, i4);
            int i9 = nextI2 + 1;
            byte b2 = this.decodingTable[bArr[nextI2]];
            int nextI3 = nextI(bArr, i9, i4);
            int i10 = nextI3 + 1;
            byte b3 = this.decodingTable[bArr[nextI3]];
            int nextI4 = nextI(bArr, i10, i4);
            int i11 = nextI4 + 1;
            byte b4 = this.decodingTable[bArr[nextI4]];
            if ((b | b2 | b3 | b4) < 0) {
                throw new java.io.IOException("invalid characters encountered in base64 data");
            }
            int i12 = i6 + 1;
            bArr2[i6] = (byte) ((b << 2) | (b2 >> 4));
            int i13 = i12 + 1;
            bArr2[i12] = (byte) ((b2 << 4) | (b3 >> 2));
            i6 = i13 + 1;
            bArr2[i13] = (byte) ((b3 << 6) | b4);
            if (i6 == 54) {
                outputStream.write(bArr2);
                i6 = 0;
            }
            i7 += 3;
            nextI = nextI(bArr, i11, i4);
        }
        if (i6 > 0) {
            outputStream.write(bArr2, 0, i6);
        }
        int nextI5 = nextI(bArr, nextI, i3);
        int nextI6 = nextI(bArr, nextI5 + 1, i3);
        int nextI7 = nextI(bArr, nextI6 + 1, i3);
        return i7 + decodeLastBlock(outputStream, (char) bArr[nextI5], (char) bArr[nextI6], (char) bArr[nextI7], (char) bArr[nextI(bArr, nextI7 + 1, i3)]);
    }

    private int nextI(byte[] bArr, int i, int i2) {
        while (i < i2 && ignore((char) bArr[i])) {
            i++;
        }
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.util.encoders.Encoder
    public int decode(java.lang.String str, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr = new byte[54];
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        if (length == 0) {
            return 0;
        }
        int i = length;
        int i2 = 0;
        while (i > 0 && i2 != 4) {
            if (!ignore(str.charAt(i - 1))) {
                i2++;
            }
            i--;
        }
        int nextI = nextI(str, 0, i);
        int i3 = 0;
        int i4 = 0;
        while (nextI < i) {
            int i5 = nextI + 1;
            byte b = this.decodingTable[str.charAt(nextI)];
            int nextI2 = nextI(str, i5, i);
            int i6 = nextI2 + 1;
            byte b2 = this.decodingTable[str.charAt(nextI2)];
            int nextI3 = nextI(str, i6, i);
            int i7 = nextI3 + 1;
            byte b3 = this.decodingTable[str.charAt(nextI3)];
            int nextI4 = nextI(str, i7, i);
            int i8 = nextI4 + 1;
            byte b4 = this.decodingTable[str.charAt(nextI4)];
            if ((b | b2 | b3 | b4) < 0) {
                throw new java.io.IOException("invalid characters encountered in base64 data");
            }
            int i9 = i3 + 1;
            bArr[i3] = (byte) ((b << 2) | (b2 >> 4));
            int i10 = i9 + 1;
            bArr[i9] = (byte) ((b2 << 4) | (b3 >> 2));
            i3 = i10 + 1;
            bArr[i10] = (byte) ((b3 << 6) | b4);
            i4 += 3;
            if (i3 == 54) {
                outputStream.write(bArr);
                i3 = 0;
            }
            nextI = nextI(str, i8, i);
        }
        if (i3 > 0) {
            outputStream.write(bArr, 0, i3);
        }
        int nextI5 = nextI(str, nextI, length);
        int nextI6 = nextI(str, nextI5 + 1, length);
        int nextI7 = nextI(str, nextI6 + 1, length);
        return i4 + decodeLastBlock(outputStream, str.charAt(nextI5), str.charAt(nextI6), str.charAt(nextI7), str.charAt(nextI(str, nextI7 + 1, length)));
    }

    private int decodeLastBlock(java.io.OutputStream outputStream, char c, char c2, char c3, char c4) throws java.io.IOException {
        if (c3 == this.padding) {
            if (c4 != this.padding) {
                throw new java.io.IOException("invalid characters encountered at end of base64 data");
            }
            byte b = this.decodingTable[c];
            byte b2 = this.decodingTable[c2];
            if ((b | b2) < 0) {
                throw new java.io.IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b << 2) | (b2 >> 4));
            return 1;
        }
        if (c4 == this.padding) {
            byte b3 = this.decodingTable[c];
            byte b4 = this.decodingTable[c2];
            byte b5 = this.decodingTable[c3];
            if ((b3 | b4 | b5) < 0) {
                throw new java.io.IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b3 << 2) | (b4 >> 4));
            outputStream.write((b4 << 4) | (b5 >> 2));
            return 2;
        }
        byte b6 = this.decodingTable[c];
        byte b7 = this.decodingTable[c2];
        byte b8 = this.decodingTable[c3];
        byte b9 = this.decodingTable[c4];
        if ((b6 | b7 | b8 | b9) < 0) {
            throw new java.io.IOException("invalid characters encountered at end of base64 data");
        }
        outputStream.write((b6 << 2) | (b7 >> 4));
        outputStream.write((b7 << 4) | (b8 >> 2));
        outputStream.write((b8 << 6) | b9);
        return 3;
    }

    private int nextI(java.lang.String str, int i, int i2) {
        while (i < i2 && ignore(str.charAt(i))) {
            i++;
        }
        return i;
    }
}
