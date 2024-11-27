package android.security.keystore;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class AndroidKeyStoreProvider extends java.security.Provider {
    private static final java.lang.String PROVIDER_NAME = "AndroidKeyStore";

    public AndroidKeyStoreProvider(java.lang.String str) {
        super(str, 1.0d, "Android KeyStore security provider");
        throw new java.lang.IllegalStateException("Should not be instantiated.");
    }

    public static long getKeyStoreOperationHandle(java.lang.Object obj) {
        return android.security.keystore2.AndroidKeyStoreProvider.getKeyStoreOperationHandle(obj);
    }

    @android.annotation.SystemApi
    public static java.security.KeyStore getKeyStoreForUid(int i) throws java.security.KeyStoreException, java.security.NoSuchProviderException {
        android.security.keystore2.AndroidKeyStoreLoadStoreParameter androidKeyStoreLoadStoreParameter = new android.security.keystore2.AndroidKeyStoreLoadStoreParameter(android.security.keystore.KeyProperties.legacyUidToNamespace(i));
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(androidKeyStoreLoadStoreParameter);
            return keyStore;
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            throw new java.security.KeyStoreException("Failed to load AndroidKeyStore KeyStore for UID " + i, e);
        }
    }
}
