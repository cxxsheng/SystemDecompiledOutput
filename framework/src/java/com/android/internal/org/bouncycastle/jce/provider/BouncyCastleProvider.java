package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public final class BouncyCastleProvider extends java.security.Provider implements com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider {
    private static final java.lang.String ASYMMETRIC_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.";
    private static final java.lang.String DIGEST_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.digest.";
    private static final java.lang.String KEYSTORE_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.keystore.";
    private static final java.lang.String SYMMETRIC_PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.symmetric.";
    private final java.security.Provider privateProvider;
    private static java.lang.String info = "BouncyCastle Security Provider v1.68";
    public static final com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration CONFIGURATION = new com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProviderConfiguration();
    private static final java.util.Map keyInfoConverters = new java.util.HashMap();
    private static final java.lang.Class revChkClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.class, "java.security.cert.PKIXRevocationChecker");
    private static final java.lang.String[] SYMMETRIC_GENERIC = {"PBEPBKDF2", "PBEPKCS12", "PBES2AlgorithmParameters"};
    private static final java.lang.String[] SYMMETRIC_MACS = new java.lang.String[0];
    private static final java.lang.String[] SYMMETRIC_CIPHERS = {android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, "ARC4", "Blowfish", "DES", android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES, "RC2", "Twofish"};
    private static final java.lang.String[] ASYMMETRIC_GENERIC = {"X509"};
    private static final java.lang.String[] ASYMMETRIC_CIPHERS = {"DSA", "DH", android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA};
    private static final java.lang.String[] DIGESTS = {android.security.keystore.KeyProperties.DIGEST_MD5, "SHA1", "SHA224", "SHA256", "SHA384", "SHA512"};
    public static final java.lang.String PROVIDER_NAME = "BC";
    private static final java.lang.String[] KEYSTORES = {PROVIDER_NAME, "BCFKS", android.security.KeyChain.EXTRA_PKCS12};

    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.68d, info);
        this.privateProvider = new com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PrivateProvider();
        java.security.AccessController.doPrivileged(new java.security.PrivilegedAction() { // from class: com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.1
            @Override // java.security.PrivilegedAction
            public java.lang.Object run() {
                com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.this.setup();
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setup() {
        loadAlgorithms(DIGEST_PACKAGE, DIGESTS);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_GENERIC);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_MACS);
        loadAlgorithms(SYMMETRIC_PACKAGE, SYMMETRIC_CIPHERS);
        loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_GENERIC);
        loadAlgorithms(ASYMMETRIC_PACKAGE, ASYMMETRIC_CIPHERS);
        loadAlgorithms(KEYSTORE_PACKAGE, KEYSTORES);
        put("CertPathValidator.PKIX", "com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.PKIX", "com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertStore.Collection", "com.android.internal.org.bouncycastle.jce.provider.CertStoreCollectionSpi");
    }

    private void loadAlgorithms(java.lang.String str, java.lang.String[] strArr) {
        for (int i = 0; i != strArr.length; i++) {
            java.lang.Class loadClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.class, str + strArr[i] + "$Mappings");
            if (loadClass != null) {
                try {
                    ((com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider) loadClass.newInstance()).configure(this);
                } catch (java.lang.Exception e) {
                    throw new java.lang.InternalError("cannot create instance of " + str + strArr[i] + "$Mappings : " + e);
                }
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void setParameter(java.lang.String str, java.lang.Object obj) {
        synchronized (CONFIGURATION) {
            ((com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProviderConfiguration) CONFIGURATION).setParameter(str, obj);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public boolean hasAlgorithm(java.lang.String str, java.lang.String str2) {
        return containsKey(new java.lang.StringBuilder().append(str).append(android.media.MediaMetrics.SEPARATOR).append(str2).toString()) || containsKey(new java.lang.StringBuilder().append("Alg.Alias.").append(str).append(android.media.MediaMetrics.SEPARATOR).append(str2).toString());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(java.lang.String str, java.lang.String str2) {
        if (containsKey(str)) {
            throw new java.lang.IllegalStateException("duplicate provider key (" + str + ") found");
        }
        put(str, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAlgorithm(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str2) {
        addAlgorithm(str + android.media.MediaMetrics.SEPARATOR + aSN1ObjectIdentifier, str2);
        addAlgorithm(str + ".OID." + aSN1ObjectIdentifier, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addKeyInfoConverter(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        synchronized (keyInfoConverters) {
            keyInfoConverters.put(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter getKeyInfoConverter(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter) keyInfoConverters.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addAttributes(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) {
        for (java.lang.String str2 : map.keySet()) {
            java.lang.String str3 = str + " " + str2;
            if (containsKey(str3)) {
                throw new java.lang.IllegalStateException("duplicate provider attribute key (" + str3 + ") found");
            }
            put(str3, map.get(str2));
        }
    }

    private static com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter getAsymmetricKeyInfoConverter(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter asymmetricKeyInfoConverter;
        synchronized (keyInfoConverters) {
            asymmetricKeyInfoConverter = (com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter) keyInfoConverters.get(aSN1ObjectIdentifier);
        }
        return asymmetricKeyInfoConverter;
    }

    public static java.security.PublicKey getPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        try {
            return java.security.KeyFactory.getInstance(subjectPublicKeyInfo.getAlgorithmId().getAlgorithm().getId()).generatePublic(new java.security.spec.X509EncodedKeySpec(subjectPublicKeyInfo.getEncoded()));
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        } catch (java.security.spec.InvalidKeySpecException e2) {
            throw new java.io.IOException(e2);
        }
    }

    public static java.security.PrivateKey getPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        try {
            return java.security.KeyFactory.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm().getId()).generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyInfo.getEncoded()));
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        } catch (java.security.spec.InvalidKeySpecException e2) {
            throw new java.io.IOException(e2);
        }
    }

    private static final class PrivateProvider extends java.security.Provider {
        public PrivateProvider() {
            super("BCPrivate", 1.0d, "Android BC private use only");
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addPrivateAlgorithm(java.lang.String str, java.lang.String str2) {
        if (this.privateProvider.containsKey(str)) {
            throw new java.lang.IllegalStateException("duplicate provider key (" + str + ") found");
        }
        this.privateProvider.put(str, str2);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider
    public void addPrivateAlgorithm(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str2) {
        addPrivateAlgorithm(str + android.media.MediaMetrics.SEPARATOR + aSN1ObjectIdentifier, str2);
    }

    public java.security.Provider getPrivateProvider() {
        return this.privateProvider;
    }
}
