package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class TestOnlyInsecureCertificateHelper {
    private static final java.lang.String TAG = "TestCertHelper";

    @android.annotation.NonNull
    public java.security.cert.X509Certificate getRootCertificate(java.lang.String str) throws android.os.RemoteException {
        java.lang.String defaultCertificateAliasIfEmpty = getDefaultCertificateAliasIfEmpty(str);
        if (isTestOnlyCertificateAlias(defaultCertificateAliasIfEmpty)) {
            return android.security.keystore.recovery.TrustedRootCertificates.getTestOnlyInsecureCertificate();
        }
        java.security.cert.X509Certificate rootCertificate = android.security.keystore.recovery.TrustedRootCertificates.getRootCertificate(defaultCertificateAliasIfEmpty);
        if (rootCertificate == null) {
            throw new android.os.ServiceSpecificException(28, "The provided root certificate alias is invalid");
        }
        return rootCertificate;
    }

    @android.annotation.Nullable
    public java.util.Date getValidationDate(java.lang.String str) {
        if (isTestOnlyCertificateAlias(str)) {
            return new java.util.Date(119, 1, 30);
        }
        return null;
    }

    @android.annotation.NonNull
    public java.lang.String getDefaultCertificateAliasIfEmpty(@android.annotation.Nullable java.lang.String str) {
        if (str == null || str.isEmpty()) {
            android.util.Log.e(TAG, "rootCertificateAlias is null or empty - use secure default value");
            return "GoogleCloudKeyVaultServiceV1";
        }
        return str;
    }

    public boolean isTestOnlyCertificateAlias(java.lang.String str) {
        return "TEST_ONLY_INSECURE_CERTIFICATE_ALIAS".equals(str);
    }

    public boolean isValidRootCertificateAlias(java.lang.String str) {
        return android.security.keystore.recovery.TrustedRootCertificates.getRootCertificates().containsKey(str) || isTestOnlyCertificateAlias(str);
    }

    public boolean doesCredentialSupportInsecureMode(int i, byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        if (i != 4 && i != 3) {
            return false;
        }
        byte[] bytes = "INSECURE_PSWD_".getBytes();
        if (bArr.length < bytes.length) {
            return false;
        }
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if (bArr[i2] != bytes[i2]) {
                return false;
            }
        }
        return true;
    }

    public java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> keepOnlyWhitelistedInsecureKeys(java.util.Map<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> map) {
        if (map == null) {
            return null;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.String, android.util.Pair<javax.crypto.SecretKey, byte[]>> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            if (key != null && key.startsWith("INSECURE_KEY_ALIAS_KEY_MATERIAL_IS_NOT_PROTECTED_")) {
                hashMap.put(entry.getKey(), android.util.Pair.create((javax.crypto.SecretKey) entry.getValue().first, (byte[]) entry.getValue().second));
                android.util.Log.d(TAG, "adding key with insecure alias " + key + " to the recovery snapshot");
            }
        }
        return hashMap;
    }
}
