package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class WrappedKey {
    private static final java.lang.String APPLICATION_KEY_ALGORITHM = "AES";
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final java.lang.String KEY_WRAP_CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final java.lang.String TAG = "WrappedKey";
    private final byte[] mKeyMaterial;
    private final byte[] mKeyMetadata;
    private final byte[] mNonce;
    private final int mPlatformKeyGenerationId;
    private final int mRecoveryStatus;

    public static com.android.server.locksettings.recoverablekeystore.WrappedKey fromSecretKey(com.android.server.locksettings.recoverablekeystore.PlatformEncryptionKey platformEncryptionKey, javax.crypto.SecretKey secretKey, @android.annotation.Nullable byte[] bArr) throws java.security.InvalidKeyException, java.security.KeyStoreException {
        if (secretKey.getEncoded() == null) {
            throw new java.security.InvalidKeyException("key does not expose encoded material. It cannot be wrapped.");
        }
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(KEY_WRAP_CIPHER_ALGORITHM);
            cipher.init(3, platformEncryptionKey.getKey());
            try {
                return new com.android.server.locksettings.recoverablekeystore.WrappedKey(cipher.getIV(), cipher.wrap(secretKey), bArr, platformEncryptionKey.getGenerationId(), 1);
            } catch (javax.crypto.IllegalBlockSizeException e) {
                java.lang.Throwable cause = e.getCause();
                if (cause instanceof java.security.KeyStoreException) {
                    throw ((java.security.KeyStoreException) cause);
                }
                throw new java.lang.RuntimeException("IllegalBlockSizeException should not be thrown by AES/GCM/NoPadding mode.", e);
            }
        } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e2) {
            throw new java.lang.RuntimeException("Android does not support AES/GCM/NoPadding. This should never happen.");
        }
    }

    public WrappedKey(byte[] bArr, byte[] bArr2, @android.annotation.Nullable byte[] bArr3, int i) {
        this(bArr, bArr2, bArr3, i, 1);
    }

    public WrappedKey(byte[] bArr, byte[] bArr2, @android.annotation.Nullable byte[] bArr3, int i, int i2) {
        this.mNonce = bArr;
        this.mKeyMaterial = bArr2;
        this.mKeyMetadata = bArr3;
        this.mPlatformKeyGenerationId = i;
        this.mRecoveryStatus = i2;
    }

    public byte[] getNonce() {
        return this.mNonce;
    }

    public byte[] getKeyMaterial() {
        return this.mKeyMaterial;
    }

    @android.annotation.Nullable
    public byte[] getKeyMetadata() {
        return this.mKeyMetadata;
    }

    public int getPlatformKeyGenerationId() {
        return this.mPlatformKeyGenerationId;
    }

    public int getRecoveryStatus() {
        return this.mRecoveryStatus;
    }

    public static java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> unwrapKeys(com.android.server.locksettings.recoverablekeystore.PlatformDecryptionKey platformDecryptionKey, java.util.Map<java.lang.String, com.android.server.locksettings.recoverablekeystore.WrappedKey> map) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, com.android.server.locksettings.recoverablekeystore.BadPlatformKeyException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        java.util.HashMap hashMap = new java.util.HashMap();
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(KEY_WRAP_CIPHER_ALGORITHM);
        int generationId = platformDecryptionKey.getGenerationId();
        for (java.lang.String str : map.keySet()) {
            com.android.server.locksettings.recoverablekeystore.WrappedKey wrappedKey = map.get(str);
            if (wrappedKey.getPlatformKeyGenerationId() != generationId) {
                throw new com.android.server.locksettings.recoverablekeystore.BadPlatformKeyException(java.lang.String.format(java.util.Locale.US, "WrappedKey with alias '%s' was wrapped with platform key %d, not platform key %d", str, java.lang.Integer.valueOf(wrappedKey.getPlatformKeyGenerationId()), java.lang.Integer.valueOf(platformDecryptionKey.getGenerationId())));
            }
            cipher.init(4, platformDecryptionKey.getKey(), new javax.crypto.spec.GCMParameterSpec(128, wrappedKey.getNonce()));
            try {
                hashMap.put(str, android.util.Pair.create((javax.crypto.SecretKey) cipher.unwrap(wrappedKey.getKeyMaterial(), APPLICATION_KEY_ALGORITHM, 3), wrappedKey.getKeyMetadata()));
            } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e) {
                android.util.Log.e(TAG, java.lang.String.format(java.util.Locale.US, "Error unwrapping recoverable key with alias '%s'", str), e);
            }
        }
        return hashMap;
    }
}
