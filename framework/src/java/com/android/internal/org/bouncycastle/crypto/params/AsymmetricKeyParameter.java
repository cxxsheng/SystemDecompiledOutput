package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class AsymmetricKeyParameter implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    boolean privateKey;

    public AsymmetricKeyParameter(boolean z) {
        this.privateKey = z;
    }

    public boolean isPrivate() {
        return this.privateKey;
    }
}
