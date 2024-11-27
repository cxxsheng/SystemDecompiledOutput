package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public class BufferedBlockCipher {
    protected byte[] buf;
    protected int bufOff;
    protected com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    protected boolean forEncryption;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;

    protected BufferedBlockCipher() {
    }

    public BufferedBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this.cipher = blockCipher;
        this.buf = new byte[blockCipher.getBlockSize()];
        boolean z = false;
        this.bufOff = 0;
        java.lang.String algorithmName = blockCipher.getAlgorithmName();
        int indexOf = algorithmName.indexOf(47) + 1;
        this.pgpCFB = indexOf > 0 && algorithmName.startsWith("PGP", indexOf);
        if (this.pgpCFB || (blockCipher instanceof com.android.internal.org.bouncycastle.crypto.StreamCipher)) {
            this.partialBlockOkay = true;
            return;
        }
        if (indexOf > 0 && algorithmName.startsWith("OpenPGP", indexOf)) {
            z = true;
        }
        this.partialBlockOkay = z;
    }

    public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
        this.forEncryption = z;
        reset();
        this.cipher.init(z, cipherParameters);
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public int getUpdateOutputSize(int i) {
        int length;
        int i2 = i + this.bufOff;
        if (this.pgpCFB) {
            if (this.forEncryption) {
                length = (i2 % this.buf.length) - (this.cipher.getBlockSize() + 2);
            } else {
                length = i2 % this.buf.length;
            }
        } else {
            length = i2 % this.buf.length;
        }
        return i2 - length;
    }

    public int getOutputSize(int i) {
        return i + this.bufOff;
    }

    public int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        byte[] bArr2 = this.buf;
        int i2 = this.bufOff;
        this.bufOff = i2 + 1;
        bArr2[i2] = b;
        if (this.bufOff != this.buf.length) {
            return 0;
        }
        int processBlock = this.cipher.processBlock(this.buf, 0, bArr, i);
        this.bufOff = 0;
        return processBlock;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        int i4;
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int updateOutputSize = getUpdateOutputSize(i2);
        if (updateOutputSize > 0 && updateOutputSize + i3 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        int length = this.buf.length - this.bufOff;
        if (i2 <= length) {
            i4 = 0;
        } else {
            java.lang.System.arraycopy(bArr, i, this.buf, this.bufOff, length);
            i4 = this.cipher.processBlock(this.buf, 0, bArr2, i3) + 0;
            this.bufOff = 0;
            i2 -= length;
            i += length;
            while (i2 > this.buf.length) {
                i4 += this.cipher.processBlock(bArr, i, bArr2, i3 + i4);
                i2 -= blockSize;
                i += blockSize;
            }
        }
        java.lang.System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
        this.bufOff += i2;
        if (this.bufOff == this.buf.length) {
            int processBlock = i4 + this.cipher.processBlock(this.buf, 0, bArr2, i3 + i4);
            this.bufOff = 0;
            return processBlock;
        }
        return i4;
    }

    public int doFinal(byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        try {
            if (this.bufOff + i > bArr.length) {
                throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short for doFinal()");
            }
            int i2 = 0;
            if (this.bufOff != 0) {
                if (!this.partialBlockOkay) {
                    throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("data not block size aligned");
                }
                this.cipher.processBlock(this.buf, 0, this.buf, 0);
                int i3 = this.bufOff;
                this.bufOff = 0;
                java.lang.System.arraycopy(this.buf, 0, bArr, i, i3);
                i2 = i3;
            }
            return i2;
        } finally {
            reset();
        }
    }

    public void reset() {
        for (int i = 0; i < this.buf.length; i++) {
            this.buf[i] = 0;
        }
        this.bufOff = 0;
        this.cipher.reset();
    }
}
