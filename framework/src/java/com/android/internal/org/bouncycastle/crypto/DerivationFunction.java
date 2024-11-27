package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface DerivationFunction {
    int generateBytes(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.DataLengthException, java.lang.IllegalArgumentException;

    void init(com.android.internal.org.bouncycastle.crypto.DerivationParameters derivationParameters);
}
