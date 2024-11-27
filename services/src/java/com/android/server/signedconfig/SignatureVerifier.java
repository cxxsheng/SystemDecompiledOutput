package com.android.server.signedconfig;

/* loaded from: classes2.dex */
public class SignatureVerifier {
    private static final boolean DBG = false;
    private static final java.lang.String DEBUG_KEY = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEmJKs4lSn+XRhMQmMid+Zbhbu13YrU1haIhVC5296InRu1x7A8PV1ejQyisBODGgRY6pqkAHRncBCYcgg5wIIJg==";
    private static final java.lang.String PROD_KEY = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE+lky6wKyGL6lE1VrD0YTMHwb0Xwc+tzC8MvnrzVxodvTpVY/jV7V+Zktcx+pry43XPABFRXtbhTo+qykhyBA1g==";
    private static final java.lang.String TAG = "SignedConfig";
    private final java.security.PublicKey mDebugKey;
    private final com.android.server.signedconfig.SignedConfigEvent mEvent;
    private final java.security.PublicKey mProdKey;

    public SignatureVerifier(com.android.server.signedconfig.SignedConfigEvent signedConfigEvent) {
        this.mEvent = signedConfigEvent;
        this.mDebugKey = android.os.Build.IS_DEBUGGABLE ? createKey(DEBUG_KEY) : null;
        this.mProdKey = createKey(PROD_KEY);
    }

    private static java.security.PublicKey createKey(java.lang.String str) {
        try {
            try {
                return java.security.KeyFactory.getInstance("EC").generatePublic(new java.security.spec.X509EncodedKeySpec(java.util.Base64.getDecoder().decode(str)));
            } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
                android.util.Slog.e(TAG, "Failed to construct public key", e);
                return null;
            }
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, "Failed to base64 decode public key", e2);
            return null;
        }
    }

    private boolean verifyWithPublicKey(java.security.PublicKey publicKey, byte[] bArr, byte[] bArr2) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        java.security.Signature signature = java.security.Signature.getInstance("SHA256withECDSA");
        signature.initVerify(publicKey);
        signature.update(bArr);
        return signature.verify(bArr2);
    }

    public boolean verifySignature(java.lang.String str, java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        try {
            byte[] decode = java.util.Base64.getDecoder().decode(str2);
            byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            if (android.os.Build.IS_DEBUGGABLE) {
                if (this.mDebugKey == null) {
                    android.util.Slog.w(TAG, "Debuggable build, but have no debug key");
                } else if (verifyWithPublicKey(this.mDebugKey, bytes, decode)) {
                    android.util.Slog.i(TAG, "Verified config using debug key");
                    this.mEvent.verifiedWith = 1;
                    return true;
                }
            }
            if (this.mProdKey == null) {
                android.util.Slog.e(TAG, "No prod key; construction failed?");
                this.mEvent.status = 9;
                return false;
            }
            if (verifyWithPublicKey(this.mProdKey, bytes, decode)) {
                android.util.Slog.i(TAG, "Verified config using production key");
                this.mEvent.verifiedWith = 2;
                return true;
            }
            this.mEvent.status = 7;
            return false;
        } catch (java.lang.IllegalArgumentException e) {
            this.mEvent.status = 3;
            android.util.Slog.e(TAG, "Failed to base64 decode signature");
            return false;
        }
    }
}
