package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface SignerWithRecovery extends com.android.internal.org.bouncycastle.crypto.Signer {
    byte[] getRecoveredMessage();

    boolean hasFullMessage();

    void updateWithRecoveredMessage(byte[] bArr) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
}
