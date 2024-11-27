package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public class KeyUtil {
    public static byte[] getEncodedSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        try {
            return getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(algorithmIdentifier, aSN1Encodable));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        try {
            return getEncodedSubjectPublicKeyInfo(new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(algorithmIdentifier, bArr));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            return subjectPublicKeyInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static byte[] getEncodedPrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        try {
            return getEncodedPrivateKeyInfo(new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(algorithmIdentifier, aSN1Encodable.toASN1Primitive()));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static byte[] getEncodedPrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) {
        try {
            return privateKeyInfo.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}
