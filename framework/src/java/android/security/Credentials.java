package android.security;

/* loaded from: classes3.dex */
public class Credentials {
    public static final java.lang.String ACTION_MANAGE_CREDENTIALS = "android.security.MANAGE_CREDENTIALS";

    @java.lang.Deprecated
    public static final java.lang.String APP_SOURCE_CERTIFICATE = "FSV_";

    @java.lang.Deprecated
    public static final java.lang.String CA_CERTIFICATE = "CACERT_";
    public static final java.lang.String CERTIFICATE_USAGE_APP_SOURCE = "appsrc";
    public static final java.lang.String CERTIFICATE_USAGE_CA = "ca";
    public static final java.lang.String CERTIFICATE_USAGE_USER = "user";
    public static final java.lang.String CERTIFICATE_USAGE_WIFI = "wifi";
    public static final java.lang.String EXTENSION_CER = ".cer";
    public static final java.lang.String EXTENSION_CRT = ".crt";
    public static final java.lang.String EXTENSION_P12 = ".p12";
    public static final java.lang.String EXTENSION_PFX = ".pfx";
    public static final java.lang.String EXTRA_CA_CERTIFICATES_DATA = "ca_certificates_data";
    public static final java.lang.String EXTRA_CERTIFICATE_USAGE = "certificate_install_usage";
    public static final java.lang.String EXTRA_INSTALL_AS_UID = "install_as_uid";
    public static final java.lang.String EXTRA_PRIVATE_KEY = "PKEY";
    public static final java.lang.String EXTRA_PUBLIC_KEY = "KEY";
    public static final java.lang.String EXTRA_USER_CERTIFICATE_DATA = "user_certificate_data";
    public static final java.lang.String EXTRA_USER_KEY_ALIAS = "user_key_pair_name";
    public static final java.lang.String EXTRA_USER_PRIVATE_KEY_DATA = "user_private_key_data";
    public static final java.lang.String INSTALL_ACTION = "android.credentials.INSTALL";
    public static final java.lang.String INSTALL_AS_USER_ACTION = "android.credentials.INSTALL_AS_USER";
    public static final java.lang.String LOCKDOWN_VPN = "LOCKDOWN_VPN";
    private static final java.lang.String LOGTAG = "Credentials";
    public static final java.lang.String PLATFORM_VPN = "PLATFORM_VPN_";

    @java.lang.Deprecated
    public static final java.lang.String USER_CERTIFICATE = "USRCERT_";

    @java.lang.Deprecated
    public static final java.lang.String USER_PRIVATE_KEY = "USRPKEY_";

    @java.lang.Deprecated
    public static final java.lang.String USER_SECRET_KEY = "USRSKEY_";
    public static final java.lang.String VPN = "VPN_";
    public static final java.lang.String WIFI = "WIFI_";

    public static byte[] convertToPem(java.security.cert.Certificate... certificateArr) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        com.android.internal.org.bouncycastle.util.io.pem.PemWriter pemWriter = new com.android.internal.org.bouncycastle.util.io.pem.PemWriter(new java.io.OutputStreamWriter(byteArrayOutputStream, java.nio.charset.StandardCharsets.US_ASCII));
        for (java.security.cert.Certificate certificate : certificateArr) {
            pemWriter.writeObject(new com.android.internal.org.bouncycastle.util.io.pem.PemObject("CERTIFICATE", certificate.getEncoded()));
        }
        pemWriter.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static java.util.List<java.security.cert.X509Certificate> convertFromPem(byte[] bArr) throws java.io.IOException, java.security.cert.CertificateException {
        com.android.internal.org.bouncycastle.util.io.pem.PemReader pemReader = new com.android.internal.org.bouncycastle.util.io.pem.PemReader(new java.io.InputStreamReader(new java.io.ByteArrayInputStream(bArr), java.nio.charset.StandardCharsets.US_ASCII));
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X509");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                com.android.internal.org.bouncycastle.util.io.pem.PemObject readPemObject = pemReader.readPemObject();
                if (readPemObject != null) {
                    if (readPemObject.getType().equals("CERTIFICATE")) {
                        arrayList.add((java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(readPemObject.getContent())));
                    } else {
                        throw new java.lang.IllegalArgumentException("Unknown type " + readPemObject.getType());
                    }
                } else {
                    return arrayList;
                }
            }
        } finally {
            pemReader.close();
        }
    }
}
