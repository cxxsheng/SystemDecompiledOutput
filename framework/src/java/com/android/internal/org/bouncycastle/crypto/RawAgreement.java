package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface RawAgreement {
    void calculateAgreement(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, byte[] bArr, int i);

    int getAgreementSize();

    void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);
}
