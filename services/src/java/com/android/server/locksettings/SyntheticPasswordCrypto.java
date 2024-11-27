package com.android.server.locksettings;

/* loaded from: classes2.dex */
class SyntheticPasswordCrypto {
    private static final int AES_GCM_IV_SIZE = 12;
    private static final int AES_GCM_KEY_SIZE = 32;
    private static final int AES_GCM_TAG_SIZE = 16;
    private static final byte[] PROTECTOR_SECRET_PERSONALIZATION = "application-id".getBytes();
    private static final java.lang.String TAG = "SyntheticPasswordCrypto";
    private static final int USER_AUTHENTICATION_VALIDITY = 15;

    SyntheticPasswordCrypto() {
    }

    private static byte[] decrypt(javax.crypto.SecretKey secretKey, byte[] bArr) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (bArr == null) {
            return null;
        }
        byte[] copyOfRange = java.util.Arrays.copyOfRange(bArr, 0, 12);
        byte[] copyOfRange2 = java.util.Arrays.copyOfRange(bArr, 12, bArr.length);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new javax.crypto.spec.GCMParameterSpec(128, copyOfRange));
        return cipher.doFinal(copyOfRange2);
    }

    private static byte[] encrypt(javax.crypto.SecretKey secretKey, byte[] bArr) throws java.io.IOException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.spec.InvalidParameterSpecException {
        if (bArr == null) {
            return null;
        }
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, secretKey);
        byte[] doFinal = cipher.doFinal(bArr);
        byte[] iv = cipher.getIV();
        if (iv.length != 12) {
            throw new java.lang.IllegalArgumentException("Invalid iv length: " + iv.length + " bytes");
        }
        javax.crypto.spec.GCMParameterSpec gCMParameterSpec = (javax.crypto.spec.GCMParameterSpec) cipher.getParameters().getParameterSpec(javax.crypto.spec.GCMParameterSpec.class);
        if (gCMParameterSpec.getTLen() != 128) {
            throw new java.lang.IllegalArgumentException("Invalid tag length: " + gCMParameterSpec.getTLen() + " bits");
        }
        return com.android.internal.util.ArrayUtils.concat(new byte[][]{iv, doFinal});
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return encrypt(new javax.crypto.spec.SecretKeySpec(java.util.Arrays.copyOf(personalizedHash(bArr2, bArr), 32), "AES"), bArr3);
        } catch (java.io.IOException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.security.spec.InvalidParameterSpecException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            android.util.Slog.e(TAG, "Failed to encrypt", e);
            return null;
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return decrypt(new javax.crypto.spec.SecretKeySpec(java.util.Arrays.copyOf(personalizedHash(bArr2, bArr), 32), "AES"), bArr3);
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            android.util.Slog.e(TAG, "Failed to decrypt", e);
            return null;
        }
    }

    public static byte[] decryptBlobV1(java.lang.String str, byte[] bArr, byte[] bArr2) {
        try {
            javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) getKeyStore().getKey(str, null);
            if (secretKey == null) {
                throw new java.lang.IllegalStateException("SP protector key is missing: " + str);
            }
            return decrypt(secretKey, decrypt(bArr2, PROTECTOR_SECRET_PERSONALIZATION, bArr));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to decrypt V1 blob", e);
            throw new java.lang.IllegalStateException("Failed to decrypt blob", e);
        }
    }

    static java.lang.String androidKeystoreProviderName() {
        return com.android.server.locksettings.recoverablekeystore.KeyStoreProxyImpl.ANDROID_KEY_STORE_PROVIDER;
    }

    static int keyNamespace() {
        return 103;
    }

    private static java.security.KeyStore getKeyStore() throws java.security.KeyStoreException, java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.io.IOException {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance(androidKeystoreProviderName());
        keyStore.load(new android.security.keystore2.AndroidKeyStoreLoadStoreParameter(keyNamespace()));
        return keyStore;
    }

    public static byte[] decryptBlob(java.lang.String str, byte[] bArr, byte[] bArr2) {
        try {
            javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) getKeyStore().getKey(str, null);
            if (secretKey == null) {
                throw new java.lang.IllegalStateException("SP protector key is missing: " + str);
            }
            return decrypt(bArr2, PROTECTOR_SECRET_PERSONALIZATION, decrypt(secretKey, bArr));
        } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            android.util.Slog.e(TAG, "Failed to decrypt blob", e);
            throw new java.lang.IllegalStateException("Failed to decrypt blob", e);
        }
    }

    public static byte[] createBlob(java.lang.String str, byte[] bArr, byte[] bArr2, long j) {
        try {
            javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new java.security.SecureRandom());
            javax.crypto.SecretKey generateKey = keyGenerator.generateKey();
            java.security.KeyStore keyStore = getKeyStore();
            android.security.keystore.KeyProtection.Builder criticalToDeviceEncryption = new android.security.keystore.KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setCriticalToDeviceEncryption(true);
            if (j != 0) {
                criticalToDeviceEncryption.setUserAuthenticationRequired(true).setBoundToSpecificSecureUserId(j).setUserAuthenticationValidityDurationSeconds(15);
            }
            android.security.keystore.KeyProtection build = criticalToDeviceEncryption.build();
            criticalToDeviceEncryption.setRollbackResistant(true);
            android.security.keystore.KeyProtection build2 = criticalToDeviceEncryption.build();
            java.security.KeyStore.SecretKeyEntry secretKeyEntry = new java.security.KeyStore.SecretKeyEntry(generateKey);
            try {
                keyStore.setEntry(str, secretKeyEntry, build2);
                android.util.Slog.i(TAG, "Using rollback-resistant key");
            } catch (java.security.KeyStoreException e) {
                android.util.Slog.w(TAG, "Rollback-resistant keys unavailable.  Falling back to non-rollback-resistant key");
                keyStore.setEntry(str, secretKeyEntry, build);
            }
            return encrypt(generateKey, encrypt(bArr2, PROTECTOR_SECRET_PERSONALIZATION, bArr));
        } catch (java.io.IOException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException | java.security.spec.InvalidParameterSpecException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e2) {
            android.util.Slog.e(TAG, "Failed to create blob", e2);
            throw new java.lang.IllegalStateException("Failed to encrypt blob", e2);
        }
    }

    public static void destroyProtectorKey(java.lang.String str) {
        try {
            getKeyStore().deleteEntry(str);
            android.util.Slog.i(TAG, "Deleted SP protector key " + str);
        } catch (java.io.IOException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            android.util.Slog.e(TAG, "Failed to delete SP protector key " + str, e);
        }
    }

    protected static byte[] personalizedHash(byte[] bArr, byte[]... bArr2) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-512");
            if (bArr.length > 128) {
                throw new java.lang.IllegalArgumentException("Personalization too long");
            }
            messageDigest.update(java.util.Arrays.copyOf(bArr, 128));
            for (byte[] bArr3 : bArr2) {
                messageDigest.update(bArr3);
            }
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.IllegalStateException("NoSuchAlgorithmException for SHA-512", e);
        }
    }

    static boolean migrateLockSettingsKey(java.lang.String str) {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        android.system.keystore2.KeyDescriptor keyDescriptor2 = new android.system.keystore2.KeyDescriptor();
        keyDescriptor2.domain = 2;
        keyDescriptor2.nspace = keyNamespace();
        keyDescriptor2.alias = str;
        android.util.Slog.i(TAG, "Migrating key " + str);
        int migrateKeyNamespace = android.security.AndroidKeyStoreMaintenance.migrateKeyNamespace(keyDescriptor, keyDescriptor2);
        if (migrateKeyNamespace == 0) {
            return true;
        }
        if (migrateKeyNamespace == 7) {
            android.util.Slog.i(TAG, "Key does not exist");
            return true;
        }
        if (migrateKeyNamespace == 20) {
            android.util.Slog.i(TAG, "Key already exists");
            return true;
        }
        android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to migrate key: %d", new java.lang.Object[]{java.lang.Integer.valueOf(migrateKeyNamespace)}));
        return false;
    }
}
