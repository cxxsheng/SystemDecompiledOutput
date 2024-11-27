package android.security.net.config;

/* loaded from: classes3.dex */
public class RootTrustManagerFactorySpi extends javax.net.ssl.TrustManagerFactorySpi {
    private android.security.net.config.ApplicationConfig mApplicationConfig;
    private android.security.net.config.NetworkSecurityConfig mConfig;

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(javax.net.ssl.ManagerFactoryParameters managerFactoryParameters) throws java.security.InvalidAlgorithmParameterException {
        if (!(managerFactoryParameters instanceof android.security.net.config.RootTrustManagerFactorySpi.ApplicationConfigParameters)) {
            throw new java.security.InvalidAlgorithmParameterException("Unsupported spec: " + managerFactoryParameters + ". Only " + android.security.net.config.RootTrustManagerFactorySpi.ApplicationConfigParameters.class.getName() + " supported");
        }
        this.mApplicationConfig = ((android.security.net.config.RootTrustManagerFactorySpi.ApplicationConfigParameters) managerFactoryParameters).config;
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(java.security.KeyStore keyStore) throws java.security.KeyStoreException {
        if (keyStore != null) {
            this.mApplicationConfig = new android.security.net.config.ApplicationConfig(new android.security.net.config.KeyStoreConfigSource(keyStore));
        } else {
            this.mApplicationConfig = android.security.net.config.ApplicationConfig.getDefaultInstance();
        }
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public javax.net.ssl.TrustManager[] engineGetTrustManagers() {
        if (this.mApplicationConfig == null) {
            throw new java.lang.IllegalStateException("TrustManagerFactory not initialized");
        }
        return new javax.net.ssl.TrustManager[]{this.mApplicationConfig.getTrustManager()};
    }

    public static final class ApplicationConfigParameters implements javax.net.ssl.ManagerFactoryParameters {
        public final android.security.net.config.ApplicationConfig config;

        public ApplicationConfigParameters(android.security.net.config.ApplicationConfig applicationConfig) {
            this.config = applicationConfig;
        }
    }
}
