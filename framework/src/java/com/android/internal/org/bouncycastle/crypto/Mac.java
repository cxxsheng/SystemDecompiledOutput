package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface Mac {
    int doFinal(byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException;

    java.lang.String getAlgorithmName();

    int getMacSize();

    void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException;

    void reset();

    void update(byte b) throws java.lang.IllegalStateException;

    void update(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalStateException;
}
