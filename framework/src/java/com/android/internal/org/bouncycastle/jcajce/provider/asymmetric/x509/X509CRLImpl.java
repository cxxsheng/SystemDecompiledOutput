package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
abstract class X509CRLImpl extends java.security.cert.X509CRL {
    protected com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper bcHelper;
    protected com.android.internal.org.bouncycastle.asn1.x509.CertificateList c;
    protected boolean isIndirect;
    protected java.lang.String sigAlgName;
    protected byte[] sigAlgParams;

    X509CRLImpl(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList, java.lang.String str, byte[] bArr, boolean z) {
        this.bcHelper = jcaJceHelper;
        this.c = certificateList;
        this.sigAlgName = str;
        this.sigAlgParams = bArr;
        this.isIndirect = z;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        java.util.Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        criticalExtensionOIDs.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
        criticalExtensionOIDs.remove(com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator.getId());
        return !criticalExtensionOIDs.isEmpty();
    }

    private java.util.Set getExtensionOIDs(boolean z) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
        if (getVersion() == 2 && (extensions = this.c.getTBSCertList().getExtensions()) != null) {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Enumeration oids = extensions.oids();
            while (oids.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                if (z == extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                    hashSet.add(aSN1ObjectIdentifier.getId());
                }
            }
            return hashSet;
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString extensionValue = getExtensionValue(this.c, str);
        if (extensionValue != null) {
            try {
                return extensionValue.getEncoded();
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalStateException("error parsing " + e.toString());
            }
        }
        return null;
    }

    @Override // java.security.cert.X509CRL
    public byte[] getEncoded() throws java.security.cert.CRLException {
        try {
            return this.c.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CRLException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public void verify(java.security.PublicKey publicKey) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.1
            @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
            public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
                try {
                    return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.this.bcHelper.createSignature(str);
                } catch (java.lang.Exception e) {
                    return java.security.Signature.getInstance(str);
                }
            }
        });
    }

    @Override // java.security.cert.X509CRL
    public void verify(java.security.PublicKey publicKey, final java.lang.String str) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.2
            @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
            public java.security.Signature createSignature(java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
                if (str != null) {
                    return java.security.Signature.getInstance(str2, str);
                }
                return java.security.Signature.getInstance(str2);
            }
        });
    }

    @Override // java.security.cert.X509CRL
    public void verify(java.security.PublicKey publicKey, final java.security.Provider provider) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        try {
            doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.3
                @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
                public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
                    if (provider != null) {
                        return java.security.Signature.getInstance(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.this.getSigAlgName(), provider);
                    }
                    return java.security.Signature.getInstance(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLImpl.this.getSigAlgName());
                }
            });
        } catch (java.security.NoSuchProviderException e) {
            throw new java.security.NoSuchAlgorithmException("provider issue: " + e.getMessage());
        }
    }

    private void doVerify(java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator signatureCreator) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.security.NoSuchProviderException {
        if (!this.c.getSignatureAlgorithm().equals(this.c.getTBSCertList().getSignature())) {
            throw new java.security.cert.CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
        }
        int i = 0;
        if ((publicKey instanceof com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) && com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.isCompositeAlgorithm(this.c.getSignatureAlgorithm())) {
            java.util.List<java.security.PublicKey> publicKeys = ((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) publicKey).getPublicKeys();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.c.getSignatureAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(this.c.getSignature()).getBytes());
            boolean z = false;
            while (i != publicKeys.size()) {
                if (publicKeys.get(i) != null) {
                    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
                    try {
                        checkSignature(publicKeys.get(i), signatureCreator.createSignature(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(algorithmIdentifier)), algorithmIdentifier.getParameters(), com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence2.getObjectAt(i)).getBytes());
                        e = null;
                        z = true;
                    } catch (java.security.SignatureException e) {
                        e = e;
                    }
                    if (e != null) {
                        throw e;
                    }
                }
                i++;
            }
            if (!z) {
                throw new java.security.InvalidKeyException("no matching key found");
            }
            return;
        }
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.isCompositeAlgorithm(this.c.getSignatureAlgorithm())) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence3 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.c.getSignatureAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence4 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(this.c.getSignature()).getBytes());
            boolean z2 = false;
            while (i != aSN1Sequence4.size()) {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2 = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence3.getObjectAt(i));
                try {
                    checkSignature(publicKey, signatureCreator.createSignature(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(algorithmIdentifier2)), algorithmIdentifier2.getParameters(), com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence4.getObjectAt(i)).getBytes());
                    e = null;
                    z2 = true;
                } catch (java.security.InvalidKeyException e2) {
                    e = null;
                } catch (java.security.NoSuchAlgorithmException e3) {
                    e = null;
                } catch (java.security.SignatureException e4) {
                    e = e4;
                }
                if (e == null) {
                    i++;
                } else {
                    throw e;
                }
            }
            if (!z2) {
                throw new java.security.InvalidKeyException("no matching key found");
            }
            return;
        }
        java.security.Signature createSignature = signatureCreator.createSignature(getSigAlgName());
        if (this.sigAlgParams == null) {
            checkSignature(publicKey, createSignature, null, getSignature());
            return;
        }
        try {
            checkSignature(publicKey, createSignature, com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(this.sigAlgParams), getSignature());
        } catch (java.io.IOException e5) {
            throw new java.security.SignatureException("cannot decode signature parameters: " + e5.getMessage());
        }
    }

    private void checkSignature(java.security.PublicKey publicKey, java.security.Signature signature, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable, byte[] bArr) throws java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException, java.security.cert.CRLException {
        if (aSN1Encodable != null) {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.setSignatureParameters(signature, aSN1Encodable);
        }
        signature.initVerify(publicKey);
        try {
            java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signature), 512);
            this.c.getTBSCertList().encodeTo(bufferedOutputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            bufferedOutputStream.close();
            if (!signature.verify(bArr)) {
                throw new java.security.SignatureException("CRL does not verify with supplied public key.");
            }
        } catch (java.io.IOException e) {
            throw new java.security.cert.CRLException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public int getVersion() {
        return this.c.getVersionNumber();
    }

    @Override // java.security.cert.X509CRL
    public java.security.Principal getIssuerDN() {
        return new com.android.internal.org.bouncycastle.jce.X509Principal(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.c.getIssuer().toASN1Primitive()));
    }

    @Override // java.security.cert.X509CRL
    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        try {
            return new javax.security.auth.x500.X500Principal(this.c.getIssuer().getEncoded());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("can't encode issuer DN");
        }
    }

    @Override // java.security.cert.X509CRL
    public java.util.Date getThisUpdate() {
        return this.c.getThisUpdate().getDate();
    }

    @Override // java.security.cert.X509CRL
    public java.util.Date getNextUpdate() {
        com.android.internal.org.bouncycastle.asn1.x509.Time nextUpdate = this.c.getNextUpdate();
        if (nextUpdate == null) {
            return null;
        }
        return nextUpdate.getDate();
    }

    private java.util.Set loadCRLEntries() {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Enumeration revokedCertificateEnumeration = this.c.getRevokedCertificateEnumeration();
        com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name = null;
        while (revokedCertificateEnumeration.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry = (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            hashSet.add(new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name));
            if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) != null) {
                x500Name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
            }
        }
        return hashSet;
    }

    @Override // java.security.cert.X509CRL
    public java.security.cert.X509CRLEntry getRevokedCertificate(java.math.BigInteger bigInteger) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        java.util.Enumeration revokedCertificateEnumeration = this.c.getRevokedCertificateEnumeration();
        com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name = null;
        while (revokedCertificateEnumeration.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry = (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            if (cRLEntry.getUserCertificate().hasValue(bigInteger)) {
                return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name);
            }
            if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) != null) {
                x500Name = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
            }
        }
        return null;
    }

    @Override // java.security.cert.X509CRL
    public java.util.Set getRevokedCertificates() {
        java.util.Set loadCRLEntries = loadCRLEntries();
        if (!loadCRLEntries.isEmpty()) {
            return java.util.Collections.unmodifiableSet(loadCRLEntries);
        }
        return null;
    }

    @Override // java.security.cert.X509CRL
    public byte[] getTBSCertList() throws java.security.cert.CRLException {
        try {
            return this.c.getTBSCertList().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CRLException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSignature() {
        return this.c.getSignature().getOctets();
    }

    @Override // java.security.cert.X509CRL
    public java.lang.String getSigAlgName() {
        return this.sigAlgName;
    }

    @Override // java.security.cert.X509CRL
    public java.lang.String getSigAlgOID() {
        return this.c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSigAlgParams() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.sigAlgParams);
    }

    @Override // java.security.cert.CRL
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("              Version: ").append(getVersion()).append(lineSeparator);
        stringBuffer.append("             IssuerDN: ").append(getIssuerDN()).append(lineSeparator);
        stringBuffer.append("          This update: ").append(getThisUpdate()).append(lineSeparator);
        stringBuffer.append("          Next update: ").append(getNextUpdate()).append(lineSeparator);
        stringBuffer.append("  Signature Algorithm: ").append(getSigAlgName()).append(lineSeparator);
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.prettyPrintSignature(getSignature(), stringBuffer, lineSeparator);
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertList().getExtensions();
        if (extensions != null) {
            java.util.Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                stringBuffer.append("           Extensions: ").append(lineSeparator);
            }
            while (oids.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                com.android.internal.org.bouncycastle.asn1.x509.Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer.append("                       critical(").append(extension.isCritical()).append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLNumber)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.x509.CRLNumber(com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator)) {
                            stringBuffer.append("Base CRL: " + new com.android.internal.org.bouncycastle.asn1.x509.CRLNumber(com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint)) {
                            stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints)) {
                            stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.freshestCRL)) {
                            stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
                        } else {
                            stringBuffer.append(aSN1ObjectIdentifier.getId());
                            stringBuffer.append(" value = ").append(com.android.internal.org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(aSN1InputStream.readObject())).append(lineSeparator);
                        }
                    } catch (java.lang.Exception e) {
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ").append("*****").append(lineSeparator);
                    }
                } else {
                    stringBuffer.append(lineSeparator);
                }
            }
        }
        java.util.Set revokedCertificates = getRevokedCertificates();
        if (revokedCertificates != null) {
            java.util.Iterator it = revokedCertificates.iterator();
            while (it.hasNext()) {
                stringBuffer.append(it.next());
                stringBuffer.append(lineSeparator);
            }
        }
        return stringBuffer.toString();
    }

    @Override // java.security.cert.CRL
    public boolean isRevoked(java.security.cert.Certificate certificate) {
        com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        if (!certificate.getType().equals("X.509")) {
            throw new java.lang.IllegalArgumentException("X.509 CRL used with non X.509 Cert");
        }
        java.util.Enumeration revokedCertificateEnumeration = this.c.getRevokedCertificateEnumeration();
        com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer2 = this.c.getIssuer();
        if (revokedCertificateEnumeration.hasMoreElements()) {
            java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) certificate;
            java.math.BigInteger serialNumber = x509Certificate.getSerialNumber();
            while (revokedCertificateEnumeration.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry = com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry.getInstance(revokedCertificateEnumeration.nextElement());
                if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) != null) {
                    issuer2 = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
                }
                if (cRLEntry.getUserCertificate().hasValue(serialNumber)) {
                    if (certificate instanceof java.security.cert.X509Certificate) {
                        issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Certificate.getIssuerX500Principal().getEncoded());
                    } else {
                        try {
                            issuer = com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(certificate.getEncoded()).getIssuer();
                        } catch (java.security.cert.CertificateEncodingException e) {
                            throw new java.lang.IllegalArgumentException("Cannot process certificate: " + e.getMessage());
                        }
                    }
                    return issuer2.equals(issuer);
                }
            }
        }
        return false;
    }

    protected static byte[] getExtensionOctets(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList, java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString extensionValue = getExtensionValue(certificateList, str);
        if (extensionValue != null) {
            return extensionValue.getOctets();
        }
        return null;
    }

    protected static com.android.internal.org.bouncycastle.asn1.ASN1OctetString getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList, java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = certificateList.getTBSCertList().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            return extension.getExtnValue();
        }
        return null;
    }
}
