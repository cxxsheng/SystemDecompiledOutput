package android.security.net.config;

/* loaded from: classes3.dex */
public final class NetworkSecurityConfigProvider extends java.security.Provider {
    private static final java.lang.String LOG_TAG = "nsconfig";
    private static final java.lang.String PREFIX = android.security.net.config.NetworkSecurityConfigProvider.class.getPackage().getName() + android.media.MediaMetrics.SEPARATOR;

    public NetworkSecurityConfigProvider() {
        super("AndroidNSSP", 1.0d, "Android Network Security Policy Provider");
        put("TrustManagerFactory.PKIX", PREFIX + "RootTrustManagerFactorySpi");
        put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
    }

    public static void install(android.content.Context context) {
        android.security.net.config.ApplicationConfig applicationConfig = new android.security.net.config.ApplicationConfig(new android.security.net.config.ManifestConfigSource(context));
        android.security.net.config.ApplicationConfig.setDefaultInstance(applicationConfig);
        int insertProviderAt = java.security.Security.insertProviderAt(new android.security.net.config.NetworkSecurityConfigProvider(), 1);
        if (insertProviderAt != 1) {
            throw new java.lang.RuntimeException("Failed to install provider as highest priority provider. Provider was installed at position " + insertProviderAt);
        }
        libcore.net.NetworkSecurityPolicy.setInstance(new android.security.net.config.ConfigNetworkSecurityPolicy(applicationConfig));
    }

    public static void handleNewApplication(android.content.Context context) {
        android.security.net.config.ApplicationConfig applicationConfig = new android.security.net.config.ApplicationConfig(new android.security.net.config.ManifestConfigSource(context));
        android.security.net.config.ApplicationConfig defaultInstance = android.security.net.config.ApplicationConfig.getDefaultInstance();
        java.lang.String str = context.getApplicationInfo().processName;
        if (defaultInstance != null && defaultInstance.isCleartextTrafficPermitted() != applicationConfig.isCleartextTrafficPermitted()) {
            android.util.Log.w(LOG_TAG, str + ": New config does not match the previously set config.");
            if (defaultInstance.hasPerDomainConfigs() || applicationConfig.hasPerDomainConfigs()) {
                throw new java.lang.RuntimeException("Found multiple conflicting per-domain rules");
            }
            if (defaultInstance.isCleartextTrafficPermitted()) {
                applicationConfig = defaultInstance;
            }
        }
        android.security.net.config.ApplicationConfig.setDefaultInstance(applicationConfig);
    }
}
