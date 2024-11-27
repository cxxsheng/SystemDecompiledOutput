package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
class X509Util {
    private static java.util.Hashtable algorithms = new java.util.Hashtable();
    private static java.util.Hashtable params = new java.util.Hashtable();
    private static java.util.Set noParams = new java.util.HashSet();

    X509Util() {
    }

    static {
        algorithms.put("MD5WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("MD5WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("SHA1WITHRSAENCRYPTION", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption);
        algorithms.put("SHA1WITHRSA", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption);
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
        algorithms.put("SHA1WITHDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("DSAWITHSHA1", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("SHA224WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224);
        algorithms.put("SHA256WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256);
        algorithms.put("SHA384WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512);
        algorithms.put("SHA1WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("ECDSAWITHSHA1", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("SHA224WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224);
        algorithms.put("SHA256WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256);
        algorithms.put("SHA384WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384);
        algorithms.put("SHA512WITHECDSA", com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384);
        noParams.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512);
        params.put("SHA1WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 20));
        params.put("SHA224WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 28));
        params.put("SHA256WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 32));
        params.put("SHA384WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 48));
        params.put("SHA512WITHRSAANDMGF1", creatPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 64));
    }

    private static com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams creatPSSParams(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, int i) {
        return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L));
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getAlgorithmOID(java.lang.String str) {
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (algorithms.containsKey(upperCase)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) algorithms.get(upperCase);
        }
        return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(upperCase);
    }

    static com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSigAlgID(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (noParams.contains(aSN1ObjectIdentifier)) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (params.containsKey(upperCase)) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) params.get(upperCase));
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    }

    static java.util.Iterator getAlgNames() {
        java.util.Enumeration keys = algorithms.keys();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (keys.hasMoreElements()) {
            arrayList.add(keys.nextElement());
        }
        return arrayList.iterator();
    }

    static java.security.Signature getSignatureInstance(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.Signature.getInstance(str);
    }

    static java.security.Signature getSignatureInstance(java.lang.String str, java.lang.String str2) throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException {
        if (str2 != null) {
            return java.security.Signature.getInstance(str, str2);
        }
        return java.security.Signature.getInstance(str);
    }

    static byte[] calculateSignature(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str, java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        if (aSN1ObjectIdentifier == null) {
            throw new java.lang.IllegalStateException("no signature algorithm specified");
        }
        java.security.Signature signatureInstance = getSignatureInstance(str);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        return signatureInstance.sign();
    }

    static byte[] calculateSignature(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str, java.lang.String str2, java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException, java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        if (aSN1ObjectIdentifier == null) {
            throw new java.lang.IllegalStateException("no signature algorithm specified");
        }
        java.security.Signature signatureInstance = getSignatureInstance(str, str2);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        return signatureInstance.sign();
    }

    static com.android.internal.org.bouncycastle.jce.X509Principal convertPrincipal(javax.security.auth.x500.X500Principal x500Principal) {
        try {
            return new com.android.internal.org.bouncycastle.jce.X509Principal(x500Principal.getEncoded());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("cannot convert principal");
        }
    }

    static class Implementation {
        java.lang.Object engine;
        java.security.Provider provider;

        Implementation(java.lang.Object obj, java.security.Provider provider) {
            this.engine = obj;
            this.provider = provider;
        }

        java.lang.Object getEngine() {
            return this.engine;
        }

        java.security.Provider getProvider() {
            return this.provider;
        }
    }

    static com.android.internal.org.bouncycastle.x509.X509Util.Implementation getImplementation(java.lang.String str, java.lang.String str2, java.security.Provider provider) throws java.security.NoSuchAlgorithmException {
        java.lang.Class<?> cls;
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str2);
        while (true) {
            java.lang.String property = provider.getProperty("Alg.Alias." + str + android.media.MediaMetrics.SEPARATOR + upperCase);
            if (property == null) {
                break;
            }
            upperCase = property;
        }
        java.lang.String property2 = provider.getProperty(str + android.media.MediaMetrics.SEPARATOR + upperCase);
        if (property2 != null) {
            try {
                java.lang.ClassLoader classLoader = provider.getClass().getClassLoader();
                if (classLoader != null) {
                    cls = classLoader.loadClass(property2);
                } else {
                    cls = java.lang.Class.forName(property2);
                }
                return new com.android.internal.org.bouncycastle.x509.X509Util.Implementation(cls.newInstance(), provider);
            } catch (java.lang.ClassNotFoundException e) {
                throw new java.lang.IllegalStateException("algorithm " + upperCase + " in provider " + provider.getName() + " but no class \"" + property2 + "\" found!");
            } catch (java.lang.Exception e2) {
                throw new java.lang.IllegalStateException("algorithm " + upperCase + " in provider " + provider.getName() + " but class \"" + property2 + "\" inaccessible!");
            }
        }
        throw new java.security.NoSuchAlgorithmException("cannot find implementation " + upperCase + " for provider " + provider.getName());
    }

    static com.android.internal.org.bouncycastle.x509.X509Util.Implementation getImplementation(java.lang.String str, java.lang.String str2) throws java.security.NoSuchAlgorithmException {
        java.security.Provider[] providers = java.security.Security.getProviders();
        for (int i = 0; i != providers.length; i++) {
            com.android.internal.org.bouncycastle.x509.X509Util.Implementation implementation = getImplementation(str, com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str2), providers[i]);
            if (implementation != null) {
                return implementation;
            }
            try {
                getImplementation(str, str2, providers[i]);
            } catch (java.security.NoSuchAlgorithmException e) {
            }
        }
        throw new java.security.NoSuchAlgorithmException("cannot find implementation " + str2);
    }

    static java.security.Provider getProvider(java.lang.String str) throws java.security.NoSuchProviderException {
        java.security.Provider provider = java.security.Security.getProvider(str);
        if (provider == null) {
            throw new java.security.NoSuchProviderException("Provider " + str + " not found");
        }
        return provider;
    }
}
