package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public interface DSAKCalculator {
    void init(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr);

    void init(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom);

    boolean isDeterministic();

    java.math.BigInteger nextK();
}
