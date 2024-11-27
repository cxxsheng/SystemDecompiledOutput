package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public class KeyGenerationParameters {
    private java.security.SecureRandom random;
    private int strength;

    public KeyGenerationParameters(java.security.SecureRandom secureRandom, int i) {
        this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom);
        this.strength = i;
    }

    public java.security.SecureRandom getRandom() {
        return this.random;
    }

    public int getStrength() {
        return this.strength;
    }
}
