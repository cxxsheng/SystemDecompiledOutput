package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class KeySyncTask implements java.lang.Runnable {
    private static final int LENGTH_PREFIX_BYTES = 4;
    private static final java.lang.String LOCK_SCREEN_HASH_ALGORITHM = "SHA-256";
    private static final java.lang.String RECOVERY_KEY_ALGORITHM = "AES";
    private static final int RECOVERY_KEY_SIZE_BITS = 256;
    private static final int SALT_LENGTH_BYTES = 16;

    @com.android.internal.annotations.VisibleForTesting
    static final int SCRYPT_PARAM_N = 4096;

    @com.android.internal.annotations.VisibleForTesting
    static final int SCRYPT_PARAM_OUTLEN_BYTES = 32;

    @com.android.internal.annotations.VisibleForTesting
    static final int SCRYPT_PARAM_P = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int SCRYPT_PARAM_R = 8;
    private static final java.lang.String TAG = "KeySyncTask";
    private static final int TRUSTED_HARDWARE_MAX_ATTEMPTS = 10;
    private final byte[] mCredential;
    private final int mCredentialType;
    private final boolean mCredentialUpdated;
    private final com.android.server.locksettings.recoverablekeystore.PlatformKeyManager mPlatformKeyManager;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb mRecoverableKeyStoreDb;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage mRecoverySnapshotStorage;
    private final android.security.Scrypt mScrypt;
    private final com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage mSnapshotListenersStorage;
    private final com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper mTestOnlyInsecureCertificateHelper;
    private final int mUserId;

    public static com.android.server.locksettings.recoverablekeystore.KeySyncTask newInstance(android.content.Context context, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb, com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage recoverySnapshotStorage, com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage recoverySnapshotListenersStorage, int i, int i2, byte[] bArr, boolean z) throws java.security.NoSuchAlgorithmException, java.security.KeyStoreException, com.android.server.locksettings.recoverablekeystore.InsecureUserException {
        return new com.android.server.locksettings.recoverablekeystore.KeySyncTask(recoverableKeyStoreDb, recoverySnapshotStorage, recoverySnapshotListenersStorage, i, i2, bArr, z, com.android.server.locksettings.recoverablekeystore.PlatformKeyManager.getInstance(context, recoverableKeyStoreDb), new com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper(), new android.security.Scrypt());
    }

    @com.android.internal.annotations.VisibleForTesting
    KeySyncTask(com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb, com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage recoverySnapshotStorage, com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage recoverySnapshotListenersStorage, int i, int i2, byte[] bArr, boolean z, com.android.server.locksettings.recoverablekeystore.PlatformKeyManager platformKeyManager, com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper testOnlyInsecureCertificateHelper, android.security.Scrypt scrypt) {
        this.mSnapshotListenersStorage = recoverySnapshotListenersStorage;
        this.mRecoverableKeyStoreDb = recoverableKeyStoreDb;
        this.mUserId = i;
        this.mCredentialType = i2;
        this.mCredential = bArr != null ? java.util.Arrays.copyOf(bArr, bArr.length) : null;
        this.mCredentialUpdated = z;
        this.mPlatformKeyManager = platformKeyManager;
        this.mRecoverySnapshotStorage = recoverySnapshotStorage;
        this.mTestOnlyInsecureCertificateHelper = testOnlyInsecureCertificateHelper;
        this.mScrypt = scrypt;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                synchronized (com.android.server.locksettings.recoverablekeystore.KeySyncTask.class) {
                    syncKeys();
                }
                if (this.mCredential != null) {
                    java.util.Arrays.fill(this.mCredential, (byte) 0);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Unexpected exception thrown during KeySyncTask", e);
                if (this.mCredential != null) {
                    java.util.Arrays.fill(this.mCredential, (byte) 0);
                }
            }
        } catch (java.lang.Throwable th) {
            if (this.mCredential != null) {
                java.util.Arrays.fill(this.mCredential, (byte) 0);
            }
            throw th;
        }
    }

    private void syncKeys() throws android.os.RemoteException {
        int generationId = this.mPlatformKeyManager.getGenerationId(this.mUserId);
        if (this.mCredentialType == -1) {
            android.util.Log.w(TAG, "Credentials are not set for user " + this.mUserId);
            if (generationId < 1001000) {
                this.mPlatformKeyManager.invalidatePlatformKey(this.mUserId, generationId);
                return;
            }
            return;
        }
        if (isCustomLockScreen()) {
            android.util.Log.w(TAG, "Unsupported credential type " + this.mCredentialType + " for user " + this.mUserId);
            if (generationId < 1001000) {
                this.mRecoverableKeyStoreDb.invalidateKeysForUserIdOnCustomScreenLock(this.mUserId);
                return;
            }
            return;
        }
        if (this.mPlatformKeyManager.isDeviceLocked(this.mUserId) && this.mUserId == 0) {
            android.util.Log.w(TAG, "Can't sync keys for locked user " + this.mUserId);
            return;
        }
        java.util.List<java.lang.Integer> recoveryAgents = this.mRecoverableKeyStoreDb.getRecoveryAgents(this.mUserId);
        java.util.Iterator<java.lang.Integer> it = recoveryAgents.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            try {
                syncKeysForAgent(intValue);
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "IOException during sync for agent " + intValue, e);
            }
        }
        if (recoveryAgents.isEmpty()) {
            android.util.Log.w(TAG, "No recovery agent initialized for user " + this.mUserId);
        }
    }

    private boolean isCustomLockScreen() {
        return (this.mCredentialType == -1 || this.mCredentialType == 1 || this.mCredentialType == 3 || this.mCredentialType == 4) ? false : true;
    }

    private void syncKeysForAgent(int i) throws java.io.IOException, android.os.RemoteException {
        boolean z;
        java.security.PublicKey recoveryServicePublicKey;
        byte[] hashCredentialsBySaltedSha256;
        java.lang.Long counterId;
        android.security.keystore.recovery.KeyDerivationParams createSha256Params;
        if (shouldCreateSnapshot(i)) {
            z = false;
        } else {
            z = this.mRecoverableKeyStoreDb.getSnapshotVersion(this.mUserId, i) != null && this.mRecoverySnapshotStorage.get(i) == null;
            if (z) {
                android.util.Log.d(TAG, "Recreating most recent snapshot");
            } else {
                android.util.Log.d(TAG, "Key sync not needed.");
                return;
            }
        }
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(this.mRecoverableKeyStoreDb.getActiveRootOfTrust(this.mUserId, i));
        java.security.cert.CertPath recoveryServiceCertPath = this.mRecoverableKeyStoreDb.getRecoveryServiceCertPath(this.mUserId, i, defaultCertificateAliasIfEmpty);
        if (recoveryServiceCertPath != null) {
            android.util.Log.d(TAG, "Using the public key in stored CertPath for syncing");
            recoveryServicePublicKey = recoveryServiceCertPath.getCertificates().get(0).getPublicKey();
        } else {
            android.util.Log.d(TAG, "Using the stored raw public key for syncing");
            recoveryServicePublicKey = this.mRecoverableKeyStoreDb.getRecoveryServicePublicKey(this.mUserId, i);
        }
        if (recoveryServicePublicKey != null) {
            byte[] serverParams = this.mRecoverableKeyStoreDb.getServerParams(this.mUserId, i);
            if (serverParams == null) {
                android.util.Log.w(TAG, "No device ID set for user " + this.mUserId);
                return;
            }
            if (this.mTestOnlyInsecureCertificateHelper.isTestOnlyCertificateAlias(defaultCertificateAliasIfEmpty)) {
                android.util.Log.w(TAG, "Insecure root certificate is used by recovery agent " + i);
                if (this.mTestOnlyInsecureCertificateHelper.doesCredentialSupportInsecureMode(this.mCredentialType, this.mCredential)) {
                    android.util.Log.w(TAG, "Whitelisted credential is used to generate snapshot by recovery agent " + i);
                } else {
                    android.util.Log.w(TAG, "Non whitelisted credential is used to generate recovery snapshot by " + i + " - ignore attempt.");
                    return;
                }
            }
            boolean shouldUseScryptToHashCredential = shouldUseScryptToHashCredential();
            byte[] generateSalt = generateSalt();
            if (shouldUseScryptToHashCredential) {
                hashCredentialsBySaltedSha256 = hashCredentialsByScrypt(generateSalt, this.mCredential);
            } else {
                hashCredentialsBySaltedSha256 = hashCredentialsBySaltedSha256(generateSalt, this.mCredential);
            }
            try {
                java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> keysToSync = getKeysToSync(i);
                if (this.mTestOnlyInsecureCertificateHelper.isTestOnlyCertificateAlias(defaultCertificateAliasIfEmpty)) {
                    keysToSync = this.mTestOnlyInsecureCertificateHelper.keepOnlyWhitelistedInsecureKeys(keysToSync);
                }
                try {
                    javax.crypto.SecretKey generateRecoveryKey = generateRecoveryKey();
                    try {
                        java.util.Map<java.lang.String, byte[]> encryptKeysWithRecoveryKey = com.android.server.locksettings.recoverablekeystore.KeySyncUtils.encryptKeysWithRecoveryKey(generateRecoveryKey, keysToSync);
                        if (!this.mCredentialUpdated) {
                            counterId = this.mRecoverableKeyStoreDb.getCounterId(this.mUserId, i);
                            if (counterId == null) {
                                counterId = java.lang.Long.valueOf(generateAndStoreCounterId(i));
                            }
                        } else {
                            counterId = java.lang.Long.valueOf(generateAndStoreCounterId(i));
                        }
                        try {
                            byte[] thmEncryptRecoveryKey = com.android.server.locksettings.recoverablekeystore.KeySyncUtils.thmEncryptRecoveryKey(recoveryServicePublicKey, hashCredentialsBySaltedSha256, com.android.server.locksettings.recoverablekeystore.KeySyncUtils.packVaultParams(recoveryServicePublicKey, counterId.longValue(), 10, serverParams), generateRecoveryKey);
                            if (shouldUseScryptToHashCredential) {
                                createSha256Params = android.security.keystore.recovery.KeyDerivationParams.createScryptParams(generateSalt, 4096);
                            } else {
                                createSha256Params = android.security.keystore.recovery.KeyDerivationParams.createSha256Params(generateSalt);
                            }
                            android.security.keystore.recovery.KeyChainProtectionParams build = new android.security.keystore.recovery.KeyChainProtectionParams.Builder().setUserSecretType(100).setLockScreenUiFormat(getUiFormat(this.mCredentialType)).setKeyDerivationParams(createSha256Params).setSecret(new byte[0]).build();
                            java.util.ArrayList arrayList = new java.util.ArrayList();
                            arrayList.add(build);
                            android.security.keystore.recovery.KeyChainSnapshot.Builder encryptedRecoveryKeyBlob = new android.security.keystore.recovery.KeyChainSnapshot.Builder().setSnapshotVersion(getSnapshotVersion(i, z)).setMaxAttempts(10).setCounterId(counterId.longValue()).setServerParams(serverParams).setKeyChainProtectionParams(arrayList).setWrappedApplicationKeys(createApplicationKeyEntries(encryptKeysWithRecoveryKey, keysToSync)).setEncryptedRecoveryKeyBlob(thmEncryptRecoveryKey);
                            try {
                                encryptedRecoveryKeyBlob.setTrustedHardwareCertPath(recoveryServiceCertPath);
                                this.mRecoverySnapshotStorage.put(i, encryptedRecoveryKeyBlob.build());
                                this.mSnapshotListenersStorage.recoverySnapshotAvailable(i);
                                this.mRecoverableKeyStoreDb.setShouldCreateSnapshot(this.mUserId, i, false);
                                return;
                            } catch (java.security.cert.CertificateException e) {
                                android.util.Log.wtf(TAG, "Cannot serialize CertPath when calling setTrustedHardwareCertPath", e);
                                return;
                            }
                        } catch (java.security.InvalidKeyException e2) {
                            android.util.Log.e(TAG, "Could not encrypt with recovery key", e2);
                            return;
                        } catch (java.security.NoSuchAlgorithmException e3) {
                            android.util.Log.wtf(TAG, "SecureBox encrypt algorithms unavailable", e3);
                            return;
                        }
                    } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e4) {
                        android.util.Log.wtf(TAG, "Should be impossible: could not encrypt application keys with random key", e4);
                        return;
                    }
                } catch (java.security.NoSuchAlgorithmException e5) {
                    android.util.Log.wtf("AES should never be unavailable", e5);
                    return;
                }
            } catch (com.android.server.locksettings.recoverablekeystore.BadPlatformKeyException e6) {
                android.util.Log.e(TAG, "Loaded keys for same generation ID as platform key, so BadPlatformKeyException should be impossible.", e6);
                return;
            } catch (com.android.server.locksettings.recoverablekeystore.InsecureUserException e7) {
                android.util.Log.e(TAG, "A screen unlock triggered the key sync flow, so user must have lock screen. This should be impossible.", e7);
                return;
            } catch (java.io.IOException e8) {
                android.util.Log.e(TAG, "Local database error.", e8);
                return;
            } catch (java.security.GeneralSecurityException e9) {
                android.util.Log.e(TAG, "Failed to load recoverable keys for sync", e9);
                return;
            }
        }
        android.util.Log.w(TAG, "Not initialized for KeySync: no public key set. Cancelling task.");
    }

    @com.android.internal.annotations.VisibleForTesting
    int getSnapshotVersion(int i, boolean z) throws java.io.IOException {
        java.lang.Long valueOf;
        java.lang.Long snapshotVersion = this.mRecoverableKeyStoreDb.getSnapshotVersion(this.mUserId, i);
        if (!z) {
            valueOf = java.lang.Long.valueOf(snapshotVersion != null ? 1 + snapshotVersion.longValue() : 1L);
        } else {
            valueOf = java.lang.Long.valueOf(snapshotVersion != null ? snapshotVersion.longValue() : 1L);
        }
        if (this.mRecoverableKeyStoreDb.setSnapshotVersion(this.mUserId, i, valueOf.longValue()) < 0) {
            android.util.Log.e(TAG, "Failed to set the snapshot version in the local DB.");
            throw new java.io.IOException("Failed to set the snapshot version in the local DB.");
        }
        return valueOf.intValue();
    }

    private long generateAndStoreCounterId(int i) throws java.io.IOException {
        long nextLong = new java.security.SecureRandom().nextLong();
        if (this.mRecoverableKeyStoreDb.setCounterId(this.mUserId, i, nextLong) < 0) {
            android.util.Log.e(TAG, "Failed to set the snapshot version in the local DB.");
            throw new java.io.IOException("Failed to set counterId in the local DB.");
        }
        return nextLong;
    }

    private java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> getKeysToSync(int i) throws com.android.server.locksettings.recoverablekeystore.InsecureUserException, java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, com.android.server.locksettings.recoverablekeystore.BadPlatformKeyException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, java.io.IOException, android.os.RemoteException {
        com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey decryptKey = this.mPlatformKeyManager.getDecryptKey(this.mUserId);
        return com.android.server.locksettings.recoverablekeystore.WrappedKey.unwrapKeys(decryptKey, this.mRecoverableKeyStoreDb.getAllKeys(this.mUserId, i, decryptKey.getGenerationId()));
    }

    private boolean shouldCreateSnapshot(int i) {
        if (!com.android.internal.util.ArrayUtils.contains(this.mRecoverableKeyStoreDb.getRecoverySecretTypes(this.mUserId, i), 100)) {
            return false;
        }
        if (this.mCredentialUpdated && this.mRecoverableKeyStoreDb.getSnapshotVersion(this.mUserId, i) != null) {
            this.mRecoverableKeyStoreDb.setShouldCreateSnapshot(this.mUserId, i, true);
            return true;
        }
        return this.mRecoverableKeyStoreDb.getShouldCreateSnapshot(this.mUserId, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getUiFormat(int i) {
        if (i == 1) {
            return 3;
        }
        return i == 3 ? 1 : 2;
    }

    private static byte[] generateSalt() {
        byte[] bArr = new byte[16];
        new java.security.SecureRandom().nextBytes(bArr);
        return bArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    static byte[] hashCredentialsBySaltedSha256(byte[] bArr, byte[] bArr2) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bArr.length + bArr2.length + 8);
        allocate.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        allocate.putInt(bArr.length);
        allocate.put(bArr);
        allocate.putInt(bArr2.length);
        allocate.put(bArr2);
        byte[] array = allocate.array();
        try {
            byte[] digest = java.security.MessageDigest.getInstance(LOCK_SCREEN_HASH_ALGORITHM).digest(array);
            java.util.Arrays.fill(array, (byte) 0);
            return digest;
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private byte[] hashCredentialsByScrypt(byte[] bArr, byte[] bArr2) {
        return this.mScrypt.scrypt(bArr2, bArr, 4096, 8, 1, 32);
    }

    private static javax.crypto.SecretKey generateRecoveryKey() throws java.security.NoSuchAlgorithmException {
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(RECOVERY_KEY_ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private static java.util.List<android.security.keystore.recovery.WrappedApplicationKey> createApplicationKeyEntries(java.util.Map<java.lang.String, byte[]> map, java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> map2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : map.keySet()) {
            arrayList.add(new android.security.keystore.recovery.WrappedApplicationKey.Builder().setAlias(str).setEncryptedKeyMaterial(map.get(str)).setMetadata((byte[]) map2.get(str).second).build());
        }
        return arrayList;
    }

    private boolean shouldUseScryptToHashCredential() {
        return this.mCredentialType == 4 || this.mCredentialType == 3;
    }
}
