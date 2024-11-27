package com.android.internal.org.bouncycastle.crypto.modes;

/* loaded from: classes4.dex */
public class CBCBlockCipher implements com.android.internal.org.bouncycastle.crypto.BlockCipher {
    private byte[] IV;
    private int blockSize;
    private byte[] cbcNextV;
    private byte[] cbcV;
    private com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    private boolean encrypting;

    public CBCBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this.cipher = null;
        this.cipher = blockCipher;
        this.blockSize = blockCipher.getBlockSize();
        this.IV = new byte[this.blockSize];
        this.cbcV = new byte[this.blockSize];
        this.cbcNextV = new byte[this.blockSize];
    }

    public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
        boolean z2 = this.encrypting;
        this.encrypting = z;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length != this.blockSize) {
                throw new java.lang.IllegalArgumentException("initialisation vector must be the same length as block size");
            }
            java.lang.System.arraycopy(iv, 0, this.IV, 0, iv.length);
            reset();
            if (parametersWithIV.getParameters() != null) {
                this.cipher.init(z, parametersWithIV.getParameters());
                return;
            } else {
                if (z2 != z) {
                    throw new java.lang.IllegalArgumentException("cannot change encrypting state without providing key.");
                }
                return;
            }
        }
        reset();
        if (cipherParameters != null) {
            this.cipher.init(z, cipherParameters);
        } else if (z2 != z) {
            throw new java.lang.IllegalArgumentException("cannot change encrypting state without providing key.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public java.lang.String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CBC";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        return this.encrypting ? encryptBlock(bArr, i, bArr2, i2) : decryptBlock(bArr, i, bArr2, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
        java.lang.System.arraycopy(this.IV, 0, this.cbcV, 0, this.IV.length);
        com.android.internal.org.bouncycastle.util.Arrays.fill(this.cbcNextV, (byte) 0);
        this.cipher.reset();
    }

    private int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        if (this.blockSize + i > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        for (int i3 = 0; i3 < this.blockSize; i3++) {
            byte[] bArr3 = this.cbcV;
            bArr3[i3] = (byte) (bArr3[i3] ^ bArr[i + i3]);
        }
        int processBlock = this.cipher.processBlock(this.cbcV, 0, bArr2, i2);
        java.lang.System.arraycopy(bArr2, i2, this.cbcV, 0, this.cbcV.length);
        return processBlock;
    }

    private int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        if (this.blockSize + i > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        java.lang.System.arraycopy(bArr, i, this.cbcNextV, 0, this.blockSize);
        int processBlock = this.cipher.processBlock(bArr, i, bArr2, i2);
        for (int i3 = 0; i3 < this.blockSize; i3++) {
            int i4 = i2 + i3;
            bArr2[i4] = (byte) (bArr2[i4] ^ this.cbcV[i3]);
        }
        byte[] bArr3 = this.cbcV;
        this.cbcV = this.cbcNextV;
        this.cbcNextV = bArr3;
        return processBlock;
    }
}
