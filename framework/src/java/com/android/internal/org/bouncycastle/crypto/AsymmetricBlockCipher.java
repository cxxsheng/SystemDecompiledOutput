package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface AsymmetricBlockCipher {
    int getInputBlockSize();

    int getOutputBlockSize();

    void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);

    byte[] processBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
}
