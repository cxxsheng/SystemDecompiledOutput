package com.android.security;

/* loaded from: classes.dex */
public class SecureBox {
    private static final java.lang.String CIPHER_ALG = "AES";
    private static final java.lang.String EC_ALG = "EC";
    private static final int EC_COORDINATE_LEN_BYTES = 32;
    private static final java.lang.String EC_P256_COMMON_NAME = "secp256r1";
    private static final java.lang.String EC_P256_OPENSSL_NAME = "prime256v1";
    private static final int EC_PUBLIC_KEY_LEN_BYTES = 65;
    private static final byte EC_PUBLIC_KEY_PREFIX = 4;
    private static final java.lang.String ENC_ALG = "AES/GCM/NoPadding";
    private static final int GCM_KEY_LEN_BYTES = 16;
    private static final int GCM_NONCE_LEN_BYTES = 12;
    private static final int GCM_TAG_LEN_BYTES = 16;
    private static final java.lang.String KA_ALG = "ECDH";
    private static final java.lang.String MAC_ALG = "HmacSHA256";
    private static final byte[] VERSION = {2, 0};
    private static final byte[] HKDF_SALT = com.android.internal.util.ArrayUtils.concat(new byte[][]{"SECUREBOX".getBytes(java.nio.charset.StandardCharsets.UTF_8), VERSION});
    private static final byte[] HKDF_INFO_WITH_PUBLIC_KEY = "P256 HKDF-SHA-256 AES-128-GCM".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] HKDF_INFO_WITHOUT_PUBLIC_KEY = "SHARED HKDF-SHA-256 AES-128-GCM".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    private static final byte[] CONSTANT_01 = {1};
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final java.math.BigInteger BIG_INT_02 = java.math.BigInteger.valueOf(2);
    private static final java.math.BigInteger EC_PARAM_P = new java.math.BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
    private static final java.math.BigInteger EC_PARAM_A = EC_PARAM_P.subtract(new java.math.BigInteger("3"));
    private static final java.math.BigInteger EC_PARAM_B = new java.math.BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);

    @com.android.internal.annotations.VisibleForTesting
    static final java.security.spec.ECParameterSpec EC_PARAM_SPEC = new java.security.spec.ECParameterSpec(new java.security.spec.EllipticCurve(new java.security.spec.ECFieldFp(EC_PARAM_P), EC_PARAM_A, EC_PARAM_B), new java.security.spec.ECPoint(new java.math.BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16), new java.math.BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16)), new java.math.BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16), 1);

    private enum AesGcmOperation {
        ENCRYPT,
        DECRYPT
    }

    private SecureBox() {
    }

    public static java.security.KeyPair genKeyPair() throws java.security.NoSuchAlgorithmException {
        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(EC_ALG);
        try {
            keyPairGenerator.initialize(new java.security.spec.ECGenParameterSpec(EC_P256_OPENSSL_NAME));
            return keyPairGenerator.generateKeyPair();
        } catch (java.security.InvalidAlgorithmParameterException e) {
            try {
                keyPairGenerator.initialize(new java.security.spec.ECGenParameterSpec(EC_P256_COMMON_NAME));
                return keyPairGenerator.generateKeyPair();
            } catch (java.security.InvalidAlgorithmParameterException e2) {
                throw new java.security.NoSuchAlgorithmException("Unable to find the NIST P-256 curve", e2);
            }
        }
    }

    public static byte[] encrypt(@android.annotation.Nullable java.security.PublicKey publicKey, @android.annotation.Nullable byte[] bArr, @android.annotation.Nullable byte[] bArr2, @android.annotation.Nullable byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        java.security.KeyPair genKeyPair;
        byte[] dhComputeSecret;
        byte[] bArr4;
        byte[] emptyByteArrayIfNull = emptyByteArrayIfNull(bArr);
        if (publicKey == null && emptyByteArrayIfNull.length == 0) {
            throw new java.lang.IllegalArgumentException("Both the public key and shared secret are empty");
        }
        byte[] emptyByteArrayIfNull2 = emptyByteArrayIfNull(bArr2);
        byte[] emptyByteArrayIfNull3 = emptyByteArrayIfNull(bArr3);
        if (publicKey == null) {
            dhComputeSecret = EMPTY_BYTE_ARRAY;
            bArr4 = HKDF_INFO_WITHOUT_PUBLIC_KEY;
            genKeyPair = null;
        } else {
            genKeyPair = genKeyPair();
            dhComputeSecret = dhComputeSecret(genKeyPair.getPrivate(), publicKey);
            bArr4 = HKDF_INFO_WITH_PUBLIC_KEY;
        }
        byte[] genRandomNonce = genRandomNonce();
        byte[] aesGcmEncrypt = aesGcmEncrypt(hkdfDeriveKey(com.android.internal.util.ArrayUtils.concat(new byte[][]{dhComputeSecret, emptyByteArrayIfNull}), HKDF_SALT, bArr4), genRandomNonce, emptyByteArrayIfNull3, emptyByteArrayIfNull2);
        if (genKeyPair == null) {
            return com.android.internal.util.ArrayUtils.concat(new byte[][]{VERSION, genRandomNonce, aesGcmEncrypt});
        }
        return com.android.internal.util.ArrayUtils.concat(new byte[][]{VERSION, encodePublicKey(genKeyPair.getPublic()), genRandomNonce, aesGcmEncrypt});
    }

    public static byte[] decrypt(@android.annotation.Nullable java.security.PrivateKey privateKey, @android.annotation.Nullable byte[] bArr, @android.annotation.Nullable byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        byte[] dhComputeSecret;
        byte[] bArr4;
        byte[] emptyByteArrayIfNull = emptyByteArrayIfNull(bArr);
        if (privateKey == null && emptyByteArrayIfNull.length == 0) {
            throw new java.lang.IllegalArgumentException("Both the private key and shared secret are empty");
        }
        byte[] emptyByteArrayIfNull2 = emptyByteArrayIfNull(bArr2);
        if (bArr3 == null) {
            throw new java.lang.NullPointerException("Encrypted payload must not be null.");
        }
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr3);
        if (!java.util.Arrays.equals(readEncryptedPayload(wrap, VERSION.length), VERSION)) {
            throw new javax.crypto.AEADBadTagException("The payload was not encrypted by SecureBox v2");
        }
        if (privateKey == null) {
            dhComputeSecret = EMPTY_BYTE_ARRAY;
            bArr4 = HKDF_INFO_WITHOUT_PUBLIC_KEY;
        } else {
            dhComputeSecret = dhComputeSecret(privateKey, decodePublicKey(readEncryptedPayload(wrap, 65)));
            bArr4 = HKDF_INFO_WITH_PUBLIC_KEY;
        }
        return aesGcmDecrypt(hkdfDeriveKey(com.android.internal.util.ArrayUtils.concat(new byte[][]{dhComputeSecret, emptyByteArrayIfNull}), HKDF_SALT, bArr4), readEncryptedPayload(wrap, 12), readEncryptedPayload(wrap, wrap.remaining()), emptyByteArrayIfNull2);
    }

    private static byte[] readEncryptedPayload(java.nio.ByteBuffer byteBuffer, int i) throws javax.crypto.AEADBadTagException {
        byte[] bArr = new byte[i];
        try {
            byteBuffer.get(bArr);
            return bArr;
        } catch (java.nio.BufferUnderflowException e) {
            throw new javax.crypto.AEADBadTagException("The encrypted payload is too short");
        }
    }

    private static byte[] dhComputeSecret(java.security.PrivateKey privateKey, java.security.PublicKey publicKey) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        javax.crypto.KeyAgreement keyAgreement = javax.crypto.KeyAgreement.getInstance(KA_ALG);
        try {
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            return keyAgreement.generateSecret();
        } catch (java.lang.RuntimeException e) {
            throw new java.security.InvalidKeyException(e);
        }
    }

    private static javax.crypto.SecretKey hkdfDeriveKey(byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(MAC_ALG);
        try {
            mac.init(new javax.crypto.spec.SecretKeySpec(bArr2, MAC_ALG));
            try {
                mac.init(new javax.crypto.spec.SecretKeySpec(mac.doFinal(bArr), MAC_ALG));
                mac.update(bArr3);
                return new javax.crypto.spec.SecretKeySpec(java.util.Arrays.copyOf(mac.doFinal(CONSTANT_01), 16), CIPHER_ALG);
            } catch (java.security.InvalidKeyException e) {
                throw new java.lang.RuntimeException(e);
            }
        } catch (java.security.InvalidKeyException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    private static byte[] aesGcmEncrypt(javax.crypto.SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        try {
            return aesGcmInternal(com.android.security.SecureBox.AesGcmOperation.ENCRYPT, secretKey, bArr, bArr2, bArr3);
        } catch (javax.crypto.AEADBadTagException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static byte[] aesGcmDecrypt(javax.crypto.SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        return aesGcmInternal(com.android.security.SecureBox.AesGcmOperation.DECRYPT, secretKey, bArr, bArr2, bArr3);
    }

    private static byte[] aesGcmInternal(com.android.security.SecureBox.AesGcmOperation aesGcmOperation, javax.crypto.SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.AEADBadTagException {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ENC_ALG);
            javax.crypto.spec.GCMParameterSpec gCMParameterSpec = new javax.crypto.spec.GCMParameterSpec(128, bArr);
            try {
                if (aesGcmOperation == com.android.security.SecureBox.AesGcmOperation.DECRYPT) {
                    cipher.init(2, secretKey, gCMParameterSpec);
                } else {
                    cipher.init(1, secretKey, gCMParameterSpec);
                }
                try {
                    cipher.updateAAD(bArr3);
                    return cipher.doFinal(bArr2);
                } catch (javax.crypto.AEADBadTagException e) {
                    throw e;
                } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e2) {
                    throw new java.lang.RuntimeException(e2);
                }
            } catch (java.security.InvalidAlgorithmParameterException e3) {
                throw new java.lang.RuntimeException(e3);
            }
        } catch (javax.crypto.NoSuchPaddingException e4) {
            throw new java.lang.RuntimeException(e4);
        }
    }

    public static byte[] encodePublicKey(java.security.PublicKey publicKey) {
        java.security.spec.ECPoint w = ((java.security.interfaces.ECPublicKey) publicKey).getW();
        byte[] byteArray = w.getAffineX().toByteArray();
        byte[] byteArray2 = w.getAffineY().toByteArray();
        byte[] bArr = new byte[65];
        java.lang.System.arraycopy(byteArray2, 0, bArr, 65 - byteArray2.length, byteArray2.length);
        java.lang.System.arraycopy(byteArray, 0, bArr, 33 - byteArray.length, byteArray.length);
        bArr[0] = 4;
        return bArr;
    }

    public static java.security.PublicKey decodePublicKey(byte[] bArr) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        java.math.BigInteger bigInteger = new java.math.BigInteger(1, java.util.Arrays.copyOfRange(bArr, 1, 33));
        java.math.BigInteger bigInteger2 = new java.math.BigInteger(1, java.util.Arrays.copyOfRange(bArr, 33, 65));
        validateEcPoint(bigInteger, bigInteger2);
        try {
            return java.security.KeyFactory.getInstance(EC_ALG).generatePublic(new java.security.spec.ECPublicKeySpec(new java.security.spec.ECPoint(bigInteger, bigInteger2), EC_PARAM_SPEC));
        } catch (java.security.spec.InvalidKeySpecException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static void validateEcPoint(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) throws java.security.InvalidKeyException {
        if (bigInteger.compareTo(EC_PARAM_P) >= 0 || bigInteger2.compareTo(EC_PARAM_P) >= 0 || bigInteger.signum() == -1 || bigInteger2.signum() == -1) {
            throw new java.security.InvalidKeyException("Point lies outside of the expected curve");
        }
        if (!bigInteger2.modPow(BIG_INT_02, EC_PARAM_P).equals(bigInteger.modPow(BIG_INT_02, EC_PARAM_P).add(EC_PARAM_A).mod(EC_PARAM_P).multiply(bigInteger).add(EC_PARAM_B).mod(EC_PARAM_P))) {
            throw new java.security.InvalidKeyException("Point lies outside of the expected curve");
        }
    }

    private static byte[] genRandomNonce() throws java.security.NoSuchAlgorithmException {
        byte[] bArr = new byte[12];
        new java.security.SecureRandom().nextBytes(bArr);
        return bArr;
    }

    private static byte[] emptyByteArrayIfNull(@android.annotation.Nullable byte[] bArr) {
        return bArr == null ? EMPTY_BYTE_ARRAY : bArr;
    }
}
