package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreECPublicKey extends android.security.keystore2.AndroidKeyStorePublicKey implements java.security.interfaces.ECPublicKey {
    private final java.security.spec.ECParameterSpec mParams;
    private final java.security.spec.ECPoint mW;

    public AndroidKeyStoreECPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, byte[] bArr, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.security.spec.ECParameterSpec eCParameterSpec, java.security.spec.ECPoint eCPoint) {
        super(keyDescriptor, keyMetadata, bArr, android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, keyStoreSecurityLevel);
        this.mParams = eCParameterSpec;
        this.mW = eCPoint;
    }

    public AndroidKeyStoreECPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.security.interfaces.ECPublicKey eCPublicKey) {
        this(keyDescriptor, keyMetadata, eCPublicKey.getEncoded(), keyStoreSecurityLevel, eCPublicKey.getParams(), eCPublicKey.getW());
        if (!"X.509".equalsIgnoreCase(eCPublicKey.getFormat())) {
            throw new java.lang.IllegalArgumentException("Unsupported key export format: " + eCPublicKey.getFormat());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        r8 = android.security.keystore2.KeymasterUtils.getCurveSpec(android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r4.keyParameter.value.getEcCurve()));
     */
    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        java.security.spec.ECParameterSpec eCParameterSpec;
        java.security.spec.ECParameterSpec eCParameterSpec2 = this.mParams;
        android.system.keystore2.Authorization[] authorizations = getAuthorizations();
        int length = authorizations.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                eCParameterSpec = eCParameterSpec2;
                break;
            }
            android.system.keystore2.Authorization authorization = authorizations[i];
            try {
                if (authorization.keyParameter.tag == 268435466) {
                    break;
                }
                i++;
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException("Unable to parse EC curve " + authorization.keyParameter.value.getEcCurve());
            }
        }
        return new android.security.keystore2.AndroidKeyStoreECPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), getSecurityLevel(), eCParameterSpec);
    }

    @Override // java.security.interfaces.ECKey
    public java.security.spec.ECParameterSpec getParams() {
        return this.mParams;
    }

    @Override // java.security.interfaces.ECPublicKey
    public java.security.spec.ECPoint getW() {
        return this.mW;
    }
}
