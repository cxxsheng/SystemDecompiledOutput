package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class KeyStoreCryptoOperationUtils {
    private static volatile java.security.SecureRandom sRng;

    private KeyStoreCryptoOperationUtils() {
    }

    public static boolean canUserAuthorizationSucceed(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.system.keystore2.Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
            switch (authorization.keyParameter.tag) {
                case -1610612234:
                    arrayList.add(java.lang.Long.valueOf(authorization.keyParameter.value.getLongInteger()));
                    break;
            }
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        long secureUserId = android.security.GateKeeper.getSecureUserId();
        if (secureUserId != 0 && arrayList.contains(java.lang.Long.valueOf(secureUserId))) {
            return true;
        }
        long[] authenticatorIds = ((android.hardware.biometrics.BiometricManager) android.app.ActivityThread.currentApplication().getSystemService(android.hardware.biometrics.BiometricManager.class)).getAuthenticatorIds();
        boolean z = authenticatorIds.length > 0;
        int length = authenticatorIds.length;
        int i = 0;
        while (true) {
            if (i < length) {
                if (arrayList.contains(java.lang.Long.valueOf(authenticatorIds[i]))) {
                    i++;
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    public static java.security.InvalidKeyException getInvalidKeyException(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey, android.security.KeyStoreException keyStoreException) {
        switch (keyStoreException.getErrorCode()) {
            case -26:
            case 2:
            case 3:
                return new android.security.keystore.UserNotAuthenticatedException();
            case -25:
                return new android.security.keystore.KeyExpiredException();
            case -24:
                return new android.security.keystore.KeyNotYetValidException();
            case 7:
            case 17:
                return new android.security.keystore.KeyPermanentlyInvalidatedException();
            default:
                return new java.security.InvalidKeyException("Keystore operation failed", keyStoreException);
        }
    }

    public static java.security.GeneralSecurityException getExceptionForCipherInit(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey, android.security.KeyStoreException keyStoreException) {
        if (keyStoreException.getErrorCode() == 1) {
            return null;
        }
        switch (keyStoreException.getErrorCode()) {
            case -55:
                return new java.security.InvalidAlgorithmParameterException("Caller-provided IV not permitted");
            case -52:
                return new java.security.InvalidAlgorithmParameterException("Invalid IV");
            default:
                return getInvalidKeyException(androidKeyStoreKey, keyStoreException);
        }
    }

    static byte[] getRandomBytesToMixIntoKeystoreRng(java.security.SecureRandom secureRandom, int i) {
        if (i <= 0) {
            return libcore.util.EmptyArray.BYTE;
        }
        if (secureRandom == null) {
            secureRandom = getRng();
        }
        byte[] bArr = new byte[i];
        secureRandom.nextBytes(bArr);
        return bArr;
    }

    private static java.security.SecureRandom getRng() {
        if (sRng == null) {
            sRng = new java.security.SecureRandom();
        }
        return sRng;
    }

    static void abortOperation(android.security.KeyStoreOperation keyStoreOperation) {
        if (keyStoreOperation != null) {
            try {
                keyStoreOperation.abort();
            } catch (android.security.KeyStoreException e) {
                if (e.getErrorCode() != -28) {
                    android.util.Log.w("KeyStoreCryptoOperationUtils", "Encountered error trying to abort a keystore operation.", e);
                }
            }
        }
    }

    static long getOrMakeOperationChallenge(android.security.KeyStoreOperation keyStoreOperation, android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) throws android.security.keystore.KeyPermanentlyInvalidatedException {
        if (keyStoreOperation.getChallenge() != null) {
            if (!canUserAuthorizationSucceed(androidKeyStoreKey)) {
                throw new android.security.keystore.KeyPermanentlyInvalidatedException();
            }
            return keyStoreOperation.getChallenge().longValue();
        }
        return getRng().nextLong();
    }
}
