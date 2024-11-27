package com.android.internal.org.bouncycastle.crypto.modes;

/* loaded from: classes4.dex */
public class CCMBlockCipher implements com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher {
    private int blockSize;
    private com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private com.android.internal.org.bouncycastle.crypto.CipherParameters keyParam;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;
    private com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher.ExposedByteArrayOutputStream associatedText = new com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher.ExposedByteArrayOutputStream();
    private com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher.ExposedByteArrayOutputStream data = new com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher.ExposedByteArrayOutputStream();

    public CCMBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this.cipher = blockCipher;
        this.blockSize = blockCipher.getBlockSize();
        this.macBlock = new byte[this.blockSize];
        if (this.blockSize != 16) {
            throw new java.lang.IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher
    public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters parameters;
        this.forEncryption = z;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.AEADParameters) {
            com.android.internal.org.bouncycastle.crypto.params.AEADParameters aEADParameters = (com.android.internal.org.bouncycastle.crypto.params.AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            this.macSize = getMacSize(z, aEADParameters.getMacSize());
            parameters = aEADParameters.getKey();
        } else if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = getMacSize(z, 64);
            parameters = parametersWithIV.getParameters();
        } else {
            throw new java.lang.IllegalArgumentException("invalid parameters passed to CCM: " + cipherParameters.getClass().getName());
        }
        if (parameters != null) {
            this.keyParam = parameters;
        }
        if (this.nonce == null || this.nonce.length < 7 || this.nonce.length > 13) {
            throw new java.lang.IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public java.lang.String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CCM";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        this.associatedText.write(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        this.associatedText.write(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        this.data.write(b);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        if (bArr.length < i + i2) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input buffer too short");
        }
        this.data.write(bArr, i, i2);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        int processPacket = processPacket(this.data.getBuffer(), 0, this.data.size(), bArr, i);
        reset();
        return processPacket;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        this.cipher.reset();
        this.associatedText.reset();
        this.data.reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        int i = this.macSize;
        byte[] bArr = new byte[i];
        java.lang.System.arraycopy(this.macBlock, 0, bArr, 0, i);
        return bArr;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i) {
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i) {
        int size = i + this.data.size();
        if (this.forEncryption) {
            return size + this.macSize;
        }
        if (size < this.macSize) {
            return 0;
        }
        return size - this.macSize;
    }

    public byte[] processPacket(byte[] bArr, int i, int i2) throws java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        byte[] bArr2;
        if (this.forEncryption) {
            bArr2 = new byte[this.macSize + i2];
        } else {
            if (i2 < this.macSize) {
                throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("data too short");
            }
            bArr2 = new byte[i2 - this.macSize];
        }
        processPacket(bArr, i, i2, bArr2, 0);
        return bArr2;
    }

    public int processPacket(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException, com.android.internal.org.bouncycastle.crypto.DataLengthException {
        int i4;
        int i5;
        if (this.keyParam == null) {
            throw new java.lang.IllegalStateException("CCM cipher unitialized.");
        }
        int length = 15 - this.nonce.length;
        if (length < 4 && i2 >= (1 << (length * 8))) {
            throw new java.lang.IllegalStateException("CCM packet too large for choice of q.");
        }
        byte[] bArr3 = new byte[this.blockSize];
        bArr3[0] = (byte) ((length - 1) & 7);
        java.lang.System.arraycopy(this.nonce, 0, bArr3, 1, this.nonce.length);
        com.android.internal.org.bouncycastle.crypto.modes.SICBlockCipher sICBlockCipher = new com.android.internal.org.bouncycastle.crypto.modes.SICBlockCipher(this.cipher);
        sICBlockCipher.init(this.forEncryption, new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(this.keyParam, bArr3));
        if (this.forEncryption) {
            i4 = this.macSize + i2;
            if (bArr2.length < i4 + i3) {
                throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("Output buffer too short.");
            }
            calculateMac(bArr, i, i2, this.macBlock);
            byte[] bArr4 = new byte[this.blockSize];
            sICBlockCipher.processBlock(this.macBlock, 0, bArr4, 0);
            int i6 = i;
            int i7 = i3;
            while (true) {
                i5 = i + i2;
                if (i6 >= i5 - this.blockSize) {
                    break;
                }
                sICBlockCipher.processBlock(bArr, i6, bArr2, i7);
                i7 += this.blockSize;
                i6 += this.blockSize;
            }
            byte[] bArr5 = new byte[this.blockSize];
            int i8 = i5 - i6;
            java.lang.System.arraycopy(bArr, i6, bArr5, 0, i8);
            sICBlockCipher.processBlock(bArr5, 0, bArr5, 0);
            java.lang.System.arraycopy(bArr5, 0, bArr2, i7, i8);
            java.lang.System.arraycopy(bArr4, 0, bArr2, i3 + i2, this.macSize);
        } else {
            if (i2 < this.macSize) {
                throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("data too short");
            }
            i4 = i2 - this.macSize;
            if (bArr2.length < i4 + i3) {
                throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("Output buffer too short.");
            }
            int i9 = i + i4;
            java.lang.System.arraycopy(bArr, i9, this.macBlock, 0, this.macSize);
            sICBlockCipher.processBlock(this.macBlock, 0, this.macBlock, 0);
            for (int i10 = this.macSize; i10 != this.macBlock.length; i10++) {
                this.macBlock[i10] = 0;
            }
            int i11 = i;
            int i12 = i3;
            while (i11 < i9 - this.blockSize) {
                sICBlockCipher.processBlock(bArr, i11, bArr2, i12);
                i12 += this.blockSize;
                i11 += this.blockSize;
            }
            byte[] bArr6 = new byte[this.blockSize];
            int i13 = i4 - (i11 - i);
            java.lang.System.arraycopy(bArr, i11, bArr6, 0, i13);
            sICBlockCipher.processBlock(bArr6, 0, bArr6, 0);
            java.lang.System.arraycopy(bArr6, 0, bArr2, i12, i13);
            byte[] bArr7 = new byte[this.blockSize];
            calculateMac(bArr2, i3, i4, bArr7);
            if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(this.macBlock, bArr7)) {
                throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("mac check in CCM failed");
            }
        }
        return i4;
    }

    private int calculateMac(byte[] bArr, int i, int i2, byte[] bArr2) {
        com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac cBCBlockCipherMac = new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cBCBlockCipherMac.init(this.keyParam);
        byte[] bArr3 = new byte[16];
        if (hasAssociatedText()) {
            bArr3[0] = (byte) (bArr3[0] | 64);
        }
        int i3 = 2;
        bArr3[0] = (byte) (bArr3[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        bArr3[0] = (byte) (bArr3[0] | (((15 - this.nonce.length) - 1) & 7));
        java.lang.System.arraycopy(this.nonce, 0, bArr3, 1, this.nonce.length);
        int i4 = i2;
        int i5 = 1;
        while (i4 > 0) {
            bArr3[16 - i5] = (byte) (i4 & 255);
            i4 >>>= 8;
            i5++;
        }
        cBCBlockCipherMac.update(bArr3, 0, 16);
        if (hasAssociatedText()) {
            int associatedTextLength = getAssociatedTextLength();
            if (associatedTextLength < 65280) {
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
            } else {
                cBCBlockCipherMac.update((byte) -1);
                cBCBlockCipherMac.update((byte) -2);
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 24));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 16));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
                i3 = 6;
            }
            if (this.initialAssociatedText != null) {
                cBCBlockCipherMac.update(this.initialAssociatedText, 0, this.initialAssociatedText.length);
            }
            if (this.associatedText.size() > 0) {
                cBCBlockCipherMac.update(this.associatedText.getBuffer(), 0, this.associatedText.size());
            }
            int i6 = (i3 + associatedTextLength) % 16;
            if (i6 != 0) {
                while (i6 != 16) {
                    cBCBlockCipherMac.update((byte) 0);
                    i6++;
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i, i2);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private int getMacSize(boolean z, int i) {
        if (z && (i < 32 || i > 128 || (i & 15) != 0)) {
            throw new java.lang.IllegalArgumentException("tag length in octets must be one of {4,6,8,10,12,14,16}");
        }
        return i >>> 3;
    }

    private int getAssociatedTextLength() {
        return this.associatedText.size() + (this.initialAssociatedText == null ? 0 : this.initialAssociatedText.length);
    }

    private boolean hasAssociatedText() {
        return getAssociatedTextLength() > 0;
    }

    private class ExposedByteArrayOutputStream extends java.io.ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] getBuffer() {
            return this.buf;
        }
    }
}
