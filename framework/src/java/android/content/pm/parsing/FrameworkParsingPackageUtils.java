package android.content.pm.parsing;

/* loaded from: classes.dex */
public class FrameworkParsingPackageUtils {
    private static final int MAX_FILE_NAME_SIZE = 223;
    public static final int PARSE_APK_IN_APEX = 512;
    public static final int PARSE_IGNORE_OVERLAY_REQUIRED_SYSTEM_PROPERTY = 128;
    private static final java.lang.String TAG = "FrameworkParsingPackageUtils";

    public static java.lang.String validateName(java.lang.String str, boolean z, boolean z2) {
        int length = str.length();
        boolean z3 = false;
        boolean z4 = true;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                z4 = false;
            } else if (z4 || ((charAt < '0' || charAt > '9') && charAt != '_')) {
                if (charAt == '.') {
                    z3 = true;
                    z4 = true;
                } else {
                    return "bad character '" + charAt + "'";
                }
            }
        }
        if (z2) {
            if (!android.os.FileUtils.isValidExtFilename(str)) {
                return "Invalid filename";
            }
            if (length > 223) {
                return "the length of the name is greater than 223";
            }
        }
        if (z3 || !z) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    public static android.content.pm.parsing.result.ParseResult validateName(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z, boolean z2) {
        java.lang.String validateName = validateName(str, z, z2);
        if (validateName != null) {
            return parseInput.error(validateName);
        }
        return parseInput.success(null);
    }

    public static java.security.PublicKey parsePublicKey(java.lang.String str) {
        if (str == null) {
            android.util.Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            return parsePublicKey(android.util.Base64.decode(str, 0));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static java.security.PublicKey parsePublicKey(byte[] bArr) {
        if (bArr == null) {
            android.util.Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            java.security.spec.X509EncodedKeySpec x509EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(bArr);
            try {
                return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA).generatePublic(x509EncodedKeySpec);
            } catch (java.security.NoSuchAlgorithmException e) {
                android.util.Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
                } catch (java.security.NoSuchAlgorithmException e2) {
                    android.util.Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        return java.security.KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                    } catch (java.security.NoSuchAlgorithmException e3) {
                        android.util.Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (java.security.spec.InvalidKeySpecException e4) {
                        return null;
                    }
                } catch (java.security.spec.InvalidKeySpecException e5) {
                    return java.security.KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                }
            } catch (java.security.spec.InvalidKeySpecException e6) {
                return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
            }
        } catch (java.lang.IllegalArgumentException e7) {
            android.util.Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static boolean checkRequiredSystemProperties(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) {
            if (android.text.TextUtils.isEmpty(str) && android.text.TextUtils.isEmpty(str2)) {
                return true;
            }
            android.util.Slog.w(TAG, "Disabling overlay - incomplete property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
        java.lang.String[] split = str.split(",");
        java.lang.String[] split2 = str2.split(",");
        if (split.length != split2.length) {
            android.util.Slog.w(TAG, "Disabling overlay - property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue lists to have the same size.");
            return false;
        }
        for (int i = 0; i < split.length; i++) {
            java.lang.String str3 = android.os.SystemProperties.get(split[i]);
            if (str3 == null) {
                return false;
            }
            if (!"*".equals(split2[i]) && !str3.equals(split2[i])) {
                return false;
            }
        }
        return true;
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> getSigningDetails(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z, boolean z2, android.content.pm.SigningDetails signingDetails, int i) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> verify;
        int minimumSignatureSchemeVersionForTargetSdk = android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
        if (z2) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        if (z) {
            verify = android.util.apk.ApkSignatureVerifier.unsafeGetCertsWithoutVerification(parseInput, str, 1);
        } else {
            verify = android.util.apk.ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (verify.isError()) {
            return parseInput.error(verify);
        }
        if (signingDetails == android.content.pm.SigningDetails.UNKNOWN) {
            return verify;
        }
        if (!android.content.pm.Signature.areExactMatch(signingDetails, verify.getResult())) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, str + " has mismatched certificates");
        }
        return parseInput.success(signingDetails);
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeMinSdkVersion(int i, java.lang.String str, int i2, java.lang.String[] strArr, android.content.pm.parsing.result.ParseInput parseInput) {
        if (str == null) {
            if (i > i2) {
                return parseInput.error(-12, "Requires newer sdk version #" + i + " (current version is #" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            return parseInput.success(java.lang.Integer.valueOf(i));
        }
        if (matchTargetCode(strArr, str)) {
            return parseInput.success(10000);
        }
        if (strArr.length > 0) {
            return parseInput.error(-12, "Requires development platform " + str + " (current platform is any of " + java.util.Arrays.toString(strArr) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return parseInput.error(-12, "Requires development platform " + str + " but this is a release platform.");
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeTargetSdkVersion(int i, java.lang.String str, java.lang.String[] strArr, android.content.pm.parsing.result.ParseInput parseInput, boolean z) {
        if (str == null) {
            return parseInput.success(java.lang.Integer.valueOf(i));
        }
        if (z) {
            try {
                if (android.internal.modules.utils.build.UnboundedSdkLevel.isAtMost(str)) {
                    return parseInput.success(10000);
                }
            } catch (java.lang.IllegalArgumentException e) {
                return parseInput.error(-12, e.getMessage());
            }
        }
        if (matchTargetCode(strArr, str)) {
            return parseInput.success(10000);
        }
        if (strArr.length > 0) {
            return parseInput.error(-12, "Requires development platform " + str + " (current platform is any of " + java.util.Arrays.toString(strArr) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return parseInput.error(-12, "Requires development platform " + str + " but this is a release platform.");
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeMaxSdkVersion(int i, int i2, android.content.pm.parsing.result.ParseInput parseInput) {
        if (i2 > i) {
            return parseInput.error(-14, "Requires max SDK version " + i + " but is " + i2);
        }
        return parseInput.success(java.lang.Integer.valueOf(i));
    }

    private static boolean matchTargetCode(java.lang.String[] strArr, java.lang.String str) {
        int indexOf = str.indexOf(46);
        if (indexOf != -1) {
            str = str.substring(0, indexOf);
        }
        return com.android.internal.util.ArrayUtils.contains(strArr, str);
    }
}
