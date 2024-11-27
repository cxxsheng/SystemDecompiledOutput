package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreProvider extends java.security.Provider {
    private static final java.lang.String DESEDE_SYSTEM_PROPERTY = "ro.hardware.keystore_desede";
    private static final java.lang.String ED25519_OID = "1.3.101.112";
    private static final java.lang.String PACKAGE_NAME = "android.security.keystore2";
    private static final java.lang.String PROVIDER_NAME = "AndroidKeyStore";
    private static final java.lang.String X25519_ALIAS = "XDH";

    public AndroidKeyStoreProvider() {
        super("AndroidKeyStore", 1.0d, "Android KeyStore security provider");
        boolean equals = "true".equals(android.os.SystemProperties.get(DESEDE_SYSTEM_PROPERTY));
        put("KeyStore.AndroidKeyStore", "android.security.keystore2.AndroidKeyStoreSpi");
        put("KeyPairGenerator.EC", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$EC");
        put("KeyPairGenerator.RSA", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$RSA");
        put("KeyPairGenerator.XDH", "android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$XDH");
        putKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC);
        putKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        putKeyFactoryImpl("XDH");
        put("KeyGenerator.AES", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$AES");
        put("KeyGenerator.HmacSHA1", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA1");
        put("KeyGenerator.HmacSHA224", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA224");
        put("KeyGenerator.HmacSHA256", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA256");
        put("KeyGenerator.HmacSHA384", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA384");
        put("KeyGenerator.HmacSHA512", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$HmacSHA512");
        if (equals) {
            put("KeyGenerator.DESede", "android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$DESede");
        }
        put("KeyAgreement.ECDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$ECDH");
        put("KeyAgreement.XDH", "android.security.keystore2.AndroidKeyStoreKeyAgreementSpi$XDH");
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        if (equals) {
            putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        }
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA1);
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA224);
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA384);
        putSecretKeyFactoryImpl(android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA512);
    }

    public static void install() {
        java.security.Provider[] providers = java.security.Security.getProviders();
        int i = 0;
        while (true) {
            if (i >= providers.length) {
                i = -1;
                break;
            } else if (com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME.equals(providers[i].getName())) {
                break;
            } else {
                i++;
            }
        }
        java.security.Security.addProvider(new android.security.keystore2.AndroidKeyStoreProvider());
        android.security.keystore2.AndroidKeyStoreBCWorkaroundProvider androidKeyStoreBCWorkaroundProvider = new android.security.keystore2.AndroidKeyStoreBCWorkaroundProvider();
        if (i != -1) {
            java.security.Security.insertProviderAt(androidKeyStoreBCWorkaroundProvider, i + 1);
        } else {
            java.security.Security.addProvider(androidKeyStoreBCWorkaroundProvider);
        }
    }

    private void putSecretKeyFactoryImpl(java.lang.String str) {
        put("SecretKeyFactory." + str, "android.security.keystore2.AndroidKeyStoreSecretKeyFactorySpi");
    }

    private void putKeyFactoryImpl(java.lang.String str) {
        put("KeyFactory." + str, "android.security.keystore2.AndroidKeyStoreKeyFactorySpi");
    }

    public static long getKeyStoreOperationHandle(java.lang.Object obj) {
        java.lang.Object currentSpi;
        if (obj == null) {
            throw new java.lang.NullPointerException();
        }
        if (obj instanceof java.security.Signature) {
            currentSpi = ((java.security.Signature) obj).getCurrentSpi();
        } else if (obj instanceof javax.crypto.Mac) {
            currentSpi = ((javax.crypto.Mac) obj).getCurrentSpi();
        } else if (obj instanceof javax.crypto.Cipher) {
            currentSpi = ((javax.crypto.Cipher) obj).getCurrentSpi();
        } else if (obj instanceof javax.crypto.KeyAgreement) {
            currentSpi = ((javax.crypto.KeyAgreement) obj).getCurrentSpi();
        } else {
            throw new java.lang.IllegalArgumentException("Unsupported crypto primitive: " + obj + ". Supported: Signature, Mac, Cipher");
        }
        if (currentSpi == null) {
            throw new java.lang.IllegalStateException("Crypto primitive not initialized");
        }
        if (!(currentSpi instanceof android.security.keystore.KeyStoreCryptoOperation)) {
            throw new java.lang.IllegalArgumentException("Crypto primitive not backed by AndroidKeyStore provider: " + obj + ", spi: " + currentSpi);
        }
        return ((android.security.keystore.KeyStoreCryptoOperation) currentSpi).getOperationHandle();
    }

    static android.security.keystore2.AndroidKeyStorePublicKey makeAndroidKeyStorePublicKeyFromKeyEntryResponse(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, int i) throws java.security.UnrecoverableKeyException {
        if (keyMetadata.certificate == null) {
            throw new java.security.UnrecoverableKeyException("Failed to obtain X.509 form of public key. Keystore has no public certificate stored.");
        }
        java.security.cert.X509Certificate certificate = android.security.keystore2.AndroidKeyStoreSpi.toCertificate(keyMetadata.certificate);
        if (certificate == null) {
            throw new java.security.UnrecoverableKeyException("Failed to parse the X.509 certificate containing the public key. This likely indicates a hardware problem.");
        }
        java.security.PublicKey publicKey = certificate.getPublicKey();
        java.lang.String algorithm = publicKey.getAlgorithm();
        if (android.security.keystore.KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
            return new android.security.keystore2.AndroidKeyStoreECPublicKey(keyDescriptor, keyMetadata, keyStoreSecurityLevel, (java.security.interfaces.ECPublicKey) publicKey);
        }
        if (android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
            return new android.security.keystore2.AndroidKeyStoreRSAPublicKey(keyDescriptor, keyMetadata, keyStoreSecurityLevel, (java.security.interfaces.RSAPublicKey) publicKey);
        }
        if (ED25519_OID.equalsIgnoreCase(algorithm)) {
            return new android.security.keystore2.AndroidKeyStoreEdECPublicKey(keyDescriptor, keyMetadata, ED25519_OID, keyStoreSecurityLevel, publicKey.getEncoded());
        }
        if ("XDH".equalsIgnoreCase(algorithm)) {
            return new android.security.keystore2.AndroidKeyStoreXDHPublicKey(keyDescriptor, keyMetadata, "XDH", keyStoreSecurityLevel, publicKey.getEncoded());
        }
        throw new java.security.ProviderException("Unsupported Android Keystore public key algorithm: " + algorithm);
    }

    public static android.security.keystore2.AndroidKeyStorePublicKey loadAndroidKeyStorePublicKeyFromKeystore(android.security.KeyStore2 keyStore2, java.lang.String str, int i) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, str, i);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof android.security.keystore2.AndroidKeyStorePublicKey) {
            return (android.security.keystore2.AndroidKeyStorePublicKey) loadAndroidKeyStoreKeyFromKeystore;
        }
        throw new java.security.UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static java.security.KeyPair loadAndroidKeyStoreKeyPairFromKeystore(android.security.KeyStore2 keyStore2, android.system.keystore2.KeyDescriptor keyDescriptor) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof android.security.keystore2.AndroidKeyStorePublicKey) {
            android.security.keystore2.AndroidKeyStorePublicKey androidKeyStorePublicKey = (android.security.keystore2.AndroidKeyStorePublicKey) loadAndroidKeyStoreKeyFromKeystore;
            return new java.security.KeyPair(androidKeyStorePublicKey, androidKeyStorePublicKey.getPrivateKey());
        }
        throw new java.security.UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static android.security.keystore2.AndroidKeyStorePrivateKey loadAndroidKeyStorePrivateKeyFromKeystore(android.security.KeyStore2 keyStore2, java.lang.String str, int i) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, str, i);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof android.security.keystore2.AndroidKeyStorePublicKey) {
            return ((android.security.keystore2.AndroidKeyStorePublicKey) loadAndroidKeyStoreKeyFromKeystore).getPrivateKey();
        }
        throw new java.security.UnrecoverableKeyException("No asymmetric key found by the given alias.");
    }

    public static javax.crypto.SecretKey loadAndroidKeyStoreSecretKeyFromKeystore(android.security.KeyStore2 keyStore2, android.system.keystore2.KeyDescriptor keyDescriptor) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        java.security.Key loadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof javax.crypto.SecretKey) {
            return (javax.crypto.SecretKey) loadAndroidKeyStoreKeyFromKeystore;
        }
        throw new java.security.UnrecoverableKeyException("No secret key found by the given alias.");
    }

    private static android.security.keystore2.AndroidKeyStoreSecretKey makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyEntryResponse keyEntryResponse, int i, int i2) throws java.security.UnrecoverableKeyException {
        try {
            return new android.security.keystore2.AndroidKeyStoreSecretKey(keyDescriptor, keyEntryResponse.metadata, android.security.keystore.KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(i, i2), new android.security.KeyStoreSecurityLevel(keyEntryResponse.iSecurityLevel));
        } catch (java.lang.IllegalArgumentException e) {
            throw ((java.security.UnrecoverableKeyException) new java.security.UnrecoverableKeyException("Unsupported secret key type").initCause(e));
        }
    }

    public static android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(android.security.KeyStore2 keyStore2, java.lang.String str, int i) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        if (i == -1) {
            keyDescriptor.nspace = -1L;
            keyDescriptor.domain = 0;
        } else {
            keyDescriptor.nspace = i;
            keyDescriptor.domain = 2;
        }
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore = loadAndroidKeyStoreKeyFromKeystore(keyStore2, keyDescriptor);
        if (loadAndroidKeyStoreKeyFromKeystore instanceof android.security.keystore2.AndroidKeyStorePublicKey) {
            return ((android.security.keystore2.AndroidKeyStorePublicKey) loadAndroidKeyStoreKeyFromKeystore).getPrivateKey();
        }
        return loadAndroidKeyStoreKeyFromKeystore;
    }

    private static android.security.keystore2.AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(android.security.KeyStore2 keyStore2, android.system.keystore2.KeyDescriptor keyDescriptor) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        java.lang.Integer num = null;
        try {
            android.system.keystore2.KeyEntryResponse keyEntry = keyStore2.getKeyEntry(keyDescriptor);
            if (keyEntry.iSecurityLevel == null) {
                return null;
            }
            int i = -1;
            for (android.system.keystore2.Authorization authorization : keyEntry.metadata.authorizations) {
                switch (authorization.keyParameter.tag) {
                    case 268435458:
                        num = java.lang.Integer.valueOf(authorization.keyParameter.value.getAlgorithm());
                        break;
                    case 536870917:
                        if (i == -1) {
                            i = authorization.keyParameter.value.getDigest();
                            break;
                        } else {
                            break;
                        }
                }
            }
            if (num == null) {
                throw new java.security.UnrecoverableKeyException("Key algorithm unknown");
            }
            if (num.intValue() == 128 || num.intValue() == 32 || num.intValue() == 33) {
                return makeAndroidKeyStoreSecretKeyFromKeyEntryResponse(keyDescriptor, keyEntry, num.intValue(), i);
            }
            if (num.intValue() == 1 || num.intValue() == 3) {
                return makeAndroidKeyStorePublicKeyFromKeyEntryResponse(keyDescriptor, keyEntry.metadata, new android.security.KeyStoreSecurityLevel(keyEntry.iSecurityLevel), num.intValue());
            }
            throw new java.security.UnrecoverableKeyException("Key algorithm unknown");
        } catch (android.security.KeyStoreException e) {
            switch (e.getErrorCode()) {
                case 7:
                    return null;
                case 17:
                    throw new android.security.keystore.KeyPermanentlyInvalidatedException("User changed or deleted their auth credentials", e);
                default:
                    throw ((java.security.UnrecoverableKeyException) new java.security.UnrecoverableKeyException("Failed to obtain information about key").initCause(e));
            }
        }
    }
}
