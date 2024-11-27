package android.net;

/* loaded from: classes2.dex */
public final class Ikev2VpnProfile extends android.net.PlatformVpnProfile {
    private static final java.lang.String ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore";
    public static final java.util.List<java.lang.String> DEFAULT_ALGORITHMS;
    private static final java.lang.String EMPTY_CERT = "";
    private static final java.lang.String MISSING_PARAM_MSG_TMPL = "Required parameter was not provided: %s";
    public static final java.lang.String PREFIX_INLINE = "INLINE:";
    public static final java.lang.String PREFIX_KEYSTORE_ALIAS = "KEYSTORE_ALIAS:";
    private static final java.lang.String TAG = android.net.Ikev2VpnProfile.class.getSimpleName();
    private final java.util.List<java.lang.String> mAllowedAlgorithms;
    private final boolean mAutomaticIpVersionSelectionEnabled;
    private final boolean mAutomaticNattKeepaliveTimerEnabled;
    private final android.net.ipsec.ike.IkeTunnelConnectionParams mIkeTunConnParams;
    private final boolean mIsBypassable;
    private final boolean mIsMetered;
    private final boolean mIsRestrictedToTestNetworks;
    private final int mMaxMtu;
    private final java.lang.String mPassword;
    private final byte[] mPresharedKey;
    private final android.net.ProxyInfo mProxyInfo;
    private final java.security.PrivateKey mRsaPrivateKey;
    private final java.lang.String mServerAddr;
    private final java.security.cert.X509Certificate mServerRootCaCert;
    private final java.security.cert.X509Certificate mUserCert;
    private final java.lang.String mUserIdentity;
    private final java.lang.String mUsername;

    static {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        addAlgorithmIfSupported(arrayList, "cbc(aes)");
        addAlgorithmIfSupported(arrayList, "rfc3686(ctr(aes))");
        addAlgorithmIfSupported(arrayList, "hmac(sha256)");
        addAlgorithmIfSupported(arrayList, "hmac(sha384)");
        addAlgorithmIfSupported(arrayList, "hmac(sha512)");
        addAlgorithmIfSupported(arrayList, "xcbc(aes)");
        addAlgorithmIfSupported(arrayList, "cmac(aes)");
        addAlgorithmIfSupported(arrayList, "rfc4106(gcm(aes))");
        addAlgorithmIfSupported(arrayList, "rfc7539esp(chacha20,poly1305)");
        DEFAULT_ALGORITHMS = java.util.Collections.unmodifiableList(arrayList);
    }

    private static void addAlgorithmIfSupported(java.util.List<java.lang.String> list, java.lang.String str) {
        if (android.net.IpSecAlgorithm.getSupportedAlgorithms().contains(str)) {
            list.add(str);
        }
    }

    private Ikev2VpnProfile(int i, java.lang.String str, java.lang.String str2, byte[] bArr, java.security.cert.X509Certificate x509Certificate, java.lang.String str3, java.lang.String str4, java.security.PrivateKey privateKey, java.security.cert.X509Certificate x509Certificate2, android.net.ProxyInfo proxyInfo, java.util.List<java.lang.String> list, boolean z, boolean z2, int i2, boolean z3, boolean z4, boolean z5, android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams, boolean z6, boolean z7) {
        super(i, z4, z5);
        checkNotNull(list, MISSING_PARAM_MSG_TMPL, "Allowed Algorithms");
        this.mServerAddr = str;
        this.mUserIdentity = str2;
        this.mPresharedKey = bArr == null ? null : java.util.Arrays.copyOf(bArr, bArr.length);
        this.mServerRootCaCert = x509Certificate;
        this.mUsername = str3;
        this.mPassword = str4;
        this.mRsaPrivateKey = privateKey;
        this.mUserCert = x509Certificate2;
        this.mProxyInfo = proxyInfo != null ? new android.net.ProxyInfo(proxyInfo) : null;
        this.mAllowedAlgorithms = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        if (z4 && !z) {
            throw new java.lang.IllegalArgumentException("Vpn must be bypassable if excludeLocalRoutes is set");
        }
        this.mIsBypassable = z;
        this.mIsMetered = z2;
        this.mMaxMtu = i2;
        this.mIsRestrictedToTestNetworks = z3;
        this.mIkeTunConnParams = ikeTunnelConnectionParams;
        this.mAutomaticNattKeepaliveTimerEnabled = z6;
        this.mAutomaticIpVersionSelectionEnabled = z7;
        validate();
    }

    private void validate() {
        if (this.mMaxMtu < 1280) {
            throw new java.lang.IllegalArgumentException("Max MTU must be at least1280");
        }
        if (this.mIkeTunConnParams != null) {
            return;
        }
        com.android.internal.util.Preconditions.checkStringNotEmpty(this.mServerAddr, MISSING_PARAM_MSG_TMPL, "Server Address");
        com.android.internal.util.Preconditions.checkStringNotEmpty(this.mUserIdentity, MISSING_PARAM_MSG_TMPL, "User Identity");
        switch (this.mType) {
            case 6:
                checkNotNull(this.mUsername, MISSING_PARAM_MSG_TMPL, "Username");
                checkNotNull(this.mPassword, MISSING_PARAM_MSG_TMPL, "Password");
                if (this.mServerRootCaCert != null) {
                    checkCert(this.mServerRootCaCert);
                    break;
                }
                break;
            case 7:
                checkNotNull(this.mPresharedKey, MISSING_PARAM_MSG_TMPL, "Preshared Key");
                break;
            case 8:
                checkNotNull(this.mUserCert, MISSING_PARAM_MSG_TMPL, "User cert");
                checkNotNull(this.mRsaPrivateKey, MISSING_PARAM_MSG_TMPL, "RSA Private key");
                checkCert(this.mUserCert);
                if (this.mServerRootCaCert != null) {
                    checkCert(this.mServerRootCaCert);
                    break;
                }
                break;
            default:
                throw new java.lang.IllegalArgumentException("Invalid auth method set");
        }
        validateAllowedAlgorithms(this.mAllowedAlgorithms);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateAllowedAlgorithms(java.util.List<java.lang.String> list) {
        if (list.contains("hmac(md5)") || list.contains("hmac(sha1)")) {
            throw new java.lang.IllegalArgumentException("Algorithm not supported for IKEv2 VPN profiles");
        }
        if (hasAeadAlgorithms(list) || hasNormalModeAlgorithms(list)) {
        } else {
            throw new java.lang.IllegalArgumentException("Algorithm set missing support for Auth, Crypt or both");
        }
    }

    public static boolean hasAeadAlgorithms(java.util.List<java.lang.String> list) {
        return list.contains("rfc4106(gcm(aes))");
    }

    public static boolean hasNormalModeAlgorithms(java.util.List<java.lang.String> list) {
        return list.contains("cbc(aes)") && (list.contains("hmac(sha256)") || list.contains("hmac(sha384)") || list.contains("hmac(sha512)"));
    }

    public java.lang.String getServerAddr() {
        return this.mIkeTunConnParams == null ? this.mServerAddr : this.mIkeTunConnParams.getIkeSessionParams().getServerHostname();
    }

    public java.lang.String getUserIdentity() {
        return this.mIkeTunConnParams == null ? this.mUserIdentity : getUserIdentityFromIkeSession(this.mIkeTunConnParams.getIkeSessionParams());
    }

    public byte[] getPresharedKey() {
        if (this.mIkeTunConnParams == null && this.mPresharedKey != null) {
            return java.util.Arrays.copyOf(this.mPresharedKey, this.mPresharedKey.length);
        }
        return null;
    }

    public java.security.cert.X509Certificate getServerRootCaCert() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mServerRootCaCert;
    }

    public java.lang.String getUsername() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mUsername;
    }

    public java.lang.String getPassword() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mPassword;
    }

    public java.security.PrivateKey getRsaPrivateKey() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mRsaPrivateKey;
    }

    public java.security.cert.X509Certificate getUserCert() {
        if (this.mIkeTunConnParams != null) {
            return null;
        }
        return this.mUserCert;
    }

    public android.net.ProxyInfo getProxyInfo() {
        return this.mProxyInfo;
    }

    public java.util.List<java.lang.String> getAllowedAlgorithms() {
        return this.mIkeTunConnParams != null ? new java.util.ArrayList() : this.mAllowedAlgorithms;
    }

    public boolean isBypassable() {
        return this.mIsBypassable;
    }

    public boolean isMetered() {
        return this.mIsMetered;
    }

    public int getMaxMtu() {
        return this.mMaxMtu;
    }

    public android.net.ipsec.ike.IkeTunnelConnectionParams getIkeTunnelConnectionParams() {
        return this.mIkeTunConnParams;
    }

    public boolean isRestrictedToTestNetworks() {
        return this.mIsRestrictedToTestNetworks;
    }

    public boolean isAutomaticNattKeepaliveTimerEnabled() {
        return this.mAutomaticNattKeepaliveTimerEnabled;
    }

    public boolean isAutomaticIpVersionSelectionEnabled() {
        return this.mAutomaticIpVersionSelectionEnabled;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), this.mServerAddr, this.mUserIdentity, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mPresharedKey)), this.mServerRootCaCert, this.mUsername, this.mPassword, this.mRsaPrivateKey, this.mUserCert, this.mProxyInfo, this.mAllowedAlgorithms, java.lang.Boolean.valueOf(this.mIsBypassable), java.lang.Boolean.valueOf(this.mIsMetered), java.lang.Integer.valueOf(this.mMaxMtu), java.lang.Boolean.valueOf(this.mIsRestrictedToTestNetworks), java.lang.Boolean.valueOf(this.mExcludeLocalRoutes), java.lang.Boolean.valueOf(this.mRequiresInternetValidation), this.mIkeTunConnParams, java.lang.Boolean.valueOf(this.mAutomaticNattKeepaliveTimerEnabled), java.lang.Boolean.valueOf(this.mAutomaticIpVersionSelectionEnabled));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.Ikev2VpnProfile)) {
            return false;
        }
        android.net.Ikev2VpnProfile ikev2VpnProfile = (android.net.Ikev2VpnProfile) obj;
        return this.mType == ikev2VpnProfile.mType && java.util.Objects.equals(this.mServerAddr, ikev2VpnProfile.mServerAddr) && java.util.Objects.equals(this.mUserIdentity, ikev2VpnProfile.mUserIdentity) && java.util.Arrays.equals(this.mPresharedKey, ikev2VpnProfile.mPresharedKey) && java.util.Objects.equals(this.mServerRootCaCert, ikev2VpnProfile.mServerRootCaCert) && java.util.Objects.equals(this.mUsername, ikev2VpnProfile.mUsername) && java.util.Objects.equals(this.mPassword, ikev2VpnProfile.mPassword) && java.util.Objects.equals(this.mRsaPrivateKey, ikev2VpnProfile.mRsaPrivateKey) && java.util.Objects.equals(this.mUserCert, ikev2VpnProfile.mUserCert) && java.util.Objects.equals(this.mProxyInfo, ikev2VpnProfile.mProxyInfo) && java.util.Objects.equals(this.mAllowedAlgorithms, ikev2VpnProfile.mAllowedAlgorithms) && this.mIsBypassable == ikev2VpnProfile.mIsBypassable && this.mIsMetered == ikev2VpnProfile.mIsMetered && this.mMaxMtu == ikev2VpnProfile.mMaxMtu && this.mIsRestrictedToTestNetworks == ikev2VpnProfile.mIsRestrictedToTestNetworks && this.mExcludeLocalRoutes == ikev2VpnProfile.mExcludeLocalRoutes && this.mRequiresInternetValidation == ikev2VpnProfile.mRequiresInternetValidation && java.util.Objects.equals(this.mIkeTunConnParams, ikev2VpnProfile.mIkeTunConnParams) && this.mAutomaticNattKeepaliveTimerEnabled == ikev2VpnProfile.mAutomaticNattKeepaliveTimerEnabled && this.mAutomaticIpVersionSelectionEnabled == ikev2VpnProfile.mAutomaticIpVersionSelectionEnabled;
    }

    @Override // android.net.PlatformVpnProfile
    public com.android.internal.net.VpnProfile toVpnProfile() throws java.io.IOException, java.security.GeneralSecurityException {
        com.android.internal.net.VpnProfile vpnProfile = new com.android.internal.net.VpnProfile("", this.mIsRestrictedToTestNetworks, this.mExcludeLocalRoutes, this.mRequiresInternetValidation, this.mIkeTunConnParams, this.mAutomaticNattKeepaliveTimerEnabled, this.mAutomaticIpVersionSelectionEnabled);
        vpnProfile.proxy = this.mProxyInfo;
        vpnProfile.isBypassable = this.mIsBypassable;
        vpnProfile.isMetered = this.mIsMetered;
        vpnProfile.maxMtu = this.mMaxMtu;
        vpnProfile.areAuthParamsInline = true;
        vpnProfile.saveLogin = true;
        if (this.mIkeTunConnParams != null) {
            vpnProfile.type = 9;
            return vpnProfile;
        }
        vpnProfile.type = this.mType;
        vpnProfile.server = getServerAddr();
        vpnProfile.ipsecIdentifier = getUserIdentity();
        vpnProfile.setAllowedAlgorithms(this.mAllowedAlgorithms);
        switch (this.mType) {
            case 6:
                vpnProfile.username = this.mUsername;
                vpnProfile.password = this.mPassword;
                vpnProfile.ipsecCaCert = this.mServerRootCaCert != null ? certificateToPemString(this.mServerRootCaCert) : "";
                return vpnProfile;
            case 7:
                vpnProfile.ipsecSecret = encodeForIpsecSecret(this.mPresharedKey);
                return vpnProfile;
            case 8:
                vpnProfile.ipsecUserCert = certificateToPemString(this.mUserCert);
                vpnProfile.ipsecSecret = PREFIX_INLINE + encodeForIpsecSecret(this.mRsaPrivateKey.getEncoded());
                vpnProfile.ipsecCaCert = this.mServerRootCaCert != null ? certificateToPemString(this.mServerRootCaCert) : "";
                return vpnProfile;
            default:
                throw new java.lang.IllegalArgumentException("Invalid auth method set");
        }
    }

    private static java.security.PrivateKey getPrivateKeyFromAndroidKeystore(java.lang.String str) {
        try {
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            java.security.Key key = keyStore.getKey(str, null);
            if (!(key instanceof java.security.PrivateKey)) {
                throw new java.lang.IllegalStateException("Unexpected key type returned from android keystore.");
            }
            return (java.security.PrivateKey) key;
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalStateException("Failed to load key from android keystore.", e);
        }
    }

    public static android.net.Ikev2VpnProfile fromVpnProfile(com.android.internal.net.VpnProfile vpnProfile) throws java.security.GeneralSecurityException {
        android.net.Ikev2VpnProfile.Builder builder;
        java.security.PrivateKey privateKey;
        if (vpnProfile.ikeTunConnParams == null) {
            builder = new android.net.Ikev2VpnProfile.Builder(vpnProfile.server, vpnProfile.ipsecIdentifier);
            builder.setAllowedAlgorithms(vpnProfile.getAllowedAlgorithms());
            switch (vpnProfile.type) {
                case 6:
                    builder.setAuthUsernamePassword(vpnProfile.username, vpnProfile.password, certificateFromPemString(vpnProfile.ipsecCaCert));
                    break;
                case 7:
                    builder.setAuthPsk(decodeFromIpsecSecret(vpnProfile.ipsecSecret));
                    break;
                case 8:
                    if (vpnProfile.ipsecSecret.startsWith(PREFIX_KEYSTORE_ALIAS)) {
                        privateKey = getPrivateKeyFromAndroidKeystore(vpnProfile.ipsecSecret.substring(PREFIX_KEYSTORE_ALIAS.length()));
                    } else if (vpnProfile.ipsecSecret.startsWith(PREFIX_INLINE)) {
                        privateKey = getPrivateKey(vpnProfile.ipsecSecret.substring(PREFIX_INLINE.length()));
                    } else {
                        throw new java.lang.IllegalArgumentException("Invalid RSA private key prefix");
                    }
                    builder.setAuthDigitalSignature(certificateFromPemString(vpnProfile.ipsecUserCert), privateKey, certificateFromPemString(vpnProfile.ipsecCaCert));
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid auth method set");
            }
        } else {
            builder = new android.net.Ikev2VpnProfile.Builder(vpnProfile.ikeTunConnParams);
        }
        builder.setProxy(vpnProfile.proxy);
        builder.setBypassable(vpnProfile.isBypassable);
        builder.setMetered(vpnProfile.isMetered);
        builder.setMaxMtu(vpnProfile.maxMtu);
        if (vpnProfile.isRestrictedToTestNetworks) {
            builder.restrictToTestNetworks();
        }
        if (vpnProfile.excludeLocalRoutes && !vpnProfile.isBypassable) {
            android.util.Log.w(TAG, "ExcludeLocalRoutes should only be set in the bypassable VPN");
        }
        builder.setLocalRoutesExcluded(vpnProfile.excludeLocalRoutes && vpnProfile.isBypassable);
        builder.setRequiresInternetValidation(vpnProfile.requiresInternetValidation);
        builder.setAutomaticNattKeepaliveTimerEnabled(vpnProfile.automaticNattKeepaliveTimerEnabled);
        builder.setAutomaticIpVersionSelectionEnabled(vpnProfile.automaticIpVersionSelectionEnabled);
        return builder.build();
    }

    public static boolean isValidVpnProfile(com.android.internal.net.VpnProfile vpnProfile) {
        if (vpnProfile.server.isEmpty() || vpnProfile.ipsecIdentifier.isEmpty()) {
            return false;
        }
        switch (vpnProfile.type) {
            case 6:
                return (vpnProfile.username.isEmpty() || vpnProfile.password.isEmpty()) ? false : true;
            case 7:
                return !vpnProfile.ipsecSecret.isEmpty();
            case 8:
                return (vpnProfile.ipsecSecret.isEmpty() || vpnProfile.ipsecUserCert.isEmpty()) ? false : true;
            default:
                return false;
        }
    }

    public static java.lang.String certificateToPemString(java.security.cert.X509Certificate x509Certificate) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        if (x509Certificate == null) {
            return "";
        }
        return new java.lang.String(android.security.Credentials.convertToPem(x509Certificate), java.nio.charset.StandardCharsets.US_ASCII);
    }

    private static java.security.cert.X509Certificate certificateFromPemString(java.lang.String str) throws java.security.cert.CertificateException {
        if (str == null || "".equals(str)) {
            return null;
        }
        try {
            java.util.List<java.security.cert.X509Certificate> convertFromPem = android.security.Credentials.convertFromPem(str.getBytes(java.nio.charset.StandardCharsets.US_ASCII));
            return convertFromPem.isEmpty() ? null : convertFromPem.get(0);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateException(e);
        }
    }

    public static java.lang.String encodeForIpsecSecret(byte[] bArr) {
        checkNotNull(bArr, MISSING_PARAM_MSG_TMPL, "secret");
        return java.util.Base64.getEncoder().encodeToString(bArr);
    }

    private static byte[] decodeFromIpsecSecret(java.lang.String str) {
        checkNotNull(str, MISSING_PARAM_MSG_TMPL, "encoded");
        return java.util.Base64.getDecoder().decode(str);
    }

    private static java.security.PrivateKey getPrivateKey(java.lang.String str) throws java.security.spec.InvalidKeySpecException, java.security.NoSuchAlgorithmException {
        return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA).generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(decodeFromIpsecSecret(str)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkCert(java.security.cert.X509Certificate x509Certificate) {
        try {
            certificateToPemString(x509Certificate);
        } catch (java.io.IOException | java.security.GeneralSecurityException e) {
            throw new java.lang.IllegalArgumentException("Certificate could not be encoded");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T checkNotNull(T t, java.lang.String str, java.lang.Object... objArr) {
        return (T) java.util.Objects.requireNonNull(t, java.lang.String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkBuilderSetter(boolean z, java.lang.String str) {
        if (z) {
            throw new java.lang.IllegalArgumentException(str + " can't be set with IkeTunnelConnectionParams builder");
        }
    }

    private static java.lang.String getUserIdentityFromIkeSession(android.net.ipsec.ike.IkeSessionParams ikeSessionParams) {
        android.net.ipsec.ike.IkeIdentification localIdentification = ikeSessionParams.getLocalIdentification();
        if (localIdentification instanceof android.net.ipsec.ike.IkeKeyIdIdentification) {
            return "@#" + new java.lang.String(((android.net.ipsec.ike.IkeKeyIdIdentification) localIdentification).keyId);
        }
        if (localIdentification instanceof android.net.ipsec.ike.IkeRfc822AddrIdentification) {
            return "@@" + ((android.net.ipsec.ike.IkeRfc822AddrIdentification) localIdentification).rfc822Name;
        }
        if (localIdentification instanceof android.net.ipsec.ike.IkeFqdnIdentification) {
            return "@" + ((android.net.ipsec.ike.IkeFqdnIdentification) localIdentification).fqdn;
        }
        if (localIdentification instanceof android.net.ipsec.ike.IkeIpv4AddrIdentification) {
            return ((android.net.ipsec.ike.IkeIpv4AddrIdentification) localIdentification).ipv4Address.getHostAddress();
        }
        if (localIdentification instanceof android.net.ipsec.ike.IkeIpv6AddrIdentification) {
            return ((android.net.ipsec.ike.IkeIpv6AddrIdentification) localIdentification).ipv6Address.getHostAddress();
        }
        if (localIdentification instanceof android.net.ipsec.ike.IkeDerAsn1DnIdentification) {
            throw new java.lang.IllegalArgumentException("Unspported ASN.1 encoded identities");
        }
        throw new java.lang.IllegalArgumentException("Unknown IkeIdentification to get user identity");
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("IkeV2VpnProfile [");
        sb.append(" MaxMtu=" + this.mMaxMtu);
        if (this.mIsBypassable) {
            sb.append(" Bypassable");
        }
        if (this.mRequiresInternetValidation) {
            sb.append(" RequiresInternetValidation");
        }
        if (this.mIsRestrictedToTestNetworks) {
            sb.append(" RestrictedToTestNetworks");
        }
        if (this.mAutomaticNattKeepaliveTimerEnabled) {
            sb.append(" AutomaticNattKeepaliveTimerEnabled");
        }
        if (this.mAutomaticIpVersionSelectionEnabled) {
            sb.append(" AutomaticIpVersionSelectionEnabled");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static final class Builder {
        private final android.net.ipsec.ike.IkeTunnelConnectionParams mIkeTunConnParams;
        private java.lang.String mPassword;
        private byte[] mPresharedKey;
        private android.net.ProxyInfo mProxyInfo;
        private java.security.PrivateKey mRsaPrivateKey;
        private final java.lang.String mServerAddr;
        private java.security.cert.X509Certificate mServerRootCaCert;
        private java.security.cert.X509Certificate mUserCert;
        private final java.lang.String mUserIdentity;
        private java.lang.String mUsername;
        private int mType = -1;
        private java.util.List<java.lang.String> mAllowedAlgorithms = android.net.Ikev2VpnProfile.DEFAULT_ALGORITHMS;
        private boolean mRequiresInternetValidation = false;
        private boolean mIsBypassable = false;
        private boolean mIsMetered = true;
        private int mMaxMtu = 1360;
        private boolean mIsRestrictedToTestNetworks = false;
        private boolean mExcludeLocalRoutes = false;
        private boolean mAutomaticNattKeepaliveTimerEnabled = false;
        private boolean mAutomaticIpVersionSelectionEnabled = false;

        public Builder(java.lang.String str, java.lang.String str2) {
            android.net.Ikev2VpnProfile.checkNotNull(str, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "serverAddr");
            android.net.Ikev2VpnProfile.checkNotNull(str2, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "identity");
            this.mServerAddr = str;
            this.mUserIdentity = str2;
            this.mIkeTunConnParams = null;
        }

        public Builder(android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams) {
            android.net.Ikev2VpnProfile.checkNotNull(ikeTunnelConnectionParams, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "ikeTunConnParams");
            this.mIkeTunConnParams = ikeTunnelConnectionParams;
            this.mServerAddr = null;
            this.mUserIdentity = null;
        }

        private void resetAuthParams() {
            this.mPresharedKey = null;
            this.mServerRootCaCert = null;
            this.mUsername = null;
            this.mPassword = null;
            this.mRsaPrivateKey = null;
            this.mUserCert = null;
        }

        public android.net.Ikev2VpnProfile.Builder setAuthUsernamePassword(java.lang.String str, java.lang.String str2, java.security.cert.X509Certificate x509Certificate) {
            android.net.Ikev2VpnProfile.checkNotNull(str, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "user");
            android.net.Ikev2VpnProfile.checkNotNull(str2, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "pass");
            android.net.Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authUsernamePassword");
            if (x509Certificate != null) {
                android.net.Ikev2VpnProfile.checkCert(x509Certificate);
            }
            resetAuthParams();
            this.mUsername = str;
            this.mPassword = str2;
            this.mServerRootCaCert = x509Certificate;
            this.mType = 6;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setAuthDigitalSignature(java.security.cert.X509Certificate x509Certificate, java.security.PrivateKey privateKey, java.security.cert.X509Certificate x509Certificate2) {
            android.net.Ikev2VpnProfile.checkNotNull(x509Certificate, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "userCert");
            android.net.Ikev2VpnProfile.checkNotNull(privateKey, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "key");
            android.net.Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authDigitalSignature");
            android.net.Ikev2VpnProfile.checkCert(x509Certificate);
            if (x509Certificate2 != null) {
                android.net.Ikev2VpnProfile.checkCert(x509Certificate2);
            }
            resetAuthParams();
            this.mUserCert = x509Certificate;
            this.mRsaPrivateKey = privateKey;
            this.mServerRootCaCert = x509Certificate2;
            this.mType = 8;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setAuthPsk(byte[] bArr) {
            android.net.Ikev2VpnProfile.checkNotNull(bArr, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "psk");
            android.net.Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "authPsk");
            resetAuthParams();
            this.mPresharedKey = bArr;
            this.mType = 7;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setBypassable(boolean z) {
            this.mIsBypassable = z;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setProxy(android.net.ProxyInfo proxyInfo) {
            this.mProxyInfo = proxyInfo;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setMaxMtu(int i) {
            if (i < 1280) {
                throw new java.lang.IllegalArgumentException("Max MTU must be at least 1280");
            }
            this.mMaxMtu = i;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setRequiresInternetValidation(boolean z) {
            this.mRequiresInternetValidation = z;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setMetered(boolean z) {
            this.mIsMetered = z;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setAllowedAlgorithms(java.util.List<java.lang.String> list) {
            android.net.Ikev2VpnProfile.checkNotNull(list, android.net.Ikev2VpnProfile.MISSING_PARAM_MSG_TMPL, "algorithmNames");
            android.net.Ikev2VpnProfile.checkBuilderSetter(this.mIkeTunConnParams != null, "algorithmNames");
            android.net.Ikev2VpnProfile.validateAllowedAlgorithms(list);
            this.mAllowedAlgorithms = list;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder restrictToTestNetworks() {
            this.mIsRestrictedToTestNetworks = true;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setAutomaticNattKeepaliveTimerEnabled(boolean z) {
            this.mAutomaticNattKeepaliveTimerEnabled = z;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setAutomaticIpVersionSelectionEnabled(boolean z) {
            this.mAutomaticIpVersionSelectionEnabled = z;
            return this;
        }

        public android.net.Ikev2VpnProfile.Builder setLocalRoutesExcluded(boolean z) {
            this.mExcludeLocalRoutes = z;
            return this;
        }

        public android.net.Ikev2VpnProfile build() {
            return new android.net.Ikev2VpnProfile(this.mType, this.mServerAddr, this.mUserIdentity, this.mPresharedKey, this.mServerRootCaCert, this.mUsername, this.mPassword, this.mRsaPrivateKey, this.mUserCert, this.mProxyInfo, this.mAllowedAlgorithms, this.mIsBypassable, this.mIsMetered, this.mMaxMtu, this.mIsRestrictedToTestNetworks, this.mExcludeLocalRoutes, this.mRequiresInternetValidation, this.mIkeTunConnParams, this.mAutomaticNattKeepaliveTimerEnabled, this.mAutomaticIpVersionSelectionEnabled);
        }
    }
}
