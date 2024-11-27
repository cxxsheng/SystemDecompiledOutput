package com.android.server.locksettings;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class UnifiedProfilePasswordCache {
    private static final int CACHE_TIMEOUT_SECONDS = (int) java.util.concurrent.TimeUnit.DAYS.toSeconds(7);
    private static final int KEY_LENGTH = 256;
    private static final java.lang.String TAG = "UnifiedProfilePasswordCache";
    private final android.util.SparseArray<byte[]> mEncryptedPasswords = new android.util.SparseArray<>();
    private final java.security.KeyStore mKeyStore;

    public UnifiedProfilePasswordCache(java.security.KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    public void storePassword(int i, com.android.internal.widget.LockscreenCredential lockscreenCredential, long j) {
        if (j == 0) {
            return;
        }
        synchronized (this.mEncryptedPasswords) {
            try {
                if (this.mEncryptedPasswords.contains(i)) {
                    return;
                }
                java.lang.String encryptionKeyName = getEncryptionKeyName(i);
                try {
                    javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance("AES", this.mKeyStore.getProvider());
                    keyGenerator.init(new android.security.keystore.KeyGenParameterSpec.Builder(encryptionKeyName, 3).setKeySize(256).setBlockModes("GCM").setNamespace(com.android.server.locksettings.SyntheticPasswordCrypto.keyNamespace()).setEncryptionPaddings("NoPadding").setUserAuthenticationRequired(true).setBoundToSpecificSecureUserId(j).setUserAuthenticationValidityDurationSeconds(CACHE_TIMEOUT_SECONDS).build());
                    javax.crypto.SecretKey generateKey = keyGenerator.generateKey();
                    try {
                        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
                        cipher.init(1, generateKey);
                        this.mEncryptedPasswords.put(i, com.android.internal.util.ArrayUtils.concat(new byte[][]{cipher.getIV(), cipher.doFinal(lockscreenCredential.getCredential())}));
                    } catch (java.security.GeneralSecurityException e) {
                        android.util.Slog.d(TAG, "Cannot encrypt", e);
                    }
                } catch (java.security.GeneralSecurityException e2) {
                    android.util.Slog.e(TAG, "Cannot generate key", e2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public com.android.internal.widget.LockscreenCredential retrievePassword(int i) {
        synchronized (this.mEncryptedPasswords) {
            byte[] bArr = this.mEncryptedPasswords.get(i);
            if (bArr == null) {
                return null;
            }
            try {
                java.security.Key key = this.mKeyStore.getKey(getEncryptionKeyName(i), null);
                if (key == null) {
                    return null;
                }
                byte[] copyOf = java.util.Arrays.copyOf(bArr, 12);
                byte[] copyOfRange = java.util.Arrays.copyOfRange(bArr, 12, bArr.length);
                try {
                    try {
                        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
                        cipher.init(2, key, new javax.crypto.spec.GCMParameterSpec(128, copyOf));
                        byte[] doFinal = cipher.doFinal(copyOfRange);
                        com.android.internal.widget.LockscreenCredential createUnifiedProfilePassword = com.android.internal.widget.LockscreenCredential.createUnifiedProfilePassword(doFinal);
                        java.util.Arrays.fill(doFinal, (byte) 0);
                        return createUnifiedProfilePassword;
                    } catch (java.security.GeneralSecurityException e) {
                        android.util.Slog.d(TAG, "Cannot decrypt", e);
                        return null;
                    }
                } catch (android.security.keystore.UserNotAuthenticatedException e2) {
                    android.util.Slog.i(TAG, "Device not unlocked for more than 7 days");
                    return null;
                }
            } catch (java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException e3) {
                android.util.Slog.d(TAG, "Cannot get key", e3);
                return null;
            }
        }
    }

    public void removePassword(int i) {
        synchronized (this.mEncryptedPasswords) {
            try {
                java.lang.String encryptionKeyName = getEncryptionKeyName(i);
                java.lang.String legacyEncryptionKeyName = getLegacyEncryptionKeyName(i);
                try {
                    if (this.mKeyStore.containsAlias(encryptionKeyName)) {
                        this.mKeyStore.deleteEntry(encryptionKeyName);
                    }
                    if (this.mKeyStore.containsAlias(legacyEncryptionKeyName)) {
                        this.mKeyStore.deleteEntry(legacyEncryptionKeyName);
                    }
                } catch (java.security.KeyStoreException e) {
                    android.util.Slog.d(TAG, "Cannot delete key", e);
                }
                if (this.mEncryptedPasswords.contains(i)) {
                    java.util.Arrays.fill(this.mEncryptedPasswords.get(i), (byte) 0);
                    this.mEncryptedPasswords.remove(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.lang.String getEncryptionKeyName(int i) {
        return "com.android.server.locksettings.unified_profile_cache_v2_" + i;
    }

    private static java.lang.String getLegacyEncryptionKeyName(int i) {
        return "com.android.server.locksettings.unified_profile_cache_" + i;
    }
}
