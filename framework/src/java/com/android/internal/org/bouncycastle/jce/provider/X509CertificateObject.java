package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class X509CertificateObject extends java.security.cert.X509Certificate implements com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    private com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier attrCarrier = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl();
    private com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints basicConstraints;
    private com.android.internal.org.bouncycastle.asn1.x509.Certificate c;
    private byte[] encoded;
    private int hashValue;
    private boolean hashValueSet;
    private boolean[] keyUsage;

    public X509CertificateObject(com.android.internal.org.bouncycastle.asn1.x509.Certificate certificate) throws java.security.cert.CertificateParsingException {
        this.c = certificate;
        try {
            byte[] extensionBytes = getExtensionBytes("2.5.29.19");
            if (extensionBytes != null) {
                this.basicConstraints = com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionBytes));
            }
            try {
                byte[] extensionBytes2 = getExtensionBytes("2.5.29.15");
                if (extensionBytes2 != null) {
                    com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extensionBytes2));
                    byte[] bytes = dERBitString.getBytes();
                    int length = (bytes.length * 8) - dERBitString.getPadBits();
                    int i = 9;
                    if (length >= 9) {
                        i = length;
                    }
                    this.keyUsage = new boolean[i];
                    for (int i2 = 0; i2 != length; i2++) {
                        this.keyUsage[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
                    }
                    return;
                }
                this.keyUsage = null;
            } catch (java.lang.Exception e) {
                throw new java.security.cert.CertificateParsingException("cannot construct KeyUsage: " + e);
            }
        } catch (java.lang.Exception e2) {
            throw new java.security.cert.CertificateParsingException("cannot construct BasicConstraints: " + e2);
        }
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
            return new javax.security.auth.x500.X500Principal(this.c.getIssuer().getEncoded());
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
            return new javax.security.auth.x500.X500Principal(this.c.getSubject().getEncoded());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("can't encode issuer DN");
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
        java.lang.String property;
        java.security.Provider provider = java.security.Security.getProvider(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        if (provider != null && (property = provider.getProperty("Alg.Alias.Signature." + getSigAlgOID())) != null) {
            return property;
        }
        java.security.Provider[] providers = java.security.Security.getProviders();
        for (int i = 0; i != providers.length; i++) {
            java.lang.String property2 = providers[i].getProperty("Alg.Alias.Signature." + getSigAlgOID());
            if (property2 != null) {
                return property2;
            }
        }
        return getSigAlgOID();
    }

    @Override // java.security.cert.X509Certificate
    public java.lang.String getSigAlgOID() {
        return this.c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        if (this.c.getSignatureAlgorithm().getParameters() == null) {
            return null;
        }
        try {
            return this.c.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            return null;
        }
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
        return this.keyUsage;
    }

    @Override // java.security.cert.X509Certificate
    public java.util.List getExtendedKeyUsage() throws java.security.cert.CertificateParsingException {
        byte[] extensionBytes = getExtensionBytes("2.5.29.37");
        if (extensionBytes != null) {
            try {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(extensionBytes).readObject();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i != aSN1Sequence.size(); i++) {
                    arrayList.add(((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(i)).getId());
                }
                return java.util.Collections.unmodifiableList(arrayList);
            } catch (java.lang.Exception e) {
                throw new java.security.cert.CertificateParsingException("error processing extended key usage extension");
            }
        }
        return null;
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
        return getAlternativeNames(getExtensionBytes(com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectAlternativeName.getId()));
    }

    @Override // java.security.cert.X509Certificate
    public java.util.Collection getIssuerAlternativeNames() throws java.security.cert.CertificateParsingException {
        return getAlternativeNames(getExtensionBytes(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuerAlternativeName.getId()));
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

    private byte[] getExtensionBytes(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertificate().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            return extension.getExtnValue().getOctets();
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getTBSCertificate().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str))) != null) {
            try {
                return extension.getExtnValue().getEncoded();
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
                java.lang.String id = aSN1ObjectIdentifier.getId();
                if (!id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.KEY_USAGE) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_MAPPINGS) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.INHIBIT_ANY_POLICY) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.DELTA_CRL_INDICATOR) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.POLICY_CONSTRAINTS) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.BASIC_CONSTRAINTS) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME) && !id.equals(com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.NAME_CONSTRAINTS) && extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
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
            if (this.encoded == null) {
                this.encoded = this.c.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            }
            return this.encoded;
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.Certificate
    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof java.security.cert.Certificate)) {
            return false;
        }
        try {
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual(getEncoded(), ((java.security.cert.Certificate) obj).getEncoded());
        } catch (java.security.cert.CertificateEncodingException e) {
            return false;
        }
    }

    @Override // java.security.cert.Certificate
    public synchronized int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = calculateHashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    private int calculateHashCode() {
        try {
            byte[] encoded = getEncoded();
            int i = 0;
            for (int i2 = 1; i2 < encoded.length; i2++) {
                i += encoded[i2] * i2;
            }
            return i;
        } catch (java.security.cert.CertificateEncodingException e) {
            return 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public java.util.Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
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
        byte[] signature = getSignature();
        stringBuffer.append("            Signature: ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, 0, 20))).append(lineSeparator);
        for (int i = 20; i < signature.length; i += 20) {
            if (i < signature.length - 20) {
                stringBuffer.append("                       ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, i, 20))).append(lineSeparator);
            } else {
                stringBuffer.append("                       ").append(new java.lang.String(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(signature, i, signature.length - i))).append(lineSeparator);
            }
        }
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
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.NetscapeCertType((com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.netscapeRevocationURL)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.NetscapeRevocationURL((com.android.internal.org.bouncycastle.asn1.DERIA5String) aSN1InputStream.readObject())).append(lineSeparator);
                        } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.verisignCzagExtension)) {
                            stringBuffer.append(new com.android.internal.org.bouncycastle.asn1.misc.VerisignCzagExtension((com.android.internal.org.bouncycastle.asn1.DERIA5String) aSN1InputStream.readObject())).append(lineSeparator);
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
        java.security.Signature signature;
        java.lang.String signatureName = com.android.internal.org.bouncycastle.jce.provider.X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        try {
            signature = java.security.Signature.getInstance(signatureName, com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME);
        } catch (java.lang.Exception e) {
            signature = java.security.Signature.getInstance(signatureName);
        }
        checkSignature(publicKey, signature);
    }

    @Override // java.security.cert.Certificate
    public final void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException {
        java.security.Signature signature;
        java.lang.String signatureName = com.android.internal.org.bouncycastle.jce.provider.X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        if (str != null) {
            signature = java.security.Signature.getInstance(signatureName, str);
        } else {
            signature = java.security.Signature.getInstance(signatureName);
        }
        checkSignature(publicKey, signature);
    }

    @Override // java.security.cert.X509Certificate, java.security.cert.Certificate
    public final void verify(java.security.PublicKey publicKey, java.security.Provider provider) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException {
        java.security.Signature signature;
        java.lang.String signatureName = com.android.internal.org.bouncycastle.jce.provider.X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
        if (provider != null) {
            signature = java.security.Signature.getInstance(signatureName, provider);
        } else {
            signature = java.security.Signature.getInstance(signatureName);
        }
        checkSignature(publicKey, signature);
    }

    private void checkSignature(java.security.PublicKey publicKey, java.security.Signature signature) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException {
        if (!isAlgIdEqual(this.c.getSignatureAlgorithm(), this.c.getTBSCertificate().getSignature())) {
            throw new java.security.cert.CertificateException("signature algorithm in TBS cert not same as outer cert");
        }
        com.android.internal.org.bouncycastle.jce.provider.X509SignatureUtil.setSignatureParameters(signature, this.c.getSignatureAlgorithm().getParameters());
        signature.initVerify(publicKey);
        signature.update(getTBSCertificate());
        if (!signature.verify(getSignature())) {
            throw new java.security.SignatureException("certificate does not verify with supplied key");
        }
    }

    private boolean isAlgIdEqual(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        if (algorithmIdentifier.getParameters() == null) {
            return algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        if (algorithmIdentifier2.getParameters() == null) {
            return algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        }
        return algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
    }

    private static java.util.Collection getAlternativeNames(byte[] bArr) throws java.security.cert.CertificateParsingException {
        if (bArr == null) {
            return null;
        }
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(bArr).getObjects();
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
                        arrayList2.add(com.android.internal.org.bouncycastle.asn1.x509.X509Name.getInstance(generalName.getName()).toString(true, com.android.internal.org.bouncycastle.asn1.x509.X509Name.DefaultSymbols));
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
}
