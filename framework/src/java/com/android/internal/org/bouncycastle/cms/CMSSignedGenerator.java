package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSSignedGenerator {
    public static final java.lang.String DATA = com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.data.getId();
    public static final java.lang.String DIGEST_SHA1 = com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1.getId();
    public static final java.lang.String DIGEST_SHA224 = com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224.getId();
    public static final java.lang.String DIGEST_SHA256 = com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256.getId();
    public static final java.lang.String DIGEST_SHA384 = com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384.getId();
    public static final java.lang.String DIGEST_SHA512 = com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512.getId();
    public static final java.lang.String DIGEST_MD5 = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5.getId();
    public static final java.lang.String ENCRYPTION_RSA = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption.getId();
    public static final java.lang.String ENCRYPTION_DSA = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa_with_sha1.getId();
    public static final java.lang.String ENCRYPTION_ECDSA = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    public static final java.lang.String ENCRYPTION_RSA_PSS = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
    private static final java.lang.String ENCRYPTION_ECDSA_WITH_SHA1 = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    private static final java.lang.String ENCRYPTION_ECDSA_WITH_SHA224 = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
    private static final java.lang.String ENCRYPTION_ECDSA_WITH_SHA256 = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
    private static final java.lang.String ENCRYPTION_ECDSA_WITH_SHA384 = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
    private static final java.lang.String ENCRYPTION_ECDSA_WITH_SHA512 = com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.ecdsa_with_SHA512.getId();
    private static final java.util.Set NO_PARAMS = new java.util.HashSet();
    private static final java.util.Map EC_ALGORITHMS = new java.util.HashMap();
    protected java.util.List certs = new java.util.ArrayList();
    protected java.util.List crls = new java.util.ArrayList();
    protected java.util.List _signers = new java.util.ArrayList();
    protected java.util.List signerGens = new java.util.ArrayList();
    protected java.util.Map digests = new java.util.HashMap();

    static {
        NO_PARAMS.add(ENCRYPTION_DSA);
        NO_PARAMS.add(ENCRYPTION_ECDSA);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA1);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA224);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA256);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA384);
        NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA512);
        EC_ALGORITHMS.put(DIGEST_SHA1, ENCRYPTION_ECDSA_WITH_SHA1);
        EC_ALGORITHMS.put(DIGEST_SHA224, ENCRYPTION_ECDSA_WITH_SHA224);
        EC_ALGORITHMS.put(DIGEST_SHA256, ENCRYPTION_ECDSA_WITH_SHA256);
        EC_ALGORITHMS.put(DIGEST_SHA384, ENCRYPTION_ECDSA_WITH_SHA384);
        EC_ALGORITHMS.put(DIGEST_SHA512, ENCRYPTION_ECDSA_WITH_SHA512);
    }

    protected CMSSignedGenerator() {
    }

    protected java.util.Map getBaseParameters(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.CONTENT_TYPE, aSN1ObjectIdentifier);
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER, algorithmIdentifier);
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST, com.android.internal.org.bouncycastle.util.Arrays.clone(bArr));
        return hashMap;
    }

    public void addCertificate(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.certs.add(x509CertificateHolder.toASN1Structure());
    }

    public void addCertificates(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.certs.addAll(com.android.internal.org.bouncycastle.cms.CMSUtils.getCertificatesFromStore(store));
    }

    public void addCRL(com.android.internal.org.bouncycastle.cert.X509CRLHolder x509CRLHolder) {
        this.crls.add(x509CRLHolder.toASN1Structure());
    }

    public void addCRLs(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.crls.addAll(com.android.internal.org.bouncycastle.cms.CMSUtils.getCRLsFromStore(store));
    }

    public void addAttributeCertificate(com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder x509AttributeCertificateHolder) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.certs.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, x509AttributeCertificateHolder.toASN1Structure()));
    }

    public void addAttributeCertificates(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.certs.addAll(com.android.internal.org.bouncycastle.cms.CMSUtils.getAttributeCertificatesFromStore(store));
    }

    public void addSigners(com.android.internal.org.bouncycastle.cms.SignerInformationStore signerInformationStore) {
        java.util.Iterator<com.android.internal.org.bouncycastle.cms.SignerInformation> it = signerInformationStore.getSigners().iterator();
        while (it.hasNext()) {
            this._signers.add(it.next());
        }
    }

    public void addSignerInfoGenerator(com.android.internal.org.bouncycastle.cms.SignerInfoGenerator signerInfoGenerator) {
        this.signerGens.add(signerInfoGenerator);
    }

    public java.util.Map getGeneratedDigests() {
        return new java.util.HashMap(this.digests);
    }
}
