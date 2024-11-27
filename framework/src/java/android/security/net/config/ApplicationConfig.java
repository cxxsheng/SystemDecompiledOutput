package android.security.net.config;

/* loaded from: classes3.dex */
public final class ApplicationConfig {
    private static android.security.net.config.ApplicationConfig sInstance;
    private static java.lang.Object sLock = new java.lang.Object();
    private android.security.net.config.ConfigSource mConfigSource;
    private java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> mConfigs;
    private android.security.net.config.NetworkSecurityConfig mDefaultConfig;
    private javax.net.ssl.X509TrustManager mTrustManager;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mInitialized = false;

    public ApplicationConfig(android.security.net.config.ConfigSource configSource) {
        this.mConfigSource = configSource;
    }

    public boolean hasPerDomainConfigs() {
        ensureInitialized();
        return (this.mConfigs == null || this.mConfigs.isEmpty()) ? false : true;
    }

    public android.security.net.config.NetworkSecurityConfig getConfigForHostname(java.lang.String str) {
        ensureInitialized();
        if (str == null || str.isEmpty() || this.mConfigs == null) {
            return this.mDefaultConfig;
        }
        if (str.charAt(0) == '.') {
            throw new java.lang.IllegalArgumentException("hostname must not begin with a .");
        }
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.US);
        if (lowerCase.charAt(lowerCase.length() - 1) == '.') {
            lowerCase = lowerCase.substring(0, lowerCase.length() - 1);
        }
        android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig> pair = null;
        for (android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig> pair2 : this.mConfigs) {
            android.security.net.config.Domain domain = pair2.first;
            android.security.net.config.NetworkSecurityConfig networkSecurityConfig = pair2.second;
            if (domain.hostname.equals(lowerCase)) {
                return networkSecurityConfig;
            }
            if (domain.subdomainsIncluded && lowerCase.endsWith(domain.hostname) && lowerCase.charAt((lowerCase.length() - domain.hostname.length()) - 1) == '.' && (pair == null || domain.hostname.length() > pair.first.hostname.length())) {
                pair = pair2;
            }
        }
        if (pair != null) {
            return pair.second;
        }
        return this.mDefaultConfig;
    }

    public javax.net.ssl.X509TrustManager getTrustManager() {
        ensureInitialized();
        return this.mTrustManager;
    }

    public boolean isCleartextTrafficPermitted() {
        ensureInitialized();
        if (this.mConfigs != null) {
            java.util.Iterator<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> it = this.mConfigs.iterator();
            while (it.hasNext()) {
                if (!it.next().second.isCleartextTrafficPermitted()) {
                    return false;
                }
            }
        }
        return this.mDefaultConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(java.lang.String str) {
        return getConfigForHostname(str).isCleartextTrafficPermitted();
    }

    public boolean isCertificateTransparencyVerificationRequired(java.lang.String str) {
        if (android.security.Flags.certificateTransparencyConfiguration()) {
            return getConfigForHostname(str).isCertificateTransparencyVerificationRequired();
        }
        return false;
    }

    public void handleTrustStorageUpdate() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                this.mDefaultConfig.handleTrustStorageUpdate();
                if (this.mConfigs != null) {
                    java.util.HashSet hashSet = new java.util.HashSet(this.mConfigs.size());
                    for (android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig> pair : this.mConfigs) {
                        if (hashSet.add(pair.second)) {
                            pair.second.handleTrustStorageUpdate();
                        }
                    }
                }
            }
        }
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                return;
            }
            this.mConfigs = this.mConfigSource.getPerDomainConfigs();
            this.mDefaultConfig = this.mConfigSource.getDefaultConfig();
            this.mConfigSource = null;
            this.mTrustManager = new android.security.net.config.RootTrustManager(this);
            this.mInitialized = true;
        }
    }

    public static void setDefaultInstance(android.security.net.config.ApplicationConfig applicationConfig) {
        synchronized (sLock) {
            sInstance = applicationConfig;
        }
    }

    public static android.security.net.config.ApplicationConfig getDefaultInstance() {
        android.security.net.config.ApplicationConfig applicationConfig;
        synchronized (sLock) {
            applicationConfig = sInstance;
        }
        return applicationConfig;
    }
}
