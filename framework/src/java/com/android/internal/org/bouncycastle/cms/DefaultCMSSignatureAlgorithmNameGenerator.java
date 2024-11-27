package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class DefaultCMSSignatureAlgorithmNameGenerator implements com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator {
    private final java.util.Map encryptionAlgs = new java.util.HashMap();
    private final java.util.Map digestAlgs = new java.util.HashMap();

    private void addEntries(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str, java.lang.String str2) {
        this.digestAlgs.put(aSN1ObjectIdentifier, str);
        this.encryptionAlgs.put(aSN1ObjectIdentifier, str2);
    }

    public DefaultCMSSignatureAlgorithmNameGenerator() {
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224, "SHA224", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256, "SHA256", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384, "SHA384", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512, "SHA512", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, "SHA1", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.md5WithRSA, android.security.keystore.KeyProperties.DIGEST_MD5, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.sha1WithRSA, "SHA1", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption, android.security.keystore.KeyProperties.DIGEST_MD5, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1", "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512", "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "SHA1", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "SHA256", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "SHA1", "RSAandMGF1");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "SHA256", "RSAandMGF1");
        this.encryptionAlgs.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, "DSA");
        this.encryptionAlgs.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        this.encryptionAlgs.put(com.android.internal.org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        this.encryptionAlgs.put(com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        this.encryptionAlgs.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS, "RSAandMGF1");
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5, android.security.keystore.KeyProperties.DIGEST_MD5);
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, "SHA1");
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, "SHA224");
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, "SHA256");
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, "SHA384");
        this.digestAlgs.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, "SHA512");
    }

    private java.lang.String getDigestAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String str = (java.lang.String) this.digestAlgs.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        return aSN1ObjectIdentifier.getId();
    }

    private java.lang.String getEncryptionAlgName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String str = (java.lang.String) this.encryptionAlgs.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        return aSN1ObjectIdentifier.getId();
    }

    protected void setSigningEncryptionAlgorithmMapping(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        this.encryptionAlgs.put(aSN1ObjectIdentifier, str);
    }

    protected void setSigningDigestAlgorithmMapping(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        this.digestAlgs.put(aSN1ObjectIdentifier, str);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator
    public java.lang.String getSignatureName(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        java.lang.String digestAlgName = getDigestAlgName(algorithmIdentifier2.getAlgorithm());
        if (!digestAlgName.equals(algorithmIdentifier2.getAlgorithm().getId())) {
            return digestAlgName + "with" + getEncryptionAlgName(algorithmIdentifier2.getAlgorithm());
        }
        return getDigestAlgName(algorithmIdentifier.getAlgorithm()) + "with" + getEncryptionAlgName(algorithmIdentifier2.getAlgorithm());
    }
}
