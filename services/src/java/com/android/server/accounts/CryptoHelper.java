package com.android.server.accounts;

/* loaded from: classes.dex */
class CryptoHelper {
    private static final java.lang.String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;
    private static final java.lang.String KEY_ALGORITHM = "AES";
    private static final java.lang.String KEY_CIPHER = "cipher";
    private static final java.lang.String KEY_IV = "iv";
    private static final java.lang.String KEY_MAC = "mac";
    private static final java.lang.String MAC_ALGORITHM = "HMACSHA256";
    private static final java.lang.String TAG = "Account";
    private static com.android.server.accounts.CryptoHelper sInstance;
    private final javax.crypto.SecretKey mEncryptionKey = javax.crypto.KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();
    private final javax.crypto.SecretKey mMacKey = javax.crypto.KeyGenerator.getInstance(MAC_ALGORITHM).generateKey();

    static synchronized com.android.server.accounts.CryptoHelper getInstance() throws java.security.NoSuchAlgorithmException {
        com.android.server.accounts.CryptoHelper cryptoHelper;
        synchronized (com.android.server.accounts.CryptoHelper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.accounts.CryptoHelper();
                }
                cryptoHelper = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return cryptoHelper;
    }

    private CryptoHelper() throws java.security.NoSuchAlgorithmException {
    }

    @android.annotation.NonNull
    android.os.Bundle encryptBundle(@android.annotation.NonNull android.os.Bundle bundle) throws java.security.GeneralSecurityException {
        java.util.Objects.requireNonNull(bundle, "Cannot encrypt null bundle.");
        android.os.Parcel obtain = android.os.Parcel.obtain();
        bundle.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(1, this.mEncryptionKey);
        byte[] doFinal = cipher.doFinal(marshall);
        byte[] iv = cipher.getIV();
        byte[] createMac = createMac(doFinal, iv);
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putByteArray(KEY_CIPHER, doFinal);
        bundle2.putByteArray(KEY_MAC, createMac);
        bundle2.putByteArray(KEY_IV, iv);
        return bundle2;
    }

    @android.annotation.Nullable
    android.os.Bundle decryptBundle(@android.annotation.NonNull android.os.Bundle bundle) throws java.security.GeneralSecurityException {
        java.util.Objects.requireNonNull(bundle, "Cannot decrypt null bundle.");
        byte[] byteArray = bundle.getByteArray(KEY_IV);
        byte[] byteArray2 = bundle.getByteArray(KEY_CIPHER);
        if (!verifyMac(byteArray2, byteArray, bundle.getByteArray(KEY_MAC))) {
            android.util.Log.w(TAG, "Escrow mac mismatched!");
            return null;
        }
        javax.crypto.spec.IvParameterSpec ivParameterSpec = new javax.crypto.spec.IvParameterSpec(byteArray);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(2, this.mEncryptionKey, ivParameterSpec);
        byte[] doFinal = cipher.doFinal(byteArray2);
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.unmarshall(doFinal, 0, doFinal.length);
        obtain.setDataPosition(0);
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.readFromParcel(obtain);
        obtain.recycle();
        return bundle2;
    }

    private boolean verifyMac(@android.annotation.Nullable byte[] bArr, @android.annotation.Nullable byte[] bArr2, @android.annotation.Nullable byte[] bArr3) throws java.security.GeneralSecurityException {
        if (bArr == null || bArr.length == 0 || bArr3 == null || bArr3.length == 0) {
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "Cipher or MAC is empty!");
                return false;
            }
            return false;
        }
        return constantTimeArrayEquals(bArr3, createMac(bArr, bArr2));
    }

    @android.annotation.NonNull
    private byte[] createMac(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2) throws java.security.GeneralSecurityException {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(MAC_ALGORITHM);
        mac.init(this.mMacKey);
        mac.update(bArr);
        mac.update(bArr2);
        return mac.doFinal();
    }

    private static boolean constantTimeArrayEquals(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return bArr == bArr2;
        }
        if (bArr.length != bArr2.length) {
            return false;
        }
        boolean z = true;
        for (int i = 0; i < bArr2.length; i++) {
            z &= bArr[i] == bArr2[i];
        }
        return z;
    }
}
