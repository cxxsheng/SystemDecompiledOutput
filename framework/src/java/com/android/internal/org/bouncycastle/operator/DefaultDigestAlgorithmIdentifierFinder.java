package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class DefaultDigestAlgorithmIdentifierFinder implements com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder {
    private static java.util.Map digestOids = new java.util.HashMap();
    private static java.util.Map digestNameToOids = new java.util.HashMap();

    static {
        digestOids.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.sha1WithRSA, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1, com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestOids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestNameToOids.put("SHA-1", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        digestNameToOids.put(android.security.keystore.KeyProperties.DIGEST_SHA224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        digestNameToOids.put("SHA-256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        digestNameToOids.put(android.security.keystore.KeyProperties.DIGEST_SHA384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        digestNameToOids.put(android.security.keystore.KeyProperties.DIGEST_SHA512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        digestNameToOids.put(android.security.keystore.KeyProperties.DIGEST_MD5, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
    }

    @Override // com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder
    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        if (algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams.getInstance(algorithmIdentifier.getParameters()).getHashAlgorithm();
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) digestOids.get(algorithmIdentifier.getAlgorithm()), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    }

    @Override // com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder
    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find(java.lang.String str) {
        return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) digestNameToOids.get(str), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    }
}
