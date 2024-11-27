package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class RFC3280CertPathUtilities {
    public static final java.lang.String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    private static final java.lang.Class revChkClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.class, "java.security.cert.PKIXRevocationChecker");
    public static final java.lang.String CERTIFICATE_POLICIES = com.android.internal.org.bouncycastle.asn1.x509.Extension.certificatePolicies.getId();
    public static final java.lang.String POLICY_MAPPINGS = com.android.internal.org.bouncycastle.asn1.x509.Extension.policyMappings.getId();
    public static final java.lang.String INHIBIT_ANY_POLICY = com.android.internal.org.bouncycastle.asn1.x509.Extension.inhibitAnyPolicy.getId();
    public static final java.lang.String ISSUING_DISTRIBUTION_POINT = com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId();
    public static final java.lang.String FRESHEST_CRL = com.android.internal.org.bouncycastle.asn1.x509.Extension.freshestCRL.getId();
    public static final java.lang.String DELTA_CRL_INDICATOR = com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId();
    public static final java.lang.String POLICY_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.policyConstraints.getId();
    public static final java.lang.String BASIC_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.basicConstraints.getId();
    public static final java.lang.String CRL_DISTRIBUTION_POINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints.getId();
    public static final java.lang.String SUBJECT_ALTERNATIVE_NAME = com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName.getId();
    public static final java.lang.String NAME_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.nameConstraints.getId();
    public static final java.lang.String AUTHORITY_KEY_IDENTIFIER = com.android.internal.org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier.getId();
    public static final java.lang.String KEY_USAGE = com.android.internal.org.bouncycastle.asn1.x509.Extension.keyUsage.getId();
    public static final java.lang.String CRL_NUMBER = com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLNumber.getId();
    protected static final java.lang.String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    RFC3280CertPathUtilities() {
    }

    protected static void processCRLB2(com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint, java.lang.Object obj, java.security.cert.X509CRL x509crl) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr;
        try {
            com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint issuingDistributionPoint = com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (issuingDistributionPoint != null) {
                if (issuingDistributionPoint.getDistributionPoint() != null) {
                    com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint2 = com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(issuingDistributionPoint).getDistributionPoint();
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    boolean z = false;
                    if (distributionPoint2.getType() == 0) {
                        for (com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName : com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(distributionPoint2.getName()).getNames()) {
                            arrayList.add(generalName);
                        }
                    }
                    if (distributionPoint2.getType() == 1) {
                        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                        try {
                            java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl)).getObjects();
                            while (objects.hasMoreElements()) {
                                aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement());
                            }
                            aSN1EncodableVector.add(distributionPoint2.getName());
                            arrayList.add(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector))));
                        } catch (java.lang.Exception e) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Could not read CRL issuer.", e);
                        }
                    }
                    if (distributionPoint.getDistributionPoint() != null) {
                        com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint3 = distributionPoint.getDistributionPoint();
                        if (distributionPoint3.getType() != 0) {
                            generalNameArr = null;
                        } else {
                            generalNameArr = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(distributionPoint3.getName()).getNames();
                        }
                        if (distributionPoint3.getType() == 1) {
                            if (distributionPoint.getCRLIssuer() != null) {
                                generalNameArr = distributionPoint.getCRLIssuer().getNames();
                            } else {
                                com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] generalNameArr2 = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName[1];
                                try {
                                    generalNameArr2[0] = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getEncodedIssuerPrincipal(obj));
                                    generalNameArr = generalNameArr2;
                                } catch (java.lang.Exception e2) {
                                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Could not read certificate issuer.", e2);
                                }
                            }
                            for (int i = 0; i < generalNameArr.length; i++) {
                                java.util.Enumeration objects2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(generalNameArr[i].getName().toASN1Primitive()).getObjects();
                                com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
                                while (objects2.hasMoreElements()) {
                                    aSN1EncodableVector2.add((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects2.nextElement());
                                }
                                aSN1EncodableVector2.add(distributionPoint3.getName());
                                generalNameArr[i] = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2)));
                            }
                        }
                        if (generalNameArr != null) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= generalNameArr.length) {
                                    break;
                                }
                                if (!arrayList.contains(generalNameArr[i2])) {
                                    i2++;
                                } else {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        if (!z) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    } else {
                        if (distributionPoint.getCRLIssuer() == null) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
                        }
                        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= names.length) {
                                break;
                            }
                            if (!arrayList.contains(names[i3])) {
                                i3++;
                            } else {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    }
                }
                try {
                    com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints = com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Extension) obj, BASIC_CONSTRAINTS));
                    if (obj instanceof java.security.cert.X509Certificate) {
                        if (issuingDistributionPoint.onlyContainsUserCerts() && basicConstraints != null && basicConstraints.isCA()) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CA Cert CRL only contains user certificates.");
                        }
                        if (issuingDistributionPoint.onlyContainsCACerts() && (basicConstraints == null || !basicConstraints.isCA())) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("End CRL only contains CA certificates.");
                        }
                    }
                    if (issuingDistributionPoint.onlyContainsAttributeCerts()) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
                    }
                } catch (java.lang.Exception e3) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Basic constraints extension could not be decoded.", e3);
                }
            }
        } catch (java.lang.Exception e4) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
        }
    }

    protected static void processCRLB1(com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint, java.lang.Object obj, java.security.cert.X509CRL x509crl) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        boolean z;
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive extensionValue = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT);
        boolean z2 = true;
        if (extensionValue != null && com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(extensionValue).isIndirectCRL()) {
            z = true;
        } else {
            z = false;
        }
        try {
            byte[] encoded = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded();
            if (distributionPoint.getCRLIssuer() != null) {
                com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
                boolean z3 = false;
                for (int i = 0; i < names.length; i++) {
                    if (names[i].getTagNo() == 4) {
                        try {
                            if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(names[i].getName().toASN1Primitive().getEncoded(), encoded)) {
                                z3 = true;
                            }
                        } catch (java.io.IOException e) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e);
                        }
                    }
                }
                if (z3 && !z) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
                }
                if (!z3) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
                }
                z2 = z3;
            } else if (!com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl).equals(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getEncodedIssuerPrincipal(obj))) {
                z2 = false;
            }
            if (!z2) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot find matching CRL issuer for certificate.");
            }
        } catch (java.io.IOException e2) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Exception encoding CRL issuer: " + e2.getMessage(), e2);
        }
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.ReasonsMask processCRLD(java.security.cert.X509CRL x509crl, com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask;
        com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask2;
        try {
            com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint issuingDistributionPoint = com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (issuingDistributionPoint != null && issuingDistributionPoint.getOnlySomeReasons() != null && distributionPoint.getReasons() != null) {
                return new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(distributionPoint.getReasons()).intersect(new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(issuingDistributionPoint.getOnlySomeReasons()));
            }
            if ((issuingDistributionPoint == null || issuingDistributionPoint.getOnlySomeReasons() == null) && distributionPoint.getReasons() == null) {
                return com.android.internal.org.bouncycastle.jce.provider.ReasonsMask.allReasons;
            }
            if (distributionPoint.getReasons() == null) {
                reasonsMask = com.android.internal.org.bouncycastle.jce.provider.ReasonsMask.allReasons;
            } else {
                reasonsMask = new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(distributionPoint.getReasons());
            }
            if (issuingDistributionPoint == null) {
                reasonsMask2 = com.android.internal.org.bouncycastle.jce.provider.ReasonsMask.allReasons;
            } else {
                reasonsMask2 = new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask(issuingDistributionPoint.getOnlySomeReasons());
            }
            return reasonsMask.intersect(reasonsMask2);
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension could not be decoded.", e);
        }
    }

    protected static java.util.Set processCRLF(java.security.cert.X509CRL x509crl, java.lang.Object obj, java.security.cert.X509Certificate x509Certificate, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.List list, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        int i;
        java.security.cert.X509CertSelector x509CertSelector = new java.security.cert.X509CertSelector();
        try {
            x509CertSelector.setSubject(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded());
            com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector<? extends java.security.cert.Certificate> build = new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.Builder(x509CertSelector).build();
            java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
            try {
                com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertificateStores());
                com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertStores());
                linkedHashSet.add(x509Certificate);
                java.util.Iterator it = linkedHashSet.iterator();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    java.security.cert.X509Certificate x509Certificate2 = (java.security.cert.X509Certificate) it.next();
                    if (x509Certificate2.equals(x509Certificate)) {
                        arrayList.add(x509Certificate2);
                        arrayList2.add(publicKey);
                    } else {
                        try {
                            com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi pKIXCertPathBuilderSpi = new com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi(true);
                            java.security.cert.X509CertSelector x509CertSelector2 = new java.security.cert.X509CertSelector();
                            x509CertSelector2.setCertificate(x509Certificate2);
                            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder targetConstraints = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTargetConstraints(new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.Builder(x509CertSelector2).build());
                            if (list.contains(x509Certificate2)) {
                                targetConstraints.setRevocationEnabled(false);
                            } else {
                                targetConstraints.setRevocationEnabled(true);
                            }
                            java.util.List<? extends java.security.cert.Certificate> certificates = pKIXCertPathBuilderSpi.engineBuild(new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder(targetConstraints.build()).build()).getCertPath().getCertificates();
                            arrayList.add(x509Certificate2);
                            arrayList2.add(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getNextWorkingKey(certificates, 0, jcaJceHelper));
                        } catch (java.security.cert.CertPathBuilderException e) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CertPath for CRL signer failed to validate.", e);
                        } catch (java.security.cert.CertPathValidatorException e2) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e2);
                        } catch (java.lang.Exception e3) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException(e3.getMessage());
                        }
                    }
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException = null;
                for (i = 0; i < arrayList.size(); i++) {
                    boolean[] keyUsage = ((java.security.cert.X509Certificate) arrayList.get(i)).getKeyUsage();
                    if (keyUsage != null && (keyUsage.length <= 6 || !keyUsage[6])) {
                        annotatedException = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                    } else {
                        hashSet.add(arrayList2.get(i));
                    }
                }
                if (hashSet.isEmpty() && annotatedException == null) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot find a valid issuer certificate.");
                }
                if (hashSet.isEmpty() && annotatedException != null) {
                    throw annotatedException;
                }
                return hashSet;
            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e4) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuer certificate for CRL cannot be searched.", e4);
            }
        } catch (java.io.IOException e5) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", e5);
        }
    }

    protected static java.security.PublicKey processCRLG(java.security.cert.X509CRL x509crl, java.util.Set set) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.util.Iterator it = set.iterator();
        java.lang.Exception e = null;
        while (it.hasNext()) {
            java.security.PublicKey publicKey = (java.security.PublicKey) it.next();
            try {
                x509crl.verify(publicKey);
                return publicKey;
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        }
        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot verify CRL.", e);
    }

    protected static java.security.cert.X509CRL processCRLH(java.util.Set set, java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.util.Iterator it = set.iterator();
        java.lang.Exception e = null;
        while (it.hasNext()) {
            java.security.cert.X509CRL x509crl = (java.security.cert.X509CRL) it.next();
            try {
                x509crl.verify(publicKey);
                return x509crl;
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        }
        if (e == null) {
            return null;
        }
        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot verify delta CRL.", e);
    }

    protected static void processCRLC(java.security.cert.X509CRL x509crl, java.security.cert.X509CRL x509crl2, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        if (x509crl == null) {
            return;
        }
        if (x509crl.hasUnsupportedCriticalExtension()) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("delta CRL has unsupported critical extensions");
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint issuingDistributionPoint = com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl2, ISSUING_DISTRIBUTION_POINT));
            if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                if (!com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl).equals(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl2))) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
                }
                try {
                    com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint issuingDistributionPoint2 = com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
                    boolean z = true;
                    if (issuingDistributionPoint != null ? !issuingDistributionPoint.equals(issuingDistributionPoint2) : issuingDistributionPoint2 != null) {
                        z = false;
                    }
                    if (!z) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                    }
                    try {
                        com.android.internal.org.bouncycastle.asn1.ASN1Primitive extensionValue = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl2, AUTHORITY_KEY_IDENTIFIER);
                        try {
                            com.android.internal.org.bouncycastle.asn1.ASN1Primitive extensionValue2 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509crl, AUTHORITY_KEY_IDENTIFIER);
                            if (extensionValue == null) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL authority key identifier is null.");
                            }
                            if (extensionValue2 == null) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Delta CRL authority key identifier is null.");
                            }
                            if (!extensionValue.equals(extensionValue2)) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                            }
                        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", e);
                        }
                    } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", e2);
                    }
                } catch (java.lang.Exception e3) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", e3);
                }
            }
        } catch (java.lang.Exception e4) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
        }
    }

    protected static void processCRLI(java.util.Date date, java.security.cert.X509CRL x509crl, java.lang.Object obj, com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        if (pKIXExtendedParameters.isUseDeltasEnabled() && x509crl != null) {
            com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    protected static void processCRLJ(java.util.Date date, java.security.cert.X509CRL x509crl, java.lang.Object obj, com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        if (certStatus.getCertStatus() == 11) {
            com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00dd, code lost:
    
        r5 = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r4, com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES)).getObjects();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e5, code lost:
    
        if (r5.hasMoreElements() == false) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e8, code lost:
    
        r7 = com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r5.nextElement());
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fd, code lost:
    
        if (com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r7.getPolicyIdentifier().getId()) == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0108, code lost:
    
        r10 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(r7.getPolicyQualifiers());
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0124, code lost:
    
        if (r4.getCriticalExtensionOIDs() == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0126, code lost:
    
        r12 = r4.getCriticalExtensionOIDs().contains(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0133, code lost:
    
        r9 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) r6.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0142, code lost:
    
        if (com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r9.getValidPolicy()) == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0144, code lost:
    
        r8 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), r3, (java.util.Set) r13.get(r11), r9, r10, r11, r12);
        r9.addChild(r8);
        r21[r3].add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0132, code lost:
    
        r12 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x010a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0112, code lost:
    
        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", r0, r19, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0114, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x011c, code lost:
    
        throw new java.security.cert.CertPathValidatorException("Policy information could not be decoded.", r0, r19, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x011d, code lost:
    
        r10 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01cc, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode prepareCertB(java.security.cert.CertPath certPath, int i, java.util.List[] listArr, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode, int i2) throws java.security.cert.CertPathValidatorException {
        int i3;
        boolean z;
        java.util.List<? extends java.security.cert.Certificate> certificates = certPath.getCertificates();
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificates.get(i);
        int size = certificates.size() - i;
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509Certificate, POLICY_MAPPINGS));
            if (aSN1Sequence == null) {
                return pKIXPolicyNode;
            }
            java.util.HashMap hashMap = new java.util.HashMap();
            java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet();
            boolean z2 = false;
            for (int i4 = 0; i4 < aSN1Sequence.size(); i4++) {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(i4);
                java.lang.String id = ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence2.getObjectAt(0)).getId();
                java.lang.String id2 = ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence2.getObjectAt(1)).getId();
                if (!hashMap.containsKey(id)) {
                    java.util.HashSet hashSet2 = new java.util.HashSet();
                    hashSet2.add(id2);
                    hashMap.put(id, hashSet2);
                    hashSet.add(id);
                } else {
                    ((java.util.Set) hashMap.get(id)).add(id2);
                }
            }
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
            for (java.lang.String str : hashSet) {
                if (i2 > 0) {
                    java.util.Iterator it = listArr[size].iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = z2;
                            break;
                        }
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode3 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it.next();
                        if (pKIXPolicyNode3.getValidPolicy().equals(str)) {
                            pKIXPolicyNode3.expectedPolicies = (java.util.Set) hashMap.get(str);
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        continue;
                    } else {
                        java.util.Iterator it2 = listArr[size].iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode4 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it2.next();
                                if (ANY_POLICY.equals(pKIXPolicyNode4.getValidPolicy())) {
                                    try {
                                        break;
                                    } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
                                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Certificate policies extension could not be decoded.", e, certPath, i);
                                    }
                                }
                                z2 = false;
                            }
                        }
                    }
                } else if (i2 <= 0) {
                    java.util.Iterator it3 = listArr[size].iterator();
                    while (it3.hasNext()) {
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode5 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it3.next();
                        if (pKIXPolicyNode5.getValidPolicy().equals(str)) {
                            ((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) pKIXPolicyNode5.getParent()).removeChild(pKIXPolicyNode5);
                            it3.remove();
                            for (int i5 = size - 1; i5 >= 0; i5--) {
                                java.util.List list = listArr[i5];
                                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode6 = pKIXPolicyNode2;
                                while (i3 < list.size()) {
                                    com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode7 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i3);
                                    i3 = (pKIXPolicyNode7.hasChildren() || (pKIXPolicyNode6 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode6, listArr, pKIXPolicyNode7)) != null) ? i3 + 1 : 0;
                                }
                                pKIXPolicyNode2 = pKIXPolicyNode6;
                            }
                        }
                    }
                }
                z2 = false;
            }
            return pKIXPolicyNode2;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e2, certPath, i);
        }
    }

    protected static void prepareNextCertA(java.security.cert.CertPath certPath, int i) throws java.security.cert.CertPathValidatorException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), POLICY_MAPPINGS));
            if (aSN1Sequence != null) {
                for (int i2 = 0; i2 < aSN1Sequence.size(); i2++) {
                    try {
                        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i2));
                        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(0));
                        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2 = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(1));
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier.getId())) {
                            throw new java.security.cert.CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, certPath, i);
                        }
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier2.getId())) {
                            throw new java.security.cert.CertPathValidatorException("SubjectDomainPolicy is anyPolicy", null, certPath, i);
                        }
                    } catch (java.lang.Exception e) {
                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", e, certPath, i);
                    }
                }
            }
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e2, certPath, i);
        }
    }

    protected static void processCertF(java.security.cert.CertPath certPath, int i, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode, int i2) throws java.security.cert.CertPathValidatorException {
        if (i2 <= 0 && pKIXPolicyNode == null) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("No valid policy tree found when one expected.", null, certPath, i);
        }
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode processCertE(java.security.cert.CertPath certPath, int i, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) throws java.security.cert.CertPathValidatorException {
        try {
            if (com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), CERTIFICATE_POLICIES)) == null) {
                return null;
            }
            return pKIXPolicyNode;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e, certPath, i);
        }
    }

    protected static void processCertBC(java.security.cert.CertPath certPath, int i, com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator pKIXNameConstraintValidator, boolean z) throws java.security.cert.CertPathValidatorException {
        java.util.List<? extends java.security.cert.Certificate> certificates = certPath.getCertificates();
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificates.get(i);
        int size = certificates.size();
        int i2 = size - i;
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(x509Certificate) || (i2 >= size && !z)) {
            try {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getSubjectPrincipal(x509Certificate));
                try {
                    pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                    pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                    try {
                        com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME));
                        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence).getRDNs(com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle.EmailAddress);
                        for (int i3 = 0; i3 != rDNs.length; i3++) {
                            com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(1, ((com.android.internal.org.bouncycastle.asn1.ASN1String) rDNs[i3].getFirst().getValue()).getString());
                            try {
                                pKIXNameConstraintValidator.checkPermitted(generalName);
                                pKIXNameConstraintValidator.checkExcluded(generalName);
                            } catch (com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException e) {
                                throw new java.security.cert.CertPathValidatorException("Subtree check for certificate subject alternative email failed.", e, certPath, i);
                            }
                        }
                        if (generalNames != null) {
                            try {
                                com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = generalNames.getNames();
                                for (int i4 = 0; i4 < names.length; i4++) {
                                    try {
                                        pKIXNameConstraintValidator.checkPermitted(names[i4]);
                                        pKIXNameConstraintValidator.checkExcluded(names[i4]);
                                    } catch (com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException e2) {
                                        throw new java.security.cert.CertPathValidatorException("Subtree check for certificate subject alternative name failed.", e2, certPath, i);
                                    }
                                }
                            } catch (java.lang.Exception e3) {
                                throw new java.security.cert.CertPathValidatorException("Subject alternative name contents could not be decoded.", e3, certPath, i);
                            }
                        }
                    } catch (java.lang.Exception e4) {
                        throw new java.security.cert.CertPathValidatorException("Subject alternative name extension could not be decoded.", e4, certPath, i);
                    }
                } catch (com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException e5) {
                    throw new java.security.cert.CertPathValidatorException("Subtree check for certificate subject failed.", e5, certPath, i);
                }
            } catch (java.lang.Exception e6) {
                throw new java.security.cert.CertPathValidatorException("Exception extracting subject name when checking subtrees.", e6, certPath, i);
            }
        }
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode processCertD(java.security.cert.CertPath certPath, int i, java.util.Set set, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode, java.util.List[] listArr, int i2, boolean z) throws java.security.cert.CertPathValidatorException {
        java.lang.String str;
        int i3;
        java.util.List<? extends java.security.cert.Certificate> certificates = certPath.getCertificates();
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificates.get(i);
        int size = certificates.size();
        int i4 = size - i;
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES));
            if (aSN1Sequence != null && pKIXPolicyNode != null) {
                java.util.Enumeration objects = aSN1Sequence.getObjects();
                java.util.HashSet hashSet = new java.util.HashSet();
                while (objects.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation policyInformation = com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation.getInstance(objects.nextElement());
                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyIdentifier = policyInformation.getPolicyIdentifier();
                    hashSet.add(policyIdentifier.getId());
                    if (!ANY_POLICY.equals(policyIdentifier.getId())) {
                        try {
                            java.util.Set qualifierSet = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(policyInformation.getPolicyQualifiers());
                            if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.processCertD1i(i4, listArr, policyIdentifier, qualifierSet)) {
                                com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.processCertD1ii(i4, listArr, policyIdentifier, qualifierSet);
                            }
                        } catch (java.security.cert.CertPathValidatorException e) {
                            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info set could not be build.", e, certPath, i);
                        }
                    }
                }
                if (set.isEmpty() || set.contains(ANY_POLICY)) {
                    set.clear();
                    set.addAll(hashSet);
                } else {
                    java.util.HashSet hashSet2 = new java.util.HashSet();
                    for (java.lang.Object obj : set) {
                        if (hashSet.contains(obj)) {
                            hashSet2.add(obj);
                        }
                    }
                    set.clear();
                    set.addAll(hashSet2);
                }
                if (i2 > 0 || ((i4 < size || z) && com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(x509Certificate))) {
                    java.util.Enumeration objects2 = aSN1Sequence.getObjects();
                    while (true) {
                        if (!objects2.hasMoreElements()) {
                            break;
                        }
                        com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation policyInformation2 = com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation.getInstance(objects2.nextElement());
                        if (ANY_POLICY.equals(policyInformation2.getPolicyIdentifier().getId())) {
                            java.util.Set qualifierSet2 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(policyInformation2.getPolicyQualifiers());
                            java.util.List list = listArr[i4 - 1];
                            for (int i5 = 0; i5 < list.size(); i5++) {
                                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i5);
                                for (java.lang.Object obj2 : pKIXPolicyNode2.getExpectedPolicies()) {
                                    if (obj2 instanceof java.lang.String) {
                                        str = (java.lang.String) obj2;
                                    } else if (obj2 instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) {
                                        str = ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) obj2).getId();
                                    }
                                    java.util.Iterator children = pKIXPolicyNode2.getChildren();
                                    boolean z2 = false;
                                    while (children.hasNext()) {
                                        if (str.equals(((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) children.next()).getValidPolicy())) {
                                            z2 = true;
                                        }
                                    }
                                    if (!z2) {
                                        java.util.HashSet hashSet3 = new java.util.HashSet();
                                        hashSet3.add(str);
                                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode3 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), i4, hashSet3, pKIXPolicyNode2, qualifierSet2, str, false);
                                        pKIXPolicyNode2.addChild(pKIXPolicyNode3);
                                        listArr[i4].add(pKIXPolicyNode3);
                                    }
                                }
                            }
                        }
                    }
                }
                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode4 = pKIXPolicyNode;
                for (int i6 = i4 - 1; i6 >= 0; i6--) {
                    java.util.List list2 = listArr[i6];
                    while (i3 < list2.size()) {
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode5 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list2.get(i3);
                        i3 = (pKIXPolicyNode5.hasChildren() || (pKIXPolicyNode4 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode4, listArr, pKIXPolicyNode5)) != null) ? i3 + 1 : 0;
                    }
                }
                java.util.Set<java.lang.String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
                if (criticalExtensionOIDs != null) {
                    boolean contains = criticalExtensionOIDs.contains(CERTIFICATE_POLICIES);
                    java.util.List list3 = listArr[i4];
                    for (int i7 = 0; i7 < list3.size(); i7++) {
                        ((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list3.get(i7)).setCritical(contains);
                    }
                }
                return pKIXPolicyNode4;
            }
            return null;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e2, certPath, i);
        }
    }

    protected static void processCertA(java.security.cert.CertPath certPath, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date, com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker pKIXCertRevocationChecker, int i, java.security.PublicKey publicKey, boolean z, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertPathValidatorException {
        java.security.cert.X509Certificate x509Certificate2 = (java.security.cert.X509Certificate) certPath.getCertificates().get(i);
        if (!z) {
            try {
                com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.verifyX509Certificate(x509Certificate2, publicKey, pKIXExtendedParameters.getSigProvider());
            } catch (java.security.GeneralSecurityException e) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not validate certificate signature.", e, certPath, i);
            }
        }
        try {
            java.util.Date validCertDateFromValidityModel = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getValidCertDateFromValidityModel(date, pKIXExtendedParameters.getValidityModel(), certPath, i);
            try {
                x509Certificate2.checkValidity(validCertDateFromValidityModel);
                if (pKIXCertRevocationChecker != null) {
                    pKIXCertRevocationChecker.initialize(new com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters(pKIXExtendedParameters, validCertDateFromValidityModel, certPath, i, x509Certificate, publicKey));
                    pKIXCertRevocationChecker.check(x509Certificate2);
                }
                com.android.internal.org.bouncycastle.asn1.x500.X500Name issuerPrincipal = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509Certificate2);
                if (!issuerPrincipal.equals(x500Name)) {
                    throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("IssuerName(" + issuerPrincipal + ") does not match SubjectName(" + x500Name + ") of signing certificate.", null, certPath, i);
                }
            } catch (java.security.cert.CertificateExpiredException e2) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not validate certificate: " + e2.getMessage(), e2, certPath, i);
            } catch (java.security.cert.CertificateNotYetValidException e3) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not validate certificate: " + e3.getMessage(), e3, certPath, i);
            }
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e4) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Could not validate time of certificate.", e4, certPath, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0030, code lost:
    
        r3 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
    
        if (r3 >= r5) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003b, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static int prepareNextCertI1(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                java.util.Enumeration objects = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(objects.nextElement());
                        if (aSN1TaggedObject.getTagNo() == 0) {
                            break;
                        }
                    } catch (java.lang.IllegalArgumentException e) {
                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e, certPath, i);
                    }
                }
            }
            return i2;
        } catch (java.lang.Exception e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e2, certPath, i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
    
        r4 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
    
        if (r4 >= r6) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static int prepareNextCertI2(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                java.util.Enumeration objects = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(objects.nextElement());
                        if (aSN1TaggedObject.getTagNo() == 1) {
                            break;
                        }
                    } catch (java.lang.IllegalArgumentException e) {
                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e, certPath, i);
                    }
                }
            }
            return i2;
        } catch (java.lang.Exception e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e2, certPath, i);
        }
    }

    protected static void prepareNextCertG(java.security.cert.CertPath certPath, int i, com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator pKIXNameConstraintValidator) throws java.security.cert.CertPathValidatorException {
        com.android.internal.org.bouncycastle.asn1.x509.NameConstraints nameConstraints;
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), NAME_CONSTRAINTS));
            if (aSN1Sequence == null) {
                nameConstraints = null;
            } else {
                nameConstraints = com.android.internal.org.bouncycastle.asn1.x509.NameConstraints.getInstance(aSN1Sequence);
            }
            if (nameConstraints != null) {
                com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] permittedSubtrees = nameConstraints.getPermittedSubtrees();
                if (permittedSubtrees != null) {
                    try {
                        pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                    } catch (java.lang.Exception e) {
                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", e, certPath, i);
                    }
                }
                com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] excludedSubtrees = nameConstraints.getExcludedSubtrees();
                if (excludedSubtrees != null) {
                    for (int i2 = 0; i2 != excludedSubtrees.length; i2++) {
                        try {
                            pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i2]);
                        } catch (java.lang.Exception e2) {
                            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", e2, certPath, i);
                        }
                    }
                }
            }
        } catch (java.lang.Exception e3) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Name constraints extension could not be decoded.", e3, certPath, i);
        }
    }

    private static void checkCRL(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date, java.util.Date date2, java.security.cert.X509Certificate x509Certificate, java.security.cert.X509Certificate x509Certificate2, java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus, com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask, java.util.List list, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        java.util.Iterator it;
        java.security.cert.X509CRL x509crl;
        java.util.Set<java.lang.String> criticalExtensionOIDs;
        if (date2.getTime() > date.getTime()) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Validation time is in future.");
        }
        java.util.Iterator it2 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getCompleteCRLs(pKIXCertRevocationCheckerParameters, distributionPoint, x509Certificate, pKIXExtendedParameters, date2).iterator();
        boolean z = false;
        com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e = null;
        while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                java.security.cert.X509CRL x509crl2 = (java.security.cert.X509CRL) it2.next();
                com.android.internal.org.bouncycastle.jce.provider.ReasonsMask processCRLD = processCRLD(x509crl2, distributionPoint);
                if (processCRLD.hasNewReasons(reasonsMask)) {
                    it = it2;
                    com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException = e;
                    try {
                        java.security.PublicKey processCRLG = processCRLG(x509crl2, processCRLF(x509crl2, x509Certificate, x509Certificate2, publicKey, pKIXExtendedParameters, list, jcaJceHelper));
                        if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                            try {
                                x509crl = processCRLH(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getDeltaCRLs(date2, x509crl2, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores(), jcaJceHelper), processCRLG);
                            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
                                e = e2;
                                it2 = it;
                            }
                        } else {
                            x509crl = null;
                        }
                        if (pKIXExtendedParameters.getValidityModel() != 1 && x509Certificate.getNotAfter().getTime() < x509crl2.getThisUpdate().getTime()) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No valid CRL for current time found.");
                        }
                        processCRLB1(distributionPoint, x509Certificate, x509crl2);
                        processCRLB2(distributionPoint, x509Certificate, x509crl2);
                        processCRLC(x509crl, x509crl2, pKIXExtendedParameters);
                        processCRLI(date2, x509crl, x509Certificate, certStatus, pKIXExtendedParameters);
                        processCRLJ(date2, x509crl2, x509Certificate, certStatus);
                        if (certStatus.getCertStatus() == 8) {
                            certStatus.setCertStatus(11);
                        }
                        reasonsMask.addReasons(processCRLD);
                        java.util.Set<java.lang.String> criticalExtensionOIDs2 = x509crl2.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs2 != null) {
                            java.util.HashSet hashSet = new java.util.HashSet(criticalExtensionOIDs2);
                            hashSet.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
                            hashSet.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId());
                            if (!hashSet.isEmpty()) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL contains unsupported critical extensions.");
                            }
                        }
                        if (x509crl != null && (criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs()) != null) {
                            java.util.HashSet hashSet2 = new java.util.HashSet(criticalExtensionOIDs);
                            hashSet2.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
                            hashSet2.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId());
                            if (!hashSet2.isEmpty()) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Delta CRL contains unsupported critical extension.");
                            }
                        }
                        it2 = it;
                        z = true;
                        e = annotatedException;
                    } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e3) {
                        e = e3;
                    }
                } else {
                    continue;
                }
            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e4) {
                e = e4;
                it = it2;
            }
        }
        com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException2 = e;
        if (!z) {
            throw annotatedException2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static void checkCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date, java.util.Date date2, java.security.cert.X509Certificate x509Certificate, java.security.cert.X509Certificate x509Certificate2, java.security.PublicKey publicKey, java.util.List list, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        int i;
        com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus;
        com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e;
        boolean z;
        int i2;
        com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPointArr;
        int i3;
        com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus2;
        com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus3;
        try {
            com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint cRLDistPoint = com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(x509Certificate, CRL_DISTRIBUTION_POINTS));
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder builder = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder(pKIXExtendedParameters);
            try {
                java.util.Iterator<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> it = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap(), date2, jcaJceHelper).iterator();
                while (it.hasNext()) {
                    builder.addCRLStore(it.next());
                }
                com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus4 = new com.android.internal.org.bouncycastle.jce.provider.CertStatus();
                com.android.internal.org.bouncycastle.jce.provider.ReasonsMask reasonsMask = new com.android.internal.org.bouncycastle.jce.provider.ReasonsMask();
                com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters build = builder.build();
                boolean z2 = true;
                int i4 = 11;
                java.lang.Object obj = null;
                if (cRLDistPoint == null) {
                    i = 11;
                    certStatus = certStatus4;
                } else {
                    try {
                        com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
                        if (distributionPoints == null) {
                            i = 11;
                            certStatus = certStatus4;
                        } else {
                            e = null;
                            int i5 = 0;
                            z = false;
                            while (i5 < distributionPoints.length && certStatus4.getCertStatus() == i4 && !reasonsMask.isAllReasons()) {
                                try {
                                    i2 = i5;
                                    distributionPointArr = distributionPoints;
                                    i3 = i4;
                                    certStatus2 = certStatus4;
                                    try {
                                        checkCRL(pKIXCertRevocationCheckerParameters, distributionPoints[i5], build, date, date2, x509Certificate, x509Certificate2, publicKey, certStatus4, reasonsMask, list, jcaJceHelper);
                                        z = true;
                                    } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
                                        e = e2;
                                    }
                                } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e3) {
                                    e = e3;
                                    i2 = i5;
                                    distributionPointArr = distributionPoints;
                                    i3 = i4;
                                    certStatus2 = certStatus4;
                                }
                                i5 = i2 + 1;
                                i4 = i3;
                                distributionPoints = distributionPointArr;
                                certStatus4 = certStatus2;
                                obj = null;
                            }
                            i = i4;
                            certStatus = certStatus4;
                            if (certStatus.getCertStatus() == i) {
                                try {
                                } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e4) {
                                    e = e4;
                                    z2 = z;
                                }
                                if (!reasonsMask.isAllReasons()) {
                                    try {
                                        checkCRL(pKIXCertRevocationCheckerParameters, new com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint(new com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName(0, new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(4, com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509Certificate)))), null, null), (com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters) pKIXExtendedParameters.clone(), date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                                        if (!z2) {
                                            if (e instanceof com.android.internal.org.bouncycastle.jce.provider.AnnotatedException) {
                                                throw e;
                                            }
                                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No valid CRL found.", e);
                                        }
                                        if (certStatus.getCertStatus() != i) {
                                            com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus5 = certStatus;
                                            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                                            simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
                                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException(("Certificate revocation after " + simpleDateFormat.format(certStatus5.getRevocationDate())) + ", reason: " + crlReasons[certStatus5.getCertStatus()]);
                                        }
                                        if (reasonsMask.isAllReasons() || certStatus.getCertStatus() != i) {
                                            certStatus3 = certStatus;
                                        } else {
                                            certStatus3 = certStatus;
                                            certStatus3.setCertStatus(12);
                                        }
                                        if (certStatus3.getCertStatus() == 12) {
                                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Certificate status could not be determined.");
                                        }
                                        return;
                                    } catch (java.lang.RuntimeException e5) {
                                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuer from certificate for CRL could not be reencoded.", e5);
                                    }
                                }
                            }
                            z2 = z;
                            if (!z2) {
                            }
                        }
                    } catch (java.lang.Exception e6) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Distribution points could not be read.", e6);
                    }
                }
                e = null;
                z = false;
                if (certStatus.getCertStatus() == i) {
                }
                z2 = z;
                if (!z2) {
                }
            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e7) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", e7);
            }
        } catch (java.lang.Exception e8) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL distribution point extension could not be read.", e8);
        }
    }

    protected static int prepareNextCertJ(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        int intValueExact;
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), INHIBIT_ANY_POLICY));
            if (aSN1Integer != null && (intValueExact = aSN1Integer.intValueExact()) < i2) {
                return intValueExact;
            }
            return i2;
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertK(java.security.cert.CertPath certPath, int i) throws java.security.cert.CertPathValidatorException {
        try {
            com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints = com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            if (basicConstraints != null) {
                if (!basicConstraints.isCA()) {
                    throw new java.security.cert.CertPathValidatorException("Not a CA certificate", null, certPath, i);
                }
                return;
            }
            throw new java.security.cert.CertPathValidatorException("Intermediate certificate lacks BasicConstraints", null, certPath, i);
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static int prepareNextCertL(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued((java.security.cert.X509Certificate) certPath.getCertificates().get(i))) {
            if (i2 <= 0) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Max path length not greater than zero", null, certPath, i);
            }
            return i2 - 1;
        }
        return i2;
    }

    protected static int prepareNextCertM(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        java.math.BigInteger pathLenConstraint;
        int intValue;
        try {
            com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints = com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            if (basicConstraints != null && (pathLenConstraint = basicConstraints.getPathLenConstraint()) != null && (intValue = pathLenConstraint.intValue()) < i2) {
                return intValue;
            }
            return i2;
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertN(java.security.cert.CertPath certPath, int i) throws java.security.cert.CertPathValidatorException {
        boolean[] keyUsage = ((java.security.cert.X509Certificate) certPath.getCertificates().get(i)).getKeyUsage();
        if (keyUsage != null) {
            if (keyUsage.length <= 5 || !keyUsage[5]) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, certPath, i);
            }
        }
    }

    protected static void prepareNextCertO(java.security.cert.CertPath certPath, int i, java.util.Set set, java.util.List list) throws java.security.cert.CertPathValidatorException {
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certPath.getCertificates().get(i);
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((java.security.cert.PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (java.security.cert.CertPathValidatorException e) {
                throw new java.security.cert.CertPathValidatorException(e.getMessage(), e.getCause(), certPath, i);
            }
        }
        if (!set.isEmpty()) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i);
        }
    }

    protected static int prepareNextCertH1(java.security.cert.CertPath certPath, int i, int i2) {
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued((java.security.cert.X509Certificate) certPath.getCertificates().get(i)) && i2 != 0) {
            return i2 - 1;
        }
        return i2;
    }

    protected static int prepareNextCertH2(java.security.cert.CertPath certPath, int i, int i2) {
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued((java.security.cert.X509Certificate) certPath.getCertificates().get(i)) && i2 != 0) {
            return i2 - 1;
        }
        return i2;
    }

    protected static int prepareNextCertH3(java.security.cert.CertPath certPath, int i, int i2) {
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued((java.security.cert.X509Certificate) certPath.getCertificates().get(i)) && i2 != 0) {
            return i2 - 1;
        }
        return i2;
    }

    protected static int wrapupCertA(int i, java.security.cert.X509Certificate x509Certificate) {
        if (!com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(x509Certificate) && i != 0) {
            return i - 1;
        }
        return i;
    }

    protected static int wrapupCertB(java.security.cert.CertPath certPath, int i, int i2) throws java.security.cert.CertPathValidatorException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue((java.security.cert.X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                java.util.Enumeration objects = aSN1Sequence.getObjects();
                while (objects.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objects.nextElement();
                    switch (aSN1TaggedObject.getTagNo()) {
                        case 0:
                            try {
                                if (com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false).intValueExact() != 0) {
                                    break;
                                } else {
                                    return 0;
                                }
                            } catch (java.lang.Exception e) {
                                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", e, certPath, i);
                            }
                    }
                }
            }
            return i2;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy constraints could not be decoded.", e2, certPath, i);
        }
    }

    protected static void wrapupCertF(java.security.cert.CertPath certPath, int i, java.util.List list, java.util.Set set) throws java.security.cert.CertPathValidatorException {
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certPath.getCertificates().get(i);
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((java.security.cert.PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (java.security.cert.CertPathValidatorException e) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException(e.getMessage(), e, certPath, i);
            } catch (java.lang.Exception e2) {
                throw new java.security.cert.CertPathValidatorException("Additional certificate path checker failed.", e2, certPath, i);
            }
        }
        if (!set.isEmpty()) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i);
        }
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode wrapupCertG(java.security.cert.CertPath certPath, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Set set, int i, java.util.List[] listArr, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode, java.util.Set set2) throws java.security.cert.CertPathValidatorException {
        int size = certPath.getCertificates().size();
        if (pKIXPolicyNode == null) {
            if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
            }
            return null;
        }
        if (com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isAnyPolicy(set)) {
            if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
                if (set2.isEmpty()) {
                    throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                for (java.util.List list : listArr) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i2);
                        if (ANY_POLICY.equals(pKIXPolicyNode2.getValidPolicy())) {
                            java.util.Iterator children = pKIXPolicyNode2.getChildren();
                            while (children.hasNext()) {
                                hashSet.add(children.next());
                            }
                        }
                    }
                }
                java.util.Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    set2.contains(((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it.next()).getValidPolicy());
                }
                if (pKIXPolicyNode != null) {
                    for (int i3 = size - 1; i3 >= 0; i3--) {
                        java.util.List list2 = listArr[i3];
                        for (int i4 = 0; i4 < list2.size(); i4++) {
                            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode3 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list2.get(i4);
                            if (!pKIXPolicyNode3.hasChildren()) {
                                pKIXPolicyNode = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3);
                            }
                        }
                    }
                }
            }
            return pKIXPolicyNode;
        }
        java.util.HashSet<com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode> hashSet2 = new java.util.HashSet();
        for (java.util.List list3 : listArr) {
            for (int i5 = 0; i5 < list3.size(); i5++) {
                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode4 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list3.get(i5);
                if (ANY_POLICY.equals(pKIXPolicyNode4.getValidPolicy())) {
                    java.util.Iterator children2 = pKIXPolicyNode4.getChildren();
                    while (children2.hasNext()) {
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode5 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) children2.next();
                        if (!ANY_POLICY.equals(pKIXPolicyNode5.getValidPolicy())) {
                            hashSet2.add(pKIXPolicyNode5);
                        }
                    }
                }
            }
        }
        for (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode6 : hashSet2) {
            if (!set.contains(pKIXPolicyNode6.getValidPolicy())) {
                pKIXPolicyNode = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode6);
            }
        }
        if (pKIXPolicyNode == null) {
            return pKIXPolicyNode;
        }
        for (int i6 = size - 1; i6 >= 0; i6--) {
            java.util.List list4 = listArr[i6];
            for (int i7 = 0; i7 < list4.size(); i7++) {
                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode7 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list4.get(i7);
                if (!pKIXPolicyNode7.hasChildren()) {
                    pKIXPolicyNode = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode7);
                }
            }
        }
        return pKIXPolicyNode;
    }
}
