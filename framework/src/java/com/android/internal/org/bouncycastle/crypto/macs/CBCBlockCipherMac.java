package com.android.internal.org.bouncycastle.crypto.macs;

/* loaded from: classes4.dex */
public class CBCBlockCipherMac implements com.android.internal.org.bouncycastle.crypto.Mac {
    private byte[] buf;
    private int bufOff;
    private com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    private byte[] mac;
    private int macSize;
    private com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding padding;

    public CBCBlockCipherMac(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, null);
    }

    public CBCBlockCipherMac(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding blockCipherPadding) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, blockCipherPadding);
    }

    public CBCBlockCipherMac(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, int i) {
        this(blockCipher, i, null);
    }

    public CBCBlockCipherMac(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, int i, com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding blockCipherPadding) {
        if (i % 8 != 0) {
            throw new java.lang.IllegalArgumentException("MAC size must be multiple of 8");
        }
        this.cipher = new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(blockCipher);
        this.padding = blockCipherPadding;
        this.macSize = i / 8;
        this.mac = new byte[blockCipher.getBlockSize()];
        this.buf = new byte[blockCipher.getBlockSize()];
        this.bufOff = 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public java.lang.String getAlgorithmName() {
        return this.cipher.getAlgorithmName();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        reset();
        this.cipher.init(true, cipherParameters);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.macSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte b) {
        if (this.bufOff == this.buf.length) {
            this.cipher.processBlock(this.buf, 0, this.mac, 0);
            this.bufOff = 0;
        }
        byte[] bArr = this.buf;
        int i = this.bufOff;
        this.bufOff = i + 1;
        bArr[i] = b;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i, int i2) {
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = this.cipher.getBlockSize();
        int i3 = blockSize - this.bufOff;
        if (i2 > i3) {
            java.lang.System.arraycopy(bArr, i, this.buf, this.bufOff, i3);
            this.cipher.processBlock(this.buf, 0, this.mac, 0);
            this.bufOff = 0;
            i2 -= i3;
            i += i3;
            while (i2 > blockSize) {
                this.cipher.processBlock(bArr, i, this.mac, 0);
                i2 -= blockSize;
                i += blockSize;
            }
        }
        java.lang.System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
        this.bufOff += i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i) {
        int blockSize = this.cipher.getBlockSize();
        if (this.padding == null) {
            while (this.bufOff < blockSize) {
                this.buf[this.bufOff] = 0;
                this.bufOff++;
            }
        } else {
            if (this.bufOff == blockSize) {
                this.cipher.processBlock(this.buf, 0, this.mac, 0);
                this.bufOff = 0;
            }
            this.padding.addPadding(this.buf, this.bufOff);
        }
        this.cipher.processBlock(this.buf, 0, this.mac, 0);
        java.lang.System.arraycopy(this.mac, 0, bArr, i, this.macSize);
        reset();
        return this.macSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void reset() {
        for (int i = 0; i < this.buf.length; i++) {
            this.buf[i] = 0;
        }
        this.bufOff = 0;
        this.cipher.reset();
    }
}
