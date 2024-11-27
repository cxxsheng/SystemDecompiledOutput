package android.net.http;

/* loaded from: classes2.dex */
public class X509TrustManagerExtensions {
    private final java.lang.reflect.Method mCheckServerTrusted;
    private final com.android.org.conscrypt.TrustManagerImpl mDelegate;
    private final java.lang.reflect.Method mIsSameTrustConfiguration;
    private final javax.net.ssl.X509TrustManager mTrustManager;

    public X509TrustManagerExtensions(javax.net.ssl.X509TrustManager x509TrustManager) throws java.lang.IllegalArgumentException {
        java.lang.reflect.Method method = null;
        if (x509TrustManager instanceof com.android.org.conscrypt.TrustManagerImpl) {
            this.mDelegate = (com.android.org.conscrypt.TrustManagerImpl) x509TrustManager;
            this.mTrustManager = null;
            this.mCheckServerTrusted = null;
            this.mIsSameTrustConfiguration = null;
            return;
        }
        this.mDelegate = null;
        this.mTrustManager = x509TrustManager;
        try {
            this.mCheckServerTrusted = x509TrustManager.getClass().getMethod("checkServerTrusted", java.security.cert.X509Certificate[].class, java.lang.String.class, java.lang.String.class);
            try {
                method = x509TrustManager.getClass().getMethod("isSameTrustConfiguration", java.lang.String.class, java.lang.String.class);
            } catch (java.lang.ReflectiveOperationException e) {
            }
            this.mIsSameTrustConfiguration = method;
        } catch (java.lang.NoSuchMethodException e2) {
            throw new java.lang.IllegalArgumentException("Required method checkServerTrusted(X509Certificate[], String, String, String) missing");
        }
    }

    public java.util.List<java.security.cert.X509Certificate> checkServerTrusted(java.security.cert.X509Certificate[] x509CertificateArr, java.lang.String str, java.lang.String str2) throws java.security.cert.CertificateException {
        if (this.mDelegate != null) {
            return this.mDelegate.checkServerTrusted(x509CertificateArr, str, str2);
        }
        try {
            return (java.util.List) this.mCheckServerTrusted.invoke(this.mTrustManager, x509CertificateArr, str, str2);
        } catch (java.lang.IllegalAccessException e) {
            throw new java.security.cert.CertificateException("Failed to call checkServerTrusted", e);
        } catch (java.lang.reflect.InvocationTargetException e2) {
            if (e2.getCause() instanceof java.security.cert.CertificateException) {
                throw ((java.security.cert.CertificateException) e2.getCause());
            }
            if (e2.getCause() instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) e2.getCause());
            }
            throw new java.security.cert.CertificateException("checkServerTrusted failed", e2.getCause());
        }
    }

    public boolean isUserAddedCertificate(java.security.cert.X509Certificate x509Certificate) {
        return android.security.net.config.UserCertificateSource.getInstance().findBySubjectAndPublicKey(x509Certificate) != null;
    }

    public boolean isSameTrustConfiguration(java.lang.String str, java.lang.String str2) {
        if (this.mIsSameTrustConfiguration == null) {
            return true;
        }
        try {
            return ((java.lang.Boolean) this.mIsSameTrustConfiguration.invoke(this.mTrustManager, str, str2)).booleanValue();
        } catch (java.lang.IllegalAccessException e) {
            throw new java.lang.RuntimeException("Failed to call isSameTrustConfiguration", e);
        } catch (java.lang.reflect.InvocationTargetException e2) {
            if (e2.getCause() instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) e2.getCause());
            }
            throw new java.lang.RuntimeException("isSameTrustConfiguration failed", e2.getCause());
        }
    }
}
