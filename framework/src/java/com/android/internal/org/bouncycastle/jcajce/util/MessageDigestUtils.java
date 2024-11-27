package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class MessageDigestUtils {
    private static java.util.Map<com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier, java.lang.String> digestOidMap = new java.util.HashMap();

    static {
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5, android.security.keystore.KeyProperties.DIGEST_MD5);
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, "SHA-1");
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, android.security.keystore.KeyProperties.DIGEST_SHA224);
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, "SHA-256");
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, android.security.keystore.KeyProperties.DIGEST_SHA384);
        digestOidMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, android.security.keystore.KeyProperties.DIGEST_SHA512);
    }

    public static java.lang.String getDigestName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String str = digestOidMap.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        return aSN1ObjectIdentifier.getId();
    }
}
