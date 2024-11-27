package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerId implements com.android.internal.org.bouncycastle.util.Selector {
    private com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector baseSelector;

    private SignerId(com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector x509CertificateHolderSelector) {
        this.baseSelector = x509CertificateHolderSelector;
    }

    public SignerId(byte[] bArr) {
        this(null, null, bArr);
    }

    public SignerId(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger) {
        this(x500Name, bigInteger, null);
    }

    public SignerId(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger, byte[] bArr) {
        this(new com.android.internal.org.bouncycastle.cert.selector.X509CertificateHolderSelector(x500Name, bigInteger, bArr));
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.baseSelector.getIssuer();
    }

    public java.math.BigInteger getSerialNumber() {
        return this.baseSelector.getSerialNumber();
    }

    public byte[] getSubjectKeyIdentifier() {
        return this.baseSelector.getSubjectKeyIdentifier();
    }

    public int hashCode() {
        return this.baseSelector.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.cms.SignerId)) {
            return false;
        }
        return this.baseSelector.equals(((com.android.internal.org.bouncycastle.cms.SignerId) obj).baseSelector);
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public boolean match(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.cms.SignerInformation) {
            return ((com.android.internal.org.bouncycastle.cms.SignerInformation) obj).getSID().equals(this);
        }
        return this.baseSelector.match(obj);
    }

    @Override // com.android.internal.org.bouncycastle.util.Selector
    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.cms.SignerId(this.baseSelector);
    }
}
