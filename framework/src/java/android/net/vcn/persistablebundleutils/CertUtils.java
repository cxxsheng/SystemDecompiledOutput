package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public class CertUtils {
    private static final java.lang.String CERT_TYPE_X509 = "X.509";
    private static final java.lang.String PRIVATE_KEY_TYPE_RSA = "RSA";

    public static java.security.cert.X509Certificate certificateFromByteArray(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr, "derEncoded is null");
        try {
            return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance(CERT_TYPE_X509).generateCertificate(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.IllegalArgumentException("Fail to decode certificate", e);
        }
    }

    public static java.security.interfaces.RSAPrivateKey privateKeyFromByteArray(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr, "pkcs8Encoded was null");
        try {
            return (java.security.interfaces.RSAPrivateKey) java.security.KeyFactory.getInstance("RSA").generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(bArr));
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
            throw new java.lang.IllegalArgumentException("Fail to decode PrivateKey", e);
        }
    }
}
