package com.android.server.companion.securechannel;

/* loaded from: classes.dex */
final class KeyStoreUtils {
    private static final java.lang.String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final java.lang.String TAG = "CDM_SecureChannelKeyStore";

    private KeyStoreUtils() {
    }

    static java.security.KeyStore loadKeyStore() throws java.security.GeneralSecurityException {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
        try {
            keyStore.load(null);
            return keyStore;
        } catch (java.io.IOException e) {
            throw new java.security.KeyStoreException("Failed to load Android Keystore.", e);
        }
    }

    static byte[] getEncodedCertificateChain(java.lang.String str) throws java.security.GeneralSecurityException {
        java.security.cert.Certificate[] certificateChain = loadKeyStore().getCertificateChain(str);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        for (java.security.cert.Certificate certificate : certificateChain) {
            byteArrayOutputStream.writeBytes(certificate.getEncoded());
        }
        return byteArrayOutputStream.toByteArray();
    }

    static void generateAttestationKeyPair(java.lang.String str, byte[] bArr) throws java.security.GeneralSecurityException {
        android.security.keystore.KeyGenParameterSpec build = new android.security.keystore.KeyGenParameterSpec.Builder(str, 12).setAttestationChallenge(bArr).setDigests("SHA-256").build();
        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
        keyPairGenerator.initialize(build);
        keyPairGenerator.generateKeyPair();
    }

    static boolean aliasExists(java.lang.String str) {
        try {
            return loadKeyStore().containsAlias(str);
        } catch (java.security.GeneralSecurityException e) {
            return false;
        }
    }

    static void cleanUp(java.lang.String str) {
        try {
            java.security.KeyStore loadKeyStore = loadKeyStore();
            if (loadKeyStore.containsAlias(str)) {
                loadKeyStore.deleteEntry(str);
            }
        } catch (java.lang.Exception e) {
        }
    }
}
