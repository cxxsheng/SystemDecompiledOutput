package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface Wrapper {
    java.lang.String getAlgorithmName();

    void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);

    byte[] unwrap(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;

    byte[] wrap(byte[] bArr, int i, int i2);
}
