package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class PlatformEncryptionKey {
    private final int mGenerationId;
    private final javax.crypto.SecretKey mKey;

    public PlatformEncryptionKey(int i, javax.crypto.SecretKey secretKey) {
        this.mGenerationId = i;
        this.mKey = secretKey;
    }

    public int getGenerationId() {
        return this.mGenerationId;
    }

    public javax.crypto.SecretKey getKey() {
        return this.mKey;
    }
}
