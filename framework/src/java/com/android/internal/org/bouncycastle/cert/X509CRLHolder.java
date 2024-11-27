package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class X509CRLHolder implements com.android.internal.org.bouncycastle.util.Encodable, java.io.Serializable {
    private static final long serialVersionUID = 20170722001L;
    private transient com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
    private transient boolean isIndirect;
    private transient com.android.internal.org.bouncycastle.asn1.x509.GeneralNames issuerName;
    private transient com.android.internal.org.bouncycastle.asn1.x509.CertificateList x509CRL;

    private static com.android.internal.org.bouncycastle.asn1.x509.CertificateList parseStream(java.io.InputStream inputStream) throws java.io.IOException {
        try {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream, true).readObject();
            if (readObject == null) {
                throw new java.io.IOException("no content found");
            }
            return com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(readObject);
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e.getMessage(), e);
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("malformed data: " + e2.getMessage(), e2);
        }
    }

    private static boolean isIndirectCRL(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        return (extensions == null || (extension = extensions.getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuingDistributionPoint)) == null || !com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(extension.getParsedValue()).isIndirectCRL()) ? false : true;
    }

    public X509CRLHolder(byte[] bArr) throws java.io.IOException {
        this(parseStream(new java.io.ByteArrayInputStream(bArr)));
    }

    public X509CRLHolder(java.io.InputStream inputStream) throws java.io.IOException {
        this(parseStream(inputStream));
    }

    public X509CRLHolder(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) {
        init(certificateList);
    }

    private void init(com.android.internal.org.bouncycastle.asn1.x509.CertificateList certificateList) {
        this.x509CRL = certificateList;
        this.extensions = certificateList.getTBSCertList().getExtensions();
        this.isIndirect = isIndirectCRL(this.extensions);
        this.issuerName = new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(certificateList.getIssuer()));
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws java.io.IOException {
        return this.x509CRL.getEncoded();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.x509CRL.getIssuer());
    }

    public java.util.Date getThisUpdate() {
        return this.x509CRL.getThisUpdate().getDate();
    }

    public java.util.Date getNextUpdate() {
        com.android.internal.org.bouncycastle.asn1.x509.Time nextUpdate = this.x509CRL.getNextUpdate();
        if (nextUpdate != null) {
            return nextUpdate.getDate();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.cert.X509CRLEntryHolder getRevokedCertificate(java.math.BigInteger bigInteger) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames = this.issuerName;
        java.util.Enumeration revokedCertificateEnumeration = this.x509CRL.getRevokedCertificateEnumeration();
        while (revokedCertificateEnumeration.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry = (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            if (cRLEntry.getUserCertificate().hasValue(bigInteger)) {
                return new com.android.internal.org.bouncycastle.cert.X509CRLEntryHolder(cRLEntry, this.isIndirect, generalNames);
            }
            if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) != null) {
                generalNames = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue());
            }
        }
        return null;
    }

    public java.util.Collection getRevokedCertificates() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.x509CRL.getRevokedCertificates().length);
        com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames = this.issuerName;
        java.util.Enumeration revokedCertificateEnumeration = this.x509CRL.getRevokedCertificateEnumeration();
        while (revokedCertificateEnumeration.hasMoreElements()) {
            com.android.internal.org.bouncycastle.cert.X509CRLEntryHolder x509CRLEntryHolder = new com.android.internal.org.bouncycastle.cert.X509CRLEntryHolder((com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement(), this.isIndirect, generalNames);
            arrayList.add(x509CRLEntryHolder);
            generalNames = x509CRLEntryHolder.getCertificateIssuer();
        }
        return arrayList;
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (this.extensions != null) {
            return this.extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.extensions;
    }

    public java.util.List getExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getExtensionOIDs(this.extensions);
    }

    public java.util.Set getCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public java.util.Set getNonCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.CertificateList toASN1Structure() {
        return this.x509CRL;
    }

    public boolean isSignatureValid(com.android.internal.org.bouncycastle.operator.ContentVerifierProvider contentVerifierProvider) throws com.android.internal.org.bouncycastle.cert.CertException {
        com.android.internal.org.bouncycastle.asn1.x509.TBSCertList tBSCertList = this.x509CRL.getTBSCertList();
        if (!com.android.internal.org.bouncycastle.cert.CertUtils.isAlgIdEqual(tBSCertList.getSignature(), this.x509CRL.getSignatureAlgorithm())) {
            throw new com.android.internal.org.bouncycastle.cert.CertException("signature invalid - algorithm identifier mismatch");
        }
        try {
            com.android.internal.org.bouncycastle.operator.ContentVerifier contentVerifier = contentVerifierProvider.get(tBSCertList.getSignature());
            java.io.OutputStream outputStream = contentVerifier.getOutputStream();
            tBSCertList.encodeTo(outputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            outputStream.close();
            return contentVerifier.verify(this.x509CRL.getSignature().getOctets());
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.cert.CertException("unable to process signature: " + e.getMessage(), e);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.X509CRLHolder)) {
            return false;
        }
        return this.x509CRL.equals(((com.android.internal.org.bouncycastle.cert.X509CRLHolder) obj).x509CRL);
    }

    public int hashCode() {
        return this.x509CRL.hashCode();
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        init(com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(objectInputStream.readObject()));
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }
}
