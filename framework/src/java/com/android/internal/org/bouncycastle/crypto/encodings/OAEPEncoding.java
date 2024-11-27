package com.android.internal.org.bouncycastle.crypto.encodings;

/* loaded from: classes4.dex */
public class OAEPEncoding implements com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher {
    private byte[] defHash;
    private com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private com.android.internal.org.bouncycastle.crypto.Digest mgf1Hash;
    private java.security.SecureRandom random;

    public OAEPEncoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1(), null);
    }

    public OAEPEncoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher, com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher, com.android.internal.org.bouncycastle.crypto.Digest digest, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest, bArr);
    }

    public OAEPEncoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher, com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.Digest digest2, byte[] bArr) {
        this.engine = asymmetricBlockCipher;
        this.mgf1Hash = digest2;
        this.defHash = new byte[digest.getDigestSize()];
        digest.reset();
        if (bArr != null) {
            digest.update(bArr, 0, bArr.length);
        }
        digest.doFinal(this.defHash, 0);
    }

    public com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            this.random = ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters).getRandom();
        } else {
            this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        }
        this.engine.init(z, cipherParameters);
        this.forEncryption = z;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        if (this.forEncryption) {
            return (inputBlockSize - 1) - (this.defHash.length * 2);
        }
        return inputBlockSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        if (this.forEncryption) {
            return outputBlockSize;
        }
        return (outputBlockSize - 1) - (this.defHash.length * 2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (this.forEncryption) {
            return encodeBlock(bArr, i, i2);
        }
        return decodeBlock(bArr, i, i2);
    }

    public byte[] encodeBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (i2 > getInputBlockSize()) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input data too long");
        }
        int inputBlockSize = getInputBlockSize() + 1 + (this.defHash.length * 2);
        byte[] bArr2 = new byte[inputBlockSize];
        int i3 = inputBlockSize - i2;
        java.lang.System.arraycopy(bArr, i, bArr2, i3, i2);
        bArr2[i3 - 1] = 1;
        java.lang.System.arraycopy(this.defHash, 0, bArr2, this.defHash.length, this.defHash.length);
        int length = this.defHash.length;
        byte[] bArr3 = new byte[length];
        this.random.nextBytes(bArr3);
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(bArr3, 0, length, inputBlockSize - this.defHash.length);
        for (int length2 = this.defHash.length; length2 != inputBlockSize; length2++) {
            bArr2[length2] = (byte) (bArr2[length2] ^ maskGeneratorFunction1[length2 - this.defHash.length]);
        }
        java.lang.System.arraycopy(bArr3, 0, bArr2, 0, this.defHash.length);
        byte[] maskGeneratorFunction12 = maskGeneratorFunction1(bArr2, this.defHash.length, inputBlockSize - this.defHash.length, this.defHash.length);
        for (int i4 = 0; i4 != this.defHash.length; i4++) {
            bArr2[i4] = (byte) (bArr2[i4] ^ maskGeneratorFunction12[i4]);
        }
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    public byte[] decodeBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        byte[] processBlock = this.engine.processBlock(bArr, i, i2);
        int outputBlockSize = this.engine.getOutputBlockSize();
        byte[] bArr2 = new byte[outputBlockSize];
        boolean z = outputBlockSize < (this.defHash.length * 2) + 1;
        if (processBlock.length <= outputBlockSize) {
            java.lang.System.arraycopy(processBlock, 0, bArr2, outputBlockSize - processBlock.length, processBlock.length);
        } else {
            java.lang.System.arraycopy(processBlock, 0, bArr2, 0, outputBlockSize);
            z = true;
        }
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(bArr2, this.defHash.length, outputBlockSize - this.defHash.length, this.defHash.length);
        for (int i3 = 0; i3 != this.defHash.length; i3++) {
            bArr2[i3] = (byte) (bArr2[i3] ^ maskGeneratorFunction1[i3]);
        }
        byte[] maskGeneratorFunction12 = maskGeneratorFunction1(bArr2, 0, this.defHash.length, outputBlockSize - this.defHash.length);
        for (int length = this.defHash.length; length != outputBlockSize; length++) {
            bArr2[length] = (byte) (bArr2[length] ^ maskGeneratorFunction12[length - this.defHash.length]);
        }
        boolean z2 = false;
        for (int i4 = 0; i4 != this.defHash.length; i4++) {
            if (this.defHash[i4] != bArr2[this.defHash.length + i4]) {
                z2 = true;
            }
        }
        int i5 = outputBlockSize;
        for (int length2 = this.defHash.length * 2; length2 != outputBlockSize; length2++) {
            if ((bArr2[length2] != 0) & (i5 == outputBlockSize)) {
                i5 = length2;
            }
        }
        boolean z3 = i5 > outputBlockSize + (-1);
        boolean z4 = bArr2[i5] != 1;
        int i6 = i5 + 1;
        if (z3 | z4 | z | z2) {
            com.android.internal.org.bouncycastle.util.Arrays.fill(bArr2, (byte) 0);
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("data wrong");
        }
        int i7 = outputBlockSize - i6;
        byte[] bArr3 = new byte[i7];
        java.lang.System.arraycopy(bArr2, i6, bArr3, 0, i7);
        com.android.internal.org.bouncycastle.util.Arrays.fill(bArr2, (byte) 0);
        return bArr3;
    }

    private void ItoOSP(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        int digestSize = this.mgf1Hash.getDigestSize();
        byte[] bArr3 = new byte[digestSize];
        byte[] bArr4 = new byte[4];
        this.mgf1Hash.reset();
        int i4 = 0;
        while (i4 < i3 / digestSize) {
            ItoOSP(i4, bArr4);
            this.mgf1Hash.update(bArr, i, i2);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            java.lang.System.arraycopy(bArr3, 0, bArr2, i4 * digestSize, digestSize);
            i4++;
        }
        int i5 = digestSize * i4;
        if (i5 < i3) {
            ItoOSP(i4, bArr4);
            this.mgf1Hash.update(bArr, i, i2);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            java.lang.System.arraycopy(bArr3, 0, bArr2, i5, i3 - i5);
        }
        return bArr2;
    }
}
