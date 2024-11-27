package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
public class DigestUtils {
    private static final int FILE_READ_BUFFER_SIZE = 16384;

    private DigestUtils() {
    }

    public static byte[] getSha256Hash(java.io.File file) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            byte[] sha256Hash = getSha256Hash(fileInputStream);
            fileInputStream.close();
            return sha256Hash;
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] getSha256Hash(java.io.InputStream inputStream) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA256");
        byte[] bArr = new byte[16384];
        while (true) {
            int read = inputStream.read(bArr);
            if (read >= 0) {
                messageDigest.update(bArr, 0, read);
            } else {
                return messageDigest.digest();
            }
        }
    }
}
