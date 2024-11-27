package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class PKCS12ParametersGenerator extends com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator {
    public static final int IV_MATERIAL = 2;
    public static final int KEY_MATERIAL = 1;
    public static final int MAC_MATERIAL = 3;
    private com.android.internal.org.bouncycastle.crypto.Digest digest;
    private int u;
    private int v;

    public PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this.digest = digest;
        if (digest instanceof com.android.internal.org.bouncycastle.crypto.ExtendedDigest) {
            this.u = digest.getDigestSize();
            this.v = ((com.android.internal.org.bouncycastle.crypto.ExtendedDigest) digest).getByteLength();
            return;
        }
        throw new java.lang.IllegalArgumentException("Digest " + digest.getAlgorithmName() + " unsupported");
    }

    private void adjust(byte[] bArr, int i, byte[] bArr2) {
        int i2 = (bArr2[bArr2.length - 1] & 255) + (bArr[(bArr2.length + i) - 1] & 255) + 1;
        bArr[(bArr2.length + i) - 1] = (byte) i2;
        int i3 = i2 >>> 8;
        for (int length = bArr2.length - 2; length >= 0; length--) {
            int i4 = i + length;
            int i5 = i3 + (bArr2[length] & 255) + (bArr[i4] & 255);
            bArr[i4] = (byte) i5;
            i3 = i5 >>> 8;
        }
    }

    private byte[] generateDerivedKey(int i, int i2) {
        byte[] bArr;
        byte[] bArr2;
        int i3;
        int i4 = this.v;
        byte[] bArr3 = new byte[i4];
        byte[] bArr4 = new byte[i2];
        int i5 = 0;
        for (int i6 = 0; i6 != i4; i6++) {
            bArr3[i6] = (byte) i;
        }
        int i7 = 1;
        if (this.salt != null && this.salt.length != 0) {
            int length = this.v * (((this.salt.length + this.v) - 1) / this.v);
            bArr = new byte[length];
            for (int i8 = 0; i8 != length; i8++) {
                bArr[i8] = this.salt[i8 % this.salt.length];
            }
        } else {
            bArr = new byte[0];
        }
        if (this.password != null && this.password.length != 0) {
            int length2 = this.v * (((this.password.length + this.v) - 1) / this.v);
            bArr2 = new byte[length2];
            for (int i9 = 0; i9 != length2; i9++) {
                bArr2[i9] = this.password[i9 % this.password.length];
            }
        } else {
            bArr2 = new byte[0];
        }
        int length3 = bArr.length + bArr2.length;
        byte[] bArr5 = new byte[length3];
        java.lang.System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        java.lang.System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
        int i10 = this.v;
        byte[] bArr6 = new byte[i10];
        int i11 = ((this.u + i2) - 1) / this.u;
        int i12 = this.u;
        byte[] bArr7 = new byte[i12];
        int i13 = 1;
        while (i13 <= i11) {
            this.digest.update(bArr3, i5, i4);
            this.digest.update(bArr5, i5, length3);
            this.digest.doFinal(bArr7, i5);
            for (int i14 = i7; i14 < this.iterationCount; i14++) {
                this.digest.update(bArr7, i5, i12);
                this.digest.doFinal(bArr7, i5);
            }
            for (int i15 = i5; i15 != i10; i15++) {
                bArr6[i15] = bArr7[i15 % i12];
            }
            for (int i16 = i5; i16 != length3 / this.v; i16++) {
                adjust(bArr5, this.v * i16, bArr6);
            }
            if (i13 == i11) {
                int i17 = i13 - 1;
                int i18 = this.u * i17;
                int i19 = i2 - (i17 * this.u);
                i3 = 0;
                java.lang.System.arraycopy(bArr7, 0, bArr4, i18, i19);
            } else {
                i3 = i5;
                java.lang.System.arraycopy(bArr7, i3, bArr4, (i13 - 1) * this.u, i12);
            }
            i13++;
            i5 = i3;
            i7 = 1;
        }
        return bArr4;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey(1, i2), 0, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] generateDerivedKey = generateDerivedKey(1, i3);
        return new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey, 0, i3), generateDerivedKey(2, i4), 0, i4);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters(int i) {
        int i2 = i / 8;
        return new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey(3, i2), 0, i2);
    }
}
