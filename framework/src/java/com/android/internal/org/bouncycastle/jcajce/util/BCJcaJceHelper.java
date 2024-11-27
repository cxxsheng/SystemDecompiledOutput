package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class BCJcaJceHelper extends com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper {
    private static volatile java.security.Provider bcProvider;

    private static synchronized java.security.Provider getBouncyCastleProvider() {
        synchronized (com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper.class) {
            java.security.Provider provider = java.security.Security.getProvider(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
            if (provider instanceof com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider) {
                return provider;
            }
            if (bcProvider != null) {
                return bcProvider;
            }
            bcProvider = new com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider();
            return bcProvider;
        }
    }

    public BCJcaJceHelper() {
        super(getBouncyCastleProvider());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Cipher createCipher(java.lang.String str) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException {
        try {
            return super.createCipher(str);
        } catch (java.security.NoSuchAlgorithmException e) {
            return javax.crypto.Cipher.getInstance(str, getPrivateProvider());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.SecretKeyFactory createSecretKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        try {
            return super.createSecretKeyFactory(str);
        } catch (java.security.NoSuchAlgorithmException e) {
            return javax.crypto.SecretKeyFactory.getInstance(str, getPrivateProvider());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.ProviderJcaJceHelper, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Mac createMac(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        try {
            return super.createMac(str);
        } catch (java.security.NoSuchAlgorithmException e) {
            return javax.crypto.Mac.getInstance(str, getPrivateProvider());
        }
    }

    private java.security.Provider getPrivateProvider() {
        if (this.provider instanceof com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider) {
            return ((com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider) this.provider).getPrivateProvider();
        }
        throw new java.lang.IllegalStateException("Internal error in BCJcaJceHelper");
    }
}
