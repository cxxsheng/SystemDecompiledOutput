package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
class X509CRLEntryObject extends java.security.cert.X509CRLEntry {
    private com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry c;
    private com.android.internal.org.bouncycastle.asn1.x500.X500Name certificateIssuer;
    private volatile int hashValue;
    private volatile boolean hashValueSet;

    protected X509CRLEntryObject(com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry) {
        this.c = cRLEntry;
        this.certificateIssuer = null;
    }

    protected X509CRLEntryObject(com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry, boolean z, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.c = cRLEntry;
        this.certificateIssuer = loadCertificateIssuer(z, x500Name);
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        java.util.Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    private com.android.internal.org.bouncycastle.asn1.x500.X500Name loadCertificateIssuer(boolean z, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        if (!z) {
            return null;
        }
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension = getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer);
        if (extension == null) {
            return x500Name;
        }
        try {
            com.android.internal.org.bouncycastle.asn1.x509.GeneralName[] names = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue()).getNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    return com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(names[i].getName());
                }
            }
            return null;
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    @Override // java.security.cert.X509CRLEntry
    public javax.security.auth.x500.X500Principal getCertificateIssuer() {
        if (this.certificateIssuer == null) {
            return null;
        }
        try {
            return new javax.security.auth.x500.X500Principal(this.certificateIssuer.getEncoded());
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private java.util.Set getExtensionOIDs(boolean z) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getExtensions();
        if (extensions != null) {
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

    private com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension = getExtension(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
        if (extension != null) {
            try {
                return extension.getExtnValue().getEncoded();
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalStateException("Exception encoding: " + e.toString());
            }
        }
        return null;
    }

    @Override // java.security.cert.X509CRLEntry
    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = super.hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    @Override // java.security.cert.X509CRLEntry
    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLEntryObject) {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLEntryObject x509CRLEntryObject = (com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLEntryObject) obj;
            if (this.hashValueSet && x509CRLEntryObject.hashValueSet && this.hashValue != x509CRLEntryObject.hashValue) {
                return false;
            }
            return this.c.equals(x509CRLEntryObject.c);
        }
        return super.equals(this);
    }

    @Override // java.security.cert.X509CRLEntry
    public byte[] getEncoded() throws java.security.cert.CRLException {
        try {
            return this.c.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CRLException(e.toString());
        }
    }

    @Override // java.security.cert.X509CRLEntry
    public java.math.BigInteger getSerialNumber() {
        return this.c.getUserCertificate().getValue();
    }

    @Override // java.security.cert.X509CRLEntry
    public java.util.Date getRevocationDate() {
        return this.c.getRevocationDate().getDate();
    }

    @Override // java.security.cert.X509CRLEntry
    public boolean hasExtensions() {
        return this.c.getExtensions() != null;
    }

    @Override // java.security.cert.X509CRLEntry
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("      userCertificate: ").append(getSerialNumber()).append(lineSeparator);
        stringBuffer.append("       revocationDate: ").append(getRevocationDate()).append(lineSeparator);
        stringBuffer.append("       certificateIssuer: ").append(getCertificateIssuer()).append(lineSeparator);
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.c.getExtensions();
        if (extensions != null) {
            java.util.Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                stringBuffer.append("   crlEntryExtensions:").append(lineSeparator);
                while (oids.hasMoreElements()) {
                    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
                    com.android.internal.org.bouncycastle.asn1.x509.Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                    if (extension.getExtnValue() != null) {
                        com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(extension.getExtnValue().getOctets());
                        stringBuffer.append("                       critical(").append(extension.isCritical()).append(") ");
                        try {
                            if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.reasonCode)) {
                                stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x509.CRLReason.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(aSN1InputStream.readObject()))).append(lineSeparator);
                            } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) {
                                stringBuffer.append("Certificate issuer: ").append(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1InputStream.readObject())).append(lineSeparator);
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
        }
        return stringBuffer.toString();
    }
}
