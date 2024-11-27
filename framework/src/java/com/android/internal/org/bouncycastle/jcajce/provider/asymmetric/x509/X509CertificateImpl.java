package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
abstract class X509CertificateImpl extends java.security.cert.X509Certificate implements com.android.internal.org.bouncycastle.jcajce.interfaces.BCX509Certificate {
    protected com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints;
    protected com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper bcHelper;
    protected com.android.internal.org.bouncycastle.asn1.x509.Certificate c;
    protected boolean[] keyUsage;
    protected java.lang.String sigAlgName;
    protected byte[] sigAlgParams;

    X509CertificateImpl(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper, com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate, com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints, boolean[] zArr, java.lang.String str, byte[] bArr) {
        this.bcHelper = jcaJceHelper;
        this.c = certificate;
        this.basicConstraints = basicConstraints;
        this.keyUsage = zArr;
        this.sigAlgName = str;
        this.sigAlgParams = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.interfaces.BCX509Certificate
    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuerX500Name() {
        return this.c.getIssuer();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.interfaces.BCX509Certificate
    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate getTBSCertificateNative() {
        return this.c.getTBSCertificate();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.interfaces.BCX509Certificate
    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubjectX500Name() {
        return this.c.getSubject();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        checkValidity(new java.util.Date());
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException {
        if (date.getTime() > getNotAfter().getTime()) {
            throw new java.security.cert.CertificateExpiredException("certificate expired on " + this.c.getEndDate().getTime());
        }
        if (date.getTime() < getNotBefore().getTime()) {
            throw new java.security.cert.CertificateNotYetValidException("certificate not valid till " + this.c.getStartDate().getTime());
        }
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        return this.c.getVersionNumber();
    }

    @Override // java.security.cert.X509Certificate
    public java.math.BigInteger getSerialNumber() {
        return this.c.getSerialNumber().getValue();
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getIssuerDN() {
        return new com.android.internal.org.bouncycastle.jce.X509Principal(this.c.getIssuer());
    }

    @Override // java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        try {
            return new javax.security.auth.x500.X500Principal(this.c.getIssuer().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("can't encode issuer DN");
        }
    }

    @Override // java.security.cert.X509Certificate
    public java.security.Principal getSubjectDN() {
        return new com.android.internal.org.bouncycastle.jce.X509Principal(this.c.getSubject());
    }

    @Override // java.security.cert.X509Certificate
    public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
        try {
            return new javax.security.auth.x500.X500Principal(this.c.getSubject().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("can't encode subject DN");
        }
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotBefore() {
        return this.c.getStartDate().getDate();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Date getNotAfter() {
        return this.c.getEndDate().getDate();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() throws java.security.cert.CertificateEncodingException {
        try {
            return this.c.getTBSCertificate().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        return this.c.getSignature().getOctets();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgName() {
        return this.sigAlgName;
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgOID() {
        return this.c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.sigAlgParams);
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueId = this.c.getTBSCertificate().getIssuerUniqueId();
        if (issuerUniqueId != null) {
            byte[] bytes = issuerUniqueId.getBytes();
            int length = (bytes.length * 8) - issuerUniqueId.getPadBits();
            boolean[] zArr = new boolean[length];
            for (int i = 0; i != length; i++) {
                zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
            }
            return zArr;
        }
        return null;
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        com.android.internal.org.bouncycastle.asn1.DERBitString subjectUniqueId = this.c.getTBSCertificate().getSubjectUniqueId();
        if (subjectUniqueId != null) {
            byte[] bytes = subjectUniqueId.getBytes();
            int length = (bytes.length * 8) - subjectUniqueId.getPadBits();
            boolean[] zArr = new boolean[length];
            for (int i = 0; i != length; i++) {
                zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
            }
            return zArr;
        }
        return null;
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.keyUsage);
    }

    @Override // java.security.cert.X509Certificate
    public java.util.List getExtendedKeyUsage() throws java.security.cert.CertificateParsingException {
        byte[] extensionOctets = getExtensionOctets(this.c, "2.5.29.37");
        if (extensionOctets == null) {
            return null;
        }
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionOctets));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i != aSN1Sequence.size(); i++) {
                arrayList.add(((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(i)).getId());
            }
            return java.util.Collections.unmodifiableList(arrayList);
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateParsingException("error processing extended key usage extension");
        }
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        if (this.basicConstraints == null || !this.basicConstraints.isCA()) {
            return -1;
        }
        if (this.basicConstraints.getPathLenConstraint() == null) {
            return Integer.MAX_VALUE;
        }
        return this.basicConstraints.getPathLenConstraint().intValue();
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Collection getSubjectAlternativeNames() throws java.security.cert.CertificateParsingException {
        return getAlternativeNames(this.c, com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName.getId());
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Collection getIssuerAlternativeNames() throws java.security.cert.CertificateParsingException {
        return getAlternativeNames(this.c, com.android.internal.org.bouncycastle.asn1.x509.Extension.issuerAlternativeName.getId());
    }

    @Override // java.security.cert.X509Extension
    public java.util.Set getCriticalExtensionOIDs() {
        if (getVersion() == 3) {
            java.util.HashSet hashSet = new java.util.HashSet();
            com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertificate().getExtensions();
            if (extensions != null) {
                java.util.Enumeration oids = extensions.oids();
                while (oids.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                    if (extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                        hashSet.add(aSN1ObjectIdentifier.getId());
                    }
                }
                return hashSet;
            }
            return null;
        }
        return null;
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

    @Override // java.security.cert.X509Extension
    public java.util.Set getNonCriticalExtensionOIDs() {
        if (getVersion() == 3) {
            java.util.HashSet hashSet = new java.util.HashSet();
            com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertificate().getExtensions();
            if (extensions != null) {
                java.util.Enumeration oids = extensions.oids();
                while (oids.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                    if (!extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                        hashSet.add(aSN1ObjectIdentifier.getId());
                    }
                }
                return hashSet;
            }
            return null;
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
        if (getVersion() == 3 && (extensions = this.c.getTBSCertificate().getExtensions()) != null) {
            java.util.Enumeration oids = extensions.oids();
            while (oids.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                if (!aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.keyUsage) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.certificatePolicies) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.policyMappings) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.inhibitAnyPolicy) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.deltaCRLIndicator) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.policyConstraints) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.basicConstraints) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName) && !aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.nameConstraints) && extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override // java.security.cert.Certificate
    public java.security.PublicKey getPublicKey() {
        try {
            return com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPublicKey(this.c.getSubjectPublicKeyInfo());
        } catch (java.io.IOException e) {
            return null;
        }
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        try {
            return this.c.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.Certificate
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("  [0]         Version: ").append(getVersion()).append(lineSeparator);
        stringBuffer.append("         SerialNumber: ").append(getSerialNumber()).append(lineSeparator);
        stringBuffer.append("             IssuerDN: ").append(getIssuerDN()).append(lineSeparator);
        stringBuffer.append("           Start Date: ").append(getNotBefore()).append(lineSeparator);
        stringBuffer.append("           Final Date: ").append(getNotAfter()).append(lineSeparator);
        stringBuffer.append("            SubjectDN: ").append(getSubjectDN()).append(lineSeparator);
        stringBuffer.append("           Public Key: ").append(getPublicKey()).append(lineSeparator);
        stringBuffer.append("  Signature Algorithm: ").append(getSigAlgName()).append(lineSeparator);
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.prettyPrintSignature(getSignature(), stringBuffer, lineSeparator);
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertificate().getExtensions();
        if (extensions != null) {
            java.util.Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                stringBuffer.append("       Extensions: \n");
            }
            while (oids.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                com.android.internal.org.bouncycastle.asn1.x509.Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer.append("                       critical(").append(extension.isCritical()).append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.basicConstraints)) {
                            stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.keyUsage)) {
                            stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.KeyUsage.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.netscapeCertType)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.NetscapeCertType(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1InputStream.readObject()))).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.netscapeRevocationURL)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.NetscapeRevocationURL(com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(aSN1InputStream.readObject()))).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.verisignCzagExtension)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.VerisignCzagExtension(com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(aSN1InputStream.readObject()))).append(lineSeparator);
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
        return stringBuffer.toString();
    }

    @Override // java.security.cert.Certificate
    public final void verify(java.security.PublicKey publicKey) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl.1
            @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
            public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException {
                try {
                    return com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl.this.bcHelper.createSignature(str);
                } catch (java.lang.Exception e) {
                    return java.security.Signature.getInstance(str);
                }
            }
        });
    }

    @Override // java.security.cert.Certificate
    public final void verify(java.security.PublicKey publicKey, final java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl.2
            @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
            public java.security.Signature createSignature(java.lang.String str2) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
                if (str != null) {
                    return java.security.Signature.getInstance(str2, str);
                }
                return java.security.Signature.getInstance(str2);
            }
        });
    }

    @Override // java.security.cert.X509Certificate, java.security.cert.Certificate
    public final void verify(java.security.PublicKey publicKey, final java.security.Provider provider) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        try {
            doVerify(publicKey, new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl.3
                @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator
                public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException {
                    if (provider != null) {
                        return java.security.Signature.getInstance(str, provider);
                    }
                    return java.security.Signature.getInstance(str);
                }
            });
        } catch (java.security.NoSuchProviderException e) {
            throw new java.security.NoSuchAlgorithmException("provider issue: " + e.getMessage());
        }
    }

    private void doVerify(java.security.PublicKey publicKey, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.SignatureCreator signatureCreator) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.security.NoSuchProviderException {
        boolean z = publicKey instanceof com.android.internal.org.bouncycastle.jcajce.CompositePublicKey;
        int i = 0;
        if (z && com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.isCompositeAlgorithm(this.c.getSignatureAlgorithm())) {
            java.util.List<java.security.PublicKey> publicKeys = ((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) publicKey).getPublicKeys();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.c.getSignatureAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(this.c.getSignature()).getBytes());
            boolean z2 = false;
            while (i != publicKeys.size()) {
                if (publicKeys.get(i) != null) {
                    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
                    try {
                        checkSignature(publicKeys.get(i), signatureCreator.createSignature(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(algorithmIdentifier)), algorithmIdentifier.getParameters(), com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence2.getObjectAt(i)).getBytes());
                        e = null;
                        z2 = true;
                    } catch (java.security.SignatureException e) {
                        e = e;
                    }
                    if (e != null) {
                        throw e;
                    }
                }
                i++;
            }
            if (!z2) {
                throw new java.security.InvalidKeyException("no matching key found");
            }
            return;
        }
        if (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.isCompositeAlgorithm(this.c.getSignatureAlgorithm())) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence3 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.c.getSignatureAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence4 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(this.c.getSignature()).getBytes());
            boolean z3 = false;
            while (i != aSN1Sequence4.size()) {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2 = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence3.getObjectAt(i));
                try {
                    checkSignature(publicKey, signatureCreator.createSignature(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(algorithmIdentifier2)), algorithmIdentifier2.getParameters(), com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence4.getObjectAt(i)).getBytes());
                    e = null;
                    z3 = true;
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
            if (!z3) {
                throw new java.security.InvalidKeyException("no matching key found");
            }
            return;
        }
        java.security.Signature createSignature = signatureCreator.createSignature(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm()));
        if (z) {
            java.util.List<java.security.PublicKey> publicKeys2 = ((com.android.internal.org.bouncycastle.jcajce.CompositePublicKey) publicKey).getPublicKeys();
            while (i != publicKeys2.size()) {
                try {
                    checkSignature(publicKeys2.get(i), createSignature, this.c.getSignatureAlgorithm().getParameters(), getSignature());
                    return;
                } catch (java.security.InvalidKeyException e5) {
                    i++;
                }
            }
            throw new java.security.InvalidKeyException("no matching signature found");
        }
        checkSignature(publicKey, createSignature, this.c.getSignatureAlgorithm().getParameters(), getSignature());
    }

    private void checkSignature(java.security.PublicKey publicKey, java.security.Signature signature, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable, byte[] bArr) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        if (!isAlgIdEqual(this.c.getSignatureAlgorithm(), this.c.getTBSCertificate().getSignature())) {
            throw new java.security.cert.CertificateException("signature algorithm in TBS cert not same as outer cert");
        }
        com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509SignatureUtil.setSignatureParameters(signature, aSN1Encodable);
        signature.initVerify(publicKey);
        try {
            java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(com.android.internal.org.bouncycastle.jcajce.io.OutputStreamFactory.createStream(signature), 512);
            this.c.getTBSCertificate().encodeTo(bufferedOutputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            bufferedOutputStream.close();
            if (!signature.verify(bArr)) {
                throw new java.security.SignatureException("certificate does not verify with supplied key");
            }
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    private boolean isAlgIdEqual(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.allow_absent_equiv_NULL")) {
            if (algorithmIdentifier.getParameters() == null) {
                return algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            }
            if (algorithmIdentifier2.getParameters() == null) {
                return algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            }
        }
        if (algorithmIdentifier.getParameters() != null) {
            return algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
        }
        if (algorithmIdentifier2.getParameters() != null) {
            return algorithmIdentifier2.getParameters().equals(algorithmIdentifier.getParameters());
        }
        return true;
    }

    private static java.util.Collection getAlternativeNames(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate, java.lang.String str) throws java.security.cert.CertificateParsingException {
        byte[] extensionOctets = getExtensionOctets(certificate, str);
        if (extensionOctets == null) {
            return null;
        }
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(extensionOctets).getObjects();
            while (objects.hasMoreElements()) {
                com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName = com.android.internal.org.bouncycastle.asn1.x509.GeneralName.getInstance(objects.nextElement());
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                arrayList2.add(com.android.internal.org.bouncycastle.util.Integers.valueOf(generalName.getTagNo()));
                switch (generalName.getTagNo()) {
                    case 0:
                    case 3:
                    case 5:
                        arrayList2.add(generalName.getEncoded());
                        arrayList.add(java.util.Collections.unmodifiableList(arrayList2));
                    case 1:
                    case 2:
                    case 6:
                        arrayList2.add(((com.android.internal.org.bouncycastle.asn1.ASN1String) generalName.getName()).getString());
                        arrayList.add(java.util.Collections.unmodifiableList(arrayList2));
                    case 4:
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(com.android.internal.org.bouncycastle.asn1.x500.style.RFC4519Style.INSTANCE, generalName.getName()).toString());
                        arrayList.add(java.util.Collections.unmodifiableList(arrayList2));
                    case 7:
                        try {
                            arrayList2.add(java.net.InetAddress.getByAddress(com.android.internal.org.bouncycastle.asn1.DEROctetString.getInstance(generalName.getName()).getOctets()).getHostAddress());
                            arrayList.add(java.util.Collections.unmodifiableList(arrayList2));
                        } catch (java.net.UnknownHostException e) {
                        }
                    case 8:
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(generalName.getName()).getId());
                        arrayList.add(java.util.Collections.unmodifiableList(arrayList2));
                    default:
                        throw new java.io.IOException("Bad tag number: " + generalName.getTagNo());
                }
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return java.util.Collections.unmodifiableCollection(arrayList);
        } catch (java.lang.Exception e2) {
            throw new java.security.cert.CertificateParsingException(e2.getMessage());
        }
    }

    protected static byte[] getExtensionOctets(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate, java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString extensionValue = getExtensionValue(certificate, str);
        if (extensionValue != null) {
            return extensionValue.getOctets();
        }
        return null;
    }

    protected static com.android.internal.org.bouncycastle.asn1.ASN1OctetString getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate, java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = certificate.getTBSCertificate().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            return extension.getExtnValue();
        }
        return null;
    }
}
