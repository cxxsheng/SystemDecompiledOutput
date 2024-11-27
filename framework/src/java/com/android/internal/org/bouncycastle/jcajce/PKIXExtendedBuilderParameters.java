package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKIXExtendedBuilderParameters implements java.security.cert.CertPathParameters {
    private final com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters baseParameters;
    private final java.util.Set<java.security.cert.X509Certificate> excludedCerts;
    private final int maxPathLength;

    public static class Builder {
        private final com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters baseParameters;
        private java.util.Set<java.security.cert.X509Certificate> excludedCerts;
        private int maxPathLength;

        public Builder(java.security.cert.PKIXBuilderParameters pKIXBuilderParameters) {
            this.maxPathLength = 5;
            this.excludedCerts = new java.util.HashSet();
            this.baseParameters = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder(pKIXBuilderParameters).build();
            this.maxPathLength = pKIXBuilderParameters.getMaxPathLength();
        }

        public Builder(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters) {
            this.maxPathLength = 5;
            this.excludedCerts = new java.util.HashSet();
            this.baseParameters = pKIXExtendedParameters;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder addExcludedCerts(java.util.Set<java.security.cert.X509Certificate> set) {
            this.excludedCerts.addAll(set);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder setMaxPathLength(int i) {
            if (i < -1) {
                throw new java.security.InvalidParameterException("The maximum path length parameter can not be less than -1.");
            }
            this.maxPathLength = i;
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters build() {
            return new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters(this);
        }
    }

    private PKIXExtendedBuilderParameters(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder builder) {
        this.baseParameters = builder.baseParameters;
        this.excludedCerts = java.util.Collections.unmodifiableSet(builder.excludedCerts);
        this.maxPathLength = builder.maxPathLength;
    }

    public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters getBaseParameters() {
        return this.baseParameters;
    }

    public java.util.Set getExcludedCerts() {
        return this.excludedCerts;
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    @Override // java.security.cert.CertPathParameters
    public java.lang.Object clone() {
        return this;
    }
}
