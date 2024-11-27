package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class BouncyCastleProviderConfiguration implements com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration {
    private volatile java.lang.Object dhDefaultParams;
    private volatile com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec ecImplicitCaParams;
    private static java.security.Permission BC_EC_LOCAL_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA);
    private static java.security.Permission BC_EC_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.EC_IMPLICITLY_CA);
    private static java.security.Permission BC_DH_LOCAL_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS);
    private static java.security.Permission BC_DH_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.DH_DEFAULT_PARAMS);
    private static java.security.Permission BC_EC_CURVE_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.ACCEPTABLE_EC_CURVES);
    private static java.security.Permission BC_ADDITIONAL_EC_CURVE_PERMISSION = new com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission(com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME, com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.ADDITIONAL_EC_PARAMETERS);
    private java.lang.ThreadLocal ecThreadSpec = new java.lang.ThreadLocal();
    private java.lang.ThreadLocal dhThreadSpec = new java.lang.ThreadLocal();
    private volatile java.util.Set acceptableNamedCurves = new java.util.HashSet();
    private volatile java.util.Map additionalECParameters = new java.util.HashMap();

    BouncyCastleProviderConfiguration() {
    }

    void setParameter(java.lang.String str, java.lang.Object obj) {
        com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec;
        java.lang.SecurityManager securityManager = java.lang.System.getSecurityManager();
        if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.THREAD_LOCAL_EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            if ((obj instanceof com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) || obj == null) {
                eCParameterSpec = (com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) obj;
            } else {
                eCParameterSpec = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj);
            }
            if (eCParameterSpec == null) {
                this.ecThreadSpec.remove();
                return;
            } else {
                this.ecThreadSpec.set(eCParameterSpec);
                return;
            }
        }
        if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            if ((obj instanceof com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) || obj == null) {
                this.ecImplicitCaParams = (com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) obj;
                return;
            } else {
                this.ecImplicitCaParams = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertSpec((java.security.spec.ECParameterSpec) obj);
                return;
            }
        }
        if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.THREAD_LOCAL_DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
            }
            if (!(obj instanceof javax.crypto.spec.DHParameterSpec) && !(obj instanceof javax.crypto.spec.DHParameterSpec[]) && obj != null) {
                throw new java.lang.IllegalArgumentException("not a valid DHParameterSpec");
            }
            if (obj == null) {
                this.dhThreadSpec.remove();
                return;
            } else {
                this.dhThreadSpec.set(obj);
                return;
            }
        }
        if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_PERMISSION);
            }
            if ((obj instanceof javax.crypto.spec.DHParameterSpec) || (obj instanceof javax.crypto.spec.DHParameterSpec[]) || obj == null) {
                this.dhDefaultParams = obj;
                return;
            }
            throw new java.lang.IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
        }
        if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.ACCEPTABLE_EC_CURVES)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_CURVE_PERMISSION);
            }
            this.acceptableNamedCurves = (java.util.Set) obj;
        } else if (str.equals(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider.ADDITIONAL_EC_PARAMETERS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
            }
            this.additionalECParameters = (java.util.Map) obj;
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec getEcImplicitlyCa() {
        com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = (com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) this.ecThreadSpec.get();
        if (eCParameterSpec != null) {
            return eCParameterSpec;
        }
        return this.ecImplicitCaParams;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public javax.crypto.spec.DHParameterSpec getDHDefaultParameters(int i) {
        java.lang.Object obj = this.dhThreadSpec.get();
        if (obj == null) {
            obj = this.dhDefaultParams;
        }
        if (obj instanceof javax.crypto.spec.DHParameterSpec) {
            javax.crypto.spec.DHParameterSpec dHParameterSpec = (javax.crypto.spec.DHParameterSpec) obj;
            if (dHParameterSpec.getP().bitLength() == i) {
                return dHParameterSpec;
            }
        } else if (obj instanceof javax.crypto.spec.DHParameterSpec[]) {
            javax.crypto.spec.DHParameterSpec[] dHParameterSpecArr = (javax.crypto.spec.DHParameterSpec[]) obj;
            for (int i2 = 0; i2 != dHParameterSpecArr.length; i2++) {
                if (dHParameterSpecArr[i2].getP().bitLength() == i) {
                    return dHParameterSpecArr[i2];
                }
            }
        }
        com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters = (com.android.internal.org.bouncycastle.crypto.params.DHParameters) com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSizedProperty(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.Property.DH_DEFAULT_PARAMS, i);
        if (dHParameters != null) {
            return new com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec(dHParameters);
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public java.security.spec.DSAParameterSpec getDSADefaultParameters(int i) {
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters = (com.android.internal.org.bouncycastle.crypto.params.DSAParameters) com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSizedProperty(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.Property.DSA_DEFAULT_PARAMS, i);
        if (dSAParameters != null) {
            return new java.security.spec.DSAParameterSpec(dSAParameters.getP(), dSAParameters.getQ(), dSAParameters.getG());
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public java.util.Set getAcceptableNamedCurves() {
        return java.util.Collections.unmodifiableSet(this.acceptableNamedCurves);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfiguration
    public java.util.Map getAdditionalECParameters() {
        return java.util.Collections.unmodifiableMap(this.additionalECParameters);
    }
}
