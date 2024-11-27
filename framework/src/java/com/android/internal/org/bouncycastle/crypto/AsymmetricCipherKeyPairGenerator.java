package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface AsymmetricCipherKeyPairGenerator {
    com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair();

    void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters);
}
