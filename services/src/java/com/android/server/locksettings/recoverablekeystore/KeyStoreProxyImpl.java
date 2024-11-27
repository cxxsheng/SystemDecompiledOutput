package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class KeyStoreProxyImpl implements com.android.server.locksettings.recoverablekeystore.KeyStoreProxy {
    public static final java.lang.String ANDROID_KEY_STORE_PROVIDER = "AndroidKeyStore";
    private final java.security.KeyStore mKeyStore;

    public KeyStoreProxyImpl(java.security.KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
    public boolean containsAlias(java.lang.String str) throws java.security.KeyStoreException {
        return this.mKeyStore.containsAlias(str);
    }

    @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
    public java.security.Key getKey(java.lang.String str, char[] cArr) throws java.security.KeyStoreException, java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
        return this.mKeyStore.getKey(str, cArr);
    }

    @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
    public void setEntry(java.lang.String str, java.security.KeyStore.Entry entry, java.security.KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        this.mKeyStore.setEntry(str, entry, protectionParameter);
    }

    @Override // com.android.server.locksettings.recoverablekeystore.KeyStoreProxy
    public void deleteEntry(java.lang.String str) throws java.security.KeyStoreException {
        this.mKeyStore.deleteEntry(str);
    }

    public static java.security.KeyStore getAndLoadAndroidKeyStore() throws java.security.KeyStoreException {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance(ANDROID_KEY_STORE_PROVIDER);
        try {
            keyStore.load(null);
            return keyStore;
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            throw new java.security.KeyStoreException("Unable to load keystore.", e);
        }
    }
}
