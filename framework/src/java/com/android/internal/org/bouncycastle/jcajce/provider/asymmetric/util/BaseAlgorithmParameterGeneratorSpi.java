package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseAlgorithmParameterGeneratorSpi extends java.security.AlgorithmParameterGeneratorSpi {
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();

    protected final java.security.AlgorithmParameters createParametersInstance(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return this.helper.createAlgorithmParameters(str);
    }
}
