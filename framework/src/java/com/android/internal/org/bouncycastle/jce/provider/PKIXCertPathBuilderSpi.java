package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PKIXCertPathBuilderSpi extends java.security.cert.CertPathBuilderSpi {
    private java.lang.Exception certPathException;
    private final boolean isForCRLCheck;

    public PKIXCertPathBuilderSpi() {
        this(false);
    }

    PKIXCertPathBuilderSpi(boolean z) {
        this.isForCRLCheck = z;
    }

    @Override // java.security.cert.CertPathBuilderSpi
    public java.security.cert.CertPathBuilderResult engineBuild(java.security.cert.CertPathParameters certPathParameters) throws java.security.cert.CertPathBuilderException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters pKIXExtendedBuilderParameters;
        com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder builder;
        if (certPathParameters instanceof java.security.cert.PKIXBuilderParameters) {
            java.security.cert.PKIXBuilderParameters pKIXBuilderParameters = (java.security.cert.PKIXBuilderParameters) certPathParameters;
            com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder builder2 = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedParameters.Builder(pKIXBuilderParameters);
            if (certPathParameters instanceof com.android.internal.org.bouncycastle.x509.ExtendedPKIXParameters) {
                com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (com.android.internal.org.bouncycastle.x509.ExtendedPKIXBuilderParameters) certPathParameters;
                java.util.Iterator it = extendedPKIXBuilderParameters.getAdditionalStores().iterator();
                while (it.hasNext()) {
                    builder2.addCertificateStore((com.android.internal.org.bouncycastle.jcajce.PKIXCertStore) it.next());
                }
                builder = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder(builder2.build());
                builder.addExcludedCerts(extendedPKIXBuilderParameters.getExcludedCerts());
                builder.setMaxPathLength(extendedPKIXBuilderParameters.getMaxPathLength());
            } else {
                builder = new com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.Builder(pKIXBuilderParameters);
            }
            pKIXExtendedBuilderParameters = builder.build();
        } else if (certPathParameters instanceof com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters) {
            pKIXExtendedBuilderParameters = (com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters) certPathParameters;
        } else {
            throw new java.security.InvalidAlgorithmParameterException("Parameters must be an instance of " + java.security.cert.PKIXBuilderParameters.class.getName() + " or " + com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters.class.getName() + android.media.MediaMetrics.SEPARATOR);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it2 = com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTargets(pKIXExtendedBuilderParameters).iterator();
        java.security.cert.CertPathBuilderResult certPathBuilderResult = null;
        while (it2.hasNext() && certPathBuilderResult == null) {
            certPathBuilderResult = build((java.security.cert.X509Certificate) it2.next(), pKIXExtendedBuilderParameters, arrayList);
        }
        if (certPathBuilderResult == null && this.certPathException != null) {
            if (this.certPathException instanceof com.android.internal.org.bouncycastle.jce.provider.AnnotatedException) {
                throw new java.security.cert.CertPathBuilderException(this.certPathException.getMessage(), this.certPathException.getCause());
            }
            throw new java.security.cert.CertPathBuilderException("Possible certificate chain could not be validated.", this.certPathException);
        }
        if (certPathBuilderResult == null && this.certPathException == null) {
            throw new java.security.cert.CertPathBuilderException("Unable to find certificate chain.");
        }
        return certPathBuilderResult;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected java.security.cert.CertPathBuilderResult build(java.security.cert.X509Certificate x509Certificate, com.android.internal.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters pKIXExtendedBuilderParameters, java.util.List list) {
        java.security.cert.CertPathBuilderResult certPathBuilderResult = null;
        if (list.contains(x509Certificate) || pKIXExtendedBuilderParameters.getExcludedCerts().contains(x509Certificate)) {
            return null;
        }
        if (pKIXExtendedBuilderParameters.getMaxPathLength() != -1 && list.size() - 1 > pKIXExtendedBuilderParameters.getMaxPathLength()) {
            return null;
        }
        list.add(x509Certificate);
        try {
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory certificateFactory = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory();
            com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi = new com.android.internal.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi(this.isForCRLCheck);
            try {
            } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
                this.certPathException = e;
            }
            if (com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.isIssuerTrustAnchor(x509Certificate, pKIXExtendedBuilderParameters.getBaseParameters().getTrustAnchors(), pKIXExtendedBuilderParameters.getBaseParameters().getSigProvider())) {
                try {
                    java.security.cert.CertPath engineGenerateCertPath = certificateFactory.engineGenerateCertPath(list);
                    try {
                        java.security.cert.PKIXCertPathValidatorResult pKIXCertPathValidatorResult = (java.security.cert.PKIXCertPathValidatorResult) pKIXCertPathValidatorSpi.engineValidate(engineGenerateCertPath, pKIXExtendedBuilderParameters);
                        return new java.security.cert.PKIXCertPathBuilderResult(engineGenerateCertPath, pKIXCertPathValidatorResult.getTrustAnchor(), pKIXCertPathValidatorResult.getPolicyTree(), pKIXCertPathValidatorResult.getPublicKey());
                    } catch (java.lang.Exception e2) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Certification path could not be validated.", e2);
                    }
                } catch (java.lang.Exception e3) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Certification path could not be constructed from certificate list.", e3);
                }
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll(pKIXExtendedBuilderParameters.getBaseParameters().getCertificateStores());
            try {
                arrayList.addAll(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAdditionalStoresFromAltNames(x509Certificate.getExtensionValue(com.android.internal.org.bouncycastle.asn1.x509.Extension.issuerAlternativeName.getId()), pKIXExtendedBuilderParameters.getBaseParameters().getNamedCertificateStoreMap()));
                java.util.HashSet hashSet = new java.util.HashSet();
                try {
                    hashSet.addAll(com.android.internal.org.bouncycastle.jce.provider.CertPathValidatorUtilities.findIssuerCerts(x509Certificate, pKIXExtendedBuilderParameters.getBaseParameters().getCertStores(), arrayList));
                    if (hashSet.isEmpty()) {
                        throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No issuer certificate for certificate in certification path found.");
                    }
                    java.util.Iterator it = hashSet.iterator();
                    while (it.hasNext() && certPathBuilderResult == null) {
                        certPathBuilderResult = build((java.security.cert.X509Certificate) it.next(), pKIXExtendedBuilderParameters, list);
                    }
                    if (certPathBuilderResult == null) {
                        list.remove(x509Certificate);
                    }
                    return certPathBuilderResult;
                } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e4) {
                    throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Cannot find issuer certificate for certificate in certification path.", e4);
                }
            } catch (java.security.cert.CertificateParsingException e5) {
                throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("No additional X.509 stores can be added from certificate locations.", e5);
            }
            this.certPathException = e;
            if (certPathBuilderResult == null) {
            }
            return certPathBuilderResult;
        } catch (java.lang.Exception e6) {
            throw new java.lang.RuntimeException("Exception creating support classes.");
        }
    }
}
