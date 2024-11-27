package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class PlatformKeyManager {
    private static final java.lang.String DECRYPT_KEY_ALIAS_SUFFIX = "decrypt";
    private static final java.lang.String ENCRYPT_KEY_ALIAS_SUFFIX = "encrypt";
    private static final byte[] GCM_INSECURE_NONCE_BYTES = new byte[12];
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final java.lang.String KEY_ALGORITHM = "AES";
    private static final java.lang.String KEY_ALIAS_PREFIX = "com.android.server.locksettings.recoverablekeystore/platform/";
    private static final int KEY_SIZE_BITS = 256;
    private static final java.lang.String KEY_WRAP_CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    static final int MIN_GENERATION_ID_FOR_UNLOCKED_DEVICE_REQUIRED = 1001000;
    private static final java.lang.String TAG = "PlatformKeyManager";
    private final android.content.Context mContext;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb mDatabase;
    private final com.android.server.locksettings.recoverablekeystore.KeyStoreProxy mKeyStore;

    public static com.android.server.locksettings.recoverablekeystore.PlatformKeyManager getInstance(android.content.Context context, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb) throws java.security.KeyStoreException, java.security.NoSuchAlgorithmException {
        return new com.android.server.locksettings.recoverablekeystore.PlatformKeyManager(context.getApplicationContext(), new com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl(getAndLoadAndroidKeyStore()), recoverableKeyStoreDb);
    }

    @com.android.internal.annotations.VisibleForTesting
    PlatformKeyManager(android.content.Context context, com.android.server.locksettings.recoverablekeystore.KeyStoreProxy keyStoreProxy, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb) {
        this.mKeyStore = keyStoreProxy;
        this.mContext = context;
        this.mDatabase = recoverableKeyStoreDb;
    }

    public int getGenerationId(int i) {
        return this.mDatabase.getPlatformKeyGenerationId(i);
    }

    public boolean isDeviceLocked(int i) {
        return ((android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)).isDeviceLocked(i);
    }

    public void invalidatePlatformKey(int i, int i2) {
        if (i2 != -1) {
            try {
                this.mKeyStore.deleteEntry(getEncryptAlias(i, i2));
                this.mKeyStore.deleteEntry(getDecryptAlias(i, i2));
            } catch (java.security.KeyStoreException e) {
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void regenerate(int i) throws java.security.NoSuchAlgorithmException, java.security.KeyStoreException, java.io.IOException, android.os.RemoteException, com.android.server.locksettings.recoverablekeystore.InsecureUserException {
        int generationId = getGenerationId(i);
        int i2 = 1;
        if (generationId != -1) {
            invalidatePlatformKey(i, generationId);
            i2 = 1 + generationId;
        }
        generateAndLoadKey(i, i2);
    }

    public com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey getEncryptKey(int i) throws java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException, java.io.IOException, android.os.RemoteException, com.android.server.locksettings.recoverablekeystore.InsecureUserException {
        init(i);
        try {
            getDecryptKeyInternal(i);
            return getEncryptKeyInternal(i);
        } catch (java.security.UnrecoverableKeyException e) {
            android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Regenerating permanently invalid Platform key for user %d.", java.lang.Integer.valueOf(i)));
            regenerate(i);
            return getEncryptKeyInternal(i);
        }
    }

    private com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey getEncryptKeyInternal(int i) throws java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException {
        int generationId = getGenerationId(i);
        java.lang.String encryptAlias = getEncryptAlias(i, generationId);
        if (!isKeyLoaded(i, generationId)) {
            throw new java.security.UnrecoverableKeyException("KeyStore doesn't contain key " + encryptAlias);
        }
        return new com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey(generationId, (javax.crypto.SecretKey) this.mKeyStore.getKey(encryptAlias, null));
    }

    public com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey getDecryptKey(int i) throws java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException, java.io.IOException, com.android.server.locksettings.recoverablekeystore.InsecureUserException, android.os.RemoteException {
        init(i);
        try {
            com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey decryptKeyInternal = getDecryptKeyInternal(i);
            ensureDecryptionKeyIsValid(i, decryptKeyInternal);
            return decryptKeyInternal;
        } catch (java.security.UnrecoverableKeyException e) {
            android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Regenerating permanently invalid Platform key for user %d.", java.lang.Integer.valueOf(i)));
            regenerate(i);
            return getDecryptKeyInternal(i);
        }
    }

    private com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey getDecryptKeyInternal(int i) throws java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException {
        int generationId = getGenerationId(i);
        java.lang.String decryptAlias = getDecryptAlias(i, generationId);
        if (!isKeyLoaded(i, generationId)) {
            throw new java.security.UnrecoverableKeyException("KeyStore doesn't contain key " + decryptAlias);
        }
        return new com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey(generationId, (javax.crypto.SecretKey) this.mKeyStore.getKey(decryptAlias, null));
    }

    private void ensureDecryptionKeyIsValid(int i, com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey platformDecryptionKey) throws java.security.UnrecoverableKeyException {
        try {
            javax.crypto.Cipher.getInstance(KEY_WRAP_CIPHER_ALGORITHM).init(4, platformDecryptionKey.getKey(), new javax.crypto.spec.GCMParameterSpec(128, GCM_INSECURE_NONCE_BYTES));
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException e) {
            android.util.Log.e(TAG, java.lang.String.format(java.util.Locale.US, "The platform key for user %d became invalid.", java.lang.Integer.valueOf(i)));
            throw new java.security.UnrecoverableKeyException(e.getMessage());
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e2) {
        }
    }

    void init(int i) throws java.security.KeyStoreException, java.security.NoSuchAlgorithmException, java.io.IOException, android.os.RemoteException, com.android.server.locksettings.recoverablekeystore.InsecureUserException {
        int generationId = getGenerationId(i);
        if (isKeyLoaded(i, generationId)) {
            android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Platform key generation %d exists already.", java.lang.Integer.valueOf(generationId)));
            return;
        }
        int i2 = 1;
        if (generationId == -1) {
            android.util.Log.i(TAG, "Generating initial platform key generation ID.");
        } else {
            android.util.Log.w(TAG, java.lang.String.format(java.util.Locale.US, "Platform generation ID was %d but no entry was present in AndroidKeyStore. Generating fresh key.", java.lang.Integer.valueOf(generationId)));
            i2 = 1 + generationId;
        }
        generateAndLoadKey(i, java.lang.Math.max(i2, MIN_GENERATION_ID_FOR_UNLOCKED_DEVICE_REQUIRED));
    }

    private java.lang.String getEncryptAlias(int i, int i2) {
        return KEY_ALIAS_PREFIX + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + ENCRYPT_KEY_ALIAS_SUFFIX;
    }

    private java.lang.String getDecryptAlias(int i, int i2) {
        return KEY_ALIAS_PREFIX + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + DECRYPT_KEY_ALIAS_SUFFIX;
    }

    private void setGenerationId(int i, int i2) throws java.io.IOException {
        this.mDatabase.setPlatformKeyGenerationId(i, i2);
    }

    private boolean isKeyLoaded(int i, int i2) throws java.security.KeyStoreException {
        return this.mKeyStore.containsAlias(getEncryptAlias(i, i2)) && this.mKeyStore.containsAlias(getDecryptAlias(i, i2));
    }

    @com.android.internal.annotations.VisibleForTesting
    android.service.gatekeeper.IGateKeeperService getGateKeeperService() {
        return android.security.GateKeeper.getService();
    }

    private void generateAndLoadKey(int i, int i2) throws java.security.NoSuchAlgorithmException, java.security.KeyStoreException, java.io.IOException, android.os.RemoteException, com.android.server.locksettings.recoverablekeystore.InsecureUserException {
        java.lang.String encryptAlias = getEncryptAlias(i, i2);
        java.lang.String decryptAlias = getDecryptAlias(i, i2);
        javax.crypto.SecretKey generateAesKey = generateAesKey();
        android.security.keystore.KeyProtection.Builder encryptionPaddings = new android.security.keystore.KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding");
        if (i == 0) {
            encryptionPaddings.setUnlockedDeviceRequired(true);
        }
        try {
            this.mKeyStore.setEntry(decryptAlias, new java.security.KeyStore.SecretKeyEntry(generateAesKey), encryptionPaddings.build());
            this.mKeyStore.setEntry(encryptAlias, new java.security.KeyStore.SecretKeyEntry(generateAesKey), new android.security.keystore.KeyProtection.Builder(1).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
            setGenerationId(i, i2);
        } catch (java.security.KeyStoreException e) {
            if (!isDeviceSecure(i)) {
                throw new com.android.server.locksettings.recoverablekeystore.InsecureUserException("Screenlock is not set");
            }
            throw e;
        }
    }

    private static javax.crypto.SecretKey generateAesKey() throws java.security.NoSuchAlgorithmException {
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private static java.security.KeyStore getAndLoadAndroidKeyStore() throws java.security.KeyStoreException {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance(com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl.ANDROID_KEY_STORE_PROVIDER);
        try {
            keyStore.load(null);
            return keyStore;
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            throw new java.security.KeyStoreException("Unable to load keystore.", e);
        }
    }

    private boolean isDeviceSecure(int i) {
        return ((android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)).isDeviceSecure(i);
    }
}
