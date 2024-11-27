package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class CertPathValidatorUtilities {
    protected static final java.lang.String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    protected static final java.lang.String CERTIFICATE_POLICIES = com.android.internal.org.bouncycastle.asn1.x509.Extension.certificatePolicies.getId();
    protected static final java.lang.String BASIC_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.basicConstraints.getId();
    protected static final java.lang.String POLICY_MAPPINGS = com.android.internal.org.bouncycastle.asn1.x509.Extension.policyMappings.getId();
    protected static final java.lang.String SUBJECT_ALTERNATIVE_NAME = com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName.getId();
    protected static final java.lang.String NAME_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.nameConstraints.getId();
    protected static final java.lang.String KEY_USAGE = com.android.internal.org.bouncycastle.asn1.x509.Extension.keyUsage.getId();
    protected static final java.lang.String INHIBIT_ANY_POLICY = com.android.internal.org.bouncycastle.asn1.x509.Extension.inhibitAnyPolicy.getId();
    protected static final java.lang.String ISSUING_DISTRIBUTION_POINT = com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId();
    protected static final java.lang.String DELTA_CRL_INDICATOR = com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId();
    protected static final java.lang.String POLICY_CONSTRAINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.policyConstraints.getId();
    protected static final java.lang.String FRESHEST_CRL = com.android.internal.org.bouncycastle.asn1.x509.Extension.freshestCRL.getId();
    protected static final java.lang.String CRL_DISTRIBUTION_POINTS = com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints.getId();
    protected static final java.lang.String AUTHORITY_KEY_IDENTIFIER = com.android.internal.org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier.getId();
    protected static final java.lang.String CRL_NUMBER = com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLNumber.getId();
    protected static final java.lang.String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    CertPathValidatorUtilities() {
    }

    static java.util.Collection findTargets(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters pKIXExtendedBuilderParameters) throws java.security.cert.CertPathBuilderException {
        com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters baseParameters = pKIXExtendedBuilderParameters.getBaseParameters();
        com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector targetConstraints = baseParameters.getTargetConstraints();
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        try {
            findCertificates(linkedHashSet, targetConstraints, baseParameters.getCertificateStores());
            findCertificates(linkedHashSet, targetConstraints, baseParameters.getCertStores());
            if (!linkedHashSet.isEmpty()) {
                return linkedHashSet;
            }
            java.security.cert.Certificate certificate = targetConstraints.getCertificate();
            if (certificate == null) {
                throw new java.security.cert.CertPathBuilderException("No certificate found matching targetConstraints.");
            }
            return java.util.Collections.singleton(certificate);
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathBuilderException("Error finding target certificate.", e);
        }
    }

    protected static java.security.cert.TrustAnchor findTrustAnchor(java.security.cert.X509Certificate x509Certificate, java.util.Set set) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        return findTrustAnchor(x509Certificate, set, null);
    }

    protected static java.security.cert.TrustAnchor findTrustAnchor(java.security.cert.X509Certificate x509Certificate, java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.security.cert.X509CertSelector x509CertSelector = new java.security.cert.X509CertSelector();
        javax.security.auth.x500.X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
        x509CertSelector.setSubject(issuerX500Principal);
        java.util.Iterator it = set.iterator();
        java.security.cert.TrustAnchor trustAnchor = null;
        java.lang.Exception e = null;
        com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name = null;
        java.security.PublicKey publicKey = null;
        while (it.hasNext() && trustAnchor == null) {
            trustAnchor = (java.security.cert.TrustAnchor) it.next();
            if (trustAnchor.getTrustedCert() != null) {
                if (x509CertSelector.match(trustAnchor.getTrustedCert())) {
                    publicKey = trustAnchor.getTrustedCert().getPublicKey();
                } else {
                    trustAnchor = null;
                }
            } else if (trustAnchor.getCA() != null && trustAnchor.getCAName() != null && trustAnchor.getCAPublicKey() != null) {
                if (x500Name == null) {
                    x500Name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(issuerX500Principal.getEncoded());
                }
                try {
                    if (x500Name.equals(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(trustAnchor.getCA().getEncoded()))) {
                        publicKey = trustAnchor.getCAPublicKey();
                    } else {
                        trustAnchor = null;
                    }
                } catch (java.lang.IllegalArgumentException e2) {
                    trustAnchor = null;
                }
            } else {
                trustAnchor = null;
            }
            if (publicKey != null) {
                try {
                    verifyX509Certificate(x509Certificate, publicKey, str);
                } catch (java.lang.Exception e3) {
                    e = e3;
                    trustAnchor = null;
                    publicKey = null;
                }
            }
        }
        if (trustAnchor == null && e != null) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("TrustAnchor found but certificate validation failed.", e);
        }
        return trustAnchor;
    }

    static boolean isIssuerTrustAnchor(java.security.cert.X509Certificate x509Certificate, java.util.Set set, java.lang.String str) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        try {
            return findTrustAnchor(x509Certificate, set, str) != null;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    static java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> getAdditionalStoresFromAltNames(byte[] bArr, java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> map) throws java.security.cert.CertificateParsingException {
        if (bArr == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(bArr).getOctets()).getNames();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i != names.length; i++) {
            com.android.internal.org.bouncycastle.jcajce.PKIXCertStore pKIXCertStore = map.get(names[i]);
            if (pKIXCertStore != null) {
                arrayList.add(pKIXCertStore);
            }
        }
        return arrayList;
    }

    protected static java.util.Date getValidityDate(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date) {
        java.util.Date validityDate = pKIXExtendedParameters.getValidityDate();
        return validityDate == null ? date : validityDate;
    }

    protected static boolean isSelfIssued(java.security.cert.X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    protected static com.android.internal.org.bouncycastle.asn1.ASN1Primitive getExtensionValue(java.security.cert.X509Extension x509Extension, java.lang.String str) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        byte[] extensionValue = x509Extension.getExtensionValue(str);
        if (extensionValue == null) {
            return null;
        }
        return getObject(str, extensionValue);
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObject(java.lang.String str, byte[] bArr) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(bArr).getOctets());
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("exception processing extension " + str, e);
        }
    }

    protected static com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier(java.security.PublicKey publicKey) throws java.security.cert.CertPathValidatorException {
        try {
            return com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getAlgorithm();
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Subject public key cannot be decoded.", e);
        }
    }

    protected static final java.util.Set getQualifierSet(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws java.security.cert.CertPathValidatorException {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (aSN1Sequence == null) {
            return hashSet;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream create = com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(byteArrayOutputStream);
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            try {
                create.writeObject((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement());
                hashSet.add(new java.security.cert.PolicyQualifierInfo(byteArrayOutputStream.toByteArray()));
                byteArrayOutputStream.reset();
            } catch (java.io.IOException e) {
                throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", e);
            }
        }
        return hashSet;
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode removePolicyNode(com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode, java.util.List[] listArr, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2) {
        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode3 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) pKIXPolicyNode2.getParent();
        if (pKIXPolicyNode == null) {
            return null;
        }
        if (pKIXPolicyNode3 == null) {
            for (int i = 0; i < listArr.length; i++) {
                listArr[i] = new java.util.ArrayList();
            }
            return null;
        }
        pKIXPolicyNode3.removeChild(pKIXPolicyNode2);
        removePolicyNodeRecurse(listArr, pKIXPolicyNode2);
        return pKIXPolicyNode;
    }

    private static void removePolicyNodeRecurse(java.util.List[] listArr, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) {
        listArr[pKIXPolicyNode.getDepth()].remove(pKIXPolicyNode);
        if (pKIXPolicyNode.hasChildren()) {
            java.util.Iterator children = pKIXPolicyNode.getChildren();
            while (children.hasNext()) {
                removePolicyNodeRecurse(listArr, (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) children.next());
            }
        }
    }

    protected static boolean processCertD1i(int i, java.util.List[] listArr, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.util.Set set) {
        java.util.List list = listArr[i - 1];
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i2);
            if (pKIXPolicyNode.getExpectedPolicies().contains(aSN1ObjectIdentifier.getId())) {
                java.util.HashSet hashSet = new java.util.HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), i, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i].add(pKIXPolicyNode2);
                return true;
            }
        }
        return false;
    }

    protected static void processCertD1ii(int i, java.util.List[] listArr, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.util.Set set) {
        java.util.List list = listArr[i - 1];
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i2);
            if ("2.5.29.32.0".equals(pKIXPolicyNode.getValidPolicy())) {
                java.util.HashSet hashSet = new java.util.HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), i, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i].add(pKIXPolicyNode2);
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x009e, code lost:
    
        if (r15.getCriticalExtensionOIDs() == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a0, code lost:
    
        r8 = r15.getCriticalExtensionOIDs().contains(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.CERTIFICATE_POLICIES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
    
        r9 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) r3.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00bc, code lost:
    
        if ("2.5.29.32.0".equals(r9.getValidPolicy()) == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00be, code lost:
    
        r10 = new com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), r11, (java.util.Set) r14.get(r13), r9, r7, r13, r8);
        r9.addChild(r10);
        r12[r11].add(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ac, code lost:
    
        r8 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static void prepareNextCertB1(int i, java.util.List[] listArr, java.lang.String str, java.util.Map map, java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, java.security.cert.CertPathValidatorException {
        boolean z;
        java.util.Set set;
        java.util.Iterator it = listArr[i].iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it.next();
            if (pKIXPolicyNode.getValidPolicy().equals(str)) {
                pKIXPolicyNode.expectedPolicies = (java.util.Set) map.get(str);
                z = true;
                break;
            }
        }
        if (!z) {
            for (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 : listArr[i]) {
                if ("2.5.29.32.0".equals(pKIXPolicyNode2.getValidPolicy())) {
                    try {
                        java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.DERSequence.getInstance(getExtensionValue(x509Certificate, CERTIFICATE_POLICIES)).getObjects();
                        while (true) {
                            if (!objects.hasMoreElements()) {
                                set = null;
                                break;
                            }
                            try {
                                com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation policyInformation = com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation.getInstance(objects.nextElement());
                                if ("2.5.29.32.0".equals(policyInformation.getPolicyIdentifier().getId())) {
                                    try {
                                        set = getQualifierSet(policyInformation.getPolicyQualifiers());
                                        break;
                                    } catch (java.security.cert.CertPathValidatorException e) {
                                        throw new com.android.internal.org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info set could not be built.", e);
                                    }
                                }
                            } catch (java.lang.Exception e2) {
                                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Policy information cannot be decoded.", e2);
                            }
                        }
                    } catch (java.lang.Exception e3) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Certificate policies cannot be decoded.", e3);
                    }
                }
            }
        }
    }

    protected static com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode prepareNextCertB2(int i, java.util.List[] listArr, java.lang.String str, com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode) {
        int i2;
        java.util.Iterator it = listArr[i].iterator();
        while (it.hasNext()) {
            com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode2 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) it.next();
            if (pKIXPolicyNode2.getValidPolicy().equals(str)) {
                ((com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) pKIXPolicyNode2.getParent()).removeChild(pKIXPolicyNode2);
                it.remove();
                for (int i3 = i - 1; i3 >= 0; i3--) {
                    java.util.List list = listArr[i3];
                    while (i2 < list.size()) {
                        com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode pKIXPolicyNode3 = (com.android.internal.org.bouncycastle.jce.provider.PKIXPolicyNode) list.get(i2);
                        i2 = (pKIXPolicyNode3.hasChildren() || (pKIXPolicyNode = removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3)) != null) ? i2 + 1 : 0;
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }

    protected static boolean isAnyPolicy(java.util.Set set) {
        return set == null || set.contains("2.5.29.32.0") || set.isEmpty();
    }

    protected static void findCertificates(java.util.LinkedHashSet linkedHashSet, com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector pKIXCertStoreSelector, java.util.List list) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                linkedHashSet.addAll(com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.getCertificates(pKIXCertStoreSelector, (java.security.cert.CertStore) it.next()));
            } catch (java.security.cert.CertStoreException e) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Problem while picking certificates from certificate store.", e);
            }
        }
    }

    static java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint cRLDistPoint, java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> map, java.util.Date date, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        if (cRLDistPoint == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint : distributionPoints) {
                com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2 != null && distributionPoint2.getType() == 0) {
                    for (com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName : com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(distributionPoint2.getName()).getNames()) {
                        com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore pKIXCRLStore = map.get(generalName);
                        if (pKIXCRLStore != null) {
                            arrayList.add(pKIXCRLStore);
                        }
                    }
                }
            }
            if (arrayList.isEmpty() && com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.enableCRLDP")) {
                try {
                    jcaJceHelper.createCertificateFactory("X.509");
                    for (int i = 0; i < distributionPoints.length; i++) {
                        com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint3 = distributionPoints[i].getDistributionPoint();
                        if (distributionPoint3 != null && distributionPoint3.getType() == 0) {
                            com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(distributionPoint3.getName()).getNames();
                            for (int i2 = 0; i2 < names.length && names[i].getTagNo() != 6; i2++) {
                            }
                        }
                    }
                } catch (java.lang.Exception e) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("cannot create certificate factory: " + e.getMessage(), e);
                }
            }
            return arrayList;
        } catch (java.lang.Exception e2) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Distribution points could not be read.", e2);
        }
    }

    protected static void getCRLIssuersFromDistributionPoint(com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint, java.util.Collection collection, java.security.cert.X509CRLSelector x509CRLSelector) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (distributionPoint.getCRLIssuer() != null) {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    try {
                        arrayList.add(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(names[i].getName().toASN1Primitive().getEncoded()));
                    } catch (java.io.IOException e) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e);
                    }
                }
            }
        } else {
            if (distributionPoint.getDistributionPoint() == null) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
            }
            java.util.Iterator it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                x509CRLSelector.addIssuerName(((com.android.internal.org.bouncycastle.asn1.x500.X500Name) it2.next()).getEncoded());
            } catch (java.io.IOException e2) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot decode CRL issuer information.", e2);
            }
        }
    }

    private static java.math.BigInteger getSerialNumber(java.lang.Object obj) {
        return ((java.security.cert.X509Certificate) obj).getSerialNumber();
    }

    protected static void getCertStatus(java.util.Date date, java.security.cert.X509CRL x509crl, java.lang.Object obj, com.android.internal.org.bouncycastle.jce.provider.CertStatus certStatus) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.security.cert.X509CRLEntry revokedCertificate;
        com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated;
        int intValueExact;
        com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name;
        try {
            if (com.android.internal.org.bouncycastle.jce.provider.X509CRLObject.isIndirectCRL(x509crl)) {
                revokedCertificate = x509crl.getRevokedCertificate(getSerialNumber(obj));
                if (revokedCertificate == null) {
                    return;
                }
                javax.security.auth.x500.X500Principal certificateIssuer = revokedCertificate.getCertificateIssuer();
                if (certificateIssuer == null) {
                    x500Name = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl);
                } else {
                    x500Name = com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getX500Name(certificateIssuer);
                }
                if (!com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getEncodedIssuerPrincipal(obj).equals(x500Name)) {
                    return;
                }
            } else if (!com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getEncodedIssuerPrincipal(obj).equals(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl)) || (revokedCertificate = x509crl.getRevokedCertificate(getSerialNumber(obj))) == null) {
                return;
            }
            if (!revokedCertificate.hasExtensions()) {
                aSN1Enumerated = null;
            } else {
                if (revokedCertificate.hasUnsupportedCriticalExtension()) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL entry has unsupported critical extensions.");
                }
                try {
                    aSN1Enumerated = com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(getExtensionValue(revokedCertificate, com.android.internal.org.bouncycastle.asn1.x509.Extension.reasonCode.getId()));
                } catch (java.lang.Exception e) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Reason code CRL entry extension could not be decoded.", e);
                }
            }
            if (aSN1Enumerated == null) {
                intValueExact = 0;
            } else {
                intValueExact = aSN1Enumerated.intValueExact();
            }
            if (date.getTime() >= revokedCertificate.getRevocationDate().getTime() || intValueExact == 0 || intValueExact == 1 || intValueExact == 2 || intValueExact == 10) {
                certStatus.setCertStatus(intValueExact);
                certStatus.setRevocationDate(revokedCertificate.getRevocationDate());
            }
        } catch (java.security.cert.CRLException e2) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Failed check for indirect CRL.", e2);
        }
    }

    protected static java.util.Set getDeltaCRLs(java.util.Date date, java.security.cert.X509CRL x509crl, java.util.List<java.security.cert.CertStore> list, java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> list2, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.math.BigInteger bigInteger;
        java.security.cert.X509CRLSelector x509CRLSelector = new java.security.cert.X509CRLSelector();
        try {
            x509CRLSelector.addIssuerName(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded());
            try {
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive extensionValue = getExtensionValue(x509crl, CRL_NUMBER);
                if (extensionValue == null) {
                    bigInteger = null;
                } else {
                    bigInteger = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(extensionValue).getPositiveValue();
                }
                try {
                    byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                    x509CRLSelector.setMinCRLNumber(bigInteger != null ? bigInteger.add(java.math.BigInteger.valueOf(1L)) : null);
                    com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder builder = new com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder(x509CRLSelector);
                    builder.setIssuingDistributionPoint(extensionValue2);
                    builder.setIssuingDistributionPointEnabled(true);
                    builder.setMaxBaseCRLNumber(bigInteger);
                    java.util.Set<java.security.cert.X509CRL> findCRLs = com.android.internal.org.bouncycastle.jce.provider.PKIXCRLUtil.findCRLs(builder.build(), date, list, list2);
                    if (findCRLs.isEmpty() && com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.enableCRLDP")) {
                        try {
                            jcaJceHelper.createCertificateFactory("X.509");
                            com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPoints = com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(extensionValue2).getDistributionPoints();
                            for (int i = 0; i < distributionPoints.length; i++) {
                                com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint = distributionPoints[i].getDistributionPoint();
                                if (distributionPoint != null && distributionPoint.getType() == 0) {
                                    com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(distributionPoint.getName()).getNames();
                                    for (int i2 = 0; i2 < names.length && names[i].getTagNo() != 6; i2++) {
                                    }
                                }
                            }
                        } catch (java.lang.Exception e) {
                            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("cannot create certificate factory: " + e.getMessage(), e);
                        }
                    }
                    java.util.HashSet hashSet = new java.util.HashSet();
                    for (java.security.cert.X509CRL x509crl2 : findCRLs) {
                        if (isDeltaCRL(x509crl2)) {
                            hashSet.add(x509crl2);
                        }
                    }
                    return hashSet;
                } catch (java.lang.Exception e2) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuing distribution point extension value could not be read.", e2);
                }
            } catch (java.lang.Exception e3) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("CRL number extension could not be extracted from CRL.", e3);
            }
        } catch (java.io.IOException e4) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot extract issuer from CRL.", e4);
        }
    }

    private static boolean isDeltaCRL(java.security.cert.X509CRL x509crl) {
        java.util.Set<java.lang.String> criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        return criticalExtensionOIDs.contains(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    protected static java.util.Set getCompleteCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint distributionPoint, java.lang.Object obj, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException, com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        java.security.cert.X509CRLSelector x509CRLSelector = new java.security.cert.X509CRLSelector();
        try {
            java.util.HashSet hashSet = new java.util.HashSet();
            hashSet.add(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getEncodedIssuerPrincipal(obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLSelector);
            if (obj instanceof java.security.cert.X509Certificate) {
                x509CRLSelector.setCertificateChecking((java.security.cert.X509Certificate) obj);
            }
            java.util.Set findCRLs = com.android.internal.org.bouncycastle.jce.provider.PKIXCRLUtil.findCRLs(new com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build(), date, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores());
            checkCRLsNotEmpty(pKIXCertRevocationCheckerParameters, findCRLs, obj);
            return findCRLs;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Could not get issuer information from distribution point.", e);
        }
    }

    protected static java.util.Date getValidCertDateFromValidityModel(java.util.Date date, int i, java.security.cert.CertPath certPath, int i2) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime;
        if (1 != i || i2 <= 0) {
            return date;
        }
        int i3 = i2 - 1;
        java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certPath.getCertificates().get(i3);
        if (i3 == 0) {
            try {
                byte[] extensionValue = ((java.security.cert.X509Certificate) certPath.getCertificates().get(i3)).getExtensionValue(com.android.internal.org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                if (extensionValue == null) {
                    aSN1GeneralizedTime = null;
                } else {
                    aSN1GeneralizedTime = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionValue));
                }
                if (aSN1GeneralizedTime != null) {
                    try {
                        return aSN1GeneralizedTime.getDate();
                    } catch (java.text.ParseException e) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Date from date of cert gen extension could not be parsed.", e);
                    }
                }
            } catch (java.io.IOException e2) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Date of cert gen extension could not be read.");
            } catch (java.lang.IllegalArgumentException e3) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Date of cert gen extension could not be read.");
            }
        }
        return x509Certificate.getNotBefore();
    }

    protected static java.security.PublicKey getNextWorkingKey(java.util.List list, int i, com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) throws java.security.cert.CertPathValidatorException {
        java.security.interfaces.DSAPublicKey dSAPublicKey;
        java.security.PublicKey publicKey = ((java.security.cert.Certificate) list.get(i)).getPublicKey();
        if (!(publicKey instanceof java.security.interfaces.DSAPublicKey)) {
            return publicKey;
        }
        java.security.interfaces.DSAPublicKey dSAPublicKey2 = (java.security.interfaces.DSAPublicKey) publicKey;
        if (dSAPublicKey2.getParams() != null) {
            return dSAPublicKey2;
        }
        do {
            i++;
            if (i < list.size()) {
                java.security.PublicKey publicKey2 = ((java.security.cert.X509Certificate) list.get(i)).getPublicKey();
                if (!(publicKey2 instanceof java.security.interfaces.DSAPublicKey)) {
                    throw new java.security.cert.CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
                }
                dSAPublicKey = (java.security.interfaces.DSAPublicKey) publicKey2;
            } else {
                throw new java.security.cert.CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
        } while (dSAPublicKey.getParams() == null);
        java.security.interfaces.DSAParams params = dSAPublicKey.getParams();
        try {
            return jcaJceHelper.createKeyFactory("DSA").generatePublic(new java.security.spec.DSAPublicKeySpec(dSAPublicKey2.getY(), params.getP(), params.getQ(), params.getG()));
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    static java.util.Collection findIssuerCerts(java.security.cert.X509Certificate x509Certificate, java.util.List<java.security.cert.CertStore> list, java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> list2) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        byte[] keyIdentifier;
        java.security.cert.X509CertSelector x509CertSelector = new java.security.cert.X509CertSelector();
        try {
            x509CertSelector.setSubject(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal(x509Certificate).getEncoded());
            try {
                byte[] extensionValue = x509Certificate.getExtensionValue(AUTHORITY_KEY_IDENTIFIER);
                if (extensionValue != null && (keyIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(extensionValue).getOctets()).getKeyIdentifier()) != null) {
                    x509CertSelector.setSubjectKeyIdentifier(new com.android.internal.org.bouncycastle.asn1.DEROctetString(keyIdentifier).getEncoded());
                }
            } catch (java.lang.Exception e) {
            }
            com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector<? extends java.security.cert.Certificate> build = new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.Builder(x509CertSelector).build();
            java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
            try {
                findCertificates(linkedHashSet, build, list);
                findCertificates(linkedHashSet, build, list2);
                return linkedHashSet;
            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e2) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Issuer certificate cannot be searched.", e2);
            }
        } catch (java.lang.Exception e3) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e3);
        }
    }

    protected static void verifyX509Certificate(java.security.cert.X509Certificate x509Certificate, java.security.PublicKey publicKey, java.lang.String str) throws java.security.GeneralSecurityException {
        if (str == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, str);
        }
    }

    static void checkCRLsNotEmpty(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, java.util.Set set, java.lang.Object obj) throws com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException {
        if (set.isEmpty()) {
            if (obj instanceof com.android.internal.org.bouncycastle.x509.X509AttributeCertificate) {
                throw new com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException("No CRLs found for issuer \"" + ((com.android.internal.org.bouncycastle.x509.X509AttributeCertificate) obj).getIssuer().getPrincipals()[0] + "\"", null, pKIXCertRevocationCheckerParameters.getCertPath(), pKIXCertRevocationCheckerParameters.getIndex());
            }
            throw new com.android.internal.org.bouncycastle.jce.provider.RecoverableCertPathValidatorException("No CRLs found for issuer \"" + com.android.internal.org.bouncycastle.asn1.x500.style.RFC4519Style.INSTANCE.toString(com.android.internal.org.bouncycastle.jce.provider.PrincipalUtils.getIssuerPrincipal((java.security.cert.X509Certificate) obj)) + "\"", null, pKIXCertRevocationCheckerParameters.getCertPath(), pKIXCertRevocationCheckerParameters.getIndex());
        }
    }
}
