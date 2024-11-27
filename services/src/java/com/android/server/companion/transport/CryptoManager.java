package com.android.server.companion.transport;

/* loaded from: classes.dex */
public class CryptoManager {
    private static final java.lang.String ALGORITHM = "AES";
    private static final int SECRET_KEY_LENGTH = 32;
    private static final java.lang.String TAG = "CDM_CryptoManager";
    private static final java.lang.String TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private javax.crypto.Cipher mDecryptCipher;
    private javax.crypto.Cipher mEncryptCipher;
    private final byte[] mPreSharedKey;
    private javax.crypto.SecretKey mSecretKey;

    public CryptoManager(byte[] bArr) {
        if (bArr == null) {
            this.mPreSharedKey = java.util.Arrays.copyOf(new byte[0], 32);
        } else {
            this.mPreSharedKey = java.util.Arrays.copyOf(bArr, 32);
        }
        this.mSecretKey = new javax.crypto.spec.SecretKeySpec(this.mPreSharedKey, ALGORITHM);
        try {
            this.mEncryptCipher = javax.crypto.Cipher.getInstance(TRANSFORMATION);
            this.mEncryptCipher.init(1, this.mSecretKey);
            this.mDecryptCipher = javax.crypto.Cipher.getInstance(TRANSFORMATION);
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
            android.util.Slog.e(TAG, e.getMessage());
        }
    }

    public byte[] encrypt(byte[] bArr) {
        try {
            if (this.mEncryptCipher == null) {
                return null;
            }
            byte[] doFinal = this.mEncryptCipher.doFinal(bArr);
            return java.nio.ByteBuffer.allocate(this.mEncryptCipher.getIV().length + 4 + 4 + doFinal.length).putInt(this.mEncryptCipher.getIV().length).put(this.mEncryptCipher.getIV()).putInt(doFinal.length).put(doFinal).array();
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            android.util.Slog.e(TAG, e.getMessage());
            return null;
        }
    }

    public byte[] decrypt(byte[] bArr) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        byte[] bArr2 = new byte[wrap.getInt()];
        wrap.get(bArr2);
        byte[] bArr3 = new byte[wrap.getInt()];
        wrap.get(bArr3);
        try {
            this.mDecryptCipher.init(2, getKey(), new javax.crypto.spec.IvParameterSpec(bArr2));
            return this.mDecryptCipher.doFinal(bArr3);
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            android.util.Slog.e(TAG, e.getMessage());
            return null;
        }
    }

    private javax.crypto.SecretKey getKey() {
        if (this.mSecretKey != null) {
            return this.mSecretKey;
        }
        this.mSecretKey = new javax.crypto.spec.SecretKeySpec(this.mPreSharedKey, ALGORITHM);
        return this.mSecretKey;
    }
}
