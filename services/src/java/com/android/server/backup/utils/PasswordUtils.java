package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class PasswordUtils {
    public static final java.lang.String ENCRYPTION_ALGORITHM_NAME = "AES-256";
    public static final int PBKDF2_HASH_ROUNDS = 10000;
    private static final int PBKDF2_KEY_SIZE = 256;
    public static final int PBKDF2_SALT_SIZE = 512;

    public static javax.crypto.SecretKey buildPasswordKey(java.lang.String str, java.lang.String str2, byte[] bArr, int i) {
        return buildCharArrayKey(str, str2.toCharArray(), bArr, i);
    }

    public static java.lang.String buildPasswordHash(java.lang.String str, java.lang.String str2, byte[] bArr, int i) {
        javax.crypto.SecretKey buildPasswordKey = buildPasswordKey(str, str2, bArr, i);
        if (buildPasswordKey != null) {
            return byteArrayToHex(buildPasswordKey.getEncoded());
        }
        return null;
    }

    public static java.lang.String byteArrayToHex(byte[] bArr) {
        return libcore.util.HexEncoding.encodeToString(bArr, true);
    }

    public static byte[] hexToByteArray(java.lang.String str) {
        int length = str.length() / 2;
        if (length * 2 != str.length()) {
            throw new java.lang.IllegalArgumentException("Hex string must have an even number of digits");
        }
        byte[] bArr = new byte[length];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 2;
            bArr[i / 2] = (byte) java.lang.Integer.parseInt(str.substring(i, i2), 16);
            i = i2;
        }
        return bArr;
    }

    public static byte[] makeKeyChecksum(java.lang.String str, byte[] bArr, byte[] bArr2, int i) {
        char[] cArr = new char[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            cArr[i2] = (char) bArr[i2];
        }
        return buildCharArrayKey(str, cArr, bArr2, i).getEncoded();
    }

    private static javax.crypto.SecretKey buildCharArrayKey(java.lang.String str, char[] cArr, byte[] bArr, int i) {
        try {
            return javax.crypto.SecretKeyFactory.getInstance(str).generateSecret(new javax.crypto.spec.PBEKeySpec(cArr, bArr, i, 256));
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "PBKDF2 unavailable!");
            return null;
        } catch (java.security.spec.InvalidKeySpecException e2) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Invalid key spec for PBKDF2!");
            return null;
        }
    }
}
