package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class JcaJceUtils {
    private JcaJceUtils() {
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Encodable extractParameters(java.security.AlgorithmParameters algorithmParameters) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(algorithmParameters.getEncoded("ASN.1"));
        } catch (java.lang.Exception e) {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(algorithmParameters.getEncoded());
        }
    }

    public static void loadParameters(java.security.AlgorithmParameters algorithmParameters, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
        } catch (java.lang.Exception e) {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
        }
    }

    public static java.lang.String getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
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
