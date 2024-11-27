package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
class CMSSignedHelper {
    static final com.android.internal.org.bouncycastle.cms.CMSSignedHelper INSTANCE = new com.android.internal.org.bouncycastle.cms.CMSSignedHelper();
    private static final java.util.Map encryptionAlgs = new java.util.HashMap();

    CMSSignedHelper() {
    }

    static {
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha384, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha512, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.md5WithRSA, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.sha1WithRSA, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha1WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha224WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha256WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha384WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.sha512WithRSAEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "ECDSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "RSAandMGF1");
        addEntries(com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "RSAandMGF1");
        addEntries(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, "DSA");
        addEntries(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
        addEntries(com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
    }

    private static void addEntries(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        encryptionAlgs.put(aSN1ObjectIdentifier.getId(), str);
    }

    java.lang.String getEncryptionAlgName(java.lang.String str) {
        java.lang.String str2 = (java.lang.String) encryptionAlgs.get(str);
        if (str2 != null) {
            return str2;
        }
        return str;
    }

    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier fixAlgID(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        if (algorithmIdentifier.getParameters() == null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(algorithmIdentifier.getAlgorithm(), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        return algorithmIdentifier;
    }

    void setSigningEncryptionAlgorithmMapping(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        addEntries(aSN1ObjectIdentifier, str);
    }

    com.android.internal.org.bouncycastle.util.Store getCertificates(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(aSN1Set.size());
            java.util.Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                    arrayList.add(new com.android.internal.org.bouncycastle.cert.X509CertificateHolder(com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(aSN1Primitive)));
                }
            }
            return new com.android.internal.org.bouncycastle.util.CollectionStore(arrayList);
        }
        return new com.android.internal.org.bouncycastle.util.CollectionStore(new java.util.ArrayList());
    }

    com.android.internal.org.bouncycastle.util.Store getAttributeCertificates(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(aSN1Set.size());
            java.util.Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                    arrayList.add(new com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Primitive).getObject())));
                }
            }
            return new com.android.internal.org.bouncycastle.util.CollectionStore(arrayList);
        }
        return new com.android.internal.org.bouncycastle.util.CollectionStore(new java.util.ArrayList());
    }

    com.android.internal.org.bouncycastle.util.Store getCRLs(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        if (aSN1Set != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(aSN1Set.size());
            java.util.Enumeration objects = aSN1Set.getObjects();
            while (objects.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive();
                if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                    arrayList.add(new com.android.internal.org.bouncycastle.cert.X509CRLHolder(com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(aSN1Primitive)));
                }
            }
            return new com.android.internal.org.bouncycastle.util.CollectionStore(arrayList);
        }
        return new com.android.internal.org.bouncycastle.util.CollectionStore(new java.util.ArrayList());
    }
}
