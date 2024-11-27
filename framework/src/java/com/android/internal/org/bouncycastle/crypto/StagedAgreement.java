package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public interface StagedAgreement extends com.android.internal.org.bouncycastle.crypto.BasicAgreement {
    com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter calculateStage(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters);
}
