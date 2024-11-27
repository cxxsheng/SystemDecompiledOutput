package android.security;

/* loaded from: classes3.dex */
public class NetworkSecurityPolicy {
    private static final android.security.NetworkSecurityPolicy INSTANCE = new android.security.NetworkSecurityPolicy();

    private NetworkSecurityPolicy() {
    }

    public static android.security.NetworkSecurityPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isCleartextTrafficPermitted() {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(java.lang.String str) {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(str);
    }

    public void setCleartextTrafficPermitted(boolean z) {
        libcore.net.NetworkSecurityPolicy.setInstance(new android.security.FrameworkNetworkSecurityPolicy(z));
    }

    public boolean isCertificateTransparencyVerificationRequired(java.lang.String str) {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCertificateTransparencyVerificationRequired(str);
    }

    public void handleTrustStorageUpdate() {
        android.security.net.config.ApplicationConfig defaultInstance = android.security.net.config.ApplicationConfig.getDefaultInstance();
        if (defaultInstance != null) {
            defaultInstance.handleTrustStorageUpdate();
        }
    }

    public static android.security.net.config.ApplicationConfig getApplicationConfigForPackage(android.content.Context context, java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return new android.security.net.config.ApplicationConfig(new android.security.net.config.ManifestConfigSource(context.createPackageContext(str, 0)));
    }
}
