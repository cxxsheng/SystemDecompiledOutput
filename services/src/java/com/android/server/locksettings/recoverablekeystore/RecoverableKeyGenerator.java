package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class RecoverableKeyGenerator {
    static final int KEY_SIZE_BITS = 256;
    private static final int RESULT_CANNOT_INSERT_ROW = -1;
    private static final java.lang.String SECRET_KEY_ALGORITHM = "AES";
    private static final java.lang.String TAG = "PlatformKeyGen";
    private final com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb mDatabase;
    private final javax.crypto.KeyGenerator mKeyGenerator;

    public static com.android.server.locksettings.recoverablekeystore.RecoverableKeyGenerator newInstance(com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb) throws java.security.NoSuchAlgorithmException {
        return new com.android.server.locksettings.recoverablekeystore.RecoverableKeyGenerator(javax.crypto.KeyGenerator.getInstance(SECRET_KEY_ALGORITHM), recoverableKeyStoreDb);
    }

    private RecoverableKeyGenerator(javax.crypto.KeyGenerator keyGenerator, com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb recoverableKeyStoreDb) {
        this.mKeyGenerator = keyGenerator;
        this.mDatabase = recoverableKeyStoreDb;
    }

    public byte[] generateAndStoreKey(com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey platformEncryptionKey, int i, int i2, java.lang.String str, @android.annotation.Nullable byte[] bArr) throws com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException, java.security.KeyStoreException, java.security.InvalidKeyException {
        this.mKeyGenerator.init(256);
        javax.crypto.SecretKey generateKey = this.mKeyGenerator.generateKey();
        if (this.mDatabase.insertKey(i, i2, str, com.android.server.locksettings.recoverablekeystore.WrappedKey.fromSecretKey(platformEncryptionKey, generateKey, bArr)) == -1) {
            throw new com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException(java.lang.String.format(java.util.Locale.US, "Failed writing (%d, %s) to database.", java.lang.Integer.valueOf(i2), str));
        }
        if (this.mDatabase.setShouldCreateSnapshot(i, i2, true) < 0) {
            android.util.Log.e(TAG, "Failed to set the shoudCreateSnapshot flag in the local DB.");
        }
        return generateKey.getEncoded();
    }

    public void importKey(@android.annotation.NonNull com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey platformEncryptionKey, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.Nullable byte[] bArr2) throws com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException, java.security.KeyStoreException, java.security.InvalidKeyException {
        if (this.mDatabase.insertKey(i, i2, str, com.android.server.locksettings.recoverablekeystore.WrappedKey.fromSecretKey(platformEncryptionKey, new javax.crypto.spec.SecretKeySpec(bArr, SECRET_KEY_ALGORITHM), bArr2)) == -1) {
            throw new com.android.server.locksettings.recoverablekeystore.RecoverableKeyStorageException(java.lang.String.format(java.util.Locale.US, "Failed writing (%d, %s) to database.", java.lang.Integer.valueOf(i2), str));
        }
        this.mDatabase.setShouldCreateSnapshot(i, i2, true);
    }
}
