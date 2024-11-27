package com.android.internal.org.bouncycastle.cert.selector;

/* loaded from: classes4.dex */
class MSOutlookKeyIdCalculator {
    MSOutlookKeyIdCalculator() {
    }

    static byte[] calculateKeyId(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.SHA1Digest sHA1Digest = new com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.SHA1Digest();
        byte[] bArr = new byte[sHA1Digest.getDigestSize()];
        try {
            byte[] encoded = subjectPublicKeyInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            sHA1Digest.update(encoded, 0, encoded.length);
            sHA1Digest.doFinal(bArr, 0);
            return bArr;
        } catch (java.io.IOException e) {
            return new byte[0];
        }
    }

    private static abstract class GeneralDigest {
        private static final int BYTE_LENGTH = 64;
        private long byteCount;
        private byte[] xBuf;
        private int xBufOff;

        protected abstract void processBlock();

        protected abstract void processLength(long j);

        protected abstract void processWord(byte[] bArr, int i);

        protected GeneralDigest() {
            this.xBuf = new byte[4];
            this.xBufOff = 0;
        }

        protected GeneralDigest(com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest generalDigest) {
            this.xBuf = new byte[generalDigest.xBuf.length];
            copyIn(generalDigest);
        }

        protected void copyIn(com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest generalDigest) {
            java.lang.System.arraycopy(generalDigest.xBuf, 0, this.xBuf, 0, generalDigest.xBuf.length);
            this.xBufOff = generalDigest.xBufOff;
            this.byteCount = generalDigest.byteCount;
        }

        public void update(byte b) {
            byte[] bArr = this.xBuf;
            int i = this.xBufOff;
            this.xBufOff = i + 1;
            bArr[i] = b;
            if (this.xBufOff == this.xBuf.length) {
                processWord(this.xBuf, 0);
                this.xBufOff = 0;
            }
            this.byteCount++;
        }

        public void update(byte[] bArr, int i, int i2) {
            while (this.xBufOff != 0 && i2 > 0) {
                update(bArr[i]);
                i++;
                i2--;
            }
            while (i2 > this.xBuf.length) {
                processWord(bArr, i);
                i += this.xBuf.length;
                i2 -= this.xBuf.length;
                this.byteCount += this.xBuf.length;
            }
            while (i2 > 0) {
                update(bArr[i]);
                i++;
                i2--;
            }
        }

        public void finish() {
            long j = this.byteCount << 3;
            update(Byte.MIN_VALUE);
            while (this.xBufOff != 0) {
                update((byte) 0);
            }
            processLength(j);
            processBlock();
        }

        public void reset() {
            this.byteCount = 0L;
            this.xBufOff = 0;
            for (int i = 0; i < this.xBuf.length; i++) {
                this.xBuf[i] = 0;
            }
        }
    }

    private static class SHA1Digest extends com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest {
        private static final int DIGEST_LENGTH = 20;
        private static final int Y1 = 1518500249;
        private static final int Y2 = 1859775393;
        private static final int Y3 = -1894007588;
        private static final int Y4 = -899497514;
        private int H1;
        private int H2;
        private int H3;
        private int H4;
        private int H5;
        private int[] X = new int[80];
        private int xOff;

        public SHA1Digest() {
            reset();
        }

        public java.lang.String getAlgorithmName() {
            return "SHA-1";
        }

        public int getDigestSize() {
            return 20;
        }

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        protected void processWord(byte[] bArr, int i) {
            int i2 = bArr[i] << android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED;
            int i3 = i + 1;
            int i4 = i2 | ((bArr[i3] & 255) << 16);
            int i5 = i3 + 1;
            this.X[this.xOff] = (bArr[i5 + 1] & 255) | i4 | ((bArr[i5] & 255) << 8);
            int i6 = this.xOff + 1;
            this.xOff = i6;
            if (i6 == 16) {
                processBlock();
            }
        }

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        protected void processLength(long j) {
            if (this.xOff > 14) {
                processBlock();
            }
            this.X[14] = (int) (j >>> 32);
            this.X[15] = (int) (j & (-1));
        }

        public int doFinal(byte[] bArr, int i) {
            finish();
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H1, bArr, i);
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H2, bArr, i + 4);
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H3, bArr, i + 8);
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H4, bArr, i + 12);
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H5, bArr, i + 16);
            reset();
            return 20;
        }

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        public void reset() {
            super.reset();
            this.H1 = 1732584193;
            this.H2 = -271733879;
            this.H3 = -1732584194;
            this.H4 = 271733878;
            this.H5 = -1009589776;
            this.xOff = 0;
            for (int i = 0; i != this.X.length; i++) {
                this.X[i] = 0;
            }
        }

        private int f(int i, int i2, int i3) {
            return ((~i) & i3) | (i2 & i);
        }

        private int h(int i, int i2, int i3) {
            return (i ^ i2) ^ i3;
        }

        private int g(int i, int i2, int i3) {
            return (i & i3) | (i & i2) | (i2 & i3);
        }

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        protected void processBlock() {
            for (int i = 16; i < 80; i++) {
                int i2 = ((this.X[i - 3] ^ this.X[i - 8]) ^ this.X[i - 14]) ^ this.X[i - 16];
                this.X[i] = (i2 >>> 31) | (i2 << 1);
            }
            int i3 = this.H1;
            int i4 = this.H2;
            int i5 = this.H3;
            int i6 = this.H4;
            int i7 = this.H5;
            int i8 = 0;
            int i9 = 0;
            while (i8 < 4) {
                int i10 = i9 + 1;
                int f = i7 + ((i3 << 5) | (i3 >>> 27)) + f(i4, i5, i6) + this.X[i9] + Y1;
                int i11 = (i4 >>> 2) | (i4 << 30);
                int i12 = i10 + 1;
                int f2 = i6 + ((f << 5) | (f >>> 27)) + f(i3, i11, i5) + this.X[i10] + Y1;
                int i13 = (i3 >>> 2) | (i3 << 30);
                int i14 = i12 + 1;
                int f3 = i5 + ((f2 << 5) | (f2 >>> 27)) + f(f, i13, i11) + this.X[i12] + Y1;
                i7 = (f >>> 2) | (f << 30);
                int i15 = i14 + 1;
                i4 = i11 + ((f3 << 5) | (f3 >>> 27)) + f(f2, i7, i13) + this.X[i14] + Y1;
                i6 = (f2 >>> 2) | (f2 << 30);
                i3 = i13 + ((i4 << 5) | (i4 >>> 27)) + f(f3, i6, i7) + this.X[i15] + Y1;
                i5 = (f3 >>> 2) | (f3 << 30);
                i8++;
                i9 = i15 + 1;
            }
            int i16 = 0;
            while (i16 < 4) {
                int i17 = i9 + 1;
                int h = i7 + ((i3 << 5) | (i3 >>> 27)) + h(i4, i5, i6) + this.X[i9] + Y2;
                int i18 = (i4 >>> 2) | (i4 << 30);
                int i19 = i17 + 1;
                int h2 = i6 + ((h << 5) | (h >>> 27)) + h(i3, i18, i5) + this.X[i17] + Y2;
                int i20 = (i3 >>> 2) | (i3 << 30);
                int i21 = i19 + 1;
                int h3 = i5 + ((h2 << 5) | (h2 >>> 27)) + h(h, i20, i18) + this.X[i19] + Y2;
                i7 = (h >>> 2) | (h << 30);
                int i22 = i21 + 1;
                i4 = i18 + ((h3 << 5) | (h3 >>> 27)) + h(h2, i7, i20) + this.X[i21] + Y2;
                i6 = (h2 >>> 2) | (h2 << 30);
                i3 = i20 + ((i4 << 5) | (i4 >>> 27)) + h(h3, i6, i7) + this.X[i22] + Y2;
                i5 = (h3 >>> 2) | (h3 << 30);
                i16++;
                i9 = i22 + 1;
            }
            int i23 = 0;
            while (i23 < 4) {
                int i24 = i9 + 1;
                int g = i7 + ((i3 << 5) | (i3 >>> 27)) + g(i4, i5, i6) + this.X[i9] + Y3;
                int i25 = (i4 >>> 2) | (i4 << 30);
                int i26 = i24 + 1;
                int g2 = i6 + ((g << 5) | (g >>> 27)) + g(i3, i25, i5) + this.X[i24] + Y3;
                int i27 = (i3 >>> 2) | (i3 << 30);
                int i28 = i26 + 1;
                int g3 = i5 + ((g2 << 5) | (g2 >>> 27)) + g(g, i27, i25) + this.X[i26] + Y3;
                i7 = (g >>> 2) | (g << 30);
                int i29 = i28 + 1;
                i4 = i25 + ((g3 << 5) | (g3 >>> 27)) + g(g2, i7, i27) + this.X[i28] + Y3;
                i6 = (g2 >>> 2) | (g2 << 30);
                i3 = i27 + ((i4 << 5) | (i4 >>> 27)) + g(g3, i6, i7) + this.X[i29] + Y3;
                i5 = (g3 >>> 2) | (g3 << 30);
                i23++;
                i9 = i29 + 1;
            }
            int i30 = 0;
            while (i30 <= 3) {
                int i31 = i9 + 1;
                int h4 = i7 + ((i3 << 5) | (i3 >>> 27)) + h(i4, i5, i6) + this.X[i9] + Y4;
                int i32 = (i4 >>> 2) | (i4 << 30);
                int i33 = i31 + 1;
                int h5 = i6 + ((h4 << 5) | (h4 >>> 27)) + h(i3, i32, i5) + this.X[i31] + Y4;
                int i34 = (i3 >>> 2) | (i3 << 30);
                int i35 = i33 + 1;
                int h6 = i5 + ((h5 << 5) | (h5 >>> 27)) + h(h4, i34, i32) + this.X[i33] + Y4;
                i7 = (h4 >>> 2) | (h4 << 30);
                int i36 = i35 + 1;
                i4 = i32 + ((h6 << 5) | (h6 >>> 27)) + h(h5, i7, i34) + this.X[i35] + Y4;
                i6 = (h5 >>> 2) | (h5 << 30);
                i3 = i34 + ((i4 << 5) | (i4 >>> 27)) + h(h6, i6, i7) + this.X[i36] + Y4;
                i5 = (h6 >>> 2) | (h6 << 30);
                i30++;
                i9 = i36 + 1;
            }
            this.H1 += i3;
            this.H2 += i4;
            this.H3 += i5;
            this.H4 += i6;
            this.H5 += i7;
            this.xOff = 0;
            for (int i37 = 0; i37 < 16; i37++) {
                this.X[i37] = 0;
            }
        }
    }
}
