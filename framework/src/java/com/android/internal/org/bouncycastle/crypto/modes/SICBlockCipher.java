package com.android.internal.org.bouncycastle.crypto.modes;

/* loaded from: classes4.dex */
public class SICBlockCipher extends com.android.internal.org.bouncycastle.crypto.StreamBlockCipher implements com.android.internal.org.bouncycastle.crypto.SkippingStreamCipher {
    private byte[] IV;
    private final int blockSize;
    private int byteCount;
    private final com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;
    private byte[] counter;
    private byte[] counterOut;

    public SICBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        super(blockCipher);
        this.cipher = blockCipher;
        this.blockSize = this.cipher.getBlockSize();
        this.IV = new byte[this.blockSize];
        this.counter = new byte[this.blockSize];
        this.counterOut = new byte[this.blockSize];
        this.byteCount = 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
            this.IV = com.android.internal.org.bouncycastle.util.Arrays.clone(parametersWithIV.getIV());
            if (this.blockSize < this.IV.length) {
                throw new java.lang.IllegalArgumentException("CTR/SIC mode requires IV no greater than: " + this.blockSize + " bytes.");
            }
            int i = 8 > this.blockSize / 2 ? this.blockSize / 2 : 8;
            if (this.blockSize - this.IV.length > i) {
                throw new java.lang.IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (this.blockSize - i) + " bytes.");
            }
            if (parametersWithIV.getParameters() != null) {
                this.cipher.init(true, parametersWithIV.getParameters());
            }
            reset();
            return;
        }
        throw new java.lang.IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public java.lang.String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/SIC";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        processBytes(bArr, i, this.blockSize, bArr2, i2);
        return this.blockSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamBlockCipher
    protected byte calculateByte(byte b) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException {
        if (this.byteCount == 0) {
            this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
            byte[] bArr = this.counterOut;
            int i = this.byteCount;
            this.byteCount = i + 1;
            return (byte) (b ^ bArr[i]);
        }
        byte[] bArr2 = this.counterOut;
        int i2 = this.byteCount;
        this.byteCount = i2 + 1;
        byte b2 = (byte) (b ^ bArr2[i2]);
        if (this.byteCount == this.counter.length) {
            this.byteCount = 0;
            incrementCounterAt(0);
            checkCounter();
        }
        return b2;
    }

    private void checkCounter() {
        if (this.IV.length < this.blockSize) {
            for (int i = 0; i != this.IV.length; i++) {
                if (this.counter[i] != this.IV[i]) {
                    throw new java.lang.IllegalStateException("Counter in CTR/SIC mode out of range.");
                }
            }
        }
    }

    private void incrementCounterAt(int i) {
        byte b;
        int length = this.counter.length - i;
        do {
            length--;
            if (length >= 0) {
                byte[] bArr = this.counter;
                b = (byte) (bArr[length] + 1);
                bArr[length] = b;
            } else {
                return;
            }
        } while (b == 0);
    }

    private void incrementCounter(int i) {
        byte b = this.counter[this.counter.length - 1];
        byte[] bArr = this.counter;
        int length = this.counter.length - 1;
        bArr[length] = (byte) (bArr[length] + i);
        if (b != 0 && this.counter[this.counter.length - 1] < b) {
            incrementCounterAt(1);
        }
    }

    private void decrementCounterAt(int i) {
        byte b;
        int length = this.counter.length - i;
        do {
            length--;
            if (length >= 0) {
                b = (byte) (r1[length] - 1);
                this.counter[length] = b;
            } else {
                return;
            }
        } while (b == -1);
    }

    private void adjustCounter(long j) {
        long j2;
        long j3;
        int i = 5;
        if (j >= 0) {
            long j4 = (this.byteCount + j) / this.blockSize;
            if (j4 <= 255) {
                j3 = j4;
            } else {
                j3 = j4;
                while (i >= 1) {
                    long j5 = 1 << (i * 8);
                    while (j3 >= j5) {
                        incrementCounterAt(i);
                        j3 -= j5;
                    }
                    i--;
                }
            }
            incrementCounter((int) j3);
            this.byteCount = (int) ((j + this.byteCount) - (this.blockSize * j4));
            return;
        }
        long j6 = ((-j) - this.byteCount) / this.blockSize;
        if (j6 <= 255) {
            j2 = j6;
        } else {
            j2 = j6;
            while (i >= 1) {
                long j7 = 1 << (i * 8);
                while (j2 > j7) {
                    decrementCounterAt(i);
                    j2 -= j7;
                }
                i--;
            }
        }
        for (long j8 = 0; j8 != j2; j8++) {
            decrementCounterAt(0);
        }
        int i2 = (int) (this.byteCount + j + (this.blockSize * j6));
        if (i2 >= 0) {
            this.byteCount = 0;
        } else {
            decrementCounterAt(0);
            this.byteCount = this.blockSize + i2;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
        com.android.internal.org.bouncycastle.util.Arrays.fill(this.counter, (byte) 0);
        java.lang.System.arraycopy(this.IV, 0, this.counter, 0, this.IV.length);
        this.cipher.reset();
        this.byteCount = 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.SkippingCipher
    public long skip(long j) {
        adjustCounter(j);
        checkCounter();
        this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
        return j;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.SkippingCipher
    public long seekTo(long j) {
        reset();
        return skip(j);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.SkippingCipher
    public long getPosition() {
        int i;
        int length = this.counter.length;
        byte[] bArr = new byte[length];
        java.lang.System.arraycopy(this.counter, 0, bArr, 0, length);
        for (int i2 = length - 1; i2 >= 1; i2--) {
            if (i2 < this.IV.length) {
                i = (bArr[i2] & 255) - (this.IV[i2] & 255);
            } else {
                i = bArr[i2] & 255;
            }
            if (i < 0) {
                int i3 = i2 - 1;
                bArr[i3] = (byte) (bArr[i3] - 1);
                i += 256;
            }
            bArr[i2] = (byte) i;
        }
        return (com.android.internal.org.bouncycastle.util.Pack.bigEndianToLong(bArr, length - 8) * this.blockSize) + this.byteCount;
    }
}
