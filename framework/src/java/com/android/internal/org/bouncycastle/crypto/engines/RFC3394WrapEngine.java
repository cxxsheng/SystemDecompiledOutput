package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class RFC3394WrapEngine implements com.android.internal.org.bouncycastle.crypto.Wrapper {
    private com.android.internal.org.bouncycastle.crypto.BlockCipher engine;
    private boolean forWrapping;
    private byte[] iv;
    private com.android.internal.org.bouncycastle.crypto.params.KeyParameter param;
    private boolean wrapCipherMode;

    public RFC3394WrapEngine(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this(blockCipher, false);
    }

    public RFC3394WrapEngine(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, boolean z) {
        this.iv = new byte[]{-90, -90, -90, -90, -90, -90, -90, -90};
        this.engine = blockCipher;
        this.wrapCipherMode = !z;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.forWrapping = z;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            cipherParameters = ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters).getParameters();
        }
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) {
            this.param = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters;
            return;
        }
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            this.iv = parametersWithIV.getIV();
            this.param = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) parametersWithIV.getParameters();
            if (this.iv.length != 8) {
                throw new java.lang.IllegalArgumentException("IV not equal to 8");
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public java.lang.String getAlgorithmName() {
        return this.engine.getAlgorithmName();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.forWrapping) {
            throw new java.lang.IllegalStateException("not set for wrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("wrap data must be a multiple of 8 bytes");
        }
        byte[] bArr2 = new byte[this.iv.length + i2];
        byte[] bArr3 = new byte[this.iv.length + 8];
        java.lang.System.arraycopy(this.iv, 0, bArr2, 0, this.iv.length);
        java.lang.System.arraycopy(bArr, i, bArr2, this.iv.length, i2);
        this.engine.init(this.wrapCipherMode, this.param);
        for (int i4 = 0; i4 != 6; i4++) {
            for (int i5 = 1; i5 <= i3; i5++) {
                java.lang.System.arraycopy(bArr2, 0, bArr3, 0, this.iv.length);
                int i6 = i5 * 8;
                java.lang.System.arraycopy(bArr2, i6, bArr3, this.iv.length, 8);
                this.engine.processBlock(bArr3, 0, bArr3, 0);
                int i7 = (i3 * i4) + i5;
                int i8 = 1;
                while (i7 != 0) {
                    int length = this.iv.length - i8;
                    bArr3[length] = (byte) (((byte) i7) ^ bArr3[length]);
                    i7 >>>= 8;
                    i8++;
                }
                java.lang.System.arraycopy(bArr3, 0, bArr2, 0, 8);
                java.lang.System.arraycopy(bArr3, 8, bArr2, i6, 8);
            }
        }
        return bArr2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (this.forWrapping) {
            throw new java.lang.IllegalStateException("not set for unwrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        byte[] bArr2 = new byte[i2 - this.iv.length];
        byte[] bArr3 = new byte[this.iv.length];
        byte[] bArr4 = new byte[this.iv.length + 8];
        java.lang.System.arraycopy(bArr, i, bArr3, 0, this.iv.length);
        java.lang.System.arraycopy(bArr, i + this.iv.length, bArr2, 0, i2 - this.iv.length);
        this.engine.init(!this.wrapCipherMode, this.param);
        int i4 = i3 - 1;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = i4; i6 >= 1; i6--) {
                java.lang.System.arraycopy(bArr3, 0, bArr4, 0, this.iv.length);
                int i7 = (i6 - 1) * 8;
                java.lang.System.arraycopy(bArr2, i7, bArr4, this.iv.length, 8);
                int i8 = (i4 * i5) + i6;
                int i9 = 1;
                while (i8 != 0) {
                    int length = this.iv.length - i9;
                    bArr4[length] = (byte) (((byte) i8) ^ bArr4[length]);
                    i8 >>>= 8;
                    i9++;
                }
                this.engine.processBlock(bArr4, 0, bArr4, 0);
                java.lang.System.arraycopy(bArr4, 0, bArr3, 0, 8);
                java.lang.System.arraycopy(bArr4, 8, bArr2, i7, 8);
            }
        }
        if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(bArr3, this.iv)) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("checksum failed");
        }
        return bArr2;
    }
}
