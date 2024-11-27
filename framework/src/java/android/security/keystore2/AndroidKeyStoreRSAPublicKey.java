package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreRSAPublicKey extends android.security.keystore2.AndroidKeyStorePublicKey implements java.security.interfaces.RSAPublicKey {
    private final java.math.BigInteger mModulus;
    private final java.math.BigInteger mPublicExponent;

    public AndroidKeyStoreRSAPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, byte[] bArr, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        super(keyDescriptor, keyMetadata, bArr, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, keyStoreSecurityLevel);
        this.mModulus = bigInteger;
        this.mPublicExponent = bigInteger2;
    }

    public AndroidKeyStoreRSAPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.security.interfaces.RSAPublicKey rSAPublicKey) {
        this(keyDescriptor, keyMetadata, rSAPublicKey.getEncoded(), keyStoreSecurityLevel, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        if (!"X.509".equalsIgnoreCase(rSAPublicKey.getFormat())) {
            throw new java.lang.IllegalArgumentException("Unsupported key export format: " + rSAPublicKey.getFormat());
        }
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    public android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        return new android.security.keystore2.AndroidKeyStoreRSAPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), getSecurityLevel(), this.mModulus);
    }

    @Override // java.security.interfaces.RSAKey
    public java.math.BigInteger getModulus() {
        return this.mModulus;
    }

    @Override // java.security.interfaces.RSAPublicKey
    public java.math.BigInteger getPublicExponent() {
        return this.mPublicExponent;
    }
}
