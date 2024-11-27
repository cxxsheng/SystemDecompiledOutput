package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface DSA {
    java.math.BigInteger[] generateSignature(byte[] bArr);

    void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);

    boolean verifySignature(byte[] bArr, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2);
}
