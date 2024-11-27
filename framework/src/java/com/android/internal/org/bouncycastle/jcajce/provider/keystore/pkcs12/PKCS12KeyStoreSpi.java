package com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12;

/* loaded from: classes4.dex */
public class PKCS12KeyStoreSpi extends java.security.KeyStoreSpi implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers, com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers, com.android.internal.org.bouncycastle.jce.interfaces.BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 51200;
    static final int NULL = 0;
    static final java.lang.String PKCS12_MAX_IT_COUNT_PROPERTY = "com.android.internal.org.bouncycastle.pkcs12.max_it_count";
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.DefaultSecretKeyProvider keySizeProvider = new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.DefaultSecretKeyProvider();
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certAlgorithm;
    private java.security.cert.CertificateFactory certFact;
    private com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable certs;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier keyAlgorithm;
    private com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable keys;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper selfHelper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
    private java.util.Hashtable localIds = new java.util.Hashtable();
    private java.util.Hashtable chainCerts = new java.util.Hashtable();
    private java.util.Hashtable keyCerts = new java.util.Hashtable();
    protected java.security.SecureRandom random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier macAlgorithm = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    private int itCount = 102400;
    private int saltLength = 20;

    private class CertId {
        byte[] id;

        CertId(java.security.PublicKey publicKey) {
            this.id = com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        CertId(byte[] bArr) {
            this.id = bArr;
        }

        public int hashCode() {
            return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.id);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId)) {
                return false;
            }
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.id, ((com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId) obj).id);
        }
    }

    public PKCS12KeyStoreSpi(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2) {
        this.keys = new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable();
        this.certs = new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable();
        this.keyAlgorithm = aSN1ObjectIdentifier;
        this.certAlgorithm = aSN1ObjectIdentifier2;
        try {
            this.certFact = jcaJceHelper.createCertificateFactory("X.509");
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException("can't create cert factory - " + e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier createSubjectKeyId(java.security.PublicKey publicKey) {
        try {
            return new com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier(getDigest(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())));
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("error creating key");
        }
    }

    private static byte[] getDigest(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        com.android.internal.org.bouncycastle.crypto.Digest sha1 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
        byte[] bArr = new byte[sha1.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        sha1.update(bytes, 0, bytes.length);
        sha1.doFinal(bArr, 0);
        return bArr;
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.BCKeyStore
    public void setRandom(java.security.SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Enumeration engineAliases() {
        java.util.Hashtable hashtable = new java.util.Hashtable();
        java.util.Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        java.util.Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            java.lang.String str = (java.lang.String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(java.lang.String str) {
        return (this.certs.get(str) == null && this.keys.get(str) == null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(java.lang.String str) throws java.security.KeyStoreException {
        java.security.Key key = (java.security.Key) this.keys.remove(str);
        java.security.cert.Certificate certificate = (java.security.cert.Certificate) this.certs.remove(str);
        if (certificate != null) {
            this.chainCerts.remove(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(certificate.getPublicKey()));
        }
        if (key != null) {
            java.lang.String str2 = (java.lang.String) this.localIds.remove(str);
            if (str2 != null) {
                certificate = (java.security.cert.Certificate) this.keyCerts.remove(str2);
            }
            if (certificate != null) {
                this.chainCerts.remove(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(certificate.getPublicKey()));
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate engineGetCertificate(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("null alias passed to getCertificate.");
        }
        java.security.cert.Certificate certificate = (java.security.cert.Certificate) this.certs.get(str);
        if (certificate == null) {
            java.lang.String str2 = (java.lang.String) this.localIds.get(str);
            if (str2 != null) {
                return (java.security.cert.Certificate) this.keyCerts.get(str2);
            }
            return (java.security.cert.Certificate) this.keyCerts.get(str);
        }
        return certificate;
    }

    @Override // java.security.KeyStoreSpi
    public java.lang.String engineGetCertificateAlias(java.security.cert.Certificate certificate) {
        java.util.Enumeration elements = this.certs.elements();
        java.util.Enumeration keys = this.certs.keys();
        while (elements.hasMoreElements()) {
            java.security.cert.Certificate certificate2 = (java.security.cert.Certificate) elements.nextElement();
            java.lang.String str = (java.lang.String) keys.nextElement();
            if (certificate2.equals(certificate)) {
                return str;
            }
        }
        java.util.Enumeration elements2 = this.keyCerts.elements();
        java.util.Enumeration keys2 = this.keyCerts.keys();
        while (elements2.hasMoreElements()) {
            java.security.cert.Certificate certificate3 = (java.security.cert.Certificate) elements2.nextElement();
            java.lang.String str2 = (java.lang.String) keys2.nextElement();
            if (certificate3.equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String str) {
        java.security.cert.Certificate engineGetCertificate;
        java.security.cert.Certificate certificate;
        byte[] keyIdentifier;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("null alias passed to getCertificateChain.");
        }
        if (!engineIsKeyEntry(str) || (engineGetCertificate = engineGetCertificate(str)) == null) {
            return null;
        }
        java.util.Vector vector = new java.util.Vector();
        while (engineGetCertificate != null) {
            java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) engineGetCertificate;
            byte[] extensionValue = x509Certificate.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier.getId());
            if (extensionValue != null && (keyIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(extensionValue).getOctets()).getKeyIdentifier()) != null) {
                certificate = (java.security.cert.Certificate) this.chainCerts.get(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(keyIdentifier));
            } else {
                certificate = null;
            }
            if (certificate == null) {
                java.security.Principal issuerDN = x509Certificate.getIssuerDN();
                if (!issuerDN.equals(x509Certificate.getSubjectDN())) {
                    java.util.Enumeration keys = this.chainCerts.keys();
                    while (true) {
                        if (!keys.hasMoreElements()) {
                            break;
                        }
                        java.security.cert.X509Certificate x509Certificate2 = (java.security.cert.X509Certificate) this.chainCerts.get(keys.nextElement());
                        if (x509Certificate2.getSubjectDN().equals(issuerDN)) {
                            try {
                                x509Certificate.verify(x509Certificate2.getPublicKey());
                                certificate = x509Certificate2;
                                break;
                            } catch (java.lang.Exception e) {
                            }
                        }
                    }
                }
            }
            if (vector.contains(engineGetCertificate)) {
                engineGetCertificate = null;
            } else {
                vector.addElement(engineGetCertificate);
                if (certificate != engineGetCertificate) {
                    engineGetCertificate = certificate;
                } else {
                    engineGetCertificate = null;
                }
            }
        }
        int size = vector.size();
        java.security.cert.Certificate[] certificateArr = new java.security.cert.Certificate[size];
        for (int i = 0; i != size; i++) {
            certificateArr[i] = (java.security.cert.Certificate) vector.elementAt(i);
        }
        return certificateArr;
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Date engineGetCreationDate(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        if (this.keys.get(str) == null && this.certs.get(str) == null) {
            return null;
        }
        return new java.util.Date();
    }

    @Override // java.security.KeyStoreSpi
    public java.security.Key engineGetKey(java.lang.String str, char[] cArr) throws java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("null alias passed to getKey.");
        }
        return (java.security.Key) this.keys.get(str);
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(java.lang.String str) {
        return this.certs.get(str) != null && this.keys.get(str) == null;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(java.lang.String str) {
        return this.keys.get(str) != null;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(java.lang.String str, java.security.cert.Certificate certificate) throws java.security.KeyStoreException {
        if (this.keys.get(str) != null) {
            throw new java.security.KeyStoreException("There is a key entry with the name " + str + android.media.MediaMetrics.SEPARATOR);
        }
        this.certs.put(str, certificate);
        this.chainCerts.put(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(certificate.getPublicKey()), certificate);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, byte[] bArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        throw new java.lang.RuntimeException("operation not supported");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, java.security.Key key, char[] cArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        boolean z = key instanceof java.security.PrivateKey;
        if (!z) {
            throw new java.security.KeyStoreException("PKCS12 does not support non-PrivateKeys");
        }
        if (z && certificateArr == null) {
            throw new java.security.KeyStoreException("no certificate chain for private key");
        }
        if (this.keys.get(str) != null) {
            engineDeleteEntry(str);
        }
        this.keys.put(str, key);
        if (certificateArr != null) {
            this.certs.put(str, certificateArr[0]);
            for (int i = 0; i != certificateArr.length; i++) {
                this.chainCerts.put(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(certificateArr[i].getPublicKey()), certificateArr[i]);
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        java.util.Hashtable hashtable = new java.util.Hashtable();
        java.util.Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        java.util.Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            java.lang.String str = (java.lang.String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    protected java.security.PrivateKey unwrapKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        try {
            if (algorithm.on(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_12PbeIds)) {
                com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams pKCS12PBEParams = com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
                javax.crypto.spec.PBEParameterSpec pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(pKCS12PBEParams.getIV(), validateIterationCount(pKCS12PBEParams.getIterations()));
                javax.crypto.Cipher createCipher = this.helper.createCipher(algorithm.getId());
                createCipher.init(4, new com.android.internal.org.bouncycastle.jcajce.PKCS12Key(cArr, z), pBEParameterSpec);
                return (java.security.PrivateKey) createCipher.unwrap(bArr, "", 2);
            }
            if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBES2)) {
                return (java.security.PrivateKey) createCipher(4, cArr, algorithmIdentifier).unwrap(bArr, "", 2);
            }
            throw new java.io.IOException("exception unwrapping private key - cannot recognise: " + algorithm);
        } catch (java.lang.Exception e) {
            throw new java.io.IOException("exception unwrapping private key - " + e.toString());
        }
    }

    protected byte[] wrapKey(java.lang.String str, java.security.Key key, com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams pKCS12PBEParams, char[] cArr) throws java.io.IOException {
        javax.crypto.spec.PBEKeySpec pBEKeySpec = new javax.crypto.spec.PBEKeySpec(cArr);
        try {
            javax.crypto.SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(str);
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            javax.crypto.Cipher createCipher = this.helper.createCipher(str);
            createCipher.init(3, createSecretKeyFactory.generateSecret(pBEKeySpec), pBEParameterSpec);
            return createCipher.wrap(key);
        } catch (java.lang.Exception e) {
            throw new java.io.IOException("exception encrypting data - " + e.toString());
        }
    }

    protected byte[] cryptData(boolean z, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z2, byte[] bArr) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        int i = z ? 1 : 2;
        if (algorithm.on(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams pKCS12PBEParams = com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
            try {
                javax.crypto.spec.PBEParameterSpec pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
                com.android.internal.org.bouncycastle.jcajce.PKCS12Key pKCS12Key = new com.android.internal.org.bouncycastle.jcajce.PKCS12Key(cArr, z2);
                javax.crypto.Cipher createCipher = this.helper.createCipher(algorithm.getId());
                createCipher.init(i, pKCS12Key, pBEParameterSpec);
                return createCipher.doFinal(bArr);
            } catch (java.lang.Exception e) {
                throw new java.io.IOException("exception decrypting data - " + e.toString());
            }
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBES2)) {
            try {
                return createCipher(i, cArr, algorithmIdentifier).doFinal(bArr);
            } catch (java.lang.Exception e2) {
                throw new java.io.IOException("exception decrypting data - " + e2.toString());
            }
        }
        throw new java.io.IOException("unknown PBE algorithm: " + algorithm);
    }

    private javax.crypto.Cipher createCipher(int i, char[] cArr, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, java.security.NoSuchProviderException {
        javax.crypto.SecretKey generateSecret;
        com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters pBES2Parameters = com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters.getInstance(algorithmIdentifier.getParameters());
        com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params pBKDF2Params = com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params.getInstance(pBES2Parameters.getKeyDerivationFunc().getParameters());
        com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2 = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(pBES2Parameters.getEncryptionScheme());
        javax.crypto.SecretKeyFactory createSecretKeyFactory = this.selfHelper.createSecretKeyFactory(pBES2Parameters.getKeyDerivationFunc().getAlgorithm().getId());
        if (pBKDF2Params.isDefaultPrf()) {
            generateSecret = createSecretKeyFactory.generateSecret(new javax.crypto.spec.PBEKeySpec(cArr, pBKDF2Params.getSalt(), validateIterationCount(pBKDF2Params.getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier2)));
        } else {
            generateSecret = createSecretKeyFactory.generateSecret(new com.android.internal.org.bouncycastle.jcajce.spec.PBKDF2KeySpec(cArr, pBKDF2Params.getSalt(), validateIterationCount(pBKDF2Params.getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier2), pBKDF2Params.getPrf()));
        }
        javax.crypto.Cipher createCipher = this.selfHelper.createCipher(pBES2Parameters.getEncryptionScheme().getAlgorithm().getId());
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = pBES2Parameters.getEncryptionScheme().getParameters();
        if (parameters instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            createCipher.init(i, generateSecret, new javax.crypto.spec.IvParameterSpec(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(parameters).getOctets()));
        }
        return createCipher;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v22, types: [com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r11v26, types: [com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier] */
    /* JADX WARN: Type inference failed for: r11v27, types: [com.android.internal.org.bouncycastle.asn1.ASN1Primitive] */
    /* JADX WARN: Type inference failed for: r13v27, types: [com.android.internal.org.bouncycastle.asn1.ASN1Encodable, com.android.internal.org.bouncycastle.asn1.ASN1Primitive] */
    /* JADX WARN: Type inference failed for: r17v10, types: [com.android.internal.org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARN: Type inference failed for: r17v11 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6, types: [com.android.internal.org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARN: Type inference failed for: r17v7 */
    /* JADX WARN: Type inference failed for: r17v8 */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.Object, java.security.cert.Certificate] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v30, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v37 */
    @Override // java.security.KeyStoreSpi
    public void engineLoad(java.io.InputStream inputStream, char[] cArr) throws java.io.IOException {
        boolean z;
        boolean z2;
        java.lang.String str;
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString;
        int i;
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence;
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive;
        ?? r5;
        ?? r17;
        com.android.internal.org.bouncycastle.asn1.DERBMPString dERBMPString;
        boolean z3;
        if (inputStream == null) {
            return;
        }
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream);
        bufferedInputStream.mark(10);
        if (bufferedInputStream.read() != 48) {
            throw new java.io.IOException("stream does not represent a PKCS12 key store");
        }
        bufferedInputStream.reset();
        try {
            com.android.internal.org.bouncycastle.asn1.pkcs.Pfx pfx = com.android.internal.org.bouncycastle.asn1.pkcs.Pfx.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bufferedInputStream).readObject());
            com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo authSafe = pfx.getAuthSafe();
            java.util.Vector vector = new java.util.Vector();
            int i2 = 1;
            int i3 = 0;
            if (pfx.getMacData() == null) {
                z = false;
            } else {
                if (cArr == null) {
                    throw new java.lang.NullPointerException("no password supplied when one expected");
                }
                com.android.internal.org.bouncycastle.asn1.pkcs.MacData macData = pfx.getMacData();
                com.android.internal.org.bouncycastle.asn1.x509.DigestInfo mac = macData.getMac();
                this.macAlgorithm = mac.getAlgorithmId();
                byte[] salt = macData.getSalt();
                this.itCount = validateIterationCount(macData.getIterationCount());
                this.saltLength = salt.length;
                byte[] octets = ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) authSafe.getContent()).getOctets();
                try {
                    byte[] calculatePbeMac = calculatePbeMac(this.macAlgorithm.getAlgorithm(), salt, this.itCount, cArr, false, octets);
                    byte[] digest = mac.getDigest();
                    if (com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(calculatePbeMac, digest)) {
                        z3 = false;
                    } else {
                        if (cArr.length > 0) {
                            throw new java.io.IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }
                        if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(calculatePbeMac(this.macAlgorithm.getAlgorithm(), salt, this.itCount, cArr, true, octets), digest)) {
                            throw new java.io.IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }
                        z3 = true;
                    }
                    z = z3;
                } catch (java.io.IOException e) {
                    throw e;
                } catch (java.lang.Exception e2) {
                    throw new java.io.IOException("error constructing MAC: " + e2.toString());
                }
            }
            com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtableIA ignoresCaseHashtableIA = null;
            this.keys = new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable();
            this.localIds = new java.util.Hashtable();
            if (!authSafe.getContentType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) data)) {
                z2 = false;
            } else {
                com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] contentInfo = com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(authSafe.getContent()).getOctets()).getContentInfo();
                int i4 = 0;
                z2 = false;
                while (i4 != contentInfo.length) {
                    if (contentInfo[i4].getContentType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) data)) {
                        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(contentInfo[i4].getContent()).getOctets());
                        int i5 = i3;
                        while (i5 != aSN1Sequence2.size()) {
                            com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag safeBag = com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag.getInstance(aSN1Sequence2.getObjectAt(i5));
                            if (safeBag.getBagId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs8ShroudedKeyBag)) {
                                com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(safeBag.getBagValue());
                                java.security.PrivateKey unwrapKey = unwrapKey(encryptedPrivateKeyInfo.getEncryptionAlgorithm(), encryptedPrivateKeyInfo.getEncryptedData(), cArr, z);
                                if (safeBag.getBagAttributes() == null) {
                                    r5 = 0;
                                    r17 = null;
                                } else {
                                    java.util.Enumeration objects = safeBag.getBagAttributes().getObjects();
                                    com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtableIA ignoresCaseHashtableIA2 = ignoresCaseHashtableIA;
                                    r17 = ignoresCaseHashtableIA2;
                                    r5 = ignoresCaseHashtableIA2;
                                    while (objects.hasMoreElements()) {
                                        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence3 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) objects.nextElement();
                                        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence3.getObjectAt(i3);
                                        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Sequence3.getObjectAt(i2);
                                        if (aSN1Set.size() <= 0) {
                                            dERBMPString = null;
                                        } else {
                                            ?? r13 = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1Set.getObjectAt(0);
                                            dERBMPString = r13;
                                            if (unwrapKey instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) {
                                                ?? r11 = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) unwrapKey;
                                                com.android.internal.org.bouncycastle.asn1.ASN1Encodable bagAttribute = r11.getBagAttribute(aSN1ObjectIdentifier);
                                                if (bagAttribute != null) {
                                                    boolean equals = bagAttribute.toASN1Primitive().equals(r13);
                                                    dERBMPString = r13;
                                                    if (!equals) {
                                                        throw new java.io.IOException("attempt to add existing attribute with different value");
                                                    }
                                                } else {
                                                    r11.setBagAttribute(aSN1ObjectIdentifier, r13);
                                                    dERBMPString = r13;
                                                }
                                            }
                                        }
                                        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_friendlyName)) {
                                            r5 = dERBMPString.getString();
                                            this.keys.put(r5, unwrapKey);
                                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_localKeyId)) {
                                            r17 = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) dERBMPString;
                                        }
                                        i2 = 1;
                                        i3 = 0;
                                        r5 = r5;
                                    }
                                }
                                if (r17 != null) {
                                    java.lang.String str2 = new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(r17.getOctets()));
                                    if (r5 == 0) {
                                        this.keys.put(str2, unwrapKey);
                                    } else {
                                        this.localIds.put(r5, str2);
                                    }
                                } else {
                                    this.keys.put("unmarked", unwrapKey);
                                    z2 = true;
                                }
                            } else if (safeBag.getBagId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) certBag)) {
                                vector.addElement(safeBag);
                            } else {
                                java.lang.System.out.println("extra in data " + safeBag.getBagId());
                                java.lang.System.out.println(com.android.internal.org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(safeBag));
                            }
                            i5++;
                            i2 = 1;
                            i3 = 0;
                            ignoresCaseHashtableIA = null;
                        }
                        i = i4;
                    } else if (contentInfo[i4].getContentType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) encryptedData)) {
                        com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData encryptedData = com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData.getInstance(contentInfo[i4].getContent());
                        i = i4;
                        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence4 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(cryptData(false, encryptedData.getEncryptionAlgorithm(), cArr, z, encryptedData.getContent().getOctets()));
                        int i6 = 0;
                        while (i6 != aSN1Sequence4.size()) {
                            com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag safeBag2 = com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag.getInstance(aSN1Sequence4.getObjectAt(i6));
                            if (safeBag2.getBagId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) certBag)) {
                                vector.addElement(safeBag2);
                                aSN1Sequence = aSN1Sequence4;
                            } else if (safeBag2.getBagId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs8ShroudedKeyBag)) {
                                com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo encryptedPrivateKeyInfo2 = com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(safeBag2.getBagValue());
                                java.security.PrivateKey unwrapKey2 = unwrapKey(encryptedPrivateKeyInfo2.getEncryptionAlgorithm(), encryptedPrivateKeyInfo2.getEncryptedData(), cArr, z);
                                com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) unwrapKey2;
                                java.util.Enumeration objects2 = safeBag2.getBagAttributes().getObjects();
                                com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString2 = null;
                                java.lang.String str3 = null;
                                while (objects2.hasMoreElements()) {
                                    com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence5 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) objects2.nextElement();
                                    com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence6 = aSN1Sequence4;
                                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence5.getObjectAt(0);
                                    java.util.Enumeration enumeration = objects2;
                                    com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2 = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Sequence5.getObjectAt(1);
                                    if (aSN1Set2.size() <= 0) {
                                        aSN1Primitive = null;
                                    } else {
                                        aSN1Primitive = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1Set2.getObjectAt(0);
                                        com.android.internal.org.bouncycastle.asn1.ASN1Encodable bagAttribute2 = pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2);
                                        if (bagAttribute2 != null) {
                                            if (!bagAttribute2.toASN1Primitive().equals(aSN1Primitive)) {
                                                throw new java.io.IOException("attempt to add existing attribute with different value");
                                            }
                                        } else {
                                            pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier2, aSN1Primitive);
                                        }
                                    }
                                    if (aSN1ObjectIdentifier2.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_friendlyName)) {
                                        str3 = ((com.android.internal.org.bouncycastle.asn1.DERBMPString) aSN1Primitive).getString();
                                        this.keys.put(str3, unwrapKey2);
                                    } else if (aSN1ObjectIdentifier2.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_localKeyId)) {
                                        aSN1OctetString2 = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive;
                                    }
                                    aSN1Sequence4 = aSN1Sequence6;
                                    objects2 = enumeration;
                                }
                                aSN1Sequence = aSN1Sequence4;
                                java.lang.String str4 = new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(aSN1OctetString2.getOctets()));
                                if (str3 == null) {
                                    this.keys.put(str4, unwrapKey2);
                                } else {
                                    this.localIds.put(str3, str4);
                                }
                            } else {
                                aSN1Sequence = aSN1Sequence4;
                                if (safeBag2.getBagId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) keyBag)) {
                                    java.security.PrivateKey privateKey = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(safeBag2.getBagValue()));
                                    com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) privateKey;
                                    java.util.Enumeration objects3 = safeBag2.getBagAttributes().getObjects();
                                    com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString3 = null;
                                    java.lang.String str5 = null;
                                    while (objects3.hasMoreElements()) {
                                        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence7 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(objects3.nextElement());
                                        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier3 = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence7.getObjectAt(0));
                                        java.util.Enumeration enumeration2 = objects3;
                                        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set3 = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1Sequence7.getObjectAt(1));
                                        if (aSN1Set3.size() > 0) {
                                            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2 = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1Set3.getObjectAt(0);
                                            com.android.internal.org.bouncycastle.asn1.ASN1Encodable bagAttribute3 = pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier3);
                                            if (bagAttribute3 != null) {
                                                if (!bagAttribute3.toASN1Primitive().equals(aSN1Primitive2)) {
                                                    throw new java.io.IOException("attempt to add existing attribute with different value");
                                                }
                                            } else {
                                                pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier3, aSN1Primitive2);
                                            }
                                            if (aSN1ObjectIdentifier3.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_friendlyName)) {
                                                str5 = ((com.android.internal.org.bouncycastle.asn1.DERBMPString) aSN1Primitive2).getString();
                                                this.keys.put(str5, privateKey);
                                            } else if (aSN1ObjectIdentifier3.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_localKeyId)) {
                                                aSN1OctetString3 = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive2;
                                            }
                                        }
                                        objects3 = enumeration2;
                                    }
                                    java.lang.String str6 = new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(aSN1OctetString3.getOctets()));
                                    if (str5 == null) {
                                        this.keys.put(str6, privateKey);
                                    } else {
                                        this.localIds.put(str5, str6);
                                    }
                                } else {
                                    java.lang.System.out.println("extra in encryptedData " + safeBag2.getBagId());
                                    java.lang.System.out.println(com.android.internal.org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(safeBag2));
                                }
                            }
                            i6++;
                            aSN1Sequence4 = aSN1Sequence;
                        }
                    } else {
                        i = i4;
                        java.lang.System.out.println("extra " + contentInfo[i].getContentType().getId());
                        java.lang.System.out.println("extra " + com.android.internal.org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(contentInfo[i].getContent()));
                    }
                    i4 = i + 1;
                    i2 = 1;
                    i3 = 0;
                    ignoresCaseHashtableIA = null;
                }
            }
            this.certs = new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.IgnoresCaseHashtable();
            this.chainCerts = new java.util.Hashtable();
            this.keyCerts = new java.util.Hashtable();
            for (int i7 = 0; i7 != vector.size(); i7++) {
                com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag safeBag3 = (com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag) vector.elementAt(i7);
                com.android.internal.org.bouncycastle.asn1.pkcs.CertBag certBag = com.android.internal.org.bouncycastle.asn1.pkcs.CertBag.getInstance(safeBag3.getBagValue());
                if (!certBag.getCertId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) x509Certificate)) {
                    throw new java.lang.RuntimeException("Unsupported certificate type: " + certBag.getCertId());
                }
                try {
                    ?? generateCertificate = this.certFact.generateCertificate(new java.io.ByteArrayInputStream(((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) certBag.getCertValue()).getOctets()));
                    if (safeBag3.getBagAttributes() != null) {
                        java.util.Enumeration objects4 = safeBag3.getBagAttributes().getObjects();
                        str = null;
                        aSN1OctetString = null;
                        while (objects4.hasMoreElements()) {
                            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence8 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(objects4.nextElement());
                            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier4 = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence8.getObjectAt(0));
                            com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set4 = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1Sequence8.getObjectAt(1));
                            if (aSN1Set4.size() > 0) {
                                com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive3 = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1Set4.getObjectAt(0);
                                if (generateCertificate instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) {
                                    com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) generateCertificate;
                                    com.android.internal.org.bouncycastle.asn1.ASN1Encodable bagAttribute4 = pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier4);
                                    if (bagAttribute4 != null) {
                                        if (!bagAttribute4.toASN1Primitive().equals(aSN1Primitive3)) {
                                            throw new java.io.IOException("attempt to add existing attribute with different value");
                                        }
                                    } else {
                                        pKCS12BagAttributeCarrier3.setBagAttribute(aSN1ObjectIdentifier4, aSN1Primitive3);
                                    }
                                }
                                if (aSN1ObjectIdentifier4.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_friendlyName)) {
                                    str = ((com.android.internal.org.bouncycastle.asn1.DERBMPString) aSN1Primitive3).getString();
                                } else if (aSN1ObjectIdentifier4.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) pkcs_9_at_localKeyId)) {
                                    aSN1OctetString = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive3;
                                }
                            }
                        }
                    } else {
                        str = null;
                        aSN1OctetString = null;
                    }
                    this.chainCerts.put(new com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId(generateCertificate.getPublicKey()), generateCertificate);
                    if (z2) {
                        if (this.keyCerts.isEmpty()) {
                            java.lang.String str7 = new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(createSubjectKeyId(generateCertificate.getPublicKey()).getKeyIdentifier()));
                            this.keyCerts.put(str7, generateCertificate);
                            this.keys.put(str7, this.keys.remove("unmarked"));
                        }
                    } else {
                        if (aSN1OctetString != null) {
                            this.keyCerts.put(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(aSN1OctetString.getOctets())), generateCertificate);
                        }
                        if (str != null) {
                            this.certs.put(str, generateCertificate);
                        }
                    }
                } catch (java.lang.Exception e3) {
                    throw new java.lang.RuntimeException(e3.toString());
                }
            }
        } catch (java.lang.Exception e4) {
            throw new java.io.IOException(e4.getMessage());
        }
    }

    private int validateIterationCount(java.math.BigInteger bigInteger) {
        int intValue = bigInteger.intValue();
        if (intValue < 0) {
            throw new java.lang.IllegalStateException("negative iteration count found");
        }
        java.math.BigInteger asBigInteger = com.android.internal.org.bouncycastle.util.Properties.asBigInteger(PKCS12_MAX_IT_COUNT_PROPERTY);
        if (asBigInteger != null && asBigInteger.intValue() < intValue) {
            throw new java.lang.IllegalStateException("iteration count " + intValue + " greater than " + asBigInteger.intValue());
        }
        return intValue;
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(java.security.KeyStore.LoadStoreParameter loadStoreParameter) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.cert.CertificateException {
        com.android.internal.org.bouncycastle.jcajce.PKCS12StoreParameter pKCS12StoreParameter;
        char[] password;
        if (loadStoreParameter == null) {
            throw new java.lang.IllegalArgumentException("'param' arg cannot be null");
        }
        boolean z = loadStoreParameter instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12StoreParameter;
        if (!z && !(loadStoreParameter instanceof com.android.internal.org.bouncycastle.jce.provider.JDKPKCS12StoreParameter)) {
            throw new java.lang.IllegalArgumentException("No support for 'param' of type " + loadStoreParameter.getClass().getName());
        }
        if (z) {
            pKCS12StoreParameter = (com.android.internal.org.bouncycastle.jcajce.PKCS12StoreParameter) loadStoreParameter;
        } else {
            com.android.internal.org.bouncycastle.jce.provider.JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (com.android.internal.org.bouncycastle.jce.provider.JDKPKCS12StoreParameter) loadStoreParameter;
            pKCS12StoreParameter = new com.android.internal.org.bouncycastle.jcajce.PKCS12StoreParameter(jDKPKCS12StoreParameter.getOutputStream(), loadStoreParameter.getProtectionParameter(), jDKPKCS12StoreParameter.isUseDEREncoding());
        }
        java.security.KeyStore.ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
        if (protectionParameter == null) {
            password = null;
        } else if (protectionParameter instanceof java.security.KeyStore.PasswordProtection) {
            password = ((java.security.KeyStore.PasswordProtection) protectionParameter).getPassword();
        } else {
            throw new java.lang.IllegalArgumentException("No support for protection parameter of type " + protectionParameter.getClass().getName());
        }
        doStore(pKCS12StoreParameter.getOutputStream(), password, pKCS12StoreParameter.isForDEREncoding());
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(java.io.OutputStream outputStream, char[] cArr) throws java.io.IOException {
        doStore(outputStream, cArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void doStore(java.io.OutputStream outputStream, char[] cArr, boolean z) throws java.io.IOException {
        java.util.Enumeration enumeration;
        boolean z2;
        boolean z3;
        if (cArr == null) {
            throw new java.lang.NullPointerException("No password supplied for PKCS#12 KeyStore.");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.Enumeration keys = this.keys.keys();
        while (keys.hasMoreElements()) {
            byte[] bArr = new byte[20];
            this.random.nextBytes(bArr);
            java.lang.String str = (java.lang.String) keys.nextElement();
            java.security.PrivateKey privateKey = (java.security.PrivateKey) this.keys.get(str);
            com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams pKCS12PBEParams = new com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams(bArr, MIN_ITERATIONS);
            com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(this.keyAlgorithm, pKCS12PBEParams.toASN1Primitive()), wrapKey(this.keyAlgorithm.getId(), privateKey, pKCS12PBEParams, cArr));
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            if (!(privateKey instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier)) {
                z3 = false;
            } else {
                com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) privateKey;
                com.android.internal.org.bouncycastle.asn1.DERBMPString dERBMPString = (com.android.internal.org.bouncycastle.asn1.DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
                if (dERBMPString == null || !dERBMPString.getString().equals(str)) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new com.android.internal.org.bouncycastle.asn1.DERBMPString(str));
                }
                if (pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate(str).getPublicKey()));
                }
                java.util.Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                z3 = false;
                while (bagAttributeKeys.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector3 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    aSN1EncodableVector3.add(aSN1ObjectIdentifier);
                    aSN1EncodableVector3.add(new com.android.internal.org.bouncycastle.asn1.DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier)));
                    aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector3));
                    z3 = true;
                }
            }
            if (!z3) {
                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector4 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                java.security.cert.Certificate engineGetCertificate = engineGetCertificate(str);
                aSN1EncodableVector4.add(pkcs_9_at_localKeyId);
                aSN1EncodableVector4.add(new com.android.internal.org.bouncycastle.asn1.DERSet(createSubjectKeyId(engineGetCertificate.getPublicKey())));
                aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector4));
                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector5 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                aSN1EncodableVector5.add(pkcs_9_at_friendlyName);
                aSN1EncodableVector5.add(new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.DERBMPString(str)));
                aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector5));
            }
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag(pkcs8ShroudedKeyBag, encryptedPrivateKeyInfo.toASN1Primitive(), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2)));
        }
        com.android.internal.org.bouncycastle.asn1.DERSequence dERSequence = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
        java.lang.String str2 = com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER;
        com.android.internal.org.bouncycastle.asn1.BEROctetString bEROctetString = new com.android.internal.org.bouncycastle.asn1.BEROctetString(dERSequence.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        byte[] bArr2 = new byte[20];
        this.random.nextBytes(bArr2);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector6 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(this.certAlgorithm, new com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams(bArr2, MIN_ITERATIONS).toASN1Primitive());
        java.util.Hashtable hashtable = new java.util.Hashtable();
        java.util.Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            try {
                java.lang.String str3 = (java.lang.String) keys2.nextElement();
                java.security.cert.Certificate engineGetCertificate2 = engineGetCertificate(str3);
                com.android.internal.org.bouncycastle.asn1.pkcs.CertBag certBag = new com.android.internal.org.bouncycastle.asn1.pkcs.CertBag(x509Certificate, new com.android.internal.org.bouncycastle.asn1.DEROctetString(engineGetCertificate2.getEncoded()));
                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector7 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                if (!(engineGetCertificate2 instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier)) {
                    enumeration = keys2;
                    z2 = false;
                } else {
                    com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) engineGetCertificate2;
                    com.android.internal.org.bouncycastle.asn1.DERBMPString dERBMPString2 = (com.android.internal.org.bouncycastle.asn1.DERBMPString) pKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_friendlyName);
                    if (dERBMPString2 == null || !dERBMPString2.getString().equals(str3)) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_friendlyName, new com.android.internal.org.bouncycastle.asn1.DERBMPString(str3));
                    }
                    if (pKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate2.getPublicKey()));
                    }
                    java.util.Enumeration bagAttributeKeys2 = pKCS12BagAttributeCarrier2.getBagAttributeKeys();
                    z2 = false;
                    while (bagAttributeKeys2.hasMoreElements()) {
                        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) bagAttributeKeys2.nextElement();
                        java.util.Enumeration enumeration2 = keys2;
                        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector8 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                        aSN1EncodableVector8.add(aSN1ObjectIdentifier2);
                        aSN1EncodableVector8.add(new com.android.internal.org.bouncycastle.asn1.DERSet(pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier2)));
                        aSN1EncodableVector7.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector8));
                        keys2 = enumeration2;
                        z2 = true;
                    }
                    enumeration = keys2;
                }
                if (!z2) {
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector9 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    aSN1EncodableVector9.add(pkcs_9_at_localKeyId);
                    aSN1EncodableVector9.add(new com.android.internal.org.bouncycastle.asn1.DERSet(createSubjectKeyId(engineGetCertificate2.getPublicKey())));
                    aSN1EncodableVector7.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector9));
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector10 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    aSN1EncodableVector10.add(pkcs_9_at_friendlyName);
                    aSN1EncodableVector10.add(new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.DERBMPString(str3)));
                    aSN1EncodableVector7.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector10));
                }
                aSN1EncodableVector6.add(new com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag(certBag, certBag.toASN1Primitive(), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector7)));
                hashtable.put(engineGetCertificate2, engineGetCertificate2);
                keys2 = enumeration;
            } catch (java.security.cert.CertificateEncodingException e) {
                throw new java.io.IOException("Error encoding certificate: " + e.toString());
            }
        }
        java.util.Enumeration keys3 = this.certs.keys();
        while (keys3.hasMoreElements()) {
            try {
                java.lang.String str4 = (java.lang.String) keys3.nextElement();
                java.security.cert.Certificate certificate = (java.security.cert.Certificate) this.certs.get(str4);
                if (this.keys.get(str4) == null) {
                    aSN1EncodableVector6.add(createSafeBag(str4, certificate));
                    hashtable.put(certificate, certificate);
                }
            } catch (java.security.cert.CertificateEncodingException e2) {
                throw new java.io.IOException("Error encoding certificate: " + e2.toString());
            }
        }
        java.util.Set usedCertificateSet = getUsedCertificateSet();
        java.util.Enumeration keys4 = this.chainCerts.keys();
        while (keys4.hasMoreElements()) {
            try {
                java.security.cert.Certificate certificate2 = (java.security.cert.Certificate) this.chainCerts.get((com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId) keys4.nextElement());
                if (usedCertificateSet.contains(certificate2) && hashtable.get(certificate2) == null) {
                    com.android.internal.org.bouncycastle.asn1.pkcs.CertBag certBag2 = new com.android.internal.org.bouncycastle.asn1.pkcs.CertBag(x509Certificate, new com.android.internal.org.bouncycastle.asn1.DEROctetString(certificate2.getEncoded()));
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector11 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    if (certificate2 instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) {
                        com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) certificate2;
                        java.util.Enumeration bagAttributeKeys3 = pKCS12BagAttributeCarrier3.getBagAttributeKeys();
                        while (bagAttributeKeys3.hasMoreElements()) {
                            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier3 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) bagAttributeKeys3.nextElement();
                            if (!aSN1ObjectIdentifier3.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector12 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                                aSN1EncodableVector12.add(aSN1ObjectIdentifier3);
                                aSN1EncodableVector12.add(new com.android.internal.org.bouncycastle.asn1.DERSet(pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier3)));
                                aSN1EncodableVector11.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector12));
                            }
                        }
                    }
                    aSN1EncodableVector6.add(new com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag(certBag, certBag2.toASN1Primitive(), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector11)));
                }
            } catch (java.security.cert.CertificateEncodingException e3) {
                throw new java.io.IOException("Error encoding certificate: " + e3.toString());
            }
        }
        com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(data, new com.android.internal.org.bouncycastle.asn1.BEROctetString(new com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe(new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[]{new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(data, bEROctetString), new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(encryptedData, new com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData(data, algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.BEROctetString(cryptData(true, algorithmIdentifier, cArr, false, new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector6).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER)))).toASN1Primitive())}).getEncoded(z ? com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER : com.android.internal.org.bouncycastle.asn1.ASN1Encoding.BER)));
        byte[] bArr3 = new byte[this.saltLength];
        this.random.nextBytes(bArr3);
        try {
            com.android.internal.org.bouncycastle.asn1.pkcs.Pfx pfx = new com.android.internal.org.bouncycastle.asn1.pkcs.Pfx(contentInfo, new com.android.internal.org.bouncycastle.asn1.pkcs.MacData(new com.android.internal.org.bouncycastle.asn1.x509.DigestInfo(this.macAlgorithm, calculatePbeMac(this.macAlgorithm.getAlgorithm(), bArr3, this.itCount, cArr, false, ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) contentInfo.getContent()).getOctets())), bArr3, this.itCount));
            if (!z) {
                str2 = com.android.internal.org.bouncycastle.asn1.ASN1Encoding.BER;
            }
            pfx.encodeTo(outputStream, str2);
        } catch (java.lang.Exception e4) {
            throw new java.io.IOException("error constructing MAC: " + e4.toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag createSafeBag(java.lang.String str, java.security.cert.Certificate certificate) throws java.security.cert.CertificateEncodingException {
        com.android.internal.org.bouncycastle.asn1.pkcs.CertBag certBag = new com.android.internal.org.bouncycastle.asn1.pkcs.CertBag(x509Certificate, new com.android.internal.org.bouncycastle.asn1.DEROctetString(certificate.getEncoded()));
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        boolean z = false;
        if (certificate instanceof com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) {
            com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) certificate;
            com.android.internal.org.bouncycastle.asn1.DERBMPString dERBMPString = (com.android.internal.org.bouncycastle.asn1.DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
            if ((dERBMPString == null || !dERBMPString.getString().equals(str)) && str != null) {
                pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new com.android.internal.org.bouncycastle.asn1.DERBMPString(str));
            }
            java.util.Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
            while (bagAttributeKeys.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                if (!aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                    aSN1EncodableVector2.add(aSN1ObjectIdentifier);
                    aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier)));
                    aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
                    z = true;
                }
            }
        }
        if (!z) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector3 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            aSN1EncodableVector3.add(pkcs_9_at_friendlyName);
            aSN1EncodableVector3.add(new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.DERBMPString(str)));
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector3));
        }
        return new com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag(certBag, certBag.toASN1Primitive(), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector));
    }

    private java.util.Set getUsedCertificateSet() {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Enumeration keys = this.keys.keys();
        while (keys.hasMoreElements()) {
            java.security.cert.Certificate[] engineGetCertificateChain = engineGetCertificateChain((java.lang.String) keys.nextElement());
            for (int i = 0; i != engineGetCertificateChain.length; i++) {
                hashSet.add(engineGetCertificateChain[i]);
            }
        }
        java.util.Enumeration keys2 = this.certs.keys();
        while (keys2.hasMoreElements()) {
            hashSet.add(engineGetCertificate((java.lang.String) keys2.nextElement()));
        }
        return hashSet;
    }

    private byte[] calculatePbeMac(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr, int i, char[] cArr, boolean z, byte[] bArr2) throws java.lang.Exception {
        javax.crypto.spec.PBEParameterSpec pBEParameterSpec = new javax.crypto.spec.PBEParameterSpec(bArr, i);
        javax.crypto.Mac createMac = this.selfHelper.createMac(aSN1ObjectIdentifier.getId());
        createMac.init(new com.android.internal.org.bouncycastle.jcajce.PKCS12Key(cArr, z), pBEParameterSpec);
        createMac.update(bArr2);
        return createMac.doFinal();
    }

    public static class BCPKCS12KeyStore extends com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    private static class IgnoresCaseHashtable {
        private java.util.Hashtable keys;
        private java.util.Hashtable orig;

        private IgnoresCaseHashtable() {
            this.orig = new java.util.Hashtable();
            this.keys = new java.util.Hashtable();
        }

        public void put(java.lang.String str, java.lang.Object obj) {
            java.lang.String lowerCase = str == null ? null : com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str);
            java.lang.String str2 = (java.lang.String) this.keys.get(lowerCase);
            if (str2 != null) {
                this.orig.remove(str2);
            }
            this.keys.put(lowerCase, str);
            this.orig.put(str, obj);
        }

        public java.util.Enumeration keys() {
            return this.orig.keys();
        }

        public java.lang.Object remove(java.lang.String str) {
            java.lang.String str2 = (java.lang.String) this.keys.remove(str == null ? null : com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.remove(str2);
        }

        public java.lang.Object get(java.lang.String str) {
            java.lang.String str2 = (java.lang.String) this.keys.get(str == null ? null : com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.get(str2);
        }

        public java.util.Enumeration elements() {
            return this.orig.elements();
        }

        public int size() {
            return this.orig.size();
        }
    }

    private static class DefaultSecretKeyProvider {
        private final java.util.Map KEY_SIZES;

        DefaultSecretKeyProvider() {
            java.util.HashMap hashMap = new java.util.HashMap();
            hashMap.put(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
            hashMap.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
            hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
            hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
            hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(256));
            this.KEY_SIZES = java.util.Collections.unmodifiableMap(hashMap);
        }

        public int getKeySize(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
            java.lang.Integer num = (java.lang.Integer) this.KEY_SIZES.get(algorithmIdentifier.getAlgorithm());
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
    }
}
