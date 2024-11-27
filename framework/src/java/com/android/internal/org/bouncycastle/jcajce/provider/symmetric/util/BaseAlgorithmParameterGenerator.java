package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseAlgorithmParameterGenerator extends java.security.AlgorithmParameterGeneratorSpi {
    protected java.security.SecureRandom random;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
    protected int strength = 1024;

    protected final java.security.AlgorithmParameters createParametersInstance(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return this.helper.createAlgorithmParameters(str);
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, java.security.SecureRandom secureRandom) {
        this.strength = i;
        this.random = secureRandom;
    }
}
