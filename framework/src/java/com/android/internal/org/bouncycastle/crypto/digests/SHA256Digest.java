package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class SHA256Digest extends com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest implements com.android.internal.org.bouncycastle.crypto.digests.EncodableDigest {
    private static final int DIGEST_LENGTH = 32;
    static final int[] K = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int H6;
    private int H7;
    private int H8;
    private int[] X;
    private int xOff;

    public SHA256Digest() {
        this.X = new int[64];
        reset();
    }

    public SHA256Digest(com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest sHA256Digest) {
        super(sHA256Digest);
        this.X = new int[64];
        copyIn(sHA256Digest);
    }

    private void copyIn(com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest sHA256Digest) {
        super.copyIn((com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest) sHA256Digest);
        this.H1 = sHA256Digest.H1;
        this.H2 = sHA256Digest.H2;
        this.H3 = sHA256Digest.H3;
        this.H4 = sHA256Digest.H4;
        this.H5 = sHA256Digest.H5;
        this.H6 = sHA256Digest.H6;
        this.H7 = sHA256Digest.H7;
        this.H8 = sHA256Digest.H8;
        java.lang.System.arraycopy(sHA256Digest.X, 0, this.X, 0, sHA256Digest.X.length);
        this.xOff = sHA256Digest.xOff;
    }

    public SHA256Digest(byte[] bArr) {
        super(bArr);
        this.X = new int[64];
        this.H1 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 16);
        this.H2 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 20);
        this.H3 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 24);
        this.H4 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 28);
        this.H5 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 32);
        this.H6 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 36);
        this.H7 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 40);
        this.H8 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 44);
        this.xOff = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 48);
        for (int i = 0; i != this.xOff; i++) {
            this.X[i] = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, (i * 4) + 52);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public java.lang.String getAlgorithmName() {
        return "SHA-256";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
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

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        this.X[14] = (int) (j >>> 32);
        this.X[15] = (int) (j & (-1));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        finish();
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H1, bArr, i);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H2, bArr, i + 4);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H3, bArr, i + 8);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H4, bArr, i + 12);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H5, bArr, i + 16);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H6, bArr, i + 20);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H7, bArr, i + 24);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H8, bArr, i + 28);
        reset();
        return 32;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest, com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 1779033703;
        this.H2 = -1150833019;
        this.H3 = 1013904242;
        this.H4 = -1521486534;
        this.H5 = 1359893119;
        this.H6 = -1694144372;
        this.H7 = 528734635;
        this.H8 = 1541459225;
        this.xOff = 0;
        for (int i = 0; i != this.X.length; i++) {
            this.X[i] = 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processBlock() {
        for (int i = 16; i <= 63; i++) {
            this.X[i] = Theta1(this.X[i - 2]) + this.X[i - 7] + Theta0(this.X[i - 15]) + this.X[i - 16];
        }
        int i2 = this.H1;
        int i3 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int i6 = this.H5;
        int i7 = this.H6;
        int i8 = this.H7;
        int i9 = this.H8;
        int i10 = 0;
        for (int i11 = 0; i11 < 8; i11++) {
            int Sum1 = i9 + Sum1(i6) + Ch(i6, i7, i8) + K[i10] + this.X[i10];
            int i12 = i5 + Sum1;
            int Sum0 = Sum1 + Sum0(i2) + Maj(i2, i3, i4);
            int i13 = i10 + 1;
            int Sum12 = i8 + Sum1(i12) + Ch(i12, i6, i7) + K[i13] + this.X[i13];
            int i14 = i4 + Sum12;
            int Sum02 = Sum12 + Sum0(Sum0) + Maj(Sum0, i2, i3);
            int i15 = i13 + 1;
            int Sum13 = i7 + Sum1(i14) + Ch(i14, i12, i6) + K[i15] + this.X[i15];
            int i16 = i3 + Sum13;
            int Sum03 = Sum13 + Sum0(Sum02) + Maj(Sum02, Sum0, i2);
            int i17 = i15 + 1;
            int Sum14 = i6 + Sum1(i16) + Ch(i16, i14, i12) + K[i17] + this.X[i17];
            int i18 = i2 + Sum14;
            int Sum04 = Sum14 + Sum0(Sum03) + Maj(Sum03, Sum02, Sum0);
            int i19 = i17 + 1;
            int Sum15 = i12 + Sum1(i18) + Ch(i18, i16, i14) + K[i19] + this.X[i19];
            i9 = Sum0 + Sum15;
            i5 = Sum15 + Sum0(Sum04) + Maj(Sum04, Sum03, Sum02);
            int i20 = i19 + 1;
            int Sum16 = i14 + Sum1(i9) + Ch(i9, i18, i16) + K[i20] + this.X[i20];
            i8 = Sum02 + Sum16;
            i4 = Sum16 + Sum0(i5) + Maj(i5, Sum04, Sum03);
            int i21 = i20 + 1;
            int Sum17 = i16 + Sum1(i8) + Ch(i8, i9, i18) + K[i21] + this.X[i21];
            i7 = Sum03 + Sum17;
            i3 = Sum17 + Sum0(i4) + Maj(i4, i5, Sum04);
            int i22 = i21 + 1;
            int Sum18 = i18 + Sum1(i7) + Ch(i7, i8, i9) + K[i22] + this.X[i22];
            i6 = Sum04 + Sum18;
            i2 = Sum18 + Sum0(i3) + Maj(i3, i4, i5);
            i10 = i22 + 1;
        }
        this.H1 += i2;
        this.H2 += i3;
        this.H3 += i4;
        this.H4 += i5;
        this.H5 += i6;
        this.H6 += i7;
        this.H7 += i8;
        this.H8 += i9;
        this.xOff = 0;
        for (int i23 = 0; i23 < 16; i23++) {
            this.X[i23] = 0;
        }
    }

    private static int Ch(int i, int i2, int i3) {
        return ((~i) & i3) ^ (i2 & i);
    }

    private static int Maj(int i, int i2, int i3) {
        return ((i ^ i2) & i3) | (i & i2);
    }

    private static int Sum0(int i) {
        return ((i << 10) | (i >>> 22)) ^ (((i >>> 2) | (i << 30)) ^ ((i >>> 13) | (i << 19)));
    }

    private static int Sum1(int i) {
        return ((i << 7) | (i >>> 25)) ^ (((i >>> 6) | (i << 26)) ^ ((i >>> 11) | (i << 21)));
    }

    private static int Theta0(int i) {
        return (i >>> 3) ^ (((i >>> 7) | (i << 25)) ^ ((i >>> 18) | (i << 14)));
    }

    private static int Theta1(int i) {
        return (i >>> 10) ^ (((i >>> 17) | (i << 15)) ^ ((i >>> 19) | (i << 13)));
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public com.android.internal.org.bouncycastle.util.Memoable copy() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public void reset(com.android.internal.org.bouncycastle.util.Memoable memoable) {
        copyIn((com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest) memoable);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 52];
        super.populateState(bArr);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H1, bArr, 16);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H2, bArr, 20);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H3, bArr, 24);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H4, bArr, 28);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H5, bArr, 32);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H6, bArr, 36);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H7, bArr, 40);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.H8, bArr, 44);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.xOff, bArr, 48);
        for (int i = 0; i != this.xOff; i++) {
            com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.X[i], bArr, (i * 4) + 52);
        }
        return bArr;
    }
}
