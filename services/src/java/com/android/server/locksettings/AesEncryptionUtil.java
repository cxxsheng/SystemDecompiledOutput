package com.android.server.locksettings;

/* loaded from: classes2.dex */
class AesEncryptionUtil {
    private static final java.lang.String CIPHER_ALGO = "AES/GCM/NoPadding";

    private AesEncryptionUtil() {
    }

    static byte[] decrypt(javax.crypto.SecretKey secretKey, java.io.DataInputStream dataInputStream) throws java.io.IOException {
        java.util.Objects.requireNonNull(secretKey);
        java.util.Objects.requireNonNull(dataInputStream);
        int readInt = dataInputStream.readInt();
        if (readInt < 0 || readInt > 32) {
            throw new java.io.IOException("IV out of range: " + readInt);
        }
        byte[] bArr = new byte[readInt];
        dataInputStream.readFully(bArr);
        int readInt2 = dataInputStream.readInt();
        if (readInt2 < 0) {
            throw new java.io.IOException("Invalid cipher text size: " + readInt2);
        }
        byte[] bArr2 = new byte[readInt2];
        dataInputStream.readFully(bArr2);
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGO);
            cipher.init(2, secretKey, new javax.crypto.spec.GCMParameterSpec(128, bArr));
            return cipher.doFinal(bArr2);
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            throw new java.io.IOException("Could not decrypt cipher text", e);
        }
    }

    static byte[] decrypt(javax.crypto.SecretKey secretKey, byte[] bArr) throws java.io.IOException {
        java.util.Objects.requireNonNull(secretKey);
        java.util.Objects.requireNonNull(bArr);
        return decrypt(secretKey, new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr)));
    }

    static byte[] encrypt(javax.crypto.SecretKey secretKey, byte[] bArr) throws java.io.IOException {
        java.util.Objects.requireNonNull(secretKey);
        java.util.Objects.requireNonNull(bArr);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGO);
            cipher.init(1, secretKey);
            byte[] doFinal = cipher.doFinal(bArr);
            byte[] iv = cipher.getIV();
            dataOutputStream.writeInt(iv.length);
            dataOutputStream.write(iv);
            dataOutputStream.writeInt(doFinal.length);
            dataOutputStream.write(doFinal);
            return byteArrayOutputStream.toByteArray();
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            throw new java.io.IOException("Could not encrypt input data", e);
        }
    }
}
