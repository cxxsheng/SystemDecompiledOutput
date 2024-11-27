package android.net.http;

/* loaded from: classes2.dex */
public class SslError {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int SSL_DATE_INVALID = 4;
    public static final int SSL_EXPIRED = 1;
    public static final int SSL_IDMISMATCH = 2;
    public static final int SSL_INVALID = 5;

    @java.lang.Deprecated
    public static final int SSL_MAX_ERROR = 6;
    public static final int SSL_NOTYETVALID = 0;
    public static final int SSL_UNTRUSTED = 3;
    final android.net.http.SslCertificate mCertificate;
    int mErrors;
    final java.lang.String mUrl;

    @java.lang.Deprecated
    public SslError(int i, android.net.http.SslCertificate sslCertificate) {
        this(i, sslCertificate, "");
    }

    @java.lang.Deprecated
    public SslError(int i, java.security.cert.X509Certificate x509Certificate) {
        this(i, x509Certificate, "");
    }

    public SslError(int i, android.net.http.SslCertificate sslCertificate, java.lang.String str) {
        addError(i);
        this.mCertificate = sslCertificate;
        this.mUrl = str;
    }

    public SslError(int i, java.security.cert.X509Certificate x509Certificate, java.lang.String str) {
        this(i, new android.net.http.SslCertificate(x509Certificate), str);
    }

    public static android.net.http.SslError SslErrorFromChromiumErrorCode(int i, android.net.http.SslCertificate sslCertificate, java.lang.String str) {
        if (i == -200) {
            return new android.net.http.SslError(2, sslCertificate, str);
        }
        if (i == -201) {
            return new android.net.http.SslError(4, sslCertificate, str);
        }
        if (i == -202) {
            return new android.net.http.SslError(3, sslCertificate, str);
        }
        return new android.net.http.SslError(5, sslCertificate, str);
    }

    public android.net.http.SslCertificate getCertificate() {
        return this.mCertificate;
    }

    public java.lang.String getUrl() {
        return this.mUrl;
    }

    public boolean addError(int i) {
        boolean z = i >= 0 && i < 6;
        if (z) {
            this.mErrors = (1 << i) | this.mErrors;
        }
        return z;
    }

    public boolean hasError(int i) {
        boolean z = i >= 0 && i < 6;
        if (z) {
            return ((1 << i) & this.mErrors) != 0;
        }
        return z;
    }

    public int getPrimaryError() {
        if (this.mErrors != 0) {
            for (int i = 5; i >= 0; i--) {
                if ((this.mErrors & (1 << i)) != 0) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    public java.lang.String toString() {
        return "primary error: " + getPrimaryError() + " certificate: " + getCertificate() + " on URL: " + getUrl();
    }
}
