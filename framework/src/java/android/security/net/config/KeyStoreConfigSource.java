package android.security.net.config;

/* loaded from: classes3.dex */
class KeyStoreConfigSource implements android.security.net.config.ConfigSource {
    private final android.security.net.config.NetworkSecurityConfig mConfig;

    public KeyStoreConfigSource(java.security.KeyStore keyStore) {
        this.mConfig = new android.security.net.config.NetworkSecurityConfig.Builder().addCertificatesEntryRef(new android.security.net.config.CertificatesEntryRef(new android.security.net.config.KeyStoreCertificateSource(keyStore), false)).build();
    }

    @Override // android.security.net.config.ConfigSource
    public java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> getPerDomainConfigs() {
        return null;
    }

    @Override // android.security.net.config.ConfigSource
    public android.security.net.config.NetworkSecurityConfig getDefaultConfig() {
        return this.mConfig;
    }
}
