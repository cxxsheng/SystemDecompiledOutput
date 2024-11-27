package com.android.internal.org.bouncycastle.crypto.modes;

/* loaded from: classes4.dex */
public class GCMBlockCipher implements com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final long MAX_INPUT_SIZE = 68719476704L;
    private byte[] H;
    private byte[] J0;
    private byte[] S;
    private byte[] S_at;
    private byte[] S_atPre;
    private byte[] atBlock;
    private int atBlockPos;
    private long atLength;
    private long atLengthPre;
    private int blocksRemaining;
    private byte[] bufBlock;
    private int bufOff;
    private com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    private byte[] counter;
    private com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator exp;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private boolean initialised;
    private byte[] lastKey;
    private byte[] macBlock;
    private int macSize;
    private com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new java.lang.IllegalArgumentException("cipher required with a block size of 16.");
        }
        gCMMultiplier = gCMMultiplier == null ? new com.android.internal.org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier() : gCMMultiplier;
        this.cipher = blockCipher;
        this.multiplier = gCMMultiplier;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher
    public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public java.lang.String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
        byte[] iv;
        com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter;
        this.forEncryption = z;
        this.macBlock = null;
        this.initialised = true;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.AEADParameters) {
            com.android.internal.org.bouncycastle.crypto.params.AEADParameters aEADParameters = (com.android.internal.org.bouncycastle.crypto.params.AEADParameters) cipherParameters;
            iv = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 32 || macSize > 128 || macSize % 8 != 0) {
                throw new java.lang.IllegalArgumentException("Invalid value for MAC size: " + macSize);
            }
            this.macSize = macSize / 8;
            keyParameter = aEADParameters.getKey();
        } else if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = 16;
            keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new java.lang.IllegalArgumentException("invalid parameters passed to GCM");
        }
        this.bufBlock = new byte[z ? 16 : this.macSize + 16];
        if (iv == null || iv.length < 1) {
            throw new java.lang.IllegalArgumentException("IV must be at least 1 byte");
        }
        if (z && this.nonce != null && com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.nonce, iv)) {
            if (keyParameter == null) {
                throw new java.lang.IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
            if (this.lastKey != null && com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.lastKey, keyParameter.getKey())) {
                throw new java.lang.IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
        }
        this.nonce = iv;
        if (keyParameter != null) {
            this.lastKey = keyParameter.getKey();
        }
        if (keyParameter != null) {
            this.cipher.init(true, keyParameter);
            this.H = new byte[16];
            this.cipher.processBlock(this.H, 0, this.H, 0);
            this.multiplier.init(this.H);
            this.exp = null;
        } else if (this.H == null) {
            throw new java.lang.IllegalArgumentException("Key must be specified in initial init");
        }
        this.J0 = new byte[16];
        if (this.nonce.length == 12) {
            java.lang.System.arraycopy(this.nonce, 0, this.J0, 0, this.nonce.length);
            this.J0[15] = 1;
        } else {
            gHASH(this.J0, this.nonce, this.nonce.length);
            byte[] bArr = new byte[16];
            com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.nonce.length * 8, bArr, 8);
            gHASHBlock(this.J0, bArr);
        }
        this.S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = com.android.internal.org.bouncycastle.util.Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        if (this.initialAssociatedText != null) {
            processAADBytes(this.initialAssociatedText, 0, this.initialAssociatedText.length);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        if (this.macBlock == null) {
            return new byte[this.macSize];
        }
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.macBlock);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (this.forEncryption) {
            return i2 + this.macSize;
        }
        if (i2 < this.macSize) {
            return 0;
        }
        return i2 - this.macSize;
    }

    private long getTotalInputSizeAfterNewInput(int i) {
        return this.totalLength + i + this.bufOff;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (!this.forEncryption) {
            if (i2 < this.macSize) {
                return 0;
            }
            i2 -= this.macSize;
        }
        return i2 - (i2 % 16);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(1) > MAX_INPUT_SIZE) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input exceeded 68719476704 bytes");
        }
        this.atBlock[this.atBlockPos] = b;
        int i = this.atBlockPos + 1;
        this.atBlockPos = i;
        if (i == 16) {
            gHASHBlock(this.S_at, this.atBlock);
            this.atBlockPos = 0;
            this.atLength += 16;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(i2) > MAX_INPUT_SIZE) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input exceeded 68719476704 bytes");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            this.atBlock[this.atBlockPos] = bArr[i + i3];
            int i4 = this.atBlockPos + 1;
            this.atBlockPos = i4;
            if (i4 == 16) {
                gHASHBlock(this.S_at, this.atBlock);
                this.atBlockPos = 0;
                this.atLength += 16;
            }
        }
    }

    private void initCipher() {
        if (this.atLength > 0) {
            java.lang.System.arraycopy(this.S_at, 0, this.S_atPre, 0, 16);
            this.atLengthPre = this.atLength;
        }
        if (this.atBlockPos > 0) {
            gHASHPartial(this.S_atPre, this.atBlock, 0, this.atBlockPos);
            this.atLengthPre += this.atBlockPos;
        }
        if (this.atLengthPre > 0) {
            java.lang.System.arraycopy(this.S_atPre, 0, this.S, 0, 16);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(1) > MAX_INPUT_SIZE) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input exceeded 68719476704 bytes");
        }
        this.bufBlock[this.bufOff] = b;
        int i2 = this.bufOff + 1;
        this.bufOff = i2;
        if (i2 != this.bufBlock.length) {
            return 0;
        }
        processBlock(this.bufBlock, 0, bArr, i);
        if (this.forEncryption) {
            this.bufOff = 0;
        } else {
            java.lang.System.arraycopy(this.bufBlock, 16, this.bufBlock, 0, this.macSize);
            this.bufOff = this.macSize;
        }
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
        int i4;
        checkStatus();
        if (getTotalInputSizeAfterNewInput(i2) > MAX_INPUT_SIZE) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input exceeded 68719476704 bytes");
        }
        if (bArr.length - i < i2) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("Input buffer too short");
        }
        if (this.forEncryption) {
            if (this.bufOff != 0) {
                while (true) {
                    if (i2 <= 0) {
                        i4 = 0;
                        break;
                    }
                    i2--;
                    int i5 = i + 1;
                    this.bufBlock[this.bufOff] = bArr[i];
                    int i6 = this.bufOff + 1;
                    this.bufOff = i6;
                    if (i6 != 16) {
                        i = i5;
                    } else {
                        processBlock(this.bufBlock, 0, bArr2, i3);
                        this.bufOff = 0;
                        i4 = 16;
                        i = i5;
                        break;
                    }
                }
            } else {
                i4 = 0;
            }
            while (i2 >= 16) {
                processBlock(bArr, i, bArr2, i3 + i4);
                i += 16;
                i2 -= 16;
                i4 += 16;
            }
            if (i2 > 0) {
                java.lang.System.arraycopy(bArr, i, this.bufBlock, 0, i2);
                this.bufOff = i2;
            }
        } else {
            i4 = 0;
            for (int i7 = 0; i7 < i2; i7++) {
                this.bufBlock[this.bufOff] = bArr[i + i7];
                int i8 = this.bufOff + 1;
                this.bufOff = i8;
                if (i8 == this.bufBlock.length) {
                    processBlock(this.bufBlock, 0, bArr2, i3 + i4);
                    java.lang.System.arraycopy(this.bufBlock, 16, this.bufBlock, 0, this.macSize);
                    this.bufOff = this.macSize;
                    i4 += 16;
                }
            }
        }
        return i4;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        checkStatus();
        if (this.totalLength == 0) {
            initCipher();
        }
        int i2 = this.bufOff;
        if (this.forEncryption) {
            if (bArr.length - i < this.macSize + i2) {
                throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("Output buffer too short");
            }
        } else {
            if (i2 < this.macSize) {
                throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("data too short");
            }
            i2 -= this.macSize;
            if (bArr.length - i < i2) {
                throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("Output buffer too short");
            }
        }
        if (i2 > 0) {
            processPartial(this.bufBlock, 0, i2, bArr, i);
        }
        this.atLength += this.atBlockPos;
        if (this.atLength > this.atLengthPre) {
            if (this.atBlockPos > 0) {
                gHASHPartial(this.S_at, this.atBlock, 0, this.atBlockPos);
            }
            if (this.atLengthPre > 0) {
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(this.S_at, this.S_atPre);
            }
            long j = ((this.totalLength * 8) + 127) >>> 7;
            byte[] bArr2 = new byte[16];
            if (this.exp == null) {
                this.exp = new com.android.internal.org.bouncycastle.crypto.modes.gcm.BasicGCMExponentiator();
                this.exp.init(this.H);
            }
            this.exp.exponentiateX(j, bArr2);
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiply(this.S_at, bArr2);
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(this.S, this.S_at);
        }
        byte[] bArr3 = new byte[16];
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.atLength * 8, bArr3, 0);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.totalLength * 8, bArr3, 8);
        gHASHBlock(this.S, bArr3);
        byte[] bArr4 = new byte[16];
        this.cipher.processBlock(this.J0, 0, bArr4, 0);
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr4, this.S);
        this.macBlock = new byte[this.macSize];
        java.lang.System.arraycopy(bArr4, 0, this.macBlock, 0, this.macSize);
        if (this.forEncryption) {
            java.lang.System.arraycopy(this.macBlock, 0, bArr, i + this.bufOff, this.macSize);
            i2 += this.macSize;
        } else {
            byte[] bArr5 = new byte[this.macSize];
            java.lang.System.arraycopy(this.bufBlock, i2, bArr5, 0, this.macSize);
            if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(this.macBlock, bArr5)) {
                throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("mac check in GCM failed");
            }
        }
        reset(false);
        return i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true);
    }

    private void reset(boolean z) {
        this.cipher.reset();
        this.S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = com.android.internal.org.bouncycastle.util.Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        if (this.bufBlock != null) {
            com.android.internal.org.bouncycastle.util.Arrays.fill(this.bufBlock, (byte) 0);
        }
        if (z) {
            this.macBlock = null;
        }
        if (this.forEncryption) {
            this.initialised = false;
        } else if (this.initialAssociatedText != null) {
            processAADBytes(this.initialAssociatedText, 0, this.initialAssociatedText.length);
        }
    }

    private void processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (bArr2.length - i2 < 16) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("Output buffer too short");
        }
        if (this.totalLength == 0) {
            initCipher();
        }
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr3, bArr, i);
            gHASHBlock(this.S, bArr3);
            java.lang.System.arraycopy(bArr3, 0, bArr2, i2, 16);
        } else {
            gHASHBlock(this.S, bArr, i);
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr3, 0, bArr, i, bArr2, i2);
        }
        this.totalLength += 16;
    }

    private void processPartial(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr, i, bArr3, 0, i2);
            gHASHPartial(this.S, bArr, i, i2);
        } else {
            gHASHPartial(this.S, bArr, i, i2);
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr, i, bArr3, 0, i2);
        }
        java.lang.System.arraycopy(bArr, i, bArr2, i3, i2);
        this.totalLength += i2;
    }

    private void gHASH(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < i; i2 += 16) {
            gHASHPartial(bArr, bArr2, i2, java.lang.Math.min(i - i2, 16));
        }
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2) {
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr, bArr2);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2, int i) {
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr, bArr2, i);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHPartial(byte[] bArr, byte[] bArr2, int i, int i2) {
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(bArr, bArr2, i, i2);
        this.multiplier.multiplyH(bArr);
    }

    private void getNextCTRBlock(byte[] bArr) {
        if (this.blocksRemaining == 0) {
            throw new java.lang.IllegalStateException("Attempt to process too many blocks");
        }
        this.blocksRemaining--;
        int i = (this.counter[15] & 255) + 1;
        this.counter[15] = (byte) i;
        int i2 = (i >>> 8) + (this.counter[14] & 255);
        this.counter[14] = (byte) i2;
        int i3 = (i2 >>> 8) + (this.counter[13] & 255);
        this.counter[13] = (byte) i3;
        this.counter[12] = (byte) ((i3 >>> 8) + (this.counter[12] & 255));
        this.cipher.processBlock(this.counter, 0, bArr, 0);
    }

    private void checkStatus() {
        if (!this.initialised) {
            if (this.forEncryption) {
                throw new java.lang.IllegalStateException("GCM cipher cannot be reused for encryption");
            }
            throw new java.lang.IllegalStateException("GCM cipher needs to be initialised");
        }
    }
}
