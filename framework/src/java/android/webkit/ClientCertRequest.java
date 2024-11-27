package android.webkit;

/* loaded from: classes4.dex */
public abstract class ClientCertRequest {
    public abstract void cancel();

    public abstract java.lang.String getHost();

    public abstract java.lang.String[] getKeyTypes();

    public abstract int getPort();

    public abstract java.security.Principal[] getPrincipals();

    public abstract void ignore();

    public abstract void proceed(java.security.PrivateKey privateKey, java.security.cert.X509Certificate[] x509CertificateArr);
}
