package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class X509CRLObject extends java.security.cert.X509CRL {
    private com.android.internal.org.bouncycastle.asn1.x509.CertificateList c;
    private int hashCodeValue;
    private boolean isHashCodeSet = false;
    private boolean isIndirect;
    private java.lang.String sigAlgName;
    private byte[] sigAlgParams;

    public static boolean isIndirectCRL(java.security.cert.X509CRL x509crl) throws java.security.cert.CRLException {
        try {
            byte[] extensionValue = x509crl.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint.getId());
            if (extensionValue != null) {
                if (com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(extensionValue).getOctets()).isIndirectCRL()) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.ExtCRLException("Exception reading IssuingDistributionPoint", e);
        }
    }

    public X509CRLObject(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) throws java.security.cert.CRLException {
        this.c = certificateList;
        try {
            this.sigAlgName = com.android.internal.org.bouncycastle.jce.provider.X509SignatureUtil.getSignatureName(certificateList.getSignatureAlgorithm());
            if (certificateList.getSignatureAlgorithm().getParameters() != null) {
                this.sigAlgParams = certificateList.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            } else {
                this.sigAlgParams = null;
            }
            this.isIndirect = isIndirectCRL(this);
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CRLException("CRL contents invalid: " + e);
        }
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        java.util.Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        criticalExtensionOIDs.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
        criticalExtensionOIDs.remove(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
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
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertList().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            try {
                return extension.getExtnValue().getEncoded();
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
        java.security.Signature signature;
        try {
            signature = java.security.Signature.getInstance(getSigAlgName(), com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        } catch (java.lang.Exception e) {
            signature = java.security.Signature.getInstance(getSigAlgName());
        }
        doVerify(publicKey, signature);
    }

    @Override // java.security.cert.X509CRL
    public void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        java.security.Signature signature;
        if (str != null) {
            signature = java.security.Signature.getInstance(getSigAlgName(), str);
        } else {
            signature = java.security.Signature.getInstance(getSigAlgName());
        }
        doVerify(publicKey, signature);
    }

    @Override // java.security.cert.X509CRL
    public void verify(java.security.PublicKey publicKey, java.security.Provider provider) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        java.security.Signature signature;
        if (provider != null) {
            signature = java.security.Signature.getInstance(getSigAlgName(), provider);
        } else {
            signature = java.security.Signature.getInstance(getSigAlgName());
        }
        doVerify(publicKey, signature);
    }

    private void doVerify(java.security.PublicKey publicKey, java.security.Signature signature) throws java.security.cert.CRLException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        if (!this.c.getSignatureAlgorithm().equals(this.c.getTBSCertList().getSignature())) {
            throw new java.security.cert.CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
        }
        signature.initVerify(publicKey);
        signature.update(getTBSCertList());
        if (!signature.verify(getSignature())) {
            throw new java.security.SignatureException("CRL does not verify with supplied public key.");
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
        if (this.c.getNextUpdate() != null) {
            return this.c.getNextUpdate().getDate();
        }
        return null;
    }

    private java.util.Set loadCRLEntries() {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Enumeration revokedCertificateEnumeration = this.c.getRevokedCertificateEnumeration();
        com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name = null;
        while (revokedCertificateEnumeration.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry = (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            hashSet.add(new com.android.internal.org.bouncycastle.jce.provider.X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name));
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
                return new com.android.internal.org.bouncycastle.jce.provider.X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name);
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
        if (this.sigAlgParams != null) {
            int length = this.sigAlgParams.length;
            byte[] bArr = new byte[length];
            java.lang.System.arraycopy(this.sigAlgParams, 0, bArr, 0, length);
            return bArr;
        }
        return null;
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
        byte[] signature = getSignature();
        stringBuffer.append("            Signature: ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, 0, 20))).append(lineSeparator);
        for (int i = 20; i < signature.length; i += 20) {
            if (i < signature.length - 20) {
                stringBuffer.append("                       ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, i, 20))).append(lineSeparator);
            } else {
                stringBuffer.append("                       ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, i, signature.length - i))).append(lineSeparator);
            }
        }
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
            throw new java.lang.RuntimeException("X.509 CRL used with non X.509 Cert");
        }
        java.util.Enumeration revokedCertificateEnumeration = this.c.getRevokedCertificateEnumeration();
        com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer2 = this.c.getIssuer();
        if (revokedCertificateEnumeration != null) {
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
                            throw new java.lang.RuntimeException("Cannot process certificate");
                        }
                    }
                    if (!issuer2.equals(issuer)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.security.cert.X509CRL
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof java.security.cert.X509CRL)) {
            return false;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.jce.provider.X509CRLObject) {
            com.android.internal.org.bouncycastle.jce.provider.X509CRLObject x509CRLObject = (com.android.internal.org.bouncycastle.jce.provider.X509CRLObject) obj;
            if (this.isHashCodeSet && x509CRLObject.isHashCodeSet && x509CRLObject.hashCodeValue != this.hashCodeValue) {
                return false;
            }
            return this.c.equals(x509CRLObject.c);
        }
        return super.equals(obj);
    }

    @Override // java.security.cert.X509CRL
    public int hashCode() {
        if (!this.isHashCodeSet) {
            this.isHashCodeSet = true;
            this.hashCodeValue = super.hashCode();
        }
        return this.hashCodeValue;
    }
}
