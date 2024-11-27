package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class DESedeWrapEngine implements com.android.internal.org.bouncycastle.crypto.Wrapper {
    private static final byte[] IV2 = {74, -35, -94, 44, 121, -24, 33, 5};
    private com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher engine;
    private boolean forWrapping;
    private byte[] iv;
    private com.android.internal.org.bouncycastle.crypto.params.KeyParameter param;
    private com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV paramPlusIV;
    com.android.internal.org.bouncycastle.crypto.Digest sha1 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
    byte[] digest = new byte[20];

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        java.security.SecureRandom secureRandom;
        this.forWrapping = z;
        this.engine = new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine());
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters;
            com.android.internal.org.bouncycastle.crypto.CipherParameters parameters = parametersWithRandom.getParameters();
            java.security.SecureRandom random = parametersWithRandom.getRandom();
            cipherParameters = parameters;
            secureRandom = random;
        } else {
            secureRandom = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        }
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) {
            this.param = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters;
            if (this.forWrapping) {
                this.iv = new byte[8];
                secureRandom.nextBytes(this.iv);
                this.paramPlusIV = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(this.param, this.iv);
                return;
            }
            return;
        }
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            this.paramPlusIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            this.iv = this.paramPlusIV.getIV();
            this.param = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) this.paramPlusIV.getParameters();
            if (this.forWrapping) {
                if (this.iv == null || this.iv.length != 8) {
                    throw new java.lang.IllegalArgumentException("IV is not 8 octets");
                }
                return;
            }
            throw new java.lang.IllegalArgumentException("You should not supply an IV for unwrapping");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public java.lang.String getAlgorithmName() {
        return android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.forWrapping) {
            throw new java.lang.IllegalStateException("Not initialized for wrapping");
        }
        byte[] bArr2 = new byte[i2];
        java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
        byte[] calculateCMSKeyChecksum = calculateCMSKeyChecksum(bArr2);
        int length = calculateCMSKeyChecksum.length + i2;
        byte[] bArr3 = new byte[length];
        java.lang.System.arraycopy(bArr2, 0, bArr3, 0, i2);
        java.lang.System.arraycopy(calculateCMSKeyChecksum, 0, bArr3, i2, calculateCMSKeyChecksum.length);
        int blockSize = this.engine.getBlockSize();
        if (length % blockSize != 0) {
            throw new java.lang.IllegalStateException("Not multiple of block length");
        }
        this.engine.init(true, this.paramPlusIV);
        byte[] bArr4 = new byte[length];
        for (int i3 = 0; i3 != length; i3 += blockSize) {
            this.engine.processBlock(bArr3, i3, bArr4, i3);
        }
        byte[] bArr5 = new byte[this.iv.length + length];
        java.lang.System.arraycopy(this.iv, 0, bArr5, 0, this.iv.length);
        java.lang.System.arraycopy(bArr4, 0, bArr5, this.iv.length, length);
        byte[] reverse = reverse(bArr5);
        this.engine.init(true, new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(this.param, IV2));
        for (int i4 = 0; i4 != reverse.length; i4 += blockSize) {
            this.engine.processBlock(reverse, i4, reverse, i4);
        }
        return reverse;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (this.forWrapping) {
            throw new java.lang.IllegalStateException("Not set for unwrapping");
        }
        if (bArr == null) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("Null pointer as ciphertext");
        }
        int blockSize = this.engine.getBlockSize();
        if (i2 % blockSize != 0) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("Ciphertext not multiple of " + blockSize);
        }
        this.engine.init(false, new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(this.param, IV2));
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 != i2; i3 += blockSize) {
            this.engine.processBlock(bArr, i + i3, bArr2, i3);
        }
        byte[] reverse = reverse(bArr2);
        this.iv = new byte[8];
        int length = reverse.length - 8;
        byte[] bArr3 = new byte[length];
        java.lang.System.arraycopy(reverse, 0, this.iv, 0, 8);
        java.lang.System.arraycopy(reverse, 8, bArr3, 0, reverse.length - 8);
        this.paramPlusIV = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(this.param, this.iv);
        this.engine.init(false, this.paramPlusIV);
        byte[] bArr4 = new byte[length];
        for (int i4 = 0; i4 != length; i4 += blockSize) {
            this.engine.processBlock(bArr3, i4, bArr4, i4);
        }
        int i5 = length - 8;
        byte[] bArr5 = new byte[i5];
        byte[] bArr6 = new byte[8];
        java.lang.System.arraycopy(bArr4, 0, bArr5, 0, i5);
        java.lang.System.arraycopy(bArr4, i5, bArr6, 0, 8);
        if (!checkCMSKeyChecksum(bArr5, bArr6)) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("Checksum inside ciphertext is corrupted");
        }
        return bArr5;
    }

    private byte[] calculateCMSKeyChecksum(byte[] bArr) {
        byte[] bArr2 = new byte[8];
        this.sha1.update(bArr, 0, bArr.length);
        this.sha1.doFinal(this.digest, 0);
        java.lang.System.arraycopy(this.digest, 0, bArr2, 0, 8);
        return bArr2;
    }

    private boolean checkCMSKeyChecksum(byte[] bArr, byte[] bArr2) {
        return com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(bArr), bArr2);
    }

    private static byte[] reverse(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            bArr2[i] = bArr[bArr.length - i2];
            i = i2;
        }
        return bArr2;
    }
}
