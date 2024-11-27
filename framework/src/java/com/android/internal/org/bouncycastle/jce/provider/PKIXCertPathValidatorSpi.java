package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PKIXCertPathValidatorSpi extends java.security.cert.CertPathValidatorSpi {
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;
    private final boolean isForCRLCheck;

    public PKIXCertPathValidatorSpi() {
        this(false);
    }

    public PKIXCertPathValidatorSpi(boolean z) {
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
        this.isForCRLCheck = z;
    }

    private static class NoPreloadHolder {
        private static final com.android.internal.org.bouncycastle.jce.provider.CertBlocklist blocklist = new com.android.internal.org.bouncycastle.jce.provider.CertBlocklist();

        private NoPreloadHolder() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier] */
    @Override // java.security.cert.CertPathValidatorSpi
    public java.security.cert.CertPathValidatorResult engineValidate(java.security.cert.CertPath certPath, java.security.cert.CertPathParameters certPathParameters) throws java.security.cert.CertPathValidatorException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters;
        java.util.List<? extends java.security.cert.Certificate> list;
        int i;
        int i2;
        com.android.internal.org.bouncycastle.asn1.x500.X500Name ca;
        java.security.PublicKey cAPublicKey;
        com.android.internal.org.bouncycastle.jce.provider.ProvCrlRevocationChecker provCrlRevocationChecker;
        java.util.HashSet hashSet;
        int i3;
        int i4;
        int i5;
        java.util.ArrayList[] arrayListArr;
        int i6;
        java.util.HashSet hashSet2;
        if (certPathParameters instanceof java.security.cert.PKIXParameters) {
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder builder = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder((java.security.cert.PKIXParameters) certPathParameters);
            if (certPathParameters instanceof com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters) {
                com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters extendedPKIXParameters = (com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters) certPathParameters;
                builder.setUseDeltasEnabled(extendedPKIXParameters.isUseDeltasEnabled());
                builder.setValidityModel(extendedPKIXParameters.getValidityModel());
            }
            pKIXExtendedParameters = builder.build();
        } else if (certPathParameters instanceof com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters) {
            pKIXExtendedParameters = ((com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters) certPathParameters).getBaseParameters();
        } else if (certPathParameters instanceof com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters) {
            pKIXExtendedParameters = (com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters) certPathParameters;
        } else {
            throw new java.security.InvalidAlgorithmParameterException("Parameters must be a " + java.security.cert.PKIXParameters.class.getName() + " instance.");
        }
        if (pKIXExtendedParameters.getTrustAnchors() == null) {
            throw new java.security.InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
        java.util.List<? extends java.security.cert.Certificate> certificates = certPath.getCertificates();
        int size = certificates.size();
        int i7 = -1;
        if (certificates.isEmpty()) {
            throw new java.security.cert.CertPathValidatorException("Certification path is empty.", null, certPath, -1);
        }
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificates.get(0);
        if (x509Certificate != null) {
            java.math.BigInteger serialNumber = x509Certificate.getSerialNumber();
            if (com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi.NoPreloadHolder.blocklist.isSerialNumberBlockListed(serialNumber)) {
                java.lang.String str = "Certificate revocation of serial 0x" + serialNumber.toString(16);
                java.lang.System.out.println(str);
                com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException(str);
                throw new java.security.cert.CertPathValidatorException(annotatedException.getMessage(), annotatedException, certPath, 0);
            }
        }
        java.util.Date validityDate = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getValidityDate(pKIXExtendedParameters, new java.util.Date());
        java.util.Set initialPolicies = pKIXExtendedParameters.getInitialPolicies();
        try {
            java.security.cert.TrustAnchor findTrustAnchor = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTrustAnchor((java.security.cert.X509Certificate) certificates.get(certificates.size() - 1), pKIXExtendedParameters.getTrustAnchors(), pKIXExtendedParameters.getSigProvider());
            if (findTrustAnchor == null) {
                list = certificates;
                try {
                    throw new java.security.cert.CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1);
                } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
                    e = e;
                    throw new java.security.cert.CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath, list.size() - 1);
                }
            }
            checkCertificate(findTrustAnchor.getTrustedCert());
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters build = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTrustAnchor(findTrustAnchor).build();
            int i8 = size + 1;
            java.util.ArrayList[] arrayListArr2 = new java.util.ArrayList[i8];
            for (int i9 = 0; i9 < i8; i9++) {
                arrayListArr2[i9] = new java.util.ArrayList();
            }
            java.util.HashSet hashSet3 = new java.util.HashSet();
            hashSet3.add(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY);
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), 0, hashSet3, null, new java.util.HashSet(), com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY, false);
            arrayListArr2[0].add(pKIXPolicyNode);
            com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator pKIXNameConstraintValidator = new com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator();
            java.util.HashSet hashSet4 = new java.util.HashSet();
            if (build.isExplicitPolicyRequired()) {
                i = 0;
            } else {
                i = i8;
            }
            if (build.isAnyPolicyInhibited()) {
                i2 = 0;
            } else {
                i2 = i8;
            }
            if (build.isPolicyMappingInhibited()) {
                i8 = 0;
            }
            java.security.cert.X509Certificate trustedCert = findTrustAnchor.getTrustedCert();
            try {
                if (trustedCert != null) {
                    ca = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getSubjectPrincipal(trustedCert);
                    cAPublicKey = trustedCert.getPublicKey();
                } else {
                    ca = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getCA(findTrustAnchor);
                    cAPublicKey = findTrustAnchor.getCAPublicKey();
                }
                try {
                    i7 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAlgorithmIdentifier(cAPublicKey);
                    i7.getAlgorithm();
                    i7.getParameters();
                    if (build.getTargetConstraints() != null && !build.getTargetConstraints().match((java.security.cert.Certificate) certificates.get(0))) {
                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath, 0);
                    }
                    java.util.List certPathCheckers = build.getCertPathCheckers();
                    java.util.Iterator it = certPathCheckers.iterator();
                    while (it.hasNext()) {
                        ((java.security.cert.PKIXCertPathChecker) it.next()).init(false);
                    }
                    if (build.isRevocationEnabled()) {
                        provCrlRevocationChecker = new com.android.internal.org.bouncycastle.jce.provider.ProvCrlRevocationChecker(this.helper);
                    } else {
                        provCrlRevocationChecker = null;
                    }
                    int size2 = certificates.size() - 1;
                    int i10 = size;
                    java.security.cert.X509Certificate x509Certificate2 = null;
                    int i11 = i2;
                    int i12 = i8;
                    java.security.PublicKey publicKey = cAPublicKey;
                    com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name = ca;
                    java.security.cert.X509Certificate x509Certificate3 = trustedCert;
                    com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                    int i13 = i;
                    int i14 = i11;
                    while (size2 >= 0) {
                        int i15 = i14;
                        if (com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi.NoPreloadHolder.blocklist.isPublicKeyBlockListed(publicKey)) {
                            java.lang.String str2 = "Certificate revocation of public key " + publicKey;
                            java.lang.System.out.println(str2);
                            com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException2 = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException(str2);
                            throw new java.security.cert.CertPathValidatorException(annotatedException2.getMessage(), annotatedException2, certPath, size2);
                        }
                        int i16 = size - size2;
                        int i17 = i10;
                        java.security.cert.X509Certificate x509Certificate4 = (java.security.cert.X509Certificate) certificates.get(size2);
                        java.security.PublicKey publicKey2 = publicKey;
                        boolean z = size2 == certificates.size() + (-1);
                        try {
                            checkCertificate(x509Certificate4);
                            java.util.List<? extends java.security.cert.Certificate> list2 = certificates;
                            int i18 = size2;
                            java.util.Date date = validityDate;
                            java.util.Date date2 = validityDate;
                            int i19 = i13;
                            com.android.internal.org.bouncycastle.jce.provider.ProvCrlRevocationChecker provCrlRevocationChecker2 = provCrlRevocationChecker;
                            com.android.internal.org.bouncycastle.jce.provider.ProvCrlRevocationChecker provCrlRevocationChecker3 = provCrlRevocationChecker;
                            int i20 = i12;
                            com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                            java.util.ArrayList[] arrayListArr3 = arrayListArr2;
                            java.security.cert.TrustAnchor trustAnchor = findTrustAnchor;
                            java.util.List list3 = certPathCheckers;
                            com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCertA(certPath, build, date, provCrlRevocationChecker2, i18, publicKey2, z, x500Name, x509Certificate3);
                            com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCertBC(certPath, i18, pKIXNameConstraintValidator2, this.isForCRLCheck);
                            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode processCertE = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCertE(certPath, i18, com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCertD(certPath, i18, hashSet4, pKIXPolicyNode2, arrayListArr3, i15, this.isForCRLCheck));
                            com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCertF(certPath, i18, processCertE, i19);
                            if (i16 == size) {
                                i3 = i15;
                                i4 = i17;
                                i5 = i20;
                                arrayListArr = arrayListArr3;
                                certPathCheckers = list3;
                            } else {
                                if (x509Certificate4 != null && x509Certificate4.getVersion() == 1) {
                                    if (i16 == 1 && x509Certificate4.equals(trustAnchor.getTrustedCert())) {
                                        i3 = i15;
                                        i4 = i17;
                                        i5 = i20;
                                        arrayListArr = arrayListArr3;
                                        certPathCheckers = list3;
                                    } else {
                                        throw new java.security.cert.CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath, i18);
                                    }
                                }
                                com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertA(certPath, i18);
                                arrayListArr = arrayListArr3;
                                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode prepareCertB = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareCertB(certPath, i18, arrayListArr, processCertE, i20);
                                com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertG(certPath, i18, pKIXNameConstraintValidator2);
                                int prepareNextCertH1 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertH1(certPath, i18, i19);
                                int prepareNextCertH2 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertH2(certPath, i18, i20);
                                int prepareNextCertH3 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertH3(certPath, i18, i15);
                                int prepareNextCertI1 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertI1(certPath, i18, prepareNextCertH1);
                                int prepareNextCertI2 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertI2(certPath, i18, prepareNextCertH2);
                                int prepareNextCertJ = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertJ(certPath, i18, prepareNextCertH3);
                                com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertK(certPath, i18);
                                i4 = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertM(certPath, i18, com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertL(certPath, i18, i17));
                                com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertN(certPath, i18);
                                java.util.Set<java.lang.String> criticalExtensionOIDs = x509Certificate4.getCriticalExtensionOIDs();
                                if (criticalExtensionOIDs != null) {
                                    hashSet2 = new java.util.HashSet(criticalExtensionOIDs);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.KEY_USAGE);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                    hashSet2.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                                } else {
                                    hashSet2 = new java.util.HashSet();
                                }
                                certPathCheckers = list3;
                                com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertO(certPath, i18, hashSet2, certPathCheckers);
                                com.android.internal.org.bouncycastle.asn1.x500.X500Name subjectPrincipal = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getSubjectPrincipal(x509Certificate4);
                                try {
                                    java.security.PublicKey nextWorkingKey = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), i18, this.helper);
                                    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                                    algorithmIdentifier.getAlgorithm();
                                    algorithmIdentifier.getParameters();
                                    pKIXPolicyNode2 = prepareCertB;
                                    publicKey = nextWorkingKey;
                                    x500Name = subjectPrincipal;
                                    x509Certificate3 = x509Certificate4;
                                    i13 = prepareNextCertI1;
                                    i6 = prepareNextCertI2;
                                    i14 = prepareNextCertJ;
                                    certificates = list2;
                                    x509Certificate2 = x509Certificate4;
                                    validityDate = date2;
                                    findTrustAnchor = trustAnchor;
                                    i10 = i4;
                                    arrayListArr2 = arrayListArr;
                                    i12 = i6;
                                    size2 = i18 - 1;
                                    pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                                    provCrlRevocationChecker = provCrlRevocationChecker3;
                                } catch (java.security.cert.CertPathValidatorException e2) {
                                    throw new java.security.cert.CertPathValidatorException("Next working key could not be retrieved.", e2, certPath, i18);
                                }
                            }
                            pKIXPolicyNode2 = processCertE;
                            i6 = i5;
                            i14 = i3;
                            i13 = i19;
                            publicKey = publicKey2;
                            certificates = list2;
                            x509Certificate2 = x509Certificate4;
                            validityDate = date2;
                            findTrustAnchor = trustAnchor;
                            i10 = i4;
                            arrayListArr2 = arrayListArr;
                            i12 = i6;
                            size2 = i18 - 1;
                            pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                            provCrlRevocationChecker = provCrlRevocationChecker3;
                        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e3) {
                            throw new java.security.cert.CertPathValidatorException(e3.getMessage(), e3.getUnderlyingException(), certPath, size2);
                        }
                    }
                    java.util.ArrayList[] arrayListArr4 = arrayListArr2;
                    java.security.cert.TrustAnchor trustAnchor2 = findTrustAnchor;
                    int i21 = size2;
                    java.security.cert.X509Certificate x509Certificate5 = x509Certificate2;
                    int i22 = i21 + 1;
                    int wrapupCertB = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.wrapupCertB(certPath, i22, com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.wrapupCertA(i13, x509Certificate5));
                    java.util.Set<java.lang.String> criticalExtensionOIDs2 = x509Certificate5.getCriticalExtensionOIDs();
                    if (criticalExtensionOIDs2 != null) {
                        hashSet = new java.util.HashSet(criticalExtensionOIDs2);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.KEY_USAGE);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_MAPPINGS);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        hashSet.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                        hashSet.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.extendedKeyUsage.getId());
                    } else {
                        hashSet = new java.util.HashSet();
                    }
                    com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.wrapupCertF(certPath, i22, certPathCheckers, hashSet);
                    com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode wrapupCertG = com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.wrapupCertG(certPath, build, initialPolicies, i22, arrayListArr4, pKIXPolicyNode2, hashSet4);
                    if (wrapupCertB > 0 || wrapupCertG != null) {
                        return new java.security.cert.PKIXCertPathValidatorResult(trustAnchor2, wrapupCertG, x509Certificate5.getPublicKey());
                    }
                    throw new java.security.cert.CertPathValidatorException("Path processing failed on policy.", null, certPath, i21);
                } catch (java.security.cert.CertPathValidatorException e4) {
                    throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e4, certPath, -1);
                }
            } catch (java.lang.RuntimeException e5) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e5, certPath, i7);
            }
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e6) {
            e = e6;
            list = certificates;
        }
    }

    static void checkCertificate(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        try {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate.getInstance(x509Certificate.getTBSCertificate());
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException(e.getMessage());
        } catch (java.security.cert.CertificateEncodingException e2) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("unable to process TBSCertificate", e2);
        }
    }
}
