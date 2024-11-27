package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class KeySyncUtils {
    private static final int KEY_CLAIMANT_LENGTH_BYTES = 16;
    private static final java.lang.String PUBLIC_KEY_FACTORY_ALGORITHM = "EC";
    private static final java.lang.String RECOVERY_KEY_ALGORITHM = "AES";
    private static final int RECOVERY_KEY_SIZE_BITS = 256;
    private static final byte[] THM_ENCRYPTED_RECOVERY_KEY_HEADER = "V1 THM_encrypted_recovery_key".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER = "V1 locally_encrypted_recovery_key".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] ENCRYPTED_APPLICATION_KEY_HEADER = "V1 encrypted_application_key".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] RECOVERY_CLAIM_HEADER = "V1 KF_claim".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] RECOVERY_RESPONSE_HEADER = "V1 reencrypted_recovery_key".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] THM_KF_HASH_PREFIX = "THM_KF_hash".getBytes(java.nio.charset.StandardCharsets.UTF_8);

    private KeySyncUtils() {
    }

    public static byte[] thmEncryptRecoveryKey(java.security.PublicKey publicKey, byte[] bArr, byte[] bArr2, javax.crypto.SecretKey secretKey) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        return com.android.security.SecureBox.encrypt(publicKey, calculateThmKfHash(bArr), com.android.internal.util.ArrayUtils.concat(new byte[][]{THM_ENCRYPTED_RECOVERY_KEY_HEADER, bArr2}), locallyEncryptRecoveryKey(bArr, secretKey));
    }

    public static byte[] calculateThmKfHash(byte[] bArr) throws java.security.NoSuchAlgorithmException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
        messageDigest.update(THM_KF_HASH_PREFIX);
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    @com.android.internal.annotations.VisibleForTesting
    static byte[] locallyEncryptRecoveryKey(byte[] bArr, javax.crypto.SecretKey secretKey) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        return com.android.security.SecureBox.encrypt(null, bArr, LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER, secretKey.getEncoded());
    }

    public static javax.crypto.SecretKey generateRecoveryKey() throws java.security.NoSuchAlgorithmException {
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(RECOVERY_KEY_ALGORITHM);
        keyGenerator.init(256, new java.security.SecureRandom());
        return keyGenerator.generateKey();
    }

    public static java.util.Map<java.lang.String, byte[]> encryptKeysWithRecoveryKey(javax.crypto.SecretKey secretKey, java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> map) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        byte[] concat;
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str : map.keySet()) {
            javax.crypto.SecretKey secretKey2 = (javax.crypto.SecretKey) map.get(str).first;
            byte[] bArr = (byte[]) map.get(str).second;
            if (bArr == null) {
                concat = ENCRYPTED_APPLICATION_KEY_HEADER;
            } else {
                concat = com.android.internal.util.ArrayUtils.concat(new byte[][]{ENCRYPTED_APPLICATION_KEY_HEADER, bArr});
            }
            hashMap.put(str, com.android.security.SecureBox.encrypt(null, secretKey.getEncoded(), concat, secretKey2.getEncoded()));
        }
        return hashMap;
    }

    public static byte[] generateKeyClaimant() {
        byte[] bArr = new byte[16];
        new java.security.SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static byte[] encryptRecoveryClaim(java.security.PublicKey publicKey, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        return com.android.security.SecureBox.encrypt(publicKey, null, com.android.internal.util.ArrayUtils.concat(new byte[][]{RECOVERY_CLAIM_HEADER, bArr, bArr2}), com.android.internal.util.ArrayUtils.concat(new byte[][]{bArr3, bArr4}));
    }

    public static byte[] decryptRecoveryClaimResponse(byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        return com.android.security.SecureBox.decrypt(null, bArr, com.android.internal.util.ArrayUtils.concat(new byte[][]{RECOVERY_RESPONSE_HEADER, bArr2}), bArr3);
    }

    public static byte[] decryptRecoveryKey(byte[] bArr, byte[] bArr2) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        return com.android.security.SecureBox.decrypt(null, bArr, LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER, bArr2);
    }

    public static byte[] decryptApplicationKey(byte[] bArr, byte[] bArr2, @android.annotation.Nullable byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        byte[] concat;
        if (bArr3 == null) {
            concat = ENCRYPTED_APPLICATION_KEY_HEADER;
        } else {
            concat = com.android.internal.util.ArrayUtils.concat(new byte[][]{ENCRYPTED_APPLICATION_KEY_HEADER, bArr3});
        }
        return com.android.security.SecureBox.decrypt(null, bArr, concat, bArr2);
    }

    public static java.security.PublicKey deserializePublicKey(byte[] bArr) throws java.security.spec.InvalidKeySpecException {
        try {
            return java.security.KeyFactory.getInstance(PUBLIC_KEY_FACTORY_ALGORITHM).generatePublic(new java.security.spec.X509EncodedKeySpec(bArr));
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static byte[] packVaultParams(java.security.PublicKey publicKey, long j, int i, byte[] bArr) {
        return java.nio.ByteBuffer.allocate(bArr.length + 77).order(java.nio.ByteOrder.LITTLE_ENDIAN).put(com.android.security.SecureBox.encodePublicKey(publicKey)).putLong(j).putInt(i).put(bArr).array();
    }
}
