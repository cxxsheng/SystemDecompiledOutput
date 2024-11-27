package android.security.net.config;

/* loaded from: classes3.dex */
public final class NetworkSecurityConfig {
    public static final boolean DEFAULT_CERTIFICATE_TRANSPARENCY_VERIFICATION_REQUIRED = false;
    public static final boolean DEFAULT_CLEARTEXT_TRAFFIC_PERMITTED = true;
    public static final boolean DEFAULT_HSTS_ENFORCED = false;
    private java.util.Set<android.security.net.config.TrustAnchor> mAnchors;
    private final java.lang.Object mAnchorsLock;
    private final boolean mCertificateTransparencyVerificationRequired;
    private final java.util.List<android.security.net.config.CertificatesEntryRef> mCertificatesEntryRefs;
    private final boolean mCleartextTrafficPermitted;
    private final boolean mHstsEnforced;
    private final android.security.net.config.PinSet mPins;
    private android.security.net.config.NetworkSecurityTrustManager mTrustManager;
    private final java.lang.Object mTrustManagerLock;

    private NetworkSecurityConfig(boolean z, boolean z2, boolean z3, android.security.net.config.PinSet pinSet, java.util.List<android.security.net.config.CertificatesEntryRef> list) {
        this.mAnchorsLock = new java.lang.Object();
        this.mTrustManagerLock = new java.lang.Object();
        this.mCleartextTrafficPermitted = z;
        this.mHstsEnforced = z2;
        this.mCertificateTransparencyVerificationRequired = z3;
        this.mPins = pinSet;
        this.mCertificatesEntryRefs = list;
        java.util.Collections.sort(this.mCertificatesEntryRefs, new java.util.Comparator<android.security.net.config.CertificatesEntryRef>() { // from class: android.security.net.config.NetworkSecurityConfig.1
            @Override // java.util.Comparator
            public int compare(android.security.net.config.CertificatesEntryRef certificatesEntryRef, android.security.net.config.CertificatesEntryRef certificatesEntryRef2) {
                return certificatesEntryRef.overridesPins() ? certificatesEntryRef2.overridesPins() ? 0 : -1 : certificatesEntryRef2.overridesPins() ? 1 : 0;
            }
        });
    }

    public java.util.Set<android.security.net.config.TrustAnchor> getTrustAnchors() {
        synchronized (this.mAnchorsLock) {
            if (this.mAnchors != null) {
                return this.mAnchors;
            }
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            java.util.Iterator<android.security.net.config.CertificatesEntryRef> it = this.mCertificatesEntryRefs.iterator();
            while (it.hasNext()) {
                for (android.security.net.config.TrustAnchor trustAnchor : it.next().getTrustAnchors()) {
                    java.security.cert.X509Certificate x509Certificate = trustAnchor.certificate;
                    if (!arrayMap.containsKey(x509Certificate)) {
                        arrayMap.put(x509Certificate, trustAnchor);
                    }
                }
            }
            android.util.ArraySet arraySet = new android.util.ArraySet(arrayMap.size());
            arraySet.addAll((java.util.Collection) arrayMap.values());
            this.mAnchors = arraySet;
            return this.mAnchors;
        }
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mCleartextTrafficPermitted;
    }

    public boolean isHstsEnforced() {
        return this.mHstsEnforced;
    }

    public boolean isCertificateTransparencyVerificationRequired() {
        return this.mCertificateTransparencyVerificationRequired;
    }

    public android.security.net.config.PinSet getPins() {
        return this.mPins;
    }

    public android.security.net.config.NetworkSecurityTrustManager getTrustManager() {
        android.security.net.config.NetworkSecurityTrustManager networkSecurityTrustManager;
        synchronized (this.mTrustManagerLock) {
            if (this.mTrustManager == null) {
                this.mTrustManager = new android.security.net.config.NetworkSecurityTrustManager(this);
            }
            networkSecurityTrustManager = this.mTrustManager;
        }
        return networkSecurityTrustManager;
    }

    public android.security.net.config.TrustAnchor findTrustAnchorBySubjectAndPublicKey(java.security.cert.X509Certificate x509Certificate) {
        java.util.Iterator<android.security.net.config.CertificatesEntryRef> it = this.mCertificatesEntryRefs.iterator();
        while (it.hasNext()) {
            android.security.net.config.TrustAnchor findBySubjectAndPublicKey = it.next().findBySubjectAndPublicKey(x509Certificate);
            if (findBySubjectAndPublicKey != null) {
                return findBySubjectAndPublicKey;
            }
        }
        return null;
    }

    public android.security.net.config.TrustAnchor findTrustAnchorByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        java.util.Iterator<android.security.net.config.CertificatesEntryRef> it = this.mCertificatesEntryRefs.iterator();
        while (it.hasNext()) {
            android.security.net.config.TrustAnchor findByIssuerAndSignature = it.next().findByIssuerAndSignature(x509Certificate);
            if (findByIssuerAndSignature != null) {
                return findByIssuerAndSignature;
            }
        }
        return null;
    }

    public java.util.Set<java.security.cert.X509Certificate> findAllCertificatesByIssuerAndSignature(java.security.cert.X509Certificate x509Certificate) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.security.net.config.CertificatesEntryRef> it = this.mCertificatesEntryRefs.iterator();
        while (it.hasNext()) {
            arraySet.addAll(it.next().findAllCertificatesByIssuerAndSignature(x509Certificate));
        }
        return arraySet;
    }

    public void handleTrustStorageUpdate() {
        synchronized (this.mAnchorsLock) {
            this.mAnchors = null;
            java.util.Iterator<android.security.net.config.CertificatesEntryRef> it = this.mCertificatesEntryRefs.iterator();
            while (it.hasNext()) {
                it.next().handleTrustStorageUpdate();
            }
        }
        getTrustManager().handleTrustStorageUpdate();
    }

    public static android.security.net.config.NetworkSecurityConfig.Builder getDefaultBuilder(android.content.pm.ApplicationInfo applicationInfo) {
        android.security.net.config.NetworkSecurityConfig.Builder addCertificatesEntryRef = new android.security.net.config.NetworkSecurityConfig.Builder().setHstsEnforced(false).addCertificatesEntryRef(new android.security.net.config.CertificatesEntryRef(android.security.net.config.SystemCertificateSource.getInstance(), false));
        addCertificatesEntryRef.setCleartextTrafficPermitted(applicationInfo.targetSdkVersion < 28 && !applicationInfo.isInstantApp());
        if (applicationInfo.targetSdkVersion <= 23 && !applicationInfo.isPrivilegedApp()) {
            addCertificatesEntryRef.addCertificatesEntryRef(new android.security.net.config.CertificatesEntryRef(android.security.net.config.UserCertificateSource.getInstance(), false));
        }
        return addCertificatesEntryRef;
    }

    public static final class Builder {
        private java.util.List<android.security.net.config.CertificatesEntryRef> mCertificatesEntryRefs;
        private android.security.net.config.NetworkSecurityConfig.Builder mParentBuilder;
        private android.security.net.config.PinSet mPinSet;
        private boolean mCleartextTrafficPermitted = true;
        private boolean mHstsEnforced = false;
        private boolean mCleartextTrafficPermittedSet = false;
        private boolean mHstsEnforcedSet = false;
        private boolean mCertificateTransparencyVerificationRequired = false;
        private boolean mCertificateTransparencyVerificationRequiredSet = false;

        public android.security.net.config.NetworkSecurityConfig.Builder setParent(android.security.net.config.NetworkSecurityConfig.Builder builder) {
            for (android.security.net.config.NetworkSecurityConfig.Builder builder2 = builder; builder2 != null; builder2 = builder2.getParent()) {
                if (builder2 == this) {
                    throw new java.lang.IllegalArgumentException("Loops are not allowed in Builder parents");
                }
            }
            this.mParentBuilder = builder;
            return this;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder getParent() {
            return this.mParentBuilder;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder setPinSet(android.security.net.config.PinSet pinSet) {
            this.mPinSet = pinSet;
            return this;
        }

        private android.security.net.config.PinSet getEffectivePinSet() {
            if (this.mPinSet != null) {
                return this.mPinSet;
            }
            if (this.mParentBuilder != null) {
                return this.mParentBuilder.getEffectivePinSet();
            }
            return android.security.net.config.PinSet.EMPTY_PINSET;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder setCleartextTrafficPermitted(boolean z) {
            this.mCleartextTrafficPermitted = z;
            this.mCleartextTrafficPermittedSet = true;
            return this;
        }

        private boolean getEffectiveCleartextTrafficPermitted() {
            if (this.mCleartextTrafficPermittedSet) {
                return this.mCleartextTrafficPermitted;
            }
            if (this.mParentBuilder != null) {
                return this.mParentBuilder.getEffectiveCleartextTrafficPermitted();
            }
            return true;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder setHstsEnforced(boolean z) {
            this.mHstsEnforced = z;
            this.mHstsEnforcedSet = true;
            return this;
        }

        private boolean getEffectiveHstsEnforced() {
            if (this.mHstsEnforcedSet) {
                return this.mHstsEnforced;
            }
            if (this.mParentBuilder != null) {
                return this.mParentBuilder.getEffectiveHstsEnforced();
            }
            return false;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder addCertificatesEntryRef(android.security.net.config.CertificatesEntryRef certificatesEntryRef) {
            if (this.mCertificatesEntryRefs == null) {
                this.mCertificatesEntryRefs = new java.util.ArrayList();
            }
            this.mCertificatesEntryRefs.add(certificatesEntryRef);
            return this;
        }

        public android.security.net.config.NetworkSecurityConfig.Builder addCertificatesEntryRefs(java.util.Collection<? extends android.security.net.config.CertificatesEntryRef> collection) {
            if (this.mCertificatesEntryRefs == null) {
                this.mCertificatesEntryRefs = new java.util.ArrayList();
            }
            this.mCertificatesEntryRefs.addAll(collection);
            return this;
        }

        private java.util.List<android.security.net.config.CertificatesEntryRef> getEffectiveCertificatesEntryRefs() {
            if (this.mCertificatesEntryRefs != null) {
                return this.mCertificatesEntryRefs;
            }
            if (this.mParentBuilder != null) {
                return this.mParentBuilder.getEffectiveCertificatesEntryRefs();
            }
            return java.util.Collections.emptyList();
        }

        public boolean hasCertificatesEntryRefs() {
            return this.mCertificatesEntryRefs != null;
        }

        java.util.List<android.security.net.config.CertificatesEntryRef> getCertificatesEntryRefs() {
            return this.mCertificatesEntryRefs;
        }

        android.security.net.config.NetworkSecurityConfig.Builder setCertificateTransparencyVerificationRequired(boolean z) {
            this.mCertificateTransparencyVerificationRequired = z;
            this.mCertificateTransparencyVerificationRequiredSet = true;
            return this;
        }

        private boolean getCertificateTransparencyVerificationRequired() {
            if (this.mCertificateTransparencyVerificationRequiredSet) {
                return this.mCertificateTransparencyVerificationRequired;
            }
            if (this.mParentBuilder != null) {
                return this.mParentBuilder.getCertificateTransparencyVerificationRequired();
            }
            return false;
        }

        public android.security.net.config.NetworkSecurityConfig build() {
            return new android.security.net.config.NetworkSecurityConfig(getEffectiveCleartextTrafficPermitted(), getEffectiveHstsEnforced(), getCertificateTransparencyVerificationRequired(), getEffectivePinSet(), getEffectiveCertificatesEntryRefs());
        }
    }
}
