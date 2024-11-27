package com.android.internal.org.bouncycastle.jce;

/* loaded from: classes4.dex */
public class PKCS10CertificationRequest extends com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequest {
    private static java.util.Hashtable algorithms = new java.util.Hashtable();
    private static java.util.Hashtable params = new java.util.Hashtable();
    private static java.util.Hashtable keyAlgorithms = new java.util.Hashtable();
    private static java.util.Hashtable oids = new java.util.Hashtable();
    private static java.util.Set noParams = new java.util.HashSet();

    static {
        algorithms.put("MD5WITHRSAENCRYPTION", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("MD5WITHRSA", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("RSAWITHMD5", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        algorithms.put("SHA1WITHRSAENCRYPTION", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA1WITHRSA", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA224WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA224WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA256WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA256WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA384WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA384WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA512WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA512WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA1WITHRSAANDMGF1", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA224WITHRSAANDMGF1", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA256WITHRSAANDMGF1", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA384WITHRSAANDMGF1", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA512WITHRSAANDMGF1", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("RSAWITHSHA1", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        algorithms.put("SHA1WITHDSA", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        algorithms.put("DSAWITHSHA1", new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        algorithms.put("SHA224WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224);
        algorithms.put("SHA256WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256);
        algorithms.put("SHA384WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512);
        algorithms.put("SHA1WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("SHA224WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224);
        algorithms.put("SHA256WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256);
        algorithms.put("SHA384WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384);
        algorithms.put("SHA512WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512);
        algorithms.put("ECDSAWITHSHA1", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        oids.put(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        oids.put(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        oids.put(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        keyAlgorithms.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        keyAlgorithms.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, "DSA");
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256);
        params.put("SHA1WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 20));
        params.put("SHA224WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 28));
        params.put("SHA256WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 32));
        params.put("SHA384WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 48));
        params.put("SHA512WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 64));
    }

    private static com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams creatPSSParams(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, int i) {
        return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L));
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence toDERSequence(byte[] bArr) {
        try {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bArr).readObject();
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException("badly encoded request");
        }
    }

    public PKCS10CertificationRequest(byte[] bArr) {
        super(toDERSequence(bArr));
    }

    public PKCS10CertificationRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        super(aSN1Sequence);
    }

    public PKCS10CertificationRequest(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, java.security.PrivateKey privateKey) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        this(str, x509Name, publicKey, aSN1Set, privateKey, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.X509Name convertName(javax.security.auth.x500.X500Principal x500Principal) {
        try {
            return new com.android.internal.org.bouncycastle.jce.X509Principal(x500Principal.getEncoded());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("can't convert name");
        }
    }

    public PKCS10CertificationRequest(java.lang.String str, javax.security.auth.x500.X500Principal x500Principal, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, java.security.PrivateKey privateKey) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        this(str, convertName(x500Principal), publicKey, aSN1Set, privateKey, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
    }

    public PKCS10CertificationRequest(java.lang.String str, javax.security.auth.x500.X500Principal x500Principal, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, java.security.PrivateKey privateKey, java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        this(str, convertName(x500Principal), publicKey, aSN1Set, privateKey, str2);
    }

    public PKCS10CertificationRequest(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, java.security.PrivateKey privateKey, java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        java.security.Signature signature;
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) algorithms.get(upperCase);
        if (aSN1ObjectIdentifier == null) {
            try {
                aSN1ObjectIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(upperCase);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("Unknown signature type requested");
            }
        }
        if (x509Name == null) {
            throw new java.lang.IllegalArgumentException("subject must not be null");
        }
        if (publicKey == null) {
            throw new java.lang.IllegalArgumentException("public key must not be null");
        }
        if (noParams.contains(aSN1ObjectIdentifier)) {
            this.sigAlgId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier);
        } else if (params.containsKey(upperCase)) {
            this.sigAlgId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) params.get(upperCase));
        } else {
            this.sigAlgId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        try {
            this.reqInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo(x509Name, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(publicKey.getEncoded())), aSN1Set);
            if (str2 == null) {
                signature = java.security.Signature.getInstance(str);
            } else {
                signature = java.security.Signature.getInstance(str, str2);
            }
            signature.initSign(privateKey);
            try {
                signature.update(this.reqInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
                this.sigBits = new com.android.internal.org.bouncycastle.asn1.DERBitString(signature.sign());
            } catch (java.lang.Exception e2) {
                throw new java.lang.IllegalArgumentException("exception encoding TBS cert request - " + e2);
            }
        } catch (java.io.IOException e3) {
            throw new java.lang.IllegalArgumentException("can't encode public key");
        }
    }

    public java.security.PublicKey getPublicKey() throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException {
        return getPublicKey(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
    }

    public java.security.PublicKey getPublicKey(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo = this.reqInfo.getSubjectPublicKeyInfo();
        try {
            java.security.spec.X509EncodedKeySpec x509EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(new com.android.internal.org.bouncycastle.asn1.DERBitString(subjectPublicKeyInfo).getOctets());
            com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm();
            try {
                if (str == null) {
                    return java.security.KeyFactory.getInstance(algorithm.getAlgorithm().getId()).generatePublic(x509EncodedKeySpec);
                }
                return java.security.KeyFactory.getInstance(algorithm.getAlgorithm().getId(), str).generatePublic(x509EncodedKeySpec);
            } catch (java.security.NoSuchAlgorithmException e) {
                if (keyAlgorithms.get(algorithm.getAlgorithm()) != null) {
                    java.lang.String str2 = (java.lang.String) keyAlgorithms.get(algorithm.getAlgorithm());
                    if (str == null) {
                        return java.security.KeyFactory.getInstance(str2).generatePublic(x509EncodedKeySpec);
                    }
                    return java.security.KeyFactory.getInstance(str2, str).generatePublic(x509EncodedKeySpec);
                }
                throw e;
            }
        } catch (java.io.IOException e2) {
            throw new java.security.InvalidKeyException("error decoding public key");
        } catch (java.security.spec.InvalidKeySpecException e3) {
            throw new java.security.InvalidKeyException("error decoding public key");
        }
    }

    public boolean verify() throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        return verify(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
    }

    public boolean verify(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        return verify(getPublicKey(str), str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public boolean verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.InvalidKeyException, java.security.SignatureException {
        try {
            if (str == 0) {
                str = java.security.Signature.getInstance(getSignatureName(this.sigAlgId));
            } else {
                str = java.security.Signature.getInstance(getSignatureName(this.sigAlgId), str);
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            if (oids.get(this.sigAlgId.getAlgorithm()) != null) {
                java.lang.String str2 = (java.lang.String) oids.get(this.sigAlgId.getAlgorithm());
                if (str == 0) {
                    str = java.security.Signature.getInstance(str2);
                } else {
                    str = java.security.Signature.getInstance(str2, str);
                }
            } else {
                throw e;
            }
        }
        setSignatureParameters(str, this.sigAlgId.getParameters());
        str.initVerify(publicKey);
        try {
            str.update(this.reqInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
            return str.verify(this.sigBits.getOctets());
        } catch (java.lang.Exception e2) {
            throw new java.security.SignatureException("exception encoding TBS cert request - " + e2);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        try {
            return getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    private void setSignatureParameters(java.security.Signature signature, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        if (aSN1Encodable != null && !com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE.equals(aSN1Encodable)) {
            java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
            try {
                algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
                if (signature.getAlgorithm().endsWith("MGF1")) {
                    try {
                        signature.setParameter(algorithmParameters.getParameterSpec(java.security.spec.PSSParameterSpec.class));
                    } catch (java.security.GeneralSecurityException e) {
                        throw new java.security.SignatureException("Exception extracting parameters: " + e.getMessage());
                    }
                }
            } catch (java.io.IOException e2) {
                throw new java.security.SignatureException("IOException decoding parameters: " + e2.getMessage());
            }
        }
    }

    static java.lang.String getSignatureName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters != null && !com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE.equals(parameters) && algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return getDigestAlgName(com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(parameters).getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
        }
        return algorithmIdentifier.getAlgorithm().getId();
    }

    private static java.lang.String getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return android.security.keystore.KeyProperties.DIGEST_MD5;
        }
        if (com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA1";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA224";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA256";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA384";
        }
        if (com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA512";
        }
        return aSN1ObjectIdentifier.getId();
    }
}
