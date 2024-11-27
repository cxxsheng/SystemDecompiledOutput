package com.android.internal.org.bouncycastle.cert.selector;

/* loaded from: classes4.dex */
public class X509CertificateHolderSelector implements com.android.internal.org.bouncycastle.util.Selector {
    private com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    private java.math.BigInteger serialNumber;
    private byte[] subjectKeyId;

    public X509CertificateHolderSelector(byte[] bArr) {
        this(null, null, bArr);
    }

    public X509CertificateHolderSelector(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger) {
        this(x500Name, bigInteger, null);
    }

    public X509CertificateHolderSelector(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger, byte[] bArr) {
        this.issuer = x500Name;
        this.serialNumber = bigInteger;
        this.subjectKeyId = bArr;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.issuer;
    }

    public java.math.BigInteger getSerialNumber() {
        return this.serialNumber;
    }

    public byte[] getSubjectKeyIdentifier() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.subjectKeyId);
    }

    public int hashCode() {
        int hashCode = com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.subjectKeyId);
        if (this.serialNumber != null) {
            hashCode ^= this.serialNumber.hashCode();
        }
        if (this.issuer != null) {
            return hashCode ^ this.issuer.hashCode();
        }
        return hashCode;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector)) {
            return false;
        }
        com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector x509CertificateHolderSelector = (com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector) obj;
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.subjectKeyId, x509CertificateHolderSelector.subjectKeyId) && equalsObj(this.serialNumber, x509CertificateHolderSelector.serialNumber) && equalsObj(this.issuer, x509CertificateHolderSelector.issuer);
    }

    private boolean equalsObj(java.lang.Object obj, java.lang.Object obj2) {
        return obj != null ? obj.equals(obj2) : obj2 == null;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.cert.X509CertificateHolder) {
            com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder = (com.android.internal.org.bouncycastle.cert.X509CertificateHolder) obj;
            if (getSerialNumber() != null) {
                com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber issuerAndSerialNumber = new com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber(x509CertificateHolder.toASN1Structure());
                return issuerAndSerialNumber.getName().equals(this.issuer) && issuerAndSerialNumber.getSerialNumber().hasValue(this.serialNumber);
            }
            if (this.subjectKeyId != null) {
                com.android.internal.org.bouncycastle.asn1.x509.Extension extension = x509CertificateHolder.getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectKeyIdentifier);
                if (extension == null) {
                    return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.subjectKeyId, com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.calculateKeyId(x509CertificateHolder.getSubjectPublicKeyInfo()));
                }
                return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.subjectKeyId, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(extension.getParsedValue()).getOctets());
            }
        } else if (obj instanceof byte[]) {
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.subjectKeyId, (byte[]) obj);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector(this.issuer, this.serialNumber, this.subjectKeyId);
    }
}
