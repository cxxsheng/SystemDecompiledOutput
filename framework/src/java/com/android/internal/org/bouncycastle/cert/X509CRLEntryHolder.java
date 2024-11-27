package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
public class X509CRLEntryHolder {
    private com.android.internal.org.bouncycastle.asn1.x509.GeneralNames ca;
    private com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry entry;

    X509CRLEntryHolder(com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry cRLEntry, boolean z, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension;
        this.entry = cRLEntry;
        this.ca = generalNames;
        if (z && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.certificateIssuer)) != null) {
            this.ca = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(extension.getParsedValue());
        }
    }

    public java.math.BigInteger getSerialNumber() {
        return this.entry.getUserCertificate().getValue();
    }

    public java.util.Date getRevocationDate() {
        return this.entry.getRevocationDate().getDate();
    }

    public boolean hasExtensions() {
        return this.entry.hasExtensions();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getCertificateIssuer() {
        return this.ca;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions = this.entry.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.entry.getExtensions();
    }

    public java.util.List getExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getExtensionOIDs(this.entry.getExtensions());
    }

    public java.util.Set getCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getCriticalExtensionOIDs(this.entry.getExtensions());
    }

    public java.util.Set getNonCriticalExtensionOIDs() {
        return com.android.internal.org.bouncycastle.cert.CertUtils.getNonCriticalExtensionOIDs(this.entry.getExtensions());
    }
}
