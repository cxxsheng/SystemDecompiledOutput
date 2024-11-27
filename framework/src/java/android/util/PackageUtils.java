package android.util;

/* loaded from: classes3.dex */
public final class PackageUtils {
    private static final int HIGH_RAM_BUFFER_SIZE_BYTES = 1000000;
    private static final int LOW_RAM_BUFFER_SIZE_BYTES = 1000;

    private PackageUtils() {
    }

    public static java.lang.String[] computeSignaturesSha256Digests(android.content.pm.Signature[] signatureArr) {
        return computeSignaturesSha256Digests(signatureArr, null);
    }

    public static java.lang.String[] computeSignaturesSha256Digests(android.content.pm.Signature[] signatureArr, java.lang.String str) {
        int length = signatureArr.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = computeSha256Digest(signatureArr[i].toByteArray(), str);
        }
        return strArr;
    }

    public static java.lang.String computeSignaturesSha256Digest(android.content.pm.Signature[] signatureArr) {
        if (signatureArr.length == 1) {
            return computeSha256Digest(signatureArr[0].toByteArray(), null);
        }
        return computeSignaturesSha256Digest(computeSignaturesSha256Digests(signatureArr, null));
    }

    public static java.lang.String computeSignaturesSha256Digest(java.lang.String[] strArr) {
        if (strArr.length == 1) {
            return strArr[0];
        }
        java.util.Arrays.sort(strArr);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        for (java.lang.String str : strArr) {
            try {
                byteArrayOutputStream.write(str.getBytes());
            } catch (java.io.IOException e) {
            }
        }
        return computeSha256Digest(byteArrayOutputStream.toByteArray(), null);
    }

    public static byte[] computeSha256DigestBytes(byte[] bArr) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static java.lang.String computeSha256Digest(byte[] bArr) {
        return computeSha256Digest(bArr, null);
    }

    public static java.lang.String computeSha256Digest(byte[] bArr, java.lang.String str) {
        byte[] computeSha256DigestBytes = computeSha256DigestBytes(bArr);
        if (computeSha256DigestBytes == null) {
            return null;
        }
        if (str == null) {
            return libcore.util.HexEncoding.encodeToString(computeSha256DigestBytes, true);
        }
        int length = computeSha256DigestBytes.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = libcore.util.HexEncoding.encodeToString(computeSha256DigestBytes[i], true);
        }
        return android.text.TextUtils.join(str, strArr);
    }

    public static byte[] createLargeFileBuffer() {
        return new byte[android.app.ActivityManager.isLowRamDeviceStatic() ? 1000 : 1000000];
    }

    public static byte[] computeSha256DigestForLargeFileAsBytes(java.lang.String str, byte[] bArr) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA256");
            messageDigest.reset();
            try {
                do {
                } while (new java.security.DigestInputStream(new java.io.FileInputStream(new java.io.File(str)), messageDigest).read(bArr) != -1);
                return messageDigest.digest();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            return null;
        }
    }

    public static java.lang.String computeSha256DigestForLargeFile(java.lang.String str, byte[] bArr) {
        return computeSha256DigestForLargeFile(str, bArr, null);
    }

    public static java.lang.String computeSha256DigestForLargeFile(java.lang.String str, byte[] bArr, java.lang.String str2) {
        byte[] computeSha256DigestForLargeFileAsBytes = computeSha256DigestForLargeFileAsBytes(str, bArr);
        if (str2 == null) {
            return libcore.util.HexEncoding.encodeToString(computeSha256DigestForLargeFileAsBytes, false);
        }
        int length = computeSha256DigestForLargeFileAsBytes.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = libcore.util.HexEncoding.encodeToString(computeSha256DigestForLargeFileAsBytes[i], true);
        }
        return android.text.TextUtils.join(str2, strArr);
    }
}
