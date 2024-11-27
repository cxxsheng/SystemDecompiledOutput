package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class RecoverableKeyStoreManager {
    private static final int INVALID_REMOTE_GUESS_LIMIT = 5;
    private static final long SYNC_DELAY_MILLIS = 2000;
    private static final java.lang.String TAG = "RecoverableKeyStoreMgr";
    private static com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager mInstance;
    private final com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage mApplicationKeyStorage;
    private final com.android.server.locksettings.recoverablekeystore.storage.CleanupManager mCleanupManager;
    private final android.content.Context mContext;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb mDatabase;
    private final java.util.concurrent.ScheduledExecutorService mExecutorService;
    private final com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage mListenersStorage;
    private final com.android.server.locksettings.recoverablekeystore.PlatformKeyManager mPlatformKeyManager;
    private final com.android.server.locksettings.recoverablekeystore.RecoverableKeyGenerator mRecoverableKeyGenerator;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage mRecoverySessionStorage;

    @android.annotation.Nullable
    private final com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage mRemoteLockscreenValidationSessionStorage;
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage mSnapshotStorage;
    private final com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper mTestCertHelper;

    public static synchronized com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager getInstance(android.content.Context context) {
        com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager recoverableKeyStoreManager;
        com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage remoteLockscreenValidationSessionStorage;
        synchronized (com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager.class) {
            if (mInstance == null) {
                com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb newInstance = com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb.newInstance(context);
                if (android.util.FeatureFlagUtils.isEnabled(context, "settings_enable_lockscreen_transfer_api")) {
                    remoteLockscreenValidationSessionStorage = new com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage();
                } else {
                    remoteLockscreenValidationSessionStorage = null;
                }
                try {
                    com.android.server.locksettings.recoverablekeystore.PlatformKeyManager platformKeyManager = com.android.server.locksettings.recoverablekeystore.PlatformKeyManager.getInstance(context, newInstance);
                    com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage applicationKeyStorage = com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage.getInstance();
                    com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage newInstance2 = com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage.newInstance();
                    mInstance = new com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager(context.getApplicationContext(), newInstance, new com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage(), java.util.concurrent.Executors.newScheduledThreadPool(1), newInstance2, new com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage(), platformKeyManager, applicationKeyStorage, new com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper(), com.android.server.locksettings.recoverablekeystore.storage.CleanupManager.getInstance(context.getApplicationContext(), newInstance2, newInstance, applicationKeyStorage), remoteLockscreenValidationSessionStorage);
                } catch (java.security.KeyStoreException e) {
                    throw new android.os.ServiceSpecificException(22, e.getMessage());
                } catch (java.security.NoSuchAlgorithmException e2) {
                    throw new java.lang.RuntimeException(e2);
                }
            }
            recoverableKeyStoreManager = mInstance;
        }
        return recoverableKeyStoreManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    RecoverableKeyStoreManager(android.content.Context context, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb, com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage recoverySessionStorage, java.util.concurrent.ScheduledExecutorService scheduledExecutorService, com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage recoverySnapshotStorage, com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage recoverySnapshotListenersStorage, com.android.server.locksettings.recoverablekeystore.PlatformKeyManager platformKeyManager, com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage applicationKeyStorage, com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper testOnlyInsecureCertificateHelper, com.android.server.locksettings.recoverablekeystore.storage.CleanupManager cleanupManager, com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage remoteLockscreenValidationSessionStorage) {
        this.mContext = context;
        this.mDatabase = recoverableKeyStoreDb;
        this.mRecoverySessionStorage = recoverySessionStorage;
        this.mExecutorService = scheduledExecutorService;
        this.mListenersStorage = recoverySnapshotListenersStorage;
        this.mSnapshotStorage = recoverySnapshotStorage;
        this.mPlatformKeyManager = platformKeyManager;
        this.mApplicationKeyStorage = applicationKeyStorage;
        this.mTestCertHelper = testOnlyInsecureCertificateHelper;
        this.mCleanupManager = cleanupManager;
        try {
            this.mCleanupManager.verifyKnownUsers();
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to verify known users", e);
        }
        try {
            this.mRecoverableKeyGenerator = com.android.server.locksettings.recoverablekeystore.RecoverableKeyGenerator.newInstance(this.mDatabase);
            this.mRemoteLockscreenValidationSessionStorage = remoteLockscreenValidationSessionStorage;
        } catch (java.security.NoSuchAlgorithmException e2) {
            android.util.Log.wtf(TAG, "AES keygen algorithm not available. AOSP must support this.", e2);
            throw new android.os.ServiceSpecificException(22, e2.getMessage());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void initRecoveryService(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestCertHelper.getDefaultCertificateAliasIfEmpty(str);
        if (!this.mTestCertHelper.isValidRootCertificateAlias(defaultCertificateAliasIfEmpty)) {
            throw new android.os.ServiceSpecificException(28, "Invalid root certificate alias");
        }
        java.lang.String activeRootOfTrust = this.mDatabase.getActiveRootOfTrust(callingUserId, callingUid);
        if (activeRootOfTrust == null) {
            android.util.Log.d(TAG, "Root of trust for recovery agent + " + callingUid + " is assigned for the first time to " + defaultCertificateAliasIfEmpty);
        } else if (!activeRootOfTrust.equals(defaultCertificateAliasIfEmpty)) {
            android.util.Log.i(TAG, "Root of trust for recovery agent " + callingUid + " is changed to " + defaultCertificateAliasIfEmpty + " from  " + activeRootOfTrust);
        }
        if (this.mDatabase.setActiveRootOfTrust(callingUserId, callingUid, defaultCertificateAliasIfEmpty) < 0) {
            throw new android.os.ServiceSpecificException(22, "Failed to set the root of trust in the local DB.");
        }
        try {
            com.android.server.locksettings.recoverablekeystore.certificate.CertXml parse = com.android.server.locksettings.recoverablekeystore.certificate.CertXml.parse(bArr);
            long serial = parse.getSerial();
            java.lang.Long recoveryServiceCertSerial = this.mDatabase.getRecoveryServiceCertSerial(callingUserId, callingUid, defaultCertificateAliasIfEmpty);
            if (recoveryServiceCertSerial != null && recoveryServiceCertSerial.longValue() >= serial && !this.mTestCertHelper.isTestOnlyCertificateAlias(defaultCertificateAliasIfEmpty)) {
                if (recoveryServiceCertSerial.longValue() == serial) {
                    android.util.Log.i(TAG, "The cert file serial number is the same, so skip updating.");
                    return;
                } else {
                    android.util.Log.e(TAG, "The cert file serial number is older than the one in database.");
                    throw new android.os.ServiceSpecificException(29, "The cert file serial number is older than the one in database.");
                }
            }
            android.util.Log.i(TAG, "Updating the certificate with the new serial number " + serial);
            java.security.cert.X509Certificate rootCertificate = this.mTestCertHelper.getRootCertificate(defaultCertificateAliasIfEmpty);
            java.util.Date validationDate = this.mTestCertHelper.getValidationDate(defaultCertificateAliasIfEmpty);
            try {
                android.util.Log.d(TAG, "Getting and validating a random endpoint certificate");
                java.security.cert.CertPath randomEndpointCert = parse.getRandomEndpointCert(rootCertificate, validationDate);
                try {
                    android.util.Log.d(TAG, "Saving the randomly chosen endpoint certificate to database");
                    long recoveryServiceCertPath = this.mDatabase.setRecoveryServiceCertPath(callingUserId, callingUid, defaultCertificateAliasIfEmpty, randomEndpointCert);
                    if (recoveryServiceCertPath > 0) {
                        if (this.mDatabase.setRecoveryServiceCertSerial(callingUserId, callingUid, defaultCertificateAliasIfEmpty, serial) < 0) {
                            throw new android.os.ServiceSpecificException(22, "Failed to set the certificate serial number in the local DB.");
                        }
                        if (this.mDatabase.getSnapshotVersion(callingUserId, callingUid) == null) {
                            android.util.Log.i(TAG, "This is a certificate change. Snapshot didn't exist");
                        } else {
                            this.mDatabase.setShouldCreateSnapshot(callingUserId, callingUid, true);
                            android.util.Log.i(TAG, "This is a certificate change. Snapshot must be updated");
                        }
                        if (this.mDatabase.setCounterId(callingUserId, callingUid, new java.security.SecureRandom().nextLong()) < 0) {
                            android.util.Log.e(TAG, "Failed to set the counter id in the local DB.");
                        }
                    } else if (recoveryServiceCertPath < 0) {
                        throw new android.os.ServiceSpecificException(22, "Failed to set the certificate path in the local DB.");
                    }
                } catch (java.security.cert.CertificateEncodingException e) {
                    android.util.Log.e(TAG, "Failed to encode CertPath", e);
                    throw new android.os.ServiceSpecificException(25, e.getMessage());
                }
            } catch (com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException e2) {
                android.util.Log.e(TAG, "Invalid endpoint cert", e2);
                throw new android.os.ServiceSpecificException(28, e2.getMessage());
            }
        } catch (com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException e3) {
            android.util.Log.d(TAG, "Failed to parse the input as a cert file: " + com.android.internal.util.HexDump.toHexString(bArr));
            throw new android.os.ServiceSpecificException(25, e3.getMessage());
        }
    }

    public void initRecoveryServiceWithSigFile(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestCertHelper.getDefaultCertificateAliasIfEmpty(str);
        java.util.Objects.requireNonNull(bArr, "recoveryServiceCertFile is null");
        java.util.Objects.requireNonNull(bArr2, "recoveryServiceSigFile is null");
        try {
            try {
                com.android.server.locksettings.recoverablekeystore.certificate.SigXml.parse(bArr2).verifyFileSignature(this.mTestCertHelper.getRootCertificate(defaultCertificateAliasIfEmpty), bArr, this.mTestCertHelper.getValidationDate(defaultCertificateAliasIfEmpty));
                initRecoveryService(defaultCertificateAliasIfEmpty, bArr);
            } catch (com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException e) {
                android.util.Log.e(TAG, "The signature over the cert file is invalid. Cert: " + com.android.internal.util.HexDump.toHexString(bArr) + " Sig: " + com.android.internal.util.HexDump.toHexString(bArr2));
                throw new android.os.ServiceSpecificException(28, e.getMessage());
            }
        } catch (com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException e2) {
            android.util.Log.d(TAG, "Failed to parse the sig file: " + com.android.internal.util.HexDump.toHexString(bArr2));
            throw new android.os.ServiceSpecificException(25, e2.getMessage());
        }
    }

    @android.annotation.NonNull
    public android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot = this.mSnapshotStorage.get(android.os.Binder.getCallingUid());
        if (keyChainSnapshot == null) {
            throw new android.os.ServiceSpecificException(21);
        }
        return keyChainSnapshot;
    }

    public void setSnapshotCreatedPendingIntent(@android.annotation.Nullable android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        this.mListenersStorage.setSnapshotListener(android.os.Binder.getCallingUid(), pendingIntent);
    }

    public void setServerParams(@android.annotation.NonNull byte[] bArr) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        byte[] serverParams = this.mDatabase.getServerParams(callingUserId, callingUid);
        if (java.util.Arrays.equals(bArr, serverParams)) {
            android.util.Log.v(TAG, "Not updating server params - same as old value.");
            return;
        }
        if (this.mDatabase.setServerParams(callingUserId, callingUid, bArr) < 0) {
            throw new android.os.ServiceSpecificException(22, "Database failure trying to set server params.");
        }
        if (serverParams == null) {
            android.util.Log.i(TAG, "Initialized server params.");
        } else if (this.mDatabase.getSnapshotVersion(callingUserId, callingUid) != null) {
            this.mDatabase.setShouldCreateSnapshot(callingUserId, callingUid, true);
            android.util.Log.i(TAG, "Updated server params. Snapshot must be updated");
        } else {
            android.util.Log.i(TAG, "Updated server params. Snapshot didn't exist");
        }
    }

    public void setRecoveryStatus(@android.annotation.NonNull java.lang.String str, int i) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "alias is null");
        if (this.mDatabase.setRecoveryStatus(android.os.Binder.getCallingUid(), str, i) < 0) {
            throw new android.os.ServiceSpecificException(22, "Failed to set the key recovery status in the local DB.");
        }
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.lang.Integer> getRecoveryStatus() throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        return this.mDatabase.getStatusForAllKeys(android.os.Binder.getCallingUid());
    }

    public void setRecoverySecretTypes(@android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(iArr, "secretTypes is null");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        int[] recoverySecretTypes = this.mDatabase.getRecoverySecretTypes(callingUserId, callingUid);
        if (java.util.Arrays.equals(iArr, recoverySecretTypes)) {
            android.util.Log.v(TAG, "Not updating secret types - same as old value.");
            return;
        }
        if (this.mDatabase.setRecoverySecretTypes(callingUserId, callingUid, iArr) < 0) {
            throw new android.os.ServiceSpecificException(22, "Database error trying to set secret types.");
        }
        if (recoverySecretTypes.length == 0) {
            android.util.Log.i(TAG, "Initialized secret types.");
            return;
        }
        android.util.Log.i(TAG, "Updated secret types. Snapshot pending.");
        if (this.mDatabase.getSnapshotVersion(callingUserId, callingUid) != null) {
            this.mDatabase.setShouldCreateSnapshot(callingUserId, callingUid, true);
            android.util.Log.i(TAG, "Updated secret types. Snapshot must be updated");
        } else {
            android.util.Log.i(TAG, "Updated secret types. Snapshot didn't exist");
        }
    }

    @android.annotation.NonNull
    public int[] getRecoverySecretTypes() throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        return this.mDatabase.getRecoverySecretTypes(android.os.UserHandle.getCallingUserId(), android.os.Binder.getCallingUid());
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    byte[] startRecoverySession(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2, @android.annotation.NonNull byte[] bArr3, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        int callingUid = android.os.Binder.getCallingUid();
        if (list.size() != 1) {
            throw new java.lang.UnsupportedOperationException("Only a single KeyChainProtectionParams is supported");
        }
        try {
            java.security.PublicKey deserializePublicKey = com.android.server.locksettings.recoverablekeystore.KeySyncUtils.deserializePublicKey(bArr);
            if (!publicKeysMatch(deserializePublicKey, bArr2)) {
                throw new android.os.ServiceSpecificException(28, "The public keys given in verifierPublicKey and vaultParams do not match.");
            }
            byte[] generateKeyClaimant = com.android.server.locksettings.recoverablekeystore.KeySyncUtils.generateKeyClaimant();
            byte[] secret = list.get(0).getSecret();
            this.mRecoverySessionStorage.add(callingUid, new com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry(str, secret, generateKeyClaimant, bArr2));
            android.util.Log.i(TAG, "Received VaultParams for recovery: " + com.android.internal.util.HexDump.toHexString(bArr2));
            try {
                return com.android.server.locksettings.recoverablekeystore.KeySyncUtils.encryptRecoveryClaim(deserializePublicKey, bArr2, bArr3, com.android.server.locksettings.recoverablekeystore.KeySyncUtils.calculateThmKfHash(secret), generateKeyClaimant);
            } catch (java.security.InvalidKeyException e) {
                throw new android.os.ServiceSpecificException(25, e.getMessage());
            } catch (java.security.NoSuchAlgorithmException e2) {
                android.util.Log.wtf(TAG, "SecureBox algorithm missing. AOSP must support this.", e2);
                throw new android.os.ServiceSpecificException(22, e2.getMessage());
            }
        } catch (java.security.spec.InvalidKeySpecException e3) {
            throw new android.os.ServiceSpecificException(25, e3.getMessage());
        }
    }

    @android.annotation.NonNull
    public byte[] startRecoverySessionWithCertPath(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.security.keystore.recovery.RecoveryCertPath recoveryCertPath, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.lang.String defaultCertificateAliasIfEmpty = this.mTestCertHelper.getDefaultCertificateAliasIfEmpty(str2);
        java.util.Objects.requireNonNull(str, "invalid session");
        java.util.Objects.requireNonNull(recoveryCertPath, "verifierCertPath is null");
        java.util.Objects.requireNonNull(bArr, "vaultParams is null");
        java.util.Objects.requireNonNull(bArr2, "vaultChallenge is null");
        java.util.Objects.requireNonNull(list, "secrets is null");
        try {
            java.security.cert.CertPath certPath = recoveryCertPath.getCertPath();
            try {
                com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.validateCertPath(this.mTestCertHelper.getRootCertificate(defaultCertificateAliasIfEmpty), certPath, this.mTestCertHelper.getValidationDate(defaultCertificateAliasIfEmpty));
                byte[] encoded = certPath.getCertificates().get(0).getPublicKey().getEncoded();
                if (encoded == null) {
                    android.util.Log.e(TAG, "Failed to encode verifierPublicKey");
                    throw new android.os.ServiceSpecificException(25, "Failed to encode verifierPublicKey");
                }
                return startRecoverySession(str, encoded, bArr, bArr2, list);
            } catch (com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException e) {
                android.util.Log.e(TAG, "Failed to validate the given cert path", e);
                throw new android.os.ServiceSpecificException(28, e.getMessage());
            }
        } catch (java.security.cert.CertificateException e2) {
            throw new android.os.ServiceSpecificException(25, e2.getMessage());
        }
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.lang.String> recoverKeyChainSnapshot(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry entry = this.mRecoverySessionStorage.get(callingUid, str);
        try {
            if (entry == null) {
                throw new android.os.ServiceSpecificException(24, java.lang.String.format(java.util.Locale.US, "Application uid=%d does not have pending session '%s'", java.lang.Integer.valueOf(callingUid), str));
            }
            try {
                return importKeyMaterials(callingUserId, callingUid, recoverApplicationKeys(decryptRecoveryKey(entry, bArr), list));
            } catch (java.security.KeyStoreException e) {
                throw new android.os.ServiceSpecificException(22, e.getMessage());
            }
        } finally {
            entry.destroy();
            this.mRecoverySessionStorage.remove(callingUid);
        }
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.lang.String> importKeyMaterials(int i, int i2, java.util.Map<java.lang.String, byte[]> map) throws java.security.KeyStoreException {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(map.size());
        for (java.lang.String str : map.keySet()) {
            this.mApplicationKeyStorage.setSymmetricKeyEntry(i, i2, str, map.get(str));
            java.lang.String alias = getAlias(i, i2, str);
            android.util.Log.i(TAG, java.lang.String.format(java.util.Locale.US, "Import %s -> %s", str, alias));
            arrayMap.put(str, alias);
        }
        return arrayMap;
    }

    @android.annotation.Nullable
    private java.lang.String getAlias(int i, int i2, java.lang.String str) {
        return this.mApplicationKeyStorage.getGrantAlias(i, i2, str);
    }

    public void closeSession(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "invalid session");
        this.mRecoverySessionStorage.remove(android.os.Binder.getCallingUid(), str);
    }

    public void removeKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "alias is null");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (this.mDatabase.removeKey(callingUid, str)) {
            this.mDatabase.setShouldCreateSnapshot(callingUserId, callingUid, true);
            this.mApplicationKeyStorage.deleteEntry(callingUserId, callingUid, str);
        }
    }

    @java.lang.Deprecated
    public java.lang.String generateKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return generateKeyWithMetadata(str, null);
    }

    public java.lang.String generateKeyWithMetadata(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable byte[] bArr) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "alias is null");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        try {
            try {
                this.mApplicationKeyStorage.setSymmetricKeyEntry(callingUserId, callingUid, str, this.mRecoverableKeyGenerator.generateAndStoreKey(this.mPlatformKeyManager.getEncryptKey(callingUserId), callingUserId, callingUid, str, bArr));
                return getAlias(callingUserId, callingUid, str);
            } catch (com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException | java.security.InvalidKeyException | java.security.KeyStoreException e) {
                throw new android.os.ServiceSpecificException(22, e.getMessage());
            }
        } catch (com.android.server.locksettings.recoverablekeystore.InsecureUserException e2) {
            throw new android.os.ServiceSpecificException(23, e2.getMessage());
        } catch (java.io.IOException | java.security.KeyStoreException | java.security.UnrecoverableKeyException e3) {
            throw new android.os.ServiceSpecificException(22, e3.getMessage());
        } catch (java.security.NoSuchAlgorithmException e4) {
            throw new java.lang.RuntimeException(e4);
        }
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public java.lang.String importKey(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) throws android.os.RemoteException {
        return importKeyWithMetadata(str, bArr, null);
    }

    @android.annotation.Nullable
    public java.lang.String importKeyWithMetadata(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.Nullable byte[] bArr2) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "alias is null");
        java.util.Objects.requireNonNull(bArr, "keyBytes is null");
        if (bArr.length != 32) {
            android.util.Log.e(TAG, "The given key for import doesn't have the required length 256");
            throw new android.os.ServiceSpecificException(27, "The given key does not contain 256 bits.");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        try {
            try {
                this.mRecoverableKeyGenerator.importKey(this.mPlatformKeyManager.getEncryptKey(callingUserId), callingUserId, callingUid, str, bArr, bArr2);
                this.mApplicationKeyStorage.setSymmetricKeyEntry(callingUserId, callingUid, str, bArr);
                return getAlias(callingUserId, callingUid, str);
            } catch (com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException | java.security.InvalidKeyException | java.security.KeyStoreException e) {
                throw new android.os.ServiceSpecificException(22, e.getMessage());
            }
        } catch (com.android.server.locksettings.recoverablekeystore.InsecureUserException e2) {
            throw new android.os.ServiceSpecificException(23, e2.getMessage());
        } catch (java.io.IOException | java.security.KeyStoreException | java.security.UnrecoverableKeyException e3) {
            throw new android.os.ServiceSpecificException(22, e3.getMessage());
        } catch (java.security.NoSuchAlgorithmException e4) {
            throw new java.lang.RuntimeException(e4);
        }
    }

    @android.annotation.Nullable
    public java.lang.String getKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        checkRecoverKeyStorePermission();
        java.util.Objects.requireNonNull(str, "alias is null");
        return getAlias(android.os.UserHandle.getCallingUserId(), android.os.Binder.getCallingUid(), str);
    }

    private byte[] decryptRecoveryKey(com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage.Entry entry, byte[] bArr) throws android.os.RemoteException, android.os.ServiceSpecificException {
        try {
            try {
                return com.android.server.locksettings.recoverablekeystore.KeySyncUtils.decryptRecoveryKey(entry.getLskfHash(), com.android.server.locksettings.recoverablekeystore.KeySyncUtils.decryptRecoveryClaimResponse(entry.getKeyClaimant(), entry.getVaultParams(), bArr));
            } catch (java.security.InvalidKeyException e) {
                android.util.Log.e(TAG, "Got InvalidKeyException during decrypting recovery key", e);
                throw new android.os.ServiceSpecificException(26, "Failed to decrypt recovery key " + e.getMessage());
            } catch (java.security.NoSuchAlgorithmException e2) {
                throw new android.os.ServiceSpecificException(22, e2.getMessage());
            } catch (javax.crypto.AEADBadTagException e3) {
                android.util.Log.e(TAG, "Got AEADBadTagException during decrypting recovery key", e3);
                throw new android.os.ServiceSpecificException(26, "Failed to decrypt recovery key " + e3.getMessage());
            }
        } catch (java.security.InvalidKeyException e4) {
            android.util.Log.e(TAG, "Got InvalidKeyException during decrypting recovery claim response", e4);
            throw new android.os.ServiceSpecificException(26, "Failed to decrypt recovery key " + e4.getMessage());
        } catch (java.security.NoSuchAlgorithmException e5) {
            throw new android.os.ServiceSpecificException(22, e5.getMessage());
        } catch (javax.crypto.AEADBadTagException e6) {
            android.util.Log.e(TAG, "Got AEADBadTagException during decrypting recovery claim response", e6);
            throw new android.os.ServiceSpecificException(26, "Failed to decrypt recovery key " + e6.getMessage());
        }
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, byte[]> recoverApplicationKeys(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.security.keystore.recovery.WrappedApplicationKey wrappedApplicationKey : list) {
            java.lang.String alias = wrappedApplicationKey.getAlias();
            try {
                hashMap.put(alias, com.android.server.locksettings.recoverablekeystore.KeySyncUtils.decryptApplicationKey(bArr, wrappedApplicationKey.getEncryptedKeyMaterial(), wrappedApplicationKey.getMetadata()));
            } catch (java.security.InvalidKeyException e) {
                android.util.Log.e(TAG, "Got InvalidKeyException during decrypting application key with alias: " + alias, e);
                throw new android.os.ServiceSpecificException(26, "Failed to recover key with alias '" + alias + "': " + e.getMessage());
            } catch (java.security.NoSuchAlgorithmException e2) {
                android.util.Log.wtf(TAG, "Missing SecureBox algorithm. AOSP required to support this.", e2);
                throw new android.os.ServiceSpecificException(22, e2.getMessage());
            } catch (javax.crypto.AEADBadTagException e3) {
                android.util.Log.e(TAG, "Got AEADBadTagException during decrypting application key with alias: " + alias, e3);
            }
        }
        if (!list.isEmpty() && hashMap.isEmpty()) {
            android.util.Log.e(TAG, "Failed to recover any of the application keys.");
            throw new android.os.ServiceSpecificException(26, "Failed to recover any of the application keys.");
        }
        return hashMap;
    }

    public void lockScreenSecretAvailable(int i, @android.annotation.NonNull byte[] bArr, int i2) {
        try {
            this.mExecutorService.schedule(com.android.server.locksettings.recoverablekeystore.KeySyncTask.newInstance(this.mContext, this.mDatabase, this.mSnapshotStorage, this.mListenersStorage, i2, i, bArr, false), SYNC_DELAY_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (com.android.server.locksettings.recoverablekeystore.InsecureUserException e) {
            android.util.Log.wtf(TAG, "Impossible - insecure user, but user just entered lock screen", e);
        } catch (java.security.KeyStoreException e2) {
            android.util.Log.e(TAG, "Key store error encountered during recoverable key sync", e2);
        } catch (java.security.NoSuchAlgorithmException e3) {
            android.util.Log.wtf(TAG, "Should never happen - algorithm unavailable for KeySync", e3);
        }
    }

    public void lockScreenSecretChanged(int i, @android.annotation.Nullable byte[] bArr, int i2) {
        try {
            this.mExecutorService.schedule(com.android.server.locksettings.recoverablekeystore.KeySyncTask.newInstance(this.mContext, this.mDatabase, this.mSnapshotStorage, this.mListenersStorage, i2, i, bArr, true), SYNC_DELAY_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (com.android.server.locksettings.recoverablekeystore.InsecureUserException e) {
            android.util.Log.e(TAG, "InsecureUserException during lock screen secret update", e);
        } catch (java.security.KeyStoreException e2) {
            android.util.Log.e(TAG, "Key store error encountered during recoverable key sync", e2);
        } catch (java.security.NoSuchAlgorithmException e3) {
            android.util.Log.wtf(TAG, "Should never happen - algorithm unavailable for KeySync", e3);
        }
    }

    public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation(com.android.server.locksettings.LockSettingsService lockSettingsService) {
        if (this.mRemoteLockscreenValidationSessionStorage == null) {
            throw new java.lang.UnsupportedOperationException("Under development");
        }
        checkVerifyRemoteLockscreenPermission();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int credentialType = lockSettingsService.getCredentialType(callingUserId);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            int lockPatternUtilsToKeyguardType = lockPatternUtilsToKeyguardType(credentialType);
            return new android.app.RemoteLockscreenValidationSession.Builder().setLockType(lockPatternUtilsToKeyguardType).setRemainingAttempts(java.lang.Math.max(5 - this.mDatabase.getBadRemoteGuessCounter(callingUserId), 0)).setSourcePublicKey(com.android.security.SecureBox.encodePublicKey(this.mRemoteLockscreenValidationSessionStorage.startSession(callingUserId).getKeyPair().getPublic())).build();
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public synchronized android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(@android.annotation.NonNull byte[] bArr, com.android.server.locksettings.LockSettingsService lockSettingsService) {
        checkVerifyRemoteLockscreenPermission();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession lockscreenVerificationSession = this.mRemoteLockscreenValidationSessionStorage.get(callingUserId);
        if (5 - this.mDatabase.getBadRemoteGuessCounter(callingUserId) <= 0) {
            return new android.app.RemoteLockscreenValidationResult.Builder().setResultCode(4).build();
        }
        if (lockscreenVerificationSession == null) {
            return new android.app.RemoteLockscreenValidationResult.Builder().setResultCode(5).build();
        }
        try {
            try {
                byte[] decrypt = com.android.security.SecureBox.decrypt(lockscreenVerificationSession.getKeyPair().getPrivate(), null, com.android.internal.widget.LockPatternUtils.ENCRYPTED_REMOTE_CREDENTIALS_HEADER, bArr);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.internal.widget.LockscreenCredential createLockscreenCredential = createLockscreenCredential(lockPatternUtilsToKeyguardType(lockSettingsService.getCredentialType(callingUserId)), decrypt);
                    try {
                        android.app.RemoteLockscreenValidationResult handleVerifyCredentialResponse = handleVerifyCredentialResponse(lockSettingsService.verifyCredential(createLockscreenCredential, callingUserId, 0), callingUserId);
                        if (createLockscreenCredential != null) {
                            createLockscreenCredential.close();
                        }
                        return handleVerifyCredentialResponse;
                    } finally {
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.security.InvalidKeyException e) {
                android.util.Log.e(TAG, "Got InvalidKeyException during lock screen credentials decryption");
                throw new java.lang.IllegalStateException(e);
            } catch (javax.crypto.AEADBadTagException e2) {
                throw new java.lang.IllegalStateException("Could not decrypt credentials guess", e2);
            }
        } catch (java.security.NoSuchAlgorithmException e3) {
            android.util.Log.wtf(TAG, "Missing SecureBox algorithm. AOSP required to support this.", e3);
            throw new java.lang.IllegalStateException(e3);
        }
    }

    private android.app.RemoteLockscreenValidationResult handleVerifyCredentialResponse(com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse, int i) {
        if (verifyCredentialResponse.getResponseCode() == 0) {
            this.mDatabase.setBadRemoteGuessCounter(i, 0);
            this.mRemoteLockscreenValidationSessionStorage.finishSession(i);
            return new android.app.RemoteLockscreenValidationResult.Builder().setResultCode(1).build();
        }
        if (verifyCredentialResponse.getResponseCode() == 1) {
            return new android.app.RemoteLockscreenValidationResult.Builder().setResultCode(3).setTimeoutMillis(verifyCredentialResponse.getTimeout()).build();
        }
        this.mDatabase.setBadRemoteGuessCounter(i, this.mDatabase.getBadRemoteGuessCounter(i) + 1);
        return new android.app.RemoteLockscreenValidationResult.Builder().setResultCode(2).build();
    }

    private com.android.internal.widget.LockscreenCredential createLockscreenCredential(int i, byte[] bArr) {
        switch (i) {
            case 0:
                return com.android.internal.widget.LockscreenCredential.createPassword(new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8));
            case 1:
                return com.android.internal.widget.LockscreenCredential.createPin(new java.lang.String(bArr));
            case 2:
                byte lockPatternSize = new com.android.internal.widget.LockPatternUtils(this.mContext).getLockPatternSize(this.mContext.getUserId());
                return com.android.internal.widget.LockscreenCredential.createPattern(com.android.internal.widget.LockPatternUtils.byteArrayToPattern(bArr, lockPatternSize), lockPatternSize);
            default:
                throw new java.lang.IllegalStateException("Lockscreen is not set");
        }
    }

    private void checkVerifyRemoteLockscreenPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CHECK_REMOTE_LOCKSCREEN", "Caller " + android.os.Binder.getCallingUid() + " doesn't have CHECK_REMOTE_LOCKSCREEN permission.");
        this.mCleanupManager.registerRecoveryAgent(android.os.UserHandle.getCallingUserId(), android.os.Binder.getCallingUid());
    }

    private int lockPatternUtilsToKeyguardType(int i) {
        switch (i) {
            case -1:
                throw new java.lang.IllegalStateException("Screen lock is not set");
            case 0:
            case 2:
            default:
                throw new java.lang.IllegalStateException("Screen lock is not set");
            case 1:
                return 2;
            case 3:
                return 1;
            case 4:
                return 0;
        }
    }

    private void checkRecoverKeyStorePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVER_KEYSTORE", "Caller " + android.os.Binder.getCallingUid() + " doesn't have RecoverKeyStore permission.");
        this.mCleanupManager.registerRecoveryAgent(android.os.UserHandle.getCallingUserId(), android.os.Binder.getCallingUid());
    }

    private boolean publicKeysMatch(java.security.PublicKey publicKey, byte[] bArr) {
        byte[] encodePublicKey = com.android.security.SecureBox.encodePublicKey(publicKey);
        return java.util.Arrays.equals(encodePublicKey, java.util.Arrays.copyOf(bArr, encodePublicKey.length));
    }
}
