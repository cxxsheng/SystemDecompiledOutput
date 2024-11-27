package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class DefaultSignatureAlgorithmIdentifierFinder implements com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder {
    private static java.util.Map algorithms = new java.util.HashMap();
    private static java.util.Set noParams = new java.util.HashSet();
    private static java.util.Map params = new java.util.HashMap();
    private static java.util.Set pkcs15RsaEncryption = new java.util.HashSet();
    private static java.util.Map digestOids = new java.util.HashMap();
    private static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ENCRYPTION_RSA = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption;
    private static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ENCRYPTION_DSA = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1;
    private static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ENCRYPTION_ECDSA = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1;
    private static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ENCRYPTION_RSA_PSS = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS;

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
        pkcs15RsaEncryption.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption);
        pkcs15RsaEncryption.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption);
        pkcs15RsaEncryption.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption);
        pkcs15RsaEncryption.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption);
        pkcs15RsaEncryption.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption);
        params.put("SHA1WITHRSAANDMGF1", createPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 20));
        params.put("SHA224WITHRSAANDMGF1", createPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 28));
        params.put("SHA256WITHRSAANDMGF1", createPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 32));
        params.put("SHA384WITHRSAANDMGF1", createPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 48));
        params.put("SHA512WITHRSAANDMGF1", createPSSParams(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), 64));
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier generate(java.lang.String str) {
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) algorithms.get(upperCase);
        if (aSN1ObjectIdentifier == null) {
            throw new java.lang.IllegalArgumentException("Unknown signature type requested: " + upperCase);
        }
        if (noParams.contains(aSN1ObjectIdentifier)) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        if (params.containsKey(upperCase)) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) params.get(upperCase));
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    }

    private static com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams createPSSParams(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, int i) {
        return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(algorithmIdentifier, new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L));
    }

    @Override // com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder
    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find(java.lang.String str) {
        return generate(str);
    }
}
