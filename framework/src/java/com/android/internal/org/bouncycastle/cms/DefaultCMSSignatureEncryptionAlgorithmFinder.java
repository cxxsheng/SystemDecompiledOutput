package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class DefaultCMSSignatureEncryptionAlgorithmFinder implements com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder {
    private static final java.util.Set RSA_PKCS1d5 = new java.util.HashSet();
    private static final java.util.Map GOST_ENC = new java.util.HashMap();

    static {
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.md5WithRSA);
        RSA_PKCS1d5.add(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.sha1WithRSA);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder
    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier findEncryptionAlgorithm(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        if (RSA_PKCS1d5.contains(algorithmIdentifier.getAlgorithm())) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        if (GOST_ENC.containsKey(algorithmIdentifier.getAlgorithm())) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier) GOST_ENC.get(algorithmIdentifier.getAlgorithm());
        }
        return algorithmIdentifier;
    }
}
