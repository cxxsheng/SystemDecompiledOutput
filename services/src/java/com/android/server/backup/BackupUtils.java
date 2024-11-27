package com.android.server.backup;

/* loaded from: classes.dex */
public class BackupUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BackupUtils";

    public static boolean signaturesMatch(java.util.ArrayList<byte[]> arrayList, android.content.pm.PackageInfo packageInfo, android.content.pm.PackageManagerInternal packageManagerInternal) {
        boolean z;
        if (packageInfo == null || packageInfo.packageName == null) {
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 1) != 0) {
            return true;
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(arrayList)) {
            return false;
        }
        android.content.pm.SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo == null) {
            android.util.Slog.w(TAG, "signingInfo is empty, app was either unsigned or the flag PackageManager#GET_SIGNING_CERTIFICATES was not specified");
            return false;
        }
        int size = arrayList.size();
        if (size == 1) {
            return packageManagerInternal.isDataRestoreSafe(arrayList.get(0), packageInfo.packageName);
        }
        java.util.ArrayList<byte[]> hashSignatureArray = hashSignatureArray(signingInfo.getApkContentsSigners());
        int size2 = hashSignatureArray.size();
        for (int i = 0; i < size; i++) {
            int i2 = 0;
            while (true) {
                if (i2 >= size2) {
                    z = false;
                    break;
                }
                if (!java.util.Arrays.equals(arrayList.get(i), hashSignatureArray.get(i2))) {
                    i2++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public static byte[] hashSignature(byte[] bArr) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Slog.w(TAG, "No SHA-256 algorithm found!");
            return null;
        }
    }

    public static byte[] hashSignature(android.content.pm.Signature signature) {
        return hashSignature(signature.toByteArray());
    }

    public static java.util.ArrayList<byte[]> hashSignatureArray(android.content.pm.Signature[] signatureArr) {
        if (signatureArr == null) {
            return null;
        }
        java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>(signatureArr.length);
        for (android.content.pm.Signature signature : signatureArr) {
            arrayList.add(hashSignature(signature));
        }
        return arrayList;
    }

    public static java.util.ArrayList<byte[]> hashSignatureArray(java.util.List<byte[]> list) {
        if (list == null) {
            return null;
        }
        java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>(list.size());
        java.util.Iterator<byte[]> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(hashSignature(it.next()));
        }
        return arrayList;
    }
}
