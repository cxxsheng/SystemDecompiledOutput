package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class RC4Engine implements com.android.internal.org.bouncycastle.crypto.StreamCipher {
    private static final int STATE_LENGTH = 256;
    private byte[] engineState = null;
    private int x = 0;
    private int y = 0;
    private byte[] workingKey = null;

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) {
            this.workingKey = ((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters).getKey();
            setKey(this.workingKey);
            return;
        }
        throw new java.lang.IllegalArgumentException("invalid parameter passed to RC4 init - " + cipherParameters.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public java.lang.String getAlgorithmName() {
        return "RC4";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b) {
        this.x = (this.x + 1) & 255;
        this.y = (this.engineState[this.x] + this.y) & 255;
        byte b2 = this.engineState[this.x];
        this.engineState[this.x] = this.engineState[this.y];
        this.engineState[this.y] = b2;
        return (byte) (b ^ this.engineState[(this.engineState[this.x] + this.engineState[this.y]) & 255]);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i + i2 > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        if (i3 + i2 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        for (int i4 = 0; i4 < i2; i4++) {
            this.x = (this.x + 1) & 255;
            this.y = (this.engineState[this.x] + this.y) & 255;
            byte b = this.engineState[this.x];
            this.engineState[this.x] = this.engineState[this.y];
            this.engineState[this.y] = b;
            bArr2[i4 + i3] = (byte) (bArr[i4 + i] ^ this.engineState[(this.engineState[this.x] + this.engineState[this.y]) & 255]);
        }
        return i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public void reset() {
        setKey(this.workingKey);
    }

    private void setKey(byte[] bArr) {
        this.workingKey = bArr;
        this.x = 0;
        this.y = 0;
        if (this.engineState == null) {
            this.engineState = new byte[256];
        }
        for (int i = 0; i < 256; i++) {
            this.engineState[i] = (byte) i;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            i3 = ((bArr[i2] & 255) + this.engineState[i4] + i3) & 255;
            byte b = this.engineState[i4];
            this.engineState[i4] = this.engineState[i3];
            this.engineState[i3] = b;
            i2 = (i2 + 1) % bArr.length;
        }
    }
}
