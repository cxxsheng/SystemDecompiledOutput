package com.android.server.security;

/* loaded from: classes2.dex */
class AttestationVerificationSelfTrustedVerifierForTesting {
    private static final java.lang.String ANDROID_KEYMINT_KEY_DESCRIPTION_EXTENSION_OID = "1.3.6.1.4.1.11129.2.1.17";
    private static final java.lang.String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final int ATTESTATION_CHALLENGE_INDEX = 4;
    private static final boolean DEBUG;
    private static final java.lang.String GOLDEN_ALIAS;
    private static final java.lang.String TAG = "AVF";
    private static volatile com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting sAttestationVerificationSelfTrustedVerifier;
    private java.security.cert.X509Certificate mGoldenRootCert;
    private final java.security.cert.CertificateFactory mCertificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
    private final java.security.cert.CertPathValidator mCertPathValidator = java.security.cert.CertPathValidator.getInstance("PKIX");
    private final java.security.KeyStore mAndroidKeyStore = java.security.KeyStore.getInstance("AndroidKeyStore");

    static {
        DEBUG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 2);
        GOLDEN_ALIAS = com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting.class.getCanonicalName() + ".Golden";
        sAttestationVerificationSelfTrustedVerifier = null;
    }

    static com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting getInstance() throws java.lang.Exception {
        if (sAttestationVerificationSelfTrustedVerifier == null) {
            synchronized (com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting.class) {
                try {
                    if (sAttestationVerificationSelfTrustedVerifier == null) {
                        sAttestationVerificationSelfTrustedVerifier = new com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting();
                    }
                } finally {
                }
            }
        }
        return sAttestationVerificationSelfTrustedVerifier;
    }

    private static void debugVerboseLog(java.lang.String str, java.lang.Throwable th) {
        if (DEBUG) {
            android.util.Slog.v(TAG, str, th);
        }
    }

    private static void debugVerboseLog(java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.v(TAG, str);
        }
    }

    private AttestationVerificationSelfTrustedVerifierForTesting() throws java.lang.Exception {
        this.mAndroidKeyStore.load(null);
        if (!this.mAndroidKeyStore.containsAlias(GOLDEN_ALIAS)) {
            java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("EC", "AndroidKeyStore");
            keyPairGenerator.initialize(new android.security.keystore.KeyGenParameterSpec.Builder(GOLDEN_ALIAS, 12).setAttestationChallenge(GOLDEN_ALIAS.getBytes()).setDigests("SHA-256", "SHA-512").build());
            keyPairGenerator.generateKeyPair();
        }
        this.mGoldenRootCert = ((java.security.cert.X509Certificate[]) ((java.security.KeyStore.PrivateKeyEntry) this.mAndroidKeyStore.getEntry(GOLDEN_ALIAS, null)).getCertificateChain())[r0.length - 1];
    }

    int verifyAttestation(int i, @android.annotation.NonNull android.os.Bundle bundle, @android.annotation.NonNull byte[] bArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        while (byteArrayInputStream.available() > 0) {
            try {
                arrayList.add((java.security.cert.X509Certificate) this.mCertificateFactory.generateCertificate(byteArrayInputStream));
            } catch (java.security.cert.CertificateException e) {
                debugVerboseLog("Unable to parse certificates from attestation", e);
                return 2;
            }
        }
        if (i != 3 || !validateRequirements(bundle) || !checkLeafChallenge(bundle, arrayList) || !verifyCertificateChain(arrayList)) {
            return 2;
        }
        return 1;
    }

    private boolean verifyCertificateChain(java.util.List<java.security.cert.X509Certificate> list) {
        if (list.size() < 2) {
            debugVerboseLog("Certificate chain less than 2 in size.");
            return false;
        }
        try {
            java.security.cert.CertPath generateCertPath = this.mCertificateFactory.generateCertPath(list);
            java.security.cert.PKIXParameters pKIXParameters = new java.security.cert.PKIXParameters(getTrustAnchors());
            pKIXParameters.setRevocationEnabled(false);
            this.mCertPathValidator.validate(generateCertPath, pKIXParameters);
            return true;
        } catch (java.lang.Throwable th) {
            debugVerboseLog("Invalid certificate chain", th);
            return false;
        }
    }

    private java.util.Set<java.security.cert.TrustAnchor> getTrustAnchors() {
        return java.util.Collections.singleton(new java.security.cert.TrustAnchor(this.mGoldenRootCert, null));
    }

    private boolean validateRequirements(android.os.Bundle bundle) {
        if (bundle.size() != 1) {
            debugVerboseLog("Requirements does not contain exactly 1 key.");
            return false;
        }
        if (bundle.containsKey("localbinding.challenge")) {
            return true;
        }
        debugVerboseLog("Requirements does not contain key: localbinding.challenge");
        return false;
    }

    private boolean checkLeafChallenge(android.os.Bundle bundle, java.util.List<java.security.cert.X509Certificate> list) {
        try {
            if (java.util.Arrays.equals(bundle.getByteArray("localbinding.challenge"), getChallengeFromCert(list.get(0)))) {
                return true;
            }
            debugVerboseLog("Self-Trusted validation failed; challenge mismatch.");
            return false;
        } catch (java.lang.Throwable th) {
            debugVerboseLog("Unable to parse challenge from certificate.", th);
            return false;
        }
    }

    private byte[] getChallengeFromCert(@android.annotation.NonNull java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException, java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(x509Certificate.getEncoded()).readObject()).getTBSCertificate().getExtensions().getExtensionParsedValue(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(ANDROID_KEYMINT_KEY_DESCRIPTION_EXTENSION_OID)).getObjectAt(4).getOctets();
    }
}
