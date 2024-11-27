package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class ExtendedPKIXBuilderParameters extends com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters {
    private java.util.Set excludedCerts;
    private int maxPathLength;

    public java.util.Set getExcludedCerts() {
        return java.util.Collections.unmodifiableSet(this.excludedCerts);
    }

    public void setExcludedCerts(java.util.Set set) {
        if (set == null) {
            java.util.Set set2 = java.util.Collections.EMPTY_SET;
        } else {
            this.excludedCerts = new java.util.HashSet(set);
        }
    }

    public ExtendedPKIXBuilderParameters(java.util.Set set, com.android.internal.org.bouncycastle.util.Selector selector) throws java.security.InvalidAlgorithmParameterException {
        super(set);
        this.maxPathLength = 5;
        this.excludedCerts = java.util.Collections.EMPTY_SET;
        setTargetConstraints(selector);
    }

    public void setMaxPathLength(int i) {
        if (i < -1) {
            throw new java.security.InvalidParameterException("The maximum path length parameter can not be less than -1.");
        }
        this.maxPathLength = i;
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    @Override // com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters
    protected void setParams(java.security.cert.PKIXParameters pKIXParameters) {
        super.setParams(pKIXParameters);
        if (pKIXParameters instanceof com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters) {
            com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters) pKIXParameters;
            this.maxPathLength = extendedPKIXBuilderParameters.maxPathLength;
            this.excludedCerts = new java.util.HashSet(extendedPKIXBuilderParameters.excludedCerts);
        }
        if (pKIXParameters instanceof java.security.cert.PKIXBuilderParameters) {
            this.maxPathLength = ((java.security.cert.PKIXBuilderParameters) pKIXParameters).getMaxPathLength();
        }
    }

    @Override // com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters, java.security.cert.PKIXParameters, java.security.cert.CertPathParameters
    public java.lang.Object clone() {
        try {
            com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = new com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters(getTrustAnchors(), getTargetConstraints());
            extendedPKIXBuilderParameters.setParams(this);
            return extendedPKIXBuilderParameters;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    public static com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters getInstance(java.security.cert.PKIXParameters pKIXParameters) {
        try {
            com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = new com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters(pKIXParameters.getTrustAnchors(), com.android.internal.org.bouncycastle.x509.X509CertStoreSelector.getInstance((java.security.cert.X509CertSelector) pKIXParameters.getTargetCertConstraints()));
            extendedPKIXBuilderParameters.setParams(pKIXParameters);
            return extendedPKIXBuilderParameters;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }
}
