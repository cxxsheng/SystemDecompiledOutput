package com.android.internal.org.bouncycastle.crypto.modes;

/* loaded from: classes4.dex */
public interface AEADCipher {
    int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;

    java.lang.String getAlgorithmName();

    byte[] getMac();

    int getOutputSize(int i);

    int getUpdateOutputSize(int i);

    void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException;

    void processAADByte(byte b);

    void processAADBytes(byte[] bArr, int i, int i2);

    int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException;

    int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException;

    void reset();
}
