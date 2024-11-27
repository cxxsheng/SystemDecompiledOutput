package com.android.server.security;

/* loaded from: classes2.dex */
class AttestationVerificationPeerDeviceVerifier {
    private static final java.lang.String ANDROID_SYSTEM_PACKAGE_NAME = "AndroidSystem";
    private static final java.util.Set<java.lang.String> ANDROID_SYSTEM_PACKAGE_NAME_SET;
    private static final boolean DEBUG;
    private static final int MAX_PATCH_AGE_MONTHS = 12;
    private static final java.lang.String PARAM_OWNED_BY_SYSTEM = "android.key_owned_by_system";
    private static final java.lang.String TAG = "AVF";
    private final java.security.cert.CertPathValidator mCertPathValidator;
    private final java.security.cert.CertificateFactory mCertificateFactory;
    private final android.content.Context mContext;
    private final boolean mRevocationEnabled;
    private final java.time.LocalDate mTestLocalPatchDate;
    private final java.time.LocalDate mTestSystemDate;
    private final java.util.Set<java.security.cert.TrustAnchor> mTrustAnchors;

    static {
        DEBUG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 2);
        ANDROID_SYSTEM_PACKAGE_NAME_SET = java.util.Collections.singleton(ANDROID_SYSTEM_PACKAGE_NAME);
    }

    AttestationVerificationPeerDeviceVerifier(@android.annotation.NonNull android.content.Context context) throws java.lang.Exception {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCertificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        this.mCertPathValidator = java.security.cert.CertPathValidator.getInstance("PKIX");
        this.mTrustAnchors = getTrustAnchors();
        this.mRevocationEnabled = true;
        this.mTestSystemDate = null;
        this.mTestLocalPatchDate = null;
    }

    @com.android.internal.annotations.VisibleForTesting
    AttestationVerificationPeerDeviceVerifier(@android.annotation.NonNull android.content.Context context, java.util.Set<java.security.cert.TrustAnchor> set, boolean z, java.time.LocalDate localDate, java.time.LocalDate localDate2) throws java.lang.Exception {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCertificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        this.mCertPathValidator = java.security.cert.CertPathValidator.getInstance("PKIX");
        this.mTrustAnchors = set;
        this.mRevocationEnabled = z;
        this.mTestSystemDate = localDate;
        this.mTestLocalPatchDate = localDate2;
    }

    int verifyAttestation(int i, @android.annotation.NonNull android.os.Bundle bundle, @android.annotation.NonNull byte[] bArr) {
        if (this.mCertificateFactory == null) {
            debugVerboseLog("Unable to access CertificateFactory");
            return 2;
        }
        if (this.mCertPathValidator == null) {
            debugVerboseLog("Unable to access CertPathValidator");
            return 2;
        }
        if (!validateAttestationParameters(i, bundle)) {
            return 2;
        }
        try {
            java.util.List<java.security.cert.X509Certificate> certificates = getCertificates(bArr);
            validateCertificateChain(certificates);
            java.security.cert.X509Certificate x509Certificate = certificates.get(0);
            com.android.server.security.AndroidKeystoreAttestationVerificationAttributes fromCertificate = com.android.server.security.AndroidKeystoreAttestationVerificationAttributes.fromCertificate(x509Certificate);
            if (checkAttestationForPeerDeviceProfile(fromCertificate)) {
                return !checkLocalBindingRequirements(x509Certificate, fromCertificate, i, bundle) ? 2 : 1;
            }
            return 2;
        } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.cert.CertPathValidatorException | java.security.cert.CertificateException e) {
            debugVerboseLog("Unable to parse/validate Android Attestation certificate(s)", e);
            return 2;
        } catch (java.lang.RuntimeException e2) {
            debugVerboseLog("Unexpected error", e2);
            return 2;
        }
    }

    @android.annotation.NonNull
    private java.util.List<java.security.cert.X509Certificate> getCertificates(byte[] bArr) throws java.security.cert.CertificateException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        while (byteArrayInputStream.available() > 0) {
            arrayList.add((java.security.cert.X509Certificate) this.mCertificateFactory.generateCertificate(byteArrayInputStream));
        }
        return arrayList;
    }

    private boolean validateAttestationParameters(int i, @android.annotation.NonNull android.os.Bundle bundle) {
        if (i != 2 && i != 3) {
            debugVerboseLog("Binding type is not supported: " + i);
            return false;
        }
        if (bundle.size() < 1) {
            debugVerboseLog("At least 1 requirement is required.");
            return false;
        }
        if (i == 2 && !bundle.containsKey("localbinding.public_key")) {
            debugVerboseLog("Requirements does not contain key: localbinding.public_key");
            return false;
        }
        if (i != 3 || bundle.containsKey("localbinding.challenge")) {
            return true;
        }
        debugVerboseLog("Requirements does not contain key: localbinding.challenge");
        return false;
    }

    private void validateCertificateChain(java.util.List<java.security.cert.X509Certificate> list) throws java.security.cert.CertificateException, java.security.cert.CertPathValidatorException, java.security.InvalidAlgorithmParameterException {
        if (list.size() < 2) {
            debugVerboseLog("Certificate chain less than 2 in size.");
            throw new java.security.cert.CertificateException("Certificate chain less than 2 in size.");
        }
        java.security.cert.CertPath generateCertPath = this.mCertificateFactory.generateCertPath(list);
        java.security.cert.PKIXParameters pKIXParameters = new java.security.cert.PKIXParameters(this.mTrustAnchors);
        if (this.mRevocationEnabled) {
            pKIXParameters.addCertPathChecker(new com.android.server.security.AttestationVerificationPeerDeviceVerifier.AndroidRevocationStatusListChecker());
        }
        pKIXParameters.setRevocationEnabled(false);
        this.mCertPathValidator.validate(generateCertPath, pKIXParameters);
    }

    private java.util.Set<java.security.cert.TrustAnchor> getTrustAnchors() throws java.security.cert.CertPathValidatorException {
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            for (java.lang.String str : getTrustAnchorResources()) {
                hashSet.add(new java.security.cert.TrustAnchor((java.security.cert.X509Certificate) this.mCertificateFactory.generateCertificate(new java.io.ByteArrayInputStream(getCertificateBytes(str))), null));
            }
            return java.util.Collections.unmodifiableSet(hashSet);
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
            throw new java.security.cert.CertPathValidatorException("Invalid trust anchor certificate.", e);
        }
    }

    private byte[] getCertificateBytes(java.lang.String str) {
        return str.replaceAll("\\s+", "\n").replaceAll("-BEGIN\\nCERTIFICATE-", "-BEGIN CERTIFICATE-").replaceAll("-END\\nCERTIFICATE-", "-END CERTIFICATE-").getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private java.lang.String[] getTrustAnchorResources() {
        return this.mContext.getResources().getStringArray(android.R.array.vendor_required_apps_managed_device);
    }

    private boolean checkLocalBindingRequirements(@android.annotation.NonNull java.security.cert.X509Certificate x509Certificate, @android.annotation.NonNull com.android.server.security.AndroidKeystoreAttestationVerificationAttributes androidKeystoreAttestationVerificationAttributes, int i, @android.annotation.NonNull android.os.Bundle bundle) {
        switch (i) {
            case 2:
                if (!checkPublicKey(x509Certificate, bundle.getByteArray("localbinding.public_key"))) {
                    debugVerboseLog("Provided public key does not match leaf certificate public key.");
                    return false;
                }
                break;
            case 3:
                if (!checkAttestationChallenge(androidKeystoreAttestationVerificationAttributes, bundle.getByteArray("localbinding.challenge"))) {
                    debugVerboseLog("Provided challenge does not match leaf certificate challenge.");
                    return false;
                }
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported local binding type " + android.security.attestationverification.AttestationVerificationManager.localBindingTypeToString(i));
        }
        if (bundle.containsKey(PARAM_OWNED_BY_SYSTEM)) {
            if (bundle.getBoolean(PARAM_OWNED_BY_SYSTEM)) {
                if (!checkOwnedBySystem(x509Certificate, androidKeystoreAttestationVerificationAttributes)) {
                    debugVerboseLog("Certificate public key is not owned by the AndroidSystem.");
                    return false;
                }
                return true;
            }
            throw new java.lang.IllegalArgumentException("The value of the requirement key android.key_owned_by_system cannot be false. You can remove the key if you don't want to verify it.");
        }
        return true;
    }

    private boolean checkAttestationForPeerDeviceProfile(@android.annotation.NonNull com.android.server.security.AndroidKeystoreAttestationVerificationAttributes androidKeystoreAttestationVerificationAttributes) {
        if (androidKeystoreAttestationVerificationAttributes.getAttestationVersion() < 3) {
            debugVerboseLog("Attestation version is not at least 3 (Keymaster 4).");
            return false;
        }
        if (androidKeystoreAttestationVerificationAttributes.getKeymasterVersion() < 4) {
            debugVerboseLog("Keymaster version is not at least 4.");
            return false;
        }
        if (androidKeystoreAttestationVerificationAttributes.getKeyOsVersion() < 100000) {
            debugVerboseLog("Android OS version is not 10+.");
            return false;
        }
        if (!androidKeystoreAttestationVerificationAttributes.isAttestationHardwareBacked()) {
            debugVerboseLog("Key is not HW backed.");
            return false;
        }
        if (!androidKeystoreAttestationVerificationAttributes.isKeymasterHardwareBacked()) {
            debugVerboseLog("Keymaster is not HW backed.");
            return false;
        }
        if (androidKeystoreAttestationVerificationAttributes.getVerifiedBootState() != com.android.server.security.AndroidKeystoreAttestationVerificationAttributes.VerifiedBootState.VERIFIED) {
            debugVerboseLog("Boot state not Verified.");
            return false;
        }
        try {
            if (!androidKeystoreAttestationVerificationAttributes.isVerifiedBootLocked()) {
                debugVerboseLog("Verified boot state is not locked.");
                return false;
            }
            if (!isValidPatchLevel(androidKeystoreAttestationVerificationAttributes.getKeyOsPatchLevel())) {
                debugVerboseLog("OS patch level is not within valid range.");
                return false;
            }
            if (!isValidPatchLevel(androidKeystoreAttestationVerificationAttributes.getKeyBootPatchLevel())) {
                debugVerboseLog("Boot patch level is not within valid range.");
                return false;
            }
            if (!isValidPatchLevel(androidKeystoreAttestationVerificationAttributes.getKeyVendorPatchLevel())) {
                debugVerboseLog("Vendor patch level is not within valid range.");
                return false;
            }
            if (!isValidPatchLevel(androidKeystoreAttestationVerificationAttributes.getKeyBootPatchLevel())) {
                debugVerboseLog("Boot patch level is not within valid range.");
                return false;
            }
            return true;
        } catch (java.lang.IllegalStateException e) {
            debugVerboseLog("VerifiedBootLocked is not set.", e);
            return false;
        }
    }

    private boolean checkPublicKey(@android.annotation.NonNull java.security.cert.Certificate certificate, @android.annotation.NonNull byte[] bArr) {
        return java.util.Arrays.equals(certificate.getPublicKey().getEncoded(), bArr);
    }

    private boolean checkAttestationChallenge(@android.annotation.NonNull com.android.server.security.AndroidKeystoreAttestationVerificationAttributes androidKeystoreAttestationVerificationAttributes, @android.annotation.NonNull byte[] bArr) {
        return java.util.Arrays.equals(androidKeystoreAttestationVerificationAttributes.getAttestationChallenge().toByteArray(), bArr);
    }

    private boolean checkOwnedBySystem(@android.annotation.NonNull java.security.cert.X509Certificate x509Certificate, @android.annotation.NonNull com.android.server.security.AndroidKeystoreAttestationVerificationAttributes androidKeystoreAttestationVerificationAttributes) {
        java.util.Set<java.lang.String> keySet = androidKeystoreAttestationVerificationAttributes.getApplicationPackageNameVersion().keySet();
        if (!ANDROID_SYSTEM_PACKAGE_NAME_SET.equals(keySet)) {
            debugVerboseLog("Owner is not system, packages=" + keySet);
            return false;
        }
        return true;
    }

    private boolean isValidPatchLevel(int i) {
        java.time.LocalDate parse;
        java.time.LocalDate now = this.mTestSystemDate != null ? this.mTestSystemDate : java.time.LocalDate.now(java.time.ZoneId.systemDefault());
        try {
            if (this.mTestLocalPatchDate != null) {
                parse = this.mTestLocalPatchDate;
            } else {
                parse = java.time.LocalDate.parse(android.os.Build.VERSION.SECURITY_PATCH);
            }
            if (java.time.temporal.ChronoUnit.MONTHS.between(parse, now) > 12) {
                return true;
            }
            java.lang.String valueOf = java.lang.String.valueOf(i);
            if (valueOf.length() != 6 && valueOf.length() != 8) {
                debugVerboseLog("Patch level is not in format YYYYMM or YYYYMMDD");
                return false;
            }
            java.time.LocalDate of = java.time.LocalDate.of(java.lang.Integer.parseInt(valueOf.substring(0, 4)), java.lang.Integer.parseInt(valueOf.substring(4, 6)), 1);
            if (of.compareTo((java.time.chrono.ChronoLocalDate) parse) > 0) {
                return java.time.temporal.ChronoUnit.MONTHS.between(parse, of) <= 12;
            }
            if (of.compareTo((java.time.chrono.ChronoLocalDate) parse) < 0) {
                return java.time.temporal.ChronoUnit.MONTHS.between(of, parse) <= 12;
            }
            return true;
        } catch (java.lang.Throwable th) {
            debugVerboseLog("Build.VERSION.SECURITY_PATCH: " + android.os.Build.VERSION.SECURITY_PATCH + " is not in format YYYY-MM-DD");
            return false;
        }
    }

    private final class AndroidRevocationStatusListChecker extends java.security.cert.PKIXCertPathChecker {
        private static final java.lang.String REASON_PROPERTY_KEY = "reason";
        private static final java.lang.String STATUS_PROPERTY_KEY = "status";
        private static final java.lang.String TOP_LEVEL_JSON_PROPERTY_KEY = "entries";
        private org.json.JSONObject mJsonStatusMap;
        private java.lang.String mStatusUrl;

        private AndroidRevocationStatusListChecker() {
        }

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public void init(boolean z) throws java.security.cert.CertPathValidatorException {
            this.mStatusUrl = getRevocationListUrl();
            if (this.mStatusUrl == null || this.mStatusUrl.isEmpty()) {
                throw new java.security.cert.CertPathValidatorException("R.string.vendor_required_attestation_revocation_list_url is empty.");
            }
            this.mJsonStatusMap = getStatusMap(this.mStatusUrl);
        }

        @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
        public boolean isForwardCheckingSupported() {
            return false;
        }

        @Override // java.security.cert.PKIXCertPathChecker
        public java.util.Set<java.lang.String> getSupportedExtensions() {
            return null;
        }

        @Override // java.security.cert.PKIXCertPathChecker
        public void check(java.security.cert.Certificate certificate, java.util.Collection<java.lang.String> collection) throws java.security.cert.CertPathValidatorException {
            java.lang.String bigInteger = ((java.security.cert.X509Certificate) certificate).getSerialNumber().toString(16);
            if (bigInteger == null) {
                throw new java.security.cert.CertPathValidatorException("Certificate serial number can not be null.");
            }
            if (this.mJsonStatusMap.has(bigInteger)) {
                try {
                    org.json.JSONObject jSONObject = this.mJsonStatusMap.getJSONObject(bigInteger);
                    throw new java.security.cert.CertPathValidatorException("Invalid certificate with serial number " + bigInteger + " has status " + jSONObject.getString(STATUS_PROPERTY_KEY) + " because reason " + jSONObject.getString("reason"));
                } catch (java.lang.Throwable th) {
                    throw new java.security.cert.CertPathValidatorException("Unable get properties for certificate with serial number " + bigInteger);
                }
            }
        }

        private org.json.JSONObject getStatusMap(java.lang.String str) throws java.security.cert.CertPathValidatorException {
            try {
                try {
                    java.io.InputStream openStream = new java.net.URL(str).openStream();
                    try {
                        org.json.JSONObject jSONObject = new org.json.JSONObject(new java.lang.String(openStream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8)).getJSONObject(TOP_LEVEL_JSON_PROPERTY_KEY);
                        openStream.close();
                        return jSONObject;
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    throw new java.security.cert.CertPathValidatorException("Unable to parse revocation status from " + this.mStatusUrl, th);
                }
            } catch (java.lang.Throwable th2) {
                throw new java.security.cert.CertPathValidatorException("Unable to get revocation status from " + this.mStatusUrl, th2);
            }
        }

        private java.lang.String getRevocationListUrl() {
            return com.android.server.security.AttestationVerificationPeerDeviceVerifier.this.mContext.getResources().getString(android.R.string.usb_accessory_notification_title);
        }
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
}
