package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class KeyAgreementSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi {
    private static final com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter converter = new com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter();
    private java.lang.Object agreement;
    private java.lang.String kaAlgorithm;
    private com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters;
    private byte[] result;

    protected KeyAgreementSpi(java.lang.String str, com.android.internal.org.bouncycastle.crypto.BasicAgreement basicAgreement, com.android.internal.org.bouncycastle.crypto.DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.kaAlgorithm = str;
        this.agreement = basicAgreement;
    }

    protected byte[] bigIntToBytes(java.math.BigInteger bigInteger) {
        return converter.integerToBytes(bigInteger, converter.getByteLength(this.parameters.getCurve()));
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected java.security.Key engineDoPhase(java.security.Key key, boolean z) throws java.security.InvalidKeyException, java.lang.IllegalStateException {
        if (this.parameters == null) {
            throw new java.lang.IllegalStateException(this.kaAlgorithm + " not initialised.");
        }
        if (!z) {
            throw new java.lang.IllegalStateException(this.kaAlgorithm + " can only be between two parties.");
        }
        if (!(key instanceof java.security.PublicKey)) {
            throw new java.security.InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(com.android.internal.org.bouncycastle.jce.interfaces.ECPublicKey.class) + " for doPhase");
        }
        try {
            this.result = bigIntToBytes(((com.android.internal.org.bouncycastle.crypto.BasicAgreement) this.agreement).calculateAgreement(com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.generatePublicKeyParameter((java.security.PublicKey) key)));
            return null;
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException("calculation failed: " + e.getMessage()) { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi.1
                @Override // java.lang.Throwable
                public java.lang.Throwable getCause() {
                    return e;
                }
            };
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        initFromKey(key, algorithmParameterSpec);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        try {
            initFromKey(key, null);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new java.security.InvalidKeyException(e.getMessage());
        }
    }

    private void initFromKey(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        if (!(key instanceof java.security.PrivateKey)) {
            throw new java.security.InvalidKeyException(this.kaAlgorithm + " key agreement requires " + getSimpleName(com.android.internal.org.bouncycastle.jce.interfaces.ECPrivateKey.class) + " for initialisation");
        }
        if (this.kdf == null && (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
        }
        com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters eCPrivateKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter((java.security.PrivateKey) key);
        this.parameters = eCPrivateKeyParameters.getParameters();
        this.ukmParameters = algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec ? ((com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec) algorithmParameterSpec).getUserKeyingMaterial() : null;
        ((com.android.internal.org.bouncycastle.crypto.BasicAgreement) this.agreement).init(eCPrivateKeyParameters);
    }

    private static java.lang.String getSimpleName(java.lang.Class cls) {
        java.lang.String name = cls.getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
    protected byte[] calcSecret() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.result);
    }

    public static class DH extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyAgreementSpi {
        public DH() {
            super("ECDH", new com.android.internal.org.bouncycastle.crypto.agreement.ECDHBasicAgreement(), null);
        }
    }
}
