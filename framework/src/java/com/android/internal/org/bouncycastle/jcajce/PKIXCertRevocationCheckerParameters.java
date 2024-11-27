package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKIXCertRevocationCheckerParameters {
    private final java.security.cert.CertPath certPath;
    private final int index;
    private final com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters paramsPKIX;
    private final java.security.cert.X509Certificate signingCert;
    private final java.util.Date validDate;
    private final java.security.PublicKey workingPublicKey;

    public PKIXCertRevocationCheckerParameters(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters, java.util.Date date, java.security.cert.CertPath certPath, int i, java.security.cert.X509Certificate x509Certificate, java.security.PublicKey publicKey) {
        this.paramsPKIX = pKIXExtendedParameters;
        this.validDate = date;
        this.certPath = certPath;
        this.index = i;
        this.signingCert = x509Certificate;
        this.workingPublicKey = publicKey;
    }

    public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters getParamsPKIX() {
        return this.paramsPKIX;
    }

    public java.util.Date getValidDate() {
        return new java.util.Date(this.validDate.getTime());
    }

    public java.security.cert.CertPath getCertPath() {
        return this.certPath;
    }

    public int getIndex() {
        return this.index;
    }

    public java.security.cert.X509Certificate getSigningCert() {
        return this.signingCert;
    }

    public java.security.PublicKey getWorkingPublicKey() {
        return this.workingPublicKey;
    }
}
