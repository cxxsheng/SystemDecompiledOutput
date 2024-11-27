package android.security.net.config;

/* loaded from: classes3.dex */
public class ConfigNetworkSecurityPolicy extends libcore.net.NetworkSecurityPolicy {
    private final android.security.net.config.ApplicationConfig mConfig;

    public ConfigNetworkSecurityPolicy(android.security.net.config.ApplicationConfig applicationConfig) {
        this.mConfig = applicationConfig;
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(java.lang.String str) {
        return this.mConfig.isCleartextTrafficPermitted(str);
    }

    public boolean isCertificateTransparencyVerificationRequired(java.lang.String str) {
        return this.mConfig.isCertificateTransparencyVerificationRequired(str);
    }
}
