package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface BasicAgreement {
    java.math.BigInteger calculateAgreement(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);

    int getFieldSize();

    void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);
}
