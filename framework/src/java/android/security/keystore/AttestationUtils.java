package android.security.keystore;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AttestationUtils {
    public static final int ID_TYPE_IMEI = 2;
    public static final int ID_TYPE_MEID = 3;
    public static final int ID_TYPE_SERIAL = 1;
    public static final int USE_INDIVIDUAL_ATTESTATION = 4;

    private AttestationUtils() {
    }

    public static java.security.cert.X509Certificate[] parseCertificateChain(android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) throws android.security.keystore.KeyAttestationException {
        java.util.List<byte[]> certificates = keymasterCertificateChain.getCertificates();
        if (certificates.size() < 2) {
            throw new android.security.keystore.KeyAttestationException("Attestation certificate chain contained " + certificates.size() + " entries. At least two are required.");
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            java.util.Iterator<byte[]> it = certificates.iterator();
            while (it.hasNext()) {
                byteArrayOutputStream.write(it.next());
            }
            return (java.security.cert.X509Certificate[]) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(new java.io.ByteArrayInputStream(byteArrayOutputStream.toByteArray())).toArray(new java.security.cert.X509Certificate[0]);
        } catch (java.lang.Exception e) {
            throw new android.security.keystore.KeyAttestationException("Unable to construct certificate chain", e);
        }
    }

    public static java.security.cert.X509Certificate[] attestDeviceIds(android.content.Context context, int[] iArr, byte[] bArr) throws android.security.keystore.DeviceIdAttestationException {
        if (bArr == null) {
            throw new java.lang.NullPointerException("Missing attestation challenge");
        }
        if (iArr == null) {
            throw new java.lang.NullPointerException("Missing id types");
        }
        java.lang.String generateRandomAlias = generateRandomAlias();
        android.security.keystore.KeyGenParameterSpec.Builder attestationChallenge = new android.security.keystore.KeyGenParameterSpec.Builder(generateRandomAlias, 4).setAlgorithmParameterSpec(new java.security.spec.ECGenParameterSpec("secp256r1")).setDigests("SHA-256").setAttestationChallenge(bArr);
        if (iArr != null) {
            attestationChallenge.setAttestationIds(iArr);
            attestationChallenge.setDevicePropertiesAttestationIncluded(true);
        }
        try {
            java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, android.security.keystore2.AndroidKeyStoreSpi.NAME);
            keyPairGenerator.initialize(attestationChallenge.build());
            keyPairGenerator.generateKeyPair();
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance(android.security.keystore2.AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            java.security.cert.Certificate[] certificateChain = keyStore.getCertificateChain(generateRandomAlias);
            java.security.cert.X509Certificate[] x509CertificateArr = (java.security.cert.X509Certificate[]) java.util.Arrays.copyOf(certificateChain, certificateChain.length, java.security.cert.X509Certificate[].class);
            keyStore.deleteEntry(generateRandomAlias);
            return x509CertificateArr;
        } catch (java.lang.SecurityException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            if (e2.getCause() instanceof android.security.keystore.DeviceIdAttestationException) {
                throw ((android.security.keystore.DeviceIdAttestationException) e2.getCause());
            }
            if ((e2 instanceof java.security.ProviderException) && (e2.getCause() instanceof java.lang.IllegalArgumentException)) {
                throw ((java.lang.IllegalArgumentException) e2.getCause());
            }
            throw new android.security.keystore.DeviceIdAttestationException("Unable to perform attestation", e2);
        }
    }

    private static java.lang.String generateRandomAlias() {
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append(secureRandom.nextInt(26) + 65);
        }
        return sb.toString();
    }

    public static boolean isChainValid(android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) {
        return keymasterCertificateChain != null && keymasterCertificateChain.getCertificates().size() >= 2;
    }
}
