package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public abstract class StreamBlockCipher implements com.android.internal.org.bouncycastle.crypto.BlockCipher, com.android.internal.org.bouncycastle.crypto.StreamCipher {
    private final com.android.internal.org.bouncycastle.crypto.BlockCipher cipher;

    protected abstract byte calculateByte(byte b);

    protected StreamBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this.cipher = blockCipher;
    }

    public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public final byte returnByte(byte b) {
        return calculateByte(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
        int i4 = i + i2;
        if (i4 > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too small");
        }
        if (i3 + i2 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        while (i < i4) {
            bArr2[i3] = calculateByte(bArr[i]);
            i3++;
            i++;
        }
        return i2;
    }
}
