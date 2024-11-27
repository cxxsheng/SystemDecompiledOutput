package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class DESedeEngine extends com.android.internal.org.bouncycastle.crypto.engines.DESEngine {
    protected static final int BLOCK_SIZE = 8;
    private boolean forEncryption;
    private int[] workingKey1 = null;
    private int[] workingKey2 = null;
    private int[] workingKey3 = null;

    @Override // com.android.internal.org.bouncycastle.crypto.engines.DESEngine, com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter)) {
            throw new java.lang.IllegalArgumentException("invalid parameter passed to DESede init - " + cipherParameters.getClass().getName());
        }
        byte[] key = ((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters).getKey();
        if (key.length != 24 && key.length != 16) {
            throw new java.lang.IllegalArgumentException("key size must be 16 or 24 bytes.");
        }
        this.forEncryption = z;
        byte[] bArr = new byte[8];
        java.lang.System.arraycopy(key, 0, bArr, 0, 8);
        this.workingKey1 = generateWorkingKey(z, bArr);
        byte[] bArr2 = new byte[8];
        java.lang.System.arraycopy(key, 8, bArr2, 0, 8);
        this.workingKey2 = generateWorkingKey(!z, bArr2);
        if (key.length == 24) {
            byte[] bArr3 = new byte[8];
            java.lang.System.arraycopy(key, 16, bArr3, 0, 8);
            this.workingKey3 = generateWorkingKey(z, bArr3);
            return;
        }
        this.workingKey3 = this.workingKey1;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.engines.DESEngine, com.android.internal.org.bouncycastle.crypto.BlockCipher
    public java.lang.String getAlgorithmName() {
        return android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.engines.DESEngine, com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.engines.DESEngine, com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey1 == null) {
            throw new java.lang.IllegalStateException("DESede engine not initialised");
        }
        if (i + 8 > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        if (i2 + 8 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        byte[] bArr3 = new byte[8];
        if (this.forEncryption) {
            desFunc(this.workingKey1, bArr, i, bArr3, 0);
            desFunc(this.workingKey2, bArr3, 0, bArr3, 0);
            desFunc(this.workingKey3, bArr3, 0, bArr2, i2);
        } else {
            desFunc(this.workingKey3, bArr, i, bArr3, 0);
            desFunc(this.workingKey2, bArr3, 0, bArr3, 0);
            desFunc(this.workingKey1, bArr3, 0, bArr2, i2);
        }
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.engines.DESEngine, com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
