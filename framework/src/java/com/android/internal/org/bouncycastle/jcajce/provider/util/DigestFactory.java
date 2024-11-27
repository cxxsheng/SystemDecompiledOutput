package com.android.internal.org.bouncycastle.jcajce.provider.util;

/* loaded from: classes4.dex */
public class DigestFactory {
    private static java.util.Set md5 = new java.util.HashSet();
    private static java.util.Set sha1 = new java.util.HashSet();
    private static java.util.Set sha224 = new java.util.HashSet();
    private static java.util.Set sha256 = new java.util.HashSet();
    private static java.util.Set sha384 = new java.util.HashSet();
    private static java.util.Set sha512 = new java.util.HashSet();
    private static java.util.Map oids = new java.util.HashMap();

    static {
        md5.add(android.security.keystore.KeyProperties.DIGEST_MD5);
        md5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5.getId());
        sha1.add("SHA1");
        sha1.add("SHA-1");
        sha1.add(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1.getId());
        sha224.add("SHA224");
        sha224.add(android.security.keystore.KeyProperties.DIGEST_SHA224);
        sha224.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224.getId());
        sha256.add("SHA256");
        sha256.add("SHA-256");
        sha256.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.getId());
        sha384.add("SHA384");
        sha384.add(android.security.keystore.KeyProperties.DIGEST_SHA384);
        sha384.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384.getId());
        sha512.add("SHA512");
        sha512.add(android.security.keystore.KeyProperties.DIGEST_SHA512);
        sha512.add(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512.getId());
        oids.put(android.security.keystore.KeyProperties.DIGEST_MD5, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
        oids.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5.getId(), com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
        oids.put("SHA1", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        oids.put("SHA-1", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        oids.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1.getId(), com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1);
        oids.put("SHA224", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        oids.put(android.security.keystore.KeyProperties.DIGEST_SHA224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        oids.put("SHA256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        oids.put("SHA-256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        oids.put("SHA384", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        oids.put(android.security.keystore.KeyProperties.DIGEST_SHA384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        oids.put("SHA512", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        oids.put(android.security.keystore.KeyProperties.DIGEST_SHA512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        oids.put("SHA512(224)", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_224);
        oids.put("SHA-512(224)", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_224);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_224.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_224);
        oids.put("SHA512(256)", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_256);
        oids.put("SHA-512(256)", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_256);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_256.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_256);
        oids.put("SHA3-224", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_224);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_224.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_224);
        oids.put("SHA3-256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_256);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_256.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_256);
        oids.put("SHA3-384", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_384);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_384.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_384);
        oids.put("SHA3-512", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_512);
        oids.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_512.getId(), com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha3_512);
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getDigest(java.lang.String str) {
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (sha1.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
        }
        if (md5.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5();
        }
        if (sha224.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224();
        }
        if (sha256.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256();
        }
        if (sha384.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA384();
        }
        if (sha512.contains(upperCase)) {
            return com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA512();
        }
        return null;
    }

    public static boolean isSameDigest(java.lang.String str, java.lang.String str2) {
        return (sha1.contains(str) && sha1.contains(str2)) || (sha224.contains(str) && sha224.contains(str2)) || ((sha256.contains(str) && sha256.contains(str2)) || ((sha384.contains(str) && sha384.contains(str2)) || ((sha512.contains(str) && sha512.contains(str2)) || (md5.contains(str) && md5.contains(str2)))));
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOID(java.lang.String str) {
        return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.get(str);
    }
}
