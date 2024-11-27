package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class ExtendedPKIXParameters extends java.security.cert.PKIXParameters {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    public static final int PKIX_VALIDITY_MODEL = 0;
    private boolean additionalLocationsEnabled;
    private java.util.List additionalStores;
    private java.util.Set attrCertCheckers;
    private java.util.Set necessaryACAttributes;
    private java.util.Set prohibitedACAttributes;
    private com.android.internal.org.bouncycastle.util.Selector selector;
    private java.util.List stores;
    private java.util.Set trustedACIssuers;
    private boolean useDeltas;
    private int validityModel;

    public ExtendedPKIXParameters(java.util.Set set) throws java.security.InvalidAlgorithmParameterException {
        super((java.util.Set<java.security.cert.TrustAnchor>) set);
        this.validityModel = 0;
        this.useDeltas = false;
        this.stores = new java.util.ArrayList();
        this.additionalStores = new java.util.ArrayList();
        this.trustedACIssuers = new java.util.HashSet();
        this.necessaryACAttributes = new java.util.HashSet();
        this.prohibitedACAttributes = new java.util.HashSet();
        this.attrCertCheckers = new java.util.HashSet();
    }

    public static com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters getInstance(java.security.cert.PKIXParameters pKIXParameters) {
        try {
            com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters extendedPKIXParameters = new com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters(pKIXParameters.getTrustAnchors());
            extendedPKIXParameters.setParams(pKIXParameters);
            return extendedPKIXParameters;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    protected void setParams(java.security.cert.PKIXParameters pKIXParameters) {
        setDate(pKIXParameters.getDate());
        setCertPathCheckers(pKIXParameters.getCertPathCheckers());
        setCertStores(pKIXParameters.getCertStores());
        setAnyPolicyInhibited(pKIXParameters.isAnyPolicyInhibited());
        setExplicitPolicyRequired(pKIXParameters.isExplicitPolicyRequired());
        setPolicyMappingInhibited(pKIXParameters.isPolicyMappingInhibited());
        setRevocationEnabled(pKIXParameters.isRevocationEnabled());
        setInitialPolicies(pKIXParameters.getInitialPolicies());
        setPolicyQualifiersRejected(pKIXParameters.getPolicyQualifiersRejected());
        setSigProvider(pKIXParameters.getSigProvider());
        setTargetCertConstraints(pKIXParameters.getTargetCertConstraints());
        try {
            setTrustAnchors(pKIXParameters.getTrustAnchors());
            if (pKIXParameters instanceof com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters) {
                com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters extendedPKIXParameters = (com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters) pKIXParameters;
                this.validityModel = extendedPKIXParameters.validityModel;
                this.useDeltas = extendedPKIXParameters.useDeltas;
                this.additionalLocationsEnabled = extendedPKIXParameters.additionalLocationsEnabled;
                this.selector = extendedPKIXParameters.selector == null ? null : (com.android.internal.org.bouncycastle.util.Selector) extendedPKIXParameters.selector.clone();
                this.stores = new java.util.ArrayList(extendedPKIXParameters.stores);
                this.additionalStores = new java.util.ArrayList(extendedPKIXParameters.additionalStores);
                this.trustedACIssuers = new java.util.HashSet(extendedPKIXParameters.trustedACIssuers);
                this.prohibitedACAttributes = new java.util.HashSet(extendedPKIXParameters.prohibitedACAttributes);
                this.necessaryACAttributes = new java.util.HashSet(extendedPKIXParameters.necessaryACAttributes);
                this.attrCertCheckers = new java.util.HashSet(extendedPKIXParameters.attrCertCheckers);
            }
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    public boolean isUseDeltasEnabled() {
        return this.useDeltas;
    }

    public void setUseDeltasEnabled(boolean z) {
        this.useDeltas = z;
    }

    public int getValidityModel() {
        return this.validityModel;
    }

    @Override // java.security.cert.PKIXParameters
    public void setCertStores(java.util.List list) {
        if (list != null) {
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                addCertStore((java.security.cert.CertStore) it.next());
            }
        }
    }

    public void setStores(java.util.List list) {
        if (list == null) {
            this.stores = new java.util.ArrayList();
            return;
        }
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof com.android.internal.org.bouncycastle.util.Store)) {
                throw new java.lang.ClassCastException("All elements of list must be of type com.android.internal.org.bouncycastle.util.Store.");
            }
        }
        this.stores = new java.util.ArrayList(list);
    }

    public void addStore(com.android.internal.org.bouncycastle.util.Store store) {
        if (store != null) {
            this.stores.add(store);
        }
    }

    public void addAdditionalStore(com.android.internal.org.bouncycastle.util.Store store) {
        if (store != null) {
            this.additionalStores.add(store);
        }
    }

    public void addAddionalStore(com.android.internal.org.bouncycastle.util.Store store) {
        addAdditionalStore(store);
    }

    public java.util.List getAdditionalStores() {
        return java.util.Collections.unmodifiableList(this.additionalStores);
    }

    public java.util.List getStores() {
        return java.util.Collections.unmodifiableList(new java.util.ArrayList(this.stores));
    }

    public void setValidityModel(int i) {
        this.validityModel = i;
    }

    @Override // java.security.cert.PKIXParameters, java.security.cert.CertPathParameters
    public java.lang.Object clone() {
        try {
            com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters extendedPKIXParameters = new com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters(getTrustAnchors());
            extendedPKIXParameters.setParams(this);
            return extendedPKIXParameters;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    public boolean isAdditionalLocationsEnabled() {
        return this.additionalLocationsEnabled;
    }

    public void setAdditionalLocationsEnabled(boolean z) {
        this.additionalLocationsEnabled = z;
    }

    public com.android.internal.org.bouncycastle.util.Selector getTargetConstraints() {
        if (this.selector != null) {
            return (com.android.internal.org.bouncycastle.util.Selector) this.selector.clone();
        }
        return null;
    }

    public void setTargetConstraints(com.android.internal.org.bouncycastle.util.Selector selector) {
        if (selector != null) {
            this.selector = (com.android.internal.org.bouncycastle.util.Selector) selector.clone();
        } else {
            this.selector = null;
        }
    }

    @Override // java.security.cert.PKIXParameters
    public void setTargetCertConstraints(java.security.cert.CertSelector certSelector) {
        super.setTargetCertConstraints(certSelector);
        if (certSelector != null) {
            this.selector = com.android.internal.org.bouncycastle.x509.X509CertStoreSelector.getInstance((java.security.cert.X509CertSelector) certSelector);
        } else {
            this.selector = null;
        }
    }

    public java.util.Set getTrustedACIssuers() {
        return java.util.Collections.unmodifiableSet(this.trustedACIssuers);
    }

    public void setTrustedACIssuers(java.util.Set set) {
        if (set == null) {
            this.trustedACIssuers.clear();
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof java.security.cert.TrustAnchor)) {
                throw new java.lang.ClassCastException("All elements of set must be of type " + java.security.cert.TrustAnchor.class.getName() + android.media.MediaMetrics.SEPARATOR);
            }
        }
        this.trustedACIssuers.clear();
        this.trustedACIssuers.addAll(set);
    }

    public java.util.Set getNecessaryACAttributes() {
        return java.util.Collections.unmodifiableSet(this.necessaryACAttributes);
    }

    public void setNecessaryACAttributes(java.util.Set set) {
        if (set == null) {
            this.necessaryACAttributes.clear();
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof java.lang.String)) {
                throw new java.lang.ClassCastException("All elements of set must be of type String.");
            }
        }
        this.necessaryACAttributes.clear();
        this.necessaryACAttributes.addAll(set);
    }

    public java.util.Set getProhibitedACAttributes() {
        return java.util.Collections.unmodifiableSet(this.prohibitedACAttributes);
    }

    public void setProhibitedACAttributes(java.util.Set set) {
        if (set == null) {
            this.prohibitedACAttributes.clear();
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof java.lang.String)) {
                throw new java.lang.ClassCastException("All elements of set must be of type String.");
            }
        }
        this.prohibitedACAttributes.clear();
        this.prohibitedACAttributes.addAll(set);
    }

    public java.util.Set getAttrCertCheckers() {
        return java.util.Collections.unmodifiableSet(this.attrCertCheckers);
    }

    public void setAttrCertCheckers(java.util.Set set) {
        if (set == null) {
            this.attrCertCheckers.clear();
            return;
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof com.android.internal.org.bouncycastle.x509.PKIXAttrCertChecker)) {
                throw new java.lang.ClassCastException("All elements of set must be of type " + com.android.internal.org.bouncycastle.x509.PKIXAttrCertChecker.class.getName() + android.media.MediaMetrics.SEPARATOR);
            }
        }
        this.attrCertCheckers.clear();
        this.attrCertCheckers.addAll(set);
    }
}
