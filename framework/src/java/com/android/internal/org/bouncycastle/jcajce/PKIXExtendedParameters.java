package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKIXExtendedParameters implements java.security.cert.CertPathParameters {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    public static final int PKIX_VALIDITY_MODEL = 0;
    private final java.security.cert.PKIXParameters baseParameters;
    private final java.util.Date date;
    private final java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> extraCRLStores;
    private final java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> extraCertStores;
    private final java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> namedCRLStoreMap;
    private final java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> namedCertificateStoreMap;
    private final boolean revocationEnabled;
    private final com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector targetConstraints;
    private final java.util.Set<java.security.cert.TrustAnchor> trustAnchors;
    private final boolean useDeltas;
    private final java.util.Date validityDate;
    private final int validityModel;

    public static class Builder {
        private final java.security.cert.PKIXParameters baseParameters;
        private final java.util.Date date;
        private java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> extraCRLStores;
        private java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> extraCertStores;
        private java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> namedCRLStoreMap;
        private java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> namedCertificateStoreMap;
        private boolean revocationEnabled;
        private com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector targetConstraints;
        private java.util.Set<java.security.cert.TrustAnchor> trustAnchors;
        private boolean useDeltas;
        private final java.util.Date validityDate;
        private int validityModel;

        public Builder(java.security.cert.PKIXParameters pKIXParameters) {
            this.extraCertStores = new java.util.ArrayList();
            this.namedCertificateStoreMap = new java.util.HashMap();
            this.extraCRLStores = new java.util.ArrayList();
            this.namedCRLStoreMap = new java.util.HashMap();
            this.validityModel = 0;
            this.useDeltas = false;
            this.baseParameters = (java.security.cert.PKIXParameters) pKIXParameters.clone();
            java.security.cert.CertSelector targetCertConstraints = pKIXParameters.getTargetCertConstraints();
            if (targetCertConstraints != null) {
                this.targetConstraints = new com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector.Builder(targetCertConstraints).build();
            }
            this.validityDate = pKIXParameters.getDate();
            this.date = this.validityDate == null ? new java.util.Date() : this.validityDate;
            this.revocationEnabled = pKIXParameters.isRevocationEnabled();
            this.trustAnchors = pKIXParameters.getTrustAnchors();
        }

        public Builder(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters pKIXExtendedParameters) {
            this.extraCertStores = new java.util.ArrayList();
            this.namedCertificateStoreMap = new java.util.HashMap();
            this.extraCRLStores = new java.util.ArrayList();
            this.namedCRLStoreMap = new java.util.HashMap();
            this.validityModel = 0;
            this.useDeltas = false;
            this.baseParameters = pKIXExtendedParameters.baseParameters;
            this.validityDate = pKIXExtendedParameters.validityDate;
            this.date = pKIXExtendedParameters.date;
            this.targetConstraints = pKIXExtendedParameters.targetConstraints;
            this.extraCertStores = new java.util.ArrayList(pKIXExtendedParameters.extraCertStores);
            this.namedCertificateStoreMap = new java.util.HashMap(pKIXExtendedParameters.namedCertificateStoreMap);
            this.extraCRLStores = new java.util.ArrayList(pKIXExtendedParameters.extraCRLStores);
            this.namedCRLStoreMap = new java.util.HashMap(pKIXExtendedParameters.namedCRLStoreMap);
            this.useDeltas = pKIXExtendedParameters.useDeltas;
            this.validityModel = pKIXExtendedParameters.validityModel;
            this.revocationEnabled = pKIXExtendedParameters.isRevocationEnabled();
            this.trustAnchors = pKIXExtendedParameters.getTrustAnchors();
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder addCertificateStore(com.android.internal.org.bouncycastle.jcajce.PKIXCertStore pKIXCertStore) {
            this.extraCertStores.add(pKIXCertStore);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder addNamedCertificateStore(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName, com.android.internal.org.bouncycastle.jcajce.PKIXCertStore pKIXCertStore) {
            this.namedCertificateStoreMap.put(generalName, pKIXCertStore);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder addCRLStore(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore pKIXCRLStore) {
            this.extraCRLStores.add(pKIXCRLStore);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder addNamedCRLStore(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore pKIXCRLStore) {
            this.namedCRLStoreMap.put(generalName, pKIXCRLStore);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder setTargetConstraints(com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector pKIXCertStoreSelector) {
            this.targetConstraints = pKIXCertStoreSelector;
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder setUseDeltasEnabled(boolean z) {
            this.useDeltas = z;
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder setValidityModel(int i) {
            this.validityModel = i;
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder setTrustAnchor(java.security.cert.TrustAnchor trustAnchor) {
            this.trustAnchors = java.util.Collections.singleton(trustAnchor);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder setTrustAnchors(java.util.Set<java.security.cert.TrustAnchor> set) {
            this.trustAnchors = set;
            return this;
        }

        public void setRevocationEnabled(boolean z) {
            this.revocationEnabled = z;
        }

        public com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters build() {
            return new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters(this);
        }
    }

    private PKIXExtendedParameters(com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder builder) {
        this.baseParameters = builder.baseParameters;
        this.validityDate = builder.validityDate;
        this.date = builder.date;
        this.extraCertStores = java.util.Collections.unmodifiableList(builder.extraCertStores);
        this.namedCertificateStoreMap = java.util.Collections.unmodifiableMap(new java.util.HashMap(builder.namedCertificateStoreMap));
        this.extraCRLStores = java.util.Collections.unmodifiableList(builder.extraCRLStores);
        this.namedCRLStoreMap = java.util.Collections.unmodifiableMap(new java.util.HashMap(builder.namedCRLStoreMap));
        this.targetConstraints = builder.targetConstraints;
        this.revocationEnabled = builder.revocationEnabled;
        this.useDeltas = builder.useDeltas;
        this.validityModel = builder.validityModel;
        this.trustAnchors = java.util.Collections.unmodifiableSet(builder.trustAnchors);
    }

    public java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> getCertificateStores() {
        return this.extraCertStores;
    }

    public java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCertStore> getNamedCertificateStoreMap() {
        return this.namedCertificateStoreMap;
    }

    public java.util.List<com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> getCRLStores() {
        return this.extraCRLStores;
    }

    public java.util.Map<com.android.internal.org.bouncycastle.asn1.x509.GeneralName, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStore> getNamedCRLStoreMap() {
        return this.namedCRLStoreMap;
    }

    public java.util.Date getValidityDate() {
        if (this.validityDate == null) {
            return null;
        }
        return new java.util.Date(this.validityDate.getTime());
    }

    public java.util.Date getDate() {
        return new java.util.Date(this.date.getTime());
    }

    public boolean isUseDeltasEnabled() {
        return this.useDeltas;
    }

    public int getValidityModel() {
        return this.validityModel;
    }

    @Override // java.security.cert.CertPathParameters
    public java.lang.Object clone() {
        return this;
    }

    public com.android.internal.org.bouncycastle.jcajce.PKIXCertStoreSelector getTargetConstraints() {
        return this.targetConstraints;
    }

    public java.util.Set getTrustAnchors() {
        return this.trustAnchors;
    }

    public java.util.Set getInitialPolicies() {
        return this.baseParameters.getInitialPolicies();
    }

    public java.lang.String getSigProvider() {
        return this.baseParameters.getSigProvider();
    }

    public boolean isExplicitPolicyRequired() {
        return this.baseParameters.isExplicitPolicyRequired();
    }

    public boolean isAnyPolicyInhibited() {
        return this.baseParameters.isAnyPolicyInhibited();
    }

    public boolean isPolicyMappingInhibited() {
        return this.baseParameters.isPolicyMappingInhibited();
    }

    public java.util.List getCertPathCheckers() {
        return this.baseParameters.getCertPathCheckers();
    }

    public java.util.List<java.security.cert.CertStore> getCertStores() {
        return this.baseParameters.getCertStores();
    }

    public boolean isRevocationEnabled() {
        return this.revocationEnabled;
    }

    public boolean getPolicyQualifiersRejected() {
        return this.baseParameters.getPolicyQualifiersRejected();
    }
}
