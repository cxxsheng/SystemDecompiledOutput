package android.security.keystore.recovery;

/* loaded from: classes3.dex */
public class X509CertificateParsingUtils {
    private static final java.lang.String CERT_FORMAT = "X.509";

    public static java.security.cert.X509Certificate decodeBase64Cert(java.lang.String str) throws java.security.cert.CertificateException {
        try {
            return decodeCert(decodeBase64(str));
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.security.cert.CertificateException(e);
        }
    }

    private static byte[] decodeBase64(java.lang.String str) {
        return java.util.Base64.getDecoder().decode(str);
    }

    private static java.security.cert.X509Certificate decodeCert(byte[] bArr) throws java.security.cert.CertificateException {
        return decodeCert(new java.io.ByteArrayInputStream(bArr));
    }

    private static java.security.cert.X509Certificate decodeCert(java.io.InputStream inputStream) throws java.security.cert.CertificateException {
        try {
            return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance(CERT_FORMAT).generateCertificate(inputStream);
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
