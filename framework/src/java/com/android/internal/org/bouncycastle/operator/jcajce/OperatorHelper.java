package com.android.internal.org.bouncycastle.operator.jcajce;

/* loaded from: classes4.dex */
class OperatorHelper {
    private com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;
    private static final java.util.Map oids = new java.util.HashMap();
    private static final java.util.Map asymmetricWrapperAlgNames = new java.util.HashMap();
    private static final java.util.Map symmetricWrapperAlgNames = new java.util.HashMap();
    private static final java.util.Map symmetricKeyAlgNames = new java.util.HashMap();
    private static final java.util.Map symmetricWrapperKeySizes = new java.util.HashMap();

    static {
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
        oids.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, "SHA1");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, "SHA224");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, "SHA256");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, "SHA384");
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, "SHA512");
        asymmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, "RSA/ECB/PKCS1Padding");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap, "DESEDEWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMSRC2wrap, "RC2Wrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_wrap, "AESWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_wrap, "AESWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_wrap, "AESWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_wrap, "CamelliaWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_wrap, "CamelliaWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_wrap, "CamelliaWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "SEEDWrap");
        symmetricWrapperAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC, android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(256));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia128_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia192_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers.id_camellia256_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(256));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        symmetricWrapperKeySizes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC, com.android.internal.org.bouncycastle.util.Integers.valueOf(192));
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.aes, android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes192_CBC, android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.des_EDE3_CBC, android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
        symmetricKeyAlgNames.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.RC2_CBC, "RC2");
    }

    OperatorHelper(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    java.lang.String getWrappingAlgorithmName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (java.lang.String) symmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
    }

    int getKeySizeInBits(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ((java.lang.Integer) symmetricWrapperKeySizes.get(aSN1ObjectIdentifier)).intValue();
    }

    java.security.KeyPairGenerator createKeyPairGenerator(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws com.android.internal.org.bouncycastle.cms.CMSException {
        try {
            return this.helper.createKeyPairGenerator(aSN1ObjectIdentifier.getId());
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("cannot create key agreement: " + e.getMessage(), e);
        }
    }

    javax.crypto.Cipher createCipher(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create cipher: " + e.getMessage(), e);
        }
    }

    javax.crypto.KeyAgreement createKeyAgreement(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            return this.helper.createKeyAgreement(aSN1ObjectIdentifier.getId());
        } catch (java.security.GeneralSecurityException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create key agreement: " + e.getMessage(), e);
        }
    }

    javax.crypto.Cipher createAsymmetricWrapper(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.util.Map map) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        java.lang.String str;
        try {
            if (map.isEmpty()) {
                str = null;
            } else {
                str = (java.lang.String) map.get(aSN1ObjectIdentifier);
            }
            if (str == null) {
                str = (java.lang.String) asymmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
            }
            if (str != null) {
                try {
                    return this.helper.createCipher(str);
                } catch (java.security.NoSuchAlgorithmException e) {
                    if (str.equals("RSA/ECB/PKCS1Padding")) {
                        try {
                            return this.helper.createCipher("RSA/NONE/PKCS1Padding");
                        } catch (java.security.NoSuchAlgorithmException e2) {
                        }
                    }
                }
            }
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (java.security.GeneralSecurityException e3) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create cipher: " + e3.getMessage(), e3);
        }
    }

    javax.crypto.Cipher createSymmetricWrapper(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            java.lang.String str = (java.lang.String) symmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
            if (str != null) {
                try {
                    return this.helper.createCipher(str);
                } catch (java.security.NoSuchAlgorithmException e) {
                }
            }
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (java.security.GeneralSecurityException e2) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create cipher: " + e2.getMessage(), e2);
        }
    }

    java.security.AlgorithmParameters createAlgorithmParameters(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption)) {
            return null;
        }
        try {
            java.security.AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(algorithmIdentifier.getAlgorithm().getId());
            try {
                createAlgorithmParameters.init(algorithmIdentifier.getParameters().toASN1Primitive().getEncoded());
                return createAlgorithmParameters;
            } catch (java.io.IOException e) {
                throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot initialise algorithm parameters: " + e.getMessage(), e);
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            return null;
        } catch (java.security.NoSuchProviderException e3) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create algorithm parameters: " + e3.getMessage(), e3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.security.MessageDigest] */
    /* JADX WARN: Type inference failed for: r4v9 */
    java.security.MessageDigest createDigest(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws java.security.GeneralSecurityException {
        try {
            if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_shake256_len)) {
                algorithmIdentifier = this.helper.createMessageDigest("SHAKE256-" + com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(algorithmIdentifier.getParameters()).getValue());
            } else {
                algorithmIdentifier = this.helper.createMessageDigest(com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(algorithmIdentifier.getAlgorithm()));
            }
            return algorithmIdentifier;
        } catch (java.security.NoSuchAlgorithmException e) {
            if (oids.get(algorithmIdentifier.getAlgorithm()) != null) {
                return this.helper.createMessageDigest((java.lang.String) oids.get(algorithmIdentifier.getAlgorithm()));
            }
            throw e;
        }
    }

    java.security.Signature createSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws java.security.GeneralSecurityException {
        java.security.Signature createSignature;
        java.lang.String signatureName = getSignatureName(algorithmIdentifier);
        try {
            createSignature = this.helper.createSignature(signatureName);
        } catch (java.security.NoSuchAlgorithmException e) {
            if (signatureName.endsWith("WITHRSAANDMGF1")) {
                createSignature = this.helper.createSignature(signatureName.substring(0, signatureName.indexOf(87)) + "WITHRSASSA-PSS");
            } else if (oids.get(algorithmIdentifier.getAlgorithm()) != null) {
                createSignature = this.helper.createSignature((java.lang.String) oids.get(algorithmIdentifier.getAlgorithm()));
            } else {
                throw e;
            }
        }
        if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
            if (notDefaultPSSParams(aSN1Sequence)) {
                try {
                    java.security.AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PSS);
                    createAlgorithmParameters.init(aSN1Sequence.getEncoded());
                    createSignature.setParameter(createAlgorithmParameters.getParameterSpec(java.security.spec.PSSParameterSpec.class));
                } catch (java.io.IOException e2) {
                    throw new java.security.GeneralSecurityException("unable to process PSS parameters: " + e2.getMessage());
                }
            }
        }
        return createSignature;
    }

    public java.security.Signature createRawSignature(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        try {
            java.lang.String signatureName = getSignatureName(algorithmIdentifier);
            java.lang.String str = android.security.keystore.KeyProperties.DIGEST_NONE + signatureName.substring(signatureName.indexOf("WITH"));
            java.security.Signature createSignature = this.helper.createSignature(str);
            if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
                java.security.AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(str);
                com.android.internal.org.bouncycastle.jcajce.util.AlgorithmParametersUtils.loadParameters(createAlgorithmParameters, algorithmIdentifier.getParameters());
                createSignature.setParameter((java.security.spec.PSSParameterSpec) createAlgorithmParameters.getParameterSpec(java.security.spec.PSSParameterSpec.class));
            }
            return createSignature;
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static java.lang.String getSignatureName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters != null && !com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE.equals(parameters) && algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return getDigestName(com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(parameters).getHashAlgorithm().getAlgorithm()) + "WITHRSAANDMGF1";
        }
        if (oids.containsKey(algorithmIdentifier.getAlgorithm())) {
            return (java.lang.String) oids.get(algorithmIdentifier.getAlgorithm());
        }
        return algorithmIdentifier.getAlgorithm().getId();
    }

    private static java.lang.String getDigestName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String digestName = com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils.getDigestName(aSN1ObjectIdentifier);
        int indexOf = digestName.indexOf(45);
        if (indexOf > 0 && !digestName.startsWith("SHA3")) {
            return digestName.substring(0, indexOf) + digestName.substring(indexOf + 1);
        }
        return digestName;
    }

    public java.security.cert.X509Certificate convertCertificate(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws java.security.cert.CertificateException {
        try {
            return (java.security.cert.X509Certificate) this.helper.createCertificateFactory("X.509").generateCertificate(new java.io.ByteArrayInputStream(x509CertificateHolder.getEncoded()));
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper.OpCertificateException("cannot get encoded form of certificate: " + e.getMessage(), e);
        } catch (java.security.NoSuchProviderException e2) {
            throw new com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper.OpCertificateException("cannot find factory provider: " + e2.getMessage(), e2);
        }
    }

    public java.security.PublicKey convertPublicKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        try {
            return this.helper.createKeyFactory(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().getId()).generatePublic(new java.security.spec.X509EncodedKeySpec(subjectPublicKeyInfo.getEncoded()));
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot get encoded form of key: " + e.getMessage(), e);
        } catch (java.security.NoSuchAlgorithmException e2) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create key factory: " + e2.getMessage(), e2);
        } catch (java.security.NoSuchProviderException e3) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot find factory provider: " + e3.getMessage(), e3);
        } catch (java.security.spec.InvalidKeySpecException e4) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot create key factory: " + e4.getMessage(), e4);
        }
    }

    private static class OpCertificateException extends java.security.cert.CertificateException {
        private java.lang.Throwable cause;

        public OpCertificateException(java.lang.String str, java.lang.Throwable th) {
            super(str);
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public java.lang.Throwable getCause() {
            return this.cause;
        }
    }

    java.lang.String getKeyAlgorithmName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String str = (java.lang.String) symmetricKeyAlgNames.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        return aSN1ObjectIdentifier.getId();
    }

    private boolean notDefaultPSSParams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws java.security.GeneralSecurityException {
        if (aSN1Sequence == null || aSN1Sequence.size() == 0) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams rSASSAPSSparams = com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(aSN1Sequence);
        if (rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1) && rSASSAPSSparams.getHashAlgorithm().equals(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(rSASSAPSSparams.getMaskGenAlgorithm().getParameters()))) {
            return rSASSAPSSparams.getSaltLength().intValue() != createDigest(rSASSAPSSparams.getHashAlgorithm()).getDigestLength();
        }
        return true;
    }
}
