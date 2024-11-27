package android.security.net.config;

/* loaded from: classes3.dex */
public class ManifestConfigSource implements android.security.net.config.ConfigSource {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "NetworkSecurityConfig";
    private final android.content.pm.ApplicationInfo mApplicationInfo;
    private android.security.net.config.ConfigSource mConfigSource;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();

    public ManifestConfigSource(android.content.Context context) {
        this.mContext = context;
        this.mApplicationInfo = new android.content.pm.ApplicationInfo(context.getApplicationInfo());
    }

    @Override // android.security.net.config.ConfigSource
    public java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> getPerDomainConfigs() {
        return getConfigSource().getPerDomainConfigs();
    }

    @Override // android.security.net.config.ConfigSource
    public android.security.net.config.NetworkSecurityConfig getDefaultConfig() {
        return getConfigSource().getDefaultConfig();
    }

    private android.security.net.config.ConfigSource getConfigSource() {
        android.security.net.config.ConfigSource defaultConfigSource;
        synchronized (this.mLock) {
            if (this.mConfigSource != null) {
                return this.mConfigSource;
            }
            int i = this.mApplicationInfo.networkSecurityConfigRes;
            if (i != 0) {
                if ((this.mApplicationInfo.flags & 2) != 0) {
                }
                defaultConfigSource = new android.security.net.config.XmlConfigSource(this.mContext, i, this.mApplicationInfo);
            } else {
                defaultConfigSource = new android.security.net.config.ManifestConfigSource.DefaultConfigSource(((this.mApplicationInfo.flags & 134217728) == 0 || this.mApplicationInfo.isInstantApp()) ? false : true, this.mApplicationInfo);
            }
            this.mConfigSource = defaultConfigSource;
            return this.mConfigSource;
        }
    }

    private static final class DefaultConfigSource implements android.security.net.config.ConfigSource {
        private final android.security.net.config.NetworkSecurityConfig mDefaultConfig;

        DefaultConfigSource(boolean z, android.content.pm.ApplicationInfo applicationInfo) {
            this.mDefaultConfig = android.security.net.config.NetworkSecurityConfig.getDefaultBuilder(applicationInfo).setCleartextTrafficPermitted(z).build();
        }

        @Override // android.security.net.config.ConfigSource
        public android.security.net.config.NetworkSecurityConfig getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override // android.security.net.config.ConfigSource
        public java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> getPerDomainConfigs() {
            return null;
        }
    }
}
